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
    public String test(@RequestParam("name") String name){

        return heroMapper.selectByPrimaryKey(name).toString();
    }

    @PostMapping("/upload/hero")
    public String uploadCourseExcel(@RequestParam("file") MultipartFile file) {

        if(file.isEmpty()) {
            return "文件为空";
        }
        try {
            InputStream inputStream = file.getInputStream();
            List<List<Object>> list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());
            inputStream.close();

           for (int i = 0; i < list.size(); i++) {
                List<Object> courseList = list.get(i);
               for (int j = 0; j < courseList.size(); j++) {
                   String name =courseList.get(0).toString();
                   String name1 =courseList.get(1).toString();
                   if(!StringUtils.isEmpty(name)&&!StringUtils.isEmpty(name1)){
                       Hero hero =new Hero();
                       hero.setName(name);
                       hero.setWinRate(name1);
                       try {
                           heroMapper.insert(hero);
                       }catch (Exception e){

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

        if(file.isEmpty()) {
            return "文件为空";
        }
        Map<String,List<List<Object>> > lolList=new HashMap<>();
        List<Object> arrayListmap= new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            List<List<Object>> list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());
            List<List<Object>> arrayList=new ArrayList();
            inputStream.close();
            for (int i = 0; i < list.size(); i++) {

                List<Object> courseList = list.get(i);


                for (int j = 0; j < courseList.size(); j++) {
                   Object obj= courseList.get(0);

                   if(obj!=null){
                       if("英雄名称".equals(obj.toString())){

                           lolList.put(i+"",arrayList);
                           arrayList=new ArrayList<>();
                           break;
                       }
                   }

                       arrayList.add(courseList);


                }

            }
        } catch (Exception e) {
        }

        Iterator<Map.Entry<String, List<List<Object>>>> entries = lolList.entrySet().iterator();
        Set<String> keyset=lolList.keySet();

        while(entries.hasNext()){
            Map.Entry<String, List<List<Object>>> entry = entries.next();
            String key = entry.getKey();
            List<List<Object>> value = entry.getValue();
            //System.out.println("===="+key+"===="+value.toString());
            for (int i = 0; i < value.size(); i++) {
                List<Object> courseList = value.get(i);
                String couist = value.get(0).get(0).toString();
                Hero hero=heroMapper.selectByPrimaryKey(couist);
                for (int j = 0; j < courseList.size(); j++) {
                    Object name =courseList.get(1);
                    Object name1 =courseList.get(2);
                    if(!StringUtils.isEmpty(name)&&!StringUtils.isEmpty(name1)){
                        try {
                            Loding loding=new Loding();
                            loding.setLodingName(name.toString());
                            loding.setLodingRate(name1.toString());
                            loding.setHeroId(hero.getId());
                            lodingMapper.insert(loding);

                        }catch (Exception e){

                        }
                    }
                    //克制
                    Object name3 =courseList.get(3);
                    Object name4 =courseList.get(4);

                    if(!StringUtils.isEmpty(name3)&&!StringUtils.isEmpty(name4)){

                        try {
                            LolRest loding=new LolRest();
                            loding.setRestName(name3.toString());
                            loding.setRestRate(name4.toString());
                            loding.setHeroId(hero.getId());
                            lolRestMapper.insert(loding);

                        }catch (Exception e){

                        }

                    }

                }

            }
        }
        return  "success"+keyset.size();
    }
    @Autowired
    LolRestMapper lolRestMapper;
    @Autowired
    LodingMapper lodingMapper;
}
