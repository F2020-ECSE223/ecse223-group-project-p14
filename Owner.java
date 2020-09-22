/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;

// line 33 "Version 0.5 - CC, YW.ump"
// line 142 "Version 0.5 - CC, YW.ump"
public class Owner extends Account
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Owner theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Attributes
  private String name;
  private String password;

  //Owner Associations
  private List<Service> services;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private Owner(String aName, String aPassword, boolean aIsOwner, FlexiBookSystem aFlexiBookSystem)
  {
    super(aName, aPassword, aIsOwner, aFlexiBookSystem);
    resetName();
    resetPassword();
    services = new ArrayList<Service>();
  }

  public static Owner getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new Owner();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean resetName()
  {
    boolean wasReset = false;
    name = getDefaultName();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean resetPassword()
  {
    boolean wasReset = false;
    password = getDefaultPassword();
    wasReset = true;
    return wasReset;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetDefaulted */
  public String getDefaultName()
  {
    return "Owner";
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template attribute_GetDefaulted */
  public String getDefaultPassword()
  {
    return "Owner";
  }
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
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

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    Owner existingOwner = aService.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aService.setOwner(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a owner
    if (!this.equals(aService.getOwner()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
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

  public boolean addOrMoveServiceAt(Service aService, int index)
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
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
    super.delete();
  }


  /**
   * PLEASE REMOVE THIS LATER!!!!!!!!!!!!!!!!!!!!!!!!!
   */
  // line 40 "Version 0.5 - CC, YW.ump"
   public int trial(){
    system.out.print("ddd");
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}