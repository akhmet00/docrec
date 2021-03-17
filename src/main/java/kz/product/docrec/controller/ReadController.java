package kz.product.docrec.controller;

import kz.product.docrec.service.ReadDataService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.sanselan.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/read")
public class ReadController {

    private final ReadDataService readDataService;

    @Autowired
    public ReadController(ReadDataService readDataService) {
        this.readDataService = readDataService;
    }


    @PostMapping("/identityCard")
    public ResponseEntity<?> readFromPhoto(@RequestParam("file") MultipartFile multipartFile) throws TesseractException, IOException, ImageReadException {
        return readDataService.readIdentityCardData(multipartFile);
    }
}
