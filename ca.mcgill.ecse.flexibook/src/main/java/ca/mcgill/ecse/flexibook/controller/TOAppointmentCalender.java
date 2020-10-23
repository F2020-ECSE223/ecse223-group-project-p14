/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 43 "../../../../../FlexiBookTransferObjects.ump"
public class TOAppointmentCalender
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointmentCalender Attributes
  private String nameOfTheCustomer;
  private String timeSlot;
  private String serviceName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointmentCalender(String aNameOfTheCustomer, String aTimeSlot, String aServiceName)
  {
    nameOfTheCustomer = aNameOfTheCustomer;
    timeSlot = aTimeSlot;
    serviceName = aServiceName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNameOfTheCustomer(String aNameOfTheCustomer)
  {
    boolean wasSet = false;
    nameOfTheCustomer = aNameOfTheCustomer;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeSlot(String aTimeSlot)
  {
    boolean wasSet = false;
    timeSlot = aTimeSlot;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
    wasSet = true;
    return wasSet;
  }

  public String getNameOfTheCustomer()
  {
    return nameOfTheCustomer;
  }

  public String getTimeSlot()
  {
    return timeSlot;
  }

  public String getServiceName()
  {
    return serviceName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "nameOfTheCustomer" + ":" + getNameOfTheCustomer()+ "," +
            "timeSlot" + ":" + getTimeSlot()+ "," +
            "serviceName" + ":" + getServiceName()+ "]";
  }
}