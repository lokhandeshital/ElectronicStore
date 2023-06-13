package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.exception.BadApiRequest;
import com.bikkadit.electronic.store.service.FileService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@Builder
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        log.info("Filename : {}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtention = filename + extention;
        String fullPathWithExtention = path  + fileNameWithExtention;

        if (extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg")) {

            //File Save
            File folder = new File(path);

            if (!folder.exists()) {

                //create the folder
                folder.mkdirs();
            }

            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithExtention));
            return fileNameWithExtention;

        } else {
            throw new BadApiRequest("File with this " + extention + "Not Allowed !!");

        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
