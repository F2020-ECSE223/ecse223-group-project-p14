/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 99 "../../../../../Domain Model v1.1.ump"
// line 184 "../../../../../Domain Model v1.1.ump"
public class ServiceCombo extends Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Associations
  private SingleService mainService;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(String aServiceName, String aDescription, OwnerAccount aOwnerAccount, FlexiBookSystem aFlexiBookSystem, SingleService aMainService)
  {
    super(aServiceName, aDescription, aOwnerAccount, aFlexiBookSystem);
    boolean didAddMainService = setMainService(aMainService);
    if (!didAddMainService)
    {
      throw new RuntimeException("Unable to create comboDetermined due to mainService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public SingleService getMainService()
  {
    return mainService;
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
    SingleService placeholderMainService = mainService;
    this.mainService = null;
    if(placeholderMainService != null)
    {
      placeholderMainService.removeComboDetermined(this);
    }
    super.delete();
  }

}