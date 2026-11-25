package use_case.add_to_shortlist;

/**
 * Output boundary for the Add to Shortlist use case.
 */
public interface AddToShortlistOutputBoundary {

  /**
   * Prepares the success response when a posting is saved.
   *
   * @param outputData the saved posting information
   */
  void prepareSuccessView(AddToShortlistOutputData outputData);

  /**
   * Prepares the failure response when a posting cannot be saved.
   *
   * @param errorMessage the reason for the failure
   */
  void prepareFailView(String errorMessage);
}
