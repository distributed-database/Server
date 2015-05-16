package relation;

public class Link {
	
	private Relation relation1;
	private Relation relation2;
	
	public Link(Relation relation1, Relation relation2){
		this.relation1 = relation1;
		this.relation2 =relation2;
		
	}
	
	public boolean isLinkBetween(Relation relation1, Relation relation2){
		return ((this.relation1.equals(relation1)&& this.relation2.equals(relation2)) ||
				(this.relation1.equals(relation2)&& this.relation2.equals(relation1)));
	}
	
	public Relation getRelation1() {
		return relation1;
	}
	public void setRelation1(Relation relation1) {
		this.relation1 = relation1;
	}
	public Relation getRelation2() {
		return relation2;
	}
	public void setRelation2(Relation relation2) {
		this.relation2 = relation2;
	}
	public String toString() {
        return "Link(" + relation1 + "," + relation2 + ")";
    }
}
