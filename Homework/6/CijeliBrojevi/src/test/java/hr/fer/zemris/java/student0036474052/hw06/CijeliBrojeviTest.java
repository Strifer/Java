package hr.fer.zemris.java.student0036474052.hw06;

import static org.junit.Assert.*;

import org.junit.Test;
import hr.fer.zemris.java.student0036474052.hw06.CijeliBrojevi;


public class CijeliBrojeviTest {
	@Test
	public void paranBrojTest() {
		assertTrue(CijeliBrojevi.jeParan(44));
		assertFalse(CijeliBrojevi.jeParan(63));
	}
	
	@Test
	public void neparanBrojTest() {
		assertTrue(CijeliBrojevi.jeNeparan(53));
		assertFalse(CijeliBrojevi.jeNeparan(44));
	}
	
	@Test
	public void jedanProstTest() {
		assertFalse(CijeliBrojevi.jeProst(1));
	}
	
	@Test
	public void dvaProstTest() {
		assertTrue(CijeliBrojevi.jeProst(2));
	}
	
	@Test
	public void generalniProstTest() {
		assertTrue(CijeliBrojevi.jeProst(961748941));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativanProstTest() {
		assertTrue(CijeliBrojevi.jeProst(-20));
	}

}
