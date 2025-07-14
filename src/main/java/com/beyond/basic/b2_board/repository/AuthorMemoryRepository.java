package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Author;
import com.beyond.basic.b2_board.dto.AuthorListDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AuthorMemoryRepository{
    private List<Author> authorList = new ArrayList<>();

    public static Long id = 1L;

//    회원가입 메서드
    public void save(Author author){
        this.authorList.add(author);
        id++;
    }
//    회원목록조회 메서드
    public List<Author> findAll(){
        return this.authorList;
    }
//    id로 회원 찾기
    public Optional<Author> findById(Long id){
        Author author =null;
        for (Author a : this.authorList){
            if (a.getId().equals(id)){
                author = a;
            }
        }
        return Optional.ofNullable(author);
//        return authorList.stream().filter(author -> author.getId().equals(id)).findFirst();
    }
//    email로 회원 찾기
    public Optional<Author> findByEmail(String email){
        Author author =null;
        for (Author a : this.authorList){
            if (a.getEmail().equals(email)){
                author = a;
            }
        }
        return Optional.ofNullable(author);
//        return authorList.stream().filter(author -> author.getEmail().equals(email)).findFirst();
    }
//    회원탈퇴
    public void delete(Long id){
//        id값으로 요소의 index 값을 찾아 삭제(id));
        for (int i = 0; i < this.authorList.size(); i++) {
            if (this.authorList.get(i).getId().equals(id)){
                this.authorList.remove(i);
                break;
            }
        }
    }

}
