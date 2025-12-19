package com.cardano.healthchain.cardano.healthchain.configs;

import com.cardano.healthchain.cardano.healthchain.clinics.ClinicRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.Healthchain_Roles_Enum;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClinicUserDetailsService implements UserDetailsService {
    private final ClinicRepositoryI clinicRepository;
    public ClinicUserDetailsService(ClinicRepositoryI clinicRepository) {
        this.clinicRepository = clinicRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //implement later using a clinic repository to load clinics and ensure information is correct
        ClinicDataResponse clinicData = clinicRepository.getClinicByEmail(username);
        return new User(
                clinicData.getClinic_id().toString(),
                clinicData.getClinic_admin_password(),
                List.of(new SimpleGrantedAuthority(Healthchain_Roles_Enum.CLINIC.toString()))
        );
    }
}