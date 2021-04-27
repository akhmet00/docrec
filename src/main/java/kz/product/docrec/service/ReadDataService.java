package kz.product.docrec.service;

import net.sourceforge.tess4j.TesseractException;
import org.apache.sanselan.ImageReadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ReadDataService {

    ResponseEntity<?> readIdentityCard(MultipartFile[] multipartFile) throws TesseractException, IOException, ImageReadException;



}
