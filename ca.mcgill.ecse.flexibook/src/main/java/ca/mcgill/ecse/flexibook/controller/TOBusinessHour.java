/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;
import java.sql.Time;

import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;

// line 66 "../../../../../FlexiBookTransferObjects.ump"
public class TOBusinessHour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBusinessHour Attributes
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBusinessHour(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}