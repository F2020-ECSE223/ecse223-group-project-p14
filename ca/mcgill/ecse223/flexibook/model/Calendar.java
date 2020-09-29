/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 42 "../../../../../Domain Model v1.1.ump"
// line 135 "../../../../../Domain Model v1.1.ump"
public class Calendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Calendar Associations
  private FlexiBookSystem flexiBookSystem;
  private DailySchedule dailySchedule;
  private List<Vacation> vacations;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Calendar(FlexiBookSystem aFlexiBookSystem, DailySchedule aDailySchedule)
  {
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create calendar due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
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
  public static int minimumNumberOfVacations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
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
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVacation(Vacation aVacation)
  {
    boolean wasRemoved = false;
    //Unable to remove aVacation, as it must always have a calendar
    if (!this.equals(aVacation.getCalendar()))
    {
      vacations.remove(aVacation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVacationAt(Vacation aVacation, int index)
  {  
    boolean wasAdded = false;
    if(addVacation(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacations()) { index = numberOfVacations() - 1; }
      vacations.remove(aVacation);
      vacations.add(index, aVacation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVacationAt(Vacation aVacation, int index)
  {
    boolean wasAdded = false;
    if(vacations.contains(aVacation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVacations()) { index = numberOfVacations() - 1; }
      vacations.remove(aVacation);
      vacations.add(index, aVacation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVacationAt(aVacation, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    appointments.add(aAppointment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    if (appointments.contains(aAppointment))
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

}