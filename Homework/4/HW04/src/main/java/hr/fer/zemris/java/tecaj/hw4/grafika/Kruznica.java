package hr.fer.zemris.java.tecaj.hw4.grafika;
/**
 * <code>Kruznica</code> predstavlja objekt kružnicu definiranu s njezinim
 * matematičkim parametrima polumjerom i središtem.
 * 
 * @author Filip Džidić
 *
 */
public class Kruznica extends Elipsa {
	/**
	 * statički stvaratelj koji služi za stvaranje pomoću čitanja parametara iz
	 * neke datoteke
	 */
	public static final StvarateljLika STVARATELJ = new KruznicaStvaratelj();
	
	/**
	 * Generalni konstruktor inicjalizira objekt sa zadanim parametrima
	 * @param xcentar x koordinata središta
	 * @param ycentar y koordinata središta 
	 * @param radijus polumjer kružnice
	 */
	public Kruznica(int xcentar, int ycentar, int radijus) {
		super(xcentar, ycentar, radijus, radijus);
	}
	/**
	 * Pomoćni razred koji služi za stvaranje objekta pomoću parametara koje dobiva kao <code>String</code>
	 * @author Filip Džidić
	 *
	 */
	private static class KruznicaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KRUG";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] args = parametri.split("[ ]");
			if (args.length!=3) {
				throw new IllegalArgumentException("invalid number of arguments");
			}
			int xcentar = Integer.parseInt(args[0]);
			int ycentar = Integer.parseInt(args[1]);
			int radijus = Integer.parseInt(args[2]);
			return new Kruznica(xcentar, ycentar, radijus);
		}
	}
}
