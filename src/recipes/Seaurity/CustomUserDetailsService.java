package recipes.Seaurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.Repository.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var optional = userRepo.findTopDistinctByEmail(username);

    if (optional.isEmpty()) {
      throw new UsernameNotFoundException("Not found: " + username);
    }
    var userEntity = optional.get();
    List<GrantedAuthority> authorites =
            List.of(new SimpleGrantedAuthority("ROLE_USER"));
    User user = new User(username, userEntity.getPassword(), authorites);
    return new CustomUserDetails(user, userEntity.getId());
  }
}
