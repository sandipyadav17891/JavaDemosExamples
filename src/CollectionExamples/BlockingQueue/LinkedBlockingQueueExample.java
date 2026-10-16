package CollectionExamples.BlockingQueue;


import java.util.concurrent.*;

	public class LinkedBlockingQueueExample {

	    private static final BlockingQueue<String> queue =
	            new LinkedBlockingQueue<>(100); // optional bound

	    public static void main(String[] args) {

	        Runnable producer = () -> {
	            try {
	                for (int i = 1; i <= 10; i++) {
	                    String log = Thread.currentThread().getName() + " - Log " + i;
	                    queue.put(log); // blocks only if full
	                    System.out.println("Produced: " + log);
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        };

	        Runnable consumer = () -> {
	            try {
	                while (true) {
	                    String log = queue.take();
	                    System.out.println("Consumed: " + log);
	                    Thread.sleep(300);
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


