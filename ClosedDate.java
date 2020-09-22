/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.sql.Date;

// line 67 "Version 0.5 - CC, YW.ump"
// line 163 "Version 0.5 - CC, YW.ump"
public class ClosedDate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClosedDate Attributes
  private Date downDateStarts;
  private Date downDateEnds;

  //ClosedDate Associations
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClosedDate(Date aDownDateStarts, Date aDownDateEnds, Calendar aCalendar)
  {
    downDateStarts = aDownDateStarts;
    downDateEnds = aDownDateEnds;
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create closedDate due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDownDateStarts(Date aDownDateStarts)
  {
    boolean wasSet = false;
    downDateStarts = aDownDateStarts;
    wasSet = true;
    return wasSet;
  }

  public boolean setDownDateEnds(Date aDownDateEnds)
  {
    boolean wasSet = false;
    downDateEnds = aDownDateEnds;
    wasSet = true;
    return wasSet;
  }

  public Date getDownDateStarts()
  {
    return downDateStarts;
  }

  public Date getDownDateEnds()
  {
    return downDateEnds;
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
      existingCalendar.removeClosedDate(this);
    }
    calendar.addClosedDate(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Calendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeClosedDate(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "downDateStarts" + "=" + (getDownDateStarts() != null ? !getDownDateStarts().equals(this)  ? getDownDateStarts().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "downDateEnds" + "=" + (getDownDateEnds() != null ? !getDownDateEnds().equals(this)  ? getDownDateEnds().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}