/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.util.*;

// line 71 "../../../../../FlexiBook.ump"
public class ServiceCombo extends BookableService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Associations
  private ComboItem mainService;
  private List<ComboItem> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(String aName, FlexiBook aFlexiBook)
  {
    super(aName, aFlexiBook);
    services = new ArrayList<ComboItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public ComboItem getMainService()
  {
    return mainService;
  }

  public boolean hasMainService()
  {
    boolean has = mainService != null;
    return has;
  }
  /* Code from template association_GetMany */
  public ComboItem getService(int index)
  {
    ComboItem aService = services.get(index);
    return aService;
  }

  /**
   * the services should be {ordered}; however, since Umple translates every * association
   * or composition into a list, it is possible to order items in that list given Umple's API
   */
  public List<ComboItem> getServices()
  {
    List<ComboItem> newServices = Collections.unmodifiableList(services);
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

  public int indexOfService(ComboItem aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setMainService(ComboItem aNewMainService)
  {
    boolean wasSet = false;
    mainService = aNewMainService;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfServicesValid()
  {
    boolean isValid = numberOfServices() >= minimumNumberOfServices();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 2;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public ComboItem addService(boolean aMandatory, Service aService)
  {
    ComboItem aNewService = new ComboItem(aMandatory, aService, this);
    return aNewService;
  }

  public boolean addService(ComboItem aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    ServiceCombo existingServiceCombo = aService.getServiceCombo();
    boolean isNewServiceCombo = existingServiceCombo != null && !this.equals(existingServiceCombo);

    if (isNewServiceCombo && existingServiceCombo.numberOfServices() <= minimumNumberOfServices())
    {
      return wasAdded;
    }
    if (isNewServiceCombo)
    {
      aService.setServiceCombo(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(ComboItem aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a serviceCombo
    if (this.equals(aService.getServiceCombo()))
    {
      return wasRemoved;
    }

    //serviceCombo already at minimum (2)
    if (numberOfServices() <= minimumNumberOfServices())
    {
      return wasRemoved;
    }

    services.remove(aService);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(ComboItem aService, int index)
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

  public boolean addOrMoveServiceAt(ComboItem aService, int index)
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
    mainService = null;
    while (services.size() > 0)
    {
      ComboItem aService = services.get(services.size() - 1);
      aService.delete();
      services.remove(aService);
    }
    
    super.delete();
  }

}