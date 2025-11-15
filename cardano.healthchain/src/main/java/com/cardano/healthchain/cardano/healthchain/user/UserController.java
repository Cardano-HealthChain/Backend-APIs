package com.cardano.healthchain.cardano.healthchain.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("{did}/record-hash")
    public UserDTOResponse getLatestRecordHash(@PathVariable("did") String did){
        return null;
    }
}
