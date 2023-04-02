package com.shruteekatech.electronic.store.service.impl;

import com.shruteekatech.electronic.store.exception.BadApiRequestException;
import com.shruteekatech.electronic.store.service.CategoryFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
@Slf4j
public class CategoryFileImpl implements CategoryFileService {

    @Override
    public String uploadCoverImage(MultipartFile file, String path) throws IOException {
        log.info("Initiated service request for upload the file with path : ");
        //fir we get original file name
        String originalFilename = file.getOriginalFilename();
        //then we print with the help of log
        log.info("Filename : {}", originalFilename);
        //then we create the random uuid which is generate the random file name when user upload the file
        String filename = UUID.randomUUID().toString();
        //then we fetch the extension
        //means yaha pe substring kiya hai file name and file name ke bad .png/.jpeg aayega
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //then here we concate the file name and extension
        String fileNamewithExtension = filename + extension;
        String fullpathwithFilename = path + fileNamewithExtension;
        log.info("Full image path {} ", fullpathwithFilename);
        //then yaha pe extension allowd ho jayge ho bhi accepted hoga
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            //then agar extension exist hoga then hum file create kr rahe hai
            //to save the file

            //here we create the folder using file
            log.info("File extension is {} ", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                //if folder not exit then create the folder using mkdirs
                folder.mkdirs();
            }
            //upload -multipart se inputStream nikala and then yaha pe extension with filename will be saved
            Files.copy(file.getInputStream(), Paths.get(fullpathwithFilename));
            return fileNamewithExtension;

        } else {
            throw new BadApiRequestException("File with this " +extension + " not allowed");

    }

}
    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        log.info("Initiated service request to get file/image: {} " + name);
        String fullpath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullpath);
        log.info("Completed service request to get file/image: {} " + name);
        return inputStream;
    }
}
