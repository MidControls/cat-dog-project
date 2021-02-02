package com.gakki.smile.service;

import com.gakki.smile.entity.Book;
import com.gakki.smile.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Book findBookById() {
        int a=10/0;
        return userMapper.getUser(1);
    }




}
