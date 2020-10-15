/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
<<<<<<< HEAD
import java.sql.Time;
import java.util.*;
import java.sql.Date;

// line 60 "../../../../../Version 0.5 - CC, YW.ump"
// line 155 "../../../../../Version 0.5 - CC, YW.ump"
=======
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 40 "../../../../../Domain Model v1.1.ump"
// line 132 "../../../../../Domain Model v1.1.ump"
>>>>>>> master
public class Calendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

<<<<<<< HEAD
  //Calendar Attributes
  private Time lunchBreakStart;
  private Time lunchBreakEnd;
  private Time openTime;
  private Time closeTime;

  //Calendar Associations
  private FlexiBookSystem flexiBookSystem;
  private List<ClosedDate> closedDates;
=======
  //Calendar Associations
  private FlexiBookSystem flexiBookSystem;
  private DailySchedule dailySchedule;
  private List<Vacation> vacations;
>>>>>>> master
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

<<<<<<< HEAD
  public Calendar(Time aLunchBreakStart, Time aLunchBreakEnd, Time aOpenTime, Time aCloseTime, FlexiBookSystem aFlexiBookSystem)
  {
    lunchBreakStart = aLunchBreakStart;
    lunchBreakEnd = aLunchBreakEnd;
    openTime = aOpenTime;
    closeTime = aCloseTime;
=======
  public Calendar(FlexiBookSystem aFlexiBookSystem, DailySchedule aDailySchedule)
  {
>>>>>>> master
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create calendar due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
<<<<<<< HEAD
    closedDates = new ArrayList<ClosedDate>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLunchBreakStart(Time aLunchBreakStart)
  {
    boolean wasSet = false;
    lunchBreakStart = aLunchBreakStart;
    wasSet = true;
    return wasSet;
  }

  public boolean setLunchBreakEnd(Time aLunchBreakEnd)
  {
    boolean wasSet = false;
    lunchBreakEnd = aLunchBreakEnd;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpenTime(Time aOpenTime)
  {
    boolean wasSet = false;
    openTime = aOpenTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCloseTime(Time aCloseTime)
  {
    boolean wasSet = false;
    closeTime = aCloseTime;
    wasSet = true;
    return wasSet;
  }

  public Time getLunchBreakStart()
  {
    return lunchBreakStart;
  }

  public Time getLunchBreakEnd()
  {
    return lunchBreakEnd;
  }

  public Time getOpenTime()
  {
    return openTime;
  }

  public Time getCloseTime()
  {
    return closeTime;
  }
=======
    if (aDailySchedule == null || aDailySchedule.getCalendar() != null)
    {
      throw new RuntimeException("Unable to create Calendar due to aDailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    dailySchedule = aDailySchedule;
    vacations = new ArrayList<Vacation>();
    appointments = new ArrayList<Appointment>();
  }

  public Calendar(FlexiBookSystem aFlexiBookSystem, Time aLunchBreakStartTimeForDailySchedule, Time aLunchBreakEndTimeForDailySchedule, Time aOpenTimeForDailySchedule, Time aCloseTimeForDailySchedule)
  {
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create calendar due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    dailySchedule = new DailySchedule(aLunchBreakStartTimeForDailySchedule, aLunchBreakEndTimeForDailySchedule, aOpenTimeForDailySchedule, aCloseTimeForDailySchedule, this);
    vacations = new ArrayList<Vacation>();
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------
>>>>>>> master
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
<<<<<<< HEAD
  /* Code from template association_GetMany */
  public ClosedDate getClosedDate(int index)
  {
    ClosedDate aClosedDate = closedDates.get(index);
    return aClosedDate;
  }

  public List<ClosedDate> getClosedDates()
  {
    List<ClosedDate> newClosedDates = Collections.unmodifiableList(closedDates);
    return newClosedDates;
  }

  public int numberOfClosedDates()
  {
    int number = closedDates.size();
    return number;
  }

  public boolean hasClosedDates()
  {
    boolean has = closedDates.size() > 0;
    return has;
  }

  public int indexOfClosedDate(ClosedDate aClosedDate)
  {
    int index = closedDates.indexOf(aClosedDate);
=======
  /* Code from template association_GetOne */
  public DailySchedule getDailySchedule()
  {
    return dailySchedule;
  }
  /* Code from template association_GetMany */
  public Vacation getVacation(int index)
  {
    Vacation aVacation = vacations.get(index);
    return aVacation;
  }

  public List<Vacation> getVacations()
  {
    List<Vacation> newVacations = Collections.unmodifiableList(vacations);
    return newVacations;
  }

  public int numberOfVacations()
  {
    int number = vacations.size();
    return number;
  }

  public boolean hasVacations()
  {
    boolean has = vacations.size() > 0;
    return has;
  }

  public int indexOfVacation(Vacation aVacation)
  {
    int index = vacations.indexOf(aVacation);
>>>>>>> master
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFlexiBookSystem(FlexiBookSystem aNewFlexiBookSystem)
  {
    boolean wasSet = false;
    if (aNewFlexiBookSystem == null)
    {
      //Unable to setFlexiBookSystem to null, as calendar must always be associated to a flexiBookSystem
      return wasSet;
    }
    
    Calendar existingCalendar = aNewFlexiBookSystem.getCalendar();
    if (existingCalendar != null && !equals(existingCalendar))
    {
      //Unable to setFlexiBookSystem, the current flexiBookSystem already has a calendar, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FlexiBookSystem anOldFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = aNewFlexiBookSystem;
    flexiBookSystem.setCalendar(this);

    if (anOldFlexiBookSystem != null)
    {
      anOldFlexiBookSystem.setCalendar(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
<<<<<<< HEAD
  public static int minimumNumberOfClosedDates()
=======
  public static int minimumNumberOfVacations()
>>>>>>> master
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
<<<<<<< HEAD
  public ClosedDate addClosedDate(Date aDownDateStarts, Date aDownDateEnds)
  {
    return new ClosedDate(aDownDateStarts, aDownDateEnds, this);
  }

  public boolean addClosedDate(ClosedDate aClosedDate)
  {
    boolean wasAdded = false;
    if (closedDates.contains(aClosedDate)) { return false; }
    Calendar existingCalendar = aClosedDate.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aClosedDate.setCalendar(this);
    }
    else
    {
      closedDates.add(aClosedDate);
=======
  public Vacation addVacation(Date aStartDate, Date aEndDate, Time aStartAt, Time aEndAt)
  {
    return new Vacation(aStartDate, aEndDate, aStartAt, aEndAt, this);
  }

  public boolean addVacation(Vacation aVacation)
  {
    boolean wasAdded = false;
    if (vacations.contains(aVacation)) { return false; }
    Calendar existingCalendar = aVacation.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aVacation.setCalendar(this);
    }
    else
    {
      vacations.add(aVacation);
>>>>>>> master
    }
    wasAdded = true;
    return wasAdded;
  }

<<<<<<< HEAD
  public boolean removeClosedDate(ClosedDate aClosedDate)
  {
    boolean wasRemoved = false;
    //Unable to remove aClosedDate, as it must always have a calendar
    if (!this.equals(aClosedDate.getCalendar()))
    {
      closedDates.remove(aClosedDate);
=======
  public boolean removeVacation(Vacation aVacation)
  {
    boolean wasRemoved = false;
    //Unable to remove aVacation, as it must always have a calendar
    if (!this.equals(aVacation.getCalendar()))
    {
      vacations.remove(aVacation);
>>>>>>> master
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
<<<<<<< HEAD
  public boolean addClosedDateAt(ClosedDate aClosedDate, int index)
  {  
    boolean wasAdded = false;
    if(addClosedDate(aClosedDate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClosedDates()) { index = numberOfClosedDates() - 1; }
      closedDates.remove(aClosedDate);
      closedDates.add(index, aClosedDate);
=======
  public boolean addVacationAt(Vacation aVacation, int index)
  {  
    boolean wasAdded = false;
    if(addVacation(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacations()) { index = numberOfVacations() - 1; }
      vacations.remove(aVacation);
      vacations.add(index, aVacation);
>>>>>>> master
      wasAdded = true;
    }
    return wasAdded;
  }

<<<<<<< HEAD
  public boolean addOrMoveClosedDateAt(ClosedDate aClosedDate, int index)
  {
    boolean wasAdded = false;
    if(closedDates.contains(aClosedDate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfClosedDates()) { index = numberOfClosedDates() - 1; }
      closedDates.remove(aClosedDate);
      closedDates.add(index, aClosedDate);
=======
  public boolean addOrMoveVacationAt(Vacation aVacation, int index)
  {
    boolean wasAdded = false;
    if(vacations.contains(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacations()) { index = numberOfVacations() - 1; }
      vacations.remove(aVacation);
      vacations.add(index, aVacation);
>>>>>>> master
      wasAdded = true;
    } 
    else 
    {
<<<<<<< HEAD
      wasAdded = addClosedDateAt(aClosedDate, index);
=======
      wasAdded = addVacationAt(aVacation, index);
>>>>>>> master
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
<<<<<<< HEAD
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Date aServiceStartDate, Time aServiceStartTime, boolean aIsCancelled, Customer aCustomer, Service aService)
  {
    return new Appointment(aServiceStartDate, aServiceStartTime, aIsCancelled, aCustomer, this, aService);
  }

=======
  /* Code from template association_AddUnidirectionalMany */
>>>>>>> master
  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
<<<<<<< HEAD
    Calendar existingCalendar = aAppointment.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aAppointment.setCalendar(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
=======
    appointments.add(aAppointment);
>>>>>>> master
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
<<<<<<< HEAD
    //Unable to remove aAppointment, as it must always have a calendar
    if (!this.equals(aAppointment.getCalendar()))
=======
    if (appointments.contains(aAppointment))
>>>>>>> master
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

  public void delete()
  {
    FlexiBookSystem existingFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = null;
    if (existingFlexiBookSystem != null)
    {
      existingFlexiBookSystem.setCalendar(null);
    }
<<<<<<< HEAD
    for(int i=closedDates.size(); i > 0; i--)
    {
      ClosedDate aClosedDate = closedDates.get(i - 1);
      aClosedDate.delete();
    }
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakStart" + "=" + (getLunchBreakStart() != null ? !getLunchBreakStart().equals(this)  ? getLunchBreakStart().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakEnd" + "=" + (getLunchBreakEnd() != null ? !getLunchBreakEnd().equals(this)  ? getLunchBreakEnd().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openTime" + "=" + (getOpenTime() != null ? !getOpenTime().equals(this)  ? getOpenTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closeTime" + "=" + (getCloseTime() != null ? !getCloseTime().equals(this)  ? getCloseTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null");
  }
=======
    DailySchedule existingDailySchedule = dailySchedule;
    dailySchedule = null;
    if (existingDailySchedule != null)
    {
      existingDailySchedule.delete();
    }
    for(int i=vacations.size(); i > 0; i--)
    {
      Vacation aVacation = vacations.get(i - 1);
      aVacation.delete();
    }
    appointments.clear();
  }

>>>>>>> master
}