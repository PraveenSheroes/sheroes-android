package appliedlife.pvtltd.SHEROES.vernacular;

public enum LanguageType {
    /*These names should be matched with server names */
    ENGLISH("en"),
    HINDI("hi");

    private final String languageName;

    LanguageType(final String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return languageName;
    }
}
