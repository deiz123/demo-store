package com.pnrpu.store.controller;

import com.pnrpu.store.persistence.entity.Phone;
import com.pnrpu.store.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public String main(
            @RequestParam(name = "sort", required = false, defaultValue = "") final String order,
            final Model model
    ) {
        final List<Phone> phones = phoneService.findWithOrder(order);

        model.addAttribute("phones", phones);

        return "catalog";
    }

    @ResponseBody
    @GetMapping("/list")
    public List<Phone> list(
            @RequestParam(name = "sort", required = false, defaultValue = "") final String order
    ) {
        return phoneService.findWithOrder(order);
    }
}
