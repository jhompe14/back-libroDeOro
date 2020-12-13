package com.scouts.backlibrodeoro;

import com.scouts.backlibrodeoro.auth.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class BackLibrodeoroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackLibrodeoroApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST,"/api/auth").permitAll()
					.antMatchers(HttpMethod.POST,"/api/usuario").permitAll()
					.antMatchers(HttpMethod.GET,"/api/grupo").permitAll()
					.antMatchers(HttpMethod.GET,"/api/rama").permitAll()
					.antMatchers(HttpMethod.GET,"/api/seccion").permitAll()
					.antMatchers(HttpMethod.GET,"/api/cargo").permitAll()
					.antMatchers(HttpMethod.POST,"/api/usuario/recovered/**").permitAll()
					.antMatchers(HttpMethod.GET, "/api/usuario/recovered/**").permitAll()
					.antMatchers(HttpMethod.PUT, "/api/usuario/recovered/**").permitAll()
					.antMatchers("/", "/static/**").permitAll()
					.anyRequest().authenticated();
			http.cors();
		}
	}

}
