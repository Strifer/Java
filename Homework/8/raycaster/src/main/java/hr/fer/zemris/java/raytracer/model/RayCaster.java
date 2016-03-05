package hr.fer.zemris.java.raytracer.model;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents a simple implementation of ray casting for creating
 * graphical images of predefined scenes.
 * 
 * Our basic model has only spheres defined as graphical objects and the colors
 * are based on Phong's model.
 * 
 * @author Filip Džidić
 *
 */
public class RayCaster {
	/** distance threshold for determining shadows */
	private final static double DISTANCE_TRESHOLD = 1E-10;

	/**
	 * This main method creates our view plane and creates a graphical image of
	 * our scene
	 * 
	 * @param args
	 *            no arguments should be provided
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getParallelIRayTracerProducer(), new Point3D(10,
				0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * This method main job is to try and find the closest
	 * <code>RayIntersection</code> of a given ray and all the objects found in
	 * our scene. If the ray doesn't intersect any objects this method returns
	 * null.
	 * 
	 * @param objects
	 *            a list of all the objects that exist in our scene
	 * @param ray
	 *            the ray we are casting on our scene
	 * @return the closest <code>RayIntersection</code> if it exists
	 */
	private static RayIntersection findClosest(List<GraphicalObject> objects,
			Ray ray) {
		RayIntersection closest = null;
		boolean firstFound = true;
		for (GraphicalObject object : objects) {
			RayIntersection intersection = object
					.findClosestRayIntersection(ray);
			if (intersection != null) {
				if (firstFound) {
					closest = intersection;
					firstFound = false;
				} else if (closest != null
						&& (intersection.getDistance() < closest.getDistance())) {
					closest = intersection;
				}
			}

		}
		return closest;
	}

	/**
	 * For our specific pixel with coordinates (x,y) and a view ray passing
	 * through it, this method attempts to find an intersection with the objects
	 * in our scene and if it exists it calculates the color model of this pixel
	 * for graphical representation.
	 * 
	 * If no intersection is found the pixel will be colored black (RGB[0,0,0]).
	 * 
	 * @param scene
	 *            the scene whose graphical image we're creating
	 * @param ray
	 *            the ray we are casting
	 * @param rgb
	 *            the color model we are creating
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		List<LightSource> lights = scene.getLights();
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection closest = findClosest(objects, ray);
		if (closest == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			colorFor(closest, lights, objects, rgb, ray);
		}
	}

	/**
	 * This method builds a color model for our intersection according to
	 * Phong's model.
	 * 
	 * @param S
	 *            the intersection of the ray we are casting
	 * @param lights
	 *            a list of all the light sources in our scene
	 * @param objects
	 *            a list of all the objects in our scene
	 * @param rgb
	 *            the color model we are creating
	 * @param ray
	 *            the ray we are casting
	 */
	private static void colorFor(RayIntersection S, List<LightSource> lights,
			List<GraphicalObject> objects, short[] rgb, Ray ray) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource source : lights) {
			Ray sourceToObject = Ray
					.fromPoints(source.getPoint(), S.getPoint());
			RayIntersection closestObject = findClosest(objects, sourceToObject);
			if (closestObject != null
					&& closestObject.getDistance() < source.getPoint()
							.sub(S.getPoint()).norm()
					&& Math.abs(closestObject.getDistance()
							- source.getPoint().sub(S.getPoint()).norm()) > DISTANCE_TRESHOLD) {
				continue;
			}

			else {
				Point3D l = source.getPoint().sub(S.getPoint()).normalize();
				Point3D n = S.getNormal();
				Point3D v = ray.direction;
				Point3D r = n.scalarMultiply(2 * n.scalarProduct(l)).sub(l);
				double scalar_ln = l.scalarProduct(n);
				double scalar_rv = r.scalarProduct(v);

				rgb[0] += source.getR() * S.getKdr() * scalar_ln;

				rgb[1] += source.getG() * S.getKdg() * scalar_ln;

				rgb[2] += source.getB() * S.getKdb() * scalar_ln;

				if (scalar_rv < 0) {
					rgb[0] += source.getR() * S.getKrr()
							* Math.pow(scalar_rv, S.getKrn());

					rgb[1] += source.getG() * S.getKrg()
							* Math.pow(scalar_rv, S.getKrn());

					rgb[2] += source.getB() * S.getKrb()
							* Math.pow(scalar_rv, S.getKrn());
				}
			}
		}

	}

	/**
	 * This factory method creates an instance of
	 * <code>IRayTracerProducer</code> which is capable of creating a graphical
	 * representation of our created scene. Calculation is done with parallel
	 * processing.
	 * 
	 * @return <code>IRayTracerProducer</code> capable of creating a graphical
	 *         image of our scene
	 */
	private static IRayTracerProducer getParallelIRayTracerProducer() {
		return new IRayTracerProducer() {
			ForkJoinPool pool = new ForkJoinPool();

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				class Job extends RecursiveAction {
					private static final long serialVersionUID = 1L;
					/** minimum iteration threshold */
					final static int MINIMUM = 20;
					private int ystart;
					private int yend;
					private short[] red;
					private short[] green;
					private short[] blue;

					public Job(int ystart, int yend, short[] red,
							short[] green, short[] blue) {
						this.ystart = ystart;
						this.yend = yend;
						this.red = red;
						this.green = green;
						this.blue = blue;
					}

					@Override
					protected void compute() {
						// if our working y-axis range is less than MINIMUM we
						// start the computation
						if (yend - ystart + 1 < MINIMUM) {
							computeDirect();
							return;
						}
						// otherwise we split our work into two smaller jobs
						Job j1 = new Job(ystart, ystart + (yend - ystart) / 2,
								red, green, blue);
						Job j2 = new Job(ystart + (yend - ystart) / 2, yend,
								red, green, blue);
						invokeAll(j1, j2);

					}

					private void computeDirect() {
						Point3D zAxis = view.sub(eye).normalize();
						Point3D yAxis = viewUp.normalize().sub(
								zAxis.scalarMultiply(zAxis.scalarProduct(viewUp
										.normalize())));
						Point3D xAxis = zAxis.vectorProduct(yAxis);
						Point3D screenCorner = view.sub(
								xAxis.scalarMultiply(horizontal / 2)).add(
								yAxis.scalarMultiply(vertical / 2));
						Scene scene = RayTracerViewer.createPredefinedScene();
						short[] rgb = new short[3];
						int offset = ystart * width;
						for (int y = ystart; y < yend; y++) {
							for (int x = 0; x < width; x++) {
								Point3D screenPoint = screenCorner.add(
										xAxis.scalarMultiply(horizontal * x
												/ (width - 1))).sub(
										yAxis.scalarMultiply(vertical * y
												/ (height - 1)));
								Ray ray = Ray.fromPoints(eye, screenPoint);
								tracer(scene, ray, rgb);
								red[offset] = rgb[0] > 255 ? 255 : rgb[0];
								green[offset] = rgb[1] > 255 ? 255 : rgb[1];
								blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
								offset++;
							}
						}

					}

				}

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Job job = new Job(0, height, red, green, blue);
				long start = System.currentTimeMillis();
				pool.invoke(job);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				long end = System.currentTimeMillis();
				System.out.println("Dojava gotova...");
				System.out
						.println("Potrebno vrijeme = " + (end - start) + "ms");
				pool.shutdown();

			}

		};

	}

	/**
	 * This factory method creates an instance of
	 * <code>IRayTracerProducer</code> which is capable of creating a graphical
	 * representation of our created scene. Calculation is done sequentially.
	 * 
	 * @return <code>IRayTracerProducer</code> capable of creating a graphical
	 *         image of our scene
	 */
	@SuppressWarnings("unused")
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(
						zAxis.scalarMultiply(zAxis.scalarProduct(viewUp
								.normalize())));
				Point3D xAxis = zAxis.vectorProduct(yAxis);
				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				long start = System.currentTimeMillis();
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(horizontal * x
										/ (width - 1))).sub(
								yAxis.scalarMultiply(vertical * y
										/ (height - 1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				long end = System.currentTimeMillis();
				System.out.println("Dojava gotova...");
				System.out
						.println("Potrebno vrijeme = " + (end - start) + "ms");
			}
		};
	}
}
