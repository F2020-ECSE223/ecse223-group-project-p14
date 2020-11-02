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
      List<String> serviceNameList = ControllerUtils.parseString(optService, ",");
      for(String serviceName: serviceNameList){
        for(BookableService service: getFlexiBook().getBookableServices()) {
          if(service.getName().equals(serviceName)) {
            if(service instanceof Service){
              setBookableService(service);
            } else {
              ServiceCombo sc = (ServiceCombo)getBookableService();
              ComboItem ci = new ComboItem(true, , sc)
              addChosenItem();
            }
          }
        }
      }
      
   		List<TimeSlot> completeTimeSlots = new ArrayList<TimeSlot>();
    	boolean check = true;
	    List<TimeSlot> validTimeSlots = new ArrayList<TimeSlot>();
	    Time t1 = Time.valueOf("00:00:00");
	    Time t2 = Time.valueOf("24:00:00");
	    validTimeSlots.add(new TimeSlot(getTimeSlot().getStartDate(), t1, getTimeSlot().getStartDate(), t2, flexiBook));
	    List<TimeSlot> vacationSlots = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();
	    List<TimeSlot> holidaySlots = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
	    List<TimeSlot> appointmentSlots = new ArrayList<TimeSlot>();
	    for(BusinessHour b: flexiBook.getHours()){
			 if(b.getDayOfWeek().equals(timeSlot.getStartDate().getDay())){
				TimeSlot businessSlot = new TimeSlot(timeSlot.getStartDate(), b.getStartTime(), timeSlot.getStartDate(), b.getEndTime(), flexiBook);
				TimeSlot businessSlotStart = new TimeSlot(timeSlot.getStartDate(), t1, timeSlot.getStartDate(), timeSlot.getStartTime(), flexiBook);
	    		TimeSlot businessSlotEnd = new TimeSlot(timeSlot.getStartDate(), businessSlot.getEndTime(), timeSlot.getStartDate(), t2, flexiBook);	    
				completeTimeSlots.add(businessSlotStart);
				completeTimeSlots.add(businessSlotEnd);
			}
		}
	    for(Appointment a: flexiBook.getAppointments()){
	    	if(a.getBookableService() instanceof Service){
	    		Service s = (Service)a.getBookableService();
				if(s.getDowntimeDuration() == 0){
					appointmentSlots.add(a.getTimeSlot());
				} else {
					LocalTime t1EndLocalTime = a.getTimeSlot().getStartTime().toLocalTime().plusMinutes(s.getDowntimeStart());
					Time t1End  = Time.valueOf(t1EndLocalTime);
					TimeSlot t_1 = new TimeSlot(a.getTimeSlot().getStartDate(), a.getTimeSlot().getStartTime(), a.getTimeSlot().getStartDate(), t1End, flexiBook);
					LocalTime t2StartLocalTime = t1End.toLocalTime().plusMinutes(s.getDuration());
					Time t2Start  = Time.valueOf(t2StartLocalTime);
					TimeSlot t_2 = new TimeSlot(a.getTimeSlot().getStartDate(), t2Start, a.getTimeSlot().getStartDate(), a.getTimeSlot().getEndTime(), flexiBook);
					appointmentSlots.add(t_1);
					appointmentSlots.add(t_2);
				}
			} else {
				ServiceCombo sc = (ServiceCombo)a.getBookableService();
				int currentTime = 0;
				for(ComboItem c: sc){
					Service s = (Service)c.getService();
					if(s.getDowntimeDuration() == 0){
						LocalTime tStartLocalTime = a.getTimeSlot().getStartTime().toLocalTime().plusMinutes(currentTime);
						Time tStart = Time.valueOf(tStartLocalTime);
						LocalTime tEndLocalTime = tStart.toLocalTime().plusMinutes(s.getDuration());
						Time tEnd = Time.valueOf(tEndLocalTime);
						appointmentSlots.add(new TimeSlot(a.getTimeSlot().getStartDate(), tStart, a.getTimeSlot().getStartDate(), tEnd, flexiBook));
					} else {
						LocalTime t1StartLocalTime = a.getTimeSlot().getStartTime().toLocalTime().plusMinutes(currentTime);
						Time t1Start  = Time.valueOf(t1StartLocalTime);
						LocalTime t1EndLocalTime = t1Start.toLocalTime().plusMinutes(s.getDowntimeStart());
						Time t1End = Time.valueOf(t1EndLocalTime);
						TimeSlot t_1 = new TimeSlot(a.getTimeSlot().getStartDate(), t1Start, a.getTimeSlot().getStartDate(), t1End, flexiBook);
						LocalTime t2StartLocalTime = t1End.toLocalTime().plusMinutes(s.getDowntimeDuration());
						Time t2Start  = Time.valueOf(t2StartLocalTime);
						LocalTime t2EndLocalTime = t1Start.toLocalTime().plusMinutes(s.getDuration());
						Time t2End = Time.valueOf(t2EndLocalTime);
						TimeSlot t_2 = new TimeSlot(a.getTimeSlot().getStartDate(), t2Start, a.getTimeSlot().getStartDate(), t2End, flexiBook);
						appointmentSlots.add(t_1);
						appointmentSlots.add(t_2);
					}
					currentTime += s.getDuration();
				}

			}
			
		}
		completeTimeSlots.addAll(vacationSlots);
		completeTimeSlots.addAll(holidaySlots);
		completeTimeSlots.addAll(appointmentSlots);

		List<TimeSlots> ourAppointmentSlots = new ArrayList<TimeSlot>();


	    for(BookableService service: getFlexiBook().getBookableServices()) {
	    	if(service.getName().equals(optService)) {
	    		break;
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