package io.project.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armen
 */
@Service
@Component
@Slf4j
public class FileStorageService {

    @Value("${fileStoragePath}")
    private String basepath;

    public String storeFile(
            String organizationName,
            String title,
            byte[] content,
            Long organizationId,
            Long userId
    ) {
        String orgDir = organizationId + "/" + userId + "/";
        String filePath = null;
        String absPath = basepath + orgDir;
        String hashString = title + String.valueOf(System.currentTimeMillis());
        File file = null;

        do {
            StringBuilder sb = new StringBuilder();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                byte[] bs;
                bs = messageDigest.digest(hashString.getBytes());
                for (int i = 0; i < bs.length; i++) {
                    String hexVal = Integer.toHexString(0xFF & bs[i]);
                    if (hexVal.length() == 1) {
                        sb.append("0");
                    }
                    sb.append(hexVal);
                }
                hashString = sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                log.info("Cannot fined implementation of SHA algorigm: " + ex);
                return filePath;
            }
            filePath = orgDir + hashString;
            
            file = new File(absPath + hashString);
            log.info("file  " + file);
        } while (file.exists());

        try {
            File dirs = new File(absPath);
            if (!dirs.exists()) {
                dirs.mkdirs();
            }

            file.createNewFile();
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(content);
            }
        } catch (IOException e) {
            log.info("Cannot create file: " + absPath + ", exception: " + e);
        }
        log.info("return file path " + filePath);
        return filePath;
    }

    public boolean removeFile(String filepath) {
        log.info("filepath is " + filepath);
        File file = new File(basepath + filepath);
        if (file.exists()) {            
            boolean delete = file.delete();
            if(delete){
                log.info("Document deleted");
                return true;
            }
        }
        log.info("Document is not present");
        return false;
    }

    public byte[] readFile(String filepath) {
        byte[] content = null;
        if (basepath != null && filepath != null) {
            String abspath = basepath + filepath;
            log.info("File abspath is " + abspath);
            File file = new File(abspath);
            log.info("******* File: *******" + file);
            if (!file.exists()) {
                log.info("File did not exist");
                return content;
            }
            try {
                FileInputStream io = new FileInputStream(file);
                log.info("IO is " + io);
                int bytesize = io.available();
                log.info("bytesize" + bytesize);
                content = new byte[bytesize];
                log.info("content " + Arrays.toString(content));
                io.read(content);
            } catch (IOException e) {
                log.info("cannot read file: " + abspath + ", exception: " + e);
            }
        }
        return content;
    }
}
