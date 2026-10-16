package DemoExample;

import java.util.concurrent.CompletableFuture;

public class samplemain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		Thread mainThread = Thread.currentThread();
		        System.out.println("Main thread: " + mainThread.getName());

		        Thread child = new Thread(() -> {
		            System.out.println("Child thread running...");
		        });

		        Thread thirdthread=
		        		Thread.startVirtualThread(()->{
		        	System.out.println("Hello from virtual thread!");
		        });
		        
		        child.start();
		        Thread.sleep(100); // give virtual thread time

		        
		    }
		

		
		
		
		
		
	}


