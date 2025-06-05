package com.ecomm.ecommservice.security;

import com.ecomm.ecommservice.entity.UserInfo;
import com.ecomm.ecommservice.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = userInfoRepository.findUserInfoByEmail(username);

        return userDetails
                .map(UserInfoSecurityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }
}
