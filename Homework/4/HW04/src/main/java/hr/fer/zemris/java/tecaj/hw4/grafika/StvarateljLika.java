package hr.fer.zemris.java.tecaj.hw4.grafika;
/**
 * Sučelje koje koristimo za stvaranje i crtanje geometrijskih likova čitanjem iz datoteke.
 * @author Filip Džidić
 *
 */
public interface StvarateljLika {
	/**
	 * Metoda koja vraća naziv geometrijskog lika nad kojim se poziva.
	 * @return naziv lika
	 */
	String nazivLika();
	/**
	 * Metoda koja stvara <code>GeometrijskiLik</code> primajući parametre u jednom Stringu.
	 * @param parametri <code>String</code> koji sadržava sve potrebne parametre za stvaranje
	 * @return novostvoreni objekt
	 * @throws IllegalArgumentException ako su parametri pogrešno zadani
	 */
	GeometrijskiLik stvoriIzStringa(String parametri);
}
