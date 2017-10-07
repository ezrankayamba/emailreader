/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader.util;

/**
 *
 * @author nkayamba
 */
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

/**
 * Created by nkayamba on 7/30/16.
 */
public final class Transaction {

	String channel, rawTrxnId, trxnId, sender, receiptNo;
	double amount, balance;
	String trxnDate;
	java.text.SimpleDateFormat fmt;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Transaction.class);

	public Transaction(String msg, Channel channel, Properties props) {
		DecimalFormat formatRaw = new DecimalFormat(props.getProperty("regex.amount.raw"));
		DecimalFormat format = new DecimalFormat(props.getProperty("regex.amount.processed"));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(props.getProperty("regex.date.raw"));

		fmt = new java.text.SimpleDateFormat(props.getProperty("regex.date.processed"));

		try {
			switch (channel) {
			case MPESA: {
				String balRegex = props.getProperty("regex.mpesa.sw.balance");
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.mpesa.sw.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.mpesa.sw.channel"));
				String trxnDateStr = value(msg, props.getProperty("regex.mpesa.sw.txndate"));
				String receipt = value(msg, props.getProperty("regex.mpesa.sw.receipt"));
				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}

				this.channel = channelStr;
				this.receiptNo = receipt;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
			}
				break;
			case P2P: {
				String balRegex = props.getProperty("regex.tigop2p.sw.balance");
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.tigop2p.sw.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.tigop2p.sw.channel"));
				String trxnDateStr = value(msg, props.getProperty("regex.tigop2p.sw.txndate"));
				String trxnId = value(msg, props.getProperty("regex.tigop2p.sw.receipt"));
				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				this.channel = channel.toString();
				this.trxnId = trxnId;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
				this.sender = channelStr;

			}
				break;
			case P2P_EN: {
				String balRegex = props.getProperty("regex.tigop2p.en.balance");
				log.info(balRegex);
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.tigop2p.en.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.tigop2p.en.channel"));
				String trxnDateStr = value(msg, props.getProperty("regex.tigop2p.en.txndate"));
				String trxnId = value(msg, props.getProperty("regex.tigop2p.en.receipt"));
				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				this.channel = channel.toString();
				this.trxnId = trxnId;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
				this.sender = channelStr;

			}
				break;
			case AIRTEL_MONEY: {
				String balRegex = props.getProperty("regex.airtelmoney.sw.balance");
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.airtelmoney.sw.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.airtelmoney.sw.channel"));
				String trxnDateStr = value(msg, props.getProperty("regex.airtelmoney.sw.txndate"));
				String receipt = value(msg, props.getProperty("regex.airtelmoney.sw.receipt"));
				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}

				this.channel = channelStr;
				this.receiptNo = receipt;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
			}
				break;
			case EZYPESA: {
				String balRegex = props.getProperty("regex.ezypesa.sw.balance");
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.ezypesa.sw.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.ezypesa.sw.channel"));

				String trxnDateStr = value(msg, props.getProperty("regex.ezypesa.sw.txndate"));
				String receipt = value(msg, props.getProperty("regex.ezypesa.sw.receipt"));
				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				this.channel = channelStr;
				this.receiptNo = receipt;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
			}
				break;
			default: {
				log.info("Default Msg: {}", msg);
				String balRegex = props.getProperty("regex.default.sw.balance");
				String balanceStr = value(msg, balRegex);
				try {
					balanceStr = format.format(formatRaw.parse(balanceStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String amountStr = value(msg, props.getProperty("regex.default.sw.amount"));
				try {
					amountStr = format.format(formatRaw.parse(amountStr));
				} catch (ParseException e) {
					log.error(e.getMessage());
				}
				String channelStr = value(msg, props.getProperty("regex.default.sw.channel"));
				String trxnDateStr = value(msg, props.getProperty("regex.default.sw.txndate"));
				String receipt = value(msg, props.getProperty("regex.default.sw.receipt"));

				try {
					trxnDateStr = (trxnDateStr == null ? trxnDateStr : fmt.format(sdf.parse(trxnDateStr)));
				} catch (ParseException e) {
					log.error(e.getMessage());
					log.info("Regex: {}, Date: {}", props.getProperty("regex.default.sw.txndate"), trxnDateStr);
				}

				this.channel = channelStr;
				this.receiptNo = receipt;
				this.amount = Double.parseDouble(amountStr);
				this.balance = Double.parseDouble(balanceStr);
				this.trxnDate = trxnDateStr;
			}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public enum Channel {

		AIRTEL_MONEY("Airtel Money"), EZYPESA("ZANTEL TANZANIA"), DEFAULT_A2W("DEFAULT A2W"), MPESA("M-PESA"), P2P(
				"P2P"), P2P_EN("P2P English");

		private final String displayName;

		Channel(String displayName) {
			this.displayName = displayName;
		}

		public String displayName() {
			return displayName;
		}

		// Optionally and/or additionally, toString.
		@Override
		public String toString() {
			return displayName;
		}
	}

	String value(String str, String regex) {
		String val = null;
		try {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);

			// if our pattern matches the string, we can try to extract our
			// groups
			if (m.find()) {
				// get the two groups we were looking for
				val = m.group(1);
			}
		} catch (Exception e) {

		}

		return val;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTrxnId() {
		return trxnId;
	}

	public void setTrxnId(String trxnId) {
		this.trxnId = trxnId;
	}

	public String getRawTrxnId() {
		return rawTrxnId;
	}

	public void setRawTrxnId(String rawTrxnId) {
		this.rawTrxnId = rawTrxnId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTrxnDate() {
		return trxnDate;
	}

	public Date getTrxnDateObj() {
		try {
			return fmt.parse(this.getTrxnDate());
		} catch (ParseException ex) {
			Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public void setTrxnDate(String trxnDate) {
		this.trxnDate = trxnDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "Transaction [channel=" + channel + ", rawTrxnId=" + rawTrxnId + ", trxnId=" + trxnId + ", sender="
				+ sender + ", receiptNo=" + receiptNo + ", amount=" + amount + ", balance=" + balance + ", trxnDate="
				+ trxnDate + ", fmt=" + fmt + "]";
	}

}
