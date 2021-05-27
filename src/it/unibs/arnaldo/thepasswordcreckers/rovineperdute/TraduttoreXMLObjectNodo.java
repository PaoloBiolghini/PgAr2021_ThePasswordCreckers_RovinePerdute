package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gestisce la creazione di oggetti di tipo Nodo a partire da XMLObjects o XMLElements che ne contengono le
 * proprietà e viceversa.
 * @author Nicol Stocchetti
 */
public class TraduttoreXMLObjectNodo {
	/**
	 * Crea un oggetto di tipo Nodo a partire da un XMLElement che ne contiene le proprietà.
	 * @param elemento l'oggetto da cui estrarre i dati, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 * @return l'oggetto creato, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Nodo.
	 */
	public static Nodo daXMLElementANodo(XMLElement elemento) {
		Nodo nodo;
		int id;
		String nome;
		int [] coordinate = new int [3];
		
		//Se l'elemento non è di tipo city si ferma (perché non è convertibile in Nodo) e ritorna null.
		if (!elemento.getTag().equals("city")) {
			return null;
		}
		
		id = Integer.parseInt(elemento.getAttributes().get("id"));
		nome = elemento.getAttributes().get("name");
		coordinate[0] = Integer.parseInt(elemento.getAttributes().get("x"));
		coordinate[1] = Integer.parseInt(elemento.getAttributes().get("y"));
		coordinate[2] = Integer.parseInt(elemento.getAttributes().get("h"));
	
		nodo = new Nodo(id, nome, coordinate, null);//Inizialmente crea solo l'oggetto nodo e non imposta i collegamenti, che verranno stabiliti
													//in un secondo momento, quando saranno già disponibili tutti quanti i nodi come istanze di Nodo
		return nodo;
	}
	
	/**
	 * Dato un oggetto di tipo Nodo, ne imposta i collegamenti con altri oggetti di tipo Nodo in base a quanto
	 * specificato nell'XMLElement di riferimento.
	 * @param nodo l'oggetto di cui si vogliono impostare i collegamenti, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Nodo.
	 * @param nodi i nodi disponibili per instaurare un collegamento, ArrayList<Nodo>.
	 * @param elementoRoot l'oggetto contenente le informazioni sui collegamenti da instaurare,  it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 */
	public static void impostaCollegamenti (Nodo nodo, ArrayList <Nodo> nodi, XMLElement elementoRoot) {
		HashMap <Nodo, Double> collegamenti = new HashMap <Nodo, Double>();
		String to;
		XMLElement city = null;
		XMLElement link = null;
		boolean trovato = false;
		
		//Cerca nei dati XML questa città
		for (Object oggetto : elementoRoot.getContent()) {
			//Controlla che l'oggetto sia un XMLElement, perché potrebbero anche esserci per esempio stringhe di testo tra un XMLElement e l'altro
			if(oggetto.getClass() != new XMLElement("").getClass()) {
				continue;
			}
			city = (XMLElement) oggetto;
			
			//Se per caso l'elemento non è di tipo city lo ignora.
			if (!city.getTag().equals("city")) {
				continue;
			}
			if(nodo.getId() == Integer.parseInt(city.getAttributes().get("id"))) {
				trovato = true;
				break;
			}
		}
		//Se non trovo nell'XML nessuna città corrispondente oppure se la trovo ma essa non contiene collegamenti il metodo termina
		if (!trovato || city.getContent() == null) {
			return;
		}
		
		for (Object oggetto : city.getContent()) {
			//Controlla che l'oggetto sia un XMLElement, perché potrebbero anche esserci per esempio stringhe di testo tra un XMLElement e l'altro
			if(oggetto.getClass() != new XMLElement("").getClass()) {
				continue;
			}
			link = (XMLElement) oggetto;
			//Se per caso l'elemento non è un collegamento lo ignora.
			if (!link.getTag().equals("link")) {
				continue;
			}
			to = link.getAttributes().get("to");//id della città collegata
			//Cerca tra i nodi quello corrispondente alla città da collegare e crea il collegamento ad essa.
			for (Nodo n : nodi) {
				if (n.getId() == Integer.parseInt(to)) {
					collegamenti.put(n, null);
					break;
				}	
			}
		}
		//Inserisce tutti i collegamenti in questo nodo.
		nodo.setCollegamenti(collegamenti);
	}
	
	/**-----SISTEMARE INDENTAZIONE-----
	 * Crea un XMLElement a partire da un oggetto di tipo Nodo.
	 * @param nodo l'oggetto di partenza, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Nodo.
	 * @return l'oggetto creato, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 */
	public static XMLElement daNodoAXMLElement(Nodo nodo) {
		String tag;
		HashMap <String, String> attributes = new HashMap <>();
		ArrayList<String> orderedAttributes = new ArrayList<String>();
		ArrayList<Object> content = new ArrayList<Object>();
		
		
		HashMap <String, String> attributesFiglio = new HashMap <>();
		ArrayList<String> orderedAttributesFiglio = new ArrayList<String>();
		
		tag = nodo.getNome();
		
		orderedAttributes.add("id");
		orderedAttributes.add("name");
		orderedAttributes.add("x");
		orderedAttributes.add("y");
		orderedAttributes.add("h");
		
		attributes.put("id", nodo.getId() + "");
		attributes.put("name", nodo.getNome());
		attributes.put("x", nodo.getCoordinate()[0] + "");
		attributes.put("y", nodo.getCoordinate()[1] + "");
		attributes.put("h", nodo.getCoordinate()[2] + "");
		
		orderedAttributesFiglio.add("to");
		
		//Per ogni città collegata a questo nodo crea un XMLElement "link" figlio.
		if(nodo.getCollegamenti() != null) {
			for (Nodo destinazione : nodo.getCollegamenti().keySet()) {
				attributesFiglio = new HashMap <>();
				content.add("\n\t\t");
				attributesFiglio.put("to", destinazione.getId() + "");
				content.add(new XMLElement("link", attributesFiglio, orderedAttributesFiglio, null));
			}
		}
		content.add("\n");
		
		return new XMLElement(tag, attributes, orderedAttributes, content);
	}
	
	/**------SISTEMARE INDENTAZIONE------
	 * Crea un XMLObject a partire da un array di oggetti di tipo Nodo.
	 * @param nodi gli oggetti di partenza, ArrayList<Nodo>.
	 * @return l'oggetto creato, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject.
	 */
	public static XMLObject daNodiAXMLObject(ArrayList<Nodo> nodi) {
		ArrayList<String> textBeforeRoot = new ArrayList<String>();
		textBeforeRoot.add("\n");
		ArrayList<String> textAfterRoot = new ArrayList<String>();
		textAfterRoot.add("\n");
		
		XMLElement elementoRoot;
		String tag;
		HashMap <String, String> attributes = new HashMap <>();
		ArrayList<String> orderedAttributes = new ArrayList<String>();
		ArrayList<Object> content = new ArrayList<Object>();
		
		tag = "map";
		orderedAttributes.add("size");
		attributes.put("size", nodi.size() + "");
		
		for (Nodo nodo : nodi) {
			content.add("\n\t");
			content.add(daNodoAXMLElement(nodo));
		}
		content.add("\n");
		
		elementoRoot = new XMLElement(tag, attributes, orderedAttributes, content);
		
		return new XMLObject("1.0", "UTF-8", textBeforeRoot, elementoRoot, textAfterRoot);
	}

}
