/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 120 "Version 0.5 - CC, YW.ump"
// line 209 "Version 0.5 - CC, YW.ump"
public class Login
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Login Attributes
  private boolean isCurrentLogin;
  private Date loginDate;
  private Time loginTime;

  //Login Associations
  private Account account;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Login(boolean aIsCurrentLogin, Date aLoginDate, Time aLoginTime, Account aAccount)
  {
    isCurrentLogin = aIsCurrentLogin;
    loginDate = aLoginDate;
    loginTime = aLoginTime;
    boolean didAddAccount = setAccount(aAccount);
    if (!didAddAccount)
    {
      throw new RuntimeException("Unable to create loginRecord due to account. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsCurrentLogin(boolean aIsCurrentLogin)
  {
    boolean wasSet = false;
    isCurrentLogin = aIsCurrentLogin;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoginDate(Date aLoginDate)
  {
    boolean wasSet = false;
    loginDate = aLoginDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoginTime(Time aLoginTime)
  {
    boolean wasSet = false;
    loginTime = aLoginTime;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsCurrentLogin()
  {
    return isCurrentLogin;
  }

  public Date getLoginDate()
  {
    return loginDate;
  }

  public Time getLoginTime()
  {
    return loginTime;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsCurrentLogin()
  {
    return isCurrentLogin;
  }
  /* Code from template association_GetOne */
  public Account getAccount()
  {
    return account;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAccount(Account aAccount)
  {
    boolean wasSet = false;
    if (aAccount == null)
    {
      return wasSet;
    }

    Account existingAccount = account;
    account = aAccount;
    if (existingAccount != null && !existingAccount.equals(aAccount))
    {
      existingAccount.removeLoginRecord(this);
    }
    account.addLoginRecord(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Account placeholderAccount = account;
    this.account = null;
    if(placeholderAccount != null)
    {
      placeholderAccount.removeLoginRecord(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isCurrentLogin" + ":" + getIsCurrentLogin()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "loginDate" + "=" + (getLoginDate() != null ? !getLoginDate().equals(this)  ? getLoginDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loginTime" + "=" + (getLoginTime() != null ? !getLoginTime().equals(this)  ? getLoginTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "account = "+(getAccount()!=null?Integer.toHexString(System.identityHashCode(getAccount())):"null");
  }
}