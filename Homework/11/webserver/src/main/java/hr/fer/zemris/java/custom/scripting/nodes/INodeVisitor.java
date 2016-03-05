package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This interface defines a <code>Visitor</code> implementation which is used to
 * visit every element inside our <code>SmartScript</code> parsetree.
 * 
 * @author Filip Džidić
 *
 */
public interface INodeVisitor {
	/**
	 * Visits <code>TextNode</code> nodes and performs some defined operation.
	 * 
	 * @param node
	 *            the <code>TextNode</code> being visited
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits <code>ForLoopNode</code> nodes and performs some defined
	 * operation.
	 * 
	 * @param node
	 *            the <code>ForLoopNode</code> being visited
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits <code>EchoNode</code> nodes and performs some defined operation.
	 * 
	 * @param node
	 *            the <code>EchoNode</code> being visited
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits <code>DocumentNode</code> nodes and performs some defined
	 * operation.
	 * 
	 * @param node
	 *            the <code>DocumentNode</code> being visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
