/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;

// line 17 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 116 "../../../../../Domain Model (Iteration 1) v1.0.ump"
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String name;
  private String password;
  private boolean isOwner;
  private boolean isActive;

  //Account Associations
  private FlexiBookSystem flexiBookSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(boolean aIsActive, FlexiBookSystem aFlexiBookSystem)
  {
    resetName();
    resetPassword();
    resetIsOwner();
    isActive = aIsActive;
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create account due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template attribute_SetDefaulted */
  public boolean setIsOwner(boolean aIsOwner)
  {
    boolean wasSet = false;
    isOwner = aIsOwner;
    wasSet = true;
    return wasSet;
  }

  public boolean resetIsOwner()
  {
    boolean wasReset = false;
    isOwner = getDefaultIsOwner();
    wasReset = true;
    return wasReset;
  }

  public boolean setIsActive(boolean aIsActive)
  {
    boolean wasSet = false;
    isActive = aIsActive;
    wasSet = true;
    return wasSet;
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

  public boolean getIsOwner()
  {
    return isOwner;
  }
  /* Code from template attribute_GetDefaulted */
  public boolean getDefaultIsOwner()
  {
    return true;
  }

  public boolean getIsActive()
  {
    return isActive;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsOwner()
  {
    return isOwner;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsActive()
  {
    return isActive;
  }
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFlexiBookSystem(FlexiBookSystem aFlexiBookSystem)
  {
    boolean wasSet = false;
    if (aFlexiBookSystem == null)
    {
      return wasSet;
    }

    FlexiBookSystem existingFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = aFlexiBookSystem;
    if (existingFlexiBookSystem != null && !existingFlexiBookSystem.equals(aFlexiBookSystem))
    {
      existingFlexiBookSystem.removeAccount(this);
    }
    flexiBookSystem.addAccount(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FlexiBookSystem placeholderFlexiBookSystem = flexiBookSystem;
    this.flexiBookSystem = null;
    if(placeholderFlexiBookSystem != null)
    {
      placeholderFlexiBookSystem.removeAccount(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "isOwner" + ":" + getIsOwner()+ "," +
            "isActive" + ":" + getIsActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null");
  }
}