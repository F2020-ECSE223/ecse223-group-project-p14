/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/


import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;

// line 1 "FlexiBookStateMachine.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment State Machines
  public enum AppointmentStatus { Booked, InProgress, FinalState }
  private AppointmentStatus appointmentStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment()
  {
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

  public boolean cancelAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        setAppointmentStatus(AppointmentStatus.FinalState);
        wasEventProcessed = true;
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
        // line 15 "FlexiBookStateMachine.ump"
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
        if (isInGoodTimeSlot()&&!(SameDay(currentDate)))
        {
        // line 18 "FlexiBookStateMachine.ump"
          updateContent(action, optService);
          setAppointmentStatus(AppointmentStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        if (isInGoodTimeSlot())
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

  public boolean startAppointment()
  {
    boolean wasEventProcessed = false;
    
    AppointmentStatus aAppointmentStatus = appointmentStatus;
    switch (aAppointmentStatus)
    {
      case Booked:
        if (goodStartTime(currentTime))
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
      case InProgress:
        // line 29 "FlexiBookStateMachine.ump"
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
        // line 37 "FlexiBookStateMachine.ump"
        this.delete();
        break;
    }
  }

  public void delete()
  {}

  // line 47 "FlexiBookStateMachine.ump"
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

  // line 61 "FlexiBookStateMachine.ump"
   public void updateContent(String action, String optService){
    if(getBookableService() instanceof ServiceCombo) {
		  
		  try {
			FlexiBookController.updateAppointmentContent(this.getBookableService().getName(), 
					  this.getTimeSlot().getStartDate(), this.getTimeSlot().getStartTime(), action, optService);
		} catch (Exception e) {
			// should be exception here since there are already checking condition
			e.printStackTrace();
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

  // line 97 "FlexiBookStateMachine.ump"
   public void incrementNoShow(){
    int noShowCount = this.getCustomer().getNoShowCount();
		noShowCount++;
		this.getCustomer().setNoShowCount(noShowCount);
  }

  // line 103 "FlexiBookStateMachine.ump"
   public boolean isInGoodTimeSlot(){
    boolean check = true;
		for(Appointment a : getFlexiBook().getAppointments()){
			if(a.getTimeSlot().getStartDate().equals(getTimeSlot().getStartDate()) && getTimeSlot().getStartTime().before(a.getTimeSlot().getStartTime())  && getTimeSlot().getEndTime().after(a.getTimeSlot().getStartTime())){
				check = false;
			}
		}
		return check;
  }

  // line 113 "FlexiBookStateMachine.ump"
   public boolean goodStartTime(Time time){
    Time tempTime = getTimeSlot().getStartTime();
		boolean check = false;
		if (time.after(tempTime)) {
			check = true;
		}
		return check;
  }

  // line 122 "FlexiBookStateMachine.ump"
   public boolean SameDay(Date date){
    Date tempToday = getTimeSlot().getStartDate();
		boolean check = false; 
		if (date.euqals(tempToday)) {
			check = true;
		}
		return check;
  }

}