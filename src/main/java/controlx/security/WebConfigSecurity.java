package controlx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService;
	
	@Override //Configura solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable() //Desativa as configurações padrões de memória.
		.authorizeRequests() //Permitir/restringir acessos.
		.antMatchers(HttpMethod.GET, "/rest/**").permitAll()
		.antMatchers(HttpMethod.POST, "/rest/**").permitAll()
		.antMatchers(HttpMethod.DELETE, "/rest/**").permitAll()
		.antMatchers(HttpMethod.PUT, "/rest/**").permitAll()
		.antMatchers(HttpMethod.GET, "/login").permitAll()
		
		.antMatchers(HttpMethod.GET, "/menu").hasAnyRole("ADMIN", "GERENTE", "ALMOXARIFE", "VENDEDOR")
		.antMatchers(HttpMethod.GET, "/estoque/**").hasAnyRole("ADMIN", "GERENTE", "ALMOXARIFE", "VENDEDOR")
		.antMatchers(HttpMethod.GET, "/categorias/**").hasAnyRole("ADMIN", "GERENTE", "ALMOXARIFE")
		.antMatchers(HttpMethod.GET, "/fornecedores/**").hasAnyRole("ADMIN", "GERENTE", "ALMOXARIFE")
		.antMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ADMIN", "GERENTE")
		.antMatchers(HttpMethod.GET, "/vender/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
		.antMatchers(HttpMethod.GET, "/historico/**").hasAnyRole("ADMIN", "GERENTE")
		
		.anyRequest().authenticated()
		.and().formLogin().permitAll() //Permite qualquer usuário
		.loginPage("/login")
		.defaultSuccessUrl("/index")
		.failureUrl("/login?error=true")
		.and().logout().logoutSuccessUrl("/login") //Mapeia URL de Logout e invalída usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}

	@Override //Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailService).passwordEncoder(NoOpPasswordEncoder.getInstance());
		
		
//		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()) new BCryptPasswordEncoder()
//		.withUser("Fabricio")
//		.password("123")
//		.roles("Admin");
	}
	
	@Override //Ignora URL's especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
}
