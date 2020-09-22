/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/



// line 103 "Version 0.5 - CC, YW.ump"
// line 197 "Version 0.5 - CC, YW.ump"
public class ServiceUpdate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceUpdate Attributes
  private int price;
  private int totalTime;
  private int downTimeLength;
  private int downTimeStartAt;
  private boolean isCurrentUpdate;

  //ServiceUpdate Associations
  private Service service;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceUpdate(int aPrice, int aTotalTime, int aDownTimeLength, int aDownTimeStartAt, boolean aIsCurrentUpdate, Service aService)
  {
    price = aPrice;
    totalTime = aTotalTime;
    downTimeLength = aDownTimeLength;
    downTimeStartAt = aDownTimeStartAt;
    isCurrentUpdate = aIsCurrentUpdate;
    boolean didAddService = setService(aService);
    if (!didAddService)
    {
      throw new RuntimeException("Unable to create updateRecord due to service. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setTotalTime(int aTotalTime)
  {
    boolean wasSet = false;
    totalTime = aTotalTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDownTimeLength(int aDownTimeLength)
  {
    boolean wasSet = false;
    downTimeLength = aDownTimeLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setDownTimeStartAt(int aDownTimeStartAt)
  {
    boolean wasSet = false;
    downTimeStartAt = aDownTimeStartAt;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsCurrentUpdate(boolean aIsCurrentUpdate)
  {
    boolean wasSet = false;
    isCurrentUpdate = aIsCurrentUpdate;
    wasSet = true;
    return wasSet;
  }

  public int getPrice()
  {
    return price;
  }

  public int getTotalTime()
  {
    return totalTime;
  }

  public int getDownTimeLength()
  {
    return downTimeLength;
  }

  public int getDownTimeStartAt()
  {
    return downTimeStartAt;
  }

  public boolean getIsCurrentUpdate()
  {
    return isCurrentUpdate;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsCurrentUpdate()
  {
    return isCurrentUpdate;
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
      existingService.removeUpdateRecord(this);
    }
    service.addUpdateRecord(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Service placeholderService = service;
    this.service = null;
    if(placeholderService != null)
    {
      placeholderService.removeUpdateRecord(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "," +
            "totalTime" + ":" + getTotalTime()+ "," +
            "downTimeLength" + ":" + getDownTimeLength()+ "," +
            "downTimeStartAt" + ":" + getDownTimeStartAt()+ "," +
            "isCurrentUpdate" + ":" + getIsCurrentUpdate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null");
  }
}