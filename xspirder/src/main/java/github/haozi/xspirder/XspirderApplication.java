package github.haozi.xspirder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class XspirderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XspirderApplication.class, args);
    }

}
