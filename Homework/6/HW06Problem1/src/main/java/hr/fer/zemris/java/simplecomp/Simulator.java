package hr.fer.zemris.java.simplecomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;
/**
 * Glavni razred koji simulira jedan računalni sustav izvodeći program napisan u asemblerskom jeziku.
 * Program iz asemblera čita se iz datoteke čija se putanja može predati prije poziva ili poslije pokretanja programa.
 * @author Filip Džidić
 *
 */
public class Simulator {
	/**
	 * Glavna metoda koja izvodi program
	 * @param args putanja do datoteke na kojoj je napisan program u asembleru koji izvodimo
	 * @throws Exception ako dođe do bilo kakve greške
	 */
	public static void main(String[] args) throws Exception {
		String filepath;
		if (args.length!=1) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			filepath = readFilepath(reader);
		} else {
			filepath = args[0];
		}
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
		"hr.fer.zemris.java.simplecomp.impl.instructions"
		);
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(
		filepath,
		comp,
		creator
		);
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);
	}
	/**
	 * Metoda kojom čitamo putanju staze ako nije data prije pozivanja.
	 * @param reader input stream reader kojim čitamo unos s tipkovnice
	 * @return String reprezentaciju putanje
	 * @throws IOException ako putanju ne možemo naći na disku
	 */
	private static String readFilepath(BufferedReader reader) throws IOException {
		System.out.println("Please provide a filepath to your program");
		String path = reader.readLine().trim();
		return path;
	}
}
