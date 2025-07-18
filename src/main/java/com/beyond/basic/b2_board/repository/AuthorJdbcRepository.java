package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJdbcRepository {

//    DataSource는 DB와 JDBC에서 사용하는 DB연결 드라이버 객체
//    application.yml에 설정한 DB정보를 사용하여 datasource객체 싱글톤생성
//    아래의 코드는 spring에서 datasource라는 객체를 만든다는 것을 의미한다
    @Autowired
    private DataSource dataSource;

    public void save(Author author){
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into author(name, email, password) values(?,?,?)";
//            PreparedStatement 객체로 만들어서 실행 가능한 상태로 만드는 것
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate(); //추가, 수정의 경우는 excuteUpdate, 조회는 excuteQuery
        } catch (SQLException e) {
//            unchecked  예외는 spring에서 트랜잭션 상황에서 롤백의 기준이 된다.
            throw new RuntimeException(e);
        }
    }
    public List<Author> findAll(){
        return null;
    }
    public Optional<Author> findById(Long id){
        return null;
    }
    public Optional<Author> findByEmail(String email){
        return null;
    }
    public void delete(Long id){

    }
}
