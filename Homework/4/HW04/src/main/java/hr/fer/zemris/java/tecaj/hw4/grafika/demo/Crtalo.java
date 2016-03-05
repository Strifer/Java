package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.Elipsa;
import hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kruznica;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kvadrat;
import hr.fer.zemris.java.tecaj.hw4.grafika.Linija;
import hr.fer.zemris.java.tecaj.hw4.grafika.Pravokutnik;
import hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;
/**
 * Razred koji služi za crtanje geometrijskih likova koje stvara čitajući ih iz datoteke.
 * @author Filip Džidić
 *
 */
public class Crtalo {
	/**
	 * Glavna metoda koja stvara sliku i prikazuje je na ekranu.
	 * Prima tri argumenta preko komandne linije.
	 * <br>Prvi argument predstavlja put do datoteke iz koje čitamo.
	 * <br>Drugi element predstavlja širinu slike
	 * <br>Treći argument predstavlja visinu slike
	 * @param args 3 argumenta koje prima preko komandne linije
	 * @throws IOException ako datoteka nije uspješno pronađena
	 * @throws IllegalArgumentException ako su argumenti pogrešno predani
	 */
	public static void main(String[] args) throws IOException {
		if (args.length!=3) {
			throw new IllegalArgumentException("nepravilno predani argumenti");
		}
		int sirina = Integer.parseInt(args[1]);
		int visina = Integer.parseInt(args[2]);
		
		if (sirina<=0 || visina <= 0) {
			throw new IllegalArgumentException("sirina i visina slike ne mogu biti negativni");
		}
		SimpleHashtable stvaratelji = podesi(Linija.class, Pravokutnik.class, Kvadrat.class, Kruznica.class, Elipsa.class);
		String[] definicije = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8).toArray(new String[0]);
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		int index=0;
		for (String d : definicije) {
			String[] polje = d.split("[ ]", 2);
			String lik = polje[0];
			String parametri = polje[1];
			StvarateljLika stvaratelj = (StvarateljLika)stvaratelji.get(lik);
			GeometrijskiLik l = null;
			try {
				l = stvaratelj.stvoriIzStringa(parametri);
			} catch (IllegalArgumentException e) {
				System.out.println("Greška u liniji "+(index+1)+" datoteke. Parametri nisu dobro inicijalizirani");
				System.out.println("Završavam rad bez crtanja slike");
				e.printStackTrace();
				System.exit(-1);
			} catch (NullPointerException e) {
				System.out.println("Greška u liniji " +(index+1)+" datoteke. Nedifiniran naziv. Završavam rad bez crtanja slike");
				e.printStackTrace();
				System.exit(-1);
			}
			likovi[index++]=l;
		}
		
		Slika slika = new Slika(sirina, visina);
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		PrikaznikSlike.prikaziSliku(slika);
	}
	
	/**
	 * Metoda podešava <code>SimpleHashtable</code> za primanje razreda koje korisnik definira
	 * @param razredi razrede koje hashtable može primiti
	 * @return podešeni hashtable
	 */
	private static SimpleHashtable podesi(Class<?> ... razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for (Class<?> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika)field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch (Exception ex) {
				throw new RuntimeException(
						"Nije moguće doći do stvaratelja za razred "+razred.getName()+".", ex);
			}
		}
		return stvaratelji;
	}
	
	
}
