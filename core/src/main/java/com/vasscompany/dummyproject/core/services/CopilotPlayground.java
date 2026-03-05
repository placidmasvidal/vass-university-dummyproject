package com.vasscompany.dummyproject.core.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CopilotPlayground {

    //
    public int countWordOccurrences(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }

    //unique words
    public int countUniqueWordOccurrences(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        Map<String, Long> wordCount = java.util.Arrays.stream(words)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        return wordCount.size();
    }
}
