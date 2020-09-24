/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 76 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 153 "../../../../../Domain Model (Iteration 1) v1.0.ump"
public abstract class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private String serviceName;
  private String description;

  //Service Associations
  private OwnerAccount ownerAccount;
  private List<ServiceDetail> pastDetails;
  private ServiceDetail currentDetail;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aServiceName, String aDescription, OwnerAccount aOwnerAccount)
  {
    serviceName = aServiceName;
    description = aDescription;
    boolean didAddOwnerAccount = setOwnerAccount(aOwnerAccount);
    if (!didAddOwnerAccount)
    {
      throw new RuntimeException("Unable to create service due to ownerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    pastDetails = new ArrayList<ServiceDetail>();
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

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public OwnerAccount getOwnerAccount()
  {
    return ownerAccount;
  }
  /* Code from template association_GetMany */
  public ServiceDetail getPastDetail(int index)
  {
    ServiceDetail aPastDetail = pastDetails.get(index);
    return aPastDetail;
  }

  public List<ServiceDetail> getPastDetails()
  {
    List<ServiceDetail> newPastDetails = Collections.unmodifiableList(pastDetails);
    return newPastDetails;
  }

  public int numberOfPastDetails()
  {
    int number = pastDetails.size();
    return number;
  }

  public boolean hasPastDetails()
  {
    boolean has = pastDetails.size() > 0;
    return has;
  }

  public int indexOfPastDetail(ServiceDetail aPastDetail)
  {
    int index = pastDetails.indexOf(aPastDetail);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPastDetails()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceDetail addPastDetail(int aPrice, int aTimeLength)
  {
    return new ServiceDetail(aPrice, aTimeLength, this);
  }

  public boolean addPastDetail(ServiceDetail aPastDetail)
  {
    boolean wasAdded = false;
    if (pastDetails.contains(aPastDetail)) { return false; }
    Service existingService = aPastDetail.getService();
    boolean isNewService = existingService != null && !this.equals(existingService);
    if (isNewService)
    {
      aPastDetail.setService(this);
    }
    else
    {
      pastDetails.add(aPastDetail);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePastDetail(ServiceDetail aPastDetail)
  {
    boolean wasRemoved = false;
    //Unable to remove aPastDetail, as it must always have a service
    if (!this.equals(aPastDetail.getService()))
    {
      pastDetails.remove(aPastDetail);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPastDetailAt(ServiceDetail aPastDetail, int index)
  {  
    boolean wasAdded = false;
    if(addPastDetail(aPastDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPastDetails()) { index = numberOfPastDetails() - 1; }
      pastDetails.remove(aPastDetail);
      pastDetails.add(index, aPastDetail);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePastDetailAt(ServiceDetail aPastDetail, int index)
  {
    boolean wasAdded = false;
    if(pastDetails.contains(aPastDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPastDetails()) { index = numberOfPastDetails() - 1; }
      pastDetails.remove(aPastDetail);
      pastDetails.add(index, aPastDetail);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPastDetailAt(aPastDetail, index);
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
    OwnerAccount placeholderOwnerAccount = ownerAccount;
    this.ownerAccount = null;
    if(placeholderOwnerAccount != null)
    {
      placeholderOwnerAccount.removeService(this);
    }
    for(int i=pastDetails.size(); i > 0; i--)
    {
      ServiceDetail aPastDetail = pastDetails.get(i - 1);
      aPastDetail.delete();
    }
    currentDetail = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "serviceName" + ":" + getServiceName()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ownerAccount = "+(getOwnerAccount()!=null?Integer.toHexString(System.identityHashCode(getOwnerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDetail = "+(getCurrentDetail()!=null?Integer.toHexString(System.identityHashCode(getCurrentDetail())):"null");
  }
}