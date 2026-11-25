package use_case.add_to_shortlist;

/**
 * Input boundary for the Add to Shortlist use case.
 */
public interface AddToShortlistInputBoundary {

  /**
   * Executes the Add to Shortlist use case.
   *
   * @param inputData the input data containing the user and posting
   */
  void execute(AddToShortlistInputData inputData);
}