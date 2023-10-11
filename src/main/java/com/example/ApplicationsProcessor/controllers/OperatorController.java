package com.example.ApplicationsProcessor.controllers;

import com.example.ApplicationsProcessor.dto.ApplicationDTO;
import com.example.ApplicationsProcessor.dto.UserForViewDTO;
import com.example.ApplicationsProcessor.models.Application;
import com.example.ApplicationsProcessor.models.Role;
import com.example.ApplicationsProcessor.models.RoleEnum;
import com.example.ApplicationsProcessor.models.Status;
import com.example.ApplicationsProcessor.models.User;
import com.example.ApplicationsProcessor.services.ApplicationService;
import com.example.ApplicationsProcessor.services.RoleService;
import com.example.ApplicationsProcessor.services.UserService;
import com.example.ApplicationsProcessor.util.ErrorResponse;
import com.example.ApplicationsProcessor.util.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

//  @GetMapping()
//  public void create(){
//    User operator = new User();
//    operator.setName("Иван");
//    operator.setSurname("Иванов");
//    Role role = roleService.findByRoleEnum(RoleEnum.ROLE_OPERATOR);
//    operator.addRole(role);
//    operator.setEmail("ivan@mail.ru");
//    operator.setPassword("1357");
//    userService.save(operator);
//    User operator2 = new User();
//    operator2.setName("Анна");
//    operator2.setSurname("Никитина");
//    Role role2 = roleService.findByRoleEnum(RoleEnum.ROLE_OPERATOR);
//    operator2.addRole(role2);
//    operator2.setEmail("ann@mail.ru");
//    operator2.setPassword("0246");
//    userService.save(operator2);
//  }

  /**
   * Принятие заявки = изменение статуса заявки
   */
  @PatchMapping("/applications/{applicationId}/accept")
  public ResponseEntity<HttpStatus> accept(
      @PathVariable("applicationId") int applicationId) {
    applicationService.accept(applicationId);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Отклонение заявки = изменение статуса заявки
   */
  @PatchMapping("/applications/{applicationId}/reject")
  public ResponseEntity<HttpStatus> reject(
      @PathVariable("applicationId") int applicationId) {
    applicationService.reject(applicationId);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  // ПРОБЛЕМА N + 1

  /**
   * Просмотр отправленных заявок
   */
  @GetMapping("/applications")
  public ResponseEntity<List<ApplicationDTO>> show(
      @RequestParam(value = "page", required = true) String page,
      @RequestParam(value = "sort", required = false) String sort,
      @RequestParam(value = "name", required = false) String userName) {
    List<Application> applicationList = null;

    if (userName != null) {
      applicationList = applicationService
          .showApplicationByUserName(userName, Integer.parseInt(page), sort);
    } else {
      applicationList = applicationService
          .showApplicationByStatus(Status.SUBMITTED, Integer.parseInt(page), sort);
    }

    ArrayList<ApplicationDTO> applicationDTOList = new ArrayList<>();
    for (Application application : applicationList) {
      ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
      applicationDTO
          .setUserForViewDTO(modelMapper.map(application.getUser(), UserForViewDTO.class));
      applicationDTO.applicationTextConversion();
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
