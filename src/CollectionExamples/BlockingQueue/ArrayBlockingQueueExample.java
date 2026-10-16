package CollectionExamples.BlockingQueue;
	
	import java.util.concurrent.*;

	public class ArrayBlockingQueueExample {

	    private static final BlockingQueue<String> queue =
	            new ArrayBlockingQueue<>(5); // fixed capacity

	    public static void main(String[] args) {

	        // Producer threads
	        Runnable producer = () -> {
	            try {
	                for (int i = 1; i <= 10; i++) {
	                    String log = Thread.currentThread().getName() + " - Log " + i;
	                    queue.put(log); // blocks if full
	                    System.out.println("Produced: " + log);
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        };

	        // Consumer thread
	        Runnable consumer = () -> {
	            try {
	                while (true) {
	                    String log = queue.take(); // blocks if empty
	                    System.out.println("Consumed: " + log);
	                    Thread.sleep(300); // simulate slow disk write
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        };

	        new Thread(producer, "Producer-1").start();
	        new Thread(producer, "Producer-2").start();
	        new Thread(consumer, "Consumer").start();
	    }
	}


