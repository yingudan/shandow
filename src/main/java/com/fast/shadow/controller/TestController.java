package com.fast.shadow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.controller
 * @Version:1.0
 **/


@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Demo";
    }


}
