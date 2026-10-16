package MapExample;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStreamExample16Aug26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String,Integer> map=new HashMap<>();
		map.put("A", 60);
		map.put("B", 10);
		map.put("C", 40);
		map.put("D", 20);
		
		//Collect Elements more than 15
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue() > 15).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//sort 
		System.out.println("sorted ::" +map.entrySet().stream()
		.sorted((s1,s2) ->s2.getValue().compareTo(s1.getValue()))
		.collect(Collectors.
     toMap(Map.Entry::getKey, Map.Entry::getValue,
    		 (s1,s2) ->s1, LinkedHashMap::new)));
		
		//group based on even values
		System.out.println(map.entrySet().stream()
		.filter(x ->x.getValue()%2==0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//Map to List -key
		System.out.println(map.keySet().stream().collect(Collectors.toList()));
		
		//Map to List -key
		System.out.println(map.values().stream().collect(Collectors.toList()));
		
		//find min
		System.out.println(map.entrySet().stream()
		.min((s1,s2) ->s1.getValue().compareTo(s2.getValue())));
		
		//find max
		System.out.println(map.entrySet().stream()
		.min((s1,s2) ->s2.getValue().compareTo(s1.getValue())));
		
		//REAL TIME OBJECTS EXAMPLE		
		//get Only enabled feature
		Map<String, Boolean> reMap=new HashMap<>();
		reMap=Map.of("a", true, "b",false, "c",true);
		
		System.out.println(reMap.entrySet().stream()
		.filter(x ->x.getValue().booleanValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//APPLY 10% DISCOUNT 
		Map<String,Double> units=Map.of("handy", 90.00, "plumb", 45.00,"maker",55.00,"Rectifier",44.00);
		System.out.println(units.entrySet().stream()
		.collect(Collectors.toMap(Map.Entry::getKey, x ->x.getValue()*0.9)));

	}

}
