package com.study.Main;

import com.study.account.CurrentUser;
import com.study.domain.Account;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if(account != null) {
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
