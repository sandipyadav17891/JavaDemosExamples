package DemoExample;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sample0831 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		Given:

		int[] nums
		int k

		Return the k most frequent elements.
		Example:
		nums = {1,1,1,2,2,3}
		k = 2
		Output:

		{1,2}

		Implement:

		public static int[] topKFrequent(int[] nums, int k)
		*/


		Integer[] nums = {1,1,1,2,2,3,3,3};
        List<Integer> temp=Arrays.asList(nums);
        System.out.println(temp.stream().
        filter(x ->Collections.frequency(temp, x) >1).collect(Collectors.toList()));
        
        System.out.println(temp.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
        .stream()
        .sorted((s1,s2)->s2.getValue().compareTo(s1.getValue())).limit(2).collect(Collectors.toList()));
        
        
		
		
		
	}

}
