package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.UserRepository;

import java.util.stream.Collectors;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String _userName) throws UsernameNotFoundException {
        ru.geekbrains.persist.model.User usr = userRepository.findByName(_userName).get();

        return userRepository.findByName(_userName)
                .map(user -> new User(
                        user.getUsername()
                        , user.getPassword()
                        , user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet())
                )).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
