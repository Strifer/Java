package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * <code>Kvadrat</code> predstavlja objekt kružnicu definiranu s njezinim
 * matematičkim parametrima.
 * 
 * @author Filip Džidić
 *
 */
public class Kvadrat extends Pravokutnik {
	/**
	 * statički stvaratelj koji služi za stvaranje pomoću čitanja parametara iz
	 * neke datoteke
	 */
	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj();
	
	/**
	 * Generalni konstruktor inicijalizira objekt sa zadanim parametrima.
	 * @param x x koordinata gornje lijeve točke
	 * @param y y koordinata gornje lijeve točke
	 * @param duzina duljina stranice kvadrata
	 */
	public Kvadrat(int x, int y, int duzina) {
		super(x, y, duzina, duzina);
	}
	/**
	 * Pomoćni razred koji služi za stvaranje objekta pomoću parametara koje dobiva kao <code>String</code>
	 * @author Filip Džidić
	 *
	 */
	private static class KvadratStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KVADRAT";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] args = parametri.split("[ ]");
			if (args.length<=3 && args.length>=4) {
				throw new IllegalArgumentException("invalid number of arguments");
			}
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			int duzina = Integer.parseInt(args[2]);
			return new Kvadrat(x, y, duzina);
		}
	}
}
