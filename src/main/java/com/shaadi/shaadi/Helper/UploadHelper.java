package com.shaadi.shaadi.Helper;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadHelper {

    // Save files in external uploads folder
    public final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";
    
    public boolean uploadFile(MultipartFile file, String fileName) {
        try {
            fileName = Paths.get(fileName).getFileName().toString();

            // Create folder if not exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            Path fullPath = Paths.get(UPLOAD_DIR, fileName);

            Files.write(fullPath, file.getBytes());

            System.out.println("✅ File uploaded to: " + fullPath.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFilePath(String fileName) {
        return UPLOAD_DIR + "/" + fileName;
    }
}
