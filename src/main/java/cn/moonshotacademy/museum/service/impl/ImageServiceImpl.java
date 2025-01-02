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
        String outputFilePath = "Thumbnailed" + "_" + inputFilePath;
    
        try {
            Builder<File> instance = Thumbnails.of(inputFilePath);
            instance.size(200, 200);
            instance.outputFormat("jpg");
            instance.toFile(outputFilePath);

            Thumbnails.of(inputFilePath)
                          .size(width, height)  // 设置缩略图的大小
                            .outputFormat("jpg")  // 设置输出格式
                          .toFile(outputFilePath);  // 指定输出文件
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputFilePath;
    }
    
}
