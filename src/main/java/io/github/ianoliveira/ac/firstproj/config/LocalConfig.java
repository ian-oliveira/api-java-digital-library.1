package io.github.ianoliveira.ac.firstproj.config;

import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private VideoRepository repository;

    @Bean
    public void startDB(){
        Video v1 = new Video("PERDIDOS EM PARIS", "eu voltei ta",
                "https://www.youtube.com/watch?v=D_GxH_KgARU");
        Video v2 = new Video("2021 EM UMA MÚSICA", "Sound design by: Lucas Vinícius",
                "https://www.youtube.com/watch?v=X2mOfqeAH7c");
        Video v3 = new Video("CASIMIRO REAGE: FEIJOADA DA COREIA? LUXUOSA COMIDA DE RUA COREANA | Cortes do Casimito",
                "Deixe o like!",
                "https://www.youtube.com/watch?v=yLXtaCAA65g");

        repository.saveAll(List.of(v1, v2, v3));
    }
}
