package com.lsh.rest.api.service;

import com.lsh.rest.api.advice.exception.CUserNotFoundException;

import com.lsh.rest.api.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;
    @Override
    public UserDetails loadUserByUsername(String userPK)  {
        return userJpaRepo.findById(Long.valueOf(userPK)).orElseThrow(CUserNotFoundException::new);
    }
}
