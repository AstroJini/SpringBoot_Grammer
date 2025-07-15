package com.beyond.basic.b2_board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
//    case1. @ResponseStatus어노테이션 사용
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/annotation1")
    public String annotation1(){
        return "ok";
    }

}
