package use_case.add_to_shortlist;

/**
 * Output data for the Add to Shortlist use case.
 */
public class AddToShortlistOutputData {

  private final String postingId;
  private final String postingTitle;

  /**
   * Constructs the output data for a saved posting.
   *
   * @param postingId the stable identifier of the posting
   * @param postingTitle the title of the posting
   */
  public AddToShortlistOutputData(String postingId, String postingTitle) {
    this.postingId = postingId;
    this.postingTitle = postingTitle;
  }

  /**
   * Returns the saved posting's identifier.
   *
   * @return the posting identifier
   */
  public String getPostingId() {
    return postingId;
  }

  /**
   * Returns the saved posting's title.
   *
   * @return the posting title
   */
  public String getPostingTitle() {
    return postingTitle;
  }
}