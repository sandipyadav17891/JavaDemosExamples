package MapExample;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class mapStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//{Map}.entryset().stream()
		//Map.Entry::getKey   //Map.Entry::getValue
		//{Map}.keyset().stream
		//{Map}.value..stream
		//in Sorting ->(a1,a2)->a1, LinkedHashMap::new    Map.Entry::comparingByValue()
		
		
	
		Map<String,Integer> map=new HashMap<>();
		map.put("A", 60);
		map.put("B", 10);
		map.put("C", 40);
		map.put("D", 20);
		
		
		//Collect Elements more than 15
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()>15)
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//sort 
		System.out.println(map.entrySet().stream()
		.sorted((e1,e2)->e1.getValue().compareTo(e2.getValue()))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
				(s1,s2)->s1, LinkedHashMap::new)));
		
		//nth Element
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()>15)
		.findFirst());
		
		//group based on even values
		System.out.println("Group Even Values"+map.entrySet().stream()
		.filter(x ->x.getValue()%2==0)
		.collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//Map to List -key
		System.out.println(map.keySet().stream().collect(Collectors.toList()));
		
		//Map to List -value
				System.out.println(map.values().stream().collect(Collectors.toList()));
				
				
		//find min
				System.out.println(map.entrySet().stream()
	.min((e1,e2)->e1.getValue().compareTo(e2.getValue())).get());
				
				//find max
				System.out.println(map.entrySet().stream()
	.min((e1,e2)->e2.getValue().compareTo(e1.getValue())).get());
	
	
	
	//REAL TIME OBJECTS EXAMPLE
				Map<String,Boolean> feature=Map.of("Section1", true, "Section2", false,"Section3", false,"Section4", true);
				System.out.println("Enabled feature is "+feature.entrySet().stream()
				.filter(x ->x.getValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	
	//APPLY 10% DISCOUNT 
				Map<String,Double> units=Map.of("handy", 90.00, "plumb", 45.00,"maker",55.00,"Rectifier",44.00);
				System.out.println(units.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, x ->x.getValue()* 0.9)));
				
				
				
				
				
		
		
	}

}
