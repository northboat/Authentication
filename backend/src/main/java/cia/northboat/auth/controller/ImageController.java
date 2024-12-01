package cia.northboat.auth.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ImageController {

    @RequestMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage() throws IOException {
        int num = (int)(Math.random()*100) + 1;
        System.out.println(num);
        BufferedImage bufferedImage;
        if(num < 35){
            // ./src/main/resources/static/images/
            bufferedImage = ImageIO.read(Files.newInputStream(new File("./Ekko&Powder.png").toPath()));
        } else if(num <= 60){
            bufferedImage = ImageIO.read(Files.newInputStream(new File("./Powder.jpg").toPath()));
        } else{
            bufferedImage = ImageIO.read(Files.newInputStream(new File("./Ekko.jpg").toPath()));
        }

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bs);
        return bs.toByteArray();
    }

}
