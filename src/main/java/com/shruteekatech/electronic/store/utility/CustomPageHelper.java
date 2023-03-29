package com.shruteekatech.electronic.store.utility;

import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.User;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CustomPageHelper {

    public static <U,V> PageableResponse<V> getPageResponse(Page<U> page, Class<V> dtoclass){
//HERE U IS NOTHING BUT THE ENTITY
        List<U> entity = page.getContent();
// AND V- IS NOTHING BUT THE USERdTO
        //yaha hum object of user pass kr rahe hai and it will return us V which is nothing but the userDtos
        List<V> userDtos = entity.stream().map(object ->new ModelMapper().map(object, dtoclass)).collect(Collectors.toList());
        PageableResponse<V> response =new PageableResponse<>();
        response.setContent(userDtos);
        response.setPageNumber(page.getNumber());
        response.setLastpage(page.isLast());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        return response;
    }
}
