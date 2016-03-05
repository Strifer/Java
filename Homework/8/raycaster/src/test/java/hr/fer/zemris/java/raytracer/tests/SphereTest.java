package hr.fer.zemris.java.raytracer.tests;

import static org.junit.Assert.*;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

import org.junit.Test;

public class SphereTest {

	@Test
	public void NoIntersectionsTest() {
		// the ray points up to the z axis while the sphere is located inside
		// the xy plane
		Sphere sphere = new Sphere(new Point3D(4, 4, 4), 2, 1, 1, 1, 1, 1, 1, 1);
		Ray ray = new Ray(new Point3D(0, 0, 0), new Point3D(0, 0, 1));
		RayIntersection s = sphere.findClosestRayIntersection(ray);
		assertTrue(s == null);
	}

	@Test
	public void TangentialIntersectionTest() {
		// the sphere lies on the xy plane and touches the x an y axis at
		// coordinates (2,0,0) and (0,2,0)
		// the ray starts at (0,0,0) and points in the direction of the y axis
		// the ray should intersect the sphere at the point (0,2,0)
		Sphere sphere = new Sphere(new Point3D(2, 2, 0), 2, 1, 1, 1, 1, 1, 1, 1);
		Ray ray = new Ray(new Point3D(0, 0, 0),
				new Point3D(1E-9, 1, 0).normalize());
		RayIntersection s = sphere.findClosestRayIntersection(ray);
		assertTrue(Math.abs(s.getPoint().x - 0) < 1E-4);
		assertTrue(Math.abs(s.getPoint().y - 2) < 1E-4);
		assertTrue(Math.abs(s.getPoint().z - 0) == 0);
		assertTrue(Math.abs(s.getDistance() - 2.0) < 1E-4);
	}

	@Test
	public void DirectIntersectionTest() {
		// the sphere is intersected by the axis at two points, (5,0,0) and
		// (15,0,0)
		// the ray starts at (0,0,0) and points in the same direction as the x
		// axis
		// the ray should intersect the sphere at the first point (5,0,0)
		Sphere sphere = new Sphere(new Point3D(10, 0, 0), 5, 1, 1, 1, 1, 1, 1,
				1);
		Ray ray = new Ray(new Point3D(0, 0, 0),
				new Point3D(1, 0, 0).normalize());
		RayIntersection s = sphere.findClosestRayIntersection(ray);
		assertTrue(s.getPoint().x == 5);
		assertTrue(s.getPoint().y == 0);
		assertTrue(s.getPoint().z == 0);
		assertTrue(s.getDistance() == 5);
	}

	@Test
	public void RayFromInsideSphereTest() {
		// the sphere is intersected by the axis at two points, (5,0,0) and
		// (15,0,0)
		// the ray starts inside the sphere at its center and points in the same
		// direction as the x axis
		// the ray should intersect the sphere at the point (15,0,0)
		// center+radius
		Sphere sphere = new Sphere(new Point3D(10, 0, 0), 5, 1, 1, 1, 1, 1, 1,
				1);
		Ray ray = new Ray(new Point3D(10, 0, 0),
				new Point3D(1, 0, 0).normalize());
		RayIntersection s = sphere.findClosestRayIntersection(ray);
		assertTrue(s.getPoint().x == 15);
		assertTrue(s.getPoint().y == 0);
		assertTrue(s.getPoint().z == 0);
		assertTrue(s.getDistance() == 5);
	}

}
