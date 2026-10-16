package DemoExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Jul13and2026 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Given List of Int print only even number
		//List<Integer> nu=new ArrayList<>();
		List<Integer> nu=List.of(2,45,22,12,4,55,11);
		System.out.println(nu.stream().filter(x ->x%2==0).collect(Collectors.toList()));
		
		//from List print the nums equals to 10
		List<Integer> num=List.of(2,1,5,7,4,5,8);
		compute(num);
		
		
		// find the list of Strings that starts with s
		List<String> strs= List.of("sandip","alice","saroj","sinhi");
		System.out.println(strs.stream().filter(x->x.startsWith("s")).collect(Collectors.toList()));
		
		//find teh sum of square of the no in the list
		List<Integer> numbers=List.of(2,45,22,12,4,55,11);
		System.out.println(numbers.stream().map(x ->x*x).reduce(0, Integer::sum));
		
		//sort the list of String in descending oreder
		List<String> str= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str.stream()
		.sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
		
		//given an array retrun the list of element appears more than once in ascending order
		String[] liStrs= {"sandip","alice","saroj","sinhi","sandip","saroj","raghav"};
		List<String> arrs=Arrays.asList(liStrs);
		System.out.println(arrs.stream()
				.filter(x ->Collections.frequency(arrs,x)>1).sorted().collect(Collectors.toList()));
		
		//groups words by there length 
		List<String> str2= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str2.stream().
		collect(Collectors.groupingBy(x ->x.length(), Collectors.toList())));
		
		//convert the Lists of List into single List
		List<List<String>> listofList=List.of(
				List.of("a","b"),
				List.of("c","d"),
				List.of("e","f")
				);	
		System.out.println(listofList.stream().flatMap(List::stream).collect(Collectors.toList()));
		
		//given "hello World" pring the frequency of each charactre
		String hello="Hello World";
		System.out.println(hello.chars().mapToObj(x ->(char) x).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
		//find the first no repeating character
		List<Character> listofChar=hello.chars().mapToObj(x ->(char)x).collect(Collectors.toList());
		System.out.println(listofChar
		.stream()
		.filter(y ->Collections.frequency(listofChar, y)==1).findFirst().get());
		
		
		//collect the list of names into set
		List<String> names=List.of("sandip","sandip","ram","jeevan");
		System.out.println(names.stream().collect(Collectors.toSet()));
		
		
		//find teh 2nd highest salary
		List<Integer> salary=List.of(10000,12000,4500,45333,50000);
		System.out.println(salary.stream().sorted(Comparator.reverseOrder()).skip(1).limit(1).collect(Collectors.toList()));
		System.out.println(salary.stream().sorted((s1,s2) ->s2.compareTo(s1)).skip(1).limit(1).collect(Collectors.toList()));
		
		//display the duplicate String 
		String dup="Hi I am Sandip Sandip";
		String[] dupli=dup.split(" ");
		List<String> stringDup=Arrays.asList(dupli);
		System.out.println(stringDup.stream().
	 filter(x -> Collections.frequency(stringDup, x)>1).distinct().collect(Collectors.toList()));
		
		
		
		
				
	}

	private static void compute(List<Integer> num) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> temp=new HashMap();
		for(Integer i:num) {
			for(Integer j:num) {
				if(i+j==10) {
					temp.put(i, j);
					//System.out.println(i +" "+ j);
				      System.out.println(temp);
				}
			}
		}
	}

	/*
	 * private static Object calculate(Integer x, List<Integer> num) { // TODO
	 * Auto-generated method stub for(Integer i:num) {
	 * 
	 * } }
	 */

}
