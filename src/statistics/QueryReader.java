package statistics;

import java.io.*;
public class QueryReader {
	protected String[] relationsStrings;
	public String[] readInput(){
		int num=1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of relations");
		try {
			num = Integer.parseInt(br.readLine());
//			System.out.println(num);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		relationsStrings = new String[num];
		System.out.println("Enter the relations");
		for(int i=0; i<num ; i++){
			try {
				relationsStrings[i]=br.readLine();
//				System.out.println(relationsStrings[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return relationsStrings;
	}
	
	//public readAttributes()
}

