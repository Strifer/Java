package hr.fer.zemris.java.student0036474052.hw06;

import java.io.BufferedInputStream;
import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Konzolni program za testiranje cijelih brojeva
 * @author Filip Džidić
 * @version 1.0
 *
 */
public class CijeliBrojeviKonzola {
	/**
	 * Glavna metoda čita korisnikov input kao cijele brojeve i za svaki broj ispisuje je li paran, neperan i prost.
	 * Naredbom quit završava rad programa
	 * @param args ne primaju se argumenti prije poziva
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));
		while(true) {
			System.out.print("Unesite broj> ");
			String line=reader.readLine().trim();
			if(line.equals("quit")) {
				break;
			}
			int broj;
			try {
				broj=Integer.parseInt(line);
			} catch (NumberFormatException e) {
				System.err.println("Invalid format please try again");
				continue;
			}
			System.out.println("Paran: "+jeParan(broj)+", neparan "+jeNeparan(broj)+", prim: "+jePrim(broj));
		}
		System.out.println("Hvala na druženju.");
	}
	/**
	 * Testira je li zadani broj paran
	 * @param broj broj koji testiramo
	 * @return "DA" ako je
	 */
	public static String jeParan(int broj) {
		if (CijeliBrojevi.jeParan(broj)) {
			return "DA";
		} else {
			return "NE";
		}
	}
	/**
	 * Testira je li zadani broj neparan
	 * @param broj broj koji testiramo
	 * @return "DA" ako je
	 */
	public static String jeNeparan(int broj) {
		if (CijeliBrojevi.jeNeparan(broj)) {
			return "DA";
		} else {
			return "NE";
		}
	}
	/**
	 * Testira je li zadani broj prost
	 * @param broj broj koji testiramo
	 * @return "DA" ako je
	 */
	public static String jePrim(int broj) {
		if (CijeliBrojevi.jeProst(broj)) {
			return "DA";
		} else {
			return "NE";
		}
	}
}
