/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tz.co.nezatech.systems.api.emailreader.data.Payment;
import tz.co.nezatech.systems.api.emailreader.repository.PaymentRepository;
import tz.co.nezatech.systems.api.emailreader.util.Transaction;

/**
 *
 * @author godfred.nkayamba
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PaymentRepository repository;

    @Scheduled(fixedRate = 10000)
    public void readMailNotifications() {
        //log.info("Reading mail notification at {}", dateFormat.format(new Date()));
        try {
            Properties props = new Properties();
            props.load(ScheduledTasks.class.getClassLoader().getResourceAsStream("mail.properties"));
            final String sender = props.getProperty("service.mail.sender");
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imaps");
            store.connect(props.getProperty("mail.imaps.username"), props.getProperty("mail.imaps.password"));
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Folder successFld = store.getFolder(String.format(props.getProperty("service.mail.folder.path"), props.getProperty("service.mail.id"), "Success"));
            successFld.open(Folder.READ_ONLY);
            Folder failFld = store.getFolder(String.format(props.getProperty("service.mail.folder.path"), props.getProperty("service.mail.id"), "Fail"));
            failFld.open(Folder.READ_ONLY);
            Message messages[];
            messages = folder.search(new SearchTerm() {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
                public boolean match(Message message) {
                    try {
                        String from = message.getFrom()[0].toString();
                        String msg = message.getContent().toString();
                        if (from.contains(sender) && msg.contains(msg)) {
                            return true;
                        }
                    } catch (MessagingException | IOException ex) {
                        log.error(ex.getMessage());
                    }
                    return false;
                }
            });
            for (Message message : messages) {
                Message mMsges[] = new Message[]{message};
                String msg = message.getContent().toString();
                msg = msg.split("\\[Tigo Tanzania\\]")[0];
                String soNo = message.getSubject().split(" ")[2];
                log.info("The message: {}", msg);

                boolean dataComplete = false;
                Transaction t = null;
                try {
                    if (msg.startsWith(props.getProperty("regex.tigop2p.sw.startswith")) && msg.contains(props.getProperty("regex.tigop2p.sw.contains"))) {
                        log.info("P2P Sw: {}", msg);
                        t = new Transaction(msg, Transaction.Channel.P2P, props);
                    } else if (msg.startsWith(props.getProperty("regex.tigop2p.en.startswith")) && msg.contains(props.getProperty("regex.tigop2p.en.contains"))) {//en
                        log.info("P2P En: {}", msg);
                        t = new Transaction(msg, Transaction.Channel.P2P_EN, props);
                    } else if (msg.contains(Transaction.Channel.MPESA.displayName())) {
                        log.info("IOP M-PESA: {}", msg);
                        t = new Transaction(msg, Transaction.Channel.MPESA, props);
                    } else if (msg.contains(Transaction.Channel.AIRTEL_MONEY.displayName())) {
                        log.info("IOP AIRTEL MONEY: {}", msg);
                        t = new Transaction(msg, Transaction.Channel.AIRTEL_MONEY, props);
                    } else if (msg.contains(Transaction.Channel.EZYPESA.displayName())) {
                        log.info("IOP EZY-PESA: {}", msg);
                        t = new Transaction(msg, Transaction.Channel.EZYPESA, props);
                    } else {
                        try {
                            t = new Transaction(msg, Transaction.Channel.DEFAULT_A2W, props);
                            if (t.getReceiptNo() != null) {
                                log.info("DEFAULT A2W: {}", msg);
                            } else {
                                log.debug("The message is not in proper format. Here is the message: {}", msg);
                            }
                        } catch (Exception e) {
                            log.debug("The message is not in proper format. Here is the message: {} | errorMsg: {}", msg, e.getMessage());
                        }
                    }

                } catch (Exception e) {
                    log.error("Nest level 2a, error msg: {}", e.getMessage());
                }
                if (t != null) {
                    log.info("The transaction: {}", t);
                    dataComplete = true;
                    double amount = t.getAmount();
                    String msisdn = null;
                    String txnId = null;
                    if (t.getChannel() != null && t.getChannel().contains("P2P") && t.getSender() != null) {//P2P has msisdn
                        int i = t.getSender().indexOf("-");
                        msisdn = t.getSender().substring(0, i).trim();
                        //String name = t.getSender().substring(i + 1).trim();
                    }
                    txnId = t.getChannel().contains("P2P") ? t.getTrxnId() : t.getReceiptNo();
                    String recordedBy = props.getProperty("service.auth.username");
                    String payeeMsisdn = props.getProperty("service.account.msisdn");
                    Date transDate = t.getTrxnDateObj();

                    log.info("Params;  TrxnID: {} Amount: {} MSISDN: {} TransDate: {}", txnId, amount, msisdn, transDate);
                    if (successFld.exists() && successFld.isOpen() && dataComplete) {

                        try {
                            repository.create(new Payment(t.getChannel(), txnId, payeeMsisdn, amount, msisdn, recordedBy, transDate));
                            folder.copyMessages(mMsges, successFld);
                            folder.setFlags(mMsges, new Flags(Flags.Flag.DELETED), true);
                            log.info("Payment successfully recorded for transaction ID: {}", soNo);
                        } catch (Exception e) {
                            log.error("Nest level 2b, error msg: {}", e.getMessage());
                            folder.copyMessages(mMsges, failFld);
                            folder.setFlags(mMsges, new Flags(Flags.Flag.DELETED), true);
                            log.info("Payment failed to be recorded for transaction ID: {}", soNo);
                        }
                    }
                } else {
                    folder.copyMessages(mMsges, failFld);
                    folder.setFlags(mMsges, new Flags(Flags.Flag.DELETED), true);
                    log.info("Payment failed to be recorded for transaction ID: {}", soNo);
                }
            }

            folder.close(false);
            successFld.close(false);
            failFld.close(false);
            store.close();

        } catch (IOException | MessagingException e) {
            log.error("Nest level 1, error msg: {}", e.getMessage());
        }
    }
    @Scheduled(fixedRate = 900000)
    public void keepAlive() {//erevy 15 minutes
        log.info("Reading mail notification keep alive at {}", dateFormat.format(new Date()));
    }
}
