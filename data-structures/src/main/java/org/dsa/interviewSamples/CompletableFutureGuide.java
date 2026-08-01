package org.dsa.interviewSamples;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompletableFutureGuide {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. BASIC CREATION ===\n");
        basicCreation();

        System.out.println("\n=== 2. TRANSFORMING RESULTS (thenApply) ===\n");
        transformingResults();

        System.out.println("\n=== 3. CONSUMING RESULTS (thenAccept) ===\n");
        consumingResults();

        System.out.println("\n=== 4. CHAINING ASYNC OPERATIONS (thenCompose) ===\n");
        chainingAsyncOperations();

        System.out.println("\n=== 5. COMBINING FUTURES (thenCombine) ===\n");
        combiningFutures();

        System.out.println("\n=== 6. WAITING FOR MULTIPLE (allOf, anyOf) ===\n");
        multipleOperations();

        System.out.println("\n=== 7. ERROR HANDLING ===\n");
        errorHandling();

        System.out.println("\n=== 8. ASYNC EXECUTION ===\n");
        asyncExecution();

        System.out.println("\n=== 9. PRACTICAL EXAMPLE: API CALLS ===\n");
        practicalExample();

        System.out.println("\n=== 10. TIMEOUTS ===\n");
        timeoutExample();

        // Give async operations time to complete
        Thread.sleep(3000);
    }

    // 1. BASIC CREATION
    static void basicCreation() throws Exception {
        // Already completed future
        CompletableFuture<String> completedFuture =
                CompletableFuture.completedFuture("Hello");
        System.out.println("Completed future: " + completedFuture.get());

        // Create and complete manually
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("World");
        System.out.println("Manual completion: " + future.get());

        // Run async task (returns CompletableFuture<Void>)
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("Running async task in: " + Thread.currentThread().getName());
        });
        runAsync.get(); // Wait for completion

        // Supply async value (returns CompletableFuture<T>)
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            return "Async result from " + Thread.currentThread().getName();
        });
        System.out.println("Supply async: " + supplyAsync.get());
    }

    // 2. TRANSFORMING RESULTS - thenApply
    static void transformingResults() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5)
                .thenApply(num -> num * 2)           // Transform: 5 -> 10
                .thenApply(num -> num + 3);          // Transform: 10 -> 13

        System.out.println("Result after transformations: " + future.get());

        // More complex transformation
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> "john_doe")
                .thenApply(String::toUpperCase)
                .thenApply(name -> "User: " + name);

        System.out.println(userFuture.get());
    }

    // 3. CONSUMING RESULTS - thenAccept (no return value)
    static void consumingResults() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "Hello World")
                .thenAccept(result -> {
                    System.out.println("Consuming result: " + result);
                    // Can perform side effects here (logging, saving to DB, etc.)
                });

        future.get(); // Wait for completion

        // thenRun - doesn't receive the result, just runs after completion
        CompletableFuture.supplyAsync(() -> 42)
                .thenRun(() -> System.out.println("Completed, but don't know the result"))
                .get();
    }

    // 4. CHAINING ASYNC OPERATIONS - thenCompose
    static void chainingAsyncOperations() throws Exception {
        // Use thenCompose when the next operation returns a CompletableFuture
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user ID...");
            return "user123";
        }).thenCompose(userId -> {
            // This returns another CompletableFuture
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("Fetching user details for: " + userId);
                return "User Details: " + userId + " - John Doe";
            });
        });

        System.out.println(result.get());
    }

    // 5. COMBINING FUTURES - thenCombine
    static void combiningFutures() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return 50;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return 30;
        });

        // Combine results from both futures
        CompletableFuture<Integer> combined = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("Combining: " + result1 + " + " + result2);
            return result1 + result2;
        });

        System.out.println("Combined result: " + combined.get());

        // thenAcceptBoth - combine but don't return anything
        future1.thenAcceptBoth(future2, (r1, r2) -> {
            System.out.println("Both completed: " + r1 + ", " + r2);
        }).get();
    }

    // 6. WAITING FOR MULTIPLE FUTURES
    static void multipleOperations() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return "Result 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "Result 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(150);
            return "Result 3";
        });

        // allOf - wait for all to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        allFutures.get(); // Wait for all
        System.out.println("All completed!");
        System.out.println(future1.get() + ", " + future2.get() + ", " + future3.get());

        // Collect all results
        List<CompletableFuture<String>> futures = Arrays.asList(future1, future2, future3);
        CompletableFuture<List<String>> allResults = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        System.out.println("All results: " + allResults.get());

        // anyOf - returns when any completes
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> { sleep(300); return "Slow"; }),
                CompletableFuture.supplyAsync(() -> { sleep(100); return "Fast"; }),
                CompletableFuture.supplyAsync(() -> { sleep(200); return "Medium"; })
        );

        System.out.println("First to complete: " + anyFuture.get());
    }

    // 7. ERROR HANDLING
    static void errorHandling() throws Exception {
        // exceptionally - handle errors
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Something went wrong!");
            }
            return "Success";
        }).exceptionally(ex -> {
            System.out.println("Error occurred: " + ex.getMessage());
            return "Default value";
        });

        System.out.println("Result with error handling: " + future.get());

        // handle - process both success and error
        CompletableFuture<Object> handleFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Error!");
        }).handle((result, ex) -> {
            if (ex != null) {
                return "Recovered from: " + ex.getMessage();
            }
            return result;
        });

        System.out.println(handleFuture.get());

        // whenComplete - perform action on completion (doesn't transform result)
        CompletableFuture.supplyAsync(() -> "Data")
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("Failed: " + ex.getMessage());
                    } else {
                        System.out.println("Succeeded with: " + result);
                    }
                }).get();
    }

    // 8. ASYNC EXECUTION WITH CUSTOM EXECUTOR
    static void asyncExecution() throws Exception {
        // Create custom thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Running in: " + Thread.currentThread().getName());
            return "Result from custom executor";
        }, executor); // Pass custom executor

        System.out.println(future.get());

        // thenApplyAsync - next stage also runs async
        CompletableFuture<String> asyncChain = CompletableFuture.supplyAsync(() -> "Start", executor)
                .thenApplyAsync(s -> {
                    System.out.println("Async transformation in: " + Thread.currentThread().getName());
                    return s + " -> Middle";
                }, executor)
                .thenApplyAsync(s -> s + " -> End", executor);

        System.out.println(asyncChain.get());

        executor.shutdown();
    }

    // 9. PRACTICAL EXAMPLE: Simulated API Calls
    static void practicalExample() throws Exception {
        System.out.println("Fetching user profile...\n");

        CompletableFuture<String> userFuture = fetchUser("user123");
        CompletableFuture<List<String>> ordersFuture = fetchOrders("user123");
        CompletableFuture<String> recommendationsFuture = fetchRecommendations("user123");

        // Combine all results
        CompletableFuture<String> profileFuture = userFuture
                .thenCombine(ordersFuture, (user, orders) ->
                        user + "\nOrders: " + orders)
                .thenCombine(recommendationsFuture, (profile, recommendations) ->
                        profile + "\nRecommendations: " + recommendations);

        System.out.println(profileFuture.get());
    }

    static CompletableFuture<String> fetchUser(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "User{id=" + userId + ", name='John Doe'}";
        });
    }

    static CompletableFuture<List<String>> fetchOrders(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return Arrays.asList("Order1", "Order2", "Order3");
        });
    }

    static CompletableFuture<String> fetchRecommendations(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(250);
            return "Product A, Product B";
        });
    }

    // 10. TIMEOUTS (Java 9+)
    static void timeoutExample() throws Exception {
        // Timeout after 1 second
        try {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                sleep(2000);
                return "Slow result";
            }).orTimeout(1, TimeUnit.SECONDS);

            future.get();
        } catch (ExecutionException e) {
            System.out.println("Timed out: " + e.getCause().getClass().getSimpleName());
        }

        // Provide default value on timeout
        CompletableFuture<String> futureWithDefault = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Slow result";
        }).completeOnTimeout("Default value", 1, TimeUnit.SECONDS);

        System.out.println("Result with timeout: " + futureWithDefault.get());
    }

    // Helper method
    static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}