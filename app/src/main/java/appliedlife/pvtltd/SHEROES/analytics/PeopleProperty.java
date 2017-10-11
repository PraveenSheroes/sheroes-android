package appliedlife.pvtltd.SHEROES.analytics;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum PeopleProperty {
    // NOTE: Capitalize the first letter
    CURRENT_STATUS("Current Status"),
    SKILLS("Skills"),
    INTEREST("Interest"),
    WORK_EXPERIENCE("Work Experience");

    private final String string;

    PeopleProperty(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
