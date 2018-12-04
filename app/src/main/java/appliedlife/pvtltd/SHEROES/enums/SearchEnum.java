package appliedlife.pvtltd.SHEROES.enums;

public enum SearchEnum {
    TOP("posts "),
    COMMUNITIES("communities"),
    HASHTAGS("hashtags"),
    ARTICLES("articles");

    private final String string;

    SearchEnum(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
