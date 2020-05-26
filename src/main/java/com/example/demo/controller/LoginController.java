package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.HeroMapper;
import com.example.demo.mapper.LodingMapper;
import com.example.demo.mapper.LolRestMapper;
import com.example.demo.model.Hero;
import com.example.demo.model.Loding;
import com.example.demo.model.LolRest;
import com.example.demo.service.ExcelUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * Created by qfcomputer on 2018/5/27.
 */
@Api(tags = "客户接口")
@RestController
public class LoginController {
    @Autowired
    HeroMapper heroMapper;

    @GetMapping("/test")
    public String test(@RequestParam("name") String name) {

        return heroMapper.selectByPrimaryKey(name).toString();
    }

    @PostMapping("/test1")
    public List<Hero> test1(@RequestParam("name") String name) {

        return heroMapper.selectByList(name);
    }

    @PostMapping("/test2")
    public List<Hero> test2(@RequestParam("name") String name) {

        return heroMapper.selectByRest(name);
    }

    @PostMapping("/upload/hero")
    public String uploadCourseExcel(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空";
        }
        try {
            InputStream inputStream = file.getInputStream();
            List<List<Object>> list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());
            inputStream.close();

            for (int i = 0; i < list.size(); i++) {
                List<Object> courseList = list.get(i);
                for (int j = 0; j < courseList.size(); j++) {
                    String name = courseList.get(0).toString();
                    String name1 = courseList.get(1).toString();
                    String name2 = courseList.get(2).toString();
                    if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(name1)) {
                        Hero hero = new Hero();
                        hero.setName(name);
                        hero.setWinRate(name1);
                        hero.setLevel(name2);
                        try {
                            heroMapper.insert(hero);
                        } catch (Exception e) {

                        }

                    }

                }

            }
        } catch (Exception e) {
        }

        return "success";
    }

    @PostMapping("/upload/loding")
    public String loding(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空";
        }
        Map<String, List<List<Object>>> lolList = new HashMap<>();
        List<List<Object>> list =null;
        try {
            InputStream inputStream = file.getInputStream();
            list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
            for (int i = 0; i < list.size(); i++) {

                List<Object> courseList = list.get(i);
                for (int j = 0; j < courseList.size(); j++) {
                    String name0 = courseList.get(0).toString();
                    if ("英雄名称".equals(name0)) {
                       break;
                    }

                    Object name = courseList.get(1);

                    Object name1 = courseList.get(2);

                    Hero hero = heroMapper.selectByPrimaryKey(name0);

                    if (name != null) {


                        Loding loding = new Loding();
                        loding.setLodingName(name.toString());
                        loding.setLodingRate(name1.toString());
                        loding.setHeroId(hero.getId());
                        try {
                            lodingMapper.insert(loding);
                            System.out.println("出装插入成功的数据"+loding.toString());
                        }catch (Exception e){

                        }


                    }




                }

            }
            return list.toString();


    }
    @PostMapping("/upload/rest")
    public String rest(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空";
        }
        Map<String, List<List<Object>>> lolList = new HashMap<>();
        List<List<Object>> list =null;
        try {
            InputStream inputStream = file.getInputStream();
            list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        for (int i = 0; i < list.size(); i++) {

            List<Object> courseList = list.get(i);
            for (int j = 0; j < courseList.size(); j++) {
                String name0 = courseList.get(0).toString();
                if ("英雄名称".equals(name0)) {
                    break;
                }

                //克制
                Object name3 = courseList.get(3);
                Object name4 = courseList.get(4);
                Hero hero = heroMapper.selectByPrimaryKey(name0);

                if (name3 != null) {

                    LolRest loding = new LolRest();
                    loding.setRestName(name3.toString());
                    loding.setRestRate(name4.toString());
                    loding.setHeroId(hero.getId());
                    try {
                        lolRestMapper.insert(loding);
                        System.out.println("克制插入成功的数据"+loding.toString());
                    }catch (Exception e){

                    }


                }


            }

        }
        return list.toString();


    }
    @Autowired
    LolRestMapper lolRestMapper;
    @Autowired
    LodingMapper lodingMapper;
}
