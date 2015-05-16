
package algorithm;

import java.util.Iterator;
import java.util.List;

import model.CalcModel;

import semijoin.*;

import relation.*;

import utils.*;

public class Calculator {
    
    private CalcModel calc;
    
    private SemijoinList joinsinEF = new SemijoinList();
    
    public Calculator(CalcModel model) {
        calc = model;
    }
    
    public SemijoinList calculate() {
        System.out.println("SDD-1 calculation started\n");

        boolean moreBeneficialJoins = true;

        while (moreBeneficialJoins) {

            SemijoinList newBeneficialJoins = findBeneficialJoins();
            System.out.println("Joins found:\n" + newBeneficialJoins.toString());

            Semijoin bestSemiJoin = newBeneficialJoins.getBestSemijoin();
            
            if (bestSemiJoin != null) {

                System.out.println("Most beneficial join: " + bestSemiJoin);
                
                updateCalculationModel(bestSemiJoin);
                
                joinsinEF.add(bestSemiJoin);

                System.out.println("\nUpdating EF. EF consists of: \n" + joinsinEF);
                System.out.println("\nModel is now:\n" + calc);

            } else
                moreBeneficialJoins = false;

        }
        
        System.out.println("Calculation finished.\n");
        
        
        Relation assemblysite = findAssemblySite();
        
        System.out.println("Assembly site is " + assemblysite + " (size: " + assemblysite.getSize() + ")\n");
        
        
        SemijoinList atassemblysite = findSemiJoinsAtSite(assemblysite);
        
        System.out.println("Remove non-beneficial joins...\n");
        
        removeCostlySemiJoins(atassemblysite);
        
        System.out.println("Updating ES: \n" + joinsinEF);
        
        return joinsinEF;
    }

    private void removeCostlySemiJoins(SemijoinList atassemblysite) {
        
        double totalCost = joinsinEF.calculateTotalCost();
        
        SemijoinList remove = new SemijoinList();
        
        for (Iterator i2 = atassemblysite.getList().iterator(); i2.hasNext(); ) {
            Semijoin join = (Semijoin) i2.next();
            double totalCostWithout = joinsinEF.calculateCostWithout(join);
            if (totalCostWithout<totalCost)  remove.add(join);
        }
        
        System.out.println("Removed: \n" + remove);
        
        joinsinEF.removeJoins(remove);
    }

    private SemijoinList findSemiJoinsAtSite(Relation assemblysite) {
        SemijoinList atassemblysite = new SemijoinList();
        
        for (Iterator i2 = this.joinsinEF.getList().iterator(); i2.hasNext(); ) {
            Semijoin join = (Semijoin) i2.next();
            if (join.getLeftRelation().equals(assemblysite)) {
                atassemblysite.add(join);
            }
        }
        return atassemblysite;
    }

    private Relation findAssemblySite() {
        double max=0;
        Relation assemblysite = null;
        
        Relation[] relations = calc.getRelations();
        
        for (int ii=0; ii<relations.length; ii++) {
           if (relations[ii].getSize()>max) {
               max = relations[ii].getSize();
               assemblysite = relations[ii];
           }
        }
        return assemblysite;
    }

    
    private void updateCalculationModel(Semijoin best) {
        
        System.out.println("\nUpdating calculation model. Changed values are:");
        
        Relation toChange = best.getLeftRelation();
        toChange.setSize(toChange.getSize() * best.getRightProjection().getSfsj());
        System.out.println(toChange + ".size =" + Utils.format(toChange.getSize()));
        
        
        List otherProjections = calc.getAllProjectionsOfRelation(toChange);
        otherProjections.remove(best.getLeftProjection()); 
        
        for (Iterator i = otherProjections.iterator(); i.hasNext(); ) {
            Projection current = (Projection)i.next();
            
            if (toChange.getSize()<current.getSize()) {
                double factor = toChange.getSize()/current.getSize();
                current.setSize(toChange.getSize());
                current.setSfsj(current.getSfsj()*factor);
            }
        }
        
        best.getLeftProjection().setSfsj(best.getLeftProjection().getSfsj() * best.getRightProjection().getSfsj());
        System.out.println(best.getLeftProjection() + ".sfsj =" + Utils.format(best.getLeftProjection().getSfsj()));

        best.getLeftProjection().setSize(best.getLeftProjection().getSize() * best.getRightProjection().getSfsj());
        System.out.println(best.getLeftProjection() + ".size =" + Utils.format(best.getLeftProjection().getSize()));
    }

    /**
     * * This method searches the calculation model for new beneficial semijoins. It returns
     * a list of newly found beneficial semi joins.
     * @return
     */
    private SemijoinList findBeneficialJoins() {

    	SemijoinList newJoins = new SemijoinList();
        
        for (Iterator i = calc.getAllLinks().iterator(); i.hasNext();) {
            
            System.out.println("Looking for joins...\n");
            
            Link link = (Link) i.next();

            Projection relationOfB = calc.getProjection(link.getRelation1(), link.getRelation2());
            Projection relationOfA = calc.getProjection(link.getRelation2(), link.getRelation1());

            double benefit1 = calc.calcBenefit(link.getRelation1(), relationOfB);
            double cost1 = calc.calcCost(relationOfB);

            Semijoin j1 = new Semijoin(link.getRelation1(), link.getRelation2(), benefit1, cost1, relationOfA, relationOfB);
            if (!joinsinEF.contains(j1))
                newJoins.add(j1);

            double benefit2 = calc.calcBenefit(link.getRelation2(), relationOfA);
            double cost2 = calc.calcCost(relationOfA);

            Semijoin j2 = new Semijoin(link.getRelation2(), link.getRelation1(), benefit2, cost2, relationOfB, relationOfA);
            if (!joinsinEF.contains(j2))
                newJoins.add(j2);
        }
        
        return newJoins;
        
    }
}
