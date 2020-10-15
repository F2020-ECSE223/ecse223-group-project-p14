/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;
<<<<<<< HEAD
import java.sql.Date;
import java.sql.Time;

// line 27 "../../../../../Version 0.5 - CC, YW.ump"
// line 136 "../../../../../Version 0.5 - CC, YW.ump"
=======

// line 17 "../../../../../Domain Model v1.1.ump"
// line 117 "../../../../../Domain Model v1.1.ump"
>>>>>>> master
public abstract class Account
{

  //------------------------
<<<<<<< HEAD
=======
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Account> accountsByName = new HashMap<String, Account>();

  //------------------------
>>>>>>> master
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String name;
  private String password;
<<<<<<< HEAD
  private boolean isOwner;

  //Account Associations
  private FlexiBookSystem flexiBookSystem;
  private List<Login> loginRecords;
=======
  private boolean isCurrentlyLoggedIn;

  //Account Associations
  private FlexiBookSystem flexiBookSystem;
>>>>>>> master

  //------------------------
  // CONSTRUCTOR
  //------------------------

<<<<<<< HEAD
  public Account(String aName, String aPassword, boolean aIsOwner, FlexiBookSystem aFlexiBookSystem)
  {
    name = aName;
    password = aPassword;
    isOwner = aIsOwner;
=======
  public Account(String aName, String aPassword, boolean aIsCurrentlyLoggedIn, FlexiBookSystem aFlexiBookSystem)
  {
    password = aPassword;
    isCurrentlyLoggedIn = aIsCurrentlyLoggedIn;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
>>>>>>> master
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create account due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
<<<<<<< HEAD
    loginRecords = new ArrayList<Login>();
=======
>>>>>>> master
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
<<<<<<< HEAD
    name = aName;
    wasSet = true;
=======
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      accountsByName.remove(anOldName);
    }
    accountsByName.put(aName, this);
>>>>>>> master
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

<<<<<<< HEAD
  public boolean setIsOwner(boolean aIsOwner)
  {
    boolean wasSet = false;
    isOwner = aIsOwner;
=======
  public boolean setIsCurrentlyLoggedIn(boolean aIsCurrentlyLoggedIn)
  {
    boolean wasSet = false;
    isCurrentlyLoggedIn = aIsCurrentlyLoggedIn;
>>>>>>> master
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
<<<<<<< HEAD
=======
  /* Code from template attribute_GetUnique */
  public static Account getWithName(String aName)
  {
    return accountsByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }
>>>>>>> master

  public String getPassword()
  {
    return password;
  }

<<<<<<< HEAD
  public boolean getIsOwner()
  {
    return isOwner;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOwner()
  {
    return isOwner;
=======
  public boolean getIsCurrentlyLoggedIn()
  {
    return isCurrentlyLoggedIn;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsCurrentlyLoggedIn()
  {
    return isCurrentlyLoggedIn;
>>>>>>> master
  }
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
<<<<<<< HEAD
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
=======
>>>>>>> master
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
<<<<<<< HEAD
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
=======

  public void delete()
  {
    accountsByName.remove(getName());
>>>>>>> master
    FlexiBookSystem placeholderFlexiBookSystem = flexiBookSystem;
    this.flexiBookSystem = null;
    if(placeholderFlexiBookSystem != null)
    {
      placeholderFlexiBookSystem.removeAccount(this);
    }
<<<<<<< HEAD
    for(int i=loginRecords.size(); i > 0; i--)
    {
      Login aLoginRecord = loginRecords.get(i - 1);
      aLoginRecord.delete();
    }
=======
>>>>>>> master
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
<<<<<<< HEAD
            "isOwner" + ":" + getIsOwner()+ "]" + System.getProperties().getProperty("line.separator") +
=======
            "isCurrentlyLoggedIn" + ":" + getIsCurrentlyLoggedIn()+ "]" + System.getProperties().getProperty("line.separator") +
>>>>>>> master
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null");
  }
}