package relation;

public class Projection {

	private Relation relation;
	private Connection connection;
	private double size;
	private double sfsj;
	
	
	public Projection(Relation relation, Connection connection, double size,
			double sfsj) {
		this.relation = relation;
		this.connection = connection;
		this.size = size;
		this.sfsj = sfsj;
	}
	
	
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getSfsj() {
		return sfsj;
	}
	public void setSfsj(double sfsj) {
		this.sfsj = sfsj;
	}
	
	public String toString() {
        return relation + "." + connection;
    }

	
}
