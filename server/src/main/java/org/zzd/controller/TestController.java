package org.zzd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :zzd
 * @date : 2023-02-27 21:14
 */
@RestController
@RequestMapping("/api/user")
public class TestController {
    @GetMapping("/login")
    public String hello() {
        return "hello";
    }
}
