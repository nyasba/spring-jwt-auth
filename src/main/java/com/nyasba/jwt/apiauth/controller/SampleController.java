package com.nyasba.jwt.apiauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping(value = "/public")
    public String publicApi(){
        return "this is public";
    }

    @GetMapping(value = "/private")
    public String privateApi(){
        return "this is private";
    }

}
