package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * <code>Elipsa</code> predstavlja objekt elipsu definiranu s njezinim
 * matematičkim parametrima središtem i poluosima.
 * 
 * @author Filip Džidić
 *
 */
public class Elipsa extends GeometrijskiLik {
	/**
	 * statički stvaratelj koji služi za stvaranje pomoću čitanja parametara iz
	 * neke datoteke
	 */
	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj();
	/** x koordinata elipsinog središta */
	private int xcentar;
	/** y koordinata elipsinog središta */
	private int ycentar;

	/** horizontalna poluos */
	private int xradijus;
	/** vertikalna poluos */
	private int yradijus;

	/**
	 * Generalni konstruktor inicijalizira i stvara jednu instancu elipse.
	 * 
	 * @param xcentar
	 *            x koordinata elipsinog središta
	 * @param ycentar
	 *            y koordinata elipsinog središta
	 * @param xradijus
	 *            horizontalna poluos
	 * @param yradijus
	 *            vertikalna poluos
	 * @throws IllegalArgumentException ako je neka poluos negativna
	 */
	public Elipsa(int xcentar, int ycentar, int xradijus, int yradijus) {
		if (xradijus<0 || yradijus<0) {
			throw new IllegalArgumentException("poluosi ne mogu biti negativne");
		}
		this.xcentar = xcentar;
		this.ycentar = ycentar;
		this.xradijus = xradijus;
		this.yradijus = yradijus;
	}

	@Override
	/**
	 * Metoda koja provjerava da li je određena točka sadržana unutar elipse.
	 */
	public boolean sadrziTocku(int x, int y) {
		int xsq = (x - xcentar) * (x - xcentar);
		int ysq = (y - ycentar) * (y - ycentar);
		int xradsq = (xradijus) * (xradijus);
		int yradsq = (yradijus) * (yradijus);
		if (yradsq * xsq + xradsq * ysq <= xradsq * yradsq) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Metoda za crtanje elipse na zadanoj slici. Crtamo samo one točke elipse
	 * koje su sadržane unutar slike. Ako niti jedna točka nije sadržana metoda
	 * neće ništa napraviti i ništa se neće nacrtati.
	 */
	public void popuniLik(Slika slika) {
		if (xcentar + xradijus <= 0 || xcentar - xradijus >= slika.getSirina())
			return;
		if (ycentar + yradijus <= 0 || ycentar - yradijus >= slika.getSirina())
			return;

		int pocetakX = Math.max(0, xcentar - xradijus);
		int krajX = Math.min(xcentar + xradijus, slika.getSirina());

		int pocetakY = Math.max(0, ycentar - yradijus);
		int krajY = Math.min(ycentar + yradijus, slika.getVisina());

		for (int i = pocetakX; i < krajX; i++) {
			for (int j = pocetakY; j < krajY; j++) {
				if (sadrziTocku(i, j)) {
					slika.upaliTocku(i, j);
				}
			}
		}
	}
	/**
	 * Pomoćni razred koji služi za stvaranje objekta pomoću parametara koje dobiva kao <code>String</code>
	 * @author Filip Džidić
	 *
	 */
	private static class ElipsaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "ELIPSA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] args = parametri.split("[ ]");
			if (args.length != 4) {
				throw new IllegalArgumentException(
						"invalid number of arguments");
			}
			int xcentar = Integer.parseInt(args[0]);
			int ycentar = Integer.parseInt(args[1]);
			int xradijus = Integer.parseInt(args[2]);
			int yradijus = Integer.parseInt(args[3]);
			return new Elipsa(xcentar, ycentar, xradijus, yradijus);
		}

	}

}
