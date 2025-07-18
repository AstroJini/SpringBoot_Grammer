package com.beyond.basic.b2_board.dto;

import com.beyond.basic.b2_board.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String password;
//    1개의 entity로만 dto가 조립되는 것이 아니기에, dto계층에서 fromEntity를 설계

//    한개의 엔티티만을 가지고 설계하는게 아니라면 dto계층에서 만드는게 맞다.
    public static AuthorDetailDto fromEntity(Author author){
        return new AuthorDetailDto(author.getId(),author.getName(),author.getEmail());
    }
}
