package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;

/**
 * Modellizza un file XML.
 * @author Nicol Stocchetti
 */
public class XMLObject {
	private String XMLDeclarationVersion;
	private String XMLDeclarationEncoding;
	private ArrayList <String> textBeforeRoot;
	private XMLElement rootElement;
	private ArrayList <String> textAfterRoot;
	
	/**
	 * Il metodo costruttore.
	 * @param _XMLDeclaration la XML declaration, String.
	 * @param _textBeforeRoot testo inserito prima dell'elemento root, ArrayList <String>.
	 * @param _rootElement l'elemento radice, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 * @param _textAfterRoot testo inserito dopo l'elemento root, ArrayList <String>.
	 */
	public XMLObject(String _XMLDeclarationVersion, String _XMLDeclarationEncoding, ArrayList <String> _textBeforeRoot, XMLElement _rootElement, ArrayList <String> _textAfterRoot) {
		this.XMLDeclarationVersion = _XMLDeclarationVersion;
		this.XMLDeclarationEncoding = _XMLDeclarationEncoding;
		this.textBeforeRoot = _textBeforeRoot;
		this.rootElement = _rootElement;
		this.textAfterRoot = _textAfterRoot;
	}

	/**
	 * Ritorna la versione dell'XML.
	 * @return la versione, String
	 */
	public String getXMLDeclarationVersion() {
		return XMLDeclarationVersion;
	}
	
	/**
	 * Ritorna l'encoding dell'XML.
	 * @return l'encoding, String.
	 */
	public String getXMLDeclarationEncoding() {
		return XMLDeclarationEncoding;
	}
	
	/**
	 * Ritorna l'elemento root dell'XML.
	 * @return l'elemento root, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 */
	public XMLElement getRootElement() {
		return rootElement;
	}
	
	/**
	 * Ritorna eventuali elementi testuali presenti prima dell'elemento root.
	 * @return ArrayList <String>.
	 */
	public ArrayList <String> getTextBeforeRoot() {
		return textBeforeRoot;
	}
	
	/**
	 * Ritorna eventuali elementi testuali presenti dopo l'elemento root.
	 * @return ArrayList <String>.
	 */
	public ArrayList <String> getTextAfterRoot() {
		return textAfterRoot;
	}
	
	/**
	 * Ritorna una stringa di testo che descrive il file XML.
	 * @return String.
	 */
	@Override
	public String toString() {
		String output;
		
		output = "<?xml";
		output += " version = \"" + this.XMLDeclarationVersion + "\"";
		output += " encoding = \"" + this.XMLDeclarationEncoding + "\"";
		output += "?>";
		for (String text : this.textBeforeRoot) {
			output += text;
		}
		
		output += this.rootElement.toString();
		
		for (String text : this.textAfterRoot) {
			output += text;
		}
		return output;
	}
}
