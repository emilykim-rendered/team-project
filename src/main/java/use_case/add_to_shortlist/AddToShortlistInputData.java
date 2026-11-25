package use_case.add_to_shortlist;

import entity.JobPosting;

/**
 * Input data for the Add to Shortlist use case.
 */
public class AddToShortlistInputData {

  private final String userId;
  private final JobPosting posting;

  /**
   * Constructs the input data.
   *
   * @param userId the identifier of the user
   * @param posting the internship posting to save
   */
  public AddToShortlistInputData(String userId, JobPosting posting) {
    this.userId = userId;
    this.posting = posting;
  }

  String getUserId() {
    return userId;
  }

  JobPosting getPosting() {
    return posting;
  }
}