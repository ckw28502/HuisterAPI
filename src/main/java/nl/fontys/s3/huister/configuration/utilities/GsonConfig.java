package nl.fontys.s3.huister.configuration.utilities;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    private Gson gson;
    @Bean
    public Gson gson(){
        if (gson == null) {
            gson=new Gson();
        }
        return gson;
    }
}
