package ro.duoline.promed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProMedApplication
//        extends SpringBootServletInitializer { //war deployment
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ProMedApplication.class);
//    }
    {
    public static void main(String[] args) {
        SpringApplication.run(ProMedApplication.class, args);
    }
}
