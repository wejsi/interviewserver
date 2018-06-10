package org.interview.config;

import org.interview.service.CargaServico;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { JmxAutoConfiguration.class })
@ComponentScan(basePackages = { "org.interview" })
@EnableJpaRepositories(basePackages = { "org.interview.repository" })
@EntityScan(basePackages = { "org.interview.db" })
@Import(DBServerConfiguration.class)
public class Application extends SpringBootServletInitializer {

	@Autowired
	private CargaServico cargaServico;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	InitializingBean cargaInicial() {
		return () -> {
			cargaServico.carregarColaboradores();
		};
	}

}