package com.example.ApplicationsProcessor.controllers;

import com.example.ApplicationsProcessor.dto.ApplicationDTO;
import com.example.ApplicationsProcessor.dto.UserDTO;
import com.example.ApplicationsProcessor.models.Application;
import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import com.example.ApplicationsProcessor.models.Status;
import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.services.ApplicationService;
import com.example.ApplicationsProcessor.services.RoleService;
import com.example.ApplicationsProcessor.services.UserService;
import com.example.ApplicationsProcessor.util.ErrorResponse;
import com.example.ApplicationsProcessor.util.UserException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Назначение пользователя правами оператора
   */
  @PatchMapping("/users/{userId}/appoint")
  public ResponseEntity<HttpStatus> appoint(@PathVariable("userId") int userId) {
    userService.updateRole(userId, roleService.findByRoleEnum(RoleEnum.ROLE_OPERATOR));
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Получение всех пользователей
   */
  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> show(
      @RequestParam(value = "name", required = false) String userName) {
    List<User> userList = null;
    if (userName == null) {
      userList = userService.findAll();
    } else {
      userList = userService.findByUserName(userName);
    }
    ArrayList<UserDTO> userDTOList = new ArrayList<>();
    for (User user : userList) {
      UserDTO userDTO = modelMapper.map(user, UserDTO.class);
      userDTO.setRoleEnum(user.getRole());
      userDTOList.add(userDTO);
    }
    return new ResponseEntity<>(userDTOList, HttpStatus.OK);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handlerException(
      UserException userException) {
    ErrorResponse errorResponse = new ErrorResponse(
        userException.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
