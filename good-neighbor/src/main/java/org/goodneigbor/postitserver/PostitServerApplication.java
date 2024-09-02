package org.goodneigbor.postitserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PostitServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostitServerApplication.class, args);
    }

}
