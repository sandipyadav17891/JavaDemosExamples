package ThreadExample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {
	/**
	 * submit()
	 * execute()
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	
	public static void main() throws InterruptedException, ExecutionException {
		
		ExecutorService es= Executors.newFixedThreadPool(2);
		es.execute(() ->{
			System.out.println("inside ExecutorService Thread1"+" Thread Name:"+Thread.currentThread().getName() );
		});
		es.submit(() ->{
			System.out.println("inside ExecutorService Thread2"+" Thread Name:"+Thread.currentThread().getName() );
		});
		Future<String> result=es.submit(() ->{
			System.out.println("inside ExecutorService Thread3"+" Thread Name:"+Thread.currentThread().getName() );
			
		},  "Success");
		System.out.println(result.get());
		
		
	}
}
