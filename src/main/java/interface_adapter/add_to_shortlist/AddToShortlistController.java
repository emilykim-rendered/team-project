package interface_adapter.add_to_shortlist;

import entity.JobPosting;
import use_case.add_to_shortlist.AddToShortlistInputBoundary;
import use_case.add_to_shortlist.AddToShortlistInputData;

/**
 * Controller for the Add to Shortlist use case.
 */
public class AddToShortlistController {

  private final AddToShortlistInputBoundary interactor;

  /**
   * Constructs the controller.
   *
   * @param interactor the input boundary for the use case
   */
  public AddToShortlistController(AddToShortlistInputBoundary interactor) {
    this.interactor = interactor;
  }

  /**
   * Requests to save a job posting to the user's shortlist.
   *
   * @param userId the identifier of the user
   * @param posting the posting to save
   */
  public void execute(String userId, JobPosting posting) {
    AddToShortlistInputData inputData =
        new AddToShortlistInputData(userId, posting);

    interactor.execute(inputData);
  }
}