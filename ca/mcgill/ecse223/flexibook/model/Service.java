/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;
<<<<<<< HEAD
import java.sql.Date;
import java.sql.Time;

// line 88 "../../../../../Version 0.5 - CC, YW.ump"
// line 177 "../../../../../Version 0.5 - CC, YW.ump"
=======

// line 73 "../../../../../Domain Model v1.1.ump"
// line 159 "../../../../../Domain Model v1.1.ump"
>>>>>>> master
public abstract class Service
{

  //------------------------
<<<<<<< HEAD
=======
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Service> servicesByServiceName = new HashMap<String, Service>();

  //------------------------
>>>>>>> master
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String serviceName;
<<<<<<< HEAD

  //Service Associations
  private Owner owner;
  private List<Appointment> appointments;
  private List<ServiceUpdate> updateRecords;
=======
  private String description;

  //Service Associations
  private OwnerAccount ownerAccount;
  private FlexiBookSystem flexiBookSystem;
  private List<ServiceDetail> oldDetails;
  private ServiceDetail currentDetail;
>>>>>>> master

  //------------------------
  // CONSTRUCTOR
  //------------------------

<<<<<<< HEAD
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
=======
  public Service(String aServiceName, String aDescription, OwnerAccount aOwnerAccount, FlexiBookSystem aFlexiBookSystem)
  {
    description = aDescription;
    if (!setServiceName(aServiceName))
    {
      throw new RuntimeException("Cannot create due to duplicate serviceName. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddOwnerAccount = setOwnerAccount(aOwnerAccount);
    if (!didAddOwnerAccount)
    {
      throw new RuntimeException("Unable to create service due to ownerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create service due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    oldDetails = new ArrayList<ServiceDetail>();
>>>>>>> master
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
<<<<<<< HEAD
    serviceName = aServiceName;
    wasSet = true;
=======
    String anOldServiceName = getServiceName();
    if (anOldServiceName != null && anOldServiceName.equals(aServiceName)) {
      return true;
    }
    if (hasWithServiceName(aServiceName)) {
      return wasSet;
    }
    serviceName = aServiceName;
    wasSet = true;
    if (anOldServiceName != null) {
      servicesByServiceName.remove(anOldServiceName);
    }
    servicesByServiceName.put(aServiceName, this);
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
>>>>>>> master
    return wasSet;
  }

  public String getServiceName()
  {
    return serviceName;
  }
<<<<<<< HEAD
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
=======
  /* Code from template attribute_GetUnique */
  public static Service getWithServiceName(String aServiceName)
  {
    return servicesByServiceName.get(aServiceName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithServiceName(String aServiceName)
  {
    return getWithServiceName(aServiceName) != null;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public OwnerAccount getOwnerAccount()
  {
    return ownerAccount;
  }
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
  /* Code from template association_GetMany */
  public ServiceDetail getOldDetail(int index)
  {
    ServiceDetail aOldDetail = oldDetails.get(index);
    return aOldDetail;
  }

  public List<ServiceDetail> getOldDetails()
  {
    List<ServiceDetail> newOldDetails = Collections.unmodifiableList(oldDetails);
    return newOldDetails;
  }

  public int numberOfOldDetails()
  {
    int number = oldDetails.size();
    return number;
  }

  public boolean hasOldDetails()
  {
    boolean has = oldDetails.size() > 0;
    return has;
  }

  public int indexOfOldDetail(ServiceDetail aOldDetail)
  {
    int index = oldDetails.indexOf(aOldDetail);
    return index;
  }
  /* Code from template association_GetOne */
  public ServiceDetail getCurrentDetail()
  {
    return currentDetail;
  }

  public boolean hasCurrentDetail()
  {
    boolean has = currentDetail != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOwnerAccount(OwnerAccount aOwnerAccount)
  {
    boolean wasSet = false;
    if (aOwnerAccount == null)
>>>>>>> master
    {
      return wasSet;
    }

<<<<<<< HEAD
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
=======
    OwnerAccount existingOwnerAccount = ownerAccount;
    ownerAccount = aOwnerAccount;
    if (existingOwnerAccount != null && !existingOwnerAccount.equals(aOwnerAccount))
    {
      existingOwnerAccount.removeService(this);
    }
    ownerAccount.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFlexiBookSystem(FlexiBookSystem aFlexiBookSystem)
  {
    boolean wasSet = false;
    if (aFlexiBookSystem == null)
    {
      return wasSet;
    }

    FlexiBookSystem existingFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = aFlexiBookSystem;
    if (existingFlexiBookSystem != null && !existingFlexiBookSystem.equals(aFlexiBookSystem))
    {
      existingFlexiBookSystem.removeService(this);
    }
    flexiBookSystem.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOldDetails()
>>>>>>> master
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
<<<<<<< HEAD
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
=======
  public ServiceDetail addOldDetail(int aPrice, int aTimeLength)
  {
    return new ServiceDetail(aPrice, aTimeLength, this);
  }

  public boolean addOldDetail(ServiceDetail aOldDetail)
  {
    boolean wasAdded = false;
    if (oldDetails.contains(aOldDetail)) { return false; }
    Service existingService = aOldDetail.getService();
    boolean isNewService = existingService != null && !this.equals(existingService);
    if (isNewService)
    {
      aOldDetail.setService(this);
    }
    else
    {
      oldDetails.add(aOldDetail);
>>>>>>> master
    }
    wasAdded = true;
    return wasAdded;
  }

<<<<<<< HEAD
  public boolean removeUpdateRecord(ServiceUpdate aUpdateRecord)
  {
    boolean wasRemoved = false;
    //Unable to remove aUpdateRecord, as it must always have a service
    if (!this.equals(aUpdateRecord.getService()))
    {
      updateRecords.remove(aUpdateRecord);
=======
  public boolean removeOldDetail(ServiceDetail aOldDetail)
  {
    boolean wasRemoved = false;
    //Unable to remove aOldDetail, as it must always have a service
    if (!this.equals(aOldDetail.getService()))
    {
      oldDetails.remove(aOldDetail);
>>>>>>> master
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
<<<<<<< HEAD
  public boolean addUpdateRecordAt(ServiceUpdate aUpdateRecord, int index)
  {  
    boolean wasAdded = false;
    if(addUpdateRecord(aUpdateRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUpdateRecords()) { index = numberOfUpdateRecords() - 1; }
      updateRecords.remove(aUpdateRecord);
      updateRecords.add(index, aUpdateRecord);
=======
  public boolean addOldDetailAt(ServiceDetail aOldDetail, int index)
  {  
    boolean wasAdded = false;
    if(addOldDetail(aOldDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOldDetails()) { index = numberOfOldDetails() - 1; }
      oldDetails.remove(aOldDetail);
      oldDetails.add(index, aOldDetail);
>>>>>>> master
      wasAdded = true;
    }
    return wasAdded;
  }

<<<<<<< HEAD
  public boolean addOrMoveUpdateRecordAt(ServiceUpdate aUpdateRecord, int index)
  {
    boolean wasAdded = false;
    if(updateRecords.contains(aUpdateRecord))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUpdateRecords()) { index = numberOfUpdateRecords() - 1; }
      updateRecords.remove(aUpdateRecord);
      updateRecords.add(index, aUpdateRecord);
=======
  public boolean addOrMoveOldDetailAt(ServiceDetail aOldDetail, int index)
  {
    boolean wasAdded = false;
    if(oldDetails.contains(aOldDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOldDetails()) { index = numberOfOldDetails() - 1; }
      oldDetails.remove(aOldDetail);
      oldDetails.add(index, aOldDetail);
>>>>>>> master
      wasAdded = true;
    } 
    else 
    {
<<<<<<< HEAD
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
=======
      wasAdded = addOldDetailAt(aOldDetail, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentDetail(ServiceDetail aNewCurrentDetail)
  {
    boolean wasSet = false;
    currentDetail = aNewCurrentDetail;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    servicesByServiceName.remove(getServiceName());
    OwnerAccount placeholderOwnerAccount = ownerAccount;
    this.ownerAccount = null;
    if(placeholderOwnerAccount != null)
    {
      placeholderOwnerAccount.removeService(this);
    }
    FlexiBookSystem placeholderFlexiBookSystem = flexiBookSystem;
    this.flexiBookSystem = null;
    if(placeholderFlexiBookSystem != null)
    {
      placeholderFlexiBookSystem.removeService(this);
    }
    for(int i=oldDetails.size(); i > 0; i--)
    {
      ServiceDetail aOldDetail = oldDetails.get(i - 1);
      aOldDetail.delete();
    }
    currentDetail = null;
>>>>>>> master
  }


  public String toString()
  {
    return super.toString() + "["+
<<<<<<< HEAD
            "serviceName" + ":" + getServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
=======
            "serviceName" + ":" + getServiceName()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ownerAccount = "+(getOwnerAccount()!=null?Integer.toHexString(System.identityHashCode(getOwnerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDetail = "+(getCurrentDetail()!=null?Integer.toHexString(System.identityHashCode(getCurrentDetail())):"null");
>>>>>>> master
  }
}