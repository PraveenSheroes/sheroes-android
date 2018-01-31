package appliedlife.pvtltd.SHEROES.utils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by Ujjwal on 31-01-2018.
 */

public class RxBus {

    private final PublishSubject<Object> bus = PublishSubject.create();
    private final HashMap<Object, Object> map = new HashMap<>();

    public void post(Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObserveable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public CompositeDisposable register(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        if (map.get(object) == null) {
            map.put(object, new CompositeDisposable());
        }
        return (CompositeDisposable) map.get(object);
    }

    public void unregister(Object object) {
        if (map.get(object) != null) {
            ((CompositeDisposable) map.get(object)).dispose();
            map.remove(object);
        }
    }

}
