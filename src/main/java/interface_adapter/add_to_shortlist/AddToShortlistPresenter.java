package interface_adapter.add_to_shortlist;

import java.util.Objects;
import use_case.add_to_shortlist.AddToShortlistOutputBoundary;
import use_case.add_to_shortlist.AddToShortlistOutputData;

/**
 * Presenter for the Add to Shortlist use case.
 */
public class AddToShortlistPresenter
    implements AddToShortlistOutputBoundary {

  private final AddToShortlistViewModel viewModel;

  /**
   * Constructs the presenter.
   *
   * @param viewModel the view model to update
   */
  public AddToShortlistPresenter(AddToShortlistViewModel viewModel) {
    this.viewModel = Objects.requireNonNull(viewModel);
  }

  @Override
  public void prepareSuccessView(AddToShortlistOutputData outputData) {
    AddToShortlistState state = new AddToShortlistState();

    state.setSavedPostingId(outputData.getPostingId());
    state.setSuccessMessage(
        outputData.getPostingTitle() + " added to shortlist.");

    viewModel.setState(state);
  }

  @Override
  public void prepareFailView(String errorMessage) {
    AddToShortlistState state = new AddToShortlistState();

    state.setErrorMessage(errorMessage);

    viewModel.setState(state);
  }
}