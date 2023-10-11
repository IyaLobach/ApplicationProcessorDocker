package com.example.ApplicationsProcessor.controllers;

import com.example.ApplicationsProcessor.dto.UserForViewDTO;
import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.security.UserDetail;
import com.example.ApplicationsProcessor.services.UserService;
import com.example.ApplicationsProcessor.util.ErrorResponse;
import com.example.ApplicationsProcessor.util.UserException;
import javax.swing.text.html.parser.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public  ResponseEntity<UserForViewDTO> login() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetail user = (UserDetail) authentication.getPrincipal();
    UserForViewDTO userForViewDTO = modelMapper.map(userService.findById(user.getUser().getId()), UserForViewDTO.class);
    return new ResponseEntity<>(userForViewDTO, HttpStatus.OK);
  }

  @GetMapping("/logout")
  public  ResponseEntity<HttpStatus> logout() {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handlerException(
      UserException userException) {
    ErrorResponse errorResponse = new ErrorResponse(
        userException.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}