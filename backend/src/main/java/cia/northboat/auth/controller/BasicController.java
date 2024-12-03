package cia.northboat.auth.controller;

import cia.northboat.auth.pojo.Image;
import cia.northboat.auth.pojo.User;
import cia.northboat.auth.service.ImageService;
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

    private final UserService userService;

    private final ImageService imageService;

    @Autowired
    public BasicController(UserService userService, ImageService imageService){
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping({"/home", "/"})
    public String home() {
        User user = userService.findById("northboat");
        System.out.println(user);
        return "index";  // 返回 templates/index.html 页面
    }

    @GetMapping("/login.html")
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


    @GetMapping("/toAuth")
    public String toAuth() {
        return "auth";  // 返回 templates/login.html 页面
    }


    @RequestMapping(value = "/toAuth/{algo}", method = RequestMethod.GET)
    public String toAuth(@PathVariable("algo") String algo, Model model) {
        System.out.println(algo);
        Image image = imageService.findById(algo);
        System.out.println(image.toString());
        model.addAttribute("algo", algo);
        model.addAttribute("image", image.getPath());
        return "auth";
    }
}

