package com.example.demo.mapper;

import com.example.demo.model.Hero;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroMapper {

    int insert(Hero record);


    Hero selectByPrimaryKey(String  name);

    List<Hero> selectByList(String  name);

    List<Hero> selectByRest(String  name);
}