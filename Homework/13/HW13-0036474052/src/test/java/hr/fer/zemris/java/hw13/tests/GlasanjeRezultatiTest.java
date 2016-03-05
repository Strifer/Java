package hr.fer.zemris.java.hw13.tests;

import static org.junit.Assert.*;
import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.BandWithVote;
import hr.fer.zemris.java.hw13.servlets.GlasanjeRezultatiServlet.VoteResult;
import hr.fer.zemris.java.hw13.servlets.GlasanjeServlet.Band;

import org.junit.Test;

public class GlasanjeRezultatiTest {

	@Test
	public void BandWithVoteTest() {
		Band band = new Band(0, "beatles", "www.youtube.com");
		BandWithVote beatleVote = new BandWithVote(band, 132);
		assertTrue(beatleVote.getBand() == band);
		assertTrue(beatleVote.getVotes() == 132);
	}

	@Test
	public void BandTest() {
		Band band = new Band(0, "beatles", "www.youtube.com");
		assertTrue(band.getIndex() == 0);
		assertTrue(band.getName().equals("beatles"));
		assertTrue(band.getLink().equals("www.youtube.com"));

	}

	@Test
	public void VoteResultTest() {
		VoteResult vote = new VoteResult(1, 44);
		assertTrue(vote.getIndex() == 1);
		assertTrue(vote.getVotes() == 44);
	}

}
