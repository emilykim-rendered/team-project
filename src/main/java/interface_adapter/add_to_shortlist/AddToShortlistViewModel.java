package interface_adapter.add_to_shortlist;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

/**
 * View model for the Add to Shortlist use case.
 */
public class AddToShortlistViewModel {

  public static final String STATE_PROPERTY = "state";

  private final PropertyChangeSupport support =
      new PropertyChangeSupport(this);

  private AddToShortlistState state = new AddToShortlistState();

  /**
   * Returns the current presentation state.
   *
   * @return the current state
   */
  public AddToShortlistState getState() {
    return state;
  }

  /**
   * Updates the state and notifies listeners.
   *
   * @param state the new presentation state
   */
  public void setState(AddToShortlistState state) {
    this.state = Objects.requireNonNull(state);
    support.firePropertyChange(STATE_PROPERTY, null, state);
  }

  /**
   * Registers a listener for state changes.
   *
   * @param listener the listener to register
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a registered listener.
   *
   * @param listener the listener to remove
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }
}
