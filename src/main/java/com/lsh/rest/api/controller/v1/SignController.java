package com.lsh.rest.api.controller.v1;

import com.lsh.rest.api.advice.exception.CEmailSigninFailedException;
import com.lsh.rest.api.config.security.JwtTokenProvider;
import com.lsh.rest.api.entity.User;
import com.lsh.rest.api.model.response.CommonResult;
import com.lsh.rest.api.model.response.SingleResult;
import com.lsh.rest.api.repo.UserJpaRepo;
import com.lsh.rest.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/signin")
    public SingleResult<String> signin(@RequestParam String id, @RequestParam String password) {
        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();
    System.out.println("getUserName ::::::::: " + user.getUsername());
        System.out.println("getRoles" + user.getRoles());

        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }

    @GetMapping(value = "/signup")
    public CommonResult signup(@RequestParam String id, @RequestParam String password, @RequestParam String name) {
        userJpaRepo.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }

}
