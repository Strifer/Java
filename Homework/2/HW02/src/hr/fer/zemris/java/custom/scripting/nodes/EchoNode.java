package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token; 
/**
 * This class represents a command which generates textual output. It has a read only field of tokens and contains methods for string conversion.
 * @author Filip Džidić
 *
 */
public class EchoNode extends Node {
	/** 
	 * An array of tokens which represent variables, integers etc.
	 */
	private Token[] tokens;
	
	/** Constructor fills the reference to the user defined array of tokens */
	public EchoNode (Token[] tokens) {
		this.tokens = tokens;
	}
	/** Returns reference to token array */
	public Token[] getTokens() {
		return tokens;
	}
	/**
	 * This method returns a <code>String</code> representation of the elements inside this class. It is used for simple output.
	 */
	public String toString() {
		String s = "{$=";
		for (int i=0; i<tokens.length; i++) {
			s+=" "+tokens[i].asText();
		}
		s+="$}";
		return s;
	}
}
