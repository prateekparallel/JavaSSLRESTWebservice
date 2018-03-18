package com.web.common.config;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;


@Configuration
@PropertySource("file:C:\\Projects\\RESTService1\\mywebconfig.properties")
public class InitConfig {
	@Autowired
	private Environment env;
	
	@PostConstruct
	   public void init(){
	     initConfig();
	     System.out.println("Initialized");
	   }

		
	@Bean
	public static ApplicationConfig applicationConfig(){
		return ApplicationConfig.getInstance();
	}
	
	public InitConfig(){
	}
	
	public void initConfig() {
		
		System.out.println("initConfig - CommonConfig");
		String logPropertyFilePath = env.getProperty("LOG_PROPERTY_FILE_PATH");
		ApplicationConfig ac = ApplicationConfig.getInstance();
		ac.setKEYSTOREPATH(env.getProperty("KEYSTOREPATH"));
		ac.setTRUSTSTOREPATH(env.getProperty("TRUSTSTOREPATH"));
		ac.setKEYSTOREPW(env.getProperty("KEYSTOREPW"));
		ac.setTRUSTSTOREPW(env.getProperty("TRUSTSTOREPW"));
		ac.setKEYPASS(env.getProperty("KEYPASS"));
		ac.setHTTPS_SERV_URL(env.getProperty("HTTPS_SERV_URL"));
		ac.setKeystoreType(env.getProperty("keystoreType"));
		ac.setTrustAllCertificate(env.getProperty("trustAllCertificate"));
		ac.setKeymanageralgorithm(env.getProperty("keymanageralgorithm"));
		ac.setREGEX(env.getProperty("regex"));
		if (logPropertyFilePath != null) {
			System.out.println("Loading log properties file: "
					+ logPropertyFilePath);
			PropertyConfigurator.configure(logPropertyFilePath);
		} else {
			System.out.println("Called BasicConfigurator.configure()");
			BasicConfigurator.configure();
		}
	}

}

