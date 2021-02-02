package com.gakki.smile.mapper;

import com.gakki.smile.entity.Book;



import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {

    @Select("SELECT * FROM book WHERE id = #{bookId}")
    Book getUser(@Param("bookId") Integer bookId);

}
