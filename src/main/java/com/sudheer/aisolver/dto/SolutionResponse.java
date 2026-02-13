package com.sudheer.aisolver.dto;

import lombok.Data;

@Data
public class SolutionResponse {
    private String intuition;
    private String bruteForce;
    private String optimizedApproach;
    private String code;
    private String timeComplexity;
    private String spaceComplexity;
    private String edgeCases;
    private String dryRun;
    private String patternRecognition;
    private String followUpQuestions;
}
