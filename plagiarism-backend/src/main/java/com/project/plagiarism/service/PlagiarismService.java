package com.project.plagiarism.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlagiarismService {

    // All dataset files
    private final List<String> datasetFiles = List.of(
            "java.txt",
            "python.txt",
            "web.txt"
    );

    public double checkAgainstAll(String inputText) throws Exception {

        if (inputText == null || inputText.trim().isEmpty()) {
            return 0.0;
        }

        double maxPlagiarism = 0.0;

        for (String file : datasetFiles) {
            double percentage = calculatePlagiarism(inputText, file);
            maxPlagiarism = Math.max(maxPlagiarism, percentage);
        }

        return maxPlagiarism;
    }

    private double calculatePlagiarism(String inputText, String fileName) throws Exception {

        ClassPathResource resource =
                new ClassPathResource("dataset/" + fileName);

        String datasetText;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()))) {

            datasetText = reader.lines()
                    .collect(Collectors.joining(" "))
                    .toLowerCase();
        }

        String[] words = inputText.toLowerCase().split("\\s+");

        int match = 0;
        for (String word : words) {
            if (!word.isBlank() && datasetText.contains(word)) {
                match++;
            }
        }

        return (match * 100.0) / words.length;
    }
}
