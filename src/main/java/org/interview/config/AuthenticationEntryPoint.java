package org.interview.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/***
 * Classe responsável pela autenticação.
 * 
 * @author weslen.silva
 *
 */
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	/***
	 * Configura a autenicação básica.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authEx.getMessage());
	}

	/***
	 * Configurando realm para autenticação no serviço.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("colaboradorInterview");
		super.afterPropertiesSet();
	}

}
