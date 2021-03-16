package kz.product.docrec.config;

import kz.product.docrec.constants.PathConstants;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Bean
    public Tesseract tesseract(){
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("kaz");
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setTessVariable("tessedit_char_whitelist", "01234567890АБВГДЕЁЖЗИЙКЛМНОПРСТУФКХЧШЩЪЫЬЭЮЯ.ӘҒҚҢӨҰҮҺІ");
        tesseract.setDatapath(PathConstants.LANGUAGES_DIRECTORY);
        return tesseract;
    }

}
