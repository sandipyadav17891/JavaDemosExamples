package MapExample;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapStreamExamples20Aug26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String,Integer> map=new HashMap<>();
		map.put("A", 60);
		map.put("B", 10);
		map.put("C", 40);
		map.put("D", 20);
		
		//Collect Elements more than 15
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()>15).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//sort 
		System.out.println(map.entrySet().stream()
		.sorted((s1,s2) -> s2.getValue().compareTo(s1.getValue()))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(a1,a2) ->a2, 
				LinkedHashMap::new)));

		//nth Element
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()>15).findAny());
		
		//group based on even values
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()%2==0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//Map to List -key
		System.out.println(map.keySet().stream().toList());
		
		//Map to List -value
		System.out.println(map.values().stream().toList());
		
		//find min
		System.out.println(map.entrySet().stream()
		.min((s1,s2)->s1.getValue().compareTo(s2.getValue())));
		
		//REAL TIME OBJECTS EXAMPLE
		Map<String,Boolean> feature=
		Map.of("Section1", true, "Section2", false,"Section3", false,"Section4", true);
		System.out.println(feature.entrySet().stream()
		.filter(x ->x.getValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//APPLY 10% DISCOUNT 
		Map<String,Double> units=Map.of("handy", 90.00, "plumb", 45.00,"maker",55.00,"Rectifier",44.00);
		System.out.println(units.entrySet().stream()
		.collect(Collectors.toMap(x->x.getKey(), x ->x.getValue()*0.9)));
		
		

	}

}
