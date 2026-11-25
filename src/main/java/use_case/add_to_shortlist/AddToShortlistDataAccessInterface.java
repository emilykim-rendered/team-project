package use_case.add_to_shortlist;

import entity.JobPosting;
import java.util.List;

/**
 * Data access operations required by the shortlist use cases.
 */
public interface AddToShortlistDataAccessInterface {

  /**
  * Saves a posting for the user if it has not already been saved.
  * Postings are identified by JobPosting.getStableId().
  *
  * @param userId the identifier of the user
  * @param posting the posting to save
  * @return true if saved, false if already present
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