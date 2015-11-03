package registerunregister;

import android.app.Fragment;
import android.os.Bundle;

public class FragmentWithoutUnregister extends Fragment{

	private FollowService mFollowService;

	private final FollowService.Listener mListener = new FollowService.Listener() {
		@Override
		public void onChanged(boolean isFollow) {
			// Change the view.
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFollowService = new FollowService();
	}

	@Override
	public void onStart() {
		super.onStart();
		mFollowService.registerListener(mListener);
	}

	@Override
	public void onStop() {
		super.onStop();
	}
}
