package com.ecomm.ecommservice.security;

import com.ecomm.ecommservice.entity.User;
import com.ecomm.ecommservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetails = userRepository.findByEmail(username);

        return userDetails
                .map(UserInfoSecurityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }
}
