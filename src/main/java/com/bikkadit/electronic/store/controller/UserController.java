package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author Shital Lokhande
     * @apiNote This Api is used to Create User Details
     * @param userDto
     * @return
     */
    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Initiated Request for Create User Details");
        UserDto createUser = this.userService.createUser(userDto);
        log.info("Completed Request for Create User Details");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }


    /**
     * @author Shital Lokhande
     * @apiNote This Api is used to Update User Details
     * @param userDto
     * @param userId
     * @return
     */
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Initiated Request for Update User with userId : {}", userId);
        UserDto userUpdate = this.userService.updateUser(userDto, userId);
        log.info("Completed Request for Update User with userId : {}", userId);
        return new ResponseEntity<>(userUpdate, HttpStatus.OK);
    }

    /**
     * @author Shital Lokhande
     * @apiNote This Api is Used to Delete User Details
     * @param userId
     * @return
     */
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        log.info("Initiated Request for Delete User with userId : {}", userId);
        this.userService.deleteUser(userId);
        log.info("Completed Request for Delete User with userId : {}", userId);
        return new ResponseEntity<>(AppConstant.USER_DELETE, HttpStatus.OK);

    }

    /**
     * @author Shital Lokhande
     * @apiNote This Api is Used to Get All Users Details
     * @return
     */
    //getAllUser
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        log.info("Initiated Request for Get All Users Details");
        PageableResponse<UserDto> allUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get All Users Details");
        return new ResponseEntity<>(allUser, HttpStatus.OK);

    }

    /**
     * @author Shital Lokhande
     * @apiNote This Api is used to Get User By UserId
     * @param userId
     * @return
     */
    //getUserById
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Initiated Request for Get User By Id with userId : {}", userId);
        UserDto userById = this.userService.getUserById(userId);
        log.info("Completed Request for Get User By Id with userId : {}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);

    }

    /**
     * @author Shital Lokhande
     * @apiNote This Api is Used to Get User By Email
     * @param email
     * @return
     */
    //getUserByEmail
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Initiated Request for Get User By Email with email : {}", email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        log.info("Completed Request for Get User By Email with email : {}", email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @author Shital Lokhande
     * @apiNote This Api is used to Search Users
     * @param keyword
     * @return
     */
    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Initiated Request for Search User with keyword : {}", keyword);
        List<UserDto> searchUser = this.userService.searchUser(keyword);
        log.info("Completed Request for Search User with keyword : {}", keyword);
        return new ResponseEntity<>(searchUser, HttpStatus.OK);

    }
}
