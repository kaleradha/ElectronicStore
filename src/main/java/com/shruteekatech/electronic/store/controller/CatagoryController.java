package com.shruteekatech.electronic.store.controller;

import com.shruteekatech.electronic.store.dto.CatagoryDto;

import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.helper.*;

import com.shruteekatech.electronic.store.service.CatagoryServiceI;
import com.shruteekatech.electronic.store.service.CategoryFileService;
import com.shruteekatech.electronic.store.service.ProductService;
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

/**
 * This Class is for Creating the API requests for Category Details.
 * @author {Radha_Patil}
 * @since 15-3-2023
 *
 */

@Slf4j
@RestController
@RequestMapping("/categories")
public class CatagoryController {
    @Autowired
    private CatagoryServiceI catagoryServiceI;
    @Autowired
    private CategoryFileService categoryFileService;
    @Autowired
    private ProductService productService;

    @Value("${category.profile.image.path}")
    private String uploadCoverImage;

    /**
     * This API is for sending the request for Creating the Category.
     * @param categoryDto need not be null {@not null}
     * @return the saved Category will never (@literal null)
     */

    @PostMapping
    public ResponseEntity<CatagoryDto> saveCatagory(@RequestBody CatagoryDto catagoryDto) {
        log.info("Initiated request for save the category data: ");
        CatagoryDto catagory = this.catagoryServiceI.createCatagory(catagoryDto);
        log.info("Completed request for save the category data: ");
        return new ResponseEntity<>(catagory, HttpStatus.CREATED);

    }

    /**
     * This API is for getting all the Category in sorting order.
     *
     * @param pageSize takes size of the page in the parameters.
     * @param pageNumber takes pageNumber start from 0.
     * @param sortBy takes condtion want to sort the @{Category}.
     * @param sortDir it defines the direction which is needed to sorting.
     * @return All the sorted list of Categories according to sorting order present in entity.
     */

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

    /**
     * This API is for Sending the Request for Update the Existing Cetegory Record.
     *
     * @param catagoryDto need not be {@literal null}
     * @param id need not be {@literal null}
     * @return It will gives the Updated Record .
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<CatagoryDto> updateCategories(@RequestBody CatagoryDto catagoryDto, @PathVariable long id) {
        log.info("Initiated request for Update and save the category data: {} " + id);
        CatagoryDto catagoryDto1 = this.catagoryServiceI.updateCatagory(catagoryDto, id);
        log.info("Completed request for update and save the category data: {} " + id);
        return new ResponseEntity<>(catagoryDto1, HttpStatus.CREATED);

    }

    /**
     * This API is for sending request for fetch the single category data.
     *
     * @param id need not be {@literal null}
     * @return the  category with the help of id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CatagoryDto> getCatagory(@PathVariable long id) {
        log.info("Initiated request for get the category data: {} " + id);
        CatagoryDto singleCatagory = this.catagoryServiceI.getSingleCatagory(id);
        log.info("Completed request for delete the category: {} " + id);
        return new ResponseEntity<>(singleCatagory, HttpStatus.OK);
    }

    /**
     * This API is for Deletes the Category by Its Id.
     *
     * @param id with with @{@link PathVariable}.
     * @return {@apiResponseMessage} with some meaningfull information after successfull deltes the Category.
     *
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseMessage> deleteCatagory(@PathVariable long id) {
        log.info("Initiated request for delete the category: {} " + id);
        this.catagoryServiceI.deleteCatagory(id);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message(AppConstant.CATAGORY_DELETE)
                .status(HttpStatus.OK).currentDate(new Date()).
                success(true).build();
        log.info("Completed request for delete the category: {} " + id);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }
    /**
     * This api is for getting all the Category having the given @keyword.
     *
     * @param keywords not be null {@literal  null}
     * @return return all the Categories having {@link PathVariable @keywords}
     */

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CatagoryDto>> searchCategories(@PathVariable String keywords) {
        log.info("Initiated request for delete the category: {} " + keywords);
        List<CatagoryDto> catagoryDtos = this.catagoryServiceI.searchCategory(keywords);
        log.info("Completed request for delete the category: {} " + keywords);
        return new ResponseEntity<>(catagoryDtos, HttpStatus.OK);
    }

    /**
     *This API  is for uploading the {CoverImage}.
     *
     * @param {coverimage}it takes which is uploaded by the user.
     * @param id cannot be {@Not null} belongs to CategoryId.
     * @return @{@link ImageResponse}with image uploaded message!.
     * @throws IOException
     */
@PostMapping("/coverImage/{id}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("uploadImage") MultipartFile coverimage, @PathVariable long id) throws IOException {
        log.info("Initiated request for upload the coverImage of category: {} "+id);
        String coverImage = categoryFileService.uploadCoverImage(coverimage, uploadCoverImage);
        CatagoryDto singleCatagory = this.catagoryServiceI.getSingleCatagory(id);
        singleCatagory.setCoverImage(coverImage);
        CatagoryDto catagoryDto = this.catagoryServiceI.updateCatagory(singleCatagory, id);
        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(coverImage).success(true).status(HttpStatus.CREATED)
                .imgUploaddate(new Date()).message(AppConstant.UPLOAD_IMAGE).build();
        log.info("Completed request for upload the coverImage of category: {} "+id);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }
    /**
     * This @api request is for get/fetch the CoverImage of the Particular Cateogry.
     *
     * @author {Radha_patil}
     * @param id need not bu @{@NotNull}.
     * @param response with the coverImage.
     * @throws IOException
     */
    @GetMapping("/image/{id}")
    public void serveImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        log.info("Intiated request for get the Image of user by userId : {} " + id);
        CatagoryDto catagory = catagoryServiceI.getSingleCatagory(id);
        log.info("User image name: {} ", catagory.getCoverImage());
        InputStream resource = categoryFileService.getResource(uploadCoverImage, catagory.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed request for get the Image of user by userId : {} " + id);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @PostMapping("/{id}/products")
    public ResponseEntity<ProductDto>createProductWithCategory(@PathVariable Long id,@RequestBody ProductDto  productDto){
        ProductDto productDto1 = this.productService.createWithCategory(productDto, id);
        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);
    }
}