/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 28 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 122 "../../../../../Domain Model (Iteration 1) v1.0.ump"
public class OwnerAccount extends Account
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static OwnerAccount theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OwnerAccount Associations
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private OwnerAccount(boolean aIsActive, FlexiBookSystem aFlexiBookSystem)
  {
    super(aIsActive, aFlexiBookSystem);
    services = new ArrayList<Service>();
  }

  public static OwnerAccount getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new OwnerAccount(false, null);
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    OwnerAccount existingOwnerAccount = aService.getOwnerAccount();
    boolean isNewOwnerAccount = existingOwnerAccount != null && !this.equals(existingOwnerAccount);
    if (isNewOwnerAccount)
    {
      aService.setOwnerAccount(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a ownerAccount
    if (!this.equals(aService.getOwnerAccount()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
    super.delete();
  }

}