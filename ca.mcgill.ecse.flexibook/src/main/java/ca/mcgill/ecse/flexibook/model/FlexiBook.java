/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 3 "../../../../../FlexiBook.ump"
public class FlexiBook
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FlexiBook Associations
  private Business business;
  private Owner owner;
  private List<Customer> customers;
  private List<BusinessHour> hours;
  private List<Appointment> appointments;
  private List<TimeSlot> timeSlots;
  private List<BookableService> bookableServices;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FlexiBook()
  {
    customers = new ArrayList<Customer>();
    hours = new ArrayList<BusinessHour>();
    appointments = new ArrayList<Appointment>();
    timeSlots = new ArrayList<TimeSlot>();
    bookableServices = new ArrayList<BookableService>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }

  public boolean hasBusiness()
  {
    boolean has = business != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }

  public boolean hasOwner()
  {
    boolean has = owner != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  public BusinessHour getHour(int index)
  {
    BusinessHour aHour = hours.get(index);
    return aHour;
  }

  public List<BusinessHour> getHours()
  {
    List<BusinessHour> newHours = Collections.unmodifiableList(hours);
    return newHours;
  }

  public int numberOfHours()
  {
    int number = hours.size();
    return number;
  }

  public boolean hasHours()
  {
    boolean has = hours.size() > 0;
    return has;
  }

  public int indexOfHour(BusinessHour aHour)
  {
    int index = hours.indexOf(aHour);
    return index;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_GetMany */
  public BookableService getBookableService(int index)
  {
    BookableService aBookableService = bookableServices.get(index);
    return aBookableService;
  }

  public List<BookableService> getBookableServices()
  {
    List<BookableService> newBookableServices = Collections.unmodifiableList(bookableServices);
    return newBookableServices;
  }

  public int numberOfBookableServices()
  {
    int number = bookableServices.size();
    return number;
  }

  public boolean hasBookableServices()
  {
    boolean has = bookableServices.size() > 0;
    return has;
  }

  public int indexOfBookableService(BookableService aBookableService)
  {
    int index = bookableServices.indexOf(aBookableService);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setBusiness(Business aNewBusiness)
  {
    boolean wasSet = false;
    if (business != null && !business.equals(aNewBusiness) && equals(business.getFlexiBook()))
    {
      //Unable to setBusiness, as existing business would become an orphan
      return wasSet;
    }

    business = aNewBusiness;
    FlexiBook anOldFlexiBook = aNewBusiness != null ? aNewBusiness.getFlexiBook() : null;

    if (!this.equals(anOldFlexiBook))
    {
      if (anOldFlexiBook != null)
      {
        anOldFlexiBook.business = null;
      }
      if (business != null)
      {
        business.setFlexiBook(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOwner(Owner aNewOwner)
  {
    boolean wasSet = false;
    if (owner != null && !owner.equals(aNewOwner) && equals(owner.getFlexiBook()))
    {
      //Unable to setOwner, as existing owner would become an orphan
      return wasSet;
    }

    owner = aNewOwner;
    FlexiBook anOldFlexiBook = aNewOwner != null ? aNewOwner.getFlexiBook() : null;

    if (!this.equals(anOldFlexiBook))
    {
      if (anOldFlexiBook != null)
      {
        anOldFlexiBook.owner = null;
      }
      if (owner != null)
      {
        owner.setFlexiBook(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(String aUsername, String aPassword)
  {
    return new Customer(aUsername, aPassword, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    FlexiBook existingFlexiBook = aCustomer.getFlexiBook();
    boolean isNewFlexiBook = existingFlexiBook != null && !this.equals(existingFlexiBook);
    if (isNewFlexiBook)
    {
      aCustomer.setFlexiBook(this);
    }
    else
    {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomer, as it must always have a flexiBook
    if (!this.equals(aCustomer.getFlexiBook()))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BusinessHour addHour(BusinessHour.DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
  {
    return new BusinessHour(aDayOfWeek, aStartTime, aEndTime, this);
  }

  public boolean addHour(BusinessHour aHour)
  {
    boolean wasAdded = false;
    if (hours.contains(aHour)) { return false; }
    FlexiBook existingFlexiBook = aHour.getFlexiBook();
    boolean isNewFlexiBook = existingFlexiBook != null && !this.equals(existingFlexiBook);
    if (isNewFlexiBook)
    {
      aHour.setFlexiBook(this);
    }
    else
    {
      hours.add(aHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHour(BusinessHour aHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aHour, as it must always have a flexiBook
    if (!this.equals(aHour.getFlexiBook()))
    {
      hours.remove(aHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHourAt(BusinessHour aHour, int index)
  {  
    boolean wasAdded = false;
    if(addHour(aHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHours()) { index = numberOfHours() - 1; }
      hours.remove(aHour);
      hours.add(index, aHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHourAt(BusinessHour aHour, int index)
  {
    boolean wasAdded = false;
    if(hours.contains(aHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHours()) { index = numberOfHours() - 1; }
      hours.remove(aHour);
      hours.add(index, aHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHourAt(aHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Customer aCustomer, BookableService aBookableService, TimeSlot aTimeSlot)
  {
    return new Appointment(aCustomer, aBookableService, aTimeSlot, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    FlexiBook existingFlexiBook = aAppointment.getFlexiBook();
    boolean isNewFlexiBook = existingFlexiBook != null && !this.equals(existingFlexiBook);
    if (isNewFlexiBook)
    {
      aAppointment.setFlexiBook(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a flexiBook
    if (!this.equals(aAppointment.getFlexiBook()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime)
  {
    return new TimeSlot(aStartDate, aStartTime, aEndDate, aEndTime, this);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    FlexiBook existingFlexiBook = aTimeSlot.getFlexiBook();
    boolean isNewFlexiBook = existingFlexiBook != null && !this.equals(existingFlexiBook);
    if (isNewFlexiBook)
    {
      aTimeSlot.setFlexiBook(this);
    }
    else
    {
      timeSlots.add(aTimeSlot);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a flexiBook
    if (!this.equals(aTimeSlot.getFlexiBook()))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookableServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addBookableService(BookableService aBookableService)
  {
    boolean wasAdded = false;
    if (bookableServices.contains(aBookableService)) { return false; }
    FlexiBook existingFlexiBook = aBookableService.getFlexiBook();
    boolean isNewFlexiBook = existingFlexiBook != null && !this.equals(existingFlexiBook);
    if (isNewFlexiBook)
    {
      aBookableService.setFlexiBook(this);
    }
    else
    {
      bookableServices.add(aBookableService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBookableService(BookableService aBookableService)
  {
    boolean wasRemoved = false;
    //Unable to remove aBookableService, as it must always have a flexiBook
    if (!this.equals(aBookableService.getFlexiBook()))
    {
      bookableServices.remove(aBookableService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookableServiceAt(BookableService aBookableService, int index)
  {  
    boolean wasAdded = false;
    if(addBookableService(aBookableService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookableServices()) { index = numberOfBookableServices() - 1; }
      bookableServices.remove(aBookableService);
      bookableServices.add(index, aBookableService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookableServiceAt(BookableService aBookableService, int index)
  {
    boolean wasAdded = false;
    if(bookableServices.contains(aBookableService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookableServices()) { index = numberOfBookableServices() - 1; }
      bookableServices.remove(aBookableService);
      bookableServices.add(index, aBookableService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookableServiceAt(aBookableService, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Business existingBusiness = business;
    business = null;
    if (existingBusiness != null)
    {
      existingBusiness.delete();
      existingBusiness.setFlexiBook(null);
    }
    Owner existingOwner = owner;
    owner = null;
    if (existingOwner != null)
    {
      existingOwner.delete();
      existingOwner.setFlexiBook(null);
    }
    while (customers.size() > 0)
    {
      Customer aCustomer = customers.get(customers.size() - 1);
      aCustomer.delete();
      customers.remove(aCustomer);
    }
    
    while (hours.size() > 0)
    {
      BusinessHour aHour = hours.get(hours.size() - 1);
      aHour.delete();
      hours.remove(aHour);
    }
    
    while (appointments.size() > 0)
    {
      Appointment aAppointment = appointments.get(appointments.size() - 1);
      aAppointment.delete();
      appointments.remove(aAppointment);
    }
    
    while (timeSlots.size() > 0)
    {
      TimeSlot aTimeSlot = timeSlots.get(timeSlots.size() - 1);
      aTimeSlot.delete();
      timeSlots.remove(aTimeSlot);
    }
    
    while (bookableServices.size() > 0)
    {
      BookableService aBookableService = bookableServices.get(bookableServices.size() - 1);
      aBookableService.delete();
      bookableServices.remove(aBookableService);
    }
    
  }

}