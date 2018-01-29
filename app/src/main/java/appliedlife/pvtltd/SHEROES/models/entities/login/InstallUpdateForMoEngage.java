package appliedlife.pvtltd.SHEROES.models.entities.login;

import org.parceler.Parcel;

/**
 * Created by Praveen_Singh on 12-05-2017.
 */
@Parcel(analyze = {InstallUpdateForMoEngage.class})
public class InstallUpdateForMoEngage {
    private int appVersion;
    private boolean isFirstOpen;
    private boolean isWelcome;
    private boolean isAppInstallFirstTime;
    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public InstallUpdateForMoEngage() {
    }

    public boolean isFirstOpen() {
        return isFirstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        isFirstOpen = firstOpen;
    }

    public boolean isWelcome() {
        return isWelcome;
    }

    public void setWelcome(boolean welcome) {
        isWelcome = welcome;
    }

    public boolean isAppInstallFirstTime() {
        return isAppInstallFirstTime;
    }

    public void setAppInstallFirstTime(boolean appInstallFirstTime) {
        isAppInstallFirstTime = appInstallFirstTime;
    }
}
