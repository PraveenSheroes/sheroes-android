package appliedlife.pvtltd.SHEROES.models.entities.poll;

public enum PollType {
    TEXT("TEXT"),
    IMAGE("IMAGE"),
    BOOLEAN("BOOLEAN"),
    EMOJI("EMOJI");

    private final String string;

    PollType(final String string) {
        this.string = string;
    }


    @Override
    public String toString() {
        return string;
    }
}
