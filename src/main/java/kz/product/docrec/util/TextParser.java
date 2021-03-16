package kz.product.docrec.util;

import kz.product.docrec.dto.IdCardDTO;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TextParser {

    public IdCardDTO parseIdCard(String response) throws ParseException {
        IdCardDTO idCardDTO = new IdCardDTO();

//        System.out.println(response);
//
//        String[] dividedData = response.split("\n");
//
//
//        idCardDTO.setLastName(dividedData[2]);
//        idCardDTO.setFirstName(dividedData[4]);
//        idCardDTO.setFathersName(dividedData[6]);
//        System.err.println(dividedData[8].split(" ")[1]);
//        idCardDTO.setBirthday(new SimpleDateFormat("dd.MM.yyyy").parse(dividedData[8].split(" ")[1]));
//        idCardDTO.setIin(dividedData[9].split(" ")[2]);

        return idCardDTO;
    }

}
