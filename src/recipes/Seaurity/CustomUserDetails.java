package recipes.Seaurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
  private final String username;
  private final String password;
  private final Long userId;
  private final List<GrantedAuthority> rolesAndAuthorities;

  public CustomUserDetails(User user, Long userId) {
    username = user.getUsername();
    password = user.getPassword();
    this.userId = userId;
    rolesAndAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return rolesAndAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public Long getUserId() {
    return userId;
  }

  // 4 remaining methods that just return true
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
