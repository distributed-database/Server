package relation;

public class Relation {
	
	private String name;
	private double size;
	
	public Relation(String name, double size){
		this.name = name;
		this.size = size;
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String toString() {
        return name;
    }
	
}
