/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;

// line 103 "../../../../../Domain Model v1.1.ump"
// line 189 "../../../../../Domain Model v1.1.ump"
public class ServiceDetail
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceDetail Attributes
  private int price;
  private int timeLength;
  private boolean hasDowntime;
  private int downTimeEndAt;
  private int downTimeStartAt;

  //ServiceDetail Associations
  private Service service;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceDetail(int aPrice, int aTimeLength, Service aService)
  {
    price = aPrice;
    timeLength = aTimeLength;
    resetHasDowntime();
    resetDownTimeEndAt();
    resetDownTimeStartAt();
    boolean didAddService = setService(aService);
    if (!didAddService)
    {
      throw new RuntimeException("Unable to create oldDetail due to service. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeLength(int aTimeLength)
  {
    boolean wasSet = false;
    timeLength = aTimeLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setHasDowntime(boolean aHasDowntime)
  {
    boolean wasSet = false;
    hasDowntime = aHasDowntime;
    wasSet = true;
    return wasSet;
  }

  public boolean resetHasDowntime()
  {
    boolean wasReset = false;
    hasDowntime = getDefaultHasDowntime();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setDownTimeEndAt(int aDownTimeEndAt)
  {
    boolean wasSet = false;
    downTimeEndAt = aDownTimeEndAt;
    wasSet = true;
    return wasSet;
  }

  public boolean resetDownTimeEndAt()
  {
    boolean wasReset = false;
    downTimeEndAt = getDefaultDownTimeEndAt();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setDownTimeStartAt(int aDownTimeStartAt)
  {
    boolean wasSet = false;
    downTimeStartAt = aDownTimeStartAt;
    wasSet = true;
    return wasSet;
  }

  public boolean resetDownTimeStartAt()
  {
    boolean wasReset = false;
    downTimeStartAt = getDefaultDownTimeStartAt();
    wasReset = true;
    return wasReset;
  }

  public int getPrice()
  {
    return price;
  }

  public int getTimeLength()
  {
    return timeLength;
  }

  public boolean getHasDowntime()
  {
    return hasDowntime;
  }
  /* Code from template attribute_GetDefaulted */
  public boolean getDefaultHasDowntime()
  {
    return false;
  }

  public int getDownTimeEndAt()
  {
    return downTimeEndAt;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultDownTimeEndAt()
  {
    return 0;
  }

  public int getDownTimeStartAt()
  {
    return downTimeStartAt;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultDownTimeStartAt()
  {
    return 0;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHasDowntime()
  {
    return hasDowntime;
  }
  /* Code from template association_GetOne */
  public Service getService()
  {
    return service;
  }
  /* Code from template association_SetOneToMany */
  public boolean setService(Service aService)
  {
    boolean wasSet = false;
    if (aService == null)
    {
      return wasSet;
    }

    Service existingService = service;
    service = aService;
    if (existingService != null && !existingService.equals(aService))
    {
      existingService.removeOldDetail(this);
    }
    service.addOldDetail(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Service placeholderService = service;
    this.service = null;
    if(placeholderService != null)
    {
      placeholderService.removeOldDetail(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "," +
            "timeLength" + ":" + getTimeLength()+ "," +
            "hasDowntime" + ":" + getHasDowntime()+ "," +
            "downTimeEndAt" + ":" + getDownTimeEndAt()+ "," +
            "downTimeStartAt" + ":" + getDownTimeStartAt()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null");
  }
}