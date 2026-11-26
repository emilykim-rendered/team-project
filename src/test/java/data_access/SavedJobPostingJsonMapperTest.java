package data_access;

import entity.JobPosting;
import entity.WorkArrangement;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests JSON serialization of saved job postings.
 */
class SavedJobPostingJsonMapperTest {

  @Test
  void testJsonRoundTrip() {
    JobPosting original = new JobPosting.Builder(
        "greenhouse", "example", "101")
        .title("Software Engineering Intern")
        .company("Example Company")
        .location("Toronto")
        .descriptionText("Java development")
        .applicationUrl("https://example.com/jobs/101")
        .workArrangement(WorkArrangement.HYBRID)
        .build();

    JSONObject json = SavedJobPostingJsonMapper.toJson(original);
    JSONObject loadedJson = new JSONObject(json.toString());
    JobPosting restored = SavedJobPostingJsonMapper.fromJson(loadedJson);

    Assertions.assertEquals(original.getStableId(), restored.getStableId());
    Assertions.assertEquals(original.getSource(), restored.getSource());
    Assertions.assertEquals(original.getBoardToken(), restored.getBoardToken());
    Assertions.assertEquals(original.getExternalJobId(),
        restored.getExternalJobId());
    Assertions.assertEquals(original.getTitle(), restored.getTitle());
    Assertions.assertEquals(original.getCompany(), restored.getCompany());
    Assertions.assertEquals(original.getLocation(), restored.getLocation());
    Assertions.assertEquals(original.getDescriptionText(),
        restored.getDescriptionText());
    Assertions.assertEquals(original.getApplicationUrl(),
        restored.getApplicationUrl());
    Assertions.assertEquals(original.getWorkArrangement(),
        restored.getWorkArrangement());
  }
}