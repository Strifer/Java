package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Razred <code>FractalProducer</code> predstavlja prikaz fraktala generiran
 * pomoću Newton-Rapshon metode. Razred nudi metode za slijedno i paralelno
 * računanje fraktala.
 * 
 * @author Filip Džidić
 *
 */
public class FractalProducer {
	/** polinom prikazan preko korijena */
	private ComplexRootedPolynomial rootPolynome;
	/** red polinoma */
	private short order;
	/** standardni prikaz polinoma */
	private ComplexPolynomial polynome;
	/** prva derivacija polinoma */
	private ComplexPolynomial derived;

	/**
	 * Stvara novu instancu <code>FractalProducer</code> na osnovu korijena koje
	 * korsnik unese.
	 * 
	 * @param root
	 *            <code>ComplexRootedPolynomial</code> polinom koji je definirao
	 *            korisnik
	 */
	public FractalProducer(ComplexRootedPolynomial root) {
		rootPolynome = root;
		polynome = rootPolynome.toComplexPolynom();
		order = polynome.order();
		derived = polynome.derivative();
	}

	/**
	 * Otvara prozor i prikazuje fraktal koji se računa slijedno.
	 */
	public void showSequential() {
		FractalViewer.show(getSequentialFractalproducer());
	}

	/**
	 * Otvara prozor i prikazuje fraktal koji se računa paralelno.
	 */
	public void showParallel() {
		FractalViewer.show(getParallelFractalproducer());
	}

	/**
	 * Slijedna implementacija koja računa fraktal pomoću Newton-Rhapson
	 * iteracije. Omogućava da se u predano polje pohrane rezultati samo za
	 * zadani raspon y-koordinata (ostatak polja se ne dira).
	 * 
	 * @param reMin
	 *            minimalna vrijednost po realnoj osi
	 * @param reMax
	 *            maksimalna vrijednost po realnoj osi
	 * @param imMin
	 *            minimalna vrijednost po imaginarnoj osi
	 * @param imMax
	 *            maksimalna vrijednost po imaginarnoj osi
	 * @param width
	 *            širina zaslona na kojem se prikazuje fraktal
	 * @param height
	 *            visina zaslona na kojem se prikazuje fraktal
	 * @param m
	 *            broj pokušaja otkrivanja divergencije
	 * @param ymin
	 *            y-linija od koje se popunjava polje (uključiva)
	 * @param ymax
	 *            y-linija do koje se popunjava polje (uključiva)
	 * @param data
	 *            polje u koje treba pohraniti rezultat
	 */
	public void calculate(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int m, int ymin, int ymax,
			short[] data) {
		int offset = ymin * width;
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = ((height - 1) - y) / (height - 1.0)
						* (imMax - imMin) + imMin;
				Complex c = new Complex(cre, cim);
				Complex zn = c;
				Complex zn1;
				int iters = 0;
				double module = 0;
				do {
					Complex numerator = rootPolynome.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.div(denominator);
					zn1 = zn.sub(fraction);
					module = zn1.sub(zn).getMagnitude();
					zn = zn1;
					iters++;
				} while (iters < m && module > 0.001);
				int index = rootPolynome.indexOfClosestRootFor(zn1, 0.002);
				if (index == -1) {
					data[offset++] = 0;
				} else {
					data[offset++] = (short) (index + 1);
				}
			}
		}
	}

	/**
	 * Vraća objekt koji fraktal generira paralelno.
	 * 
	 * @return paralelni generator fraktala
	 */
	private IFractalProducer getParallelFractalproducer() {
		return new IFractalProducer() {
			int threadCount = 8 * Runtime.getRuntime().availableProcessors();
			ExecutorService pool = Executors.newFixedThreadPool(threadCount,
					new ThreadFactory() {

						@Override
						public Thread newThread(Runnable r) {
							Thread thread = Executors.defaultThreadFactory()
									.newThread(r);
							thread.setDaemon(true);
							return thread;
						}

					});

			@Override
			public void produce(double reMin, double reMax, double imMin,
					double imMax, int width, int height, long requestNo,
					IFractalResultObserver observer) {

				class Job implements Runnable {

					int startIndex;
					int endIndex;
					short[] data;
					int m;

					public Job(int start, int end, int m, short[] data) {
						startIndex = start;
						endIndex = end;
						this.m = m;
						this.data = data;

					}

					@Override
					public void run() {
						calculate(reMin, reMax, imMin, imMax, width, height, m,
								startIndex, endIndex, data);

					}

				}

				System.out.println("Započinjem izračune...");
				int m = 16 * 16;
				short[] data = new short[width * height];

				List<Future<?>> futureList = new ArrayList<Future<?>>();
				Job[] jobs = new Job[threadCount];

				int start = 0;
				int end = width / threadCount - 1;

				for (int i = 0; i < threadCount; i++) {
					jobs[i] = new Job(start, end, m, data);
					start = end + 1;
					end = end + width / threadCount;
					if (i == threadCount - 2) {
						end = width - 1;
					}
				}

				long startt = System.currentTimeMillis();
				for (int i = 0; i < threadCount; i++) {
					futureList.add(pool.submit(jobs[i]));
				}

				for (Future<?> f : futureList) {
					while (true) {
						try {
							f.get();
							break;
						} catch (InterruptedException | ExecutionException ex) {

						}
					}
				}
				System.out.println("Izračuni gotovi...");
				long endt = System.currentTimeMillis();
				System.out.println("Trajanje je " + (endt - startt));
				observer.acceptResult(data, (short) (order + 1), requestNo);
				System.out.println("Dojava gotova...");
				// pool.shutdown();

			}
		};
	}

	/**
	 * Vraća objekt koji fraktal generira slijedno.
	 * 
	 * @return slijedni generator fraktala
	 */
	private IFractalProducer getSequentialFractalproducer() {
		return new IFractalProducer() {

			@Override
			public void produce(double reMin, double reMax, double imMin,
					double imMax, int width, int height, long requestNo,
					IFractalResultObserver observer) {
				System.out.println("Započinjem izračune...");
				int m = 16 * 16;
				short[] data = new short[width * height];
				long startt = System.currentTimeMillis();
				calculate(reMin, reMax, imMin, imMax, width, height, m, 0,
						height - 1, data);
				long endt = System.currentTimeMillis();
				System.out.println("Izračuni gotovi...");
				System.out.println("Trajanje je " + (endt - startt));

				observer.acceptResult(data, (short) (order + 1), requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

}
