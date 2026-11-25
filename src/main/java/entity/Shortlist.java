package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user's shortlist of saved job postings.
 */
public final class Shortlist {

  private final String userId;
  private final List<JobPosting> postings;

  /**
   * Creates an empty shortlist for a user.
   *
   * @param userId the identifier of the user
   */
  public Shortlist(String userId) {
    if (userId == null || userId.isBlank()) {
      throw new IllegalArgumentException("User ID must not be blank.");
    }
    this.userId = userId;
    this.postings = new ArrayList<>();
  }

  /**
   * Returns the owner of this shortlist.
   *
   * @return the user identifier
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Adds a posting if it is not already saved.
   *
   * @param posting the job posting to add
   * @return true if added, false if already present
   */
  public boolean addPosting(JobPosting posting) {
    Objects.requireNonNull(posting, "Posting must not be null.");

    if (containsPosting(posting.getStableId())) {
      return false;
    }

    postings.add(posting);
    return true;
  }

  /**
   * Checks whether a posting is already saved.
   *
   * @param stableId the stable identifier of the posting
   * @return true if the posting is present
   */
  public boolean containsPosting(String stableId) {
    for (JobPosting posting : postings) {
      if (posting.getStableId().equals(stableId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns an unmodifiable snapshot of saved postings.
   *
   * @return the saved job postings
   */
  public List<JobPosting> getPostings() {
    return List.copyOf(postings);
  }
}