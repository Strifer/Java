package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * <code>Pravokutnik</code> predstavlja <code>GeometrijskiLik</code>
 * pravokutnika koji je definiran sa matematičkim parametrima gornje lijeve
 * točke dužinom i visinom.
 * 
 * @author Filip Džidić
 *
 */
public class Pravokutnik extends GeometrijskiLik {
	/**
	 * statički stvaratelj koji služi za stvaranje pomoću čitanja parametara iz
	 * neke datoteke
	 */
	public static final StvarateljLika STVARATELJ = new PravokutnikStvaratelj();
	/** x koordinata gornje lijeve točke */
	private int x;
	/** y koordinata gornje lijeve točke */
	private int y;
	
	/** sirina pravokutnika */
	private int sirina;
	/** duzina pravokutnika */
	private int visina;
	
	/**
	 * Generalni konstruktor inicijalizira pravokutnik sa zadanim parametrima.
	 * @param x x koordinata gornje lijeve točke
	 * @param y y koordinata gornje lijeve točke
	 * @param sirina sirina pravokutnika
	 * @param visina duzina pravokutnika
	 */
	public Pravokutnik(int x, int y, int sirina, int visina) {
		if (sirina<0 || visina <0) {
			throw new IllegalArgumentException("širina i visina ne mogu biti negativne");
		}
		this.x = x;
		this.y = y;
		this.sirina = sirina;
		this.visina = visina;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < this.x || x >= this.x + sirina)
			return false;
		if (y < this.y || y >= this.y + visina)
			return false;
		return true;
	}

	/**
	 * Metoda za crtanje pravokutnika na zadanoj slici. Crtamo samo one točke pravokutnika
	 * koje su sadržane unutar slike. Ako niti jedna točka nije sadržana metoda
	 * neće ništa napraviti i ništa se neće nacrtati.
	 */
	public void popuniLik(Slika slika) {
		if (x + sirina <= 0 || x >= slika.getSirina())
			return;
		if (y + visina <= 0 || y >= slika.getVisina())
			return;

		int pocetakX = Math.max(0, x);
		int krajX = Math.min(x + sirina, slika.getSirina());

		int pocetakY = Math.max(0, y);
		int krajY = Math.min(y + visina, slika.getVisina());

		for (int i = pocetakX; i < krajX; i++) {
			for (int j = pocetakY; j < krajY; j++) {
				slika.upaliTocku(i, j);
			}
		}
	}
	/**
	 * Pomoćni razred koji služi za stvaranje objekta pomoću parametara koje dobiva kao <code>String</code>
	 * @author Filip Džidić
	 *
	 */
	private static class PravokutnikStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] args = parametri.split("[ ]");
			if (args.length != 4) {
				throw new IllegalArgumentException(
						"invalid number of arguments");
			}
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			int sirina = Integer.parseInt(args[2]);
			int visina = Integer.parseInt(args[3]);
			return new Pravokutnik(x, y, sirina, visina);
		}
	}
}
