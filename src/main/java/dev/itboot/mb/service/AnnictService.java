// package dev.itboot.mb.service;

// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;
// import reactor.core.publisher.Mono;

// @Service
// public class AnnictService {
//     private final WebClient webClient;

//     public AnnictService(WebClient.Builder webClientBuilder) {
//         this.webClient = webClientBuilder.baseUrl("https://api.annict.com/v1").build();
//     }

//     public Mono<String> fetchWorks() {
//         return webClient.get()
//                 .uri(uriBuilder -> uriBuilder
//                         .path("/works")
//                         .queryParam("fields", "id,title,season_name,season_year,watchers_count")
//                         .queryParam("sort_watchers_count", "desc")
//                         .queryParam("access_token", "YOUR_ANNICT_ACCESS_TOKEN")
//                         .build())
//                 .retrieve()
//                 .bodyToMono(String.class);
//     }
// }
