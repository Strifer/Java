package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere that can be found in our scene. A sphere's
 * location within the scene is defined by its radius and the coordinates of its
 * center.
 * 
 * @author Filip Džidić
 *
 */
public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	/** red diffuse component */
	private double kdr;
	/** green diffuse component */
	private double kdg;
	/** blue diffuse component */
	private double kdb;
	/** red reflective component */
	private double krr;
	/** green reflective component */
	private double krg;
	/** blue reflective component */
	private double krb;
	/** material specific reflective component */
	private double krn;

	/**
	 * Initializes sphere with all of it's necessary parameters.
	 * 
	 * @param center
	 *            coordinates of the sphere's center in 3D space
	 * @param radius
	 *            the sphere's radius
	 * @param kdr
	 *            diffuse component for red color
	 * @param kdg
	 *            diffuse component for green color
	 * @param kdb
	 *            diffuse component for blue color
	 * @param krr
	 *            reflective component for red color
	 * @param krg
	 *            reflective component for green color
	 * @param krb
	 *            reflective component for blue color
	 * @param krn
	 *            reflective material specific component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D oc_vector = ray.start.sub(center);
		double arg = Math.pow((oc_vector.scalarProduct(ray.direction)), 2);
		double discriminant = arg - oc_vector.norm() * oc_vector.norm()
				+ radius * radius;
		RayIntersection ret = null;
		if (discriminant < 0) {
			return ret;
		} else if (discriminant == 0) {
			double distance = -(ray.direction.scalarProduct(oc_vector));
			Point3D intersection = ray.start.add(ray.direction
					.scalarMultiply(distance));
			ret = IntersectionFactory(intersection, distance, true);

		} else {
			double distance1 = -(ray.direction.scalarProduct(oc_vector))
					+ Math.sqrt(discriminant);
			double distance2 = -(ray.direction.scalarProduct(oc_vector))
					- Math.sqrt(discriminant);

			if (distance1 < 0 && distance2 >= 0) {
				Point3D intersection2 = ray.start.add(ray.direction
						.scalarMultiply(distance2));
				ret = IntersectionFactory(intersection2, distance2, false);
			} else if (distance1 >= 0 && distance2 < 0) {
				Point3D intersection1 = ray.start.add(ray.direction
						.scalarMultiply(distance1));
				ret = IntersectionFactory(intersection1, distance1, false);
			} else {
				double distance = Math.min(distance1, distance2);
				Point3D intersection = ray.start.add(ray.direction
						.scalarMultiply(distance));
				ret = IntersectionFactory(intersection, distance, true);
			}

		}
		return ret;
	}

	/**
	 * Factory method for creating new instances of <code>RayIntersection</code>
	 * 
	 * @param intersection
	 *            the point at which the ray intersects our object
	 * @param distance
	 *            the distance between the starting point of the ray and our
	 *            intersection point
	 * @param outer
	 *            signifies if the ray is intersecting from outside or from
	 *            inside the object
	 * @return new instance of <code>RayIntersection</code>
	 */
	private RayIntersection IntersectionFactory(Point3D intersection,
			double distance, boolean outer) {
		return new RayIntersection(intersection, distance, outer) {

			@Override
			public Point3D getNormal() {
				return intersection.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
