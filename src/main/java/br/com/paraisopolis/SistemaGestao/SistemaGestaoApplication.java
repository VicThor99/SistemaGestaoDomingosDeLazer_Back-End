package br.com.paraisopolis.SistemaGestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SistemaGestaoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGestaoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SistemaGestaoApplication.class);
    }

}
