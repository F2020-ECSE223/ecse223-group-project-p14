/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;

// line 79 "../../../../../FlexiBook.ump"
public class ComboItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ComboItem Attributes
  private boolean mandatory;

  //ComboItem Associations
  private Service service;
  private ServiceCombo serviceCombo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ComboItem(boolean aMandatory, Service aService, ServiceCombo aServiceCombo)
  {
    mandatory = aMandatory;
    if (!setService(aService))
    {
      throw new RuntimeException("Unable to create ComboItem due to aService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddServiceCombo = setServiceCombo(aServiceCombo);
    if (!didAddServiceCombo)
    {
      throw new RuntimeException("Unable to create service due to serviceCombo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMandatory(boolean aMandatory)
  {
    boolean wasSet = false;
    mandatory = aMandatory;
    wasSet = true;
    return wasSet;
  }

  public boolean getMandatory()
  {
    return mandatory;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isMandatory()
  {
    return mandatory;
  }
  /* Code from template association_GetOne */
  public Service getService()
  {
    return service;
  }
  /* Code from template association_GetOne */
  public ServiceCombo getServiceCombo()
  {
    return serviceCombo;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setService(Service aNewService)
  {
    boolean wasSet = false;
    if (aNewService != null)
    {
      service = aNewService;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setServiceCombo(ServiceCombo aServiceCombo)
  {
    boolean wasSet = false;
    //Must provide serviceCombo to service
    if (aServiceCombo == null)
    {
      return wasSet;
    }

    if (serviceCombo != null && serviceCombo.numberOfServices() <= ServiceCombo.minimumNumberOfServices())
    {
      return wasSet;
    }

    ServiceCombo existingServiceCombo = serviceCombo;
    serviceCombo = aServiceCombo;
    if (existingServiceCombo != null && !existingServiceCombo.equals(aServiceCombo))
    {
      boolean didRemove = existingServiceCombo.removeService(this);
      if (!didRemove)
      {
        serviceCombo = existingServiceCombo;
        return wasSet;
      }
    }
    serviceCombo.addService(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    service = null;
    ServiceCombo placeholderServiceCombo = serviceCombo;
    this.serviceCombo = null;
    if(placeholderServiceCombo != null)
    {
      placeholderServiceCombo.removeService(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "mandatory" + ":" + getMandatory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceCombo = "+(getServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getServiceCombo())):"null");
  }
}