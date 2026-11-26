package use_case.add_to_shortlist;

import entity.JobPosting;
import interface_adapter.add_to_shortlist.AddToShortlistController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Add to Shortlist controller.
 */
class AddToShortlistControllerTest {

  @Test
  void testExecutePassesCorrectInputData() {
    JobPosting posting = new JobPosting.Builder(
        "greenhouse", "example", "101")
        .title("Software Engineering Intern")
        .company("Example Company")
        .applicationUrl("https://example.com/jobs/101")
        .build();

    RecordingInputBoundary inputBoundary = new RecordingInputBoundary();
    AddToShortlistController controller =
        new AddToShortlistController(inputBoundary);

    controller.execute("user1", posting);

    Assertions.assertEquals(1, inputBoundary.executeCalls);
    Assertions.assertNotNull(inputBoundary.receivedInput);
    Assertions.assertEquals(
        "user1", inputBoundary.receivedInput.getUserId());
    Assertions.assertSame(
        posting, inputBoundary.receivedInput.getPosting());
  }

  @Test
  void testInvalidInputIsDelegatedToInteractor() {
    RecordingInputBoundary inputBoundary = new RecordingInputBoundary();
    AddToShortlistController controller =
        new AddToShortlistController(inputBoundary);

    controller.execute("", null);

    Assertions.assertEquals(1, inputBoundary.executeCalls);
    Assertions.assertEquals(
        "", inputBoundary.receivedInput.getUserId());
    Assertions.assertNull(inputBoundary.receivedInput.getPosting());
  }

  /**
   * Records input passed to the use case boundary.
   */
  private static final class RecordingInputBoundary
      implements AddToShortlistInputBoundary {

    private AddToShortlistInputData receivedInput;
    private int executeCalls;

    @Override
    public void execute(AddToShortlistInputData inputData) {
      receivedInput = inputData;
      executeCalls++;
    }
  }
}