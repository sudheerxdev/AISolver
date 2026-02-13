//package com.sudheer.aisolver.service;
//
//import com.sudheer.aisolver.dto.ProblemRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class ProblemService {
//
//    private final WebClient webClient;
//
//    public String solveProblem(ProblemRequest request) {
//
//        try {
//
//            String prompt = buildPrompt(request.getProblem(), request.getLanguage());
//
//            Map<String, Object> body = Map.of(
//                    "contents", List.of(
//                            Map.of(
//                                    "parts", List.of(
//                                            Map.of("text", prompt)
//                                    )
//                            )
//                    )
//            );
//
//            Map<String, Object> response = webClient.post()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/v1beta/models/gemini-3-flash-preview:generateContent")
//                            .queryParam("key", System.getenv("GEMINI_API_KEY"))
//                            .build())
//                    .bodyValue(body)
//                    .retrieve()
//                    .bodyToMono(Map.class)
//                    .block();
//
//            if (response == null || response.get("candidates") == null) {
//                return "AI did not return any response.";
//            }
//
//            List<Map<String, Object>> candidates =
//                    (List<Map<String, Object>>) response.get("candidates");
//
//            Map<String, Object> firstCandidate = candidates.get(0);
//
//            Map<String, Object> content =
//                    (Map<String, Object>) firstCandidate.get("content");
//
//            List<Map<String, Object>> parts =
//                    (List<Map<String, Object>>) content.get("parts");
//
//            Map<String, Object> part = parts.get(0);
//
//            return part.get("text").toString();
//
//        } catch (Exception e) {
//            return "Error while calling AI service: " + e.getMessage();
//        }
//    }
//
//
//    private String buildPrompt(String problem, String language) {
//        return """
//        You are a senior software engineer.
//
//        STRICT JSON OUTPUT:
//        {
//          "intuition": "",
//          "bruteForce": "",
//          "optimizedApproach": "",
//          "code": "",
//          "timeComplexity": "",
//          "spaceComplexity": "",
//          "edgeCases": "",
//          "dryRun": "",
//          "patternRecognition": "",
//          "followUpQuestions": ""
//        }
//
//        Language: %s
//
//        Problem:
//        %s
//        """.formatted(language, problem);
//    }
//}

//package com.sudheer.aisolver.service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sudheer.aisolver.dto.AiResponse;
//
//import com.sudheer.aisolver.dto.ProblemRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class ProblemService {
//
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//
//
//
//    public Mono<AiResponse> solveProblem(ProblemRequest request) {
//
//        String prompt = buildPrompt(request.getProblem(), request.getLanguage());
//
//        Map<String, Object> body = Map.of(
//                "contents", List.of(
//                        Map.of(
//                                "parts", List.of(
//                                        Map.of("text", prompt)
//                                )
//                        )
//                )
//        );
//
//        return webClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/v1beta/models/gemini-3-flash-preview:generateContent")
//                        .queryParam("key", System.getenv("GEMINI_API_KEY"))
//                        .build())
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .map(response -> {
//
//                    if (response == null || response.get("candidates") == null) {
//                        return "AI did not return any response.";
//                    }
//
//                    List<Map<String, Object>> candidates =
//                            (List<Map<String, Object>>) response.get("candidates");
//
//                    Map<String, Object> firstCandidate = candidates.get(0);
//
//                    Map<String, Object> content =
//                            (Map<String, Object>) firstCandidate.get("content");
//
//                    List<Map<String, Object>> parts =
//                            (List<Map<String, Object>>) content.get("parts");
//
//                    return parts.get(0).get("text").toString();
//                })
//                .onErrorResume(e ->
//                        Mono.just("Error while calling AI service: " + e.getMessage())
//                );
//    }
//
//    private String buildPrompt(String problem, String language) {
//        return """
//        You are a senior software engineer.
//
//        STRICT JSON OUTPUT:
//        {
//          "intuition": "",
//          "bruteForce": "",
//          "optimizedApproach": "",
//          "code": "",
//          "timeComplexity": "",
//          "spaceComplexity": "",
//          "edgeCases": "",
//          "dryRun": "",
//          "patternRecognition": "",
//          "followUpQuestions": ""
//        }
//
//        Language: %s
//
//        Problem:
//        %s
//        """.formatted(language, problem);
//    }
//}



package com.sudheer.aisolver.service;

import com.sudheer.aisolver.entity.AiProblem;
import com.sudheer.aisolver.repository.AiProblemRepository;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudheer.aisolver.dto.AiResponse;
import com.sudheer.aisolver.dto.ProblemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final AiProblemRepository repository;

    public Mono<AiResponse> solveProblem(ProblemRequest request) {

        String prompt = buildPrompt(request.getProblem(), request.getLanguage());

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-3-flash-preview:generateContent")
                        .queryParam("key", System.getenv("GEMINI_API_KEY"))
                        .build())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {

                    if (response == null || response.get("candidates") == null) {
                        throw new RuntimeException("AI did not return any response.");
                    }

                    List<Map<String, Object>> candidates =
                            (List<Map<String, Object>>) response.get("candidates");

                    Map<String, Object> firstCandidate = candidates.get(0);

                    Map<String, Object> content =
                            (Map<String, Object>) firstCandidate.get("content");

                    List<Map<String, Object>> parts =
                            (List<Map<String, Object>>) content.get("parts");

                    String aiText = parts.get(0).get("text").toString();

                    try {

                        // 🔥 Convert AI JSON → DTO
                        AiResponse aiResponse =
                                objectMapper.readValue(aiText, AiResponse.class);

                        // 🔥 SAVE TO DATABASE
                        AiProblem saved = AiProblem.builder()
                                .problem(request.getProblem())
                                .language(request.getLanguage())
                                .response(aiText)
                                .createdAt(LocalDateTime.now())
                                .build();

                        repository.save(saved);

                        return aiResponse;

                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse AI response", e);
                    }
                });
    }

    private String buildPrompt(String problem, String language) {
        return """
        You are a senior software engineer.

        STRICT JSON OUTPUT:
        {
          "intuition": "",
          "bruteForce": "",
          "optimizedApproach": "",
          "code": "",
          "timeComplexity": "",
          "spaceComplexity": "",
          "edgeCases": "",
          "dryRun": "",
          "patternRecognition": "",
          "followUpQuestions": ""
        }

        Language: %s

        Problem:
        %s
        """.formatted(language, problem);
    }
}
