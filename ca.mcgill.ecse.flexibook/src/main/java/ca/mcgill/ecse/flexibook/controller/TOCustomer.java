/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 52 "../../../../../FlexiBookTransferObjects.ump"
public class TOCustomer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCustomer Attributes
  private String userName;
  private String password;
  private int noShowCount;
  private int showCount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCustomer(String aUserName, String aPassword, int aNoShowCount, int aShowCount)
  {
    userName = aUserName;
    password = aPassword;
    noShowCount = aNoShowCount;
    showCount = aShowCount;
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
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
  
  public boolean setNoShowCount(int aNoShowCount) {
	  boolean wasSet = false;
	  noShowCount = aNoShowCount;
	  wasSet = true;
	  return wasSet;
  }
  
  public boolean setShowCount(int aShowCount) {
	  boolean wasSet = false;
	  showCount = aShowCount;
	  wasSet = true;
	  return wasSet;
  }
  
  public String getUserName()
  {
    return userName;
  }

  public String getPassword()
  {
    return password;
  }
  
  public int getNoShowCount()
  {
    return noShowCount;
  }
  
  public int getShowCount()
  {
    return showCount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "noShowCount" + ":" + getNoShowCount() + "," +
            "showCount" + ":" + getShowCount() + "]";
  }
}