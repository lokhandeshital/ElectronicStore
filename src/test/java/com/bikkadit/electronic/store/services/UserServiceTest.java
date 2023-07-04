package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    public void init(){

        user = User.builder().userName("Shital")
                             .email("shitallokhande596@gmail.com")
                             .about("This is Testing create method")
                             .gender("male")
                             .imageName("abc.png")
                             .password("shital")
                             .build();
    }

    //create user
    @Test
    public void createUserTest(){

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getUserName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Shital",user1.getUserName());

    }








}
