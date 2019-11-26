package com.lsh.rest.api.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InstallController {
    @GetMapping(value = "helloworld/string")
    @ResponseBody
public String helloworldString() {
    return "Hello~!!!";
}

@GetMapping(value = "helloworld/json")
@ResponseBody
public Hello helloworldJson() {
        Hello hello = new Hello();
        hello.message = "hellowolrd";
        return hello;
}

@GetMapping(value = "helloworld/page")
public String helloworld() {
        return "helloworld";
    }

@GetMapping(value = "helloworld/a")
public String helloworldPage() {
        return "a";
}

@Getter
@Setter
public static class Hello {
        private String message;
}
}
