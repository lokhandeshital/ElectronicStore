package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.ApiResponse;
import com.bikkadit.electronic.store.dtos.ImageResponse;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.UserDto;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param userDto
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is used to Create User Details
     */
    //create
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Initiated Request for Create User Details");
        UserDto createUser = this.userService.createUser(userDto);
        log.info("Completed Request for Create User Details");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }


    /**
     * @param userDto
     * @param userId
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is used to Update User Details
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
     * @param userId
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is Used to Delete User Details
     */
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Initiated Request for Delete User with userId : {}", userId);
        this.userService.deleteUser(userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.USER_DELETE).success(true).build();
        log.info("Completed Request for Delete User with userId : {}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    /**
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is Used to Get All Users Details
     */
    //getAllUser
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "userName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Initiated Request for Get All Users Details");
        PageableResponse<UserDto> allUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get All Users Details");
        return new ResponseEntity<>(allUser, HttpStatus.OK);

    }

    /**
     * @param userId
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is used to Get User By UserId
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
     * @param email
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is Used to Get User By Email
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
     * @param keyword
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is used to Search Users
     */
    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Initiated Request for Search User with keyword : {}", keyword);
        List<UserDto> searchUser = this.userService.searchUser(keyword);
        log.info("Completed Request for Search User with keyword : {}", keyword);
        return new ResponseEntity<>(searchUser, HttpStatus.OK);

    }

    /**
     * @param image
     * @param userId
     * @return
     * @throws IOException
     */
    //Upload User Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId
    ) throws IOException {

        log.info("Initiated Request for Upload Image with userId : {}" + userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).build();
        log.info("Completed Request for Upload Image with userId : {}" + userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    /**
     * @param userId
     * @param response
     * @throws IOException
     */
    //Serve User Image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
        log.info(" User image name : {} ", user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }


}
