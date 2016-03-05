package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;
/**
 * <code>Linija</code> predstavlja dužinu koja spaja dvije točke te koju možemo crtati na slici.
 * @author Filip Džidić
 *
 */
public class Linija extends GeometrijskiLik {
	/**
	 * statički stvaratelj koji služi za stvaranje pomoću čitanja parametara iz
	 * neke datoteke
	 */
	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj();
	/** x koordinata prve točke */
	private int x0;
	/** y koordinata prve točke */
	private int y0;
	/** x koordinata druge točke */
	private int x1;
	/** y koordinata druge točke */
	private int y1;
	
	 
	/**
	 * Generalni konstruktor inicijalizira liniju sa zadanim parametrima
	 * @param x0 x koordinata prve točke
	 * @param y0 y koordinata prve točke
	 * @param x1 x koordinata druge točke
	 * @param y1 y koordinata druge točke
	 */
	public Linija (int x0, int y0, int x1, int y1) {
		this.x0=x0;
		this.x1=x1;
		this.y0=y0;
		this.y1=y1;
	}
	/**
	 * Getter metoda za x koordinatu prve točke.
	 * @return x koordinata prve točke
	 */
	public int getX0() {
		return x0;
	}
	/**
	 * Getter metoda za y koordinatu prve točke.
	 * @return y koordinata prve točke
	 */
	public int getY0() {
		return y0;
	}
	/**
	 * Getter metoda za x koordinatu druge točke.
	 * @return x koordinata druge točke
	 */
	public int getX1() {
		return x1;
	}
	/**
	 * Getter metoda za y koordinatu druge točke.
	 * @return y koordinata prve točke
	 */
	public int getY1() {
		return y1;
	}
	
	@Override
	public boolean sadrziTocku(int x, int y) {
		int dy = y1-y0;
		int dx = x1-x0;
		if (dx==0) {
			if(x==x0 && y>=y0 && y<=y1) {
				return true;
			}else  {
				return false;
			}
		}
		int slope = dy/dx;
		if (y-y0 == slope*(x-x0) && (y>=y0 && y<=y1)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metoda crta liniju koristeći Bresenhamov algoritam. Samo one točke koje se nalaze u slici će biti nacrtane.
	 */
	public void popuniLik(Slika slika) {
		int w = x1 - x0;
	    int h = y1 - y0;
	    int x = x0;
	    int y = y0;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
	    if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
	    if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
	    if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
	    int longest = Math.abs(w) ;
	    int shortest = Math.abs(h) ;
	    if (!(longest>shortest)) {
	        longest = Math.abs(h) ;
	        shortest = Math.abs(w) ;
	        if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
	        dx2 = 0 ;            
	    }
	    int numerator = longest >> 1 ;
	    for (int i=0;i<=longest;i++) {
	        if(x>=0 && x<slika.getSirina() && y>=0 && y<slika.getVisina()) {
	        	slika.upaliTocku(x, y);
	        }
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x += dx1 ;
	            y += dy1 ;
	        } else {
	            x += dx2 ;
	            y += dy2 ;
	        }
	    }
	}
	/**
	 * Pomoćni razred koji služi za stvaranje objekta pomoću parametara koje dobiva kao <code>String</code>
	 * @author Filip Džidić
	 *
	 */
	private static class LinijaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "LINIJA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] args = parametri.split("[ ]");
			if (args.length!=4) {
				throw new IllegalArgumentException("invalid number of arguments");
			}
			int x0 = Integer.parseInt(args[0]);
			int y0 = Integer.parseInt(args[1]);
			int x1 = Integer.parseInt(args[2]);
			int y1 = Integer.parseInt(args[3]);
			return new Linija(x0, y0, x1, y1);
		}
	}
}
