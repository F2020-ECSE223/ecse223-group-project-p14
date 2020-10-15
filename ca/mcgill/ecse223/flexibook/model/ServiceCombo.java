/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

<<<<<<< HEAD
// line 101 "../../../../../Version 0.5 - CC, YW.ump"
// line 192 "../../../../../Version 0.5 - CC, YW.ump"
=======
// line 87 "../../../../../Domain Model v1.1.ump"
// line 172 "../../../../../Domain Model v1.1.ump"
>>>>>>> master
public class ServiceCombo extends Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

<<<<<<< HEAD
  //ServiceCombo Attributes
  private String description;

  //ServiceCombo Associations
  private ServiceSingle mainService;
=======
  //ServiceCombo Associations
  private List<SingleService> otherServices;
  private SingleService mainService;
>>>>>>> master

  //------------------------
  // CONSTRUCTOR
  //------------------------

<<<<<<< HEAD
  public ServiceCombo(String aServiceName, Owner aOwner, String aDescription, ServiceSingle aMainService)
  {
    super(aServiceName, aOwner);
    description = aDescription;
=======
  public ServiceCombo(String aServiceName, String aDescription, OwnerAccount aOwnerAccount, FlexiBookSystem aFlexiBookSystem, SingleService aMainService, SingleService... allOtherServices)
  {
    super(aServiceName, aDescription, aOwnerAccount, aFlexiBookSystem);
    otherServices = new ArrayList<SingleService>();
    boolean didAddOtherServices = setOtherServices(allOtherServices);
    if (!didAddOtherServices)
    {
      throw new RuntimeException("Unable to create ServiceCombo, must have at least 1 otherServices. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
>>>>>>> master
    boolean didAddMainService = setMainService(aMainService);
    if (!didAddMainService)
    {
      throw new RuntimeException("Unable to create comboDetermined due to mainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
<<<<<<< HEAD

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public ServiceSingle getMainService()
  {
    return mainService;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMainService(ServiceSingle aMainService)
=======
  /* Code from template association_GetMany */
  public SingleService getOtherService(int index)
  {
    SingleService aOtherService = otherServices.get(index);
    return aOtherService;
  }

  public List<SingleService> getOtherServices()
  {
    List<SingleService> newOtherServices = Collections.unmodifiableList(otherServices);
    return newOtherServices;
  }

  public int numberOfOtherServices()
  {
    int number = otherServices.size();
    return number;
  }

  public boolean hasOtherServices()
  {
    boolean has = otherServices.size() > 0;
    return has;
  }

  public int indexOfOtherService(SingleService aOtherService)
  {
    int index = otherServices.indexOf(aOtherService);
    return index;
  }
  /* Code from template association_GetOne */
  public SingleService getMainService()
  {
    return mainService;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOtherServices()
  {
    return 1;
  }
  /* Code from template association_AddUnidirectionalMStar */
  public boolean addOtherService(SingleService aOtherService)
  {
    boolean wasAdded = false;
    if (otherServices.contains(aOtherService)) { return false; }
    otherServices.add(aOtherService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOtherService(SingleService aOtherService)
  {
    boolean wasRemoved = false;
    if (!otherServices.contains(aOtherService))
    {
      return wasRemoved;
    }

    if (numberOfOtherServices() <= minimumNumberOfOtherServices())
    {
      return wasRemoved;
    }

    otherServices.remove(aOtherService);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMStar */
  public boolean setOtherServices(SingleService... newOtherServices)
  {
    boolean wasSet = false;
    ArrayList<SingleService> verifiedOtherServices = new ArrayList<SingleService>();
    for (SingleService aOtherService : newOtherServices)
    {
      if (verifiedOtherServices.contains(aOtherService))
      {
        continue;
      }
      verifiedOtherServices.add(aOtherService);
    }

    if (verifiedOtherServices.size() != newOtherServices.length || verifiedOtherServices.size() < minimumNumberOfOtherServices())
    {
      return wasSet;
    }

    otherServices.clear();
    otherServices.addAll(verifiedOtherServices);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOtherServiceAt(SingleService aOtherService, int index)
  {  
    boolean wasAdded = false;
    if(addOtherService(aOtherService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOtherServices()) { index = numberOfOtherServices() - 1; }
      otherServices.remove(aOtherService);
      otherServices.add(index, aOtherService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOtherServiceAt(SingleService aOtherService, int index)
  {
    boolean wasAdded = false;
    if(otherServices.contains(aOtherService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOtherServices()) { index = numberOfOtherServices() - 1; }
      otherServices.remove(aOtherService);
      otherServices.add(index, aOtherService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOtherServiceAt(aOtherService, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMainService(SingleService aMainService)
>>>>>>> master
  {
    boolean wasSet = false;
    if (aMainService == null)
    {
      return wasSet;
    }

<<<<<<< HEAD
    ServiceSingle existingMainService = mainService;
=======
    SingleService existingMainService = mainService;
>>>>>>> master
    mainService = aMainService;
    if (existingMainService != null && !existingMainService.equals(aMainService))
    {
      existingMainService.removeComboDetermined(this);
    }
    mainService.addComboDetermined(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
<<<<<<< HEAD
    ServiceSingle placeholderMainService = mainService;
=======
    otherServices.clear();
    SingleService placeholderMainService = mainService;
>>>>>>> master
    this.mainService = null;
    if(placeholderMainService != null)
    {
      placeholderMainService.removeComboDetermined(this);
    }
    super.delete();
  }

<<<<<<< HEAD

  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null");
  }
=======
>>>>>>> master
}