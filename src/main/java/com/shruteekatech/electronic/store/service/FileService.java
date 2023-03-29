package com.shruteekatech.electronic.store.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    //path of the file or image and file or any image int the form of multipartfile
    public String uplaodImage( MultipartFile file,String path) throws IOException;

    InputStream getResource(String path, String name) throws FileNotFoundException;
    //it will return path and file in the form of inputstream
}
