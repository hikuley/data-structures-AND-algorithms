package org.dsa.interviewSamples;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CharacterCounter {

    // 1. Find characters that appear exactly 3 times
    public static void problem1() {
        String input = "programming";
        final Map<Character, Long> map = input.chars()
                .mapToObj(c -> (char) c)
                .collect(
                        Collectors.groupingBy(Function.identity(), Collectors.counting())
                );

        List<Character> result = map
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 3)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("1. Characters appearing exactly 3 times: " + result);
    }

    // 2. Find the most frequently occurring character
    public static void problem2() {
        String input = "mississippi";

        final Map<Character, Long> map = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<Character, Long>> result = map
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        result.ifPresent(e ->
                System.out.println("2. Most frequent character: '" + e.getKey() + "' appears " + e.getValue() + " times")
        );
    }

    // 3. Find all unique characters (appear only once)
    public static void problem3() {
        String input = "hello world";
        List<Character> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("3. Unique characters (appear once): " + result);
    }

    // 4. Count vowels and consonants separately
    public static void problem4() {
        String input = "java streams are powerful";
        String vowels = "aeiouAEIOU";

        Map<String, Long> result = input.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .collect(Collectors.groupingBy(
                        c -> vowels.indexOf(c) != -1 ? "Vowels" : "Consonants",
                        Collectors.counting()
                ));

        System.out.println("4. Vowels and Consonants: " + result);
    }

    // 5. Find first non-repeating character
    public static void problem5() {
        String input = "swiss";
        Optional<Character> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();

        System.out.println("5. First non-repeating character: " +
                result.map(c -> "'" + c + "'").orElse("None"));
    }

    // 6. Group characters by their frequency
    public static void problem6() {
        String input = "aabbbbcccc";
        Map<Long, List<Character>> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));

        System.out.println("6. Characters grouped by frequency: " + result);
    }

    // 7. Find characters that appear more than N times
    public static void problem7() {
        String input = "bookkeeper";
        int N = 2;
        List<Character> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > N)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("7. Characters appearing more than " + N + " times: " + result);
    }

    // 8. Calculate percentage frequency of each character
    public static void problem8() {
        String input = "banana";
        long totalChars = input.length();

        Map<Character, String> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.format("%.2f%%", (e.getValue() * 100.0) / totalChars)
                ));

        System.out.println("8. Percentage frequency: " + result);
    }

    // 9. Find the longest sequence of repeating characters
    public static void problem9() {
        String input = "aaabbccccdd";
        char[] chars = input.toCharArray();
        char maxChar = chars[0];
        int maxCount = 1;
        char currentChar = chars[0];
        int currentCount = 1;

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == currentChar) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    maxChar = currentChar;
                }
                currentChar = chars[i];
                currentCount = 1;
            }
        }

        if (currentCount > maxCount) {
            maxCount = currentCount;
            maxChar = currentChar;
        }

        System.out.println("9. Longest consecutive sequence: '" + maxChar + "' appears " + maxCount + " times");
    }

    // 10. Sort characters by frequency in descending order
    public static void problem10() {
        String input = "algorithm";
        List<Map.Entry<Character, Long>> result = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        System.out.println("10. Characters sorted by frequency (descending):");
        result.forEach(e -> System.out.println("    '" + e.getKey() + "': " + e.getValue()));
    }

    public static void main(String[] args) {
        System.out.println("=== Java Streams Character Analysis - 10 Problems ===\n");

        problem1();
        System.out.println();

        problem2();
        System.out.println();

        problem3();
        System.out.println();

        problem4();
        System.out.println();

        problem5();
        System.out.println();

        problem6();
        System.out.println();

        problem7();
        System.out.println();

        problem8();
        System.out.println();

        problem9();
        System.out.println();

        problem10();
    }
}