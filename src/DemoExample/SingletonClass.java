package DemoExample;

public class SingletonClass {

	private static SingletonClass instance;
	
	private SingletonClass() {
		
	};
	
	public static SingletonClass initiate() {
		
		if(instance ==null) {
			return instance= new SingletonClass();
		}else {
			return instance;
		}
		
		
	}
}
