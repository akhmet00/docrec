package kz.product.docrec.service;

import kz.product.docrec.constants.PathConstants;
import kz.product.docrec.dto.IdCardDTO;
import kz.product.docrec.exception.CustomConflictException;
import kz.product.docrec.util.TextParser;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.sanselan.ImageReadException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ReadDataServiceImpl implements ReadDataService {

    private final Tesseract tesseractChars;
    private final Tesseract tesseractNumbers;
    private final TextParser textParser;

    @Autowired
    public ReadDataServiceImpl(@Qualifier("tesseractChars") Tesseract tesseractChars, @Qualifier("tesseractNumbers") Tesseract tesseractNumbers, TextParser textParser) {
        this.tesseractChars = tesseractChars;
        this.tesseractNumbers = tesseractNumbers;
        this.textParser = textParser;
    }



    @Override
    public ResponseEntity<?> readIdentityCard(MultipartFile[] multipartFile) {

        try {

            Rectangle rectangle = new Rectangle(0, 0, 900, 130);

            BufferedImage in = ImageIO.read(convert(multipartFile[0]));

            BufferedImage in2 = Scalr.resize(in, 2250, 1450);
            BufferedImage mono = invertImage(in2);


            BufferedImage croppedLastname = cropLastname(mono, rectangle);
            BufferedImage croppedFirstName = cropFirstname(mono, rectangle);
            BufferedImage croppedFathersName = cropFathersname(mono, rectangle);
            BufferedImage croppedBirthday = cropBirthday(mono, rectangle);
            BufferedImage croppedIin = cropIin(mono, rectangle);




            String lastname = tesseractChars.doOCR(croppedLastname).replace("\n", "");
            String firstname = tesseractChars.doOCR(croppedFirstName).replace("\n", "");
            String fathersname = tesseractChars.doOCR(croppedFathersName).replace("\n", "");
            String birthday = tesseractNumbers.doOCR(croppedBirthday).replace("\n", "");
            String iin = tesseractNumbers.doOCR(croppedIin).replace("\n", "");

            BufferedImage in3 = ImageIO.read(convert(multipartFile[1]));

            Rectangle rectangle1 = new Rectangle(0, 0, 700, 230);

            BufferedImage in4 = Scalr.resize(in3, 2250, 1450);

            BufferedImage mono2 = invertImage(in4);

            BufferedImage croppedIdCardNumber = cropIdCardNumber(mono2, rectangle1);

//            File outputFile = new File(PathConstants.PHOTOS_DIRECTORY + "/keks.jpg");
//            ImageIO.write(croppedIdCardNumber, "jpg", outputFile);

            String idCardNumber = tesseractNumbers.doOCR(croppedIdCardNumber).replace("\n", "").replace(".", "");



            return new ResponseEntity<>(new IdCardDTO(firstname, lastname, fathersname, iin, birthday, idCardNumber), HttpStatus.OK) ;
        } catch (Exception e) {
            throw new CustomConflictException(e.getLocalizedMessage());
        }
    }


    @Override
    public ResponseEntity<?> readIdentityCardOld(MultipartFile[] multipartFile)  throws TesseractException, IOException, ImageReadException {
        try {

            Rectangle rectangle = new Rectangle(0, 0, 900, 130);
            BufferedImage in = ImageIO.read(convert(multipartFile[0]));

            BufferedImage in2 = Scalr.resize(in, 2250, 1450);


            BufferedImage cropedLastname = cropLastnameOld(in2, rectangle);
            BufferedImage cropedFirstName = cropFirstnameOld(in2, rectangle);
            BufferedImage cropedFathersName = cropFathersnameOld(in2, rectangle);
            BufferedImage cropedBirthday = cropBirthdayOld(in2, rectangle);
            BufferedImage cropedIin = cropIinOld(in2, rectangle);



            String lastname = tesseractChars.doOCR(cropedLastname).replace("\n", "").replace(".", "");
            String firstname = tesseractChars.doOCR(cropedFirstName).replace("\n", "").replace(".", "");
            String fathersname = tesseractChars.doOCR(cropedFathersName).replace("\n", "").replace(".", "");
            String birthday = tesseractNumbers.doOCR(cropedBirthday).replace("\n", "");
            String iin = tesseractNumbers.doOCR(cropedIin).replace("\n", "").replace(".", "");

            BufferedImage in3 = ImageIO.read(convert(multipartFile[1]));

            Rectangle rectangle1 = new Rectangle(0, 0, 700, 330);

            BufferedImage in4 = Scalr.resize(in3, 2250, 1450);

            BufferedImage croppedIdCardNumber = cropIdCardNumberOld(in4, rectangle1);

            String idCardNumber = tesseractNumbers.doOCR(croppedIdCardNumber).replace("\n", "").replace(".", "");


            return new ResponseEntity<>(new IdCardDTO(firstname, lastname, fathersname, iin, birthday, idCardNumber),HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomConflictException(e.getLocalizedMessage());
        }
    }


    public static BufferedImage invertImage(BufferedImage inputFile) {


        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                int MONO_THRESHOLD = 395;
                if (col.getRed() + col.getGreen() + col.getBlue() > MONO_THRESHOLD)
                    col = new Color(255, 255, 255);
                else
                    col = new Color(0, 0, 0);
                inputFile.setRGB(x, y, col.getRGB());
            }
        }

        return inputFile;
    }



    private BufferedImage cropParameter(BufferedImage src) {
        return src.getSubimage(1, 1, 1100, 300);
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

    private BufferedImage cropIdCardNumber(BufferedImage src, Rectangle rect) {
        return src.getSubimage(1450, 0, rect.width, rect.height);
    }

    ///////////////////////////
    private BufferedImage cropLastnameOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 640, rect.width, rect.height);
    }

    private BufferedImage cropFirstnameOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 790, rect.width, rect.height);
    }

    private BufferedImage cropFathersnameOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 940, rect.width, rect.height);
    }

    private BufferedImage cropBirthdayOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(770, 1090, rect.width - 100, rect.height);
    }

    private BufferedImage cropIinOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(1300, 1090, rect.width, rect.height);
    }

    private BufferedImage cropIdCardNumberOld(BufferedImage src, Rectangle rect) {
        return src.getSubimage(1380, 25, rect.width, rect.height);
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
