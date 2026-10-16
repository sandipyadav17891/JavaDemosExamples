package ThreadExample;

/** 
 * Callable is interface return values<T> using call Method
 * Runnable isn interface no return values using run method 
 */
public class ThreadExampleMain {

	public static void main(String a[]) {
		
		Thread t1=new Thread(()->{			
			System.out.println("In Thread 1");
			try {
				Thread.sleep(2000);
				System.out.println("Post Sleep");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Thread t2=new Thread(()->{			
			System.out.println("In Thread 2");
		});
		
		t1.start();
		t2.start();		
		
	}
}
