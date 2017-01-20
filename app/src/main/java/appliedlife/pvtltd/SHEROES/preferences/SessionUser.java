package appliedlife.pvtltd.SHEROES.preferences;

import com.f2prateek.rx.preferences.Preference;

public class SessionUser {
  public static final int FACEBOOK_LOGIN =1;
  public static final int SKIP_LOGIN =2;
  private String name;
  private String firstName;
  private String lastName;
  private String picUrl;
  private String email;
  private int id;
  private String phoneNumber;
  private int loginType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getLoginType() {
    return loginType;
  }

  public void setLoginType(int loginType) {
    this.loginType = loginType;
  }

  public static SessionUser assignNewSessionUserValues(Preference<SessionUser> userPreference, UserObject userObject){
    SessionUser sessionUser = new SessionUser();
    sessionUser.setId(userPreference.get().getId());
    sessionUser.setPicUrl(userPreference.get().getPicUrl());
    sessionUser.setName(userPreference.get().getName());
    sessionUser.setEmail(userPreference.get().getEmail());
    sessionUser.setLoginType(userPreference.get().getLoginType());
    sessionUser.setPhoneNumber(userPreference.get().getPhoneNumber());
    userPreference.delete();
    userPreference.set(sessionUser);
    return sessionUser;
  }
  public static SessionUser UpdateUserValues(Preference<SessionUser> userPreference, UserObject userObject){
    SessionUser sessionUser = new SessionUser();
    sessionUser.setId(userPreference.get().getId());
    sessionUser.setPicUrl(userPreference.get().getPicUrl());
    sessionUser.setName(userObject.getUser().getName());
    sessionUser.setEmail(userObject.getUser().getEmail());
    sessionUser.setLoginType(userPreference.get().getLoginType());
    if (userObject.getContacts() != null) {
      for (int i = 0; i < userObject.getContacts().length; i++) {
        if (userObject.getContacts()[i].getIs_primary() == true) {
          sessionUser.setPhoneNumber(userObject.getContacts()[i].getContact_number());
          break;
        }
      }
    }
    userPreference.delete();
    userPreference.set(sessionUser);
    return sessionUser;
  }
}

