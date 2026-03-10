package de.netschach;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class ChessApplication {

    @Autowired
    private BuildProperties buildProperties;

    @PostConstruct
    void logVersion() {
        log.info("app-version: {}", buildProperties.getVersion());
    }

    public static void main(String[] args) {
        SpringApplication.run(ChessApplication.class, args);
    }
}
