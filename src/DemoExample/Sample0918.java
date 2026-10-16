package DemoExample;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sample0918 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Integer[] number= {2,45,36,24,14} ;
		//Int sumTotal = 38;
		List<Integer> temp=Arrays.asList(number);
		compuate(temp);
		//Arrays.asList(number).stream().map(x -> calucluate(x,temp));
		
		String str="sandipyadav";
		System.out.println(str.chars().mapToObj(x -> (char) x).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).
		entrySet()
		.stream()
		.max((s1,s2) ->s1.getValue().compareTo(s2.getValue())).get());
		
		String str1="balguptaary";
		Map<Character, Integer> charCounts = new HashMap();
		str1.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		.entrySet().stream().max((s1, s2) -> s1.getValue().compareTo(s2.getValue())).get();
		
		//.map(c -> charCounts.put(c, charCounts.getOrDefault(c, 0) + 1))
	
		
	}

	private static void compuate(List<Integer> temp) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> hol=new HashMap<>();
		for(Integer in:temp) {
			for(Integer in2:temp) {
				if(in2+in==38) {
					hol.put(in2, in);
				}
			}
		}
		System.out.println(hol);
		
	}



}
