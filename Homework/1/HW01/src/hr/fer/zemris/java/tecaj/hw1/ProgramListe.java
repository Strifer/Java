package hr.fer.zemris.java.tecaj.hw1;
/** Jednostavna implementacija podatkovne strukture liste
 * 
 * @author Filip Džidić
 * @version 1.0
 */
public class ProgramListe {
	
	/** Ovaj unutarnji razred predstavlja jedan element povezane liste
	 * 
	 * @author Filip Džidić
	 * @param sljedeci referenca na sljedeći element u listi
	 * @param podatak spremljen kao znakovni niz
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	/** Prebrojava elemente u listi
	 * 
	 * @param cvor pocetak liste
	 * @return broj elemenata u listi
	 */
	static int velicinaListe(CvorListe cvor) {
		int velicina=0;
		while(cvor!=null) {
			velicina++;
			cvor=cvor.sljedeci;
		}
		return velicina;
	}
	/** Ubacuje element na kraj liste
	 * 
	 * @param prvi pocetak liste
	 * @param podatak podatak koji dodajemo
	 * @return vraca pocetak nove liste
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		if (prvi==null) {
			prvi=new CvorListe();
			prvi.podatak=podatak;
			prvi.sljedeci=null;
		} else {
			CvorListe temp = prvi;
			while (temp.sljedeci!=null) {
				temp=temp.sljedeci;
			}
			CvorListe novi = new CvorListe();
			novi.sljedeci=null;
			novi.podatak=podatak;
			temp.sljedeci=novi;
		}
		return prvi;
		
	}
	/** Ispisuje sve elemente povezane liste od prvog do zadnjeg.
	 * 
	 * @param cvor pocetak liste
	 */
	static void ispisiListu(CvorListe cvor) {
		while(cvor!=null) {
			System.out.println(cvor.podatak);
			cvor=cvor.sljedeci;
		}
	}
	/** Sortira podatke uzlazno u listi po leksikografskom odnosu koristeći insertion sort algoritam.
	 * 
	 * @param cvor pocetak liste
	 * @return vraća referencu na sortiranu listu
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		if (cvor == null || cvor.sljedeci == null)
			return cvor;
		
		CvorListe novi = new CvorListe();
		novi.podatak=cvor.podatak;
		CvorListe j = cvor.sljedeci;
		
		while (j!=null) {
			CvorListe temp = novi;
			CvorListe sljed = j.sljedeci;
			
			if (j.podatak.compareTo(novi.podatak)<=0) {
				CvorListe stari = novi;
				novi = j;
				novi.sljedeci=stari;
			} else {
				while(temp.sljedeci!=null) {
					if (j.podatak.compareTo(temp.podatak)>0 && j.podatak.compareTo(temp.sljedeci.podatak)<=0) {
						CvorListe stariSljed = temp.sljedeci;
						temp.sljedeci=j;
						j.sljedeci=stariSljed;
					}
					temp=temp.sljedeci;
				}
				if (temp.sljedeci == null && j.podatak.compareTo(temp.podatak)>0) {
					temp.sljedeci=j;
					j.sljedeci=null;
				}
			}
			j = sljed;
		}
		return novi;
	}
}
