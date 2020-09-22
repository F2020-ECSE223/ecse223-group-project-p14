/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 24 "Version 0.5 - CC, YW.ump"
// line 136 "Version 0.5 - CC, YW.ump"
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String name;
  private String password;
  private boolean isOwner;

  //Account Associations
  private FlexiBookSystem flexiBookSystem;
  private List<Login> loginRecords;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aName, String aPassword, boolean aIsOwner, FlexiBookSystem aFlexiBookSystem)
  {
    name = aName;
    password = aPassword;
    isOwner = aIsOwner;
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create account due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    loginRecords = new ArrayList<Login>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsOwner(boolean aIsOwner)
  {
    boolean wasSet = false;
    isOwner = aIsOwner;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public boolean getIsOwner()
  {
    return isOwner;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOwner()
  {
    return isOwner;
  }
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
  /* Code from template association_GetMany */
  public Login getLoginRecord(int index)
  {
    Login aLoginRecord = loginRecords.get(index);
    return aLoginRecord;
  }

  public List<Login> getLoginRecords()
  {
    List<Login> newLoginRecords = Collections.unmodifiableList(loginRecords);
    return newLoginRecords;
  }

  public int numberOfLoginRecords()
  {
    int number = loginRecords.size();
    return number;
  }

  public boolean hasLoginRecords()
  {
    boolean has = loginRecords.size() > 0;
    return has;
  }

  public int indexOfLoginRecord(Login aLoginRecord)
  {
    int index = loginRecords.indexOf(aLoginRecord);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFlexiBookSystem(FlexiBookSystem aFlexiBookSystem)
  {
    boolean wasSet = false;
    if (aFlexiBookSystem == null)
    {
      return wasSet;
    }

    FlexiBookSystem existingFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = aFlexiBookSystem;
    if (existingFlexiBookSystem != null && !existingFlexiBookSystem.equals(aFlexiBookSystem))
    {
      existingFlexiBookSystem.removeAccount(this);
    }
    flexiBookSystem.addAccount(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoginRecords()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Login addLoginRecord(boolean aIsCurrentLogin, Date aLoginDate, Time aLoginTime)
  {
    return new Login(aIsCurrentLogin, aLoginDate, aLoginTime, this);
  }

  public boolean addLoginRecord(Login aLoginRecord)
  {
    boolean wasAdded = false;
    if (loginRecords.contains(aLoginRecord)) { return false; }
    Account existingAccount = aLoginRecord.getAccount();
    boolean isNewAccount = existingAccount != null && !this.equals(existingAccount);
    if (isNewAccount)
    {
      aLoginRecord.setAccount(this);
    }
    else
    {
      loginRecords.add(aLoginRecord);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoginRecord(Login aLoginRecord)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoginRecord, as it must always have a account
    if (!this.equals(aLoginRecord.getAccount()))
    {
      loginRecords.remove(aLoginRecord);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoginRecordAt(Login aLoginRecord, int index)
  {  
    boolean wasAdded = false;
    if(addLoginRecord(aLoginRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoginRecords()) { index = numberOfLoginRecords() - 1; }
      loginRecords.remove(aLoginRecord);
      loginRecords.add(index, aLoginRecord);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoginRecordAt(Login aLoginRecord, int index)
  {
    boolean wasAdded = false;
    if(loginRecords.contains(aLoginRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoginRecords()) { index = numberOfLoginRecords() - 1; }
      loginRecords.remove(aLoginRecord);
      loginRecords.add(index, aLoginRecord);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoginRecordAt(aLoginRecord, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    FlexiBookSystem placeholderFlexiBookSystem = flexiBookSystem;
    this.flexiBookSystem = null;
    if(placeholderFlexiBookSystem != null)
    {
      placeholderFlexiBookSystem.removeAccount(this);
    }
    for(int i=loginRecords.size(); i > 0; i--)
    {
      Login aLoginRecord = loginRecords.get(i - 1);
      aLoginRecord.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "isOwner" + ":" + getIsOwner()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null");
  }
}