package com.shruteekatech.electronic.store.services;

import com.shruteekatech.electronic.store.dto.CatagoryDto;

import com.shruteekatech.electronic.store.entity.Catagory;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.CatagoryRepository;
import com.shruteekatech.electronic.store.service.CatagoryServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CategoryImplTest extends BaseTest {
    @MockBean
    private CatagoryRepository catagoryRepository;
    @Autowired
    private CatagoryServiceI catagoryServiceI;
    @Autowired
    private ModelMapper modelMapper;

    Catagory catagory;

    Catagory catagory1;

    List<Catagory> Categories;
    CatagoryDto catagoryDto;

    @BeforeEach
    public void init() {
        catagory = Catagory.builder().title("Java development").
                description("java is an object oriented language").
                coverImage("radha.png").build();

        catagoryDto = CatagoryDto.builder().title("this is about python").
                description("python is simple than java").coverImage("pht.jpg").build();

        catagory1 = Catagory.builder().title("physics").description("one of the hardest subject").coverImage("phy.png").build();

        Categories = new ArrayList<>();
        Categories.add(catagory);
        Categories.add(catagory1);


    }

    @Test
    public void createCategoryTest() {
        //arrange
        when(catagoryRepository.save(Mockito.any())).thenReturn(catagory);
//act
        CatagoryDto catagoryDto1 = catagoryServiceI.createCatagory(modelMapper.map(catagory, CatagoryDto.class));
//assert
        Assertions.assertEquals("Java development", catagoryDto1.getTitle(), "Title did not matched!");

    }

    @Test
    public void getCategoryTest() {
        Long catId = 10l;
        //arrange
        when(catagoryRepository.findById(catId)).thenReturn(Optional.of(catagory));
        //act
        CatagoryDto catagory2 = catagoryServiceI.getSingleCatagory(catId);
        //assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> catagoryServiceI.getSingleCatagory(12l));
    }


    @Test
    void deleteUserByIdTest() {
        Long cId = 10L;
//        User user = User.builder()
////                .imageName("abc.jpg")
//                .build();
//


        //Arrange
        when(catagoryRepository.findById(cId)).thenReturn(Optional.of(catagory));
        //Act
        catagoryServiceI.deleteCatagory(cId);
        //Assert
        verify(catagoryRepository, times(1)).findById(cId);
        verify(catagoryRepository, times(1)).save(catagory);

        Assertions.assertThrows(ResourceNotFoundException.class,()->catagoryServiceI.deleteCatagory(43));

//}
    }
    @Test
    public void updateCategoryTest(){
        //arrange
        Long cid=45l;
        Mockito.when(catagoryRepository.findById(cid)).thenReturn(Optional.of(catagory));
        Mockito.when(catagoryRepository.save(Mockito.any())).thenReturn(catagory);
        CatagoryDto updateCatagory = catagoryServiceI.updateCatagory(catagoryDto, cid);
        Assertions.assertNotNull(updateCatagory);
        Assertions.assertEquals(catagoryDto.getTitle(),updateCatagory.getTitle());
        Assertions.assertThrows(ResourceNotFoundException.class,()->catagoryServiceI.updateCatagory(catagoryDto,33l));

    }
    @Test
    public void getAllTest(){
        Page<Catagory>page=new PageImpl<>(Categories);
        Mockito.when(catagoryRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<CatagoryDto> allCatagories = catagoryServiceI.getAllCatagories(1, 2, "name", "asc");
        Assertions.assertEquals(2,allCatagories.getContent().size());


    }
@Test
public void searchbyKeywordTest(){
        //arrange
    String keyword="radha";
    Mockito.when(catagoryRepository.findByTitleContaining(keyword)).thenReturn(Categories);
    //act
    List<CatagoryDto> catagoryDtos = catagoryServiceI.searchCategory(keyword);
    //assert
    Assertions.assertEquals(2,catagoryDtos.size());

}
}