package br.com.af.pokerchase.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  public JwtAuthenticationToken(UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
    super(principal, null, authorities);
  }
}
