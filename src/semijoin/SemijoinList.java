package semijoin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import relation.Link;

public class SemijoinList {
	private List semijoinList = new ArrayList();
	
	public void addSemijoin (Semijoin semijoin){
		semijoinList.add(semijoin);
	}
	public boolean contains(Semijoin semijoin){
		
		for (Iterator i = semijoinList.iterator(); i.hasNext();) {
            
            
            Semijoin s = (Semijoin) i.next();
            if(s.equals(semijoin)){
            	return true;
            }
		}
		return semijoinList.contains(semijoin);
		
	}
	
	public Semijoin getSemijoin(int index){
		return (Semijoin) semijoinList.get(index);
	}
	
	public Semijoin getBestSemijoin(){
		double ratio =0;
		Semijoin best = null;
		for (Iterator i = semijoinList.iterator(); i.hasNext();) {
			Semijoin sj = (Semijoin) i.next();
			if(sj.getRatio() > ratio){
				
				ratio = sj.getRatio();
				best = sj;
			}
		}
		return best;
	}
	
	 public double calculateTotalCost() {
	        double tmp=0;
	    
	        for (Iterator i = semijoinList.iterator(); i.hasNext();) {
	            Semijoin j = (Semijoin) i.next();
	            tmp+=j.getCost();
	        }
	        return tmp;
	    }
	 	public double calculateCostWithout(Semijoin join) {
	        double tmp=0;
	        
	            for (Iterator i = semijoinList.iterator(); i.hasNext();) {
	                Semijoin j = (Semijoin) i.next();
	                if (!j.equals(join)) tmp+=j.getCost();
	            }
	            return tmp;
	    }
	 	public void add(Semijoin j) {
	        semijoinList.add(j);
	    }
	 	public List getList() {
	        return semijoinList;
	    }
	    public void removeJoins(SemijoinList remove) {
	        semijoinList.removeAll(remove.semijoinList);        
	    }
	    public String toString() {

	        StringBuffer b = new StringBuffer();
	        
	        b.append("--------------------------------------------\n");


	        for (Iterator i = semijoinList.iterator(); i.hasNext();) {
	            Semijoin j = (Semijoin) i.next();
	            b.append(j + "\n");
	        }
	        b.append("--------------------------------------------\n");

	        return b.toString();
	    }
}
