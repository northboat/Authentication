package cia.northboat.auth.controller;

import cia.northboat.auth.pojo.User;
import cia.northboat.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BasicController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        User user = userService.findById("northboat");
        System.out.println(user);
        return "index";  // 返回 templates/index.html 页面
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // 返回 templates/login.html 页面
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
        return "login";
    }

    @GetMapping("/elements")
    public String elements() {
        return "elements";  // 返回 templates/login.html 页面
    }


    @GetMapping("/auth")
    public String auth() {
        return "auth";  // 返回 templates/login.html 页面
    }


    @RequestMapping(value = "/auth/{algo}", method = RequestMethod.GET)
    public String getLogin(@PathVariable("algo") String algo, Model model) {
        System.out.println(algo);
        model.addAttribute("algo", algo);
        return "auth";
    }
}

