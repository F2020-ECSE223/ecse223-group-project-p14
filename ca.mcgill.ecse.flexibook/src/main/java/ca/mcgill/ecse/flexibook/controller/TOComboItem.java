/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 21 "../../../../../FlexiBookTransferObjects.ump"
public class TOComboItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOComboItem Attributes
  private boolean isMandatory;
  private String serviceName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOComboItem(boolean aIsMandatory, String aServiceName)
  {
    isMandatory = aIsMandatory;
    serviceName = aServiceName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMandatory(boolean aIsMandatory)
  {
    boolean wasSet = false;
    isMandatory = aIsMandatory;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceName(String aServiceName)
  {
    boolean wasSet = false;
    serviceName = aServiceName;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsMandatory()
  {
    return isMandatory;
  }

  public String getServiceName()
  {
    return serviceName;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsMandatory()
  {
    return isMandatory;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "isMandatory" + ":" + getIsMandatory()+ "," +
            "serviceName" + ":" + getServiceName()+ "]";
  }
}