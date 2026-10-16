package DemoExample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Amex0903 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		//2d array to single array and count the frequency of each element in the array and return the result as a map
		int[][] temp = {{1,2,4,5},{3,4,5,6}};
/**Make into single arrays and count the frequency of each element in the array and return the result as a map **/
		 //int[] tem1=temp[0];
		 //int[] temp2=temp[1];
		 
		 List<int[]> temp2=Arrays.asList(temp);
		 System.out.println(
				 temp2.stream().flatMapToInt(Arrays::stream)
				 .boxed()
				 .collect(Collectors.groupingBy(Function.identity()	, Collectors.counting())));
		
		 int[][] tempArray = {{1,2,4,5},{1,2},{3,4,5,6},{1,2},{1,2,3,4}};
		 
		 //3d array to single array and count the frequency of each element in the array and return the result as a map
		 int[][][] darray= {
				 {
					 {1,2,3,4 },
					 {4,5,6,7}
					 }
		 };
		 int [][][] darray1=new int[2][2][3];
		 darray1=new int[][][] {
				 {
					 {1},
					 {2}
				 },
				 {
					 {3},
					 {4}
				 }
		 };
		 System.out.println(Arrays.asList(darray).stream().flatMap(Arrays::stream).flatMapToInt(Arrays::stream).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
		 
		 // can we do 2 terminal operation on stream of variable
		 /**Yes, we can do 2 terminal operation on stream of variable but we need to use the stream again for the second terminal operation. **/
		 
		 //what is main thread and child thread in java completableFuture
		 /** Main thread is the thread that is created by the JVM when the program starts.
		 It is the thread that executes the main method. 
		 Child thread is a thread that is created by the main thread or any other thread. 
		 It is a separate thread of execution that runs concurrently with the main thread. **/
		 
		 //can interface /functional interface have static method in java 8
		 /**Yes, interface /functional interface can have static method in java 8. **/
		 
		 

	}

}
