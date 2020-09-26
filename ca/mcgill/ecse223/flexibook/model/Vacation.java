/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Date;
import java.sql.Time;

// line 60 "../../../../../Domain Model v1.1.ump"
// line 157 "../../../../../Domain Model v1.1.ump"
public class Vacation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Vacation Attributes
  private Date startDate;
  private Date endDate;
  private Time startAt;
  private Time endAt;

  //Vacation Associations
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Vacation(Date aStartDate, Date aEndDate, Time aStartAt, Time aEndAt, Calendar aCalendar)
  {
    startDate = aStartDate;
    endDate = aEndDate;
    startAt = aStartAt;
    endAt = aEndAt;
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create vacation due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartAt(Time aStartAt)
  {
    boolean wasSet = false;
    startAt = aStartAt;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndAt(Time aEndAt)
  {
    boolean wasSet = false;
    endAt = aEndAt;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public Time getStartAt()
  {
    return startAt;
  }

  public Time getEndAt()
  {
    return endAt;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCalendar(Calendar aCalendar)
  {
    boolean wasSet = false;
    if (aCalendar == null)
    {
      return wasSet;
    }

    Calendar existingCalendar = calendar;
    calendar = aCalendar;
    if (existingCalendar != null && !existingCalendar.equals(aCalendar))
    {
      existingCalendar.removeVacation(this);
    }
    calendar.addVacation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Calendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeVacation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startAt" + "=" + (getStartAt() != null ? !getStartAt().equals(this)  ? getStartAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endAt" + "=" + (getEndAt() != null ? !getEndAt().equals(this)  ? getEndAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}