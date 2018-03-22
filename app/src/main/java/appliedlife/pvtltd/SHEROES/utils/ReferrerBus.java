package appliedlife.pvtltd.SHEROES.utils;

/**
 * Created by Sowrabh on 7/26/2015.
 */
public class ReferrerBus extends RxBus {
    private static RxBus rxBus;

    public static RxBus getInstance(){
        if(rxBus == null) {
            rxBus = new RxBus();
        }
        return rxBus;
    }

}
