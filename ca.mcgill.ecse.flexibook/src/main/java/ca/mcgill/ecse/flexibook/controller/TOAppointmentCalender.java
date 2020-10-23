/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 59 "../../../../../FlexiBookTransferObjects.ump"
public class TOAppointmentCalender
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointmentCalender Attributes
  private String nameOfTheCustomer;
  private String serviceName;

  //TOAppointmentCalender Associations
  private TOTimeSlot timeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointmentCalender(String aNameOfTheCustomer, String aServiceName, TOTimeSlot aTimeSlot)
  {
    nameOfTheCustomer = aNameOfTheCustomer;
    serviceName = aServiceName;
    if (!setTimeSlot(aTimeSlot))
    {
      throw new RuntimeException("Unable to create TOAppointmentCalender due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public String getServiceName()
  {
    return serviceName;
  }
  /* Code from template association_GetOne */
  public TOTimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeSlot(TOTimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot != null)
    {
      timeSlot = aNewTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    timeSlot = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "nameOfTheCustomer" + ":" + getNameOfTheCustomer()+ "," +
            "serviceName" + ":" + getServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}