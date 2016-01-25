package org.cheetyan.weibospider.main;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbc.properties")
public class TxConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean(name={"dataSource"},destroyMethod="close")
	public DataSource dataSource(@Value("${jdbc.url}") String url,@Value("${jdbc.driverClassName}") String driverClassName,
			@Value("${jdbc.username}") String username,@Value("${jdbc.password}") String password){
		BasicDataSource ds = new org.apache.commons.dbcp2.BasicDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean(name={"sessionFactory"})
	@Autowired
	public SessionFactory sessionFactory(@Qualifier("dataSource") DataSource ds) throws IOException{
		LocalSessionFactoryBean sf = new org.springframework.orm.hibernate4.LocalSessionFactoryBean();
		sf.setDataSource(ds);
		sf.setConfigLocations(new DefaultResourceLoader().getResource("classpath:hibernate.cfg.xml"));
		sf.setPackagesToScan("cheetyan.model");
		sf.afterPropertiesSet();
		return sf.getObject();
	}
	
	@Bean(name={"transactionManager"})
	@Autowired
	public org.springframework.orm.hibernate4.HibernateTransactionManager transactionManager(@Qualifier("sessionFactory") SessionFactory sf){
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(sf);
		tx.afterPropertiesSet();
		return tx;
	}
}
