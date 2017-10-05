package appliedlife.pvtltd.SHEROES.analytics;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum PeopleProperty {
    // NOTE: Capitalize the first letter
    TOTAL_ARTICLES_LIKED("Total Articles Liked"),
    TOTAL_ARTICLES_SHARED("Total Articles Shared"),
    TOTAL_ARTICLES_READ("Total Articles Read"),
    TOTAL_CHAT_SESSIONS("Total Chat Sessions"),
    TOTAL_QUESTIONS_ASKED("Total Questions Asked"),
    TOTAL_QUESTIONS_ANSWERED("Total Questions Answered"),
    TOTAL_ANSWERS_LIKED("Total Answers Liked"),

    LAST_APP_OPEN("Last App Open"),
    APP_OPENS("App Opens"),
    INSTALL_SOURCE("Install Source"),
    MEDIUM("Medium"),
    CAMPAIGN("Campaign"),
    CURRENT_STATUS("Current Status"),

    LOOKING_FOR("Looking For"),
    SKILLS("Skills"),
    INTEREST("Interest"),
    USER_TYPE("User Type"),
    WORK_EXPERIENCE("Work Experience");

    private final String string;

    PeopleProperty(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
