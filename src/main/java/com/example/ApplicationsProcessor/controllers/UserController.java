package com.example.ApplicationsProcessor.controllers;

import com.example.ApplicationsProcessor.dto.ApplicationDTO;
import com.example.ApplicationsProcessor.dto.UserForViewDTO;
import com.example.ApplicationsProcessor.models.Application;
import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.security.UserDetail;
import com.example.ApplicationsProcessor.services.ApplicationService;
import com.example.ApplicationsProcessor.services.RoleService;
import com.example.ApplicationsProcessor.services.UserService;
import com.example.ApplicationsProcessor.util.ErrorResponse;
import com.example.ApplicationsProcessor.util.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RoleService roleService;

//  @GetMapping()
//  public void create() {
//    User user = new User();
//    user.setName("Ия");
//    user.setSurname("Лобач");
//    user.setPassword("1234");
//    Role role = roleService.findByRoleEnum(RoleEnum.ROLE_USER);
//    user.addRole(role);
//    user.setEmail("iya@mail.ru");
//    userService.save(user);
//    User user2 = new User();
//    user2.setName("Петр");
//    user2.setSurname("Петров");
//    Role role2 = roleService.findByRoleEnum(RoleEnum.ROLE_USER);
//    user2.addRole(role2);
//    user2.setEmail("petr@mail.ru");
//    user2.setPassword("4567");
//    userService.save(user2);
//    User user3 = new User();
//    user3.setName("Анита");
//    user3.setSurname("Климова");
//    user3.setPassword("8910");
//    Role role3 = roleService.findByRoleEnum(RoleEnum.ROLE_USER);
//    user3.addRole(role3);
//    user3.setEmail("anita@mail.ru");
//    userService.save(user3);
//  }


  /**
   * Создание новой заявки
   */
  @PostMapping("/applications")
  public ResponseEntity<HttpStatus> create(
      @RequestBody @Valid ApplicationDTO applicationDTO, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      List<FieldError> errorList = bindingResult.getFieldErrors();
      StringBuilder message = new StringBuilder();
      for (FieldError error : errorList) {
        message.append(error.getDefaultMessage());
      }
      throw new ApplicationException(message.toString());
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetail user = (UserDetail) authentication.getPrincipal();
    applicationService
        .create(modelMapper.map(applicationDTO, Application.class),
            userService.findById(user.getUser().getId()));

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Отправка заявки = обновление статуса заявки
   */
  @PatchMapping("/applications/{applicationId}/submit")
  public ResponseEntity<HttpStatus> submit(@PathVariable("applicationId") int applicationId) {
    applicationService.submit(applicationId);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Редактирование заявки = обновление текста заявки
   */
  @PatchMapping("/applications/{applicationId}/edit")
  public ResponseEntity<HttpStatus> update(
      @PathVariable("applicationId") int applicationId,
      @RequestBody @Valid ApplicationDTO applicationDTO, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      List<FieldError> errorList = bindingResult.getFieldErrors();
      StringBuilder message = new StringBuilder();
      for (FieldError error : errorList) {
        message.append(error.getDefaultMessage());
      }
      throw new ApplicationException(message.toString());
    }
    applicationService
        .updateText(applicationId, modelMapper.map(applicationDTO, Application.class).getText());

    return ResponseEntity.ok(HttpStatus.OK);
  }

  // проблема N + 1

  /**
   * Просмотр заявок
   */
  @GetMapping("/applications")
  public ResponseEntity<List<ApplicationDTO>> show(
      @RequestParam(value = "page", required = true) String page,
      @RequestParam(value = "sort", required = false) String sort) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetail user = (UserDetail) authentication.getPrincipal();
    List<Application> applicationList = applicationService
        .showApplicationByUserId(user.getUser().getId(), Integer.parseInt(page), sort);
    ArrayList<ApplicationDTO> applicationDTOList = new ArrayList<>();
    for (Application application : applicationList) {
      ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
      applicationDTO
          .setUserForViewDTO(modelMapper.map(application.getUser(), UserForViewDTO.class));
      applicationDTOList.add(applicationDTO);
    }
    return new ResponseEntity<>(applicationDTOList, HttpStatus.OK);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handlerException(
      ApplicationException applicationException) {
    ErrorResponse applicationErrorResponse = new ErrorResponse(
        applicationException.getMessage());

    return new ResponseEntity<>(applicationErrorResponse, HttpStatus.BAD_REQUEST);
  }
}
