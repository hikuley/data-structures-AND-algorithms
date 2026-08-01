package org.dsa.interviewSamples;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ExecutorServiceGuide {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. BASIC EXECUTOR TYPES ===\n");
        basicExecutorTypes();

        System.out.println("\n=== 2. SUBMITTING TASKS (Runnable vs Callable) ===\n");
        submittingTasks();

        System.out.println("\n=== 3. GETTING RESULTS WITH FUTURE ===\n");
        gettingResults();

        System.out.println("\n=== 4. INVOKEALL - MULTIPLE TASKS ===\n");
        invokeAllExample();

        System.out.println("\n=== 5. INVOKEANY - FIRST COMPLETED ===\n");
        invokeAnyExample();

        System.out.println("\n=== 6. SCHEDULED EXECUTOR ===\n");
        scheduledExecutorExample();

        System.out.println("\n=== 7. THREAD POOL CONFIGURATION ===\n");
        threadPoolConfiguration();

        System.out.println("\n=== 8. PROPER SHUTDOWN ===\n");
        properShutdown();

        System.out.println("\n=== 9. EXCEPTION HANDLING ===\n");
        exceptionHandling();

        System.out.println("\n=== 10. PRACTICAL EXAMPLE: PARALLEL PROCESSING ===\n");
        practicalExample();
    }

    // 1. BASIC EXECUTOR TYPES
    static void basicExecutorTypes() throws Exception {
        // Fixed thread pool - fixed number of threads
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        System.out.println("Fixed Thread Pool (3 threads):");
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            fixedPool.submit(() ->
                    System.out.println("Task " + taskId + " in " + Thread.currentThread().getName())
            );
        }
        fixedPool.shutdown();
        fixedPool.awaitTermination(2, TimeUnit.SECONDS);

        // Cached thread pool - creates threads as needed, reuses if available
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        System.out.println("\nCached Thread Pool:");
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            cachedPool.submit(() ->
                    System.out.println("Task " + taskId + " in " + Thread.currentThread().getName())
            );
        }
        cachedPool.shutdown();
        cachedPool.awaitTermination(2, TimeUnit.SECONDS);

        // Single thread executor - only one thread
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        System.out.println("\nSingle Thread Executor:");
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            singleThread.submit(() ->
                    System.out.println("Task " + taskId + " in " + Thread.currentThread().getName())
            );
        }
        singleThread.shutdown();
        singleThread.awaitTermination(2, TimeUnit.SECONDS);
    }

    // 2. SUBMITTING TASKS - Runnable vs Callable
    static void submittingTasks() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Runnable - no return value
        System.out.println("Submitting Runnable (no return):");
        Future<?> runnableFuture = executor.submit(() -> {
            System.out.println("  Runnable task executing...");
            sleep(100);
        });
        runnableFuture.get(); // Wait for completion
        System.out.println("  Runnable completed\n");

        // Callable - returns a value
        System.out.println("Submitting Callable (with return):");
        Callable<String> callableTask = () -> {
            System.out.println("  Callable task executing...");
            sleep(100);
            return "Result from callable";
        };

        Future<String> callableFuture = executor.submit(callableTask);
        String result = callableFuture.get(); // Blocks until result is ready
        System.out.println("  Result: " + result);

        // execute() - fire and forget (only for Runnable)
        System.out.println("\nUsing execute() - fire and forget:");
        executor.execute(() -> System.out.println("  Execute task running"));

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }

    // 3. GETTING RESULTS WITH FUTURE
    static void gettingResults() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Integer> future = executor.submit(() -> {
            System.out.println("Computing...");
            sleep(1000);
            return 42;
        });

        System.out.println("Task submitted, doing other work...");

        // Check if done (non-blocking)
        System.out.println("Is done? " + future.isDone());

        // Wait with timeout
        try {
            Integer result = future.get(500, TimeUnit.MILLISECONDS);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for result");
        }

        // Wait without timeout
        Integer result = future.get(); // Blocks until ready
        System.out.println("Final result: " + result);
        System.out.println("Is done now? " + future.isDone());

        // Cancel a task
        Future<Integer> cancelFuture = executor.submit(() -> {
            sleep(2000);
            return 100;
        });

        boolean cancelled = cancelFuture.cancel(true); // true = interrupt if running
        System.out.println("Cancelled: " + cancelled);
        System.out.println("Is cancelled? " + cancelFuture.isCancelled());

        executor.shutdown();
    }

    // 4. INVOKEALL - Submit multiple tasks and get all results
    static void invokeAllExample() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create multiple Callable tasks
        List<Callable<String>> tasks = Arrays.asList(
                () -> { sleep(200); return "Task 1 result"; },
                () -> { sleep(300); return "Task 2 result"; },
                () -> { sleep(100); return "Task 3 result"; }
        );

        System.out.println("Submitting all tasks...");
        long start = System.currentTimeMillis();

        // invokeAll waits for ALL tasks to complete
        List<Future<String>> futures = executor.invokeAll(tasks);

        long duration = System.currentTimeMillis() - start;
        System.out.println("All tasks completed in " + duration + "ms\n");

        // Get all results
        for (int i = 0; i < futures.size(); i++) {
            System.out.println("Result " + (i+1) + ": " + futures.get(i).get());
        }

        // invokeAll with timeout
        System.out.println("\nWith timeout (200ms):");
        List<Future<String>> timedFutures = executor.invokeAll(tasks, 200, TimeUnit.MILLISECONDS);

        for (int i = 0; i < timedFutures.size(); i++) {
            try {
                System.out.println("Result " + (i+1) + ": " + timedFutures.get(i).get());
            } catch (CancellationException e) {
                System.out.println("Result " + (i+1) + ": Cancelled (timeout)");
            }
        }

        executor.shutdown();
    }

    // 5. INVOKEANY - Returns result of first completed task
    static void invokeAnyExample() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = Arrays.asList(
                () -> { sleep(300); return "Slow task"; },
                () -> { sleep(100); return "Fast task"; },
                () -> { sleep(200); return "Medium task"; }
        );

        System.out.println("Submitting tasks, will return first completed...");
        long start = System.currentTimeMillis();

        // invokeAny returns as soon as ONE task completes
        String result = executor.invokeAny(tasks);

        long duration = System.currentTimeMillis() - start;
        System.out.println("First result: " + result + " (in " + duration + "ms)");

        executor.shutdown();
    }

    // 6. SCHEDULED EXECUTOR - For delayed and periodic tasks
    static void scheduledExecutorExample() throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Schedule one-time task with delay
        System.out.println("Scheduling task to run after 1 second...");
        ScheduledFuture<String> delayed = scheduler.schedule(() -> {
            return "Delayed task executed at " + System.currentTimeMillis();
        }, 1, TimeUnit.SECONDS);

        String result = delayed.get();
        System.out.println(result);

        // Schedule periodic task (fixed rate)
        System.out.println("\nScheduling periodic task (every 500ms):");
        ScheduledFuture<?> periodic = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("  Periodic task at " + System.currentTimeMillis());
        }, 0, 500, TimeUnit.MILLISECONDS); // initial delay, period, unit

        Thread.sleep(2000); // Let it run for 2 seconds
        periodic.cancel(false);

        // Schedule with fixed delay between executions
        System.out.println("\nScheduling with fixed delay (500ms between executions):");
        ScheduledFuture<?> fixedDelay = scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("  Task start at " + System.currentTimeMillis());
            sleep(200); // Task takes 200ms
            System.out.println("  Task end");
        }, 0, 500, TimeUnit.MILLISECONDS); // 500ms after previous task ENDS

        Thread.sleep(2000);
        fixedDelay.cancel(false);

        scheduler.shutdown();
    }

    // 7. THREAD POOL CONFIGURATION
    static void threadPoolConfiguration() throws Exception {
        // Custom ThreadPoolExecutor
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 60;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);

        ThreadPoolExecutor customExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue
        );

        System.out.println("Custom ThreadPoolExecutor:");
        System.out.println("  Core pool size: " + customExecutor.getCorePoolSize());
        System.out.println("  Max pool size: " + customExecutor.getMaximumPoolSize());
        System.out.println("  Active threads: " + customExecutor.getActiveCount());

        // Submit tasks
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            customExecutor.submit(() -> {
                System.out.println("  Task " + taskId + " executing");
                sleep(500);
            });
        }

        Thread.sleep(100);
        System.out.println("  Active threads now: " + customExecutor.getActiveCount());
        System.out.println("  Queue size: " + customExecutor.getQueue().size());

        customExecutor.shutdown();
        customExecutor.awaitTermination(5, TimeUnit.SECONDS);
    }

    // 8. PROPER SHUTDOWN
    static void properShutdown() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit some tasks
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running");
                sleep(1000);
                System.out.println("Task " + taskId + " completed");
            });
        }

        // Proper shutdown pattern
        System.out.println("\nInitiating shutdown...");
        executor.shutdown(); // Prevents new tasks, allows existing to complete

        try {
            // Wait for tasks to complete
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                System.out.println("Tasks didn't finish in time, forcing shutdown...");
                List<Runnable> droppedTasks = executor.shutdownNow(); // Force shutdown
                System.out.println("Dropped " + droppedTasks.size() + " tasks");

                // Wait again after forced shutdown
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.out.println("Executor didn't terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Executor shutdown complete");
    }

    // 9. EXCEPTION HANDLING
    static void exceptionHandling() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Exception in Callable - caught when calling get()
        Future<Integer> future = executor.submit(() -> {
            System.out.println("Task throwing exception...");
            if (true) throw new RuntimeException("Something went wrong!");
            return 42;
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("Caught exception: " + e.getCause().getMessage());
        }

        // Exception in Runnable submitted with submit() - silent unless you call get()
        Future<?> runnableFuture = executor.submit(() -> {
            throw new RuntimeException("Runnable exception");
        });

        try {
            runnableFuture.get();
        } catch (ExecutionException e) {
            System.out.println("Caught runnable exception: " + e.getCause().getMessage());
        }

        // Exception in Runnable with execute() - goes to uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            System.out.println("Uncaught exception in " + thread.getName() + ": " + ex.getMessage());
        });

        executor.execute(() -> {
            throw new RuntimeException("Execute exception");
        });

        Thread.sleep(100);
        executor.shutdown();
    }

    // 10. PRACTICAL EXAMPLE - Parallel data processing
    static void practicalExample() throws Exception {
        // Simulate processing large dataset
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            data.add(i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Split data into chunks
        int chunkSize = 25;
        List<List<Integer>> chunks = new ArrayList<>();
        for (int i = 0; i < data.size(); i += chunkSize) {
            chunks.add(data.subList(i, Math.min(i + chunkSize, data.size())));
        }

        System.out.println("Processing " + data.size() + " items in " + chunks.size() + " chunks");

        // Create tasks for each chunk
        List<Callable<Long>> tasks = chunks.stream()
                .map(chunk -> (Callable<Long>) () -> {
                    long sum = 0;
                    for (Integer num : chunk) {
                        sum += num;
                        sleep(10); // Simulate processing time
                    }
                    System.out.println("  Chunk processed by " + Thread.currentThread().getName() +
                            ", sum = " + sum);
                    return sum;
                })
                .collect(Collectors.toList());

        // Execute all tasks
        long start = System.currentTimeMillis();
        List<Future<Long>> futures = executor.invokeAll(tasks);

        // Collect results
        long totalSum = 0;
        for (Future<Long> future : futures) {
            totalSum += future.get();
        }

        long duration = System.currentTimeMillis() - start;

        System.out.println("\nTotal sum: " + totalSum);
        System.out.println("Processed in: " + duration + "ms");
        System.out.println("Expected sum: " + (100 * 101 / 2)); // Sum formula: n(n+1)/2

        executor.shutdown();
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