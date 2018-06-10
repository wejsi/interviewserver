package org.interview.config;

import java.util.List;

import org.interview.db.Colaborador;
import org.interview.service.ColaboradorServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/***
 * Classe responsável pelas configurações de segurança.
 * 
 * @author weslen.silva
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Autowired
	private ColaboradorServico colaboradorServico;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);
	}

	/***
	 * Atribui acesso aos usuários do serviço.
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		List<Colaborador> colaboradores = colaboradorServico.obterTodos();
		for (Colaborador colaborador : colaboradores) {
			auth.inMemoryAuthentication().withUser(colaborador.getId()).password("123456").roles("USER");
		}

	}

}