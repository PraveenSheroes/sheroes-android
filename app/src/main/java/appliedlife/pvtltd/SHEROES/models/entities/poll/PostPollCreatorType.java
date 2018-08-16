package appliedlife.pvtltd.SHEROES.models.entities.poll;

public enum PostPollCreatorType {
    COMMUNITY_OWNER("COMMUNITY_OWNER"),
    USER("USER"),
    ANONYMOUS("ANONYMOUS");


    private final String string;

    PostPollCreatorType(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
