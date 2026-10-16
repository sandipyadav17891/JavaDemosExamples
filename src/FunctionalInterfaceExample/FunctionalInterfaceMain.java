package FunctionalInterfaceExample;

import java.util.concurrent.Callable;

//FunctionalInterface is way of passing Behaviour as paramter to calling method or class. 
//only 1 ABSTRACT Method becoz Because Java needs a single, clear target for a lambda expression.


public class FunctionalInterfaceMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		FuntionalInterface fi= (a,b) -> { 
			return a*b;
		};
		
		FuntionalInterface fiAdd=(a,b)->{
			return Double.valueOf(a+b);
		};
		
		FunctionalInterfaceExample2 fn2=(int a,int b,int c)->{
			 return a*b*c;
			 
		};		
		
		FunctionalIntExample3 FI3=(int a, int b)->{
			return a*b/b;
		};
		FunctionalIntExample3 F4=(int a, int b)->{
			return a*b;
		};
		System.out.println("Calculate in FI3 example : "+ FI3.calculate(9, 4)+" 2nd calculate FI3: " +
		F4.calculate(4, 4));

		System.out.println(fn2.calculate(5, 6, 7));
		
		System.out.println("Multiply "+fi.result(14, 12)+" "+"AdditionInterface "+fiAdd.result(12, 10));
		
		Runnable runnable =()->{
			System.out.println("Its Runnable Execution");
		};
		
		Callable<String> callable=() ->{
			return "Its CAllable";
			
		};
		
		runnable.run();
		System.out.println(callable.call().toString());
	}

}
