package hr.fer.zemris.java.tecaj.hw3;

/**
 * The <code>CString</code> class represents an immutable array of characters.
 * CStrings are constant their value cannot be changed after they are created.
 * <p>
 * The class <code>CString</code> comes with several methods for searching,
 * extracting, replacing and concatenating character arrays.
 * 
 * @author Filip Džidić
 *
 */

public final class CString {
	/** Used for storing characters */
	private  final char[] data;
	/** Initial offset, signifies where the first character starts */
	private  final int offset;
	/** Signifies how many characters there are */
	private  final int length;
	
	public static void main(String[] args) {
		CString test = new CString("abcdef");
		test = test.substring(1, 6);
		System.out.println(test);
	}
	/**
	 * Initializes a new <code>CString</code> object which contains the characters from a subarray of the character array in the argument.
	 * This is controlled by the offset and length arguments.
	 * @param data the character array we are copying from
	 * @param offset signifies which index in the argument array will be the beginning of our new subarray
	 * @param length signifies how many characters we will take, starting from offset
	 * @throws StringIndexOutOfBoundsException if subarray cannot be formed because of bad indexing
	 */
	public CString(char[] data, int offset, int length) {
		if (data==null) {
			throw new IllegalArgumentException();
		}
		if (offset<0) {
			throw new StringIndexOutOfBoundsException();
		}
		if (length<0) {
			throw new StringIndexOutOfBoundsException();
		}
		if (offset > data.length - length) {
			throw new StringIndexOutOfBoundsException();
		}
		this.offset=0;
		this.data = charArrayCopy(data, offset, length);
		this.length=length;
	}
	/**
	 * Initializes a new <code>CString</code> object which copies and contains all the characters in the character array argument.
	 * @param data the character array we are copying
	 */
	public CString(char[] data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		this.offset=0;
		this.length=data.length;
		char copy[] = new char[data.length];
		copy = charArrayCopy(data, offset, length);
		this.data=copy;
	}
	/**
	 * Initializes a new <code>CString</code> object which is a copy of the original argument.
	 * Generally the new <code>CString</code> will share a single character array so less memory is used.
	 * If the original argument doesn't utilize its entire internal character array this constructor copies the usable part into a new character array.
	 * @param original the <code>CString</code> we are copying
	 */
	public CString(CString original) {
		if (original == null) {
			throw new IllegalArgumentException();
		}
		char [] copy;
		 
		if (original.data.length > original.length) {
		 
			copy = charArrayCopy(original.data, original.offset, original.length);
		} else {
		 
			copy = original.data;
		}
		
		this.offset=0;
		this.length=original.length;
		this.data=copy;
	}
	/**
	 * Initializes a new <code>CString</code> as a copy of a <code>String</code> argument
	 * @param original the <code>String</code> whose contents we are copying
	 */
	public CString(String original) {
		if (original == null) {
			throw new IllegalArgumentException("Null reference not allowed");
		}
		final int len = original.length();
		char[] copy = new char[len];
		for (int i=0; i<len; i++) {
			copy[i]=original.charAt(i);
		}
		this.data = copy;
		this.length = len;
		this.offset=0;
	}
	/**
	 * This private constructor is used for creating CStrings who share references of their character array.
	 * The general idea is to avoid copying data if it isn't necessary. This enables methods like substring to be performed much faster.
	 * @param data the shared character array
	 * @param offset signifies the index of the 1st character
	 * @param length signifies how many characters
	 * @param flag signifies that we wish to use this constructor for copying references
	 */
	private CString(char[] data, int offset, int length, boolean flag) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		if (flag) {
			this.data=data;
			this.offset=offset;
			this.length=length;
		} else {
			throw new IllegalArgumentException("This constructor is only used for copying references");
		}
	}
	/**
	 * Makes a new subarray copy from the argument character array determined by the offset and length.
	 * @param data the array we are copying
	 * @param offset signifies the starting index of our new subarray
	 * @param length how many characters we will have, starting from offset
	 * @return reference to newly made copy
	 * @throws StringIndexOutOfBoundsException if we can't make an array due to bad indexing
	 */
	private char[] charArrayCopy(char[] data, int offset, int length) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		if (offset<0) {
			throw new StringIndexOutOfBoundsException();
		}
		if (length<0) {
			throw new StringIndexOutOfBoundsException();
		}
		if (offset > data.length - length) {
			throw new StringIndexOutOfBoundsException(offset+length);
		}
		char[] ret = new char [length];
		for (int i=0; i<ret.length; i++) {
			ret[i]=data[i+offset];
		}
		return ret;
		
	}
	/**
	 * Getter method for length
	 * @return the length of <code>CString</code> instance
	 */
	public int length() {
		return length;
	}
	/**
	 * Retrieves the character found at the user provided index.
	 * @param index the index of the searched character
	 * @return the character found at index
	 */
	public char charAt(int index) {
		if (index < 0) {
			throw new StringIndexOutOfBoundsException("String index out of range: "+index);
		}
		if (index+offset > length-1) {
			throw new StringIndexOutOfBoundsException("CString index out of range: "+index);
		}
		return data[offset+index];
	}
	/**
	 * Returns a char[] array representation of the <code>CString</code>
	 * @return the character array representing the <code>CString</code>
	 */
	public char[] toCharArray() {
		return charArrayCopy(data, offset, length);
	}
	/**
	 * Returns a <code>String</code> representation of the <code>CString</code> for easy output.
	 */
	public String toString() {
		return new String(this.toCharArray());
	}
	/**
	 * Finds and returns the index of the first character c appearing in the <code>CString</code>
	 * @param c the character we're searching
	 * @return the index of the found character or -1 if it's not found
	 */
	public int indexOf(char c) {
		for (int i=offset; i<data.length; i++) {
			if (c==data[i]) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Test if the <code>CString</code> starts with prefix s.
	 * @param s the prefix
	 * @return true if it starts with the prefix
	 */
	public boolean startsWith(CString s) {
		if (s.length>length) {
			return false;
		}
		for (int i=offset; i<s.length; i++) {
			if (data[i]!=s.data[i-offset]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Test if the <code>CString</code> ends with suffix s.
	 * @param s the suffix
	 * @return true if it ends with teh suffix prefix
	 */
	public boolean endsWith(CString s) {
		
		//can't be a suffix if it's longer
		if (s.length()>length) {
			return false;
		}
		
		int len = s.length()-1;
		
		//return true if the suffix is empty
		if (len<0) {
			return true;
		}
		//count backwards and check each character
		for (int i=(offset+length-1); i>=offset; i--) {
			if(data[i]!=s.data[len--]) {
				return false;
			}
			//if we've checked every character leave and return true
			if (len<0) break;
		}
		return true;
	}
	/**
	 * Checks if the <code>CString</code> contains the substring s anywhere.
	 * @param s the substring we're searching for
	 * @return true if found
	 */
	public boolean contains(CString s) {
		if(s.length()==0) {
			return true;
		}
		
		if (s.length()>this.length) {
			return false;
		}
		
		int suboff = s.offset;
		for (int i=this.offset; i<this.length; i++) {
			if (this.charAt(i) == s.charAt(suboff)) {
				int foundIndex = i;
				int foundIndexS = suboff;
				while (true) {
					
					if ((foundIndexS-suboff)==s.length()) {
						return true;
					}
					
					if (this.charAt(foundIndex)==s.charAt(foundIndexS)) {
						foundIndex++;
						foundIndexS++;
					} else {
						i+=foundIndex;
						break;
					}
					
				}
			}
		}
		return false;
	}
	/**
	 * Returns a substring starting from startIndex to endIndex.
	 * @param startIndex the beginning index of our substring
	 * @param endIndex the index of the last character NOT contained by the substring
	 * @return the asked substring
	 * @throws StringIndexOutOfBoundsException if bad indexing
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new StringIndexOutOfBoundsException("CString index out of range"+startIndex);
		}
		if (endIndex<startIndex) {
			throw new StringIndexOutOfBoundsException("CString index out of range"+(endIndex-startIndex));
		}
		return new CString(this.data, offset+startIndex, endIndex-startIndex, true);
	}
	/**
	 * Makes a substring of first n characters
	 * @param n how many characters counting from the start
	 * @return a substring containing the first n characters
	 */
	public CString left(int n) {
		if (n==0) {
			return new CString("");
		}
		if (n<0) {
			throw new IllegalArgumentException("argument must be positive");
		}
		CString ret = null;
		try {
			ret = this.substring(0, n);
		} catch (StringIndexOutOfBoundsException ex) {
			throw ex;
		}
		return ret;
	}
	/**
	 * Makes a substring of last n characters
	 * @param n how many characters counting from the end
	 * @return a substring containing the last n characters
	 */
	public CString right(int n) {
		if (n==0) {
			return new CString("");
		}
		if (n<0) {
			throw new IllegalArgumentException("argument must be positive");
		}
		CString ret = null;
		try {
			ret = this.substring(this.length-n, this.length);
		} catch (StringIndexOutOfBoundsException ex) {
			throw ex;
		}
		return ret;
	}
	/**
	 * Concatenates two <code>CStrings</code> and returns reference to newly created concatenated <code>CString</code>
	 * @param s the <code>CString</code> we are concatenating
	 * @return the reference to the newly created <code>CString</code>
	 */
	public CString add(CString s) {
		char[] old1 = this.data;
		char[] old2 = s.data;
		int old1len = this.length();
		int old2len = s.length();
		char[] newData = new char[old1len+old2len];
		for (int i=this.offset; i<old1len; i++) {
			newData[i-offset]=old1[i];
		}
		for (int i=old1len; i<newData.length; i++) {
			newData[i]=old2[i-old1.length+s.offset];
		}
		return new CString(newData);
	}
	/**
	 * Replaces all occurrence of oldChar with newChar in the <code>CString</code>
	 * @param oldChar the character we are replacing
	 * @param newChar the replacement
	 * @return reference to newly created <code>CString</code>
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] copy = new char[this.length()];
		for (int i=0; i<copy.length; i++) {
			if (data[i+offset]==oldChar) {
				copy[i]=newChar;
			} else {
				copy[i]=data[i+offset];
			}
		}
		return new CString(copy);
	}
	
	/** 
	 * Creates a pattern table used for our pattern searching algorithm method
	 * @param ptrn the pattern we're searching
	 * @return 
	 */
	private int[] preProcessPattern(char[] ptrn) {
	    int i = 0, j = -1;
	    int ptrnLen = ptrn.length;
	    int[] b = new int[ptrnLen + 1];
	 
	    b[i] = j;
	    while (i < ptrnLen) {            
	            while (j >= 0 && ptrn[i] != ptrn[j]) {
	            // if there is mismatch consider the next widest border
	            // The borders to be examined are obtained in decreasing order from 
	            //  the values b[i], b[b[i]] etc.
	            j = b[j];
	        }
	        i++;
	        j++;
	        b[i] = j;
	    }
	    return b;
	}
	/**
	 * Searches a string and builds an array containing the starting indices of all the places where the substring ptrn can be found.
	 * Based on the KMP string search algorithm
	 * @param text the string we're searching through
	 * @param ptrn the substring we're looking for
	 * @return an array containing all the starting indices of every occurrence of our substring ptrn
	 */
	private int[] searchSubString(char[] text, char[] ptrn) {
        int i = 0, j = 0;
        // pattern and text lengths
        int ptrnLen = ptrn.length;
        int txtLen = text.length;
        int[] matrix = new int[txtLen];
        int ind=0;
        int count=0;
        // initialize new array and preprocess the pattern
        int[] b = preProcessPattern(ptrn);
 
        while (i < txtLen) {
            while (j >= 0 && text[i] != ptrn[j]) {
                j = b[j];
            }
            i++;
            j++;
 
            // a match is found
            if (j == ptrnLen) {
            	matrix[ind++]=i-ptrnLen;
            	count++;
                j = b[j];
            }
        }
        int[] ret = new int[count];
        for (i=0; i<ret.length; i++) {
        	ret[i]=matrix[i];
        }
        return ret;
    }
	/**
	 * Replaces all occurrences of substring oldStr with newStr regardless of length.
	 * @param oldStr the substring we wish to replace
	 * @param newStr what we're replacing the substring
	 * @return reference to newly created <code>CString</code>
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		int[] indices = this.searchSubString(this.toCharArray(), oldStr.toCharArray());
		char[] copy = new char[this.length()-indices.length*oldStr.length()+indices.length*newStr.length()];
		int i=0; //index for original string
		int j=0; //index for indices
		int index=0; //index for copy
		while(i<this.length()) {
			if (j<=indices.length-1 && i==indices[j]) { //if we find a substring
				for(int k=0; k<newStr.length(); k++) { //in copy put replacement substring
					copy[index+k]=newStr.charAt(k);
				}
				index+=newStr.length(); //in copy skip over replacement substring
				i+=oldStr.length(); //in original string skip over original substring
				j++; //we're done with this index, move on to the next one in indices
				continue;
			}
			copy[index]=this.charAt(i); //keep copying characters that aren't in the substring
			index++;
			i++;
		}
		return new CString(copy); //return the newly made copy
	}
}
