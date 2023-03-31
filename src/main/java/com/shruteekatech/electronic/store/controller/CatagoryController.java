package com.shruteekatech.electronic.store.controller;

import com.shruteekatech.electronic.store.dto.CatagoryDto;

import com.shruteekatech.electronic.store.helper.*;

import com.shruteekatech.electronic.store.service.CatagoryServiceI;
import com.shruteekatech.electronic.store.service.CategoryFileService;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CatagoryController {
    @Autowired
    private CatagoryServiceI catagoryServiceI;
    @Autowired
    private CategoryFileService categoryFileService;

    @Value("${category.profile.image.path}")
    private String uploadCoverImage;

    @PostMapping
    public ResponseEntity<CatagoryDto> saveCatagory(@RequestBody CatagoryDto catagoryDto) {
        log.info("Initiated request for save the category data: ");
        CatagoryDto catagory = this.catagoryServiceI.createCatagory(catagoryDto);
        log.info("Completed request for save the category data: ");
        return new ResponseEntity<>(catagory, HttpStatus.CREATED);

    }

    @GetMapping("/getall")
    public ResponseEntity<PageableResponse<CatagoryDto>> getAll(
            @RequestParam(value = "pageSize", defaultValue = PaginationConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = PaginationConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = PaginationConstant.CATE_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request for get all the category data: ");
        PageableResponse<CatagoryDto> allCatagories = this.catagoryServiceI.getAllCatagories(pageSize, pageNumber, sortBy, sortDir);
        log.info("Completed request for get all the category data: ");
        return new ResponseEntity<>(allCatagories, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CatagoryDto> updateCategories(@RequestBody CatagoryDto catagoryDto, @PathVariable long id) {
        log.info("Initiated request for Update and save the category data: " + id);
        CatagoryDto catagoryDto1 = this.catagoryServiceI.updateCatagory(catagoryDto, id);
        log.info("Completed request for update and save the category data: " + id);
        return new ResponseEntity<>(catagoryDto1, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CatagoryDto> getCatagory(@PathVariable long id) {
        log.info("Initiated request for get the category data: " + id);
        CatagoryDto singleCatagory = this.catagoryServiceI.getSingleCatagory(id);
        log.info("Completed request for delete the category: " + id);
        return new ResponseEntity<>(singleCatagory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseMessage> deleteCatagory(@PathVariable long id) {
        log.info("Initiated request for delete the category: " + id);
        this.catagoryServiceI.deleteCatagory(id);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message(AppConstant.CATAGORY_DELETE)
                .status(HttpStatus.OK).currentDate(new Date()).
                success(true).build();
        log.info("Completed request for delete the category: " + id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CatagoryDto>> searchCategories(@PathVariable String keywords) {
        log.info("Initiated request for delete the category: " + keywords);
        List<CatagoryDto> catagoryDtos = this.catagoryServiceI.searchCategory(keywords);
        log.info("Completed request for delete the category: " + keywords);
        return new ResponseEntity<>(catagoryDtos, HttpStatus.OK);
    }
@PostMapping("/coverImage/{id}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("uploadImage") MultipartFile coverimage, @PathVariable long id) throws IOException {
        log.info("Initiated request for upload the coverImage of category: "+id);
        String coverImage = categoryFileService.uploadCoverImage(coverimage, uploadCoverImage);
        CatagoryDto singleCatagory = this.catagoryServiceI.getSingleCatagory(id);
        singleCatagory.setCoverImage(coverImage);
        CatagoryDto catagoryDto = this.catagoryServiceI.updateCatagory(singleCatagory, id);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(coverImage).success(true).status(HttpStatus.CREATED)
                .imgUploaddate(new Date()).message(AppConstant.UPLOAD_IMAGE).build();
        log.info("Completed request for upload the coverImage of category: "+id);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{id}")
    public void serveImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        log.info("Intiated request for get the Image of user by userId : " + id);
        CatagoryDto catagory = catagoryServiceI.getSingleCatagory(id);
        log.info("User image name: {} ", catagory.getCoverImage());
        InputStream resource = categoryFileService.getResource(uploadCoverImage, catagory.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed request for get the Image of user by userId : " + id);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}