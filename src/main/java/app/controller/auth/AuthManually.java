package app.controller.auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthManually {

    public void login(String userName, String password, String authority) {
        try {
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(authority));

            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password, authorities);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }
}
