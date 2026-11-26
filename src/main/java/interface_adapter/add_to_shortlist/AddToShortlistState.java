package interface_adapter.add_to_shortlist;

/**
 * Holds the presentation state for the Add to Shortlist use case.
 */
public class AddToShortlistState {

  private String savedPostingId = "";
  private String successMessage = "";
  private String errorMessage = "";

  public String getSavedPostingId() {
    return savedPostingId;
  }

  public void setSavedPostingId(String savedPostingId) {
    this.savedPostingId = savedPostingId;
  }

  public String getSuccessMessage() {
    return successMessage;
  }

  public void setSuccessMessage(String successMessage) {
    this.successMessage = successMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}