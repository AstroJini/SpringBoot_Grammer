package com.beyond.basic.b2_board.service;

import com.beyond.basic.b2_board.domain.Author;
import com.beyond.basic.b2_board.dto.AuthorCreateDto;
import com.beyond.basic.b2_board.dto.AuthorDetailDto;
import com.beyond.basic.b2_board.dto.AuthorListDto;
import com.beyond.basic.b2_board.dto.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.repository.AuthorMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//Transaction하고 롤백에 대한 추가설명 필요.
// 싱글톤 객체를 만드는 것 외에는 다른 기능을 하지 않음
@Service //@Component로도 대체 가능함(만약 트랜잭션 처리가 없는 경우에)
@RequiredArgsConstructor
public class AuthorService {

////    의존성 주입(DI)방법 1. Autowired 어노테이션 사용 -> 필드주입 | 취약점: 변수의 안전성이 깨짐 언제든 원하면 객체를 재조립할 수 있기 때문임.
////    간편한 목적으로 사용 하기는 하나 standard하지는 않다.
//    @Autowired
//    private AuthorMemoryRepository authorMemoryInterface;

////    의존성 주입(DI)방법 2. 생성자 주입 방식(가장 많이 쓰는 방식)
////    장점1) final을 통해 상수로 사용 가능(안정성 향상)
////    장점2) 다형성 구현가능
////    장점3) 순환 참조 방지 (컴파일타임에 체크한다|순환참조를 방지할 수는 없다. 하지만, 컴파일타임에 찾아서 실행을 막아둘 수 있다.)
//
//    private final AuthorMemoryRepository authorMemoryRepository;
////    AuthorService가 객체로 만들어지는 시점에 스프링에서 authorRepository 객체를 매개변수로 주입해 준다.
//    생성자가 하나밖에 없을 때에는 Autowired 생략 가능
//    @Autowired
//    public AuthorService(AuthorMemoryRepository authorMemoryRepository) {
//        this.authorMemoryRepository = authorMemoryRepository;
//    }

//    의존성 주입(DI)방법 3. RequiredArgs 어노테이션 사용 -> 반드시 초기화 되어야 하는 필드(final 등)을 대상으로 생성자를 자동생성
//    다형성 설계는 불가
    private final AuthorMemoryRepository authorMemoryRepository;

    //    객체조립은 서비스 담당
    public void save(AuthorCreateDto authorCreateDto){
//    이메일 중복 검증
        if (authorMemoryRepository.findByEmail(authorCreateDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword());
        this.authorMemoryRepository.save(author);
//    비밀번호 길이 검증
    }

    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = new ArrayList<>();
        for (Author a: authorMemoryRepository.findAll()){
            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(), a.getEmail());
            dtoList.add(dto);
        }
        return dtoList;
    }


//    아이디로 조회
    public AuthorDetailDto findById(Long id) throws NoSuchElementException{ //Optional객체에서 꺼내는 것도 서비스의 역학 예외 터뜨리기 =>
        Author author = authorMemoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("없는 id입니다."));
        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getEmail());
        return dto;
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto){
        Author author = authorMemoryRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(()->new NoSuchElementException());
        author.updatePw(authorUpdatePwDto.getEmail());
    }

    public void delete(Long id){
        authorMemoryRepository.findById(id).orElseThrow(()->new NoSuchElementException("존재하지 않는 사용자 입니다."));
        authorMemoryRepository.delete(id);
    }
}
