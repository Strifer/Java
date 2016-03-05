package hr.fer.zemris.java.custom.scripting.parser;


import java.io.IOException;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
/** 
 * This class represents a script parser which parses through text trying to see if it follows a pre defined format.
 * The parsed document is stored as a tree of <code>Node</code> elements where the root of the tree is a <code>DocumentNode</code>.
 * <p>Comes equipped with several methods for parsing the document, retrieving the root of the constructed document tree and retrieving the text reconstruction of said tree.
 * @author Filip Džidić
 * @throws SmartScriptParserException if parsing error is found, should output appropriate message
 */
public class SmartScriptParser {
	/** represents the root of our parsed tree */
	private DocumentNode parsedbody;
	/** used for storing and separating text and tags */
	private ArrayBackedIndexCollection elements;
	/** used for constructing the parsed document tree */
	private ObjectStack stack;
	
	/**
	 * This constructor takes a single user defined <code>String</code> representation of the document we wish to parse.
	 * It then initializes the necessary data structures and starts the parsing process.
	 * @param docBody represents the document we are going to parse
	 * @throws IOException 
	 * @throws SmartScriptParserException if document has invalid format
	 */
	public SmartScriptParser (String docBody) {
		elements = new ArrayBackedIndexCollection();
		stack= new ObjectStack();
		parsedbody = new DocumentNode();
		parse(docBody);
	}
	
	/**
	 * This method begins the parsing process. It's basic function is to separate document body text from tags.
	 * @param text the <code>String</code> representation of the document we are parsing
	 * @throws SmartScriptParserException if tags aren't properly closed
	 */
	private void parse(String text) {
		//this method's main job is to separate tags from normal text body by searching for tag openers
		
		//we initialize the String indices
		int index = 0;
		int end = 0;
		int start = 0;
		int length = text.length();
		char c; 
		
		//we search the entire string character by character
		while (index<text.length()) {
			c = text.charAt(index);
			
			//escapes the specially marked character
			if (c=='\\' && (index+2)<length) {
				index+=2;
				continue;
			}
			
			
			//if true potential tag opener is found
			if (c=='{' && text.startsWith("$", (index+1))) {
				if (!((text.substring(start, index)).equalsIgnoreCase(""))) {
					elements.add(text.substring(start, index));
				}
				try {
					//see if the tag is properly closed
					end = findTagEnd(text, index);
				} catch (SmartScriptParserException e) {
					throw e;
				}
				//we add the tag as  a single element and shift around the indices to skip over it
				elements.add(text.substring(index, end+1));
				index=end+1;
				start=end+1;
			}
			//move onto the next character
			index++;
			if (end+2==length) {
				elements.add(text.substring(end+1,index));
				break;
			}
		}
		
		if ((end+2)!=index) {
			if (end==0) {
				elements.add(text.substring(end, index));
			} else {
				elements.add(text.substring(end+1, index));
			}
		}
		//when finished begin tokenization
		tokenize();
		
	}
	
	/**
	 * This method's main job is to begin tokenizing and analysing tags in our document. If tags are in valid format a document tree which represents our parsed document is created.
	 * @throws SmartScriptParserException if tags have invalid format
	 */
	private void tokenize() {
		//push the root to stack
		stack.push(parsedbody);
		
		//start tokenising tags and body text
		for (int i=0, size=elements.size(); i<size; i++) {
			String s = (String) elements.get(i);
			
			//if element is a tag find out if it is valid
			if (isTagged(s)) {
				
				//cuts out the unneeded characters
				s=s.substring(2, s.length()-2);
				s=s.trim();
				
				//if the tag is defined start analyzing the format, call the right method based on its name
				if (isValidTag(s)) {
					
					s=s.trim();
					char c = s.charAt(0);
					if (c=='F' || c=='f') {
						forToken(s);
					} else if (c=='=') {
						equalsToken(s);
					} else {
						endToken(s);
					}
				} else {
					throw new SmartScriptParserException("Invalid token");
				}
			//if its normal text add it as a node
			} else {
				Node temp = (Node) stack.peek();
				temp.addChildren(new TextNode(s));
			}
		}
		
	}
	/**
	 * This method is used for analyzing the format of a valid <code>{$=...$}</code> tag.
	 * <p>There is no predefined number of elements this tag can contain however only certain types are allowed and they all have their format.
	 * <p> - Variables must start with a letter followed by any number of digits letters or underscores
	 * <br> - Integers must only contain digits and no decimal point
	 * <br> - Doubles must contain only digits and a single decimal point
	 * <br> - Operators must be pre-defined, currently only supported operators are {+,-,*,/,%}
	 * <br> - Strings must be properly opened and closed \"example\"
	 * <br> - Functions must start with '@' followed by a valid function  name
	 * @param s <code>String representation of a <code>{$=..$}</code> tag
	 * @throws SmartScriptParserException if format is invalid
	 */
	private void equalsToken(String s) {
		//get rid of unneeded characters
		s=s.substring(1);
		s=s.trim();
		//marks the end
		s=s.concat("  $");
		
		int index=0;
		int start=0;
		//tokens are stored here as they are found in order
		ArrayBackedIndexCollection tokenarray = new ArrayBackedIndexCollection();
		
		//Begin parsing
		char c = s.charAt(0);
		while(s.length()!=0) {
			
			//potential variable is found
			if (Character.isLetter(c)) {
				while (Character.isLetter(c) || Character.isDigit(c) || c=='_') {
					index++;
					c=s.charAt(index);
				}
				
				//add the variable, skip the text and move on to the next token
				tokenarray.add(new TokenVariable(s.substring(start, index)));
				 
				s=s.substring(index);
				s=s.trim();
				index=0;
				start=0;
				c=s.charAt(index);
				continue;
				
				//potential integer or double is found
			} else if (Character.isDigit(c)) {
				//counts decimal points
				int counterPoint = 0;
				
				while (Character.isDigit(c) || c=='.') {
					index++;
					if (c=='.') {
						counterPoint++;
					}
					c=s.charAt(index);
				}
				//if no decimal points are found the number is an integer
				if (counterPoint == 0) {
					int i = Integer.parseInt(s.substring(start, index));
					tokenarray.add(new TokenConstantInteger(i));
				//if a single decimal point was found the number is a double
				} else if (counterPoint == 1) {
					double d = Double.parseDouble(s.substring(start, index));
					tokenarray.add(new TokenConstantDouble(d));
				//if more than one decimal point was found input was invalid
				} else {
					throw new SmartScriptParserException("invalid literal digit");
				}
				//add the token and skip the parsed text
				s=s.substring(index);
				s=s.trim();
				index=0;
				start=0;
				counterPoint=0;
				c=s.charAt(index);
				continue;
				
				//check if a potential operator was found
			}  else if (this.isValidOperator(c)) {
				
				//add it as a token and skip the text
				tokenarray.add(new TokenOperator(s.substring(start, index+1)));
				 
				s=s.substring(index+1);
				s=s.trim();
				index=0;
				start=0;
				c=s.charAt(index);
				continue;
				
			//if potential string is found start parsing
			} else if (c=='"') {
				c=s.charAt(index+1);
				while (c!='"') {
					if (c=='\\') {
						index+=2;
						c=s.charAt(index);
					}
					index++;
					if (c=='$') {
						break;
					}
					c=s.charAt(index);
					
				}
				if (c=='$') {
					tokenarray.add(new TokenString(s.substring(start, index-1)));
						break;	
				}
				tokenarray.add(new TokenString(s.substring(start, index+1)));
				 //add the string and move over to next token
				if (index>s.length()) {
					s=s.substring(index-1);
				} else {
					s=s.substring(index+1);
				}
				
				s=s.trim();
				index=0;
				start=0;
				c=s.charAt(index);
				continue;
			
				//potential function is found
			} else if (c=='@') {
				c= s.charAt(index+1);
				if (Character.isLetter(c)) {
					while (Character.isLetter(c) || Character.isDigit(c) || c=='_') {
						index++;
						c=s.charAt(index);
					}
					
					//add the function name and skip the text
					tokenarray.add(new TokenFunction(s.substring(start, index)));
					
					s=s.substring(index);
					s=s.trim();
					index=0;
					start=0;
					c=s.charAt(index);
					continue;
				//if a letter doesn't follow function name is invalid
				} else {
					throw new SmartScriptParserException("invalid function tag");
				}
				//if we reach this we're done
			} else if(c=='$') {
				
				break;
			}	else {
				//if input isn't defined throw an error
				throw new SmartScriptParserException("invalid equals tag");
			}
		
		}
		//create the final token and construct the node
		Token[] field = new Token [tokenarray.size()];
		for (int i=0, j=tokenarray.size(); i<j; i++) {
			field[i]=(Token)tokenarray.get(i);
		}
		//add the node to the tree
		EchoNode ecnode = new EchoNode(field);
		Node temp = (Node) stack.peek();
		temp.addChildren(ecnode);
	}
	
	
	/**
	 * This method is used for analyzing the format of a valid <code>{$FOR ...$}</code> tag.
	 * <p>A valid for tag must start with a variable name followed by two or three parameters which can be digits variables or strings.
	 * <p> - Variables must start with a letter followed by any number of digits letters or underscores
	 * <br> - Integers must only contain digits and no decimal point
	 * <br> - Doubles must contain only digits and a single decimal point
	 * <br> - Strings must be properly opened and closed \"example\"
	 * @param s <code>String</code> representation of a for tag
	 * @throws SmartScriptParserException if format is invalid
	 */
	private void forToken(String s) {
		//delete the characters we don't need
		s=s.substring(3);
		s=s.trim();
		//signifies the end
		s=s.concat("$");
		//we're going to store the tokens here as we find them
		ArrayBackedIndexCollection col = new ArrayBackedIndexCollection(4);
		 
		char c = s.charAt(0);
		int index = 0;
		int start = 0;
		
		//first we check if the first token is a valid variable
		if (Character.isLetter(c)) {
			while (Character.isLetter(c) || Character.isDigit(c) || c=='_') {
				index++;
				c=s.charAt(index);
			}
		} else {
			//immediately throw exception if it isn't
			throw new SmartScriptParserException();
		}
		//tokenize it and store it
		TokenVariable var= new TokenVariable(s.substring(start, index));
		col.add(var);
		
		//skip the text
		s=s.substring(index);
		s=s.trim();
		index = 0;
		start = 0;
		c = s.charAt(index);
		while(s.length()>0) {
			//potential variable is found
			if (Character.isLetter(c)) {
				while (Character.isLetter(c) || Character.isDigit(c) || c=='_') {
					index++;
					c=s.charAt(index);
				}
				//store the variable and skip the text
				var = new TokenVariable(s.substring(start, index));
				 
				col.add(var);
				s=s.substring(index);
				s=s.trim();
				index=0;
				start=0;
				c=s.charAt(index);
				continue;
			//potential digit is found
			} else if (Character.isDigit(c)) {
				//counts decimal points
				int counterPoint = 0;
				
				while (Character.isDigit(c) || c=='.') {
					index++;
					if (c=='.') {
						counterPoint++;
					}
					c=s.charAt(index);
				}
				//if no decimal points are found the number is an integer
				if (counterPoint == 0) {
					int i = Integer.parseInt(s.substring(start, index));
					col.add(new TokenConstantInteger(i));
				//if a single decimal point was found the number is a double
				} else if (counterPoint == 1) {
					double d = Double.parseDouble(s.substring(start, index));
					col.add(new TokenConstantDouble(d));
				//if more than one decimal point was found input was invalid
				} else {
					throw new SmartScriptParserException("invalid literal digit");
				}
				//add the token and skip the parsed text
				s=s.substring(index);
				s=s.trim();
				index=0;
				start=0;
				counterPoint=0;
				c=s.charAt(index);
				continue;
				
			//potential string is found
			} else if (c=='"') {
				c=s.charAt(index+1);
				while (c!='"') {
					if (c=='\\') {
						index+=2;
						c=s.charAt(index);
					}
					index++;
					if (c=='$') {
						break;
					}
					c=s.charAt(index);
					
				}
				if (c=='$') {
					col.add(new TokenString(s.substring(start, index-1)));
						break;	
				}
				//store the string and skip text
				col.add(new TokenString(s.substring(start, index+1)));
				 
				s=s.substring(index+1);
				s=s.trim();
				index=0;
				start=0;
				c=s.charAt(index);
				continue;
			//we're done if we reach this
			} else if(c=='$') {
				break;
			}	else {
				//throw exception if undefined character is found
				throw new SmartScriptParserException("invalid for tag format");
			}
			
		}
		ForLoopNode forloop = null;
		//a for tag can only contain 3 or 4 elements
		if (col.size()==2 || col.size()>4) {
			throw new SmartScriptParserException("invalid number of tokens");
		}
		//initialize the node
		if (col.size()==3) {
			forloop = new ForLoopNode((TokenVariable)col.get(0), (Token)col.get(1), (Token)col.get(2), null);
		} else {
			forloop = new ForLoopNode((TokenVariable)col.get(0), (Token)col.get(1), (Token)col.get(2), (Token)col.get(3));
		}	
		
			//adds the node to the tree
			Node parent = (Node)stack.peek();
			parent.addChildren(forloop);
			stack.push(forloop); 
			
		
		
		
	}
	/**
	 * This method is used for parsing {$ END $} tag.
	 * <p> Their sole purpose is to signify the end of non empty tags.
	 * @param s <code>String</code> representation of an END tag
	 */
	private void endToken (String s) {
		//pop the preceding for tag when we come to an end
		stack.pop();
		//if stack popped a document node tags weren't used properly
		if (stack.size()==0) {
			throw new SmartScriptParserException("FOR tags not properly closed");
		}
	}
	/**
	 * This method checks to see if the tag has been properly closed.
	 * @param text the document we are parsing
	 * @param index the index where we have found the tag opener
	 * @return the index of the closer, if it is found
	 * @throws SmartScriptParserException if tag isn't properly closed
	 */
	private static int findTagEnd(String text, int index) {
		index+=2;
		while (index<text.length()) {
			char c = text.charAt(index);
			if (c=='$' && text.startsWith("}", (index+1))) {
				return index+1;
			}
			if (c=='{') {
				throw new SmartScriptParserException("Tag not closed properly");
			}
			index++;
		}
		throw new SmartScriptParserException("Tag not closed properly");
	}
	/**
	 * Retrieves the parser's constructed document tree
	 * @return the root of the tree
	 */
	public DocumentNode getDocumentNode() {
		return parsedbody;
	}
	
	/**
	 * This method checks if a given character represents a valid defined operator.
	 * @param s the operator we are testing
	 * @return true if operator is supported
	 */
	private boolean isValidOperator (char s) {
		if (s=='*' || s=='+' || s=='-' || s=='/' || s=='%')
			return true;
		return false;
	}
	/**
	 * This method checks if the found tag is valid
	 * @param s the tag we are testing
	 * @return true if supproted
	 */
	public boolean isValidTag (String s) {
		if (s.charAt(0)=='=') {
			return true;
		}
		s=s.substring(0, 3);
		if (s.equalsIgnoreCase("FOR")) {
			return true;
		}
		if (s.equalsIgnoreCase("END")) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to differentiate tags from body text
	 * @param s the text we are testing
	 * @return true if text is a properly opened and closed tag
	 */
	private boolean isTagged (String s) {
		if (s.charAt(0)=='{' && s.charAt(s.length()-1)=='}')
			return true;
		else
			return false;
	}
}
