package org.dsa.interviewSamples;

public class StringModificationSamples {

    public static void main(String[] args) {
        System.out.println("=== 1. REPLACE & REPLACE ALL ===\n");
        replaceExamples();

        System.out.println("\n=== 2. SUBSTRING & SPLITTING ===\n");
        substringAndSplit();

        System.out.println("\n=== 3. TRIM & STRIP (Whitespace Removal) ===\n");
        trimAndStrip();

        System.out.println("\n=== 4. CASE CONVERSION ===\n");
        caseConversion();

        System.out.println("\n=== 5. STRING BUILDER (Efficient Modification) ===\n");
        stringBuilderExamples();

        System.out.println("\n=== 6. JOIN & CONCATENATION ===\n");
        joinAndConcat();

        System.out.println("\n=== 7. FORMAT & TEMPLATE ===\n");
        formatExamples();

        System.out.println("\n=== 8. REGEX PATTERNS ===\n");
        regexPatterns();

        System.out.println("\n=== 9. PADDING & REPEATING ===\n");
        paddingAndRepeat();

        System.out.println("\n=== 10. ADVANCED TRANSFORMATIONS ===\n");
        advancedTransformations();
    }

    // 1. REPLACE & REPLACE ALL
    static void replaceExamples() {
        String text = "Hello World, World is beautiful";

        // replace() - replaces first occurrence or all if literal
        String replaced = text.replace("World", "Java");
        System.out.println("replace(): " + replaced);

        // replaceFirst() - only first match
        String replacedFirst = text.replaceFirst("World", "Universe");
        System.out.println("replaceFirst(): " + replacedFirst);

        // replaceAll() - uses regex
        String email = "contact@example.com";
        String masked = email.replaceAll("(.{3}).*(@.*)", "$1***$2");
        System.out.println("Masked email: " + masked);

        // Remove all digits
        String alphaOnly = "abc123def456".replaceAll("\\d+", "");
        System.out.println("Remove digits: " + alphaOnly);

        // Replace multiple spaces with single space
        String normalized = "Hello    World   Java".replaceAll("\\s+", " ");
        System.out.println("Normalized spaces: " + normalized);
    }

    // 2. SUBSTRING & SPLITTING
    static void substringAndSplit() {
        String text = "Java Programming Language";

        // substring(beginIndex)
        System.out.println("From index 5: " + text.substring(5));

        // substring(beginIndex, endIndex)
        System.out.println("Index 5-16: " + text.substring(5, 16));

        // split() - split by delimiter
        String csv = "John,Doe,30,Engineer";
        String[] parts = csv.split(",");
        System.out.println("Split CSV: " + String.join(" | ", parts));

        // split with regex
        String data = "apple;banana:cherry,date";
        String[] fruits = data.split("[;:,]");
        System.out.println("Split by multiple delimiters: " + String.join(", ", fruits));

        // split with limit
        String sentence = "one two three four five";
        String[] limited = sentence.split(" ", 3);
        System.out.println("Split with limit 3: " + String.join(" / ", limited));

        // Extract file extension
        String filename = "document.report.pdf";
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        System.out.println("Extension: " + extension);
    }

    // 3. TRIM & STRIP (Whitespace Removal)
    static void trimAndStrip() {
        String text = "   Hello World   ";

        // trim() - removes leading and trailing spaces
        System.out.println("trim(): '" + text.trim() + "'");

        // strip() - removes leading and trailing whitespace (Java 11+, Unicode-aware)
        String unicode = "\u2000\u2001Hello\u2002\u2003";
        System.out.println("strip(): '" + unicode.strip() + "'");

        // stripLeading() - only leading whitespace
        System.out.println("stripLeading(): '" + text.stripLeading() + "'");

        // stripTrailing() - only trailing whitespace
        System.out.println("stripTrailing(): '" + text.stripTrailing() + "'");

        // Remove all whitespace (including middle)
        String noSpaces = "H e l l o   W o r l d".replaceAll("\\s", "");
        System.out.println("No spaces: '" + noSpaces + "'");

        // Clean user input
        String userInput = "  john.doe@example.com  ";
        String cleaned = userInput.strip().toLowerCase();
        System.out.println("Cleaned input: '" + cleaned + "'");
    }

    // 4. CASE CONVERSION
    static void caseConversion() {
        String text = "Hello World";

        // toUpperCase()
        System.out.println("Upper: " + text.toUpperCase());

        // toLowerCase()
        System.out.println("Lower: " + text.toLowerCase());

        // Title Case (capitalize first letter of each word)
        String sentence = "hello world from java";
        String titleCase = toTitleCase(sentence);
        System.out.println("Title case: " + titleCase);

        // Swap case
        String swapped = swapCase("Hello World");
        System.out.println("Swapped: " + swapped);

        // Capitalize first letter only
        String name = "john";
        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        System.out.println("Capitalized: " + capitalized);

        // camelCase to snake_case
        String camel = "myVariableName";
        String snake = camel.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        System.out.println("Snake case: " + snake);
    }

    static String toTitleCase(String text) {
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    static String swapCase(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 5. STRING BUILDER (Efficient Modification)
    static void stringBuilderExamples() {
        // StringBuilder is mutable and efficient for multiple modifications
        StringBuilder sb = new StringBuilder("Hello");

        // append()
        sb.append(" World");
        System.out.println("After append: " + sb);

        // insert()
        sb.insert(5, " Beautiful");
        System.out.println("After insert: " + sb);

        // delete()
        sb.delete(5, 15);
        System.out.println("After delete: " + sb);

        // replace()
        sb.replace(0, 5, "Hi");
        System.out.println("After replace: " + sb);

        // reverse()
        System.out.println("Reversed: " + sb.reverse());
        sb.reverse(); // Reverse back

        // Building complex strings efficiently
        StringBuilder html = new StringBuilder();
        html.append("<html>")
                .append("<body>")
                .append("<h1>Title</h1>")
                .append("<p>Content</p>")
                .append("</body>")
                .append("</html>");
        System.out.println("HTML: " + html);

        // Performance comparison example
        long start = System.nanoTime();
        String result = "";
        for (int i = 0; i < 1000; i++) {
            result += i; // BAD - creates new String object each time
        }
        long stringTime = System.nanoTime() - start;

        start = System.nanoTime();
        StringBuilder sbResult = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sbResult.append(i); // GOOD - modifies existing object
        }
        long sbTime = System.nanoTime() - start;

        System.out.println("String concat time: " + stringTime + "ns");
        System.out.println("StringBuilder time: " + sbTime + "ns");
        System.out.println("StringBuilder is " + (stringTime / sbTime) + "x faster");
    }

    // 6. JOIN & CONCATENATION
    static void joinAndConcat() {
        // String.join() - join with delimiter
        String[] words = {"Java", "is", "awesome"};
        String joined = String.join(" ", words);
        System.out.println("Joined: " + joined);

        // Join with different delimiter
        System.out.println("CSV: " + String.join(", ", words));

        // Join with Stream
        String streamJoin = String.join(" - ",
                java.util.Arrays.asList("Apple", "Banana", "Cherry"));
        System.out.println("Stream join: " + streamJoin);

        // concat() - concatenate two strings
        String first = "Hello";
        String second = " World";
        System.out.println("Concat: " + first.concat(second));

        // Multiple concatenation
        String result = String.join("",
                "Part1",
                "Part2",
                "Part3");
        System.out.println("Multiple parts: " + result);

        // Joining with prefix and suffix
        java.util.StringJoiner joiner = new java.util.StringJoiner(", ", "[", "]");
        joiner.add("apple").add("banana").add("cherry");
        System.out.println("StringJoiner: " + joiner);

        // Collectors.joining() with Stream
        java.util.List<String> list = java.util.Arrays.asList("A", "B", "C");
        String collected = list.stream()
                .collect(java.util.stream.Collectors.joining(" -> ", "Start: ", " :End"));
        System.out.println("Stream joining: " + collected);
    }

    // 7. FORMAT & TEMPLATE
    static void formatExamples() {
        // String.format() - printf-style formatting
        String name = "John";
        int age = 30;
        double salary = 75000.50;

        String formatted = String.format("Name: %s, Age: %d, Salary: $%.2f", name, age, salary);
        System.out.println(formatted);

        // Padding numbers
        System.out.println(String.format("Padded: %05d", 42)); // 00042

        // Alignment
        System.out.println(String.format("Left: %-10s|", "text"));
        System.out.println(String.format("Right: %10s|", "text"));

        // formatted() method (Java 15+)
        String template = "Hello %s, you have %d messages".formatted("Alice", 5);
        System.out.println(template);

        // Date formatting
        java.util.Date now = new java.util.Date();
        System.out.println(String.format("Date: %tF %tT", now, now));

        // Hexadecimal, octal
        int num = 255;
        System.out.println(String.format("Dec: %d, Hex: %x, Oct: %o", num, num, num));

        // Currency formatting
        double price = 1234.56;
        System.out.println(String.format("Price: $%,.2f", price));
    }

    // 8. REGEX PATTERNS
    static void regexPatterns() {
        // Validate email
        String email = "user@example.com";
        boolean isValid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        System.out.println("Email valid: " + isValid);

        // Extract all numbers
        String text = "I have 3 apples and 5 oranges, total 8 fruits";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        System.out.print("Numbers found: ");
        while (matcher.find()) {
            System.out.print(matcher.group() + " ");
        }
        System.out.println();

        // Replace with regex groups
        String date = "2024-01-15";
        String usFormat = date.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$2/$3/$1");
        System.out.println("US date format: " + usFormat);

        // Remove HTML tags
        String html = "<p>Hello <b>World</b></p>";
        String plainText = html.replaceAll("<[^>]+>", "");
        System.out.println("Plain text: " + plainText);

        // Validate phone number
        String phone = "123-456-7890";
        boolean validPhone = phone.matches("\\d{3}-\\d{3}-\\d{4}");
        System.out.println("Phone valid: " + validPhone);

        // Extract domain from URL
        String url = "https://www.example.com/path/page.html";
        String domain = url.replaceAll("https?://([^/]+).*", "$1");
        System.out.println("Domain: " + domain);
    }

    // 9. PADDING & REPEATING
    static void paddingAndRepeat() {
        // repeat() - repeat string N times (Java 11+)
        String repeated = "* ".repeat(5);
        System.out.println("Repeated: " + repeated);

        // Left pad with zeros
        String number = "42";
        String padded = String.format("%05d", Integer.parseInt(number));
        System.out.println("Zero padded: " + padded);

        // Left pad with spaces
        String leftPad = String.format("%10s", "text");
        System.out.println("Left padded: '" + leftPad + "'");

        // Right pad
        String rightPad = String.format("%-10s", "text");
        System.out.println("Right padded: '" + rightPad + "'");

        // Custom padding
        String text = "Hello";
        String customPad = padLeft(text, 10, '-');
        System.out.println("Custom pad: '" + customPad + "'");

        // Create separator line
        String separator = "-".repeat(50);
        System.out.println(separator);

        // Center text
        String centered = centerText("TITLE", 20);
        System.out.println("Centered: '" + centered + "'");
    }

    static String padLeft(String text, int length, char padChar) {
        if (text.length() >= length) return text;
        return String.valueOf(padChar).repeat(length - text.length()) + text;
    }

    static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    // 10. ADVANCED TRANSFORMATIONS
    static void advancedTransformations() {
        // Reverse words in sentence
        String sentence = "Hello World Java";
        String reversedWords = reverseWords(sentence);
        System.out.println("Reversed words: " + reversedWords);

        // Remove duplicates
        String withDups = "aabbccddaabbcc";
        String noDups = removeDuplicateChars(withDups);
        System.out.println("No duplicates: " + noDups);

        // Slugify (URL-friendly string)
        String title = "Hello World! This is Java...";
        String slug = slugify(title);
        System.out.println("Slug: " + slug);

        // Truncate with ellipsis
        String longText = "This is a very long text that needs to be truncated";
        String truncated = truncate(longText, 20);
        System.out.println("Truncated: " + truncated);

        // Count word occurrences
        String text = "java is great and java is powerful";
        java.util.Map<String, Long> wordCount = java.util.Arrays.stream(text.split("\\s+"))
                .collect(java.util.stream.Collectors.groupingBy(
                        String::toLowerCase,
                        java.util.stream.Collectors.counting()
                ));
        System.out.println("Word count: " + wordCount);

        // Remove accents/diacritics
        String accented = "café résumé naïve";
        String normalized = java.text.Normalizer.normalize(accented, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        System.out.println("No accents: " + normalized);
    }

    static String reverseWords(String sentence) {
        String[] words = sentence.split(" ");
        java.util.Collections.reverse(java.util.Arrays.asList(words));
        return String.join(" ", words);
    }

    static String removeDuplicateChars(String text) {
        return text.chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(java.util.stream.Collectors.joining());
    }

    static String slugify(String text) {
        return text.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }

    static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}