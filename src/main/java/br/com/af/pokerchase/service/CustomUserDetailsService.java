package br.com.af.pokerchase.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private List<User> users = List.of(
    new User("admin", "admin", Collections.emptyList()),
    new User("user", "user", Collections.emptyList())
  );

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    if (user == null) throw new UsernameNotFoundException("Usuário não encontrado");
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
  }
}
