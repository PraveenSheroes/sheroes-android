package appliedlife.pvtltd.SHEROES.utils;

/**
 * Created by Ujjwal on 31-01-2018.
 */

public class SheroesBus extends RxBus {
    private static RxBus rxBus;

    public static RxBus getInstance(){
        if(rxBus == null) {
            rxBus = new RxBus();
        }
        return rxBus;
    }
}
