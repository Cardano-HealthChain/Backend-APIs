package com.cardano.healthchain.cardano.healthchain.configs;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.DoctorRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.Healthchain_Roles_Enum;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorUserDetailsService implements UserDetailsService {
    private final DoctorRepositoryI doctorRepository;
    public DoctorUserDetailsService(DoctorRepositoryI doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            DoctorDataResponse doctorByEmail = doctorRepository.getDoctorByEmail(username);
            return new User(
                    doctorByEmail.getDoctor_id().toString(),
                    doctorByEmail.getPassword(),
                    List.of(new SimpleGrantedAuthority(Healthchain_Roles_Enum.DOCTOR.name()))
            );
        }catch(EmptyResultDataAccessException ex){
            throw new UsernameNotFoundException("No Doctor with that email could be found");
        }
    }
}
