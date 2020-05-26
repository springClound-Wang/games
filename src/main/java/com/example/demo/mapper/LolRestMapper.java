package com.example.demo.mapper;

import com.example.demo.model.Hero;
import com.example.demo.model.LolRest;
import org.springframework.stereotype.Repository;

@Repository
public interface LolRestMapper {

    int insert(LolRest record);


    LolRest selectByPrimaryKey(Integer id);


}