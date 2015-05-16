package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import relation.*;
import utils.Utils;
public abstract class CalcModel {

	protected Projection[] projections;
	protected List connections = new ArrayList();
	protected Relation[] relations;
	protected List allLinks;
	protected double ttr;
	protected double tmsg;
	
	public double calcCost(Projection p) {
		return(p.getSize() + tmsg) * ttr;
		
	}
	public double calcBenefit(Relation leftHandSide,Projection p){
		double sfsj = p.getSfsj();
		return((1-sfsj)*leftHandSide.getSize()*ttr);
	}
	
	public List getAllProjectionsOfRelation(Relation relation){
		List tmp = new ArrayList();
		for(int i=0; i<projections.length;i++){
			if(projections[i].getRelation().equals(relation)){
				tmp.add(projections[i]);
			}
		}
		return tmp;
	}
	
	public Projection getProjection(Relation leftHandSide, Relation rightHandSide){

        for (int i = 0; i < projections.length; i++) {
            if ((projections[i].getRelation().equals(rightHandSide))
                    && (projections[i].getConnection() == getConnectionBetweenRelations(leftHandSide, rightHandSide)))
                return projections[i];
        }
        return null;
	}
	
	private Connection getConnectionBetweenRelations(Relation a, Relation b) {
        for (Iterator i = connections.iterator(); i.hasNext();) {
            Connection c = (Connection) i.next();
            if (c.contains(a, b))
                return c;

        }
        return null;
    }
	@Override
	public String toString() {
        
        StringBuffer b = new StringBuffer();
        
        for (int i=0; i<relations.length; i++) {
            b.append("Relation " + relations[i] +" -> Size: " + Utils.format(relations[i].getSize()) + "\n");
            List rels = getAllProjectionsOfRelation(relations[i]);
            for (Iterator j = rels.iterator(); j.hasNext(); ) {
                Projection currentP = (Projection)j.next();
                b.append("Projection of " + relations[i] + "(" + currentP.getConnection() + ") -> Size: " + Utils.format(currentP.getSize()) + " ->Sfsj: " + Utils.format(currentP.getSfsj()) +"\n");
            }
            
        }
        return b.toString();
    }
    
	public Projection[] getProjections() {
		return projections;
	}
	public List getConnections() {
		return connections;
	}
	public Relation[] getRelations() {
		return relations;
	}
	public List getAllLinks() {
		return allLinks;
	}
	public double getTtr() {
		return ttr;
	}
	public double getTmsg() {
		return tmsg;
	}
	
	
	
	
}
