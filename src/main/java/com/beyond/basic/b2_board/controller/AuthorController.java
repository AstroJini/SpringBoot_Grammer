package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Author;
import com.beyond.basic.b2_board.dto.AuthorCreateDto;
import com.beyond.basic.b2_board.dto.AuthorDetailDto;
import com.beyond.basic.b2_board.dto.AuthorListDto;
import com.beyond.basic.b2_board.dto.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController // Controller + ResponseBody
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
//    기본 CRUD
    private final AuthorService authorService;

    //    회원 가입
    @PostMapping("/create")
    public String save(@RequestBody AuthorCreateDto authorCreateDto){
        try {
            this.authorService.save(authorCreateDto);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
        return "OK";
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
    public AuthorDetailDto findById(@PathVariable Long inputId){
        try {
            return authorService.findById(inputId);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }
//    비밀번호 수정 : email, password -> json | /author/updatepw
//    get : 조회 | post : 등록 | patch : 부분수정 | put : 대체 | delete : 삭제
    @PatchMapping("/updatepw")
    public String updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto){
        authorService.updatePassword(authorUpdatePwDto);
        return "OK";
    }
//    회원 탈퇴(삭제) : /author/detail/1
    @DeleteMapping("/delete/{inputId}")
    public void delete(@PathVariable Long inputId){
        authorService.delete(inputId);
    }
}
