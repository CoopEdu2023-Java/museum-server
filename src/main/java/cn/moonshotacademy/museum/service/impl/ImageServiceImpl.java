package cn.moonshotacademy.museum.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import cn.moonshotacademy.museum.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String createThumbnailedImage(String inputFilePath, int width, int height, Boolean isunique) {
        String outputFilePath;
        if (isunique) {
            outputFilePath = inputFilePath;
        } else {
            outputFilePath = inputFilePath.substring(0, inputFilePath.lastIndexOf(".")) + "_thumb" + inputFilePath.substring(inputFilePath.lastIndexOf("."));
        }
        try {
            Thumbnails.of(inputFilePath)
                          .size(width, height)  // 设置缩略图的大小
                          .toFile(outputFilePath);  // 指定输出文件
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputFilePath;
    }
    
}
