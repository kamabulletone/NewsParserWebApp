package com.webapp.newsparser.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(method = RequestMethod.GET,
            value = {"/about"},
            produces = "text/html")
    public String index() {
        return "index.html";
    }
}
