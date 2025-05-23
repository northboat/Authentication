package cia.arkrypto.auth.controller;

import cia.arkrypto.auth.dto.Friend;
import cia.arkrypto.auth.dto.Image;
import cia.arkrypto.auth.service.FriendService;
import cia.arkrypto.auth.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BasicController {


    private final ImageService imageService;

    private final FriendService friendService;

    @Autowired
    public BasicController(ImageService imageService, FriendService friendService){
        this.imageService = imageService;
        this.friendService = friendService;
    }

    @GetMapping({"/home", "/"})
    public String home(Model model) {
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

