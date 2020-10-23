/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;
import java.util.*;

// line 10 "../../../../../FlexiBookTransferObjects.ump"
public class TOAppointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointment Attributes
  private String customerName;
  private String serviceName;

  //TOAppointment Associations
  private TOTimeSlot timeSlot;
  private List<TOComboItem> chosenItems;
  private List<TOTimeSlot> downTimeTimeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointment(String aCustomerName, String aServiceName, TOTimeSlot aTimeSlot)
  {
    customerName = aCustomerName;
    serviceName = aServiceName;
    if (!setTimeSlot(aTimeSlot))
    {
      throw new RuntimeException("Unable to create TOAppointment due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    chosenItems = new ArrayList<TOComboItem>();
    downTimeTimeSlot = new ArrayList<TOTimeSlot>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerName(String aCustomerName)
  {
    boolean wasSet = false;
    customerName = aCustomerName;
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

  public String getCustomerName()
  {
    return customerName;
  }

  public String getServiceName()
  {
    return serviceName;
  }
  /* Code from template association_GetOne */
  public TOTimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_GetMany */
  public TOComboItem getChosenItem(int index)
  {
    TOComboItem aChosenItem = chosenItems.get(index);
    return aChosenItem;
  }

  public List<TOComboItem> getChosenItems()
  {
    List<TOComboItem> newChosenItems = Collections.unmodifiableList(chosenItems);
    return newChosenItems;
  }

  public int numberOfChosenItems()
  {
    int number = chosenItems.size();
    return number;
  }

  public boolean hasChosenItems()
  {
    boolean has = chosenItems.size() > 0;
    return has;
  }

  public int indexOfChosenItem(TOComboItem aChosenItem)
  {
    int index = chosenItems.indexOf(aChosenItem);
    return index;
  }
  /* Code from template association_GetMany */
  public TOTimeSlot getDownTimeTimeSlot(int index)
  {
    TOTimeSlot aDownTimeTimeSlot = downTimeTimeSlot.get(index);
    return aDownTimeTimeSlot;
  }

  public List<TOTimeSlot> getDownTimeTimeSlot()
  {
    List<TOTimeSlot> newDownTimeTimeSlot = Collections.unmodifiableList(downTimeTimeSlot);
    return newDownTimeTimeSlot;
  }

  public int numberOfDownTimeTimeSlot()
  {
    int number = downTimeTimeSlot.size();
    return number;
  }

  public boolean hasDownTimeTimeSlot()
  {
    boolean has = downTimeTimeSlot.size() > 0;
    return has;
  }

  public int indexOfDownTimeTimeSlot(TOTimeSlot aDownTimeTimeSlot)
  {
    int index = downTimeTimeSlot.indexOf(aDownTimeTimeSlot);
    return index;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeSlot(TOTimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot != null)
    {
      timeSlot = aNewTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChosenItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addChosenItem(TOComboItem aChosenItem)
  {
    boolean wasAdded = false;
    if (chosenItems.contains(aChosenItem)) { return false; }
    chosenItems.add(aChosenItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChosenItem(TOComboItem aChosenItem)
  {
    boolean wasRemoved = false;
    if (chosenItems.contains(aChosenItem))
    {
      chosenItems.remove(aChosenItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChosenItemAt(TOComboItem aChosenItem, int index)
  {  
    boolean wasAdded = false;
    if(addChosenItem(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChosenItemAt(TOComboItem aChosenItem, int index)
  {
    boolean wasAdded = false;
    if(chosenItems.contains(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChosenItemAt(aChosenItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDownTimeTimeSlot()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addDownTimeTimeSlot(TOTimeSlot aDownTimeTimeSlot)
  {
    boolean wasAdded = false;
    if (downTimeTimeSlot.contains(aDownTimeTimeSlot)) { return false; }
    downTimeTimeSlot.add(aDownTimeTimeSlot);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDownTimeTimeSlot(TOTimeSlot aDownTimeTimeSlot)
  {
    boolean wasRemoved = false;
    if (downTimeTimeSlot.contains(aDownTimeTimeSlot))
    {
      downTimeTimeSlot.remove(aDownTimeTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDownTimeTimeSlotAt(TOTimeSlot aDownTimeTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addDownTimeTimeSlot(aDownTimeTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDownTimeTimeSlot()) { index = numberOfDownTimeTimeSlot() - 1; }
      downTimeTimeSlot.remove(aDownTimeTimeSlot);
      downTimeTimeSlot.add(index, aDownTimeTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDownTimeTimeSlotAt(TOTimeSlot aDownTimeTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(downTimeTimeSlot.contains(aDownTimeTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDownTimeTimeSlot()) { index = numberOfDownTimeTimeSlot() - 1; }
      downTimeTimeSlot.remove(aDownTimeTimeSlot);
      downTimeTimeSlot.add(index, aDownTimeTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDownTimeTimeSlotAt(aDownTimeTimeSlot, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    timeSlot = null;
    chosenItems.clear();
    downTimeTimeSlot.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "customerName" + ":" + getCustomerName()+ "," +
            "serviceName" + ":" + getServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}