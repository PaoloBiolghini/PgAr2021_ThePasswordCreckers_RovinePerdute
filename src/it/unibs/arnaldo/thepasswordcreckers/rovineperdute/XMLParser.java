package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Gestisce la lettura e scrittura su file XML, contiene metodi per il parsing
 * da file XML a it.unibs.arnaldo.thepasswordcreckers.rovineperdute.XMLObject e viceversa.
 * @author Nicol Stocchetti.
 *
 */
public class XMLParser {
	private static XMLInputFactory inputFactory = creaInputFactory();
	private static XMLOutputFactory outputFactory = creaOutputFactory();
	
	private static XMLStreamReader reader = null;
	private static XMLStreamWriter writer = null;

	/**
	 * Crea e ritorna un oggetto di tipo XMLInputFactory, che viene usato per inizializzare
	 * la relativa variabile di classe.
	 * @return la Input Factory, XMLInputFactory.
	 */
	private static XMLInputFactory creaInputFactory() {
		XMLInputFactory inFactory = null;
		try {
			inFactory = XMLInputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione dell'Input Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return inFactory;
	}
	
	/**
	 * Crea e ritorna un oggetto di tipo XMLOutputFactory, che viene usato per inizializzare
	 * la relativa variabile di classe.
	 * @return la Output Factory, XMLOutputFactory.
	 */
	private static XMLOutputFactory creaOutputFactory() {
		XMLOutputFactory outFactory = null;
		try {
			outFactory = XMLOutputFactory.newInstance();
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione dell'Output Factory: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
		return outFactory;
	}
	
	/**
	 * Inizializza lo stream reader, fornendogli il file XML da leggere, se è impossibile accedere al file termina il programma.
	 * @param percorsoFile il percorso del file da leggere, String.
	 * @return lo stream reader, XMLStreamReader.
	 */
	public static XMLStreamReader impostaReader(String percorsoFile) {
		try {
			XMLParser.reader = XMLParser.inputFactory.createXMLStreamReader(percorsoFile, new FileInputStream(percorsoFile));
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione del reader: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // dato che non c'è il file è inutile che il programma prosegua
		}
		return XMLParser.reader; //in teoria non dovrebbe comunque servire in quanto tutto il parsing XML viene gestito
								  //esclusivamente all'interno dei metodi di questa classe.
	}
	
	/**
	 * Inizializza lo stream writer e crea il documento XML su cui scrivere, se è impossibile creare il file termina il programma.
	 * @param percorsoFile il percorso del file da creare, String.
	 * @return lo stream writer, XMLStreamWriter.
	 */
	public static XMLStreamWriter impostaWriter(String percorsoFile) {
		try {
			XMLParser.writer = XMLParser.outputFactory.createXMLStreamWriter(new FileOutputStream(percorsoFile), "utf-8");
			//XMLParser.writer.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.err.println("Errore nell'inizializzazione del writer: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			System.exit(0); // dato che non c'è il file è inutile che il programma prosegua
		}
		return XMLParser.writer; //in teoria non dovrebbe comunque servire in quanto tutto il parsing XML viene gestito
		  						  //esclusivamente all'interno dei metodi di questa classe.
	}

	/**
	 * Ritorna l'ultimo reader creato.
	 * @return il reader, XMLStreamReader.
	 */
	/*public static XMLStreamReader getReader() {
		return reader;
	}*/

	/**
	 * Ritorna l'ultimo writer creato.
	 * @return il writer, XMLStreamWriter.
	 */
	/**public static XMLStreamWriter getWriter() {
		return writer;
	}*/
	
	/**
	 * Crea un oggetto di tipo XMLObject parsando il contenuto di un file XML, in caso di errore ritorna null.
	 * @param percorsoFile il percorso del file XML da parsare, String.
	 * @return un oggetto contenente i dati parsati a partire da un file XML (oppure null in caso di errore), XMLObject.
	 */
	public static XMLObject estraiXMLObject(String percorsoFile) {
		XMLObject oggetto = null;
		String XMLDeclarationVersion = "";
		String XMLDeclarationEncoding = "";
		ArrayList <String> textBeforeRoot = new ArrayList <String>();
		XMLElement rootElement = null;
		ArrayList <String> textAfterRoot = new ArrayList <String>();
		
		boolean rootEstratto = false;
		
		try {
			impostaReader(percorsoFile);
			while (reader.hasNext()) { //finché ci sono eventi da leggere...
				switch (reader.getEventType()) { //guarda il tipo di evento
					case XMLStreamConstants.START_DOCUMENT: //inizio del documento -> estrae la declaration
						XMLDeclarationVersion = reader.getVersion();
						XMLDeclarationEncoding = reader.getCharacterEncodingScheme();
						textBeforeRoot.add("\n"); //Non so perché non prende la formattazione qui come CHARACTERS...
						break;
					
					case XMLStreamConstants.START_ELEMENT: //inizio di un elemento -> è l'elemento radice, lo estrae
						rootElement = estraiXMLElement();
						rootEstratto = true;
						break;
					
					case XMLStreamConstants.COMMENT: //i commenti non vanno parsati
						break;
						
					case XMLStreamConstants.CHARACTERS: //testo all’interno di un elemento -> lo estraggo
						if (rootEstratto) {
							textAfterRoot.add(reader.getText());
						} else {
							textBeforeRoot.add(reader.getText());
						}
						break;
					
					default:
						break;
				}
				reader.next();
			}
			oggetto = new XMLObject(XMLDeclarationVersion, XMLDeclarationEncoding, textBeforeRoot, rootElement, textAfterRoot);
		} catch (XMLStreamException e) {
			System.err.println("Eccezione: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			oggetto = null;
		}
		return oggetto;
	}
	
	/**
	 * Crea un oggetto di tipo XMLObject parsando il contenuto di del file "./xml/input.xml", in caso di errore ritorna null.
	 * @return un oggetto contenente i dati parsati a partire da un file XML (oppure null in caso di errore), XMLObject.
	 */
	public static XMLObject estraiXMLObject() {
		return estraiXMLObject("./xml/input.xml");
	}
	
	/**
	 * Crea un oggetto di tipo XMLElement parsando il contenuto di un elemento XML, partendo a scorrere dal tag di apertura
	 * fino ad arrivare al corrispondente tag di chiusura. In caso di errore ritorna null.
	 * @return un oggetto contenente i dati parsati a partire da un elemento XML (oppure null in caso di errore), XMLElement.
	 */
	private static XMLElement estraiXMLElement() {
		XMLElement elemento = null;
		
		try {
			//il reader è sul tag d'inizio dell'elemento -> crea l'elemento parsando il nome del tag e i suoi attributi
			elemento = new XMLElement(reader.getLocalName());
			for (int i = 0; i < reader.getAttributeCount(); i++) {
				elemento.addAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
			} 
			reader.next();//prossimo evento
			while (reader.hasNext()) { //finché ci sono eventi da leggere...
				switch (reader.getEventType()) { //guarda il tipo di evento
					case XMLStreamConstants.START_ELEMENT: // inizio di un elemento figlio -> richiamo la funzione e lo aggiungo ai contenuti di questo elemento
						elemento.addChildElement(estraiXMLElement());
						break;
					
					case XMLStreamConstants.END_ELEMENT: // fine dell'elemento -> esco
						return elemento;
					
					case XMLStreamConstants.COMMENT: //i commenti non vanno parsati
						break;
					
					case XMLStreamConstants.CHARACTERS: //testo all’interno di un elemento -> lo estraggo e aggiungo ai contenuti di questo elemento
						elemento.addText(reader.getText());
						break;
						
					default:
						break;
				}
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.err.println("Eccezione: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
			elemento = null;
		}
		return elemento;
	}
	
	/**
	 * A partire da un oggetto di tipo XMLElement, scrive su file il codice XML dell'elemento che esso rappresenta.
	 * @param element l'oggetto da tradurre in XML, XMLElement.
	 */
	private static void scriviElemento(XMLElement element) {
		try {
			if(element.getContent() == null) {
				writer.writeEmptyElement(element.getTag()); //apertura del tag
				
				if (element.getAttributes() != null) { //scrittura attributi
					for (String key : element.getOrderedAttributes()) {
						writer.writeAttribute(key, element.getAttributes().get(key));
					}
				}
			} else {
				writer.writeStartElement(element.getTag()); //apertura del tag
				
				if (element.getAttributes() != null) { //scrittura attributi
					for (String key : element.getOrderedAttributes()) {
						writer.writeAttribute(key, element.getAttributes().get(key));
					}
				}
				
				//scrittura contenuto
				for (Object obj : element.getContent()) {
					if (obj instanceof String) {
						writer.writeCharacters((String)obj);
					}
					if (obj instanceof XMLElement) {
						scriviElemento((XMLElement)obj);
					}
				}
				
				writer.writeEndElement(); //chiusura del tag
			}
			
		} catch (Exception e) {
			System.err.println("Errore nella scrittura di un elemento XML: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
	
	/**
	 * A partire da un oggetto di tipo XMLObject genera un file XML nella directory specificata.
	 * @param object l'oggetto di partenza, XMLObject.
	 * @param percorsoFile il percorso del file XML da creare, String.
	 */
	public static void scriviDocumento(XMLObject object, String percorsoFile) {
		try {
			impostaWriter(percorsoFile);
			
			writer.writeStartDocument(object.getXMLDeclarationEncoding(), object.getXMLDeclarationVersion());
			
			if (object.getTextBeforeRoot() != null) {
				for (String text : object.getTextBeforeRoot()) {
					writer.writeCharacters(text);
				}
			}
			
			if(object.getRootElement() != null) {
				scriviElemento(object.getRootElement());
			}
			
			if (object.getTextAfterRoot() != null) {
				for (String text : object.getTextBeforeRoot()) {
					writer.writeCharacters(text);
				}
			}
			
			writer.writeEndDocument(); // scrittura della fine del documento
			writer.flush(); // svuota il buffer e procede alla scrittura
			writer.close(); // chiusura del documento e delle risorse impiegate
			
		} catch (Exception e) { // se c’è un errore viene eseguita questa parte
			System.err.println("Errore nella scrittura del file XML: " + e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
	
	/**
	 * A partire da un oggetto di tipo XMLObject genera il file XML "./xml/output.xml".
	 * @param object l'oggetto di partenza, XMLObject.
	 */
	public static void scriviDocumento(XMLObject object) {
		scriviDocumento(object, "./xml/output.xml");
	}
	
}

