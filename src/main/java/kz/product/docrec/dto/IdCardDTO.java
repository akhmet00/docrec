package kz.product.docrec.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdCardDTO {

    private String firstName;

    private String lastName;

    private String fathersName;

    private String iin;

    private String birthday;

    private String idCardNumber;


}
