package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;
/**
 * Ovaj apstraktni razred predstavlja jedan geometrijski lik koji možemo crtati.
 * @author Filip Džidić
 *
 */
public abstract class GeometrijskiLik {
	
	/**
	 * Apstraktna metoda koja služi za provjeru da li <code>GeometrijskiLik</code> sadrži određenu točku T(x,y)
	 * @param x koordinata apscisa točke
	 * @param y koordinata ordinata točke
	 * @return true ako je točka sadržana u liku
	 */
	public abstract boolean sadrziTocku(int x, int y);
	
	/**
	 * Ova općenita metoda prolazi kroz svaku moguću točku u slici i provjerava je li točka sadržana u geometrijskom liku.
	 * @param slika slika na kojoj crtamo
	 */
	public void popuniLik(Slika slika) {
		for (int i=0, sirina = slika.getSirina(), visina = slika.getVisina(); i<sirina; i++) {
			for (int j=0; j<visina; j++) {
				if (this.sadrziTocku(i,j)) {
					slika.upaliTocku(i, j);
				}
			}
		}
	}
}
