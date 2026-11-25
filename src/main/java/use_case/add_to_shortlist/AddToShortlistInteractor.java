package use_case.add_to_shortlist;

import java.util.Objects;

/**
 * Interactor for adding a job posting to a user's shortlist.
 */
public class AddToShortlistInteractor implements AddToShortlistInputBoundary {

  private final AddToShortlistDataAccessInterface dataAccess;
  private final AddToShortlistOutputBoundary outputBoundary;

  /**
   * Constructs the interactor.
   *
   * @param dataAccess the shortlist data access interface
   * @param outputBoundary the output boundary
   */
  public AddToShortlistInteractor(
      AddToShortlistDataAccessInterface dataAccess,
      AddToShortlistOutputBoundary outputBoundary) {
    this.dataAccess = Objects.requireNonNull(dataAccess);
    this.outputBoundary = Objects.requireNonNull(outputBoundary);
  }

  @Override
  public void execute(AddToShortlistInputData inputData) {
    if (inputData == null
        || inputData.getUserId() == null
        || inputData.getUserId().isBlank()
        || inputData.getPosting() == null) {
      outputBoundary.prepareFailView("Invalid user or job posting.");
      return;
    }

    try {
      boolean saved = dataAccess.saveIfAbsent(
          inputData.getUserId(), inputData.getPosting());

      if (!saved) {
        outputBoundary.prepareFailView("This job posting is already saved.");
        return;
      }
    } catch (IllegalStateException exception) {
      outputBoundary.prepareFailView("The job posting could not be saved.");
      return;
    }

    AddToShortlistOutputData outputData = new AddToShortlistOutputData(
        inputData.getPosting().getStableId(),
        inputData.getPosting().getTitle());

    outputBoundary.prepareSuccessView(outputData);
  }
}