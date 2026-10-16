package ThreadExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		//CompletableFuture.runAsync() is of type void whereas 
		//CompletableFuture.supplyAsync() is of type supplier i.e return the values

		CompletableFuture ct=CompletableFuture.runAsync(() -> {
			System.out.println("CT Thread");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		CompletableFuture<String> ctt=CompletableFuture.supplyAsync(() -> {
			System.out.println("CTT Thread");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "CTT Thread2";

		});
		
		CompletableFuture<String> cff=CompletableFuture.supplyAsync(() -> {
			return "sandip";
		});
		
		//waits for all 
		//CompletableFuture<Void> 
		//Void finalres=
			//	CompletableFuture.allOf(ct,ctt).get(100, TimeUnit.MILLISECONDS);
		//finalres.join();  //Blocks until all complete
			System.out.println(ct.get() +" "+ctt.get() );
			ctt.get(100, TimeUnit.MILLISECONDS);
	}

}
