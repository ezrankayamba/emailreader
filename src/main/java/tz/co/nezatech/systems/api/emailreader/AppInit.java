package tz.co.nezatech.systems.api.emailreader;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tz.co.nezatech.systems.api.emailreader.data.Property;
import tz.co.nezatech.systems.api.emailreader.repository.PropertyRepository;

@Component
public class AppInit {

	private static final Logger LOG = Logger.getLogger(AppInit.class);

	@Autowired
	PropertyRepository propertyRepos;

	@PostConstruct
	public void postConstruct() {
		Properties props = new Properties();
		try {
			props.load(ScheduledTasks.class.getClassLoader().getResourceAsStream("mail.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		String load = props.getProperty("load.init.properties");
		if (load != null && 1==Integer.parseInt(load)) {
			for (Object key : props.keySet()) {
				try {
					propertyRepos.create(new Property(null, key.toString(), props.getProperty((String) key)));
					LOG.info(String.format("%s=%s", key, props.get(key)));
				} catch (Exception e) {
				}
			}
		}
	}

	public void init() {
		LOG.info("init-method");
	}
}
