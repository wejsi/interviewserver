package org.interview.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/***
 * Classe responsável pela configuração do datasource.
 * 
 * @author weslen.silva
 *
 */
@Configuration
@EnableJpaRepositories(DBServerConfiguration.DB_SCAN)
public class DBServerConfiguration {
	public static final String DB_SCAN = "org.interview.db";

	/***
	 * Bean responsável pela definição do datasouce. Visto que foi escolhido HSQLDB.
	 * 
	 * @return dataSource
	 * @throws Exception
	 */
	@Bean
	public DataSource dataSource() throws Exception {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/dbinterview");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		return dataSource;
	}

	/***
	 * Bean responsável pela configuração do datasource.
	 * 
	 * @param dataSource
	 * @return entityManagerFactoryBean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.put("hibernate.default_schema", "PUBLIC");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "false");
		properties.put("hibernate.hbm2ddl.auto", "create");

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);

		entityManagerFactoryBean.setJpaProperties(properties);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		// Escaneando entidades do pacote DB_SCAN.
		entityManagerFactoryBean.setPackagesToScan(DB_SCAN);

		return entityManagerFactoryBean;
	}
}
