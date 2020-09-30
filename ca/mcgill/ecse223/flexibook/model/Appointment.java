/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Date;
import java.sql.Time;

// line 62 "../../../../../Domain Model v1.1.ump"
// line 151 "../../../../../Domain Model v1.1.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Date appointmentStartDate;
  private Time appointmentStartTime;
  private boolean isCancelled;

  //Appointment Associations
  private CustomerAccount customerAccount;
  private Service serviceChosen;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Date aAppointmentStartDate, Time aAppointmentStartTime, boolean aIsCancelled, CustomerAccount aCustomerAccount, Service aServiceChosen)
  {
    appointmentStartDate = aAppointmentStartDate;
    appointmentStartTime = aAppointmentStartTime;
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

  public boolean setAppointmentStartDate(Date aAppointmentStartDate)
  {
    boolean wasSet = false;
    appointmentStartDate = aAppointmentStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setAppointmentStartTime(Time aAppointmentStartTime)
  {
    boolean wasSet = false;
    appointmentStartTime = aAppointmentStartTime;
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

  public Date getAppointmentStartDate()
  {
    return appointmentStartDate;
  }

  public Time getAppointmentStartTime()
  {
    return appointmentStartTime;
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
            "  " + "appointmentStartDate" + "=" + (getAppointmentStartDate() != null ? !getAppointmentStartDate().equals(this)  ? getAppointmentStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentStartTime" + "=" + (getAppointmentStartTime() != null ? !getAppointmentStartTime().equals(this)  ? getAppointmentStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceChosen = "+(getServiceChosen()!=null?Integer.toHexString(System.identityHashCode(getServiceChosen())):"null");
  }
}