package com.lsh.rest.api.controller.v1;

import com.lsh.rest.api.advice.exception.CUserNotFoundException;
import com.lsh.rest.api.entity.User;
import com.lsh.rest.api.model.response.CommonResult;
import com.lsh.rest.api.model.response.ListResult;
import com.lsh.rest.api.model.response.SingleResult;
import com.lsh.rest.api.repo.UserJpaRepo;
import com.lsh.rest.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController //결과물을 Json으로 출력
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 조회", notes = "모든회원을 조회하는 펑션")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userJpaRepo.findAll());
    }

    // @PathVariable를 넣어줘야함. 주소에서 받아오는것이라서
//    @GetMapping(value = "/user/{msrl}")
//    public SingleResult<User> findUserById(@PathVariable long msrl) throws Exception {
//        // advice/ExceptionAdvice사용
//      return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(Exception::new));
//    }

    /* 인증없이 msrl로 단건 조
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@PathVariable long msrl){
        // advice/ExceptionAdvice사용 (CUserNotFoundException 사용)
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
    }
*/
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(@RequestParam String lang){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        System.out.println("getName :   " + id);
        long idl = Long.parseLong(id);
        System.out.println("getName :   " + idl);
        return responseService.getSingleResult(userJpaRepo.findById(idl).orElseThrow(CUserNotFoundException::new));
    }

    @PostMapping(value = "/user")
    public SingleResult<User> save(@RequestParam String uid, @RequestParam String name) {
        User user = User.builder().uid(uid).name(name).build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @PutMapping(value = "/user")
    public SingleResult<User> modify(@RequestParam long msrl, @RequestParam String uid, @RequestParam String name) {
        User user = User.builder().msrl(msrl).name(name).build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete (@PathVariable long msrl) {
       userJpaRepo.deleteById(msrl);
        return responseService.getSuccessResult();
    }

//    @ApiOperation(value = "회원 입력1", notes = "회원을 입력한다.")
//    @PostMapping(value = "/user")
//    public User save() {
//        User user = User.builder().uid("SH@gmail.com").name("승한").build();
//        return userJpaRepo.save(user);
//    }
//    @PostMapping(value = "/user2")
//    public User save2() {
//        User user = User.builder().uid("LL@gmail.com").name("수진").build();
//        return userJpaRepo.save(user);
//    }
}
