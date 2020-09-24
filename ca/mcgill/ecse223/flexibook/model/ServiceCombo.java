/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 101 "../../../../../Version 0.5 - CC, YW.ump"
// line 192 "../../../../../Version 0.5 - CC, YW.ump"
public class ServiceCombo extends Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Attributes
  private String description;

  //ServiceCombo Associations
  private ServiceSingle mainService;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(String aServiceName, Owner aOwner, String aDescription, ServiceSingle aMainService)
  {
    super(aServiceName, aOwner);
    description = aDescription;
    boolean didAddMainService = setMainService(aMainService);
    if (!didAddMainService)
    {
      throw new RuntimeException("Unable to create comboDetermined due to mainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  {
    boolean wasSet = false;
    if (aMainService == null)
    {
      return wasSet;
    }

    ServiceSingle existingMainService = mainService;
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
    ServiceSingle placeholderMainService = mainService;
    this.mainService = null;
    if(placeholderMainService != null)
    {
      placeholderMainService.removeComboDetermined(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mainService = "+(getMainService()!=null?Integer.toHexString(System.identityHashCode(getMainService())):"null");
  }
}