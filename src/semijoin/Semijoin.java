package semijoin;

import java.text.NumberFormat;

import relation.Projection;
import relation.Relation;

public class Semijoin {

	private Relation leftRelation;
	private Relation rightRelation;
	
	private Projection leftProjection;
	private Projection rightProjection;
	
	private double cost;
	private double benefit;
	private double ratio;
	public Semijoin(Relation leftRelation, Relation rightRelation,double benefit,
			double cost,Projection leftProjection, Projection rightProjection) {
		this.leftRelation = leftRelation;
		this.rightRelation = rightRelation;
		this.leftProjection = leftProjection;
		this.rightProjection = rightProjection;
		this.cost = cost;
		this.benefit = benefit;
		this.ratio = benefit - cost;
	}
	
	public boolean equals(Semijoin sj){
		return ((leftRelation.equals(sj.getLeftRelation()) ) && rightRelation.equals(sj.getRightRelation()));
	}

	public Relation getLeftRelation() {
		return leftRelation;
	}

	public void setLeftRelation(Relation leftRelation) {
		this.leftRelation = leftRelation;
	}

	public Relation getRightRelation() {
		return rightRelation;
	}

	public void setRightRelation(Relation rightRelation) {
		this.rightRelation = rightRelation;
	}

	public Projection getLeftProjection() {
		return leftProjection;
	}

	public void setLeftProjection(Projection leftProjection) {
		this.leftProjection = leftProjection;
	}

	public Projection getRightProjection() {
		return rightProjection;
	}

	public void setRightProjection(Projection rightProjection) {
		this.rightProjection = rightProjection;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getBenefit() {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}
	public double getRatio() {
		return ratio;
	}
	public String toString() {
        
        NumberFormat f = NumberFormat.getInstance();
        
        StringBuffer c = new StringBuffer();
        c.append(leftRelation);
        c.append("(" + leftProjection + ")");
        c.append(" SJ ");
        c.append(rightRelation);
        c.append("(" + rightProjection + ")");
        c.append(" Benefit: " + f.format(benefit));
        c.append(" Cost: " + f.format(cost));
        c.append(" Ratio: " + f.format(ratio));
        c.append(ratio<0 ? " (non-beneficial)" : " (beneficial)");

        return c.toString();
    }
	
}
