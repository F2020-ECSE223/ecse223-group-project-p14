/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 29 "../../../../../FlexiBookTransferObjects.ump"
public class TOBusiness
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBusiness Attributes
  private String name;
  private String adress;
  private String phoneNumber;
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBusiness(String aName, String aAdress, String aPhoneNumber, String aEmail)
  {
    name = aName;
    adress = aAdress;
    phoneNumber = aPhoneNumber;
    email = aEmail;
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

  public boolean setAdress(String aAdress)
  {
    boolean wasSet = false;
    adress = aAdress;
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

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getAdress()
  {
    return adress;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "adress" + ":" + getAdress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}