/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;
import java.time.LocalDateTime;
import ca.mcgill.ecse.flexibook.controller.TOTimeSlot;
import java.io.Serializable;
import java.util.*;

/**
 * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
 */
// line 1 "../../../../../FlexiBookStateMachine.ump"
// line 17 "../../../../../FlexiBookPersistence.ump"
// line 89 "../../../../../FlexiBook.ump"
public class Appointment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment State Machines
  public enum AppointmentStatus { Booked, InProgress, FinalState }
  private AppointmentStatus appointmentStatus;

  //Appointment Associations
  private Customer customer;
  private BookableService bookableService;
  private List<ComboItem> chosenItems;
  private TimeSlot timeSlot;
  private FlexiBook flexiBook;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Customer aCustomer, BookableService aBookableService, TimeSlot aTimeSlot, FlexiBook aFlexiBook)
  {
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBookableService = setBookableService(aBookableService);
    if (!didAddBookableService)
    {
      throw new RuntimeException("Unable to create appointment due to bookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    chosenItems = new ArrayList<ComboItem>();
    if (!setTimeSlot(aTimeSlot))
    {
      throw new RuntimeException("Unable to create Appointment due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddFlexiBook = setFlexiBook(aFlexiBook);
    if (!didAddFlexiBook)
    {
      throw new RuntimeException("Unable to create appointment due to flexiBook. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setAppointmentStatus(AppointmentStatus.Booked);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getAppointmentStatusFullName()
  {
    String answer = appointmentStatus.toString();
    return answer;
  }

  public AppointmentStatus getAppointmentStatus()
  {
    return appointmentStatus;
  }

  public boolean cancelAppointment(Date currentDate)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (!(isOnSameDayAsAppointment(currentDate)))
        {
          setAppointmentStatus(AppointmentStatus.FinalState);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateAppointmentTime(Date newDate,Time newStartTime,Date currentDate,Time currentTime)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (isGoodForTimeUpdate(newDate,newStartTime,currentDate,currentTime)&&isBeforeToday(currentDate))
        {
        // line 19 "../../../../../FlexiBookStateMachine.ump"
          doUpdateTime(newDate , newStartTime);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateAppointmentContent(String action,String optService,Date currentDate,Time currentTime)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (isGoodForContentUpdate(action,optService,currentDate,currentTime)&&!(isOnSameDayAsAppointment(currentDate)))
        {
        // line 22 "../../../../../FlexiBookStateMachine.ump"
          doUpdateContent(action, optService);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        if (isGoodForContentUpdate(action,optService,currentDate,currentTime))
        {
        // line 33 "../../../../../FlexiBookStateMachine.ump"
          doUpdateContent(action, optService);
          setAppointmentStatus(AppointmentStatus.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startAppointment(Date currentDate,Time currentTime)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (hasReachedStartTime(currentDate,currentTime))
        {
          setAppointmentStatus(AppointmentStatus.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean registeredNoShow()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        // line 26 "../../../../../FlexiBookStateMachine.ump"
        incrementNoShow();
        setAppointmentStatus(AppointmentStatus.FinalState);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean finishedAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case InProgress:
        setAppointmentStatus(AppointmentStatus.FinalState);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setAppointmentStatus(AppointmentStatus aAppointmentStatus)
  {
    appointmentStatus = aAppointmentStatus;

    // entry actions and do activities
    switch(appointmentStatus)
    {
      case FinalState:
        // line 41 "../../../../../FlexiBookStateMachine.ump"
        this.delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public BookableService getBookableService()
  {
    return bookableService;
  }
  /* Code from template association_GetMany */
  public ComboItem getChosenItem(int index)
  {
    ComboItem aChosenItem = chosenItems.get(index);
    return aChosenItem;
  }

  public List<ComboItem> getChosenItems()
  {
    List<ComboItem> newChosenItems = Collections.unmodifiableList(chosenItems);
    return newChosenItems;
  }

  public int numberOfChosenItems()
  {
    int number = chosenItems.size();
    return number;
  }

  public boolean hasChosenItems()
  {
    boolean has = chosenItems.size() > 0;
    return has;
  }

  public int indexOfChosenItem(ComboItem aChosenItem)
  {
    int index = chosenItems.indexOf(aChosenItem);
    return index;
  }
  /* Code from template association_GetOne */
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_GetOne */
  public FlexiBook getFlexiBook()
  {
    return flexiBook;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBookableService(BookableService aBookableService)
  {
    boolean wasSet = false;
    if (aBookableService == null)
    {
      return wasSet;
    }

    BookableService existingBookableService = bookableService;
    bookableService = aBookableService;
    if (existingBookableService != null && !existingBookableService.equals(aBookableService))
    {
      existingBookableService.removeAppointment(this);
    }
    bookableService.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChosenItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addChosenItem(ComboItem aChosenItem)
  {
    boolean wasAdded = false;
    if (chosenItems.contains(aChosenItem)) { return false; }
    chosenItems.add(aChosenItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChosenItem(ComboItem aChosenItem)
  {
    boolean wasRemoved = false;
    if (chosenItems.contains(aChosenItem))
    {
      chosenItems.remove(aChosenItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChosenItemAt(ComboItem aChosenItem, int index)
  {  
    boolean wasAdded = false;
    if(addChosenItem(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChosenItemAt(ComboItem aChosenItem, int index)
  {
    boolean wasAdded = false;
    if(chosenItems.contains(aChosenItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChosenItems()) { index = numberOfChosenItems() - 1; }
      chosenItems.remove(aChosenItem);
      chosenItems.add(index, aChosenItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChosenItemAt(aChosenItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot != null)
    {
      timeSlot = aNewTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFlexiBook(FlexiBook aFlexiBook)
  {
    boolean wasSet = false;
    if (aFlexiBook == null)
    {
      return wasSet;
    }

    FlexiBook existingFlexiBook = flexiBook;
    flexiBook = aFlexiBook;
    if (existingFlexiBook != null && !existingFlexiBook.equals(aFlexiBook))
    {
      existingFlexiBook.removeAppointment(this);
    }
    flexiBook.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    BookableService placeholderBookableService = bookableService;
    this.bookableService = null;
    if(placeholderBookableService != null)
    {
      placeholderBookableService.removeAppointment(this);
    }
    chosenItems.clear();
    timeSlot = null;
    FlexiBook placeholderFlexiBook = flexiBook;
    this.flexiBook = null;
    if(placeholderFlexiBook != null)
    {
      placeholderFlexiBook.removeAppointment(this);
    }
  }


  /**
   * 
   * This is the action event "updateAppointmentTime()" takes in the state machine
   * This method will move the time slot of the appointment to a new one
   * defined by the date and time parameter passed in.<br>
   * This action will only be executed if guard condition is passed.
   * @param newDate
   * @param newStartTime
   * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
   * @see #updateAppointmentTime(Date, Time, Date, Time)
   * @see #isGoodForTimeUpdate(Date, Time, Date, Time)
   */
  // line 62 "../../../../../FlexiBookStateMachine.ump"
   public void doUpdateTime(Date newDate, Time newStartTime){
    // get duration of the original service
		TimeSlot oldTimeSlot = getTimeSlot();
		Duration d = Duration.between(oldTimeSlot.getStartTime().toLocalTime(), oldTimeSlot.getEndTime().toLocalTime());
		// get the duration to set new end time. Since there is no change in combo item, the time is same
		int durationMinutes = (int) d.toMinutes();
		Time newEndTime = Time.valueOf(newStartTime.toLocalTime().plusMinutes(durationMinutes));
		TimeSlot timeSlot = new TimeSlot(newDate, newStartTime, newDate, newEndTime, getFlexiBook());
		setTimeSlot(timeSlot);
  }


  /**
   * 
   * This is the action event "updateAppointmentContent()" takes in the state machine.
   * This method will add or remove a service combo item and remain the rest in sequence.<br>
   * Or if the appointment is a single service, this method will delete the chosen service and link the 
   * optService to that appointment
   * This action will only be executed if guard condition is passed.
   * @param action String indicating the action, can be "add" or "remove"
   * @param optService String, name of the optional service
   * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
   * @see #updateAppointmentContent(String, String, Date, Time)
   * @see #isGoodForContentUpdate(Date, Time, Date, Time)
   */
  // line 87 "../../../../../FlexiBookStateMachine.ump"
   private void doUpdateContent(String action, String optService){
    if(getBookableService() instanceof ServiceCombo) {
    	  	
    	TimeSlot oldTimeSlot = getTimeSlot();

		// Scenario: check if the request on adding and removing is legitimate, aka can not remove a mandatory service
		if (action.equals("remove")) {
				if(findServiceCombo(this.getBookableService().getName()).getMainService().getService().getName().equals(optService)) {
					// bad request: cannot remove main service
					return;
				}

			// since appInSystem.getChosenItems() is inmutable by umple, have to create a deep copy here to iterate
			List<ComboItem> copy= new ArrayList<ComboItem>();
			for(ComboItem item: getChosenItems()) {
				copy.add(item);
			}
			for(ComboItem item: copy) {
					if(item.getService().getName().equals(optService)) {
						if(item.getMandatory()) {
							// bad request: cannot remove mandatory service
							return;
						}else {					
							removeChosenItem(item);
						}
					}	
			}
		}else if (action.equals("add")) {
			
			List<ComboItem> newListComboItem = new ArrayList<ComboItem>();
			for(ComboItem item: findServiceCombo(this.getBookableService().getName()).getServices()) {
				// iterate through the combo as a template
				// iN THIS MANNER the combo item in the appointment will also follow certain order!
				// 1. ADD MANDATORY
				if(item.getMandatory() == true) {
					newListComboItem.add(item);
				}else {
					// 2. keeps the ones already exist
					if (getChosenItems().contains(item)) {
						newListComboItem.add(item);
					}else {
						// 3. add the chosen one
							if(optService.equals(item.getService().getName())) {
								newListComboItem.add(item);
							}
							
					}
					
					
				}
			}
			
			List<ComboItem> existingItems = getChosenItems();
			int index = 0;
			for(ComboItem newitem: newListComboItem) {
					if (!existingItems.contains(newitem)) {
						addChosenItemAt(newitem, index);
				}
				index++;
			}

		}
			int newDuration = calcActualTimeOfAppointment(getChosenItems());
			Time newEndTime = Time.valueOf(oldTimeSlot.getStartTime().toLocalTime().plusMinutes(newDuration));
			TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), 
					newEndTime, getFlexiBook());
			
			setTimeSlot(timeSlot);

	  }else if(getBookableService() instanceof Service) {
		  Service s = null;
		  for (BookableService bservice : getFlexiBook().getBookableServices()) {
				if (bservice.getName().equals(optService) && bservice instanceof Service) {
					s = (Service)bservice;
					break;
				}
			}
		  if(s != null) {
			  
			  setBookableService(s);
			  
			  // Shouldnt need this step since the original service is a single service already
			  if(chosenItems.size() !=0) {
				  chosenItems.clear();
			  }
			  Time startTime = getTimeSlot().getStartTime();
			  LocalTime aEndtime = startTime.toLocalTime().plusMinutes(s.getDuration());
			  Time endTime = Time.valueOf(aEndtime);
			  this.getTimeSlot().setEndTime(endTime);
		  }
	  }
  }


  /**
   * 
   * Increments the noShowCount of the customer associated to the account
   * 
   * @author Catherine
   */
  // line 186 "../../../../../FlexiBookStateMachine.ump"
   public void incrementNoShow(){
    int noShowCount = this.getCustomer().getNoShowCount();
		noShowCount++;
		this.getCustomer().setNoShowCount(noShowCount);
  }


  /**
   * 
   * Check if the request of moving an appointment's time slot is valid.
   * This method uses the logic of Controller.isInGoodTiming()<br>
   * 
   * This method will create a new time slot o assuming the time update is successful.
   * Then if the new time slot has no conflict with other time slots, and is in business time,
   * the check returns true.<br>
   * Otherwise the provisional timeslot is delete and return false
   * @param newDate New start date of appointment
   * @param newStartTime New start time of appointment
   * @param currentDate current Date of the system
   * @param currentTime current time of the system
   * @return
   * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
   */
  // line 209 "../../../../../FlexiBookStateMachine.ump"
   public boolean isGoodForTimeUpdate(Date newDate, Time newStartTime, Date currentDate, Time currentTime){
    //--------------------------------- Implemented by AntoineW -----------------------------------------------------------------
		// get duration of the original service
		TimeSlot oldTimeSlot = getTimeSlot();
		Duration d = Duration.between(oldTimeSlot.getStartTime().toLocalTime(), oldTimeSlot.getEndTime().toLocalTime());
		// get the duration to set new end time. Since there is no change in combo item, the time is same
		int durationMinutes = (int) d.toMinutes();
		Time newEndTime = Time.valueOf(newStartTime.toLocalTime().plusMinutes(durationMinutes));


		TimeSlot timeSlot = new TimeSlot(newDate, newStartTime, newDate, newEndTime, getFlexiBook());
		int index = getFlexiBook().indexOfTimeSlot(timeSlot);
		int oldIndex = getFlexiBook().indexOfTimeSlot(oldTimeSlot);

		if (!isInGoodTiming(timeSlot, index, oldIndex, this.getAppointmentStatus(),currentDate, currentTime)) {
			getFlexiBook().removeTimeSlot(timeSlot);
			return false;
		}

	   
    //--------------------------------- Implemented by Mike Wang & -----------------------------------------------------------------
    List<TimeSlot> vacations = getFlexiBook().getBusiness().getVacation();
    List<TimeSlot> holidays = getFlexiBook().getBusiness().getHolidays();
    //check if overlapping with other appointment
		for(Appointment a : getFlexiBook().getAppointments()){
			if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate()) 
					&& getTimeSlot().getStartTime().before(a.getTimeSlot().getStartTime())  
					&& getTimeSlot().getEndTime().after(a.getTimeSlot().getStartTime())
					&& (getFlexiBook().getAppointments().indexOf(a) != getFlexiBook().getAppointments().indexOf(this))){
				return false;
			}
		}
	// check vacations
		for (TimeSlot vacation: vacations) {
			if(vacation.getStartDate().equals(getTimeSlot().getStartDate()) 
					&& vacation.getStartTime().before(getTimeSlot().getStartTime())
					&& vacation.getStartTime().after(getTimeSlot().getEndDate())) {
				return false;
			}
			
		}
	// check holidays 
		for (TimeSlot holiday: holidays) {
			if(holiday.getStartDate().equals(getTimeSlot().getStartDate()) 
					&& holiday.getStartTime().before(getTimeSlot().getStartTime())
					&& holiday.getStartTime().after(getTimeSlot().getEndDate())) {
				return false;
			}
			
		}
		return true;
  }


  /**
   * line 203 "../../../../../FlexiBookStateMachine.ump"
   */
  // line 264 "../../../../../FlexiBookStateMachine.ump"
   public boolean hasReachedStartTime(Date date, Time time){
    Time tempTime = getTimeSlot().getStartTime();
		boolean check = false;
		if ((time.after(tempTime) || time.equals(tempTime)) && date.equals(getTimeSlot().getStartDate())) {
			check = true;
		}
		return check;
  }


  /**
   * line 213 "../../../../../FlexiBookStateMachine.ump"
   */
  // line 275 "../../../../../FlexiBookStateMachine.ump"
   public boolean isOnSameDayAsAppointment(Date date){
    Date tempToday = getTimeSlot().getStartDate();
		boolean check = false; 
		if (date.equals(tempToday)) {
			check = true;
		}
		return check;
  }


  /**
   * line 223 "../../../../../FlexiBookStateMachine.ump"
   */
  // line 285 "../../../../../FlexiBookStateMachine.ump"
   public boolean isBeforeToday(Date date){
    Date tempToday = getTimeSlot().getStartDate();
	   boolean check = false;
	   if (date.before(tempToday)) {
		   check =true;
	   }
	   return check;
  }


  /**
   * 
   * Check if the request of updating a appointment's content is valid.
   * Option of updating appointment content please check doUpdateContent()<br>
   * 
   * This condition checks if the schedule is allowed to add or remove a service.
   * Also it make sure the service the customer what to change is not mandatory.<br>
   * 
   * @param action A string describing the way of update, can either be "add" or "remove"
   * @param optService name of the service to add or remove
   * @param currentDate Current date of the system
   * @param currentTime Current date of the system
   * @return Boolean whether the update request is valid. if false, no update will be performed
   * @see doUpdateContent(String action, String optService)
   * 
   * @author: Catherine, jedla, gtjarvis, mikewang, chengchen, AntoineW
   */
  // line 312 "../../../../../FlexiBookStateMachine.ump"
   public boolean isGoodForContentUpdate(String action, String optService, Date currentDate, Time currentTime){
    //--------------------------------- Implemented by AntoineW -----------------------------------------------------------------
		TimeSlot oldTimeSlot = getTimeSlot();

		// Scenario: check if the request on adding and removing is legitimate, aka can not remove a mandatory service
		if (action.equals("remove")) {
				if(findServiceCombo(this.getBookableService().getName()).getMainService().getService().getName().equals(optService)) {
					// bad request: cannot remove main service
					return false;
				}

			// since appInSystem.getChosenItems() is inmutable by umple, have to create a deep copy here to iterate
			List<ComboItem> copy= new ArrayList<ComboItem>();
			for(ComboItem item: getChosenItems()) {
				copy.add(item);
			}
			for(ComboItem item: copy) {
					if(item.getService().getName().equals(optService)) {
						if(item.getMandatory()) {
							
							return false;
							}

					}	
			}
		}else if (action.equals("add")) {
			// add: there is no restriction for adding option service other than time
			// thus do nothing in this part.
		}
		Service s = null;
	    for(BookableService service: getFlexiBook().getBookableServices()) {
	    	if(service.getName().equals(optService)) {
	    		s = (Service)service;
	    	}
	    }
		if(getBookableService() instanceof Service) {
			
			LocalTime aEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDuration());
			Time newEndTime  = Time.valueOf(aEndtime);
			TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), 
					newEndTime, getFlexiBook());
			int index = getFlexiBook().indexOfTimeSlot(timeSlot);
			int oldIndex = getFlexiBook().indexOfTimeSlot(oldTimeSlot);
			if (!isInGoodTiming(timeSlot, index, oldIndex, this.getAppointmentStatus(),currentDate, currentTime)) {
				getFlexiBook().removeTimeSlot(timeSlot);
				return false;
			}
			
		}else {

			int addOrMinusTime = 1;
			if (action.equals("remove")) {
				// remove means minus time since the slot is shorter
				addOrMinusTime = -1;
			}
			LocalTime aEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(addOrMinusTime*s.getDuration());
			Time newEndTime  = Time.valueOf(aEndtime);

			TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), 
						newEndTime, getFlexiBook());
			int index = getFlexiBook().indexOfTimeSlot(timeSlot);
			int oldIndex = getFlexiBook().indexOfTimeSlot(oldTimeSlot);
			
			if (!isInGoodTiming(timeSlot, index, oldIndex, this.getAppointmentStatus(),currentDate, currentTime)) {
				getFlexiBook().removeTimeSlot(timeSlot);
				// remove all newly added service since the time is not good
				// update fails, later return false
				for(ComboItem item: findServiceCombo(this.getBookableService().getName()).getServices()) {
					if(item.getService().getName().equals(optService)) {
						removeChosenItem(item);
					}
				}
				return false;
			}

		}
		
		// ----------------------------------------------  Implemented by Mike Wang, Chen Charles & ----------------------------------
	    List<TimeSlot> vacations = getFlexiBook().getBusiness().getVacation();
	    List<TimeSlot> holidaySlots = getFlexiBook().getBusiness().getHolidays();
		// 1. check with vacation
		for (TimeSlot vacation: vacations) {
			if(getBookableService() instanceof Service) {
				
				LocalDateTime vStart = ControllerUtils.combineDateAndTime(vacation.getStartDate(), vacation.getStartTime());
				LocalDateTime vEnd = ControllerUtils.combineDateAndTime(vacation.getEndDate(), vacation.getEndTime());
				
				LocalDateTime aStart = ControllerUtils.combineDateAndTime(this.getTimeSlot().getStartDate(), this.getTimeSlot().getStartTime());
				
				LocalDateTime aNewEnd = aStart.plusMinutes(s.getDuration());
				
				if(!(aNewEnd.isBefore(vStart) || vEnd.isBefore(aStart))) {
					return false;
				}
				
			}else if(getBookableService() instanceof ServiceCombo) {
				LocalDateTime vStart = ControllerUtils.combineDateAndTime(vacation.getStartDate(), vacation.getStartTime());
				LocalDateTime vEnd = ControllerUtils.combineDateAndTime(vacation.getEndDate(), vacation.getEndTime());
				
				LocalDateTime aStart = ControllerUtils.combineDateAndTime(this.getTimeSlot().getStartDate(), this.getTimeSlot().getStartTime());
				LocalDateTime aEnd = ControllerUtils.combineDateAndTime(this.getTimeSlot().getEndDate(), this.getTimeSlot().getEndTime());
				
				LocalDateTime aNewEnd = aEnd.plusMinutes(s.getDuration());
				
				if(!(aNewEnd.isBefore(vStart) || vEnd.isBefore(aStart))) {
					return false;
				}
			}
		}
		
		// 2. check with holiday
				for (TimeSlot holiday: holidaySlots) {
					if(getBookableService() instanceof Service) {
						
						LocalDateTime vStart = ControllerUtils.combineDateAndTime(holiday.getStartDate(), holiday.getStartTime());
						LocalDateTime vEnd = ControllerUtils.combineDateAndTime(holiday.getEndDate(), holiday.getEndTime());
						
						LocalDateTime aStart = ControllerUtils.combineDateAndTime(this.getTimeSlot().getStartDate(), this.getTimeSlot().getStartTime());
						
						LocalDateTime aNewEnd = aStart.plusMinutes(s.getDuration());
						
						if(!(aNewEnd.isBefore(vStart) || vEnd.isBefore(aStart))) {
							return false;
						}
						
					}else if(getBookableService() instanceof ServiceCombo) {
						LocalDateTime vStart = ControllerUtils.combineDateAndTime(holiday.getStartDate(), holiday.getStartTime());
						LocalDateTime vEnd = ControllerUtils.combineDateAndTime(holiday.getEndDate(), holiday.getEndTime());
						
						LocalDateTime aStart = ControllerUtils.combineDateAndTime(this.getTimeSlot().getStartDate(), this.getTimeSlot().getStartTime());
						LocalDateTime aEnd = ControllerUtils.combineDateAndTime(this.getTimeSlot().getEndDate(), this.getTimeSlot().getEndTime());
						
						LocalDateTime aNewEnd = aEnd.plusMinutes(s.getDuration());
						
						if(!(aNewEnd.isBefore(vStart) || vEnd.isBefore(aStart))) {
							return false;
						}
					}
				}
		
		return true;
  }


  /**
   * 
   * This method is a helper method determining the actual time of a appointment
   * It will only be used for a serviceCombo.<p>
   * This is implemented because customer can choose to not have certain optional services in a combo.
   * @return
   * 
   * @author p14
   */
  // line 464 "../../../../../FlexiBookStateMachine.ump"
   private static  int calcActualTimeOfAppointment(List<ComboItem> comboItemList){
    int actualTime = 0;

		for (ComboItem ci : comboItemList) {
			actualTime = actualTime + ci.getService().getDuration();
		}

		return actualTime;
  }


  /**
   * Helper method of finding a service combo
   * Copied from iter 2 controller
   */
  // line 476 "../../../../../FlexiBookStateMachine.ump"
   private ServiceCombo findServiceCombo(String name){
    for (BookableService bservice : getFlexiBook().getBookableServices()) {
			if (bservice.getName().equals(name) && bservice instanceof ServiceCombo) {
				return (ServiceCombo)bservice;
			}
		}
		return null;
  }


  /**
   * 
   * wrapper method of isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot), isDuringDowntime(TimeSlot timeSlot), 
   * isDuringWorkTime(TimeSlot timeSlot) and isInTheFuture(TimeSlot timeSlot)
   * The method will return true if the timeslot passes through all 4 tests in a specific order.
   * Note some changes are made and Here it takes in more parameters like AppointmentStatus status,Date currentDate, Time currentTime
   * All changes are to accomodate the isInTheFuture() method
   * @return
   * 
   * @author p14
   */
  // line 496 "../../../../../FlexiBookStateMachine.ump"
   private boolean isInGoodTiming(TimeSlot timeSlot, int index, int oldIndex, AppointmentStatus status, Date currentDate, Time currentTime){
    // here handle Scenario: A customer attempts to make various invalid appointments for services
		// there are three time constraints to check:
		// 1. if in the business time, if not, fail directly
		// 		2. if overlap with other time slot (other appointment/vacation/holiday). if there is overlap, we check the downtime!
		// 		3. if not in the downtime of other app, fail
		if (!isDuringWorkTime(timeSlot)) {
			return false;
		}else {
			if(!isNotOverlapWithOtherTimeSlots (timeSlot, index, oldIndex)) {
				if (!isDuringDowntime(timeSlot)) {
					return false;
				}
			}
		}

		// Make sure appointment is made in the future not in the past
		
		if(status != AppointmentStatus.InProgress) {
		if (!isInTheFuture(timeSlot,currentDate,currentTime)) {
			return false;
		}
		}
		return true;
  }


  /**
   * 
   * Check if the time slot overlaps with other appointment
   * solves constraint: checks whether there is no overlap between two time slots
   * @author p14
   */
  // line 527 "../../../../../FlexiBookStateMachine.ump"
   private boolean isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot, int index, int oldIndex){
    FlexiBook flexiBook = getFlexiBook();
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(timeSlot.getEndDate(), timeSlot.getEndTime());

		boolean isTheCase = true;

		for (TimeSlot ts :flexiBook.getTimeSlots()){
			LocalDateTime tsStart = ControllerUtils.combineDateAndTime(ts.getStartDate(), ts.getStartTime());
			LocalDateTime tsEnd = ControllerUtils.combineDateAndTime(ts.getEndDate(), ts.getEndTime());


			if(timeSlotEnd.isBefore(tsStart) || tsEnd.isBefore(timeSlotStart) || timeSlotEnd.equals(tsStart)||tsEnd.equals(timeSlotStart) ||
					flexiBook.getTimeSlots().indexOf(ts) ==  index ||
					flexiBook.getTimeSlots().indexOf(ts) ==  oldIndex) {
				isTheCase = true;
			}else {
				isTheCase = false;
				break;
			}
		}		
		return isTheCase;
  }


  /**
   * 
   * appointments do not overlap UNLESS the overlap is during the downtime;
   * @param timeSlot
   * @return
   * @author p14
   */
  // line 558 "../../../../../FlexiBookStateMachine.ump"
   private boolean isDuringDowntime(TimeSlot timeSlot){
    // Initially false, if there is a downtime period completely contains a timeslot
		// then will be turned true
		boolean isDuringDowntime = false;

		FlexiBook flexiBook = getFlexiBook();

		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(timeSlot.getEndDate(), timeSlot.getEndTime());

		for (Appointment app: flexiBook.getAppointments()) {

			List<TOTimeSlot> tsList = ControllerUtils.getDowntimesByAppointment(app);
			for(TOTimeSlot TOTs: tsList) {
				LocalDateTime tsStart = ControllerUtils.combineDateAndTime(TOTs.getStartDate(), TOTs.getStartTime());
				LocalDateTime tsEnd = ControllerUtils.combineDateAndTime(TOTs.getEndDate(), TOTs.getEndTime());

				if((timeSlotStart.isAfter(tsStart)||timeSlotStart.equals(tsStart))
						&& (timeSlotEnd.isBefore(tsEnd)||timeSlotEnd.equals(tsEnd))) {
					isDuringDowntime = true;
					break;
				}
			}		
		}
		return isDuringDowntime;
  }


  /**
   * 
   * appointment cannot be made on holidays or during vacation
   * @param timeSlot
   * @return
   * @author p14
   */
  // line 593 "../../../../../FlexiBookStateMachine.ump"
   private boolean isDuringWorkTime(TimeSlot timeSlot){
    boolean isDuringWorkTime = false;

		//First get the weekday
		DayOfWeek dOfWeek = ControllerUtils.getDoWByDate(timeSlot.getStartDate());
		// then check all businessHour list
		List<BusinessHour> bhList =getFlexiBook().getBusiness().getBusinessHours();
		for(BusinessHour bh: bhList) {
			// check weekday

			if(dOfWeek .equals(bh.getDayOfWeek())) {
				// if the appointment is on that day, compare if the time slot is included by business hour
				if((timeSlot.getStartTime().toLocalTime().isAfter(bh.getStartTime().toLocalTime())
						|| timeSlot.getStartTime().toLocalTime().equals(bh.getStartTime().toLocalTime()))
						&&
						timeSlot.getEndTime().toLocalTime().isBefore(bh.getEndTime().toLocalTime())
						|| timeSlot.getEndTime().toLocalTime().equals(bh.getEndTime().toLocalTime())) {
					isDuringWorkTime = true;
					break;
				}
			}

		}
		return isDuringWorkTime;
  }


  /**
   * 
   * Check if the appointment is made before now (which is not allowed)
   * @param timeSlot
   * @return
   * 
   * @author p14
   */
  // line 626 "../../../../../FlexiBookStateMachine.ump"
   private boolean isInTheFuture(TimeSlot timeSlot, Date currentDate, Time currentTime){
    boolean isInFuture = true;
		LocalDateTime now = ControllerUtils.combineDateAndTime(currentDate, currentTime);

		LocalDateTime appointmentDateTime = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		if(appointmentDateTime.isBefore(now)) {
			isInFuture = false;
		}

		return isInFuture;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 20 "../../../../../FlexiBookPersistence.ump"
  private static final long serialVersionUID = -1782000978128890763L ;

  
}