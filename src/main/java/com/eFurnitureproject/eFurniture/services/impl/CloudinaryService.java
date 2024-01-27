package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;
    public CloudinaryService() {
        Cloudinary cloudinary = null;
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dbzzkebfm");
        config.put("api_key", "491474858821112");
        config.put("api_secret", "FRYILcI0JEt7BcVnrr2BecTBlak");
        cloudinary = new Cloudinary(config);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
    File file = convert(multipartFile);
    Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    if(!Files.deleteIfExists(file.toPath())){
        throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
    }
    return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
