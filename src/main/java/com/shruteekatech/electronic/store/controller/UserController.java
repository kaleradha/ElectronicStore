package com.shruteekatech.electronic.store.controller;

import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.helper.*;

import com.shruteekatech.electronic.store.service.FileService;
import com.shruteekatech.electronic.store.service.UserServiceI;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * This class is for generates API's request for User details.
 *
 * @author {Radha_patil}
 * @since 15-3-2023
 * <p>
 * <p>
 * ///=========//////=========/////===========/////==========/////====
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private FileService fileservice;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * This api is for completing the req of save the user
     *
     * @param userDto as parameter
     * @return the saved user will never (@literal null)
     * @apiNote This api is for creating the new User
     */

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Initiated request for save the user details");
        UserDto user = this.userServiceI.createUser(userDto);
        log.info("Completed request for save the user details");
        return new ResponseEntity<>(user, HttpStatus.CREATED);


    }

    /**
     * this api is for completing the request of update the user.
     *
     * @param userdto with @{@link RequestBody}.
     * @param userId  with @{@link PathVariable}.
     * @return the updated user.
     */

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userdto, @PathVariable long userId) {
        log.info("Initiated request for update the user details by userId : " + userId);
        UserDto userDto = this.userServiceI.updateUser(userdto, userId);
        log.info("Completed request for update the user details by userId: " + userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * This api is for getting the single user details.
     *
     * @param userId with @{@link PathVariable}
     * @return retrives the single user with given id.
     */
    @GetMapping("/getuser/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        log.info("Initiated request for get the single user details by userId: " + userId);
        UserDto userDto = this.userServiceI.getSingleUser(userId);
        log.info("Completed request for get the single user details by userId: " + userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * This api is for getting the user by its Email.
     *
     * @param email with @{@link PathVariable} not be {@literal null}
     * @return retrives the single user with given email.
     */
    @GetMapping("/byemail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Initiated request for get the user details by User Email: " + email);
        UserDto userByEmail = this.userServiceI.getUserByEmail(email);
        log.info("Completed request for get the user details by User Email: " + email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * This api is for accessing all the Users details.
     *
     * @return all the users presents in entities.
     */

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageSize", defaultValue = PaginationConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = PaginationConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = PaginationConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_DIR, required = false) String sortDir) {

        log.info("Initiated request for get all the user details: ");
        PageableResponse<UserDto> allUsers = this.userServiceI.getAllUsers(pageSize, pageNumber, sortBy, sortDir);
        log.info("Completed request for get all the user details: ");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * This api is for delete the user by its userId.
     *
     * @param userId with with @{@link PathVariable}.
     * @return apiResponseMessage with some meaningfull information.
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable long userId) {
        log.info("Initiated request for Update the user details: " + userId);

            this.userServiceI.deleteUser(userId);
            ApiResponseMessage apiResponseMessage = ApiResponseMessage
                    .builder().message(AppConstant.USER_DELETE)
                    .success(true).currentDate(new Date())
                    .status(HttpStatus.OK).build();
            log.info("Completed request for Update the user details: " + userId);

            return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);

    }

    /**
     * This api is for getting all the users having the given @keyword
     *
     * @param keywords not be null {@literal  null}
     * @return return all the users having {@link PathVariable @keywords}
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keywords) {
        log.info("Initiated request for search the user by keyword: " + keywords);

            List<UserDto> userDtos = this.userServiceI.searchUser(keywords);

            if (userDtos.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
            }
            log.info("Completed request for search the user by keyword: " + keywords);
            return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>uploadUserImage(@RequestParam("userImage") MultipartFile image,@PathVariable long userId) throws IOException {
     log.info("Intiated request for upload the user: " +userId);
        String imagename = fileservice.uplaodImage(image,imageUploadPath);

        UserDto user = this.userServiceI.getSingleUser(userId);
        user.setImageName(imagename);
        UserDto userDto = userServiceI.updateUser(user, userId);
        //to update the image in database
        ImageResponse imageResponse =ImageResponse.builder()
                .imageName(imagename).success(true).status(HttpStatus.CREATED)
                .imgUploaddate(new Date()).message(AppConstant.UPLOAD_IMAGE).build();
        log.info("Completed request for uploading the user image: " +userId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);//201
    }
    //here we have to get the userImage by userId
    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable long userId, HttpServletResponse response) throws IOException {
        log.info("Intiated request for get the Image of user by userId : " +userId);
        UserDto user = userServiceI.getSingleUser(userId);
        log.info("User image name: {} ",user.getImageName());
        InputStream resource = fileservice.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed request for get the Image of user by userId : " +userId);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
