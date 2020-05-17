package com.pnrpu.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spa")
public class SpaController {
    @GetMapping
    public String spa() {
        return "spa";
    }
}
