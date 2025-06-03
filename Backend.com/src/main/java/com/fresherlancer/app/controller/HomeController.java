package com.fresherlancer.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/home")
public class HomeController {

    @GetMapping
    public String getHome(){
        return "Home";
    }
}
