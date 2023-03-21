package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import recipes.DTO.RegisterDTO;
import recipes.Service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

  UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public void register(@Valid @RequestBody RegisterDTO registerDTO) {
    userService.register(registerDTO);
  }
}
