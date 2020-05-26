package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: xingyan.wzz
 * @create: 2020-05-26 10:06
 */

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "test";
    }
}
