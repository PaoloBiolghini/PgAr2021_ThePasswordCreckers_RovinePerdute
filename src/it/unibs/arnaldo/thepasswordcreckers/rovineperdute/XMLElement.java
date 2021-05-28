package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Modellizza un elemento XML.
 * @author Nicol Stocchetti
 */
public class XMLElement {
	private String tag;
	private HashMap <String, String> attributes;
	private ArrayList <String> orderedAttributes; //salva le chiavi di attributes in un certo ordine (dato che nelle hash maps non è possibile mantenere un ordinamento)
	private ArrayList <Object> content; //il contenuto di questo elemento, un array ordinato di oggetti che possono essere
										//o stringhe di testo o altri XMLElements. Se è = null l'XMLElement corrisponde a un tag vuoto.
	
	/**
	 * Il metodo costruttore più completo.
	 * @param _tag il nome del tag di questo elemento, String.
	 * @param _attributes gli attributi di questo tag, HashMap <String, String>.
	 * @param _orderedAttributes le chiavi di _attributes messe in un array ordinato, ArrayList <String>.
	 * @param _content il contenuto di questo elemento, ArrayList <Object>.
	 */
	public XMLElement (String _tag, HashMap <String, String> _attributes, ArrayList <String> _orderedAttributes, ArrayList <Object> _content){
		this.tag = _tag;
		this.attributes = _attributes;
		this.orderedAttributes = _orderedAttributes;
		this.content = _content; 
	}
	
	/**
	 * Un metodo costruttore che crea un elemento vuoto e senza attributi.
	 * @param _tag il nome del tag di questo elemento, String.
	 */
	public XMLElement (String _tag){
		this(_tag, null, null, null);
	}
	
	/**
	 * Controlla se questo oggetto corrisponde a un elemento vuoto.
	 * @return true se è un elemento vuoto, altrimenti false.
	 */
	public boolean isEmpty() {
		return this.content == null;
	}
	
	/**
	 * Aggiunge un attributo al tag di questo elemento.
	 * @param name il nome dell'attributo, String.
	 * @param value il valore dell'attributo, String.
	 */
	public void addAttribute(String name, String value) {
		if (this.attributes == null) {
			this.attributes = new HashMap <String, String>();
			this.orderedAttributes = new ArrayList <String>();
		}
		this.attributes.put(name, value);
		this.orderedAttributes.add(name);
	}
	
	/**
	 * Aggiunge una stringa di testo al contenuto di questo elemento.
	 * @param text il testo, String.
	 */
	public void addText(String text) {
		if (this.content == null) {
			this.content = new ArrayList <Object>();
		}
		this.content.add(text);
	}
	
	/**
	 * Aggiunge un elemento figlio al contenuto di questo elemento.
	 * @param childElement l'elemento figlio, XMLElement.
	 */
	public void addChildElement(XMLElement childElement) {
		if (this.content == null) {
			this.content = new ArrayList <Object>();
		}
		this.content.add(childElement);
	}
	
	/**
	 * Ritorna una stringa di testo che descrive l'elemento XML.
	 * @return String.
	 */
	@Override
	public String toString() {
		String output;
		
		output = "<" + this.tag;
		
		if (this.attributes != null) {
			for (String key : this.orderedAttributes) {
				output += " " + key + " = \"" + this.attributes.get(key) + "\"";
			}
		}
		
		if (this.isEmpty()) {
			output += "/>";
			return output;
		}
			
		output +=">";
		
		if (this.content != null) {
			for (Object element : this.content) {
				output += element.toString();
			}
		}
		
		output += "</" + this.tag + ">";
		return output;
	}
	
	/**
	 * Ritorna il tag dell'elemento.
	 * @return il tag, String.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Ritorna gli attributi dell'elemento (disordinati).
	 * @return gli attributi, HashMap<String, String>.
	 */
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	
	/**
	 * Ritorna i nomi degli attributi dell'elemento nell'ordine in cui sono inseriti nel tag.
	 * @return i nomi degli attributi, ArrayList<String>.
	 */
	public ArrayList<String> getOrderedAttributes() {
		return orderedAttributes;
	}
	
	/**
	 * Ritorna il contenuto di questo elemento.
	 * @return gli oggetti contenuti in questo elemento, ArrayList<Object>.
	 */
	public ArrayList<Object> getContent() {
		return content;
	}

}
