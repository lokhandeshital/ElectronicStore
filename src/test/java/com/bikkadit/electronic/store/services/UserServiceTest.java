package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init() {

        user = User.builder().userName("Shital")
                .email("shitallokhande596@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("abc.png")
                .password("shital")
                .build();
    }

    //create user Test
    @Test
    public void createUserTest() {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getUserName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Shital", user1.getUserName());

    }

    //Update User Test
    @Test
    public void updateUserTest() {

        String userId = "sasasdgsfgf";
        UserDto userDto = UserDto.builder().userName("Kshitij Lokhande")
                .about("This is updated User about details")
                .gender("Male")
                .imageName("xyz.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        System.out.println(updatedUser.getUserName());
        Assertions.assertNotNull(userDto);

    }

    //Delete User Test
    @Test
    public void deleteUserTest() {
        String userId = "userIdabc";

        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);

    }

    // Get All User Test
    @Test
    public void getAllUserTest() {

        User user1 = User.builder().userName("Sagar")
                .email("sagar596@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("cda.png")
                .password("sagar")
                .build();

        User user2 = User.builder().userName("Shubham")
                .email("shubham596@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("pqr.png")
                .password("shubham")
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "userName", "asc");
        Assertions.assertEquals(3, allUser.getContent().size());

    }

    // Get User By Id Test
    @Test
    public void getUserByIdTest() {

        String userID = "userIdTest";
        Mockito.when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        //Actual Call of service Method
        UserDto userDto = userService.getUserById(userID);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getUserName(), userDto.getUserName(), "Name Not Match");

    }


}
