package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.model.*;


public class FlexiBookController {
	
	public FlexiBookController() {
		
	}
	
	/* This method adds a service to the database
	 * @param name - name of the service to be added
	 * @param durantion - duration of the service to be added
	 * @param downtimeDuration - duration of the downtime of the service to be added
	 * @param downtimeStart - the start time of the downtime of the service to be added
	 *
	 */
	public static void addService(String name, int duration, int downtimeDuration, int downtimeStart) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		try {
			BookableService aBookableService = new Service(name, flexiBook, duration, downtimeDuration, downtimeStart);
			flexiBook.addBookableService(aBookableService);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}
	
	
	/*
	 * This method remove the service with specified name from the database
	 * @param name - name of the service to be removed
	 */
	public static void removeService(String name) throws InvalidInputException{
		BookableService bookableService = findBookableService(name);
		if (bookableService!= null) {
			bookableService.delete();
		}
		
	}
	
	
	/*
	 * This method update the attributes of the specified service
	 * @param name - name of the service to be updated
	 * @param durantion - duration of the service to be updated
	 * @param downtimeDuration - duration of the downtime of the service to be updated
	 * @param downtimeStart - the start time of the downtime of the service to be updated
	 * 
	 */
	public static void updateService(String name, int duration, int downtimeDuration, int downtimeStart) {
		BookableService bookableService = findBookableService(name);
		if (bookableService!= null) {
			Service service = (Service) bookableService;
			service.setDuration(duration);
			service.setName(name);
			service.setDowntimeStart(downtimeStart);
			service.setDowntimeDuration(downtimeDuration);
		}
	
	}
	
	
	/**
	 * @author AntoineW
	 */
	public static void addAppointmentForService(String serviceName, Date date, Time time, boolean isSingleService) 
			throws InvalidInputException{
		
		User user = FlexiBookApplication.getCurrentLoginUser();
		// Scenario: The owner attempts to make an appointment
		if(user instanceof Owner) {
			throw new InvalidInputException("An owner cannot make an appointment");
		}else if (user == null) {
			throw new InvalidInputException("An User is not logged in");
		}
		
		Service s = findSingleService(serviceName);
		if(s == null) {
			throw new InvalidInputException("No such single service exist!");
		}
		
		
		
		LocalTime aEndtime = time.toLocalTime().plusMinutes(s.getDuration());
		Time endTime = Time.valueOf(aEndtime);
		
		// Here handle constraints: start and end date of an appointment have to be the same
		TimeSlot timeSlot = new TimeSlot(date, time, date, endTime, 
				FlexiBookApplication.getFlexiBook());
		// here handle Scenario: A customer attempts to make various invalid appointments for services
		if(!isNotOverlapWithOtherTimeSlots (timeSlot)) {
			if (!isDuringDowntime(timeSlot)) {
				throw new InvalidInputException("There are no available slots for " + serviceName + " on "
						+ date + " at " + time);
				}
		}
		
		//@ TODO
		// need to check if it is in on the workday and in the business time schedule
		
	}
	
	/**
	 * 
	 * @param serviceName
	 * @param date
	 * @param time
	 * @param isSingleService
	 * @throws InvalidInputException
	 * @author AntoineW
	 */
	public static void addAppointmentForComboService(String serviceName, Date date, Time time, boolean isSingleService) 
			throws InvalidInputException{
	
		
		//@ TODO
		// This is for the combo
		
	}
	
	/**
	 * This method creates a new customer account when a customer signs up
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 * @author Catherine
	 */
	public static void signUpCustomer(String username, String password) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		try {
			Customer aCustomer = new Customer(username, password, flexiBook);
			flexiBook.addCustomer(aCustomer); //@ TODO check if this is necessary or if new Customer does it already
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		// @ TODO may need if statements to capture all edge cases: 
		// add code to capture incomplete form
		// add code to capture if owner is logged in when customer account is trying to be made (use getCurrentLoginUser)
		// may need to overwrite error messages, as described in feature description
	}
	
	/**
	 * This method updates the username and/or password for a customer account, 
	 * or the password for an owner account
	 * @param currentUsername
	 * @param newUsername
	 * @param newPassword
	 * @throws InvalidInputException
	 * @author Catherine
	 */
	public static void updateUserAccount(String currentUsername, String newUsername, String newPassword) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); //this app
		User user = FlexiBookApplication.getCurrentLoginUser(); //this user
		//check if current username is the same as currently logged in
		if (user.getUsername() != currentUsername) {
			throw new InvalidInputException("You do not have permission to update this account"); //technically not in scope of feature
		}
		// check if newusername is null/empty --> if yes, throw error else continue	
		if (newUsername == null || newUsername == "") {
			throw new InvalidInputException("The user name cannot be empty");
		}
		// check if currentUsername == owner && newUsername != owner --> cant change owner username else continue
		if (currentUsername == "owner" && newUsername != "owner") {
			throw new InvalidInputException("Changing username of owner is not allowed");
		}
		// check if password is null/empty --> if yes, throw error else continue
		if (newPassword == null || newPassword == "") {
			throw new InvalidInputException("The password cannot be empty");
		}
		// check if username already used --> if yes, throw error else continue
		// this one may already be covered in the setUsername method. @ TODO check if setUsername covers this case
		if (flexiBook.getCustomers().stream().anyMatch(p -> p.getUsername().equals(newUsername))) { //this is a crazy line
		//if (user.hasWithUsername(newUsername)){ //can maybe use this instead! it's simpler!
			throw new InvalidInputException("Username not available");
		}
		// if it gets to here, then just update username and password!
		user.setUsername(newUsername);
		user.setPassword(newPassword);
		//FlexiBookApplication.setCurrentLoginUser(user); //pretty sure this isn't needed

	}
	/**
	 * This method deletes the current customer's account so their personal information is deleted
	 * @param username
	 * @throws InvalidInputException
	 * @author Catherine
	 */
	public static void deleteCustomerAccount(String username) throws InvalidInputException{ //maybe this should take a user as param and not username?
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); //this app
		User user = FlexiBookApplication.getCurrentLoginUser(); //this user
		// check if customer to be deleted is currently logged in
		// add code to stop owner from deleting account
		if (user.getUsername() != username || user.getUsername() == "owner" || user instanceof Owner) { //definitely some overlap here
			throw new InvalidInputException("You do not have permission to delete this account");
		}
		// delete customer account and associated appointments (captured already in Customer)
		((Customer)user).delete(); // I want to use the customer delete method
		// log out
		FlexiBookApplication.clearCurrentLoginUser();
	}
	

/*----------------------------------------------- Query methods --------------------------------------------------------------*/
	
	
	
	
	
	
	
/*----------------------------------------------- private helper methods -----------------------------------------------------*/
	
	/*
	 * This method finds the service with specified name
	 * @param name - the name of the service to found 
	 * @return the service found 
	 */
	private static BookableService findBookableService(String name) {
		BookableService foundBookableService = null;
		for (BookableService service : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (service.getName() == name) {
				foundBookableService = service;
				break;
			}
		}
		return foundBookableService;
	}

	/**
	 * @author AntoineW
	 */
	private static Service findSingleService(String name) {
		Service s = null;
		for (BookableService bservice : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (bservice.getName() == name && bservice instanceof Service) {
				s = (Service)bservice;
				break;
			}
		}
		return s;
	}

	


	/**
	 * Check if the time slot overlaps with other appointment
	 * solves constraint: checks whether there is no overlap between two time slots
     * @author AntoineW
	 */
	private static boolean isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot) {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(timeSlot.getEndDate(), timeSlot.getEndTime());
		
		boolean isTheCase = true;
		
		for (TimeSlot ts :flexiBook.getTimeSlots()){
			LocalDateTime tsStart = ControllerUtils.combineDateAndTime(ts.getStartDate(), ts.getStartTime());
			LocalDateTime tsEnd = ControllerUtils.combineDateAndTime(ts.getEndDate(), ts.getEndTime());
			
			if(timeSlotEnd.isBefore(tsStart) || tsEnd.isBefore(timeSlotStart)) {
				isTheCase = true;
			}else {
				isTheCase = false;
				break;
			}
		}		
		return isTheCase;
	}
	
	/**
	 * appointments do not overlap UNLESS the overlap is during the downtime;
	 * @param timeSlot
	 * @return
	 * @author AntoineW
	 */
	private static boolean isDuringDowntime(TimeSlot timeSlot) {
		
		// Initially false, if there is a downtime period completely contains a timeslot
		// then will be turned true
		boolean isDuringDowntime = false;
		
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(timeSlot.getEndDate(), timeSlot.getEndTime());
		
		for (Appointment app: flexiBook.getAppointments()) {
			
			List<TOTimeSlot> tsList = ControllerUtils.getDowntimesByAppointment(app);
			
			for(TOTimeSlot TOTs: tsList) {
				LocalDateTime tsStart = ControllerUtils.combineDateAndTime(TOTs.getStartDate(), TOTs.getStartTime());
				LocalDateTime tsEnd = ControllerUtils.combineDateAndTime(TOTs.getEndDate(), TOTs.getEndTime());
				
				if(timeSlotStart.isAfter(tsStart) && timeSlotEnd.isBefore(tsEnd)) {
					isDuringDowntime = true;
					break;
				}
			}		
		}
		return isDuringDowntime;
	}
	
}
