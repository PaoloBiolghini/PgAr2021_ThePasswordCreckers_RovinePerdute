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
			if(!(oggetto instanceof XMLElement)) {
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
			if(!(oggetto instanceof XMLElement)) {
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
	
	/**
	 * Crea un array di oggetti di tipo Nodo a partire da un XMLObject .
	 * @param fileXML l'oggetto da cui ricavare i dati, XMLObject.
	 * @return i nodi, ArrayList <Nodo>.
	 */
	public static ArrayList <Nodo> daXMLObjectANodi(XMLObject fileXML) {
		ArrayList <Nodo> nodi = new ArrayList <Nodo>();
		XMLElement city;
		
		//Scorre gli oggetti contenuti nell'elemento root del file XML, che corrispondono alle varie città
		for (Object oggetto : fileXML.getRootElement().getContent()) {
			//Controlla che l'oggetto sia un XMLElement, perché potrebbero anche esserci per esempio stringhe di testo tra un XMLElement e l'altro
			if(!(oggetto instanceof XMLElement)) {
				continue; //Se è un oggetto diverso dall'XMLElement non lo considera e passa all'oggetto successivo...
			}
			city = (XMLElement) oggetto;
					
			//Se per caso l'elemento non è di tipo city lo ignora.
			if (!city.getTag().equals("city")) {
				continue;
			}
			//Parsing dall'XMLElement contenente i dati della città alla classe Nodo (che rappresenta una città nel grafo).
			nodi.add(TraduttoreXMLObjectNodo.daXMLElementANodo(city));
		}
		
		//Ora ho una lista di nodi (città).		
		//Scorro ciascun nodo e imposto i suoi collegamenti con altri nodi.
		for (Nodo nodo : nodi) {
			TraduttoreXMLObjectNodo.impostaCollegamenti(nodo, nodi, fileXML.getRootElement());
		}	
		return nodi;
	}
	
	/**
	 * Crea un XMLElement a partire da un oggetto di tipo Nodo.
	 * @param nodo l'oggetto di partenza, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.Nodo.
	 * @param tab l'indentazione da dare agli elementi figli, String.
	 * @param nl gli a capo tra un elemento e l'altro, String.
	 * @return l'oggetto creato, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLElement.
	 */
	public static XMLElement daNodoAXMLElement(Nodo nodo, String tab, String nl) {
		String tag;
		HashMap <String, String> attributes = new HashMap <>();
		ArrayList<String> orderedAttributes = new ArrayList<String>();
		ArrayList<Object> content = new ArrayList<Object>();
		
		HashMap <String, String> attributesFiglio = new HashMap <>();
		ArrayList<String> orderedAttributesFiglio = new ArrayList<String>();
		
		tag = "city";
		
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
				content.add(nl + tab + tab);
				attributesFiglio.put("to", destinazione.getId() + "");
				content.add(new XMLElement("link", attributesFiglio, orderedAttributesFiglio, null));
			}
		}
		content.add(nl + tab);
		
		return new XMLElement(tag, attributes, orderedAttributes, content);
	}
	
	/**
	 * Crea un XMLObject a partire da un array di oggetti di tipo Nodo.
	 * @param nodi gli oggetti di partenza, ArrayList<Nodo>.
	 * @param tab l'indentazione da dare agli elementi figli, String.
	 * @param nl gli a capo tra un elemento e l'altro, String.
	 * @return l'oggetto creato, it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject.
	 */
	public static XMLObject daNodiAXMLObject(ArrayList<Nodo> nodi, String tab, String nl) {
		ArrayList<String> textBeforeRoot = new ArrayList<String>();
		textBeforeRoot.add(nl);
		ArrayList<String> textAfterRoot = new ArrayList<String>();
		textAfterRoot.add(nl);
		
		XMLElement elementoRoot;
		String tag;
		HashMap <String, String> attributes = new HashMap <>();
		ArrayList<String> orderedAttributes = new ArrayList<String>();
		ArrayList<Object> content = new ArrayList<Object>();
		
		tag = "map";
		orderedAttributes.add("size");
		attributes.put("size", nodi.size() + "");
		
		for (Nodo nodo : nodi) {
			content.add(nl + tab);
			content.add(daNodoAXMLElement(nodo, tab, nl));
		}
		content.add(nl);
		
		elementoRoot = new XMLElement(tag, attributes, orderedAttributes, content);
		
		return new XMLObject("1.0", "UTF-8", textBeforeRoot, elementoRoot, textAfterRoot);
	}
	
	/**
	 * Crea un XMLObject contenente la struttura del file Route.xml atteso in output.
	 * @param percorso1 il percorso della prima squadra.
	 * @param percorso2 il percorso della seconda squadra.
	 * @param tab l'indentazione da dare agli elementi figli, String.
	 * @param nl gli a capo tra un elemento e l'altro, String.
	 * @return la struttura del file Route.xml atteso in output, XMLObject.
	 */
	public static XMLObject daPercorsoAXMLObject(Percorso percorso1, Percorso percorso2, String tab, String nl) {
		ArrayList<String> textBeforeRoot = new ArrayList<String>();
		textBeforeRoot.add(nl);
		ArrayList<String> textAfterRoot = new ArrayList<String>();
		textAfterRoot.add(nl);
		
		XMLElement elementoRoot;
		String tag;
		ArrayList<Object> content = new ArrayList<Object>();
		
		XMLElement route;
		String tagr;
		HashMap <String, String> attributesr = new HashMap <>();
		ArrayList<String> orderedAttributesr = new ArrayList<String>();
		ArrayList<Object> contentr = new ArrayList<Object>();
		
		XMLElement city;
		String tagc;
		HashMap <String, String> attributesc = new HashMap <String, String>();
		ArrayList<String> orderedAttributesc = new ArrayList<String>();
		
		tag = "routes"; //non ha attributi
		
		//Route team 1
		tagr = "route";
		orderedAttributesr.add("team");
		attributesr.put("team", "Toniathu");
		orderedAttributesr.add("cost");
		attributesr.put("cost", /**/ String.format("%.2f", percorso1.getCostoTotale()));
		orderedAttributesr.add("cities");
		attributesr.put("cities", /**/ Integer.toString(percorso1.getPercorso().size()));
		
		tagc = "city";
		orderedAttributesc.add("id");
		orderedAttributesc.add("name");
		
		
		for (Nodo nodo : percorso1.getPercorso()) {
			attributesc = new HashMap <String, String>();
			attributesc.put("id", Integer.toString(/**/nodo.getId()));
			attributesc.put("name", /**/nodo.getNome());
			contentr.add(nl + tab + tab);
			contentr.add(new XMLElement(tagc, attributesc, orderedAttributesc, null));
		}
		contentr.add(nl + tab);
		
		content.add(nl + tab);
		content.add(new XMLElement(tagr, attributesr, orderedAttributesr, contentr));
		
		//Route team 2
		attributesr = new HashMap <>();
		contentr = new ArrayList<Object>();
		
		attributesc = new HashMap <String, String>();
		
		
		attributesr.put("team", /**/ "Metztli");
		attributesr.put("cost", /**/ String.format("%.2f", percorso2.getCostoTotale()));
		attributesr.put("cities", /**/ Integer.toString(percorso2.getPercorso().size()));
		
		for (Nodo nodo : /**/percorso2.getPercorso()) {
			attributesc = new HashMap <String, String>();
			attributesc.put("id", Integer.toString(/**/nodo.getId()));
			attributesc.put("name", /**/nodo.getNome());
			contentr.add(nl + tab + tab);
			contentr.add(new XMLElement(tagc, attributesc, orderedAttributesc, null));
		}
		contentr.add(nl + tab);
		
		content.add(nl + tab);
		content.add(new XMLElement(tagr, attributesr, orderedAttributesr, contentr));
		content.add(nl);
		
		elementoRoot = new XMLElement(tag, null, null, content);
		
		return new XMLObject("1.0", "UTF-8", textBeforeRoot, elementoRoot, textAfterRoot);
	}

}
