package cn.moonshotacademy.museum.service;

public interface ImageService {
    public String createThumbnailedImage(String inputString, int width, int height, Boolean isunique);
}
