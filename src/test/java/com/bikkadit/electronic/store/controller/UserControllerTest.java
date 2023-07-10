package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.helper.AppConstant;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    // Create User Test
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

    //Update User Test
    @Test
    public void updateUserTest() throws Exception {

        String userId = "abhgsf";
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(mapper.map(user, UserDto.class));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists());


    }

    //Delete User Test
    @Test
    public void deleteUserTest() throws Exception {

        String userId = "agjccbb";

        Mockito.doNothing().when(userService).deleteUser(userId);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(AppConstant.USER_DELETE + userId));


        Mockito.verify(userService).deleteUser(userId);

    }

    private String converObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get All User Test
    @Test
    public void getAllUserTest() throws Exception {

        UserDto userDto1 = UserDto.builder().userName("Sagar")
                .email("sagar123@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("xyz.png")
                .password("sagar")
                .build();

        UserDto userDto2 = UserDto.builder().userName("Shubham")
                .email("shubhamlokhande596@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("cda.png")
                .password("shubham")
                .build();

        UserDto userDto3 = UserDto.builder().userName("Ankit")
                .email("ankit789@gmail.com")
                .about("This is Testing create method")
                .gender("male")
                .imageName("cda.png")
                .password("ankit")
                .build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(userDto1, userDto2, userDto3));

        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //Get Single User Test
    @Test
    public void getSingleUserTest() throws Exception {

        String userId = "achcdk652";

        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.any())).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    // Get Single User By Email Test
    @Test
    public void getSingleUserByEmailTest() throws Exception {

        String email = "shital596@Gamil.com";

        UserDto userDto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.any())).thenReturn(userDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/" + email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
