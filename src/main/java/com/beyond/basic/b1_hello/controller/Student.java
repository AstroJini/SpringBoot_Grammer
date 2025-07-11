package com.beyond.basic.b1_hello.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private String name;
    private String email;
    private List<Score> scores;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Score{
        private String subject;
        private int point;
    }
}



