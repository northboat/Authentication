package cia.northboat.auth.service;


import cia.northboat.auth.dao.ImageRepository;
import cia.northboat.auth.pojo.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Random;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private final Random random = new Random();

    public Image findById(int id){
        Image image = imageRepository.findById(id);
        if(Objects.isNull(image)){
            throw new RuntimeException("Image not found");
        }
        return image;
    }

    public long count(){
        return imageRepository.count();
    }


    public String getRandomPath(){
        long count = count();
        if (count == 0) {
            throw new IllegalStateException("No records found in the database.");
        }
        int id = random.nextInt((int) count) + 1;
        return findById(id).getPath();
    }


    public byte[] getRandomImage() throws IOException {
        String path = getRandomPath();
        BufferedImage bufferedImage = ImageIO.read(Files.newInputStream(new File(path).toPath()));

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bs);
        return bs.toByteArray();
    }

}