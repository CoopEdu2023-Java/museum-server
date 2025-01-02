package cn.moonshotacademy.museum.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import cn.moonshotacademy.museum.service.ImageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String createThumbnailedImage(String inputFilePath, int width, int height) {
        String outputFilePath = inputFilePath;
    
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
