package interface_adapter.add_to_shortlist;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import use_case.add_to_shortlist.AddToShortlistOutputData;

/**
 * Unit tests for the Add to Shortlist presenter.
 */
class AddToShortlistPresenterTest {

  @Test
  void testPrepareSuccessView() {
    AddToShortlistViewModel viewModel = new AddToShortlistViewModel();
    AddToShortlistPresenter presenter = new AddToShortlistPresenter(viewModel);

    AddToShortlistOutputData outputData =
        new AddToShortlistOutputData("posting101", "Software Intern");

    presenter.prepareSuccessView(outputData);

    AddToShortlistState state = viewModel.getState();

    Assertions.assertEquals("posting101", state.getSavedPostingId());
    Assertions.assertEquals(
        "Software Intern added to shortlist.",
        state.getSuccessMessage());
    Assertions.assertEquals("", state.getErrorMessage());
  }

  @Test
  void testPrepareFailView() {
    AddToShortlistViewModel viewModel = new AddToShortlistViewModel();
    AddToShortlistPresenter presenter = new AddToShortlistPresenter(viewModel);

    presenter.prepareSuccessView(
        new AddToShortlistOutputData("posting101", "Software Intern"));

    presenter.prepareFailView("This job posting is already saved.");

    AddToShortlistState state = viewModel.getState();

    Assertions.assertEquals("", state.getSavedPostingId());
    Assertions.assertEquals("", state.getSuccessMessage());
    Assertions.assertEquals(
        "This job posting is already saved.",
        state.getErrorMessage());
  }

  @Test
  void testViewModelNotifiesListeners() {
    AddToShortlistViewModel viewModel = new AddToShortlistViewModel();
    AddToShortlistPresenter presenter = new AddToShortlistPresenter(viewModel);

    List<PropertyChangeEvent> events = new ArrayList<>();
    viewModel.addPropertyChangeListener(events::add);

    presenter.prepareSuccessView(
        new AddToShortlistOutputData("posting101", "Software Intern"));

    presenter.prepareFailView("Already saved.");

    Assertions.assertEquals(2, events.size());

    for (PropertyChangeEvent event : events) {
      Assertions.assertEquals(
          AddToShortlistViewModel.STATE_PROPERTY,
          event.getPropertyName());
    }

    Assertions.assertEquals(
        "posting101",
        ((AddToShortlistState) events.get(0).getNewValue())
            .getSavedPostingId());

    Assertions.assertEquals(
        "Already saved.",
        ((AddToShortlistState) events.get(1).getNewValue())
            .getErrorMessage());
  }
}
