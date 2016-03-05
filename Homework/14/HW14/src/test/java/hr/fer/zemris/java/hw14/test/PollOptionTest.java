package hr.fer.zemris.java.hw14.test;

import static org.junit.Assert.*;
import hr.fer.zemris.java.hw14.beans.PollOptions;

import org.junit.Test;

public class PollOptionTest {

	@Test
	public void ConstructionTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		assertTrue(option != null);
	}

	@Test
	public void IDTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		long ID = option.getId();
		assertTrue(ID == 1);
		option.setId(33);
		assertTrue(option.getId() != ID);
	}

	@Test
	public void NameTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		String title = option.getOptionTitle();
		assertTrue(title.equals("Google"));
		option.setOptionTitle("Wikipedia");
		assertTrue(!option.getOptionLink().equals(title));
	}

	@Test
	public void LinkTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		String link = option.getOptionLink();
		assertTrue(link.equals("www.google.com"));
		option.setOptionLink("www.wikipedia.hr");
		assertTrue(!option.getOptionLink().equals(link));
	}

	@Test
	public void PollIDTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		long pollID = option.getPollID();
		assertTrue(pollID == 1);
		option.setPollID(33);
		assertTrue(option.getPollID() != pollID);
	}

	@Test
	public void VoteTest() {
		PollOptions option = new PollOptions(1, "Google", "www.google.com", 1,
				30);
		long vote = option.getVotesCount();
		assertTrue(vote == 30);
		option.setVotesCount(532);
		assertTrue(option.getVotesCount() != vote);
	}

}
