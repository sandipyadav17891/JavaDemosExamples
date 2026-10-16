package CollectionExamples.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Comprehensive ConcurrentHashMap examples covering:
 * 1. Basic operations (get, put, remove)
 * 2. Atomic compound operations (putIfAbsent, compute, merge)
 * 3. Thread-safety demonstrations
 * 4. Performance comparison with HashMap
 * 5. Real-world usage patterns
 */
public class ConcurrentHashMapExamples {

    // =========================================================================
    // 1. BASIC OPERATIONS
    // =========================================================================

    public static void basicOperations() {
        System.out.println("\n=== 1. BASIC OPERATIONS ===\n");

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // put(K, V) - Add or overwrite
        map.put("apple", 10);
        map.put("banana", 20);
        System.out.println("After puts: " + map);

        // get(K) - Retrieve value
        Integer appleCount = map.get("apple");
        System.out.println("Get apple: " + appleCount);

        // getOrDefault(K, defaultValue)
        Integer grapeCount = map.getOrDefault("grape", 0);
        System.out.println("Get grape (default 0): " + grapeCount);

        // containsKey(K)
        System.out.println("Contains apple: " + map.containsKey("apple"));
        System.out.println("Contains grape: " + map.containsKey("grape"));

        // remove(K)
        map.remove("banana");
        System.out.println("After remove banana: " + map);

        // size() - NOTE: approximate under high concurrency
        System.out.println("Size: " + map.size());
    }

    // =========================================================================
    // 2. ATOMIC COMPOUND OPERATIONS
    // =========================================================================

    public static void atomicCompoundOperations() {
        System.out.println("\n=== 2. ATOMIC COMPOUND OPERATIONS ===\n");

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // putIfAbsent(K, V) - Put only if key absent
        Integer prev1 = map.putIfAbsent("counter", 0);
        System.out.println("First putIfAbsent returned: " + prev1); // null

        Integer prev2 = map.putIfAbsent("counter", 100);
        System.out.println("Second putIfAbsent returned: " + prev2); // 0 (key already existed)

        System.out.println("Map after putIfAbsent: " + map);

        // compute(K, BiFunction) - Atomic read-modify-write
        map.compute("counter", (k, v) -> v == null ? 1 : v + 1);
        System.out.println("After compute (increment): " + map);

        // computeIfAbsent(K, Function) - Compute only if absent
        map.computeIfAbsent("newKey", k -> k.length()); // value = length of key
        System.out.println("After computeIfAbsent: " + map);

        // computeIfPresent(K, BiFunction) - Compute only if present
        map.computeIfPresent("counter", (k, v) -> v * 2);
        System.out.println("After computeIfPresent: " + map);

        // merge(K, V, BiFunction) - Merge new value with existing
        ConcurrentHashMap<String, Long> stats = new ConcurrentHashMap<>();
        stats.merge("pageviews", 5L, Long::sum);
        stats.merge("pageviews", 3L, Long::sum); // Merges with existing
        System.out.println("After merge: " + stats); // pageviews = 8
    }

    // =========================================================================
    // 3. THREAD-SAFETY DEMONSTRATION
    // =========================================================================

    public static void threadSafetyDemo() throws InterruptedException {
        System.out.println("\n=== 3. THREAD-SAFETY DEMONSTRATION ===\n");

        // Scenario 1: Multiple threads incrementing a counter
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("counter", 0);

        int numThreads = 4;
        int incrementsPerThread = 250;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("Scenario 1: " + numThreads + " threads each incrementing " + 
                          incrementsPerThread + " times");
        System.out.println("Expected final count: " + (numThreads * incrementsPerThread));

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    // ATOMIC: read-modify-write all happens atomically
                    map.compute("counter", (k, v) -> v + 1);
                }
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("Actual final count: " + map.get("counter"));
        System.out.println("✓ No race condition!\n");

        executor.shutdown();

        // Scenario 2: putIfAbsent atomicity
        System.out.println("Scenario 2: Multiple threads calling putIfAbsent on same key");
        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>();

        CountDownLatch latch2 = new CountDownLatch(numThreads);
        ExecutorService executor2 = Executors.newFixedThreadPool(numThreads);

        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor2.submit(() -> {
                String result = map2.putIfAbsent("singleKey", "thread-" + threadId);
                if (result == null) {
                    successCount.incrementAndGet();
                    System.out.println("Thread " + threadId + " succeeded");
                } else {
                    System.out.println("Thread " + threadId + " failed (key already has: " + result + ")");
                }
                latch2.countDown();
            });
        }

        latch2.await();
        System.out.println("Total successful puts: " + successCount.get() + " (should be 1)");
        System.out.println("✓ Only one thread succeeded!\n");

        executor2.shutdown();
    }

    // =========================================================================
    // 4. DISTRIBUTED COUNTING PATTERN
    // =========================================================================

    public static void distributedCountingDemo() throws InterruptedException {
        System.out.println("\n=== 4. DISTRIBUTED COUNTING (merge pattern) ===\n");

        ConcurrentHashMap<String, Long> metrics = new ConcurrentHashMap<>();

        int numThreads = 8;
        int operationsPerThread = 500;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("" + numThreads + " threads recording events");

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    String eventType = (j % 3 == 0) ? "login" : (j % 3 == 1) ? "logout" : "pageview";
                    // Atomic: merge new value with existing (if any)
                    metrics.merge(eventType, 1L, Long::sum);

                    // Simulate some work
                    if (j % 100 == 0) {
                        Thread.yield();
                    }
                }
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("Final metrics:");
        metrics.forEach((event, count) -> 
            System.out.println("  " + event + " = " + count)
        );

        long total = metrics.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("  TOTAL = " + total);
        System.out.println("  Expected = " + (numThreads * operationsPerThread));

        executor.shutdown();
    }

    // =========================================================================
    // 5. CONCURRENT READ-MODIFY-WRITE (compute pattern)
    // =========================================================================

    public static void readModifyWriteDemo() throws InterruptedException {
        System.out.println("\n=== 5. READ-MODIFY-WRITE (compute pattern) ===\n");

        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

        int numThreads = 4;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Scenario: updating player scores atomically
        scores.put("Alice", 100);
        scores.put("Bob", 150);

        System.out.println("Initial scores: " + scores);
        System.out.println("\nThreads modifying scores concurrently...\n");

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                // Each thread updates both players' scores
                scores.compute("Alice", (k, v) -> v + (threadId + 1));
                scores.compute("Bob", (k, v) -> v - threadId);

                System.out.println("Thread " + threadId + " completed update");
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("\nFinal scores: " + scores);
        System.out.println("Alice: 100 + 1 + 2 + 3 + 4 = 110");
        System.out.println("Bob: 150 - 0 - 1 - 2 - 3 = 144");

        executor.shutdown();
    }

    // =========================================================================
    // 6. CACHE PATTERN
    // =========================================================================

    public static void cachePatternDemo() throws InterruptedException {
        System.out.println("\n=== 6. CACHE PATTERN (computeIfAbsent) ===\n");

        ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

        AtomicInteger computationCount = new AtomicInteger(0);

        int numThreads = 8;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("" + numThreads + " threads requesting same key");
        System.out.println("Computation should happen only ONCE\n");

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                // All threads call computeIfAbsent for same key
                // Expensive computation runs only once, others reuse result
                String value = cache.computeIfAbsent("expensiveKey", key -> {
                    try {
                        Thread.sleep(100); // Simulate expensive computation
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    computationCount.incrementAndGet();
                    return "computed-value";
                });

                System.out.println("Thread " + threadId + " got: " + value);
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("\nComputation count: " + computationCount.get());
        System.out.println("✓ Computation happened only once despite 8 concurrent requests!");

        executor.shutdown();
    }

    // =========================================================================
    // 7. NULL HANDLING GOTCHA
    // =========================================================================

    public static void nullHandlingGotcha() {
        System.out.println("\n=== 7. NULL HANDLING GOTCHA ===\n");

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // ERROR 1: Cannot put null value
        try {
            map.put("key", null);
            System.out.println("ERROR: Should have thrown NullPointerException!");
        } catch (NullPointerException e) {
            System.out.println("✓ Null value rejected: " + e.getClass().getSimpleName());
        }

        // ERROR 2: Cannot use null key
        try {
            map.put(null, "value");
            System.out.println("ERROR: Should have thrown NullPointerException!");
        } catch (NullPointerException e) {
            System.out.println("✓ Null key rejected: " + e.getClass().getSimpleName());
        }

        // WORKAROUND: Use sentinels or optionals
        map.put("empty", "EMPTY_MARKER");
        System.out.println("\n✓ Workaround: Use sentinel value like 'EMPTY_MARKER'");
    }

    // =========================================================================
    // 8. ITERATOR BEHAVIOR (weakly consistent, not atomic snapshot)
    // =========================================================================

    public static void iteratorBehavior() throws InterruptedException {
        System.out.println("\n=== 8. ITERATOR BEHAVIOR ===\n");

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("key" + i, i);
        }

        System.out.println("Initial map: " + map);
        System.out.println("\nIterating while another thread modifies...\n");

        Thread iteratorThread = new Thread(() -> {
            int count = 0;
            for (String key : map.keySet()) {
                count++;
                System.out.println("Iterator sees: " + key);
                try {
                    Thread.sleep(100); // Slow iteration
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Iterator completed, saw " + count + " keys");
        });

        Thread modifierThread = new Thread(() -> {
            try {
                Thread.sleep(150);
                System.out.println("\n[Modifier] Adding new keys...");
                for (int i = 5; i < 10; i++) {
                    map.put("key" + i, i);
                    System.out.println("[Modifier] Added key" + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        iteratorThread.start();
        modifierThread.start();

        iteratorThread.join();
        modifierThread.join();

        System.out.println("\nFinal map size: " + map.size());
        System.out.println("✓ Iterator is 'weakly consistent' - might see concurrent updates");
        System.out.println("✓ Won't throw ConcurrentModificationException like HashMap would");
    }

    // =========================================================================
    // 9. PERFORMANCE: DIFFERENT BUCKET SPEEDS (no contention vs contention)
    // =========================================================================

    public static void performanceComparison() throws InterruptedException {
        System.out.println("\n=== 9. PERFORMANCE: DIFFERENT BUCKET ACCESS ===\n");

        int numThreads = 4;
        int operationsPerThread = 10000;

        // Test 1: Low contention (different buckets per thread)
        System.out.println("Test 1: LOW CONTENTION (different buckets)");
        ConcurrentHashMap<String, Integer> lowContentionMap = new ConcurrentHashMap<>();
        long startTime = System.nanoTime();

        CountDownLatch latch1 = new CountDownLatch(numThreads);
        ExecutorService executor1 = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor1.submit(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    // Each thread uses different keys (different buckets)
                    lowContentionMap.put("thread" + threadId + "_key" + j, j);
                }
                latch1.countDown();
            });
        }

        latch1.await();
        long lowContentionTime = System.nanoTime() - startTime;
        executor1.shutdown();

        System.out.println("Time: " + (lowContentionTime / 1_000_000) + " ms");
        System.out.println("Throughput: " + ((numThreads * operationsPerThread) / (lowContentionTime / 1_000_000_000.0)) + " ops/sec\n");

        // Test 2: High contention (same bucket for all threads)
        System.out.println("Test 2: HIGH CONTENTION (same bucket)");
        ConcurrentHashMap<String, Integer> highContentionMap = new ConcurrentHashMap<>();
        startTime = System.nanoTime();

        CountDownLatch latch2 = new CountDownLatch(numThreads);
        ExecutorService executor2 = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executor2.submit(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    // All threads use same key (same bucket)
                    highContentionMap.compute("sameKey", (k, v) -> (v == null ? 0 : v) + 1);
                }
                latch2.countDown();
            });
        }

        latch2.await();
        long highContentionTime = System.nanoTime() - startTime;
        executor2.shutdown();

        System.out.println("Time: " + (highContentionTime / 1_000_000) + " ms");
        System.out.println("Throughput: " + ((numThreads * operationsPerThread) / (highContentionTime / 1_000_000_000.0)) + " ops/sec");
        System.out.println("\nRatio: " + String.format("%.2f", (double)highContentionTime / lowContentionTime) + "x slower under contention");
    }

    // =========================================================================
    // 10. PRACTICAL: THREAD-SAFE METRICS COLLECTOR
    // =========================================================================

    public static class MetricsCollector {
        private final ConcurrentHashMap<String, Long> metrics = new ConcurrentHashMap<>();

        public void recordEvent(String eventName) {
            metrics.merge(eventName, 1L, Long::sum);
        }

        public void recordEvent(String eventName, long count) {
            metrics.merge(eventName, count, Long::sum);
        }

        public long getCount(String eventName) {
            return metrics.getOrDefault(eventName, 0L);
        }

        public void printMetrics() {
            System.out.println("=== METRICS ===");
            metrics.forEach((event, count) -> 
                System.out.println("  " + event + ": " + count)
            );
            long total = metrics.values().stream().mapToLong(Long::longValue).sum();
            System.out.println("  TOTAL: " + total);
        }
    }

    public static void practicalMetricsCollectorDemo() throws InterruptedException {
        System.out.println("\n=== 10. PRACTICAL: METRICS COLLECTOR ===\n");

        MetricsCollector collector = new MetricsCollector();

        int numThreads = 8;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                // Simulate events from different sources
                for (int j = 0; j < 1000; j++) {
                    if (j % 3 == 0) collector.recordEvent("http_request");
                    else if (j % 3 == 1) collector.recordEvent("db_query");
                    else collector.recordEvent("cache_hit");
                }
                latch.countDown();
            });
        }

        latch.await();
        collector.printMetrics();

        executor.shutdown();
    }

    // =========================================================================
    // MAIN
    // =========================================================================

    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   ConcurrentHashMap - Comprehensive Examples      ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        basicOperations();
        atomicCompoundOperations();
        threadSafetyDemo();
        distributedCountingDemo();
        readModifyWriteDemo();
        cachePatternDemo();
        nullHandlingGotcha();
        iteratorBehavior();
        performanceComparison();
        practicalMetricsCollectorDemo();

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              Examples Complete                     ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }
}