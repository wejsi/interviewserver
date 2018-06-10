package org.interview.config;

import org.interview.service.CargaColaboradorServico;
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
	private CargaColaboradorServico cargaServico;

	/***
	 * Responsável pelo inicio da aplicação. Visto que iniciará o serviço.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/***
	 * Criação de carga inicial para colaboradores.
	 * 
	 * @return
	 */
	@Bean
	InitializingBean cargaInicial() {
		return () -> {
			cargaServico.buildColaboradores();
		};
	}

}