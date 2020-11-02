/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.flexibook.model;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import java.util.*;

// line 1 "../../../../../FlexiBookStateMachine.ump"
// line 87 "../../../../../FlexiBook.ump"
public class Appointment
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
        if (!(SameDay(currentDate)))
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

  public boolean updateAppointmentTime(Date newDate,Time newStartTime,Date currentDate)
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (isInGoodTimeSlot()&&!(SameDay(currentDate)))
        {
        // line 15 "../../../../../FlexiBookStateMachine.ump"
          updateTime(newDate , newStartTime);
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
        if (isInGoodTimeSlotForUpdate(optService)&&!(SameDay(currentDate)))
        {
        // line 18 "../../../../../FlexiBookStateMachine.ump"
          updateContent(action, optService);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        if (isInGoodTimeSlotForUpdate(optService))
        {
        // line 30 "../../../../../FlexiBookStateMachine.ump"
          updateContent(action, optService);
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
        if (goodStartTime(currentDate,currentTime))
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
        // line 23 "../../../../../FlexiBookStateMachine.ump"
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
        // line 38 "../../../../../FlexiBookStateMachine.ump"
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

  // line 48 "../../../../../FlexiBookStateMachine.ump"
   public void updateTime(Date newDate, Time newStartTime){
    // get duration of the original service
		TimeSlot oldTimeSlot = getTimeSlot();
		Duration d = Duration.between(oldTimeSlot.getStartTime().toLocalTime(), oldTimeSlot.getEndTime().toLocalTime());
		// get the duration to set new end time. Since there is no change in combo item, the time is same
		int durationMinutes = (int) d.toMinutes();
		Time newEndTime = Time.valueOf(newStartTime.toLocalTime().plusMinutes(durationMinutes));
		TimeSlot timeSlot = new TimeSlot(newDate, newStartTime, newDate, newEndTime, getFlexiBook());
		setTimeSlot(timeSlot);
  }

  // line 60 "../../../../../FlexiBookStateMachine.ump"
   public void updateContent(String action, String optService){
    if (isInGoodTimeSlotForUpdate(optService)) {
    if(getBookableService() instanceof ServiceCombo) {
    	
    		
    	
    	TimeSlot oldTimeSlot = getTimeSlot();
    	ServiceCombo sc = (ServiceCombo)getBookableService();
    	List<String> serviceNameList = ControllerUtils.parseString(optService, ",");
		List<ComboItem> newlyAddedItem = new ArrayList<ComboItem>();
		
		if (action.equals("remove")) {

			// since appInSystem.getChosenItems() is inmutable by umple, have to create a deep copy here to iterate
			List<ComboItem> copy= new ArrayList<ComboItem>();
			for(ComboItem item: getChosenItems()) {
				copy.add(item);
			}
			for(ComboItem item: copy) {
				for (String name: serviceNameList) {
					// When we search that there is a name mentioned in the request
					// we check if it is mandatory or main service.
					// we can only removed it if the above two conditions are both wrong.
					if(item.getService().getName().equals(name)) {
						if(!item.getMandatory() && !name.equals(sc.getMainService().getService().getName())) {
							removeChosenItem(item);
						}
					}	
				}
			}
		}else if(action.equals("add")) {
				List<ComboItem> newListComboItem = new ArrayList<ComboItem>();
			
			for(ComboItem item: sc.getServices()) {
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
						for(String name: serviceNameList) {
							if(name.equals(item.getService().getName())) {
								newListComboItem.add(item);
							}
							
						}
						
					}
					
					
				}
			}
			
			this.chosenItems = newListComboItem;

			
			int newDuration = calcActualTimeOfAppointment(getChosenItems());
			Time newEndTime = Time.valueOf(oldTimeSlot.getStartTime().toLocalTime().plusMinutes(newDuration));
			TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), 
					newEndTime, getFlexiBook());
			
			setTimeSlot(timeSlot);
			

			
		}
		  
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
  }

  // line 158 "../../../../../FlexiBookStateMachine.ump"
   public void incrementNoShow(){
    int noShowCount = this.getCustomer().getNoShowCount();
		noShowCount++;
		this.getCustomer().setNoShowCount(noShowCount);
  }

  // line 165 "../../../../../FlexiBookStateMachine.ump"
   public boolean isInGoodTimeSlot(){
    boolean check = true;
		for(Appointment a : getFlexiBook().getAppointments()){
			if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate()) && getTimeSlot().getStartTime().before(a.getTimeSlot().getStartTime())  && getTimeSlot().getEndTime().after(a.getTimeSlot().getStartTime())){
				check = false;
			}
		}
		return check;
  }

  // line 176 "../../../../../FlexiBookStateMachine.ump"
   public boolean goodStartTime(Date date, Time time){
    Time tempTime = getTimeSlot().getStartTime();
		boolean check = false;
		if ((time.after(tempTime) || time.equals(tempTime)) && date.equals(getTimeSlot().getStartDate())) {
			check = true;
		}
		return check;
  }

  // line 186 "../../../../../FlexiBookStateMachine.ump"
   public boolean SameDay(Date date){
    Date tempToday = getTimeSlot().getStartDate();
		boolean check = false; 
		if (date.equals(tempToday)) {
			check = true;
		}
		return check;
  }

  // line 197 "../../../../../FlexiBookStateMachine.ump"
   public boolean isInGoodTimeSlotForUpdate(String optService){
//	   boolean available = false; //if true, then isInGoodTimeSlotForUpdate returns true
//	   //get day of appointment
//	   Date appointmentDay = this.getTimeSlot().getStartDate(); //assuming start date and end date are always the same
//	   //figure out downtimes later
//	   
//	   //find bookable service optService
//	   Service newOptService = null; //service being added/switched out
//	   for(BookableService service: getFlexiBook().getBookableServices()) {
//	    	if(service.getName().equals(optService)) {
//	    		newOptService = (Service)service;
//	    	}
//	    } 
//	   
//	   //get new endtime
//	   //if switching out single service
//	   if (getBookableService() instanceof Service) {
//		   LocalTime anEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(newOptService.getDuration());
//		   Time newEndTime  = Time.valueOf(anEndtime);
//	   }
//	
//	   //if adding a service to a combo
//	   if (getBookableService() instanceof ServiceCombo) {
//		   LocalTime anEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(newOptService.getDuration());
//			Time newEndTime  = Time.valueOf(anEndtime);
//	   }
//	   
//
//	   //check if day is a holiday
//		List<TimeSlot> holidays = new ArrayList<TimeSlot>();
//		for (TimeSlot holiday : FlexiBookApplication.getFlexiBook().getTimeSlots()) {
//			if (app.getCustomer().getUsername().equals(username)){
//				appointments.add(app);
//			}
//		}
//		return appointments;
//	   
//	   //check if day is a vacation
//	   		//if vacation is a half day, then check if overlap
//	   //check if outside business hours
//	   //check if overlaps with another appointment
//	   //if passes all of these, then boolean true
	   
	   
    boolean check = true;
	    Service s = null;
	    List<TimeSlot> vacations = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();
	    List<TimeSlot> holidaySlots = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
	    for(BookableService service: getFlexiBook().getBookableServices()) {
	    	if(service.getName().equals(optService)) {
	    		s = (Service)service;
	    	}
	    }
		for(Appointment a : getFlexiBook().getAppointments()){
//			for (TimeSlot vacation: vacations) {
//				
//			}
//			for (TimeSlot holiday: holidaySlots) {
//				
//			}
			for (TimeSlot vacation: vacations) {
				for (TimeSlot holiday: holidaySlots) {
				if (vacation.getStartDate().after(getTimeSlot().getStartDate())) {
					if (holiday.getStartDate().after(getTimeSlot().getStartDate())) {
						// only check appointment on the same day
						if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate())&& 
								getFlexiBook().getAppointments().indexOf(a) != getFlexiBook().getAppointments().indexOf(this)) {
							// if single service: replace the endtime with new duration
							if(getBookableService() instanceof Service) {
								LocalTime aEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDuration());
								Time newEndTime  = Time.valueOf(aEndtime);
								if(newEndTime.after(a.getTimeSlot().getStartTime())) {
									check = false;
								}
							// if service combo, meaning we are appending something, we add to the endtime
							}else if(getBookableService() instanceof ServiceCombo) {
								LocalTime aEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(s.getDuration());
								Time newEndTime  = Time.valueOf(aEndtime);
								if(newEndTime.after(a.getTimeSlot().getStartTime())) {
									check = false;
								}
								
							}
							
						}
					}else if(holiday.getStartDate().equals(getTimeSlot().getStartDate())){
						// only check appointment on the same day
						if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate())&& 
								getFlexiBook().getAppointments().indexOf(a) != getFlexiBook().getAppointments().indexOf(this)) {
							// if single service: replace the endtime with new duration
							if(getBookableService() instanceof Service) {
								LocalTime aEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDuration());
								Time newEndTime  = Time.valueOf(aEndtime);
								if(newEndTime.after(a.getTimeSlot().getStartTime()) || newEndTime.after(holiday.getStartTime())) {
									check = false;
								}
							// if service combo, meaning we are appending something, we add to the endtime
							}else if(getBookableService() instanceof ServiceCombo) {
								LocalTime aEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(s.getDuration());
								Time newEndTime  = Time.valueOf(aEndtime);
								if(newEndTime.after(a.getTimeSlot().getStartTime()) || newEndTime.after(holiday.getStartTime())) {
									check = false;
								}
								
							}
							
						}
					}
				}else if(vacation.getStartDate().equals(getTimeSlot().getStartDate())){
					// only check appointment on the same day
					if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate())&& 
							getFlexiBook().getAppointments().indexOf(a) != getFlexiBook().getAppointments().indexOf(this)) {
						// if single service: replace the endtime with new duration
						if(getBookableService() instanceof Service) {
							LocalTime aEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDuration());
							Time newEndTime  = Time.valueOf(aEndtime);
							if(newEndTime.after(a.getTimeSlot().getStartTime()) || newEndTime.after(vacation.getStartTime())) {
								check = false;
							}
						// if service combo, meaning we are appending something, we add to the endtime
						}else if(getBookableService() instanceof ServiceCombo) {
							LocalTime aEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(s.getDuration());
							Time newEndTime  = Time.valueOf(aEndtime);
							if(newEndTime.after(a.getTimeSlot().getStartTime())|| newEndTime.after(vacation.getStartTime())) {
								check = false;
							}
							
						}
						
					}
				}
//			// only check appointment on the same day
//			if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate())&& 
//					getFlexiBook().getAppointments().indexOf(a) != getFlexiBook().getAppointments().indexOf(this)) {
//				// if single service: replace the endtime with new duration
//				if(getBookableService() instanceof Service) {
//					LocalTime aEndtime = getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDuration());
//					Time newEndTime  = Time.valueOf(aEndtime);
//					if(newEndTime.after(a.getTimeSlot().getStartTime())) {
//						check = false;
//					}
//				// if service combo, meaning we are appending something, we add to the endtime
//				}else if(getBookableService() instanceof ServiceCombo) {
//					LocalTime aEndtime = getTimeSlot().getEndTime().toLocalTime().plusMinutes(s.getDuration());
//					Time newEndTime  = Time.valueOf(aEndtime);
//					if(newEndTime.after(a.getTimeSlot().getStartTime())) {
//						check = false;
//					}
//					
//				}
//				
//			}
		}
			}
		}
		return check;
  }

  // line 312 "../../../../../FlexiBookStateMachine.ump"
   private static  int calcActualTimeOfAppointment(List<ComboItem> comboItemList){
    int actualTime = 0;

		for (ComboItem ci : comboItemList) {
			actualTime = actualTime + ci.getService().getDuration();
		}

		return actualTime;
  }

}