package com.beyond.basic.b1_hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Component어노테이션을 통해 별도의 객체를 생성할 필요가 없는 싱글톤 객체 생성 | 실행되면 메모리상 어딘가에 객체를 선언한다.
//Controller 어노테이션을 통해 쉽게 사용자의 http req를 분석하고, http res를 생성한다. | req-request, res-response

// get요청의 case들
// case1. 서버가 사용자에게 단순 String 데이터 return - @ResponseBody가 있을 경우

@Controller
//클래스 차원에 url매핑 시에는 RequestMapping을 사용한다.
@RequestMapping("/hello")
public class HelloController {
//    ResponseBody가 없고 return타입이 String인 경우에 서버는 화면을 찾음 templates폴더 밑에 helloworld.html을 찾아서 리턴(화면을 찾아서 리턴)
//    있으면 데이터를 찾아서 리턴

    @GetMapping("") //아래 method에 대한 server의 endpoint를 설정한 것
    @ResponseBody
    public String HelloWorld(){
        return "helloworld";
    }

//    case 2. 서버가 사용자에게 String(json형식)의 데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
        Hello h1 = new Hello("hong1","hong1@naver.com");
//        직접 json으로 직렬화 할 필요없이 return 타입에 객체가 있으면 자동으로 직렬화
//        ObjectMapper objectMapper = new ObjectMapper();
//        String result = objectMapper.writeValueAsString(h1);
        return h1;
    }
//    ...hello/1 : pathvariable방식 | ...hello?id=1 : parameter방식
//    case 3. parameter방식을 통해 사용자로부터 값을 수신
//    parameter의 형식 : /member?id=1 or /member?name=hong  | ? 뒤에는 parameter
    @GetMapping("/param")
    @ResponseBody
    public Hello param(@RequestParam(value = "name")String inputName){
        System.out.println(inputName);
//        {name:사용자가 넣어온 이름, email:아무거나}
        Hello h1 = new Hello(inputName,"abc@naver.com");
        return h1;
    }
//    case 4. pathvariable방식을 통해 사용자로부터 값을 수신
//    pathvariable의 형식 : /member/1
//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현할 때 사용(좀더 resful함)
    @GetMapping("/path/{inputId}")
    @ResponseBody
    public String path(@PathVariable Long inputId){
//        별도의 형변환 없이도, 매개변수에 타입지정 시 자동형변환 시켜줌
//        long id = Long.parseLong(inputId);
        System.out.println(inputId);
        return "OK";
    }
//    case 5. parameter 2개 이상 형식
//    /hello/param2?name=hong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name")String inputName,
                         @RequestParam(value = "email")String inputEmail){
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "OK";
    }
}
