package tk.maxuz.kwh.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.maxuz.kwh.database.repository.UserRepository;
import tk.maxuz.kwh.model.User;
import tk.maxuz.kwh.security.UserPrincipal;

@Service
public class DaoUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DaoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}