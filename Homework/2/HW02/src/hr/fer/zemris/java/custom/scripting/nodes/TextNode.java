package hr.fer.zemris.java.custom.scripting.nodes;
/** 
 * This class represents normal text in a document, outside of any tags.
 * 
 * @author Filip Džidić
 *
 */
public class TextNode extends Node {
	/** holds the text as a <code>String</code> representation */
	private String text;
	
	
	/** The class' constructor initializes text with given <code>String</code> parameter. */ 
	public TextNode (String text) {
		this.text=text;
	}
	/** Returns the <code>String</code> representation of this class */
	public String getText() {
		return text;
	}
	/** This method is used for text reconstruction of a Node. Returns a <code>String</code> representation of this class */
	public String toString() {
		return this.getText();
	}
}
