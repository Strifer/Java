package hr.fer.zemris.java.custom.scripting.nodes;
/** 
 * This class represents the root of our parsing tree. It has a single method which converts the entire Node tree we've parsed for simple output.
 * 
 * @author Filip Džidić
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Returns a string representation of the contents of this class.
	 */
	public String toString() {
		String s="";
		for (int i = 0; i<this.numberOfChildren(); i++) {
			s+=this.getChild(i).toString();
		}
		return s;
	}
}
