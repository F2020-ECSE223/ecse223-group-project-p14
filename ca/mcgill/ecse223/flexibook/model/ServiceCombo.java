/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 87 "../../../../../Domain Model v1.1.ump"
// line 172 "../../../../../Domain Model v1.1.ump"
public class ServiceCombo extends Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Associations
  private List<SingleService> otherServices;
  private SingleService mainService;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(String aServiceName, String aDescription, OwnerAccount aOwnerAccount, FlexiBookSystem aFlexiBookSystem, SingleService aMainService, SingleService... allOtherServices)
  {
    super(aServiceName, aDescription, aOwnerAccount, aFlexiBookSystem);
    otherServices = new ArrayList<SingleService>();
    boolean didAddOtherServices = setOtherServices(allOtherServices);
    if (!didAddOtherServices)
    {
      throw new RuntimeException("Unable to create ServiceCombo, must have at least 1 otherServices. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMainService = setMainService(aMainService);
    if (!didAddMainService)
    {
      throw new RuntimeException("Unable to create comboDetermined due to mainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  {
    boolean wasSet = false;
    if (aMainService == null)
    {
      return wasSet;
    }

    SingleService existingMainService = mainService;
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
    otherServices.clear();
    SingleService placeholderMainService = mainService;
    this.mainService = null;
    if(placeholderMainService != null)
    {
      placeholderMainService.removeComboDetermined(this);
    }
    super.delete();
  }

}