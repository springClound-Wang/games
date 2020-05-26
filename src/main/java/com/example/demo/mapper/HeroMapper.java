package com.example.demo.mapper;

import com.example.demo.model.Hero;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroMapper {

    int insert(Hero record);


    Hero selectByPrimaryKey(String  name);


}