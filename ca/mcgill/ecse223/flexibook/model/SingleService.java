/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;

// line 83 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 159 "../../../../../Domain Model (Iteration 1) v1.0.ump"
public class SingleService extends Service
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ServiceCategory { Wash, HairExtension, Highlight, BlowDry, Cut, Color }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SingleService Attributes
  private ServiceCategory category;

  //SingleService Associations
  private List<ServiceCombo> combosAddedTo;
  private List<ServiceCombo> comboDetermined;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SingleService(String aServiceName, String aDescription, OwnerAccount aOwnerAccount, ServiceCategory aCategory)
  {
    super(aServiceName, aDescription, aOwnerAccount);
    category = aCategory;
    combosAddedTo = new ArrayList<ServiceCombo>();
    comboDetermined = new ArrayList<ServiceCombo>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCategory(ServiceCategory aCategory)
  {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public ServiceCategory getCategory()
  {
    return category;
  }
  /* Code from template association_GetMany */
  public ServiceCombo getCombosAddedTo(int index)
  {
    ServiceCombo aCombosAddedTo = combosAddedTo.get(index);
    return aCombosAddedTo;
  }

  public List<ServiceCombo> getCombosAddedTo()
  {
    List<ServiceCombo> newCombosAddedTo = Collections.unmodifiableList(combosAddedTo);
    return newCombosAddedTo;
  }

  public int numberOfCombosAddedTo()
  {
    int number = combosAddedTo.size();
    return number;
  }

  public boolean hasCombosAddedTo()
  {
    boolean has = combosAddedTo.size() > 0;
    return has;
  }

  public int indexOfCombosAddedTo(ServiceCombo aCombosAddedTo)
  {
    int index = combosAddedTo.indexOf(aCombosAddedTo);
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
  public static int minimumNumberOfCombosAddedTo()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCombosAddedTo(ServiceCombo aCombosAddedTo)
  {
    boolean wasAdded = false;
    if (combosAddedTo.contains(aCombosAddedTo)) { return false; }
    combosAddedTo.add(aCombosAddedTo);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCombosAddedTo(ServiceCombo aCombosAddedTo)
  {
    boolean wasRemoved = false;
    if (combosAddedTo.contains(aCombosAddedTo))
    {
      combosAddedTo.remove(aCombosAddedTo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCombosAddedToAt(ServiceCombo aCombosAddedTo, int index)
  {  
    boolean wasAdded = false;
    if(addCombosAddedTo(aCombosAddedTo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombosAddedTo()) { index = numberOfCombosAddedTo() - 1; }
      combosAddedTo.remove(aCombosAddedTo);
      combosAddedTo.add(index, aCombosAddedTo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCombosAddedToAt(ServiceCombo aCombosAddedTo, int index)
  {
    boolean wasAdded = false;
    if(combosAddedTo.contains(aCombosAddedTo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombosAddedTo()) { index = numberOfCombosAddedTo() - 1; }
      combosAddedTo.remove(aCombosAddedTo);
      combosAddedTo.add(index, aCombosAddedTo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCombosAddedToAt(aCombosAddedTo, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboDetermined()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceCombo addComboDetermined(String aServiceName, String aDescription, OwnerAccount aOwnerAccount)
  {
    return new ServiceCombo(aServiceName, aDescription, aOwnerAccount, this);
  }

  public boolean addComboDetermined(ServiceCombo aComboDetermined)
  {
    boolean wasAdded = false;
    if (comboDetermined.contains(aComboDetermined)) { return false; }
    SingleService existingMainService = aComboDetermined.getMainService();
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
    combosAddedTo.clear();
    for(int i=comboDetermined.size(); i > 0; i--)
    {
      ServiceCombo aComboDetermined = comboDetermined.get(i - 1);
      aComboDetermined.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this)  ? getCategory().toString().replaceAll("  ","    ") : "this" : "null");
  }
}