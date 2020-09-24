/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 13 "../../../../../Version 0.5 - CC, YW.ump"
// line 130 "../../../../../Version 0.5 - CC, YW.ump"
public class FlexiBookSystem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static FlexiBookSystem theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FlexiBookSystem Attributes
  private Date currentDate;
  private Time currentTime;
  private String address;
  private String phoneNumber;
  private String emailAddress;

  //FlexiBookSystem Associations
  private List<Account> accounts;
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private FlexiBookSystem()
  {
    currentDate = null;
    currentTime = null;
    address = null;
    phoneNumber = null;
    emailAddress = null;
    accounts = new ArrayList<Account>();
  }

  public static FlexiBookSystem getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new FlexiBookSystem();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrentDate(Date aCurrentDate)
  {
    boolean wasSet = false;
    currentDate = aCurrentDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentTime(Time aCurrentTime)
  {
    boolean wasSet = false;
    currentTime = aCurrentTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public Date getCurrentDate()
  {
    return currentDate;
  }

  public Time getCurrentTime()
  {
    return currentTime;
  }

  /**
   * attribute of the business
   */
  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }
  /* Code from template association_GetMany */
  public Account getAccount(int index)
  {
    Account aAccount = accounts.get(index);
    return aAccount;
  }

  public List<Account> getAccounts()
  {
    List<Account> newAccounts = Collections.unmodifiableList(accounts);
    return newAccounts;
  }

  public int numberOfAccounts()
  {
    int number = accounts.size();
    return number;
  }

  public boolean hasAccounts()
  {
    boolean has = accounts.size() > 0;
    return has;
  }

  public int indexOfAccount(Account aAccount)
  {
    int index = accounts.indexOf(aAccount);
    return index;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }

  public boolean hasCalendar()
  {
    boolean has = calendar != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccounts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addAccount(Account aAccount)
  {
    boolean wasAdded = false;
    if (accounts.contains(aAccount)) { return false; }
    FlexiBookSystem existingFlexiBookSystem = aAccount.getFlexiBookSystem();
    boolean isNewFlexiBookSystem = existingFlexiBookSystem != null && !this.equals(existingFlexiBookSystem);
    if (isNewFlexiBookSystem)
    {
      aAccount.setFlexiBookSystem(this);
    }
    else
    {
      accounts.add(aAccount);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAccount(Account aAccount)
  {
    boolean wasRemoved = false;
    //Unable to remove aAccount, as it must always have a flexiBookSystem
    if (!this.equals(aAccount.getFlexiBookSystem()))
    {
      accounts.remove(aAccount);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountAt(Account aAccount, int index)
  {  
    boolean wasAdded = false;
    if(addAccount(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountAt(Account aAccount, int index)
  {
    boolean wasAdded = false;
    if(accounts.contains(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountAt(aAccount, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setCalendar(Calendar aNewCalendar)
  {
    boolean wasSet = false;
    if (calendar != null && !calendar.equals(aNewCalendar) && equals(calendar.getFlexiBookSystem()))
    {
      //Unable to setCalendar, as existing calendar would become an orphan
      return wasSet;
    }

    calendar = aNewCalendar;
    FlexiBookSystem anOldFlexiBookSystem = aNewCalendar != null ? aNewCalendar.getFlexiBookSystem() : null;

    if (!this.equals(anOldFlexiBookSystem))
    {
      if (anOldFlexiBookSystem != null)
      {
        anOldFlexiBookSystem.calendar = null;
      }
      if (calendar != null)
      {
        calendar.setFlexiBookSystem(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (accounts.size() > 0)
    {
      Account aAccount = accounts.get(accounts.size() - 1);
      aAccount.delete();
      accounts.remove(aAccount);
    }
    
    Calendar existingCalendar = calendar;
    calendar = null;
    if (existingCalendar != null)
    {
      existingCalendar.delete();
      existingCalendar.setFlexiBookSystem(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentDate" + "=" + (getCurrentDate() != null ? !getCurrentDate().equals(this)  ? getCurrentDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentTime" + "=" + (getCurrentTime() != null ? !getCurrentTime().equals(this)  ? getCurrentTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}