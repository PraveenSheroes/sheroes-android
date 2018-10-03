package appliedlife.pvtltd.SHEROES.vernacular;

public enum LanguageType {
    ENGLISH("en"),
    HINDI("hi");

    private final String string;

    LanguageType(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
