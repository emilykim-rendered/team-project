package use_case.add_to_shortlist;

import entity.JobPosting;
import java.util.List;

/**
 * Data access operations required by the shortlist use cases.
 */
public interface AddToShortlistDataAccessInterface {

  /**
   * Adds a posting to the user's shortlist if absent.
   * Implementations must enforce uniqueness using Shortlist.addPosting().
   *
   * @param userId the identifier of the user
   * @param posting the posting to save
   * @return true if successfully persisted, false if already saved
   * @throws IllegalStateException if persistence fails
   */
  boolean saveIfAbsent(String userId, JobPosting posting);

  /**
  * Returns the user's saved internship postings.
  *
  * @param userId the identifier of the user
  * @return the saved postings
  */
  List<JobPosting> getSavedPostings(String userId);
}