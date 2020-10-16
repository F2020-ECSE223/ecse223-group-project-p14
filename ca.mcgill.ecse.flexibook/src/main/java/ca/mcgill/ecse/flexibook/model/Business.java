/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.util.*;

// line 27 "../../../../../FlexiBook.ump"
public class Business
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Attributes
  private String name;
  private String address;
  private String phoneNumber;
  private String email;

  //Business Associations
  private List<BusinessHour> businessHours;
  private List<TimeSlot> holidays;
  private List<TimeSlot> vacation;
  private FlexiBook flexiBook;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aName, String aAddress, String aPhoneNumber, String aEmail, FlexiBook aFlexiBook)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    businessHours = new ArrayList<BusinessHour>();
    holidays = new ArrayList<TimeSlot>();
    vacation = new ArrayList<TimeSlot>();
    boolean didAddFlexiBook = setFlexiBook(aFlexiBook);
    if (!didAddFlexiBook)
    {
      throw new RuntimeException("Unable to create business due to flexiBook. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetMany */
  public BusinessHour getBusinessHour(int index)
  {
    BusinessHour aBusinessHour = businessHours.get(index);
    return aBusinessHour;
  }

  /**
   * since there is only one business in this system, the first
   * 0..1 should be a 1. since this is a directed association, the
   * first multiplicity is not taken into account. hence, it is
   * changed to 0..1 to avoid Umple issuing warning W036
   * Unmanaged Multiplicity
   */
  public List<BusinessHour> getBusinessHours()
  {
    List<BusinessHour> newBusinessHours = Collections.unmodifiableList(businessHours);
    return newBusinessHours;
  }

  public int numberOfBusinessHours()
  {
    int number = businessHours.size();
    return number;
  }

  public boolean hasBusinessHours()
  {
    boolean has = businessHours.size() > 0;
    return has;
  }

  public int indexOfBusinessHour(BusinessHour aBusinessHour)
  {
    int index = businessHours.indexOf(aBusinessHour);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getHoliday(int index)
  {
    TimeSlot aHoliday = holidays.get(index);
    return aHoliday;
  }

  public List<TimeSlot> getHolidays()
  {
    List<TimeSlot> newHolidays = Collections.unmodifiableList(holidays);
    return newHolidays;
  }

  public int numberOfHolidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasHolidays()
  {
    boolean has = holidays.size() > 0;
    return has;
  }

  public int indexOfHoliday(TimeSlot aHoliday)
  {
    int index = holidays.indexOf(aHoliday);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getVacation(int index)
  {
    TimeSlot aVacation = vacation.get(index);
    return aVacation;
  }

  public List<TimeSlot> getVacation()
  {
    List<TimeSlot> newVacation = Collections.unmodifiableList(vacation);
    return newVacation;
  }

  public int numberOfVacation()
  {
    int number = vacation.size();
    return number;
  }

  public boolean hasVacation()
  {
    boolean has = vacation.size() > 0;
    return has;
  }

  public int indexOfVacation(TimeSlot aVacation)
  {
    int index = vacation.indexOf(aVacation);
    return index;
  }
  /* Code from template association_GetOne */
  public FlexiBook getFlexiBook()
  {
    return flexiBook;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusinessHours()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addBusinessHour(BusinessHour aBusinessHour)
  {
    boolean wasAdded = false;
    if (businessHours.contains(aBusinessHour)) { return false; }
    businessHours.add(aBusinessHour);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusinessHour(BusinessHour aBusinessHour)
  {
    boolean wasRemoved = false;
    if (businessHours.contains(aBusinessHour))
    {
      businessHours.remove(aBusinessHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessHourAt(BusinessHour aBusinessHour, int index)
  {  
    boolean wasAdded = false;
    if(addBusinessHour(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessHourAt(BusinessHour aBusinessHour, int index)
  {
    boolean wasAdded = false;
    if(businessHours.contains(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessHourAt(aBusinessHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHolidays()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHoliday(TimeSlot aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    holidays.add(aHoliday);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHoliday(TimeSlot aHoliday)
  {
    boolean wasRemoved = false;
    if (holidays.contains(aHoliday))
    {
      holidays.remove(aHoliday);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHolidayAt(TimeSlot aHoliday, int index)
  {  
    boolean wasAdded = false;
    if(addHoliday(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHolidayAt(TimeSlot aHoliday, int index)
  {
    boolean wasAdded = false;
    if(holidays.contains(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHolidayAt(aHoliday, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVacation()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addVacation(TimeSlot aVacation)
  {
    boolean wasAdded = false;
    if (vacation.contains(aVacation)) { return false; }
    vacation.add(aVacation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVacation(TimeSlot aVacation)
  {
    boolean wasRemoved = false;
    if (vacation.contains(aVacation))
    {
      vacation.remove(aVacation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVacationAt(TimeSlot aVacation, int index)
  {  
    boolean wasAdded = false;
    if(addVacation(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacation()) { index = numberOfVacation() - 1; }
      vacation.remove(aVacation);
      vacation.add(index, aVacation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVacationAt(TimeSlot aVacation, int index)
  {
    boolean wasAdded = false;
    if(vacation.contains(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacation()) { index = numberOfVacation() - 1; }
      vacation.remove(aVacation);
      vacation.add(index, aVacation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVacationAt(aVacation, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFlexiBook(FlexiBook aNewFlexiBook)
  {
    boolean wasSet = false;
    if (aNewFlexiBook == null)
    {
      //Unable to setFlexiBook to null, as business must always be associated to a flexiBook
      return wasSet;
    }
    
    Business existingBusiness = aNewFlexiBook.getBusiness();
    if (existingBusiness != null && !equals(existingBusiness))
    {
      //Unable to setFlexiBook, the current flexiBook already has a business, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FlexiBook anOldFlexiBook = flexiBook;
    flexiBook = aNewFlexiBook;
    flexiBook.setBusiness(this);

    if (anOldFlexiBook != null)
    {
      anOldFlexiBook.setBusiness(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    businessHours.clear();
    holidays.clear();
    vacation.clear();
    FlexiBook existingFlexiBook = flexiBook;
    flexiBook = null;
    if (existingFlexiBook != null)
    {
      existingFlexiBook.setBusiness(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBook = "+(getFlexiBook()!=null?Integer.toHexString(System.identityHashCode(getFlexiBook())):"null");
  }
}