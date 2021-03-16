package kz.product.docrec.service;

import kz.product.docrec.dto.IdCardDTO;
import kz.product.docrec.util.TextParser;
import lombok.SneakyThrows;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.sanselan.ImageReadException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

@Service
public class ReadDataServiceImpl implements ReadDataService {

    private final Tesseract tesseract;
    private final TextParser textParser;

    @Autowired
    public ReadDataServiceImpl(Tesseract tesseract, TextParser textParser) {
        this.tesseract = tesseract;
        this.textParser = textParser;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> readIdentityCardData(MultipartFile multipartFile) throws TesseractException, IOException, ImageReadException {

        BufferedImage in = ImageIO.read(convert(multipartFile));

        Rectangle rectangle = new Rectangle(0,0 ,900, 130);

        BufferedImage in2 = Scalr.resize(in, 2250, 1450);


        BufferedImage cropedLastname = cropLastname(in2, rectangle);
        BufferedImage cropedFirstName = cropFirstname(in2, rectangle);
        BufferedImage cropedFathersName = cropFathersname(in2, rectangle);
        BufferedImage cropedBirthday= cropBirthday(in2, rectangle);
        BufferedImage cropedIin = cropIin(in2, rectangle);

        File outputfile = new File("/home/escanor/IdeaProjects/docrec/src/main/resources/static/files/photos/kek/lol.jpg");
        ImageIO.write(in2, "jpg", outputfile);


        String lastname = tesseract.doOCR(cropedLastname).replace("\n", "");
        String firstname = tesseract.doOCR(cropedFirstName).replace("\n", "");
        String fathersname = tesseract.doOCR(cropedFathersName).replace("\n", "");
        String birthday = tesseract.doOCR(cropedBirthday).replace("\n", "");
        String iin = tesseract.doOCR(cropedIin).replace("\n", "");

        System.err.println(lastname);
        System.err.println(firstname);
        System.err.println(fathersname);
        System.err.println(birthday);
        System.err.println(iin);

        IdCardDTO idCardDTO = new IdCardDTO(firstname, lastname , fathersname, iin, birthday);

        return new ResponseEntity<>(idCardDTO, HttpStatus.OK);
    }


    private BufferedImage cropLastname(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 470, rect.width, rect.height);
    }

    private BufferedImage cropFirstname(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 670, rect.width, rect.height);
    }

    private BufferedImage cropFathersname(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 870, rect.width, rect.height);
    }

    private BufferedImage cropBirthday(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 1070, rect.width, rect.height);
    }

    private BufferedImage cropIin(BufferedImage src, Rectangle rect) {
        return src.getSubimage(300, 1270, rect.width, rect.height);
    }


    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }




}
