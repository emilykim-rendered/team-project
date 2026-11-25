package entity;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Shortlist entity.
 */
class ShortlistTest {

  private JobPosting createPosting(String id, String title) {
    return new JobPosting.Builder("greenhouse", "example", id)
        .title(title)
        .company("Example Company")
        .applicationUrl("https://example.com/jobs/" + id)
        .build();
  }

  @Test
  void testEmptyShortlist() {
    Shortlist shortlist = new Shortlist("user1");

    Assertions.assertEquals("user1", shortlist.getUserId());
    Assertions.assertTrue(shortlist.getPostings().isEmpty());
    Assertions.assertFalse(shortlist.containsPosting("unknown"));
  }

  @Test
  void testAddPostingAndPreventDuplicates() {
    Shortlist shortlist = new Shortlist("user1");
    JobPosting posting = createPosting("101", "Software Intern");
    JobPosting duplicate = createPosting("101", "Updated Title");

    Assertions.assertTrue(shortlist.addPosting(posting));
    Assertions.assertTrue(shortlist.containsPosting(posting.getStableId()));

    Assertions.assertFalse(shortlist.addPosting(duplicate));
    Assertions.assertEquals(1, shortlist.getPostings().size());
  }

  @Test
  void testAddDifferentPostings() {
    Shortlist shortlist = new Shortlist("user1");

    Assertions.assertTrue(
        shortlist.addPosting(createPosting("101", "Software Intern")));
    Assertions.assertTrue(
        shortlist.addPosting(createPosting("102", "Data Analyst")));

    Assertions.assertEquals(2, shortlist.getPostings().size());
  }

  @Test
  void testUnmodifiableSnapshot() {
    Shortlist shortlist = new Shortlist("user1");
    shortlist.addPosting(createPosting("101", "Software Intern"));

    List<JobPosting> snapshot = shortlist.getPostings();

    Assertions.assertThrows(
        UnsupportedOperationException.class,
        () -> snapshot.clear());

    shortlist.addPosting(createPosting("102", "Data Analyst"));

    Assertions.assertEquals(1, snapshot.size());
    Assertions.assertEquals(2, shortlist.getPostings().size());
  }

  @Test
  void testInvalidInputs() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> new Shortlist(null));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> new Shortlist("  "));

    Shortlist shortlist = new Shortlist("user1");

    Assertions.assertThrows(
        NullPointerException.class,
        () -> shortlist.addPosting(null));
  }
}