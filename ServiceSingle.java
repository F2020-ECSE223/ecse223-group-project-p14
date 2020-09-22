/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.util.*;

// line 91 "Version 0.5 - CC, YW.ump"
// line 183 "Version 0.5 - CC, YW.ump"
public class ServiceSingle extends Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceSingle Attributes
  private String type;

  //ServiceSingle Associations
  private List<ServiceCombo> comboAdded;
  private List<ServiceCombo> comboDetermined;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceSingle(String aServiceName, Owner aOwner, String aType)
  {
    super(aServiceName, aOwner);
    type = aType;
    comboAdded = new ArrayList<ServiceCombo>();
    comboDetermined = new ArrayList<ServiceCombo>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(String aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  /**
   * can be enum?
   */
  public String getType()
  {
    return type;
  }
  /* Code from template association_GetMany */
  public ServiceCombo getComboAdded(int index)
  {
    ServiceCombo aComboAdded = comboAdded.get(index);
    return aComboAdded;
  }

  public List<ServiceCombo> getComboAdded()
  {
    List<ServiceCombo> newComboAdded = Collections.unmodifiableList(comboAdded);
    return newComboAdded;
  }

  public int numberOfComboAdded()
  {
    int number = comboAdded.size();
    return number;
  }

  public boolean hasComboAdded()
  {
    boolean has = comboAdded.size() > 0;
    return has;
  }

  public int indexOfComboAdded(ServiceCombo aComboAdded)
  {
    int index = comboAdded.indexOf(aComboAdded);
    return index;
  }
  /* Code from template association_GetMany */
  public ServiceCombo getComboDetermined(int index)
  {
    ServiceCombo aComboDetermined = comboDetermined.get(index);
    return aComboDetermined;
  }

  public List<ServiceCombo> getComboDetermined()
  {
    List<ServiceCombo> newComboDetermined = Collections.unmodifiableList(comboDetermined);
    return newComboDetermined;
  }

  public int numberOfComboDetermined()
  {
    int number = comboDetermined.size();
    return number;
  }

  public boolean hasComboDetermined()
  {
    boolean has = comboDetermined.size() > 0;
    return has;
  }

  public int indexOfComboDetermined(ServiceCombo aComboDetermined)
  {
    int index = comboDetermined.indexOf(aComboDetermined);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboAdded()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addComboAdded(ServiceCombo aComboAdded)
  {
    boolean wasAdded = false;
    if (comboAdded.contains(aComboAdded)) { return false; }
    comboAdded.add(aComboAdded);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeComboAdded(ServiceCombo aComboAdded)
  {
    boolean wasRemoved = false;
    if (comboAdded.contains(aComboAdded))
    {
      comboAdded.remove(aComboAdded);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboAddedAt(ServiceCombo aComboAdded, int index)
  {  
    boolean wasAdded = false;
    if(addComboAdded(aComboAdded))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboAdded()) { index = numberOfComboAdded() - 1; }
      comboAdded.remove(aComboAdded);
      comboAdded.add(index, aComboAdded);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboAddedAt(ServiceCombo aComboAdded, int index)
  {
    boolean wasAdded = false;
    if(comboAdded.contains(aComboAdded))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboAdded()) { index = numberOfComboAdded() - 1; }
      comboAdded.remove(aComboAdded);
      comboAdded.add(index, aComboAdded);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboAddedAt(aComboAdded, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboDetermined()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceCombo addComboDetermined(String aServiceName, Owner aOwner, String aDescription)
  {
    return new ServiceCombo(aServiceName, aOwner, aDescription, this);
  }

  public boolean addComboDetermined(ServiceCombo aComboDetermined)
  {
    boolean wasAdded = false;
    if (comboDetermined.contains(aComboDetermined)) { return false; }
    ServiceSingle existingMainService = aComboDetermined.getMainService();
    boolean isNewMainService = existingMainService != null && !this.equals(existingMainService);
    if (isNewMainService)
    {
      aComboDetermined.setMainService(this);
    }
    else
    {
      comboDetermined.add(aComboDetermined);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeComboDetermined(ServiceCombo aComboDetermined)
  {
    boolean wasRemoved = false;
    //Unable to remove aComboDetermined, as it must always have a mainService
    if (!this.equals(aComboDetermined.getMainService()))
    {
      comboDetermined.remove(aComboDetermined);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboDeterminedAt(ServiceCombo aComboDetermined, int index)
  {  
    boolean wasAdded = false;
    if(addComboDetermined(aComboDetermined))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboDetermined()) { index = numberOfComboDetermined() - 1; }
      comboDetermined.remove(aComboDetermined);
      comboDetermined.add(index, aComboDetermined);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboDeterminedAt(ServiceCombo aComboDetermined, int index)
  {
    boolean wasAdded = false;
    if(comboDetermined.contains(aComboDetermined))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboDetermined()) { index = numberOfComboDetermined() - 1; }
      comboDetermined.remove(aComboDetermined);
      comboDetermined.add(index, aComboDetermined);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboDeterminedAt(aComboDetermined, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    comboAdded.clear();
    for(int i=comboDetermined.size(); i > 0; i--)
    {
      ServiceCombo aComboDetermined = comboDetermined.get(i - 1);
      aComboDetermined.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "type" + ":" + getType()+ "]";
  }
}