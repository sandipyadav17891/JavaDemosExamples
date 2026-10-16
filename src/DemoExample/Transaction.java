package DemoExample;

import java.util.Objects;

public class Transaction {

	
	String id;
	String name;
	double amount;
	
	public boolean equals(Object obj) {
		if(obj instanceof Transaction) {
			Transaction t=(Transaction)obj;
			return this.id.equals(t.id);
		}
		return false;
	}
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
