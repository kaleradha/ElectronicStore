package com.shruteekatech.electronic.store.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shruteekatech.electronic.store.controller.UserController;
import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.User;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.service.UserServiceI;
import com.shruteekatech.electronic.store.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends BaseTest {
    @MockBean
    private UserServiceI userservice;
    @InjectMocks
    private UserController userController;

    User user;

    @Autowired
    ModelMapper mapper;
    @Autowired
    MockMvc mockMvc;

    UserDto dto, dto1, dto2;

    List<UserDto> userDtoList;

    @BeforeEach
    public void init() {
        user = User.builder().userId(12l).name("Radha")
                .email("radha1236@gmail.com")
                .about("I am FullStack Developer fgfgggggggggggggddddddddgb fff")
                .gender("Female")
                .password("1234")
                .imageName("pn.png").build();

        dto = UserDto.builder().userId(12l).name("Radha")
                .email("radha1236@gmail.com")
                .about("I am FullStack Developer fgfgggggggggggggddddddddgb fff")
                .gender("Female")
                .password("1234")
                .imageName("pn.png").build();
        dto1 = UserDto.builder().name("Radha gwande")
                .email("radha1236@gmail.com")
                .about("I am FullStack Developer fgfgggggggggggggddddddddgb fff")
                .gender("Female")
                .password("1234")
                .imageName("pn.png").build();
        dto2 = UserDto.builder().name("Radha kalbande")
                .email("radha1236@gmail.com")
                .about("I am FullStack Developer fgfgggggggggggggddddddddgb fff")
                .gender("Female")
                .password("1234")
                .imageName("pn.png").build();

        userDtoList = new ArrayList<>();
        userDtoList.add(dto);
        userDtoList.add(dto1);
        userDtoList.add(dto2);

    }


    @Test
    @DisplayName("[201] POST /users/ - Create new User !")
    public void createUserTest() throws Exception {
        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userservice.createUser(Mockito.any())).thenReturn(dto);

        //acutul request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonstring(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    @Test
    @DisplayName("[201] PUT /users/update/10L - Update User By UserId :  ")
    public void updateUserTest() throws Exception {
        long userId = 100L;
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userservice.updateUser(Mockito.any(), Mockito.anyLong())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonstring(user))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    @DisplayName("[200] GET /users/ - Get All Users :")
    public void getAllUsers() throws Exception {
        UserDto object1 = UserDto.builder().name("Radha").email("radhagawande5050@gmail.com").password("abcd").gender("female").about("fullstack devleoperdxfxdvvvvvvvvvvedzzz").imageName("abc.png").build();
        UserDto object2 = UserDto.builder().name("Surbhi").email("surbhi1234@gmail.com").password("abcd").gender("female").about("fullstackzmvmmbrkmflekfekeker devleoper").imageName("abcd.png").build();
        UserDto object3 = UserDto.builder().name("Pallavi").email("pallavi1236@gmail.com").password("xyzt").gender("female").about("fullstackskkkkkkkkkfrsrrrf devleoper").imageName("yz.png").build();

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1, object2, object3));

        pageableResponse.setLastpage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElement(1000);
        Mockito.when(userservice.getAllUsers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                ).andDo(print())
                .andExpect(status().isOk());

    }

    private String convertObjectToJsonstring(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    @DisplayName("[200] GET /users/getuser/12l - Get Single User by UserId :")
    public void getSingleUserTest() throws Exception {
        Long userId = 12l;
        UserDto dto = this.mapper.map(user, UserDto.class);

        Mockito.when(userservice.getSingleUser(Mockito.anyLong())).thenReturn(dto);
//        ResponseEntity<UserDto> user1 = userController.getUser(12l);
//        long actual = user1.getBody().getUserId();
//        Assertions.assertEquals(uId,actual);
//        Assertions.assertEquals(HttpStatus.OK,user1.getStatusCode());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/getuser/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());


    }


    private ResultActions andDo(ResultHandler print) {
        return null;
    }

    @Test
    @DisplayName("[200] GET /users/byemail/email - Get User By EmailId :")
    public void getByEmailTest() throws Exception {
        String email = "kaleradha419@gmail.com";
        Mockito.when(userservice.getUserByEmail(Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/byemail/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("[200] GET /users/search/ - Search User By Keyword!")
    public void searchUserTest() throws Exception {
        Mockito.when(userservice.searchUser(Mockito.anyString())).thenReturn(userDtoList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/search/" + "Radha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk());

    }

    @Test
    @DisplayName("[200] DELETE /users/1L - Delete User by UserId:")
    public void deleteUserTest() throws Exception {
        doNothing().when(userservice).deleteUser(Mockito.anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + 1l)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }
}