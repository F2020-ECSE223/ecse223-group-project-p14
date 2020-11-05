/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.io.Serializable;
import java.util.*;

/**
 * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
 */
// line 72 "../../../../../FlexiBookPersistence.ump"
// line 68 "../../../../../FlexiBook.ump"
public class Service extends BookableService implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private int duration;
  private int downtimeDuration;
  private int downtimeStart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, FlexiBook aFlexiBook, int aDuration, int aDowntimeDuration, int aDowntimeStart)
  {
    super(aName, aFlexiBook);
    duration = aDuration;
    downtimeDuration = aDowntimeDuration;
    downtimeStart = aDowntimeStart;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public int getDuration()
  {
    return duration;
  }

  /**
   * if downtimeDuration is 0, then downtimeStart is irrelevant
   */
  public int getDowntimeDuration()
  {
    return downtimeDuration;
  }

  public int getDowntimeStart()
  {
    return downtimeStart;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "," +
            "downtimeDuration" + ":" + getDowntimeDuration()+ "," +
            "downtimeStart" + ":" + getDowntimeStart()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 75 "../../../../../FlexiBookPersistence.ump"
  private static final long serialVersionUID = -4371758872458522436L ;

  
}