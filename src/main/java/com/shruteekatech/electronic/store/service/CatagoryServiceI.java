package com.shruteekatech.electronic.store.service;

import com.shruteekatech.electronic.store.dto.CatagoryDto;
import com.shruteekatech.electronic.store.helper.PageableResponse;

import java.util.List;

public interface CatagoryServiceI {
    CatagoryDto createCatagory(CatagoryDto catagoryDto);

    CatagoryDto getSingleCatagory(long id);

    PageableResponse<CatagoryDto> getAllCatagories(int pageSize, int pageNumber, String sortBy, String sortDir);

    CatagoryDto updateCatagory(CatagoryDto catagoryDto, long id);

    void deleteCatagory(long id);

    List<CatagoryDto> searchCategory(String keyword);



}
