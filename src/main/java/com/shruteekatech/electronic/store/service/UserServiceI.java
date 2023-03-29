package com.shruteekatech.electronic.store.service;

import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.User;
import com.shruteekatech.electronic.store.helper.PageableResponse;

import java.util.List;

public interface UserServiceI {

    UserDto createUser(UserDto userdto);

    UserDto updateUser(UserDto userdto, long userId);

    void deleteUser(long userId);

    UserDto getSingleUser(long userId);

    UserDto getUserByEmail(String email);

    PageableResponse<UserDto> getAllUsers(int pageSize, int pageNumber, String sortDir, String sortBy);

    List<UserDto> searchUser(String keyword);





}
