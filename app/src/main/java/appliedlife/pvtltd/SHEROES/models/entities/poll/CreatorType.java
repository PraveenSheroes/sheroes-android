package appliedlife.pvtltd.SHEROES.models.entities.poll;

public enum CreatorType {
    COMMUNITY_OWNER("COMMUNITY_OWNER"),
    USER("USER"),
    ANONYMOUS("ANONYMOUS");

    private final String string;

    CreatorType(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
