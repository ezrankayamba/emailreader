package tz.co.nezatech.systems.api.emailreader;

import java.io.IOException;
import java.util.Properties;

import tz.co.nezatech.systems.api.emailreader.util.Transaction;
import tz.co.nezatech.systems.api.emailreader.util.Transaction.Channel;

public class TestRegex {
	public static void main(String... args) throws IOException {
		System.out.println("Test Regex");
		Properties p=new Properties();
		p.load(TestRegex.class.getClassLoader().getResourceAsStream("mail.properties"));
		
		String sample="Hamisho la pesa limekamilika. Salio jipya TSh 530,850. Umepokea TSh 15,000 kutoka kwa M-PESA, kumbukumbu ya malipo. MFICI.170702.1309.R75734763371. 4G21PHGXGT. 02/07/17 13:09.";
		Transaction t =new Transaction(sample, Channel.MPESA, p);
		System.out.println(t);
	}
}
