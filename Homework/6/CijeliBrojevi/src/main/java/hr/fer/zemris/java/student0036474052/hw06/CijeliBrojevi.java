package hr.fer.zemris.java.student0036474052.hw06;
/**
 * Ovaj razred nudi tri metode za testiranje je li cijeli broj paran, neparan ili prost.
 * @author Filip Džidić
 *
 */
 public class CijeliBrojevi {
        /**
	 	 * Metoda koja testira je li broj neparan
	 	 * @param broj broj koji testiramo
	 	 * @return true ako je broj neparan
	 	 */
        public static boolean jeNeparan(int broj) {
        		return broj%2==1;
         }
        
        /**
         * Metoda koja testira je li broj paran.
         * @param broj broj koji testiramo
         * @return true ako je broj paran
         */
        public static boolean jeParan(int broj) { 
        	return broj%2==0;
        }
        
        /**
         * Metoda koja testira je li broj prost.
         * @param broj broj koji testiramo
         * @return true ako je broj prost
         * @throws IllegalArgumentException ako korisnik unese broj koji nije pozitivan integer
         */
        public static boolean jeProst(int broj) { 
        	if (broj<=0) {
        		throw new IllegalArgumentException();
        	}
    	    if (broj!=2 && jeParan(broj) || broj==1) return false;
    	    for(int i=3; i*i<=broj;i+=2) {
    	        if(broj%i==0)
    	            return false;
    	    }
    	    return true;
        }
    }