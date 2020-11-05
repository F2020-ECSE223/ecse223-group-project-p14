/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.io.Serializable;
import java.util.*;

/**
 * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
 */
// line 65 "../../../../../FlexiBookPersistence.ump"
// line 23 "../../../../../FlexiBook.ump"
public class Owner extends User implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private FlexiBook flexiBook;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, FlexiBook aFlexiBook)
  {
    super(aUsername, aPassword);
    boolean didAddFlexiBook = setFlexiBook(aFlexiBook);
    if (!didAddFlexiBook)
    {
      throw new RuntimeException("Unable to create owner due to flexiBook. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public FlexiBook getFlexiBook()
  {
    return flexiBook;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFlexiBook(FlexiBook aNewFlexiBook)
  {
    boolean wasSet = false;
    if (aNewFlexiBook == null)
    {
      //Unable to setFlexiBook to null, as owner must always be associated to a flexiBook
      return wasSet;
    }
    
    Owner existingOwner = aNewFlexiBook.getOwner();
    if (existingOwner != null && !equals(existingOwner))
    {
      //Unable to setFlexiBook, the current flexiBook already has a owner, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FlexiBook anOldFlexiBook = flexiBook;
    flexiBook = aNewFlexiBook;
    flexiBook.setOwner(this);

    if (anOldFlexiBook != null)
    {
      anOldFlexiBook.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FlexiBook existingFlexiBook = flexiBook;
    flexiBook = null;
    if (existingFlexiBook != null)
    {
      existingFlexiBook.setOwner(null);
    }
    super.delete();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 68 "../../../../../FlexiBookPersistence.ump"
  private static final long serialVersionUID = -33499187203441385L ;

  
}