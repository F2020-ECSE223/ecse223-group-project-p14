/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.controller;

// line 42 "../../../../../../model.ump"
// line 81 "../../../../../../model.ump"
public class TOService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOService Attributes
  private String name;
  private int duration;
  private int downtimeDuration;
  private int downtimeStart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOService(String aName, int aDuration, int aDowntimeDuration, int aDowntimeStart)
  {
    name = aName;
    duration = aDuration;
    downtimeDuration = aDowntimeDuration;
    downtimeStart = aDowntimeStart;
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

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setDowntimeDuration(int aDowntimeDuration)
  {
    boolean wasSet = false;
    downtimeDuration = aDowntimeDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setDowntimeStart(int aDowntimeStart)
  {
    boolean wasSet = false;
    downtimeStart = aDowntimeStart;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDuration()
  {
    return duration;
  }

  public int getDowntimeDuration()
  {
    return downtimeDuration;
  }

  public int getDowntimeStart()
  {
    return downtimeStart;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "duration" + ":" + getDuration()+ "," +
            "downtimeDuration" + ":" + getDowntimeDuration()+ "," +
            "downtimeStart" + ":" + getDowntimeStart()+ "]";
  }
}