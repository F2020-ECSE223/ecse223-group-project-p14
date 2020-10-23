/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;
import java.util.*;

// line 36 "../../../../../../model.ump"
// line 75 "../../../../../../model.ump"
public class TOServiceCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOServiceCombo Attributes
  private String name;

  //TOServiceCombo Associations
  private List<TOComboItem> services;
  private TOComboItem mainService;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOServiceCombo(String aName, TOComboItem... allServices)
  {
    name = aName;
    services = new ArrayList<TOComboItem>();
    boolean didAddServices = setServices(allServices);
    if (!didAddServices)
    {
      throw new RuntimeException("Unable to create TOServiceCombo, must have at least 2 services. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public TOComboItem getService(int index)
  {
    TOComboItem aService = services.get(index);
    return aService;
  }

  public List<TOComboItem> getServices()
  {
    List<TOComboItem> newServices = Collections.unmodifiableList(services);
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

  public int indexOfService(TOComboItem aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_GetOne */
  public TOComboItem getMainService()
  {
    return mainService;
  }

  public boolean hasMainService()
  {
    boolean has = mainService != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 2;
  }
  /* Code from template association_AddUnidirectionalMStar */
  public boolean addService(TOComboItem aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    services.add(aService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(TOComboItem aService)
  {
    boolean wasRemoved = false;
    if (!services.contains(aService))
    {
      return wasRemoved;
    }

    if (numberOfServices() <= minimumNumberOfServices())
    {
      return wasRemoved;
    }

    services.remove(aService);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMStar */
  public boolean setServices(TOComboItem... newServices)
  {
    boolean wasSet = false;
    ArrayList<TOComboItem> verifiedServices = new ArrayList<TOComboItem>();
    for (TOComboItem aService : newServices)
    {
      if (verifiedServices.contains(aService))
      {
        continue;
      }
      verifiedServices.add(aService);
    }

    if (verifiedServices.size() != newServices.length || verifiedServices.size() < minimumNumberOfServices())
    {
      return wasSet;
    }

    services.clear();
    services.addAll(verifiedServices);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(TOComboItem aService, int index)
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

  public boolean addOrMoveServiceAt(TOComboItem aService, int index)
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
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setMainService(TOComboItem aNewMainService)
  {
    boolean wasSet = false;
    mainService = aNewMainService;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    services.clear();
    mainService = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null");
  }
}