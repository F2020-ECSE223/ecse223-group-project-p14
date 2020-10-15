/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Time;

// line 46 "../../../../../Domain Model v1.1.ump"
// line 139 "../../../../../Domain Model v1.1.ump"
public class DailySchedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DailySchedule Attributes
  private Time lunchBreakStartTime;
  private Time lunchBreakEndTime;
  private Time openTime;
  private Time closeTime;

  //DailySchedule Associations
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailySchedule(Time aLunchBreakStartTime, Time aLunchBreakEndTime, Time aOpenTime, Time aCloseTime, Calendar aCalendar)
  {
    lunchBreakStartTime = aLunchBreakStartTime;
    lunchBreakEndTime = aLunchBreakEndTime;
    openTime = aOpenTime;
    closeTime = aCloseTime;
    if (aCalendar == null || aCalendar.getDailySchedule() != null)
    {
      throw new RuntimeException("Unable to create DailySchedule due to aCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    calendar = aCalendar;
  }

  public DailySchedule(Time aLunchBreakStartTime, Time aLunchBreakEndTime, Time aOpenTime, Time aCloseTime, FlexiBookSystem aFlexiBookSystemForCalendar)
  {
    lunchBreakStartTime = aLunchBreakStartTime;
    lunchBreakEndTime = aLunchBreakEndTime;
    openTime = aOpenTime;
    closeTime = aCloseTime;
    calendar = new Calendar(aFlexiBookSystemForCalendar, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLunchBreakStartTime(Time aLunchBreakStartTime)
  {
    boolean wasSet = false;
    lunchBreakStartTime = aLunchBreakStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setLunchBreakEndTime(Time aLunchBreakEndTime)
  {
    boolean wasSet = false;
    lunchBreakEndTime = aLunchBreakEndTime;
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

  public Time getLunchBreakStartTime()
  {
    return lunchBreakStartTime;
  }

  public Time getLunchBreakEndTime()
  {
    return lunchBreakEndTime;
  }

  public Time getOpenTime()
  {
    return openTime;
  }

  public Time getCloseTime()
  {
    return closeTime;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }

  public void delete()
  {
    Calendar existingCalendar = calendar;
    calendar = null;
    if (existingCalendar != null)
    {
      existingCalendar.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakStartTime" + "=" + (getLunchBreakStartTime() != null ? !getLunchBreakStartTime().equals(this)  ? getLunchBreakStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakEndTime" + "=" + (getLunchBreakEndTime() != null ? !getLunchBreakEndTime().equals(this)  ? getLunchBreakEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openTime" + "=" + (getOpenTime() != null ? !getOpenTime().equals(this)  ? getOpenTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closeTime" + "=" + (getCloseTime() != null ? !getCloseTime().equals(this)  ? getCloseTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}