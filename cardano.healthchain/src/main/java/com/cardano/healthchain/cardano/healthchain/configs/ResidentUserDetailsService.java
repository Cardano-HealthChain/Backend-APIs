package com.cardano.healthchain.cardano.healthchain.configs;

import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryI;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.Healthchain_Roles_Enum;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResidentUserDetailsService implements UserDetailsService {
    private final UserRepositoryI userRepository;
    public ResidentUserDetailsService(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDataProfileResponse user = userRepository.getUserByEmail(username);
        return new User(
                user.getUser_id().toString(),
                user.getHashed_password(),
                List.of(new SimpleGrantedAuthority(Healthchain_Roles_Enum.RESIDENT.name()))
        );
    }
}