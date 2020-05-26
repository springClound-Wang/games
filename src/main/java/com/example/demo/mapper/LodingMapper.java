package com.example.demo.mapper;

import com.example.demo.model.Hero;
import com.example.demo.model.Loding;
import org.springframework.stereotype.Repository;

@Repository
public interface LodingMapper {

    int insert(Loding record);


    Loding selectByPrimaryKey(Integer id);


}