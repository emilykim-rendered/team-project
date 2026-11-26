package data_access;

import entity.JobPosting;
import entity.WorkArrangement;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests file-based persistence of shortlisted postings.
 */
class FileShortlistDataAccessObjectTest {

  @TempDir
  private Path temporaryDirectory;

  private JobPosting createPosting(String id, String title) {
    return new JobPosting.Builder("greenhouse", "example", id)
        .title(title)
        .company("Example Company")
        .location("Toronto")
        .descriptionText("Internship opportunity")
        .applicationUrl("https://example.com/jobs/" + id)
        .workArrangement(WorkArrangement.HYBRID)
        .build();
  }

  @Test
  void testSaveAndReload() {
    Path file = temporaryDirectory.resolve("shortlists.json");
    FileShortlistDataAccessObject dao =
        new FileShortlistDataAccessObject(file);

    JobPosting posting = createPosting("101", "Software Intern");
    Assertions.assertTrue(dao.saveIfAbsent("user1", posting));

    FileShortlistDataAccessObject reopened =
        new FileShortlistDataAccessObject(file);

    List<JobPosting> saved = reopened.getSavedPostings("user1");

    Assertions.assertEquals(1, saved.size());
    Assertions.assertEquals(posting.getStableId(),
        saved.get(0).getStableId());
    Assertions.assertEquals(posting.getTitle(),
        saved.get(0).getTitle());
  }

  @Test
  void testDuplicatePrevention() {
    Path file = temporaryDirectory.resolve("shortlists.json");
    FileShortlistDataAccessObject dao =
        new FileShortlistDataAccessObject(file);

    JobPosting first = createPosting("101", "Software Intern");
    JobPosting duplicate = createPosting("101", "Updated Title");

    Assertions.assertTrue(dao.saveIfAbsent("user1", first));
    Assertions.assertFalse(dao.saveIfAbsent("user1", duplicate));

    Assertions.assertEquals(1,
        dao.getSavedPostings("user1").size());
  }

  @Test
  void testSeparateUsers() {
    Path file = temporaryDirectory.resolve("shortlists.json");
    FileShortlistDataAccessObject dao =
        new FileShortlistDataAccessObject(file);

    dao.saveIfAbsent("user1", createPosting("101", "Software Intern"));
    dao.saveIfAbsent("user2", createPosting("102", "Data Intern"));

    Assertions.assertEquals(1, dao.getSavedPostings("user1").size());
    Assertions.assertEquals(1, dao.getSavedPostings("user2").size());
    Assertions.assertTrue(dao.getSavedPostings("user3").isEmpty());
  }

  @Test
  void testCorruptedFileDoesNotGetOverwritten() throws IOException {
    Path file = temporaryDirectory.resolve("shortlists.json");
    String corruptedContent = "{invalid json";
    Files.writeString(file, corruptedContent, StandardCharsets.UTF_8);

    FileShortlistDataAccessObject dao =
        new FileShortlistDataAccessObject(file);

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> dao.getSavedPostings("user1"));

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> dao.saveIfAbsent("user1",
            createPosting("101", "Software Intern")));

    Assertions.assertEquals(
        corruptedContent,
        Files.readString(file, StandardCharsets.UTF_8));
  }

  @Test
  void testInvalidStorageDestination() throws IOException {
    Path blockingFile = temporaryDirectory.resolve("blocking-file");
    Files.writeString(blockingFile, "original", StandardCharsets.UTF_8);

    Path invalidPath = blockingFile.resolve("shortlists.json");
    FileShortlistDataAccessObject dao =
        new FileShortlistDataAccessObject(invalidPath);

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> dao.saveIfAbsent("user1",
            createPosting("101", "Software Intern")));

    Assertions.assertEquals(
        "original",
        Files.readString(blockingFile, StandardCharsets.UTF_8));
  }
}
