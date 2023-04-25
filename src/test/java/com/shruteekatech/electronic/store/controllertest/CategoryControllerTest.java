package com.shruteekatech.electronic.store.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shruteekatech.electronic.store.controller.CatagoryController;
import com.shruteekatech.electronic.store.dto.CatagoryDto;
import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.Catagory;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.service.CatagoryServiceI;
import com.shruteekatech.electronic.store.services.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CategoryControllerTest extends BaseTest {
    @MockBean
    private CatagoryServiceI catagoryServiceI;
    @InjectMocks
    private CatagoryController catagoryController;

    @Autowired
    private MockMvc mockMvc;
    Catagory catagory;
    CatagoryDto dto, dto1, dto2;
    @Autowired
    private ModelMapper mapper;
    List<CatagoryDto> catagoryDtos;

    @BeforeEach
    public void init() {
        catagory = Catagory.builder().title("this tis fabroyt moivoe").coverImage("jo.jpg")
                .description("goodquoairymo fo jejmfjjjjjdjjj").build();

        dto = CatagoryDto.builder().title("fgggggggggggggggggggwssssssssssssss").coverImage("jo.jpg")
                .description("goodquoairymo fo jejmfjjjjjdjjj").build();
        dto1 = CatagoryDto.builder().title("fgggggggggggggggggggwssssssssssssss").coverImage("jo.jpg")
                .description("goodquoairymo fo jejmfjjjjjdjjj").build();
        dto2 = CatagoryDto.builder().title("fgggggggggggggggggggwssssssssssssss").coverImage("jo.jpg")
                .description("goodquoairymo fo jejmfjjjjjdjjj").build();
        catagoryDtos = new ArrayList<>();
        catagoryDtos.add(dto);
        catagoryDtos.add(dto1);
        catagoryDtos.add(dto2);

    }

    @Test
    @DisplayName("[201] POST /categories/ - Create New Category")
    public void createUserTest() throws Exception {
        CatagoryDto dto = mapper.map(catagory, CatagoryDto.class);

        Mockito.when(catagoryServiceI.createCatagory(Mockito.any())).thenReturn(dto);

        //acutul request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonstring(catagory))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonstring(Object catagory) {
        try {
            return new ObjectMapper().writeValueAsString(catagory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Test
    @DisplayName("[201] CREATED /categories/update/")
    public void updateUserTest() throws Exception {
        long id = 1l;
        CatagoryDto dto = this.mapper.map(catagory, CatagoryDto.class);

        Mockito.when(catagoryServiceI.updateCatagory(Mockito.any(), Mockito.anyLong())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonstring(catagory))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                   .andExpect(jsonPath("$.title").exists());


    }



    @Test
    @DisplayName("[200] GET /categories/getall/ - Get all categories ! ")
    public void getAllCategoriesTest() throws Exception {

        PageableResponse<CatagoryDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(catagoryDtos);
        pageableResponse.setLastpage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElement(1000);
        Mockito.when(catagoryServiceI.getAllCatagories(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/getall/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                ).andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("[200] DELETE /categories/delete/1l - Delete Category by Category Id !")
    public void deleteCategoryTest() throws Exception {
        doNothing().when(catagoryServiceI).deleteCatagory(Mockito.anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/" + 1l)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /categories/search/radha - Searchall Categories by keyword !")
    public void searchCategoriesTest() throws Exception {
        Mockito.when(catagoryServiceI.searchCategory(Mockito.anyString())).thenReturn(catagoryDtos);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/search/" + "radha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /categories/3l - Get Single Category By Id !")
    public void getSingleCategoryTest() throws Exception {
        long id=3l;

        Mockito.when(catagoryServiceI.getSingleCatagory(Mockito.anyLong())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());


    }}