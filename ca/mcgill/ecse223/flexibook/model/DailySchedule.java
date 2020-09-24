/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.flexibook.model;
import java.sql.Time;

// line 48 "../../../../../Domain Model (Iteration 1) v1.0.ump"
// line 195 "../../../../../Domain Model (Iteration 1) v1.0.ump"
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
  private FlexiBookSystem flexiBookSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailySchedule(Time aLunchBreakStartTime, Time aLunchBreakEndTime, Time aOpenTime, Time aCloseTime, FlexiBookSystem aFlexiBookSystem)
  {
    lunchBreakStartTime = aLunchBreakStartTime;
    lunchBreakEndTime = aLunchBreakEndTime;
    openTime = aOpenTime;
    closeTime = aCloseTime;
    boolean didAddFlexiBookSystem = setFlexiBookSystem(aFlexiBookSystem);
    if (!didAddFlexiBookSystem)
    {
      throw new RuntimeException("Unable to create dailySchedule due to flexiBookSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public FlexiBookSystem getFlexiBookSystem()
  {
    return flexiBookSystem;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFlexiBookSystem(FlexiBookSystem aNewFlexiBookSystem)
  {
    boolean wasSet = false;
    if (aNewFlexiBookSystem == null)
    {
      //Unable to setFlexiBookSystem to null, as dailySchedule must always be associated to a flexiBookSystem
      return wasSet;
    }
    
    DailySchedule existingDailySchedule = aNewFlexiBookSystem.getDailySchedule();
    if (existingDailySchedule != null && !equals(existingDailySchedule))
    {
      //Unable to setFlexiBookSystem, the current flexiBookSystem already has a dailySchedule, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FlexiBookSystem anOldFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = aNewFlexiBookSystem;
    flexiBookSystem.setDailySchedule(this);

    if (anOldFlexiBookSystem != null)
    {
      anOldFlexiBookSystem.setDailySchedule(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FlexiBookSystem existingFlexiBookSystem = flexiBookSystem;
    flexiBookSystem = null;
    if (existingFlexiBookSystem != null)
    {
      existingFlexiBookSystem.setDailySchedule(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakStartTime" + "=" + (getLunchBreakStartTime() != null ? !getLunchBreakStartTime().equals(this)  ? getLunchBreakStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lunchBreakEndTime" + "=" + (getLunchBreakEndTime() != null ? !getLunchBreakEndTime().equals(this)  ? getLunchBreakEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openTime" + "=" + (getOpenTime() != null ? !getOpenTime().equals(this)  ? getOpenTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closeTime" + "=" + (getCloseTime() != null ? !getCloseTime().equals(this)  ? getCloseTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "flexiBookSystem = "+(getFlexiBookSystem()!=null?Integer.toHexString(System.identityHashCode(getFlexiBookSystem())):"null");
  }
}