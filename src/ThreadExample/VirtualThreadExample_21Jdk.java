package ThreadExample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VirtualThreadExample_21Jdk {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		
		// Thread.startVirtualThread(() ->{});
		// Executors.newVirtualThreadPerTaskExecutor().submit(() ->{});
		
		//Creating a virtual thread
		Thread vt=Thread.startVirtualThread(() ->{
			for(int i=0;i<100;i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  //sleep for 1 second
				System.out.println("Virtual Thread :"+i);
			}
		});
		//vt.start();
		vt.join();  //waits for the thread to complete
		
		Future<String> vt2= (Future<String>) Executors.newVirtualThreadPerTaskExecutor().submit(() ->{
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  //sleep for 1 second
			System.out.println("Virtual Thread2");
			return "Virtual Thread2";
		});
		((Thread) vt2).join();  //waits for the thread to complete
		((Future<String>) vt2).get();  //waits for the thread to complete and returns the result
		((Future<String>) vt2).get(1000, TimeUnit.MILLISECONDS);  //waits for the thread to complete and returns the result
	}

}
