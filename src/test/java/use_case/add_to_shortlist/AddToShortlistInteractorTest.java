package use_case.add_to_shortlist;

import entity.JobPosting;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Add to Shortlist interactor.
 */
class AddToShortlistInteractorTest {

  private static JobPosting createPosting() {
    return new JobPosting.Builder("greenhouse", "example", "101")
        .title("Software Engineering Intern")
        .company("Example Company")
        .applicationUrl("https://example.com/jobs/101")
        .build();
  }

  @Test
  void testSuccessfulSave() {
    FakeDataAccess dataAccess = new FakeDataAccess();
    RecordingOutput output = new RecordingOutput();
    AddToShortlistInteractor interactor =
        new AddToShortlistInteractor(dataAccess, output);
    JobPosting posting = createPosting();

    interactor.execute(new AddToShortlistInputData("user1", posting));

    Assertions.assertEquals(1, dataAccess.saveCalls);
    Assertions.assertNotNull(output.success);
    Assertions.assertEquals(posting.getStableId(),
        output.success.getPostingId());
    Assertions.assertEquals(posting.getTitle(),
        output.success.getPostingTitle());
    Assertions.assertNull(output.error);
  }

  @Test
  void testDuplicatePosting() {
    FakeDataAccess dataAccess = new FakeDataAccess();
    dataAccess.saveResult = false;
    RecordingOutput output = new RecordingOutput();
    AddToShortlistInteractor interactor =
        new AddToShortlistInteractor(dataAccess, output);

    interactor.execute(new AddToShortlistInputData("user1", createPosting()));

    Assertions.assertNull(output.success);
    Assertions.assertEquals(
        "This job posting is already saved.", output.error);
  }

  @Test
  void testInvalidInput() {
    FakeDataAccess dataAccess = new FakeDataAccess();
    RecordingOutput output = new RecordingOutput();
    AddToShortlistInteractor interactor =
        new AddToShortlistInteractor(dataAccess, output);

    interactor.execute(null);

    Assertions.assertEquals(
        "Invalid user or job posting.", output.error);
    Assertions.assertEquals(0, dataAccess.saveCalls);
  }

  @Test
  void testStorageFailure() {
    FakeDataAccess dataAccess = new FakeDataAccess();
    dataAccess.storageFailure = true;
    RecordingOutput output = new RecordingOutput();
    AddToShortlistInteractor interactor =
        new AddToShortlistInteractor(dataAccess, output);

    interactor.execute(new AddToShortlistInputData("user1", createPosting()));

    Assertions.assertNull(output.success);
    Assertions.assertEquals(
        "The job posting could not be saved.", output.error);
  }

  private static final class FakeDataAccess
      implements AddToShortlistDataAccessInterface {

    private boolean saveResult = true;
    private boolean storageFailure;
    private int saveCalls;

    @Override
    public boolean saveIfAbsent(String userId, JobPosting posting) {
      saveCalls++;
      if (storageFailure) {
        throw new IllegalStateException("Storage unavailable.");
      }
      return saveResult;
    }

    @Override
    public List<JobPosting> getSavedPostings(String userId) {
      return List.of();
    }
  }

  private static final class RecordingOutput
      implements AddToShortlistOutputBoundary {

    private AddToShortlistOutputData success;
    private String error;

    @Override
    public void prepareSuccessView(AddToShortlistOutputData outputData) {
      success = outputData;
    }

    @Override
    public void prepareFailView(String errorMessage) {
      error = errorMessage;
    }
  }
}