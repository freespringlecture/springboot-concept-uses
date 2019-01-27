package me.freelife;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
