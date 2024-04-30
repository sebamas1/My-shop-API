package web_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

@Configuration
class SecurityConfig {

    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeHttpRequests(request -> request
                         .requestMatchers("/sebas/**")
                         .authenticated())
                 .httpBasic(Customizer.withDefaults())
                 .csrf(csrf -> csrf.disable());
         return http.build();
    }

   @Bean
   PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

   @Bean
  UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
   User.UserBuilder users = User.builder();
   UserDetails seba = users
     .username("seba")
     .password(passwordEncoder.encode("abc123"))
     .roles() // Faltarian los roles
     .build();
   return new InMemoryUserDetailsManager(seba);
  }

}
