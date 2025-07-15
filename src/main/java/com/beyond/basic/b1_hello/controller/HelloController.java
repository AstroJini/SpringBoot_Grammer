package com.beyond.basic.b1_hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
//    case 6. parameter가 많아질 경우 데이터 바인딩을 통해 input값 처리
//    데이터바인딩: param을 사용하여 객체로 생성해줌.
//    ?name=hong&email=hong@naver.com
    @GetMapping("/param3")
    @ResponseBody
//    public String param3(Hello hello){
//    @ModelAtribte를 써도 되고 안써도 되는데 이 키워드를 써서 명시적으로 param형식의 데이터를 받겠다 라는 것을 표현
    public String param3(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "OK";
    }

//    case 7. 서버애서 화면을 return, 사용자로부터 넘어오는 input값을 활용하여 동적인 화면 생성
//    서버에서 화면(+데이터)을 렌더링해주는  ssr방식(csr은 서버는 데이터만)
//    mvc(model, view, controller)패턴이라고도 함
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value="id")Long inputId, Model model){
//      model 객체는 데이터를 화면에 전달해주는 역할
//      name 이라는 키에 hongildong 이라는 value를 key:value 형태로 화면에 전달
        if(inputId == 1){
            model.addAttribute("name", "hong");
            model.addAttribute("email", "hong@naver.com");
        } else if (inputId == 2){
            model.addAttribute("name", "hong2");
            model.addAttribute("email", "hong2@naver.com");
        }
        return "helloworld2";
    }

//    post요청의 case 2가지 : url인코딩 방식 또는 multipart-formdata | json
//    case 1. text만 있는 fomr-data형식
//    형식 : body부에 데이터가 들어오는 형식이다. name=xxx&email=xxx
    @GetMapping("/form-view")
    public String formView(){
        return "form-view";
    }
    @PostMapping("/form-view")
    @ResponseBody
//    get요처에에 url에 파라미터 방식과 동일한 데이터 형식이므로, RequestParam 또는 데이터바인딩 방식 가능
    public String formViewPost(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "OK";
    }
//    case 2-1. text와 file이 있는 form-data형식 (순수html로 제출)
    @GetMapping("/form-file-view")
    public String formFileView(){
        return "form-file-view";
    }
    @PostMapping("/form-file-view")
    @ResponseBody
    public String formFileViewPost(@ModelAttribute Hello hello,
                                   @RequestParam(value = "photo")MultipartFile photo){/// 만약 두개를 합치고싶다면 Hello안에 MultipartFile photo 를 추가하면 된다.
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "OK";
    }
//    case 2-2. text와 file이 있는 form-data형식 (js로 제출)
    @GetMapping("/axios-file-view")
    public String axiosFileView(){
        return "axios-file-view";
    }

//    case 3. text와 multi-file이 있는 form-data형식(js로 제출)
    @GetMapping("/axios-multi-file-view")
    public String axiosMultiFileView(){
        return "axios-multi-file-view";
    }
    @PostMapping("/axios-multi-file-view")
    @ResponseBody
    public String axiosMultiFileViewPost(@ModelAttribute Hello hello,
                                         @RequestParam(value = "photos") List<MultipartFile> photos){
        System.out.println(hello);
        for (int i = 0; i < photos.size(); i++) {
            System.out.println(photos.get(i).getOriginalFilename());;
        }
        return "OK";
    }

//    case 4. json 데이터 처리
    @GetMapping("/axios-json-view")
    public String axiosJsonView(){
        return "axios-json-view";
    }
    @PostMapping("/axios-json-view")
    @ResponseBody
//    @RequestBody : json형식으로 데이터가 들어올 때 객체로 자동 파싱
    public String axiosJsonViewPost(@RequestBody Hello hello){
        System.out.println(hello);
        return "OK";
    }

//    case 5. 중첩된 json 데이터 처리
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }
    @PostMapping("/axios-nested-json-view")
    @ResponseBody
//    @RequestBody : json형식으로 데이터가 들어올 때 객체로 자동 파싱
    public String axiosNestedJsonViewPost(@RequestBody Student student){
        System.out.println(student);
        return "OK";
    }



//    case 6. json(text) + file 같이 처리할 때 : text 구조가 복잡하여 피치못하게 json을 써야하는 경우
//    데이터형식 : hello={namd:"xx", email:"xxx"}&photo=이미지.jpg
//    결론은 단순 json구조가 아닌,, multipart-formdata구조안에 json을 넣는 구조
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(){
        return "axios-json-file-view";
    }
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFileViewPost(
//            json과 file을 함께 처리해야할 때 RequestPart 일반적으로 활용
            @RequestPart("hello") Hello hello,
            @RequestPart("photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "OK";
    }
}

