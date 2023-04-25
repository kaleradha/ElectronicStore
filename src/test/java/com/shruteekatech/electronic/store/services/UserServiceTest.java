package com.shruteekatech.electronic.store.services;

import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.User;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.UserRepository;
import com.shruteekatech.electronic.store.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;


import java.util.*;

@SpringBootTest(value =" UserServiceTest.class")

public class UserServiceTest{
    @MockBean
    private UserRepository userRepository ;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ModelMapper modelMapper;
    User user;

    User user1;

    List<User> users;

    UserDto userDto;

@BeforeEach
public void init() {
    userDto = UserDto.builder().name("priti")
            .email("priti1234@gmail.com")
            .about("I am Developer")
            .gender("Female")
            .password("priti1221")
            .imageName("abc.png").build();

    user = User.builder().name("Radha")
            .email("radha1236@gmail.com")
            .about("I am FullStack Developer")
            .gender("Female")
            .password("radha123")
            .imageName("pn.png").build();

    user1 = User.builder().name("Shivani")
            .email("shivani1267@gmail.com")
            .about("I am Tester ")
            .gender("Female")
            .password("shivani90")
            .imageName("pr.png").build();

//     user2 = User.builder().name("radhakale")
//            .email("rk123@gmail.com")
//            .about("I am a teacher ")
//            .gender("Female")
//            .password("radha")
//            .imageName("prng.png").build();

    users = new ArrayList<>();
    users.add(user);
    users.add(user1);

}
    @Test
    void createUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        //Act
        UserDto user1 = userServiceI.createUser(modelMapper.map(user, UserDto.class));
        //Assert
        Assertions.assertEquals("Radha", user1.getName(),"save User Test failed!");

}




@Test
void updateUserTest(){
    Long id = 10L;
    Mockito.when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
    //act
    UserDto updateUser = userServiceI.updateUser(userDto, id);
    Assertions.assertNotNull(updateUser);
    Assertions.assertEquals(userDto.getName(),updateUser.getName());
    Assertions.assertThrows(ResourceNotFoundException.class,()->userServiceI.updateUser(userDto,19L));


}
@Test
void deleteUserTest(){
    Long id = 10L;


    Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
    userServiceI.deleteUser(id);
    Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    Assertions.assertThrows(ResourceNotFoundException.class,()->userServiceI.deleteUser(12l));




}
@Test
void getSingleuserTest(){
    Long id=12l;
    Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
    UserDto singleUser = userServiceI.getSingleUser(id);
    Assertions.assertThrows(ResourceNotFoundException.class, ()->userServiceI.getSingleUser(23l));

}
@Test
public void getAllTest(){
    Page<User>page=new PageImpl<>(users);
    Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
 //   Sort sort = Sort.by("name").ascending();
  //  PageRequest pageRequest = PageRequest.of(1, 2, sort);

   // Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
    PageableResponse<UserDto> allUsers = userServiceI.getAllUsers(1, 2, "name", "asc");
    Assertions.assertEquals(2,allUsers.getContent().size());


}
@Test
public void getByEmailTest(){
String emailId="kaleradha";
Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
    UserDto userDto1 = userServiceI.getUserByEmail(emailId);
    Assertions.assertThrows(ResourceNotFoundException.class,()->userServiceI.getUserByEmail("radha"));


}
@Test
public void searchBykeyTest(){
    String keyword="radha";
    Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(users);

    List<UserDto> userDtos = userServiceI.searchUser(keyword);
    Assertions.assertEquals(2,userDtos.size());
}


}
