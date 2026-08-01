package org.dsa.interviewSamples;

import java.util.*;
import java.util.stream.*;

public class StreamAPIProblems {

    static class Employee {
        String name;
        String department;
        double salary;
        int age;

        Employee(String name, String department, double salary, int age) {
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " (" + department + ", $" + salary + ", " + age + "yo)";
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "Engineering", 95000, 28),
                new Employee("Bob", "Marketing", 65000, 32),
                new Employee("Charlie", "Engineering", 85000, 25),
                new Employee("Diana", "HR", 70000, 30),
                new Employee("Eve", "Engineering", 105000, 35),
                new Employee("Frank", "Marketing", 72000, 29),
                new Employee("Grace", "HR", 68000, 27),
                new Employee("Henry", "Engineering", 92000, 31)
        );

        System.out.println("=== PROBLEM 1: Basic Mapping ===");
        System.out.println("Get all employee names in uppercase\n");
        problem1(employees);

        System.out.println("\n=== PROBLEM 2: Filtering and Mapping ===");
        System.out.println("Get names of employees earning more than $80,000\n");
        problem2(employees);

        System.out.println("\n=== PROBLEM 3: Grouping by Department ===");
        System.out.println("Group employees by department\n");
        problem3(employees);

        System.out.println("\n=== PROBLEM 4: Grouping and Counting ===");
        System.out.println("Count employees in each department\n");
        problem4(employees);

        System.out.println("\n=== PROBLEM 5: Average Salary by Department ===");
        System.out.println("Calculate average salary per department\n");
        problem5(employees);

        System.out.println("\n=== PROBLEM 6: Partitioning ===");
        System.out.println("Partition employees by salary > $80,000\n");
        problem6(employees);

        System.out.println("\n=== PROBLEM 7: Finding Max/Min ===");
        System.out.println("Find highest paid employee\n");
        problem7(employees);

        System.out.println("\n=== PROBLEM 8: FlatMap ===");
        System.out.println("Get all unique characters from employee names\n");
        problem8(employees);

        System.out.println("\n=== PROBLEM 9: Complex Grouping ===");
        System.out.println("Group employees by department and age group (under/over 30)\n");
        problem9(employees);

        System.out.println("\n=== PROBLEM 10: Custom Collector ===");
        System.out.println("Get comma-separated names of Engineering employees\n");
        problem10(employees);
    }

    // PROBLEM 1: Basic Mapping
    // Transform each employee to their uppercase name
    static void problem1(List<Employee> employees) {
        List<String> upperNames = employees.stream()
                .map(emp -> emp.name.toUpperCase())
                .toList();

        System.out.println("Solution: " + upperNames);
    }

    // PROBLEM 2: Filtering and Mapping
    // Filter employees with salary > 80000 and get their names
    static void problem2(List<Employee> employees) {
        List<String> highEarners = employees.stream()
                .filter(emp -> emp.salary > 80000)
                .map(emp -> emp.name)
                .collect(Collectors.toList());

        System.out.println("Solution: " + highEarners);
    }

    // PROBLEM 3: Grouping by Department
    // Group employees by their department
    static void problem3(List<Employee> employees) {
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(emp -> emp.department));

        byDept.forEach((dept, emps) -> {
            System.out.println(dept + ": " + emps.stream()
                    .map(e -> e.name)
                    .collect(Collectors.joining(", ")));
        });
    }

    // PROBLEM 4: Grouping and Counting
    // Count how many employees in each department
    static void problem4(List<Employee> employees) {
        Map<String, Long> countByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department,
                        Collectors.counting()
                ));

        System.out.println("Solution: " + countByDept);
    }

    // PROBLEM 5: Average Salary by Department
    // Calculate average salary for each department
    static void problem5(List<Employee> employees) {
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department,
                        Collectors.averagingDouble(emp -> emp.salary)
                ));

        avgSalaryByDept.forEach((dept, avg) ->
                System.out.println(dept + ": $" + String.format("%.2f", avg))
        );
    }

    // PROBLEM 6: Partitioning
    // Split employees into two groups: high earners (>80k) and others
    static void problem6(List<Employee> employees) {
        Map<Boolean, List<Employee>> partitioned = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.salary > 80000));

        System.out.println("High earners (>$80k): " +
                partitioned.get(true).stream()
                        .map(e -> e.name)
                        .collect(Collectors.joining(", ")));
        System.out.println("Others: " +
                partitioned.get(false).stream()
                        .map(e -> e.name)
                        .collect(Collectors.joining(", ")));
    }

    // PROBLEM 7: Finding Max/Min
    // Find the employee with highest salary
    static void problem7(List<Employee> employees) {
        Optional<Employee> highest = employees.stream()
                .max(Comparator.comparingDouble(emp -> emp.salary));

        highest.ifPresent(emp ->
                System.out.println("Solution: " + emp.name + " with $" + emp.salary)
        );
    }

    // PROBLEM 8: FlatMap
    // Get all unique characters from all employee names
    static void problem8(List<Employee> employees) {
        Set<Character> uniqueChars = employees.stream()
                .flatMap(emp -> emp.name.chars().mapToObj(c -> (char) c))
                .collect(Collectors.toSet());

        System.out.println("Solution: " + uniqueChars);
    }

    // PROBLEM 9: Complex Grouping
    // Group by department, then by age group (under 30 vs 30+)
    static void problem9(List<Employee> employees) {
        Map<String, Map<String, List<Employee>>> grouped = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.department,
                        Collectors.groupingBy(emp -> emp.age < 30 ? "Under 30" : "30+")
                ));

        grouped.forEach((dept, ageGroups) -> {
            System.out.println(dept + ":");
            ageGroups.forEach((ageGroup, emps) -> {
                System.out.println("  " + ageGroup + ": " +
                        emps.stream().map(e -> e.name).collect(Collectors.joining(", ")));
            });
        });
    }

    // PROBLEM 10: Custom Collector (Joining)
    // Get names of all Engineering employees as comma-separated string
    static void problem10(List<Employee> employees) {
        String engineeringNames = employees.stream()
                .filter(emp -> emp.department.equals("Engineering"))
                .map(emp -> emp.name)
                .collect(Collectors.joining(", ", "Engineers: ", ""));

        System.out.println("Solution: " + engineeringNames);
    }
}