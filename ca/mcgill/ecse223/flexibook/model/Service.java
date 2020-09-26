/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 84 "../../../../../Domain Model v1.1.ump"
// line 171 "../../../../../Domain Model v1.1.ump"
public abstract class Service
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Service> servicesByServiceName = new HashMap<String, Service>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String serviceName;
  private String description;

  //Service Associations
  private OwnerAccount ownerAccount;
  private FlexiBookSystem flexiBookSystem;
  private List<ServiceDetail> oldDetails;
  private ServiceDetail currentDetail;

  //------------------------
  // CONSTRUCTOR
  //------------------------

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
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
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
    return wasSet;
  }

  public String getServiceName()
  {
    return serviceName;
  }
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
    {
      return wasSet;
    }

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
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
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
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOldDetail(ServiceDetail aOldDetail)
  {
    boolean wasRemoved = false;
    //Unable to remove aOldDetail, as it must always have a service
    if (!this.equals(aOldDetail.getService()))
    {
      oldDetails.remove(aOldDetail);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOldDetailAt(ServiceDetail aOldDetail, int index)
  {  
    boolean wasAdded = false;
    if(addOldDetail(aOldDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOldDetails()) { index = numberOfOldDetails() - 1; }
      oldDetails.remove(aOldDetail);
      oldDetails.add(index, aOldDetail);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOldDetailAt(ServiceDetail aOldDetail, int index)
  {
    boolean wasAdded = false;
    if(oldDetails.contains(aOldDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOldDetails()) { index = numberOfOldDetails() - 1; }
      oldDetails.remove(aOldDetail);
      oldDetails.add(index, aOldDetail);
      wasAdded = true;
    } 
    else 
    {
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
  }


  public String toString()
  {
    return super.toString() + "["+
            "serviceName" + ":" + getServiceName()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ownerAccount = "+(getOwnerAccount()!=null?Integer.toHexString(System.identityHashCode(getOwnerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDetail = "+(getCurrentDetail()!=null?Integer.toHexString(System.identityHashCode(getCurrentDetail())):"null");
  }
}