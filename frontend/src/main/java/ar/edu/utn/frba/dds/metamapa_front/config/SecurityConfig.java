package ar.edu.utn.frba.dds.metamapa_front.config;

import ar.edu.utn.frba.dds.metamapa_front.providers.CustomAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

  @Bean
  public AuthenticationManager authManager(HttpSecurity http, CustomAuthProvider provider) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(provider)
        .build();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // Recursos estáticos y login público
            .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**", "/colecciones", "/colecciones/**", "/hechos/**", "/404").permitAll()
            // Ejemplo: Acceso a alumnos: ADMIN y DOCENTE
            //.requestMatchers("/admin/**").hasAnyRole("ADMIN")
            // Lo demás requiere autenticación
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")    // tu template de login
            .permitAll()
                .successHandler(((request, response, authentication) -> {
                  var auth = authentication.getAuthorities();
                  if (auth.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                    response.sendRedirect("/admin");
                  } else {
                    response.sendRedirect("/colecciones");
                  }
                }))
//            .defaultSuccessUrl("/colecciones", true) // redirigir tras login exitoso
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout") // redirigir tras logout
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            // Usuario no autenticado → redirigir a login
            .authenticationEntryPoint((request, response, authException) ->
                response.sendRedirect("/login?unauthorized")
            )
            // Usuario autenticado pero sin permisos → redirigir a página de error
            .accessDeniedHandler((request, response, accessDeniedException) ->
                response.sendRedirect("/403")
            )
        );

    return http.build();
  }
}
