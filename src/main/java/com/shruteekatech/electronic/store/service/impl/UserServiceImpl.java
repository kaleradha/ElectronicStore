package com.shruteekatech.electronic.store.service.impl;

import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.User;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.AppConstant;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.UserRepository;
import com.shruteekatech.electronic.store.service.UserServiceI;
import com.shruteekatech.electronic.store.utility.CustomPageHelper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
;
import java.util.stream.Collectors;

/**
 * This class is for creates CRUD operation for saving the User Details.
 *
 * @author Radha_patil
 */
@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {
   // Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * Saves the user entity Return the object of the saved UserDto.
     *
     * @param userdto need not be (@literal null)
     * @return the saved User entity; will never be (@literal null)
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public UserDto createUser(UserDto userdto) {
        log.info("Initiating dao call for the Save the user data: ");
        User user = this.modelMapper.map(userdto, User.class);
        user.setIsactive(AppConstant.YES);
        User savedUser = userRepository.save(user);
        log.info("Completing dao call for Save the user data: ");
        return this.modelMapper.map(savedUser, UserDto.class);
    }

    /**
     * to update the existing record of user by using userId.
     *
     * @param userdto need not be (@literal null).
     * @param userId  of the user we want to update the record of user.
     * @return retrives the new updated User details/instance.
     */
    @Override
    public UserDto updateUser(UserDto userdto, long userId) {
        log.info("Initiating dao call for Update the user data by id: {} " + userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND));
        user.setIsactive(AppConstant.YES);
        user.setName(userdto.getName());
        user.setAbout(userdto.getAbout());
        user.setPassword(userdto.getPassword());
        user.setGender(userdto.getGender());
        user.setEmail(userdto.getEmail());
        user.setImageName(userdto.getImageName());
        User user1 = this.userRepository.save(user);
        log.info("Completing dao call for Update the user data by id: {} " + userId);
        return this.modelMapper.map(user1, UserDto.class);
    }

    /**
     * delete the user by its userId.
     *
     * @param userId must not be (@literal null).
     * @throws IllegalArgumentException in case the given Userid is {@literal null}.
     */

    @Override
    public void deleteUser(long userId) {
        log.info("Initiating dao call for delete the user data: {} " + userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND + userId));

        String fullpath=imageUploadPath+user.getImageName();
        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            log.info("User Image Not found in folder");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        user.setIsactive(AppConstant.NO);
        log.info("Completed dao request for delete user");
        userRepository.delete(user);
    }

    /**
     *  This Method is to get the single user details by its userId.
     *
     * @param userId must not be (@literal null).
     * @return the instance of single user.
     * @throws IllegalArgumentException in case the given Userid is {@literal null}.
     */

    @Override
    public UserDto getSingleUser(long userId) {
        log.info("Initiating dao call for get the single user data: {} " + userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND + userId));
        user.setIsactive(AppConstant.YES);
        log.info("Completing dao call for get the single user data: {} " + userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    /**
     * to get the single user details by its emailId.
     *
     * @param email must not be (@literal null).
     * @return the instance of single user.
     * @throws IllegalArgumentException in case the given Userid is {@literal null}.
     */
    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Initiating dao call for get the single user data by Email: {} " + email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND + email));
        log.info("Completing dao call for get the single user data by Email: {} " + email);
        return this.modelMapper.map(user, UserDto.class);
    }

    /**
     * This Method is for get all the Users in Sorting order.
     *
     * @param pageSize
     * @param pageNumber
     * @param sortBy
     * @param sortDir
     * @return list of user present in entity & according to sorting order.
     */
    @Override
    public PageableResponse<UserDto> getAllUsers(int pageSize, int pageNumber, String sortBy, String sortDir) {
        log.info("Initiating dao call for get the all users data: ");
        //  Sort sort = Sort.by(sortBy);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        // PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
        PageRequest request = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepository.findAll(request);
        PageableResponse<UserDto> response = CustomPageHelper.getPageResponse(page, UserDto.class);

//        List<User> users = page.getContent();
//
//        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//        PageableResponse<UserDto> response =new PageableResponse<>();
//        response.setContent(userDtos);
//        response.setPageNumber(page.getNumber());
//        response.setLastpage(page.isLast());
//        response.setPageSize(page.getSize());
//        response.setTotalElement(page.getTotalElements());
        log.info("Completing dao call for get the all users data: ");
        return response;


    }

    /**
     * to find the users by its keyword.
     *
     * @param keyword as parameter .
     * @return all the userdto where keyword is occurs.
     */
    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Initiating dao call for get the all users data by keyword: {} " + keyword);
        List<User> user = this.userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = user.stream().map(users -> this.modelMapper.map(users, UserDto.class)).collect(Collectors.toList());
        log.info("Completing dao call for get the all users data by keyword: {} " + keyword);
        return userDtos;
    }

    // private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
    //  return userDto;
}

//  private User dtoToEntity(UserDto userdto) {
//        User user = User.builder().userId(userdto.getUserId())
//                .name(userdto.getName())
//                .email(userdto.getEmail())
//                .password(userdto.getPassword())
//                .about(userdto.getAbout())
//                .gender(userdto.getGender())
//                .imageName(userdto.getImageName()).build();
//    return user;


