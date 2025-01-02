package cn.moonshotacademy.museum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
        exclude = {
            SecurityAutoConfiguration.class,
        })
public class MsaMuseumServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaMuseumServerApplication.class, args);
    }
}
