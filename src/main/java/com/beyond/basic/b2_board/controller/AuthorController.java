package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Author;
import com.beyond.basic.b2_board.dto.AuthorCreateDto;
import com.beyond.basic.b2_board.dto.AuthorDetailDto;
import com.beyond.basic.b2_board.dto.AuthorListDto;
import com.beyond.basic.b2_board.dto.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.NoSuchElementException;

@RestController // Controller + ResponseBody
@RequestMapping("/author")
@RequiredArgsConstructor //의존성 주입 방법 중 생성자 주입 방법임
public class AuthorController {
//    기본 CRUD
    private final AuthorService authorService; //반드시 final로 두어야 함!
    //    회원 가입
    @PostMapping("/create") //postmapping 에서 반드시 post를 해야하는 것은 아님 update를 할 수도 있음 그렇기에 반드시 뭘 해야한다라고 생각하지 말 것
    public ResponseEntity<?> save(@RequestBody AuthorCreateDto authorCreateDto){
//        만약 body부에 어떤 타입의 데이터가 들어올지 모르는 상황이라면 ? 또는 Object를 입력해주면 된다
        try {
            this.authorService.save(authorCreateDto);
            return new ResponseEntity<>("OK",HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return e.printStackTrace();
//            생성자 매개변수: body 부분의 객체와 header부에 상태코드를 넣어 준다.
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//    회원 목록 조회 : /author/list
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        return authorService.findAll();
    }
//    회원 상세 조회 : id로 조회 | /author/detail/1
//    서버에서 별도의 try catch 하지 않으면 에러 발생시 500에러 + 스프링의 포맷으로 에러 리턴

    @GetMapping("/detail/{inputId}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long inputId){
        try {
            return new ResponseEntity<>(authorService.findById(inputId), HttpStatus.OK);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
//    비밀번호 수정 : email, password -> json | /author/updatepw
//    get : 조회 | post : 등록 | patch : 부분수정 | put : 대체 | delete : 삭제
    @PatchMapping("/updatepw")
    public ResponseEntity<?> updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto){

        try {
            return new ResponseEntity<>(authorService.updatePassword(authorUpdatePwDto), HttpStatus.OK);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
//    회원 탈퇴(삭제) : /author/detail/1
    @DeleteMapping("/delete/{inputId}")
    public ResponseEntity<?> delete(@PathVariable Long inputId){
        try {
            return new ResponseEntity<>(authorService.delete(inputId), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
