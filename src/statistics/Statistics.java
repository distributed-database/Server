package statistics;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.w3c.dom.stylesheets.LinkStyle;



import relation.Link;
import relation.Projection;
import relation.Relation;

import model.CalcModel;


public class Statistics extends CalcModel {

	private final String userName = "project";

	private final String password = "project";

	private final String serverName = "localhost";

	private final int portNumber = 3306;
	private final String database = "statistics";
	
	private final String statTable = "stats";
	
	private Connection statistic;
	/*
	 * connection function : with database name 'dbName'
	 */
	private QueryReader reader = new QueryReader();
	
	
	
	String[] relationsString;
	String queryString;
	public Statistics(){
		
		Hashtable<String, List> hm= new Hashtable<String, List>();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		double sfsj =0.001;
		statistic = null;
		try {
			statistic = this.getConnection(database);
			System.out.println("Connected to database "+ database);
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
		relationsString=reader.readInput();
		ResultSet rs=null;
		Statement stmt = null;
		try {
			stmt=statistic.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		relations = new Relation[relationsString.length];
		int i=0;
		for(String rel:relationsString){
			queryString="select cardinality*SUM(column_length) from stats where relation_name=\'"+rel+"\';";
			try {
				rs=stmt.executeQuery(queryString);
				rs.next();
				relations[i]=new Relation(rel,rs.getDouble(1));
				i++;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		
		for(i=0;i<relationsString.length-1;i++){
			for(int j = i+1;j<relationsString.length;j++){
				queryString = " SELECT  s1.attribute_name " +
						"FROM stats s1,stats s2 " +
						"WHERE s1.relation_name = \'"+ relationsString[i] +"\'" +
						"AND s2.relation_name = \'"+ relationsString[j] +"\'" +
						"AND s1.attribute_name = s2.attribute_name";
				//System.out.println(queryString);
				try {
					rs=stmt.executeQuery(queryString);
					
					if (rs.next()) {
						String attributeString = rs.getString(1);
						
//						System.out.println(queryString);
						if(hm.containsKey(attributeString)){
							hm.get(attributeString).add(new Link(relations[i],relations[j]));
						}else {
							hm.put(attributeString, new ArrayList());
							hm.get(attributeString).add(new Link(relations[i],relations[j]));
						}
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		Set<String> keys = hm.keySet();
		List all = new ArrayList();
		
		for(String key:keys){
			connections.add(new relation.Connection(key,hm.get(key)));
			all.addAll(hm.get(key));
			
		}
		this.allLinks = all;
		List projectionList = new ArrayList();
		
		for(Iterator v = connections.iterator();v.hasNext();){
			relation.Connection c = (relation.Connection) v.next();
//			System.out.println("connection = "+ c.toString());
			for(Iterator l = c.getListOfLinks().iterator();l.hasNext();){
				Link li = (Link) l.next();
//				System.out.println(li.toString());
				String R1=li.getRelation1().getName();
				String R2=li.getRelation2().getName();
				queryString="SELECT domain,cardinality,column_length " +
						"FROM stats " +
						"WHERE relation_name=\'"+R1+"\'" +
						"AND attribute_name=\'"+c.getAttributeString()+"\'";
				try {
					rs=stmt.executeQuery(queryString);
					rs.next();
					if(!ht.containsKey(R1)){
//						System.out.println(R1);
						sfsj = rs.getDouble(1)/rs.getDouble(2);
						if(sfsj <= 0.0001){
							sfsj = 0.0001;
						}
						projectionList.add(new Projection(li.getRelation1(), c, rs.getDouble(2)*rs.getInt(3), sfsj));
						ht.put(R1, "yes");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				
				queryString="SELECT domain,cardinality,column_length " +
						"FROM stats " +
						"WHERE relation_name=\'"+R2+"\'" +
						"AND attribute_name=\'"+c.getAttributeString()+"\'";
				try {
					rs=stmt.executeQuery(queryString);
					rs.next();
					if(!ht.containsKey(R2)){
//						System.out.println(R2);
						sfsj = rs.getDouble(1)/rs.getDouble(2);
						if(sfsj <= 0.0001){
							sfsj = 0.0001;
						}
						projectionList.add(new Projection(li.getRelation2(), c, rs.getDouble(2)*rs.getInt(3), sfsj));
						ht.put(R2, "yes");
						}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};

				
				
			}
			ht.clear();
		}
		projections = new Projection[projectionList.size()];
		projectionList.toArray(projections);
		for (Projection p : projections) {
			System.out.println("sfsj="+p.getSfsj()+" size="+p.getSize()+"    "+p.toString());
		}

		System.out.println("Projection array created");
		ttr=1;
		tmsg=20;
	}
	
	public Connection getConnection(String databaseName) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + databaseName,
				connectionProps);

		return conn;
	}
	
}
