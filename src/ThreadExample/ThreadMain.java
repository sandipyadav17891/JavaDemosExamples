package ThreadExample;

public class ThreadMain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		Thread t1=new Thread(() -> {
			
			for(int i=0;i<500;i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Thread1 :"+i);
			}
		});
	    t1.start();   //Thread.start() used to start the thread
		
		//ThreadExample2 t2=new ThreadExample2();
	    //t2.run();    //Thread.run() used to call and run the thread
	    
        Thread thirdthread=
        		Thread.startVirtualThread(()->{
        	System.out.println("Hello from virtual thread!");
        });
        
		Thread mainThread = Thread.currentThread();
        System.out.println("Main thread: " + mainThread.getName());

        Thread child = new Thread(() -> {
            System.out.println("Child thread running...");
        });


        child.start();
       // Thread.sleep(100); // give virtual thread time
	    
	   
	}

}
