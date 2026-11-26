package data_access;

import entity.JobPosting;
import entity.WorkArrangement;
import org.json.JSONObject;

/**
 * Converts job postings to and from JSON for local persistence.
 */
final class SavedJobPostingJsonMapper {

  private SavedJobPostingJsonMapper() {
  }

  /**
   * Converts a job posting into a JSON object.
   *
   * @param posting the posting to convert
   * @return the JSON representation
   */
  static JSONObject toJson(JobPosting posting) {
    return new JSONObject()
        .put("source", posting.getSource())
        .put("boardToken", posting.getBoardToken())
        .put("externalJobId", posting.getExternalJobId())
        .put("title", posting.getTitle())
        .put("company", posting.getCompany())
        .put("location", posting.getLocation())
        .put("descriptionText", posting.getDescriptionText())
        .put("applicationUrl", posting.getApplicationUrl())
        .put("workArrangement", posting.getWorkArrangement().name());
  }

  /**
   * Reconstructs a job posting from JSON.
   *
   * @param json the saved posting data
   * @return the reconstructed job posting
   */
  static JobPosting fromJson(JSONObject json) {
    return new JobPosting.Builder(
        json.getString("source"),
        json.getString("boardToken"),
        json.getString("externalJobId"))
        .title(json.getString("title"))
        .company(json.getString("company"))
        .location(json.getString("location"))
        .descriptionText(json.getString("descriptionText"))
        .applicationUrl(json.getString("applicationUrl"))
        .workArrangement(
            WorkArrangement.valueOf(json.getString("workArrangement")))
        .build();
  }
}