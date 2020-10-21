/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 27 "../../../../../FlexiBookTransferObjects.ump"
public class TOBusiness
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBusiness Attributes
  private String Name;
  private String Adress;
  private String PhoneNumber;
  private String Email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBusiness(String aName, String aAdress, String aPhoneNumber, String aEmail)
  {
    Name = aName;
    Adress = aAdress;
    PhoneNumber = aPhoneNumber;
    Email = aEmail;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    Name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAdress(String aAdress)
  {
    boolean wasSet = false;
    Adress = aAdress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    PhoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    Email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return Name;
  }

  public String getAdress()
  {
    return Adress;
  }

  public String getPhoneNumber()
  {
    return PhoneNumber;
  }

  public String getEmail()
  {
    return Email;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "Name" + ":" + getName()+ "," +
            "Adress" + ":" + getAdress()+ "," +
            "PhoneNumber" + ":" + getPhoneNumber()+ "," +
            "Email" + ":" + getEmail()+ "]";
  }
}