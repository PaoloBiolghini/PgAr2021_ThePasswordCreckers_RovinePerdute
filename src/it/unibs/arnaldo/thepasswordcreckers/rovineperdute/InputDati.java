package it.unibs.arnaldo.thepasswordcreckers.rovineperdute;

import java.util.*;

public class InputDati {

	private final static String ERRORE_FORMATO = "Attenzione: il dato inserito presenta un formato errato! ";
	private final static String ERRORE_MINIMO = "Attenzione: il valore inserito deve essere maggiore o uguale a ";
	private final static String ERRORE_STRINGA_VUOTA = "Attenzione: nessun carattere inserito! ";
	private final static String ERRORE_MASSIMO = "Attenzione: il valore inserito deve essere minore o uguale a ";
	private final static String MESSAGGIO_AMMISSIBILI = "Attenzione: i caratteri ammissibili sono ";
	private final static String ERRORE_MIN_MAX = "Attenzione: il valore inserito deve essere maggiore o uguale a %d e minore o uguale a %d ";

	private final static char RISPOSTA_SI = 'S';
	private final static char RISPOSTA_NO = 'N';

	private static Scanner lettore = creaScanner();

	private static Scanner creaScanner() {
		Scanner creato = new Scanner(System.in);
		//creato.useDelimiter(System.getProperty("line.separator"));
		return creato;
	}

	public static String leggiStringa(String messaggio) {
		System.out.print(messaggio);
		return lettore.next();
	}

	public static String leggiStringaNonVuota(String messaggio) {
		boolean finito = false;
		String lettura = null;
		do {
			lettura = leggiStringa(messaggio);
			lettura = lettura.trim();
			if (lettura.length() > 0)
				finito = true;
			else
				System.out.println(ERRORE_STRINGA_VUOTA);
		} while (!finito);

		return lettura;
	}

	public static char leggiChar(String messaggio) {
		boolean finito = false;
		char valoreLetto = '\0';
		do {
			System.out.print(messaggio);
			String lettura = lettore.next();
			if (lettura.length() > 0) {
				valoreLetto = lettura.charAt(0);
				finito = true;
			} else {
				System.out.println(ERRORE_STRINGA_VUOTA);
			}
		} while (!finito);
		return valoreLetto;
	}

	public static char leggiUpperChar(String messaggio, String ammissibili) {
		boolean finito = false;
		char valoreLetto = '\0';
		do {
			valoreLetto = leggiChar(messaggio);
			valoreLetto = Character.toUpperCase(valoreLetto);
			if (ammissibili.indexOf(valoreLetto) != -1)
				finito = true;
			else
				System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
		} while (!finito);
		return valoreLetto;
	}

	public static int leggiIntero(String messaggio) {
		boolean finito = false;
		int valoreLetto = 0;
		do {
			System.out.print(messaggio);
			try {
				valoreLetto = lettore.nextInt();
				finito = true;
			} catch (InputMismatchException e) {
				System.out.println(ERRORE_FORMATO);
				lettore.next(); // butta via quello che c'� ora nel buffer
			}
		} while (!finito);
		return valoreLetto;
	}

	public static int leggiIntero(String messaggio, int minimo, int massimo) {
		boolean finito = false;
		int valoreLetto = 0;
		do {
			valoreLetto = leggiIntero(messaggio);
			if (valoreLetto >= minimo && valoreLetto <= massimo)
				finito = true;
			else
				System.out.println(String.format(ERRORE_MIN_MAX, minimo, massimo));
		} while (!finito);

		return valoreLetto;
	}

	public static int leggiInteroPositivo(String messaggio) {
		return leggiInteroConMinimo(messaggio, 1);
	}

	public static int leggiInteroNonNegativo(String messaggio) {
		return leggiInteroConMinimo(messaggio, 0);
	}

	public static int leggiInteroConMinimo(String messaggio, int minimo) {
		boolean finito = false;
		int valoreLetto = 0;
		do {
			valoreLetto = leggiIntero(messaggio);
			if (valoreLetto >= minimo)
				finito = true;
			else
				System.out.println(ERRORE_MINIMO + minimo);
		} while (!finito);

		return valoreLetto;
	}

	public static int leggiInteroConMassimo(String messaggio, int massimo) {
		boolean finito = false;
		int valoreLetto = 0;
		do {
			valoreLetto = leggiIntero(messaggio);
			if (valoreLetto <= massimo)
				finito = true;
			else
				System.out.println(ERRORE_MASSIMO + massimo);
		} while (!finito);

		return valoreLetto;
	}

	public static double leggiDouble(String messaggio) {
		boolean finito = false;
		double valoreLetto = 0;
		do {
			System.out.print(messaggio);
			try {
				valoreLetto = lettore.nextDouble();
				finito = true;
			} catch (InputMismatchException e) {
				System.out.println(ERRORE_FORMATO);
				lettore.next(); // butta via quello che c'� ora nel buffer
			}
		} while (!finito);
		return valoreLetto;
	}

	public static double leggiDouble(String messaggio, double minimo, double massimo) {
		boolean finito = false;
		double valoreLetto = 0;
		do {
			valoreLetto = leggiDouble(messaggio);
			if (valoreLetto >= minimo && valoreLetto <= massimo)
				finito = true;
			else
				System.out.println(String.format(ERRORE_MIN_MAX.replace("%d", "%.2f"), minimo, massimo));
		} while (!finito);

		return valoreLetto;
	}

	public static double leggiDoublePositivo(String messaggio) {
		return leggiDoubleConMinimo(messaggio, 1.0);
	}

	public static double leggiDoubleNonNegativo(String messaggio) {
		return leggiDoubleConMinimo(messaggio, 0.0);
	}

	public static double leggiDoubleConMinimo(String messaggio, double minimo) {
		boolean finito = false;
		double valoreLetto = 0;
		do {
			valoreLetto = leggiDouble(messaggio);
			if (valoreLetto >= minimo)
				finito = true;
			else
				System.out.println(ERRORE_MINIMO + minimo);
		} while (!finito);

		return valoreLetto;
	}

	public static double leggiDoubleConMassimo(String messaggio, double massimo) {
		boolean finito = false;
		double valoreLetto = 0;
		do {
			valoreLetto = leggiDouble(messaggio);
			if (valoreLetto <= massimo)
				finito = true;
			else
				System.out.println(ERRORE_MASSIMO + massimo);
		} while (!finito);

		return valoreLetto;
	}

	public static boolean siONo(String messaggio) {
		String mioMessaggio = messaggio + "(" + RISPOSTA_SI + "/" + RISPOSTA_NO + ")";
		char valoreLetto = leggiUpperChar(mioMessaggio, String.valueOf(RISPOSTA_SI) + String.valueOf(RISPOSTA_NO));

		if (valoreLetto == RISPOSTA_SI)
			return true;
		else
			return false;
	}
	
	/**
	 * Verifica se un anno sia bisestile.
	 * @param anno l'anno da controllare, int.
	 * @return true se l'anno fornito � bisestile, altrimenti false, boolean.
	 */
	public static boolean verificaAnnoBisestile(int anno) {
		if (anno % 400 == 0)
			return true;
		else if(anno % 4 == 0 && anno % 100 != 0)
			return true; 
		else
			return false;
	}
	
	/**
	 * Verifica che una data sia corretta.
	 * @param giorno il giorno, int.
	 * @param mese il mese, int.
	 * @param anno l'anno, int.
	 * @return true se la data � corretta, altrimenti false, boolean.
	 */
	public static boolean verificaCorrettezzaData(int giorno, int mese, int anno) {
		if (mese < 1 || mese > 12)
			return false;
		if (giorno < 1 || giorno > 31)
			return false;
		if (giorno > 30 && (mese == 11 || mese == 4 || mese == 6 || mese == 9))
			return false;
		if (verificaAnnoBisestile(anno)) {
			if (mese == 2 && giorno > 29)
				return false;
		} else {
			if (mese == 2 && giorno > 28)
				return false;
		}
		return true;
	}
	
}