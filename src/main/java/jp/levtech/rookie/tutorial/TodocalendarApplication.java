package jp.levtech.rookie.tutorial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("jp.levtech.rookie.tutorial.repository.mybatis")
@EnableScheduling 
public class TodocalendarApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodocalendarApplication.class, args);
    }
}