package com.shruteekatech.electronic.store.service.impl;

import com.shruteekatech.electronic.store.dto.CatagoryDto;
import com.shruteekatech.electronic.store.entity.Catagory;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.AppConstant;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.CatagoryRepository;
import com.shruteekatech.electronic.store.service.CatagoryServiceI;
import com.shruteekatech.electronic.store.utility.CustomPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CatagoryImpl implements CatagoryServiceI {


    @Autowired
    private CatagoryRepository catagoryRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CatagoryDto createCatagory(CatagoryDto catagoryDto) {
        log.info("Initiated Dao call for create the category! ");
        Catagory catagory = this.modelMapper.map(catagoryDto, Catagory.class);
        catagory.setIsactive(AppConstant.YES);
        Catagory catagory1 = this.catagoryRepository.save(catagory);
        log.info("Completed Dao call for create the category! ");
        return this.modelMapper.map(catagory1, CatagoryDto.class);
    }

    @Override
    public CatagoryDto getSingleCatagory(long id) {
        log.info("Intiated dao call for get single category by Id {} " + id);
        Catagory catagory = this.catagoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND + id));
        log.info("Completed dao call for get single category by Id {} " + id);
        return this.modelMapper.map(catagory, CatagoryDto.class);

    }

    @Override
    public PageableResponse<CatagoryDto> getAllCatagories(int pageSize, int pageNumber, String sortBy, String sortDir) {
        log.info("Intiated dao call for get all category  {} " );
       Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());

        PageRequest request=PageRequest.of(pageNumber,pageSize,sort);
        Page<Catagory> catagories = this.catagoryRepository.findAll(request);
        PageableResponse<CatagoryDto> pageResponse = CustomPageHelper.getPageResponse(catagories, CatagoryDto.class);
        log.info("Completed dao call for get all category  {} " );
        return pageResponse;
    }

    @Override
    public CatagoryDto updateCatagory(CatagoryDto catagoryDto, long id) {
        log.info("Initiating dao call for Update the category data by id: " + id);
        Catagory catagory = this.catagoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND +id));
        catagory.setDescription(catagoryDto.getDescription());
        catagory.setTitle(catagoryDto.getTitle());
        catagory.setCoverImage(catagoryDto.getCoverImage());
        Catagory catagory1 = this.catagoryRepository.save(catagory);
        CatagoryDto catagorydto = this.modelMapper.map(catagory1, CatagoryDto.class);
        log.info("Completing dao call for Update the category data by id: " + id);
        return catagorydto;
    }

    @Override
    public void deleteCatagory(long id) {
        log.info("Intiated dao call for delete category by Id {} " + id);
        Catagory catagory = this.catagoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND +id));
        log.info("Completing dao call for delete category by Id {} " + id);
        catagory.setIsactive(AppConstant.NO);
        this.catagoryRepository.save(catagory);
      //  this.catagoryRepository.delete(catagory);
    }

    @Override
    public List<CatagoryDto> searchCategory(String keyword) {
        log.info("Intiated dao call for get all category by keyword {} " +keyword );
        List<Catagory> catagories = this.catagoryRepository.findByTitleContaining(keyword);
        List<CatagoryDto> collect = catagories.stream().map(catagory -> this.modelMapper.map(catagory, CatagoryDto.class)).collect(Collectors.toList());
        log.info("Completed dao call for get all category by keywords {} " +keyword );
        return collect;
    }

}