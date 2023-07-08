package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.model.User;
import com.bikkadit.electronic.store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private User user;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void createUserTest() throws Exception {

        UserDto userDto = mapper.map(user, UserDto.class);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);

        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").exists());

    }

    @Test
    public void updateUserTest() throws Exception {

        String userId = "abhgsf";
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(mapper.map(user, UserDto.class));
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(converObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists());


    }

    private String converObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
