package org.dsa.interviewSamples;

import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

public class AdvancedStreamProblems {

    static class Employee {
        String name;
        String department;
        double salary;
        int age;
        List<String> skills;

        Employee(String name, String department, double salary, int age, List<String> skills) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.age = age;
            this.skills = skills;
        }

        @Override
        public String toString() {
            return name + " (" + department + ", $" + salary + ")";
        }
    }

    static class Transaction {
        String id;
        String category;
        double amount;
        int year;

        Transaction(String id, String category, double amount, int year) {
            this.id = id;
            this.category = category;
            this.amount = amount;
            this.year = year;
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "Engineering", 95000, 28, Arrays.asList("Java", "Python", "SQL")),
                new Employee("Bob", "Marketing", 65000, 32, Arrays.asList("SEO", "Analytics")),
                new Employee("Charlie", "Engineering", 85000, 25, Arrays.asList("Java", "JavaScript", "Docker")),
                new Employee("Diana", "HR", 70000, 30, Arrays.asList("Recruiting", "SQL")),
                new Employee("Eve", "Engineering", 105000, 35, Arrays.asList("Python", "AWS", "Docker")),
                new Employee("Frank", "Marketing", 72000, 29, Arrays.asList("Content", "SEO")),
                new Employee("Grace", "HR", 68000, 27, Arrays.asList("Payroll", "SQL")),
                new Employee("Henry", "Engineering", 92000, 31, Arrays.asList("Java", "Kubernetes", "AWS"))
        );

        List<Transaction> transactions = Arrays.asList(
                new Transaction("T1", "Electronics", 1200.50, 2023),
                new Transaction("T2", "Groceries", 85.30, 2023),
                new Transaction("T3", "Electronics", 450.00, 2024),
                new Transaction("T4", "Clothing", 120.00, 2023),
                new Transaction("T5", "Groceries", 95.60, 2024),
                new Transaction("T6", "Electronics", 890.00, 2024),
                new Transaction("T7", "Clothing", 200.00, 2024),
                new Transaction("T8", "Groceries", 110.40, 2023)
        );

        System.out.println("=== PROBLEM 1: Nth Highest Salary ===");
        System.out.println("Find the 2nd highest salary\n");
        problem1(employees);

        System.out.println("\n=== PROBLEM 2: Complex Grouping with Mapping ===");
        System.out.println("Group by department, collect only names as Set\n");
        problem2(employees);

        System.out.println("\n=== PROBLEM 3: Top N per Group ===");
        System.out.println("Get top 2 highest paid employees per department\n");
        problem3(employees);

        System.out.println("\n=== PROBLEM 4: FlatMap with Frequency Count ===");
        System.out.println("Count frequency of each skill across all employees\n");
        problem4(employees);

        System.out.println("\n=== PROBLEM 5: Custom Collector - Statistics ===");
        System.out.println("Get salary statistics (min, max, avg, sum) by department\n");
        problem5(employees);

        System.out.println("\n=== PROBLEM 6: Multi-level Grouping with Transformation ===");
        System.out.println("Group by year and category, sum amounts\n");
        problem6(transactions);

        System.out.println("\n=== PROBLEM 7: Reduce Operation ===");
        System.out.println("Calculate total salary budget with bonus (10% for age > 30)\n");
        problem7(employees);

        System.out.println("\n=== PROBLEM 8: Finding Duplicates ===");
        System.out.println("Find departments with duplicate skill sets (employees with same skills)\n");
        problem8(employees);

        System.out.println("\n=== PROBLEM 9: Collectors.teeing (Java 12+) ===");
        System.out.println("Get both highest and lowest paid employees simultaneously\n");
        problem9(employees);

        System.out.println("\n=== PROBLEM 10: Custom Object Creation ===");
        System.out.println("Create department summary: name, count, total salary, avg age\n");
        problem10(employees);
    }

    // PROBLEM 1: Find Nth highest salary (2nd highest)
    static void problem1(List<Employee> employees) {
        Optional<Double> secondHighest = employees.stream()
                .map(emp -> emp.salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();

        secondHighest.ifPresent(salary ->
                System.out.println("Solution: $" + salary)
        );
    }

    // PROBLEM 2: Group by department, collect names as Set (not List)
    static void problem2(List<Employee> employees) {
        Map<String, Set<String>> deptNames = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department, Collectors.mapping(emp -> emp.name, Collectors.toSet())
                ));

        deptNames.forEach((dept, names) -> System.out.println(dept + ": " + names));

        Map<String, List<String>> deptNamesWithList = employees
                .stream()
                .collect(
                        Collectors.groupingBy(
                                emp -> emp.department,
                                Collectors.mapping(emp -> emp.name, Collectors.toList())
                        )
                );

    }

    // PROBLEM 3: Top N per group - Top 2 earners in each department
    static void problem3(List<Employee> employees) {
        Map<String, List<Employee>> topByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream().sorted(Comparator.comparingDouble((Employee e) -> e.salary).reversed()).limit(2).collect(Collectors.toList())
                        )
                ));

        topByDept.forEach((dept, emps) -> {
            System.out.println(dept + ":");
            emps.forEach(emp -> System.out.println("  " + emp.name + " - $" + emp.salary));
        });
    }

    // PROBLEM 4: FlatMap with frequency - Count each skill occurrence
    static void problem4(List<Employee> employees) {
        Map<String, Long> skillFrequency = employees.stream()
                .flatMap(emp -> emp.skills.stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        skillFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.println(entry.getKey() + ": " + entry.getValue() + " employees")
                );
    }

    // PROBLEM 5: Get statistics by department (min, max, avg, sum)
    static void problem5(List<Employee> employees) {
        Map<String, DoubleSummaryStatistics> statsByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department,
                        Collectors.summarizingDouble(emp -> emp.salary)
                ));

        statsByDept.forEach((dept, stats) -> {
            System.out.println(dept + ":");
            System.out.println("  Min: $" + stats.getMin());
            System.out.println("  Max: $" + stats.getMax());
            System.out.println("  Avg: $" + String.format("%.2f", stats.getAverage()));
            System.out.println("  Sum: $" + stats.getSum());
        });
    }

    // PROBLEM 6: Multi-level grouping - Group by year, then category, sum amounts
    static void problem6(List<Transaction> transactions) {
        Map<Integer, Map<String, Double>> summary = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.year,
                        Collectors.groupingBy(
                                t -> t.category,
                                Collectors.summingDouble(t -> t.amount)
                        )
                ));

        summary.forEach((year, categories) -> {
            System.out.println("Year " + year + ":");
            categories.forEach((cat, total) ->
                    System.out.println("  " + cat + ": $" + String.format("%.2f", total))
            );
        });
    }

    // PROBLEM 7: Reduce - Calculate total with conditional bonus
    static void problem7(List<Employee> employees) {
        double totalWithBonus = employees.stream()
                .mapToDouble(emp -> emp.age > 30 ? emp.salary * 1.10 : emp.salary)
                .reduce(0.0, Double::sum);

        System.out.println("Solution: $" + String.format("%.2f", totalWithBonus));
    }

    // PROBLEM 8: Find skills that appear in multiple employees
    static void problem8(List<Employee> employees) {
        Map<String, List<String>> skillToEmployees = employees.stream()
                .flatMap(emp -> emp.skills.stream()
                        .map(skill -> new AbstractMap.SimpleEntry<>(skill, emp.name)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        Map<String, List<String>> duplicateSkills = skillToEmployees.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        duplicateSkills.forEach((skill, empNames) ->
                System.out.println(skill + ": " + empNames)
        );
    }

    // PROBLEM 9: Collectors.teeing - Get max and min simultaneously
    static void problem9(List<Employee> employees) {
        Map<String, Employee> result = employees.stream()
                .collect(Collectors.teeing(
                        Collectors.maxBy(Comparator.comparingDouble(e -> e.salary)),
                        Collectors.minBy(Comparator.comparingDouble(e -> e.salary)),
                        (max, min) -> {
                            Map<String, Employee> map = new HashMap<>();
                            max.ifPresent(e -> map.put("Highest", e));
                            min.ifPresent(e -> map.put("Lowest", e));
                            return map;
                        }
                ));

        result.forEach((key, emp) ->
                System.out.println(key + ": " + emp.name + " ($" + emp.salary + ")")
        );
    }

    // PROBLEM 10: Create custom summary objects
    static void problem10(List<Employee> employees) {
        class DepartmentSummary {
            String department;
            long count;
            double totalSalary;
            double avgAge;

            DepartmentSummary(String dept, long count, double total, double avgAge) {
                this.department = dept;
                this.count = count;
                this.totalSalary = total;
                this.avgAge = avgAge;
            }

            @Override
            public String toString() {
                return String.format("%s: %d employees, $%.2f total, %.1f avg age",
                        department, count, totalSalary, avgAge);
            }
        }

        List<DepartmentSummary> summaries = employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.department))
                .entrySet().stream()
                .map(entry -> new DepartmentSummary(
                        entry.getKey(),
                        entry.getValue().size(),
                        entry.getValue().stream().mapToDouble(e -> e.salary).sum(),
                        entry.getValue().stream().mapToDouble(e -> e.age).average().orElse(0)
                ))
                .collect(Collectors.toList());

        summaries.forEach(System.out::println);
    }

    // --------------- Claude ---------------

}