/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 38 "../../../../../Domain Model v1.1.ump"
// line 139 "../../../../../Domain Model v1.1.ump"
public class CustomerAccount extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CustomerAccount Attributes
  private boolean isOwner;

  //CustomerAccount Associations
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CustomerAccount(String aName, String aPassword, boolean aIsOwner, boolean aIsActive, FlexiBookSystem aFlexiBookSystem)
  {
    super(aName, aPassword, aIsOwner, aIsActive, aFlexiBookSystem);
    resetIsOwner();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setIsOwner(boolean aIsOwner)
  {
    boolean wasSet = false;
    isOwner = aIsOwner;
    wasSet = true;
    return wasSet;
  }

  public boolean resetIsOwner()
  {
    boolean wasReset = false;
    isOwner = getDefaultIsOwner();
    wasReset = true;
    return wasReset;
  }

  public boolean getIsOwner()
  {
    return isOwner;
  }
  /* Code from template attribute_GetDefaulted */
  public boolean getDefaultIsOwner()
  {
    return false;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOwner()
  {
    return isOwner;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Date aAppointmentStartDate, Time aAppointmentStartTime, boolean aIsCancelled, Service aServiceChosen)
  {
    return new Appointment(aAppointmentStartDate, aAppointmentStartTime, aIsCancelled, this, aServiceChosen);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    CustomerAccount existingCustomerAccount = aAppointment.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount)
    {
      aAppointment.setCustomerAccount(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a customerAccount
    if (!this.equals(aAppointment.getCustomerAccount()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (appointments.size() > 0)
    {
      Appointment aAppointment = appointments.get(appointments.size() - 1);
      aAppointment.delete();
      appointments.remove(aAppointment);
    }
    
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isOwner" + ":" + getIsOwner()+ "]";
  }
}