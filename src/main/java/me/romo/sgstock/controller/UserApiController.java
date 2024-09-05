package me.romo.sgstock.controller;

import lombok.RequiredArgsConstructor;
import me.romo.sgstock.dto.AddUserRequest;
import me.romo.sgstock.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request,
                         @RequestParam("confirmPassword") String confirmPassword,
                         Model model) {
        if (userService.existsByPublicId(request.getPublicId())) {
            model.addAttribute("errorMessage", "중복된 아이디가 존재합니다.");
            return "signup"; // 회원가입 페이지로 다시 리턴
        }

        if (!request.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "signup"; // 회원가입 페이지로 다시 리턴
        }

        userService.save(request);
        return "redirect:/login";
    }


}
