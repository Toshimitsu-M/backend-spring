package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import dev.itboot.mb.service.AnnictService;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // フロントエンドのURLを指定
public class AnimeController {

    private static final Logger logger = LoggerFactory.getLogger(AnimeController.class);

    // private final AnnictService annictService;

    // public AnimeController(AnnictService annictService) {
    //     this.annictService = annictService;
    // }

    @GetMapping("/anime")
    public List<Map<String, String>> getAnimeList() {
        logger.info("GET /api/anime called");
        return Arrays.asList(
            Map.of("id", "1", "title", "進撃の巨人", "year", "2013"),
            Map.of("id", "2", "title", "鬼滅の刃", "year", "2019"),
            Map.of("id", "3", "title", "呪術廻戦", "year", "2020")
        );
    }

    // @GetMapping("/annict/works")
    // public Mono<String> getWorks() {
    //     return annictService.fetchWorks();
    // }

}
