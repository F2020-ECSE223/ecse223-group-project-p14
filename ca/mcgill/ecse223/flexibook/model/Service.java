/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 88 "../../../../../Version 0.5 - CC, YW.ump"
// line 177 "../../../../../Version 0.5 - CC, YW.ump"
public abstract class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String serviceName;

  //Service Associations
  private Owner owner;
  private List<Appointment> appointments;
  private List<ServiceUpdate> updateRecords;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aServiceName, Owner aOwner)
  {
    serviceName = aServiceName;
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create service due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointments = new ArrayList<Appointment>();
    updateRecords = new ArrayList<ServiceUpdate>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
    wasSet = true;
    return wasSet;
  }

  public String getServiceName()
  {
    return serviceName;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
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
  /* Code from template association_GetMany */
  public ServiceUpdate getUpdateRecord(int index)
  {
    ServiceUpdate aUpdateRecord = updateRecords.get(index);
    return aUpdateRecord;
  }

  public List<ServiceUpdate> getUpdateRecords()
  {
    List<ServiceUpdate> newUpdateRecords = Collections.unmodifiableList(updateRecords);
    return newUpdateRecords;
  }

  public int numberOfUpdateRecords()
  {
    int number = updateRecords.size();
    return number;
  }

  public boolean hasUpdateRecords()
  {
    boolean has = updateRecords.size() > 0;
    return has;
  }

  public int indexOfUpdateRecord(ServiceUpdate aUpdateRecord)
  {
    int index = updateRecords.indexOf(aUpdateRecord);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOwner(Owner aOwner)
  {
    boolean wasSet = false;
    if (aOwner == null)
    {
      return wasSet;
    }

    Owner existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removeService(this);
    }
    owner.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Date aServiceStartDate, Time aServiceStartTime, boolean aIsCancelled, Customer aCustomer, Calendar aCalendar)
  {
    return new Appointment(aServiceStartDate, aServiceStartTime, aIsCancelled, aCustomer, aCalendar, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Service existingService = aAppointment.getService();
    boolean isNewService = existingService != null && !this.equals(existingService);
    if (isNewService)
    {
      aAppointment.setService(this);
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
    //Unable to remove aAppointment, as it must always have a service
    if (!this.equals(aAppointment.getService()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUpdateRecords()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceUpdate addUpdateRecord(int aPrice, int aTotalTime, int aDownTimeLength, int aDownTimeStartAt, boolean aIsCurrentUpdate)
  {
    return new ServiceUpdate(aPrice, aTotalTime, aDownTimeLength, aDownTimeStartAt, aIsCurrentUpdate, this);
  }

  public boolean addUpdateRecord(ServiceUpdate aUpdateRecord)
  {
    boolean wasAdded = false;
    if (updateRecords.contains(aUpdateRecord)) { return false; }
    Service existingService = aUpdateRecord.getService();
    boolean isNewService = existingService != null && !this.equals(existingService);
    if (isNewService)
    {
      aUpdateRecord.setService(this);
    }
    else
    {
      updateRecords.add(aUpdateRecord);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUpdateRecord(ServiceUpdate aUpdateRecord)
  {
    boolean wasRemoved = false;
    //Unable to remove aUpdateRecord, as it must always have a service
    if (!this.equals(aUpdateRecord.getService()))
    {
      updateRecords.remove(aUpdateRecord);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUpdateRecordAt(ServiceUpdate aUpdateRecord, int index)
  {  
    boolean wasAdded = false;
    if(addUpdateRecord(aUpdateRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUpdateRecords()) { index = numberOfUpdateRecords() - 1; }
      updateRecords.remove(aUpdateRecord);
      updateRecords.add(index, aUpdateRecord);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUpdateRecordAt(ServiceUpdate aUpdateRecord, int index)
  {
    boolean wasAdded = false;
    if(updateRecords.contains(aUpdateRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUpdateRecords()) { index = numberOfUpdateRecords() - 1; }
      updateRecords.remove(aUpdateRecord);
      updateRecords.add(index, aUpdateRecord);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUpdateRecordAt(aUpdateRecord, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Owner placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeService(this);
    }
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    for(int i=updateRecords.size(); i > 0; i--)
    {
      ServiceUpdate aUpdateRecord = updateRecords.get(i - 1);
      aUpdateRecord.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "serviceName" + ":" + getServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
  }
}