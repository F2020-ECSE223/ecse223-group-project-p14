/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Date;
import java.sql.Time;

// line 65 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 144 "../../../../../Domain Model (Iteration 1) v1.0.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Date appointmentDate;
  private Time appointmentTime;
  private boolean isCancelled;

  //Appointment Associations
  private CustomerAccount customerAccount;
  private Service serviceChosen;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Date aAppointmentDate, Time aAppointmentTime, boolean aIsCancelled, CustomerAccount aCustomerAccount, Service aServiceChosen)
  {
    appointmentDate = aAppointmentDate;
    appointmentTime = aAppointmentTime;
    isCancelled = aIsCancelled;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create appointment due to customerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setServiceChosen(aServiceChosen))
    {
      throw new RuntimeException("Unable to create Appointment due to aServiceChosen. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAppointmentDate(Date aAppointmentDate)
  {
    boolean wasSet = false;
    appointmentDate = aAppointmentDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setAppointmentTime(Time aAppointmentTime)
  {
    boolean wasSet = false;
    appointmentTime = aAppointmentTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsCancelled(boolean aIsCancelled)
  {
    boolean wasSet = false;
    isCancelled = aIsCancelled;
    wasSet = true;
    return wasSet;
  }

  public Date getAppointmentDate()
  {
    return appointmentDate;
  }

  public Time getAppointmentTime()
  {
    return appointmentTime;
  }

  public boolean getIsCancelled()
  {
    return isCancelled;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsCancelled()
  {
    return isCancelled;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_GetOne */
  public Service getServiceChosen()
  {
    return serviceChosen;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount)
  {
    boolean wasSet = false;
    if (aCustomerAccount == null)
    {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount))
    {
      existingCustomerAccount.removeAppointment(this);
    }
    customerAccount.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setServiceChosen(Service aNewServiceChosen)
  {
    boolean wasSet = false;
    if (aNewServiceChosen != null)
    {
      serviceChosen = aNewServiceChosen;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removeAppointment(this);
    }
    serviceChosen = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isCancelled" + ":" + getIsCancelled()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentDate" + "=" + (getAppointmentDate() != null ? !getAppointmentDate().equals(this)  ? getAppointmentDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentTime" + "=" + (getAppointmentTime() != null ? !getAppointmentTime().equals(this)  ? getAppointmentTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceChosen = "+(getServiceChosen()!=null?Integer.toHexString(System.identityHashCode(getServiceChosen())):"null");
  }
}