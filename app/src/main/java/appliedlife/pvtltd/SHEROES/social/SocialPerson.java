package appliedlife.pvtltd.SHEROES.social;

public class SocialPerson {

    public static final String LOGIN_TYPE_FACEBOOK = "Facebook";
    public static final String LOGIN_TYPE_GOOGLE = "Google";

    private String firstname;
    private String lastname;
    private String email;
    private long birthday;
    private String accesstoken;
    private String logintype;
    private String socialId;
    private String imageUrl;
    private String idToken;

    SocialPerson(String firstname, String lastname, String email, long birthday, String accesstoken, String logintype, String socialId, String imageUrl, String idToken) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.accesstoken = accesstoken;
        this.logintype = logintype;
        this.socialId = socialId;
        this.imageUrl=imageUrl;
        this.idToken = idToken;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public long getBirthday() {
        return birthday;
    }

    public String getAccessToken() {
        return accesstoken;
    }

    public String getLoginType() {
        return logintype;
    }

    public String getSocialId() {
        return socialId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIdToken() {
        return idToken;
    }
}