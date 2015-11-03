package registerunregister;

import java.util.HashSet;
import java.util.Set;

public class FollowService {

	private Set<Listener> mListeners = new HashSet<>();

	public void registerListener(Listener listener) {
		mListeners.add(listener);
	}

	public void unregisterListener(Listener listener) {
		mListeners.remove(listener);
	}

	public void notify(boolean isFollow) {
		for (Listener listener : mListeners) {
			listener.onChanged(isFollow);
		}
	}

	public interface Listener {
		void onChanged(boolean isFollow);
	}
}
