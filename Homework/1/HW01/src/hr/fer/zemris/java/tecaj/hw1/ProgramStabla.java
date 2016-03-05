package hr.fer.zemris.java.tecaj.hw1;

/**
 * Jednostavna implementacija podatkovne strukture binarnog stabla
 * 
 * @author ebin
 * @version 1.0
 *
 */

public class ProgramStabla {
	/**
	 * <code>CvorStabla</code> predstavlja jedan element u binarnom stablu.
	 * Svaki element binarnog stabla sadrži podatak i reference na svoju djecu.
	 * 
	 * @param lijevi referenca na lijevo dijete
	 * @param desni referenca na desno dijete
	 * @param podatak sadržaj čvora
	 */
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}

	/**
	 * Demonstrira funkcionalnost programa.
	 * 
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");

		System.out.println("Ispisujem stablo inorder");
		ispisiStablo(cvor);

		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata " + vel);

		boolean pronadjen = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronađen: " + pronadjen);

	}

	/**
	 * Rekurzivno pretražuje binarno stablo. Kompleksnost pretraživanja je O(log
	 * n) u najgorem slučaju.
	 * 
	 * @param cvor
	 *            referenca na korijen stabla
	 * @param podatak
	 *            podatak koji se traži
	 * @return vraća referencu na čvor
	 */
	private static boolean sadrziPodatak(CvorStabla cvor, String podatak) {
		if (cvor == null) {
			return false;
		}
		if (cvor.lijevi == null && cvor.desni == null
				&& cvor.podatak.equals(podatak) != true) {
			return false;
		} else if (cvor.podatak.equals(podatak)) {
			return true;
		} else if (cvor.podatak.compareTo(podatak) > 0) {
			return sadrziPodatak(cvor.lijevi, podatak);
		} else {
			return sadrziPodatak(cvor.desni, podatak);
		}
	}

	/**
	 * Broji elemente u stablu.
	 * 
	 * @param cvor
	 *            referenca na korijen stabla
	 * @return broj elemenata u stablu
	 */
	private static int velicinaStabla(CvorStabla cvor) {
		if (cvor == null) {
			return 0;
		}
		if (cvor.lijevi == null && cvor.desni == null) {
			return 1;
		} else {
			return 1 + velicinaStabla(cvor.desni) + velicinaStabla(cvor.lijevi);
		}
	}

	/**
	 * Ispisuje stablo u inorder poretku.
	 * 
	 * @param cvor
	 *            referenca na korijen stabla
	 */
	private static void ispisiStablo(CvorStabla cvor) {
		if (cvor == null) {
			return;
		}
		if (cvor.desni == null && cvor.lijevi == null) {
			System.out.println(cvor.podatak);
			return;
		}
		ispisiStablo(cvor.lijevi);
		System.out.println(cvor.podatak);
		ispisiStablo(cvor.desni);
	}

	/**
	 * Ubacuje novi element u stablo prateći da lijevo idu leksikografski
	 * "manji" elementi a desno leksikografski "veći". U slučaju da element već
	 * postoji ispisuje odgovarajuću poruku.
	 * 
	 * @param cvor
	 *            referenca na korijen stabla
	 * @param podatak
	 *            podatak koji se ubacuje
	 * @return referencu na čvor
	 */
	private static CvorStabla ubaci(CvorStabla cvor, String podatak) {
		if (cvor == null) {
			cvor = new CvorStabla();
			cvor.podatak = podatak;
		} else {
			if (cvor.podatak.compareTo(podatak) < 0) {
				cvor.desni = ubaci(cvor.desni, podatak);
			} else if (cvor.podatak.compareTo(podatak) == 0) {
				System.out.println("Podatak '" + podatak
						+ "' već postoji u stablu. Element nije dodan");
				return cvor;
			} else {
				cvor.lijevi = ubaci(cvor.lijevi, podatak);
			}
		}
		return cvor;
	}
}
