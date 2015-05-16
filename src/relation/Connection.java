package relation;

import java.util.Iterator;
import java.util.List;

public class Connection {
	
	private String attributeString;
	private List listOfLinks;
	
	public String getAttributeString() {
		return attributeString;
	}
	public void setAttributeString(String attributeString) {
		this.attributeString = attributeString;
	}
	public List getListOfLinks() {
		return listOfLinks;
	}
	public void setListOfLinks(List listOfLinks) {
		this.listOfLinks = listOfLinks;
	}
	public Connection(String attributeString, List listOfLinks){
		this.attributeString =attributeString;
		this.listOfLinks = listOfLinks;
	}
	public boolean contains(Relation r1, Relation r2){
		boolean result;
		Link link;
		for(Iterator iter = listOfLinks.iterator() ; iter.hasNext(); ){
			link =(Link)iter.next();
			if(link.isLinkBetween(r1, r2)){
				return true;
			}
			
		}
		return false;
	}
	public String toString() {
        return attributeString;
    }

}
