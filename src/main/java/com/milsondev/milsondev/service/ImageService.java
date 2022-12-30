package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.ImageData;
import com.milsondev.milsondev.db.repository.ImageDataRepository;
import com.milsondev.milsondev.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageDataRepository imageDataRepository;

    @Autowired
    public ImageService(ImageDataRepository imageDataRepository) {
        this.imageDataRepository = imageDataRepository;
    }

    public Long uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        return imageData.getId();
    }


    public BufferedImage downloadImage(Long idImage) throws IOException {
        Optional<ImageData> dbImageData = imageDataRepository.findById(idImage);
        byte[] imagesBytes =ImageUtils.decompressImage(dbImageData.get().getImageData());
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imagesBytes));
        return img;
    }

    public String downloadImageBase64(Long idImage) throws IOException {
        Optional<ImageData> dbImageData = imageDataRepository.findById(idImage);
        byte[] imagesBytes =ImageUtils.decompressImage(dbImageData.get().getImageData());
        return DatatypeConverter.printBase64Binary(imagesBytes);
    }


}
