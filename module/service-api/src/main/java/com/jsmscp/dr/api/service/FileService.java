package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${path.root}")
    private String rootPath;
    @Value("${path.prefix.image}")
    private String imgPath;

    public String uploadImg(MultipartFile file) throws Exception {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MMdd/");
        String fileName = format.format(date) + date.getTime() + ".jpg";
        fileName = imgPath + fileName;
        try {
            File osFile = new File(rootPath + fileName);
            logger.info("上传位置:{}", osFile.getAbsolutePath());
            if (!osFile.getParentFile().exists()) {
                osFile.getParentFile().mkdirs();
            }
            file.transferTo(osFile);
        } catch (Exception e) {
            logger.error("", e);
            throw new ServiceException("上传失败！");
        }
        return fileName;
    }
}
