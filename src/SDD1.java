import model.CalcModel;
import algorithm.Calculator;
import statistics.Statistics;


public class SDD1 {

	/**
	 * @param args
	 */
	public void Sdd() {
		// TODO Auto-generated method stub
		CalcModel test = new Statistics();
		Calculator calculator = new Calculator(test); 	
		calculator.calculate();
	}

}
