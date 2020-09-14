package com.babooin.testapp.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.babooin")
@PropertySources({@PropertySource("classpath:datasource.properties"), @PropertySource("classpath:config.properties") })
@EnableTransactionManagement
public class AppConfig {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private Environment env;
	
	@Bean
	public XmlMapper xmlMapper() {
		return new XmlMapper();
	}
	
	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("/templates/");
		resolver.setSuffix(".html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(false);
		return resolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateEngineMessageSource(messageSource());
		return engine;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setCharacterEncoding("UTF-8");
		resolver.setTemplateEngine(templateEngine());
		return resolver;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}
	
	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource source = new ComboPooledDataSource();
		
		String driverClass = env.getProperty("jdbc.driver");
		String jdbcUrl = env.getProperty("jdbc.url");
		String user = env.getProperty("jdbc.user");
		String pass = env.getProperty("jdbc.password");
		
		int initialPoolSize = getIntProperty("connection.pool.initialPoolSize");
		int minPoolSize = getIntProperty("connection.pool.minPoolSize");
		int maxPoolSize = getIntProperty("connection.pool.maxPoolSize");
		int maxIdleTime = getIntProperty("connection.pool.maxIdleTime");
		
		logger.info(" >>> jdbc.url = " + jdbcUrl);
		logger.info(" >>> jdbc.user = " + user);
		
		try {
			source.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		source.setJdbcUrl(jdbcUrl); 
		source.setUser(user);
		source.setPassword(pass);
		
		source.setInitialPoolSize(initialPoolSize);
		source.setMinPoolSize(minPoolSize);
		source.setMaxPoolSize(maxPoolSize);
		source.setMaxIdleTime(maxIdleTime);
		
		return source;
		
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactoryBean.setHibernateProperties(getHibernateProperties());
		
		return sessionFactoryBean;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		
		String dialect = "hibernate.dialect";
		String showSql = "hibernate.show_sql";
		String hbm2ddl = "hibernate.hbm2ddl.auto";
		
		hibernateProperties.setProperty(dialect, env.getProperty(dialect));
		hibernateProperties.setProperty(showSql, env.getProperty(showSql));
		hibernateProperties.setProperty(hbm2ddl, env.getProperty(hbm2ddl));
		
		return hibernateProperties;
	}
	
	private int getIntProperty(String property) {
		return Integer.parseInt(env.getProperty(property));
	}

}
