package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    List<UserDto> getAllUser();

    //get user by id
    UserDto getUserById(String userId);

    //get user by email
    UserDto getUserByEmail(String userId);

    //search user
    List<UserDto> searchUser(String keyword);


    UserDto isActive();


}
