package com.beyond.basic.b1_hello.controller;


import lombok.*;

//@Getter //클래스 내의 모든 변수를 대상으로 getter 생성
@Data // Getter, Setter, ToString 메서드까지 모두 만들어주는 어노테이션이다.
@AllArgsConstructor //모든매개변수가 있는 생성자
@NoArgsConstructor //기본생성자
//기본생성자+getter로 parsing이 이뤄지므로 보통은 필수적 요소
public class Hello {
    private String name;
    private String email;

}
