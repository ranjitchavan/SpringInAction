package com.einfochips.webportal.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.einfochips.webportal.utils.GCCorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// Inject object of UserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	// Inject object of AuthenticationEntryPoint
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	// Default constructor
	public WebSecurityConfiguration() {
		// Empty Constructor
	}

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new AuthenticationTokenFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling()
				.authenticationEntryPoint(this.authenticationEntryPoint).and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		http.authorizeRequests().antMatchers("/get-all-user", "/add-user", "/update-user",
				"/change-password", "/get-loggedin-user", "/activate-user",
				"/deactivate-user", "/save-uidai-public-certificate",
				"/get-all-uidai-public-certificate", "/delete-uidai-public-certificate",
				"/save-vender-certificate", "/get-vender-certificates",
				"/get-vender-cert", "/get-uidai-cert", "/update-vender-certificate",
				"/add-device-list", "/de-register-device", "/deviceReport",
				"/errorReport", "/get-previous-versions", "/get-current-versions",
				"/add-latest-version", "/update-current-version", "/edit-version",
				"/download-version", "/download-signature").authenticated()
				.and()
				.addFilterBefore(authenticationTokenFilterBean(),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new GCCorsFilter(), ChannelProcessingFilter.class);
		http.logout().logoutUrl("/logout")
				.logoutSuccessHandler(new LogoutSuccessHandler())
				.deleteCookies("JSESSIONID").permitAll();
	}

	// This configuration for By pass the API without security
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/login");
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
}
