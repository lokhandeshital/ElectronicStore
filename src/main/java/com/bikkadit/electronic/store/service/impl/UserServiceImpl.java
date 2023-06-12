package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;


    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to create new User
     * @param userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {

        //Generating unique ID in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        log.info("Initiated Request for save the User Details");
        User newUser = this.mapper.map(userDto, User.class);
        User saveUser = this.userRepository.save(newUser);
        log.info("Completed Request for save the User Details");
        UserDto    createUser = this.mapper.map(saveUser, UserDto.class);

        return createUser;
    }

    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to update User Details
     * @param userDto
     * @param userId
     * @return
     */
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        log.info("Initiated Request for Update User Details with userId : {}", userId);
        User newUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND + userId));

        newUser.setUserName(userDto.getUserName());
        newUser.setPassword(userDto.getPassword());
        newUser.setGender(userDto.getGender());
        newUser.setImageName(userDto.getImageName());
        newUser.setAbout(userDto.getAbout());

        this.userRepository.save(newUser);
        log.info("Completed Request for Update User Details with userId : {}", userId);
        UserDto updatedUser = this.mapper.map(newUser, UserDto.class);

        return updatedUser;
    }

    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to delete User Details
     * @param userId
     */
    @Override
    public void deleteUser(String userId) {

        log.info("Initiated Request for Delete User Details with userId : {}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_DELETE + userId));

        log.info("Completed Request for Delete User Details with userId : {}", userId);
        this.userRepository.delete(user);


    }

    /**
     * @author Shital LOkhande
     * @implNote This Impl is used to get all User Details
     * @return
     */
    @Override
    public List<UserDto> getAllUser(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        log.info("Initiated Request for Get All Users Details");

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        //PageNumber Default Start From 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepository.findAll(pageable);

        List<User> findAll = page.getContent();

        List<UserDto> allList = findAll.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());

        log.info("Completed Request for Get All Users Details");
        return allList;
    }

    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to get User By UserId
     * @param userId
     * @return
     */
    @Override
    public UserDto getUserById(String userId) {

        log.info("Initiated Request for Get User By Id with userId : {}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND + userId));

        log.info("Completed Request for Get User By Id with userId : {}", userId);
        return mapper.map(user, UserDto.class);
    }

    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to get User By Email
     * @param email
     * @return
     */
    @Override
    public UserDto getUserByEmail(String email) {

        log.info("Initiated Request for Get User By Email with email : {}", email);
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER_NOT_FOUND_WITH_EMAIL + email));

        log.info("Completed Request for Get User By Email with email : {}", email);
        return mapper.map(user, UserDto.class);
    }

    /**
     * @author Shital Lokhande
     * @implNote This Impl is Used to Search User
     * @param keyword
     * @return
     */
    @Override
    public List<UserDto> searchUser(String keyword) {

        log.info("Initiated Request for Search User with keyword : {}", keyword);
        List<User> userList = this.userRepository.findByUserNameContaining(keyword);

        List<UserDto> list = userList.stream().map(user -> this.mapper.map(user, UserDto.class)).collect(Collectors.toList());

        log.info("Completed Request for Search User with keyword : {}", keyword);
        return list;
    }

    @Override
    public UserDto isActive() {
        return null;
    }
}
