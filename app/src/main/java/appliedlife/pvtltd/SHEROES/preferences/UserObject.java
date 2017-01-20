package appliedlife.pvtltd.SHEROES.preferences;

public class UserObject
{
  private String[] interest;

  private Linked_ids[] linked_ids;

  private User user;

  private String about_yourself;

  private Contacts[] contacts;

  public String[] getInterest ()
  {
    return interest;
  }

  public void setInterest (String[] interest)
  {
    this.interest = interest;
  }

  public Linked_ids[] getLinked_ids ()
  {
    return linked_ids;
  }

  public void setLinked_ids (Linked_ids[] linked_ids)
  {
    this.linked_ids = linked_ids;
  }

  public User getUser ()
  {
    return user;
  }

  public void setUser (User user)
  {
    this.user = user;
  }

  public String getAbout_yourself ()
  {
    return about_yourself;
  }

  public void setAbout_yourself (String about_yourself)
  {
    this.about_yourself = about_yourself;
  }

  public Contacts[] getContacts ()
  {
    return contacts;
  }

  public void setContacts (Contacts[] contacts)
  {
    this.contacts = contacts;
  }

  public class Linked_ids
  {
    private String image_url;

    private String proof_type;

    private String is_primary;

    public String getImage_url ()
    {
      return image_url;
    }

    public void setImage_url (String image_url)
    {
      this.image_url = image_url;
    }

    public String getProof_type ()
    {
      return proof_type;
    }

    public void setProof_type (String proof_type)
    {
      this.proof_type = proof_type;
    }

    public String getIs_primary ()
    {
      return is_primary;
    }

    public void setIs_primary (String is_primary)
    {
      this.is_primary = is_primary;
    }

    @Override
    public String toString()
    {
      return "ClassPojo [image_url = "+image_url+", proof_type = "+proof_type+", is_primary = "+is_primary+"]";
    }
  }


  public class Contacts
  {
    private String contact_number;

    private boolean is_primary;

    public String getContact_number ()
    {
      return contact_number;
    }

    public void setContact_number (String contact_number)
    {
      this.contact_number = contact_number;
    }

    public boolean getIs_primary ()
    {
      return is_primary;
    }

    public void setIs_primary (boolean is_primary)
    {
      this.is_primary = is_primary;
    }

    @Override
    public String toString()
    {
      return "ClassPojo [contact_number = "+contact_number+", is_primary = "+is_primary+"]";
    }
  }

  public class User
  {
    private String nationality;

    private String email;

    private String age;

    private String name;

    private String gender;

    public String getNationality ()
    {
      return nationality;
    }

    public void setNationality (String nationality)
    {
      this.nationality = nationality;
    }

    public String getEmail ()
    {
      return email;
    }

    public void setEmail (String email)
    {
      this.email = email;
    }

    public String getAge ()
    {
      return age;
    }

    public void setAge (String age)
    {
      this.age = age;
    }

    public String getName ()
    {
      return name;
    }

    public void setName (String name)
    {
      this.name = name;
    }

    public String getGender ()
    {
      return gender;
    }

    public void setGender (String gender)
    {
      this.gender = gender;
    }

    @Override
    public String toString()
    {
      return "ClassPojo [nationality = "+nationality+", email = "+email+", age = "+age+", name = "+name+", gender = "+gender+"]";
    }
  }
}
