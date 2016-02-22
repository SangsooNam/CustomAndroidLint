package rx;

import rx.functions.Action1;

public class SubscribeWithoutErrorHandling {

	private Observable<String> mObservable = Observable.just("A", "B", "C");

	public void subscribe() {
		mObservable.subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
			}
		});
	}
}
