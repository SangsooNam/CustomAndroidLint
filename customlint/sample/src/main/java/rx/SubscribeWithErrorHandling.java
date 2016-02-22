package rx;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class SubscribeWithErrorHandling {

	private Observable<String> mObservable = Observable.just("A", "B", "C");

	public void subscribe(Action1<String> onNext, Action1<Throwable> onError) {
		mObservable.subscribe(onNext, onError);
	}

	public void subscribe(Observer<String> observer) {
		mObservable.subscribe(observer);
	}
}
