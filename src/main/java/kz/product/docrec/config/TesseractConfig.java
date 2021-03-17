package kz.product.docrec.config;

import kz.product.docrec.constants.PathConstants;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Bean
    @Qualifier(value = "tesseractChars")
    public Tesseract tesseractChars(){
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("kaz");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_whitelist", "АБВГДЕЁЖЗИЙКЛМНОПРСТУФКХЧШЩЪЫЬЭЮЯӘҒҚҢӨҰҮҺІ");
        tesseract.setDatapath(PathConstants.LANGUAGES_DIRECTORY);
        return tesseract;
    }

    @Bean
    @Qualifier(value = "tesseractNumbers")
    public Tesseract tesseractNumbers(){
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("kaz");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_whitelist", "01234567890.");
        tesseract.setDatapath(PathConstants.LANGUAGES_DIRECTORY);
        return tesseract;
    }

}
