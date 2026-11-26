package data_access;

import entity.JobPosting;
import entity.Shortlist;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.add_to_shortlist.AddToShortlistDataAccessInterface;

/**
 * File-based persistence for users' shortlisted job postings.
 */
public final class FileShortlistDataAccessObject
    implements AddToShortlistDataAccessInterface {

  private final Path filePath;

  /**
   * Constructs a file-based shortlist repository.
   *
   * @param filePath the JSON storage file
   */
  public FileShortlistDataAccessObject(Path filePath) {
    this.filePath = Objects.requireNonNull(filePath).toAbsolutePath();
  }

  @Override
  public synchronized boolean saveIfAbsent(String userId, JobPosting posting) {
    Objects.requireNonNull(posting, "Posting must not be null.");

    JSONObject document = readDocument();
    Shortlist shortlist = loadShortlist(document, userId);

    if (!shortlist.addPosting(posting)) {
      return false;
    }

    JSONArray postings = new JSONArray();
    for (JobPosting saved : shortlist.getPostings()) {
      postings.put(SavedJobPostingJsonMapper.toJson(saved));
    }

    document.put(userId, postings);
    writeDocument(document);
    return true;
  }

  @Override
  public synchronized List<JobPosting> getSavedPostings(String userId) {
    JSONObject document = readDocument();
    return loadShortlist(document, userId).getPostings();
  }

  private Shortlist loadShortlist(JSONObject document, String userId) {
    Shortlist shortlist = new Shortlist(userId);

    if (document.has(userId)) {
      try {
        JSONArray postings = document.getJSONArray(userId);

        for (int i = 0; i < postings.length(); i++) {
          JobPosting posting = SavedJobPostingJsonMapper.fromJson(
              postings.getJSONObject(i));

          if (!shortlist.addPosting(posting)) {
            throw new IllegalStateException("Duplicate saved posting.");
          }
        }
      } catch (JSONException | IllegalArgumentException exception) {
        throw new IllegalStateException("Invalid shortlist data.", exception);
      }
    }

    return shortlist;
  }

  private JSONObject readDocument() {
    try {
      if (Files.notExists(filePath)) {
        return new JSONObject();
      }

      String content = Files.readString(filePath, StandardCharsets.UTF_8);
      return new JSONObject(content);
    } catch (IOException | JSONException exception) {
      throw new IllegalStateException("Cannot read shortlist file.", exception);
    }
  }

  private void writeDocument(JSONObject document) {
    Path temporaryFile = null;

    try {
      Path directory = filePath.getParent();
      Files.createDirectories(directory);

      temporaryFile = Files.createTempFile(directory, "shortlist-", ".tmp");

      Files.writeString(
          temporaryFile, document.toString(2), StandardCharsets.UTF_8);

      Files.move(
          temporaryFile,
          filePath,
          StandardCopyOption.ATOMIC_MOVE,
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot save shortlist file.", exception);
    } finally {
      if (temporaryFile != null) {
        try {
          Files.deleteIfExists(temporaryFile);
        } catch (IOException ignored) {
          // The existing shortlist file remains untouched.
        }
      }
    }
  }
}