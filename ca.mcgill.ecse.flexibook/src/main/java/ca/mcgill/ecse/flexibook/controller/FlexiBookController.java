package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.model.*;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;


public class FlexiBookController {
	
	public FlexiBookController() {
		
	}
	
	/** 
	 * 
	 * This method adds a service to the database
	 * @param name - name of the service to be added
	 * @param durantion - duration of the service to be added
	 * @param downtimeDuration - duration of the downtime of the service to be added
	 * @param downtimeStart - the start ti
	 * me of the downtime of the service to be added
	 * 
	 * @author chengchen
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
	
	
	/**
	 * 
	 * This method removes the service with specified name from the database
	 * @param name - name of the service to be removed
	 * 
	 * @author chengchen
	 */
	public static void removeService(String name) throws InvalidInputException{
		BookableService bookableService = findBookableService(name);
		if (bookableService!= null) {
			bookableService.delete();
		}
		
	}
	
	
	/**
	 * 
	 * This method updates the attributes of the specified service
	 * @param name - name of the service to be updated
	 * @param durantion - duration of the service to be updated
	 * @param downtimeDuration - duration of the downtime of the service to be updated
	 * @param downtimeStart - the start time of the downtime of the service to be updated
	 * 
	 * @author chengchen
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
	 * This method handle adding appointment for a single service.
	 * (there will be a wrapper method to add a general BookableService,
	 * but to fit the UI design, kept this method public for direct use)
	 * @param serviceName
	 * @param date
	 * @param time
	 * @throws InvalidInputException
	 * 
	 * @author AntoineW
	 */
	public static void addAppointmentForService(String serviceName, Date date, Time time) 
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
		
		if(!isInGoodTiming(timeSlot)) {
			// the added timeslot is not good. So we remove it because the appointment booking fails
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + time);
		}
		
		// after making sure time is OK, lets add appointment for a single service.
		try {
			FlexiBookApplication.getFlexiBook().addAppointment((Customer) user, s, timeSlot);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		
		

	}
	
	/**
	 * This method handle adding appointment for a service combo.
	 * (there will be a wrapper method to add a general BookableService,
	 * but to fit the UI design, kept this method public for direct use)
	 * @param serviceName
	 * @param date
	 * @param time
	 * @param optService String defines all optional service name
	 * @throws InvalidInputException
	 * 
	 * @author AntoineW
	 */
	public static void addAppointmentForComboService(String serviceName, String optService, Date date, Time time) 
			throws InvalidInputException{
	
		User user = FlexiBookApplication.getCurrentLoginUser();
		// Scenario: The owner attempts to make an appointment
		if(user instanceof Owner) {
			throw new InvalidInputException("An owner cannot make an appointment");
		}else if (user == null) {
			throw new InvalidInputException("An User is not logged in");
		}
		
		ServiceCombo sCombo = findServiceCombo(serviceName);
		if(sCombo == null) {
			throw new InvalidInputException("No such service Combo exist!");
		}
		
		
		List<ComboItem> itemList = sCombo.getServices();
		int actualTotalTime = calcActualTimeOfAppointment(itemList, optService);
		
		LocalTime aEndtime = time.toLocalTime().plusMinutes(actualTotalTime);
		Time endTime = Time.valueOf(aEndtime);
		
		// Here handle constraints: start and end date of an appointment have to be the same
		TimeSlot timeSlot = new TimeSlot(date, time, date, endTime, 
						FlexiBookApplication.getFlexiBook());
		
		if(!isInGoodTiming(timeSlot)) {
			// the added timeslot is not good. So we remove it because the appointment booking fails
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + time);
		}
		
		
	

				
		try {
			Appointment appointment = new Appointment((Customer) user, sCombo, timeSlot, FlexiBookApplication.getFlexiBook());
			
			// very much similar to calcActualTimeOfAppointment(List<ComboItem> comboItemList, String chosenItemNames)
			// add all mandatory and chosen optional combo item to appointment
			for (ComboItem item: sCombo.getServices()) {
				if(item.getMandatory() == true) {
					appointment.addChosenItem(item);
				}else {
					
					for(String name : ControllerUtils.parseString(optService)) {
						if (item.getService().getName().equals(name)) {
							appointment.addChosenItem(item);
						}
					}
				}	
			}
			
			FlexiBookApplication.getFlexiBook().addAppointment(appointment);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		
		
	}
	
	/**
	 * wrapper method combining creating appointment for single service and combo
	 * @param serviceName
	 * @param optService
	 * @param date
	 * @param time
	 * 
	 * @author AntoineW
	 * @throws InvalidInputException 
	 */
	public static void makeAppointment(String serviceName, String optService, Date date, Time time) throws InvalidInputException {
		BookableService bs = findBookableService(serviceName);
		
		if(bs instanceof Service) {
			addAppointmentForService(serviceName, date, time);
		}else if(bs instanceof ServiceCombo) {
			addAppointmentForComboService(serviceName, optService, date, time);
		}else {
			//BookableService might be null
			throw new InvalidInputException("No such service or service Combo exist!");
		}
	}
	
	/**
	 * This method handles customer wants to update appointments time
	 * @param appointment
	 * @param newDate
	 * @param newStartTime
	 * @return
	 * @throws InvalidInputException 
	 * @author AntoineW
	 */
	public static boolean updateAppointment(String serviceName, Date date, Time time, Date newDate ,Time newStartTime) throws InvalidInputException {
		
		Appointment appInSystem = findAppointment(serviceName, date, time);
		
		// Scenario: check if the current user is tweaking his/her own appointment
		if(! (appInSystem.getCustomer().getUsername() == FlexiBookApplication.getCurrentLoginUser().getUsername())) {
			throw new InvalidInputException("A customer can only update their own appointments");
		}else if(FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
			throw new InvalidInputException("An owner cannot update a customer's appointment");
		}
		
		
		// get duration of the original service
		TimeSlot oldTimeSlot = appInSystem.getTimeSlot();
		Duration d = Duration.between(oldTimeSlot.getStartTime().toLocalTime(), oldTimeSlot.getEndTime().toLocalTime());
		// get the duration to set new end time. Since there is no change in combo item, the time is same
		int durationMinutes = (int) d.toMinutes();
		Time newEndTime = Time.valueOf(newStartTime.toLocalTime().plusMinutes(durationMinutes));
		
		
		TimeSlot timeSlot = new TimeSlot(newDate, newStartTime, newDate, newEndTime, FlexiBookApplication.getFlexiBook());
		if (!isInGoodTiming(timeSlot)) {
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			return false;
		}
				
		
		appInSystem.setTimeSlot(timeSlot);
		return true;
	}
	
	

	/**
	 * This method handles a customer wants to change appointment content.
	 * can add multiple or remove multiple services once, as long as all the names are in optService
	 * @param serviceName
	 * @param date
	 * @param time
	 * @param action string "add" or "remove"
	 * @param optService String defines all optional service name
	 * @return
	 * @throws InvalidInputException
	 * @author AntoineW
	 */
	public static boolean updateAppointmentForServiceCombo(String serviceName, Date date, Time time, String action, String optService) throws InvalidInputException {
		
		Appointment appInSystem = findAppointment(serviceName,date, time);
		TimeSlot oldTimeSlot = appInSystem.getTimeSlot();
		
		// Scenario: check if the current user is tweaking his/her own appointment
		if(! (appInSystem.getCustomer().getUsername() == FlexiBookApplication.getCurrentLoginUser().getUsername())) {
			throw new InvalidInputException("A customer can only update their own appointments");
		}else if(FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
			throw new InvalidInputException("An owner cannot update a customer's appointment");
		}
		
		
		List<String> serviceNameList = ControllerUtils.parseString(optService);
		List<ComboItem> newlyAddedItem = new ArrayList<ComboItem>();
		// Scenario: check if the request on adding and removing is legitimate, aka can not remove a mandatory service
		if (action.equals("remove")) {
			for (String name: serviceNameList) {
				if(findServiceCombo(serviceName).getMainService().getService().getName().equals(name)) {
					// bad request: cannot remove main service
					return false;
				}
			}
			
			for(ComboItem item: appInSystem.getChosenItems()) {
				for (String name: serviceNameList) {
					if(item.getService().getName().equals(name)) {
						if(item.getMandatory()) {
							// bad request: cannot remove mandatory service
							return false;
						}else {
						    // start remove this optional item
							appInSystem.removeChosenItem(item);
						}
					}	
				}
			}
		}else if (action.equals("add")) {
			
			for(String name: serviceNameList) {
				for(ComboItem item: findServiceCombo(serviceName).getServices()) {
					if(item.getService().getName().equals(name)) {
						// find the right item in ServiceCombo and add it to the appointment.
						// will check time later
						appInSystem.addChosenItem(item);
						newlyAddedItem.add(item);
					}
				}
			}
			
		}
		
		
		int newDuration = calcActualTimeOfAppointment(appInSystem.getChosenItems());
		Time newEndTime = Time.valueOf(oldTimeSlot.getStartTime().toLocalTime().plusMinutes(newDuration));
		
		TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), newEndTime, FlexiBookApplication.getFlexiBook());
		 
		if (!isInGoodTiming(timeSlot)) {
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			// remove all newly added service since the time is not good
			// update fails, later return false
			for(ComboItem item: newlyAddedItem) {
				appInSystem.removeChosenItem(item);
			}
			return false;
		}
				
		
		appInSystem.setTimeSlot(timeSlot);
		return true;
	}
	
	/**
	 * @author AntoineW
	 */
	public static void cancelAppointment() {
		//@ TODO
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
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		if (user.getUsername() == "owner" || user instanceof Owner) {
			throw new InvalidInputException("You must log out of the owner account before creating a customer account");
		}
		if (username == null || username == "") {
			throw new InvalidInputException("The user name cannot be empty");
		}
		if (password == null || password == "") {
			throw new InvalidInputException("The password cannot be empty");
		}
		if (flexiBook.getCustomers().stream().anyMatch(p -> p.getUsername().equals(username))) { //this is a crazy line
			//if (user.hasWithUsername(newUsername)){ //can maybe use this instead! it's simpler!
			throw new InvalidInputException("The username already exists");
		}
		Customer aCustomer = new Customer(username, password, flexiBook);
		flexiBook.addCustomer(aCustomer);
		//assuming signing up also logs you in:
		FlexiBookApplication.setCurrentLoginUser(aCustomer);
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
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		if (user.getUsername() != currentUsername) {
			throw new InvalidInputException("You do not have permission to update this account"); //technically not in scope of feature
		}
		if (newUsername == null || newUsername == "") {
			throw new InvalidInputException("The user name cannot be empty");
		}
		if (currentUsername == "owner" && newUsername != "owner") {
			throw new InvalidInputException("Changing username of owner is not allowed");
		}
		if (newPassword == null || newPassword == "") {
			throw new InvalidInputException("The password cannot be empty");
		}
		// @ TODO check if setUsername covers this case
		if (flexiBook.getCustomers().stream().anyMatch(p -> p.getUsername().equals(newUsername))) { //this is a crazy line
		//if (user.hasWithUsername(newUsername)){ //can maybe use this instead! it's simpler!
			throw new InvalidInputException("Username not available");
		}
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
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		if (user.getUsername() != username || user.getUsername() == "owner" || user instanceof Owner) { //definitely some overlap here
			throw new InvalidInputException("You do not have permission to delete this account");
		}
		((Customer)user).delete(); 
		FlexiBookApplication.clearCurrentLoginUser();
	}
	
	/**
	 * This method logins the user based on their Username and password information
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 */
	public static void logIn(String username, String password) throws InvalidInputException{
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		Customer ThisCustomer = findCustomer(username);
		Owner ThisOwner2 = findOwner(username);

		if (currentUser == null) {
			if (ThisOwner2 != null && ThisOwner2.getPassword() == password) {
				FlexiBookApplication.setCurrentLoginUser(ThisOwner2);
			}
			else if (ThisCustomer != null && ThisCustomer.getPassword()==password) {
				FlexiBookApplication.setCurrentLoginUser(ThisCustomer);
			}
			else {
					throw new InvalidInputException("Password or Username is incorrect, please try again!");
				}
		}
		else {
			throw new InvalidInputException("There is another user currently login, please try again later");
		}
	}

	/**
	 * This method logout the user 
	 * @author mikewang
	 */
	
	// requires UI for more detailed implementation
	public static void logOut() throws InvalidInputException{ 
		User currentLoginUser = FlexiBookApplication.getCurrentLoginUser();
		if (currentLoginUser == null) {
			throw new InvalidInputException("Password or Username is incorrect, please try again!");
		}
		else {
			FlexiBookApplication.clearCurrentLoginUser();
		}
	}
	
	


/*----------------------------------------------- Query methods --------------------------------------------------------------*/
	
	/**
	 * This method is a helper method of finding all existing customers objects into an TO ArrayList
	 * @return
	 * @author mikewang
	 */
	public static List<TOCustomer> getTOCustomers(){
		ArrayList<TOCustomer> Customers = new ArrayList<TOCustomer>();
		for (User user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			TOCustomer toCustomer = new TOCustomer(user.getUsername(), user.getPassword());
			Customers.add(toCustomer);
		}
		return Customers;
	}
	
	/**
	 * This is a query method which returns a list of TOAppointmentCanlander with a chosen data and a chosen mode
	 * 
	 * @param date
	 * @param ByDay
	 * @param ByMonth
	 * @param ByYear
	 * @return
	 * @author mikewang
	 */
	public static List<TOAppointmentCanlander> viewAppointmentCalnader(Date date, Boolean ByDay, Boolean ByMonth, Boolean ByYear){
		//@ TODO
		ArrayList<TOAppointmentCanlander> appointmentCanlanders = new ArrayList<TOAppointmentCanlander>();
		if (ByDay == true && ByMonth == false && ByYear == false) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getDate() <= date.getDate() &&  date.getDate() <= toAppointments.getTimeSlot().getEndDate().getDate()) {
					TOAppointmentCanlander toAppointmentCanlander = new TOAppointmentCanlander(toAppointments.getCustomerName(),toAppointments.getTimeSlot(),toAppointments.getServiceName());
					appointmentCanlanders.add(toAppointmentCanlander);
				}
			}
		}
		else if(ByDay == false && ByMonth == true && ByYear == false) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getMonth() <= date.getMonth() &&  date.getMonth() <= toAppointments.getTimeSlot().getEndDate().getMonth()) {
					TOAppointmentCanlander toAppointmentCanlander = new TOAppointmentCanlander(toAppointments.getCustomerName(),toAppointments.getTimeSlot(),toAppointments.getServiceName());
					appointmentCanlanders.add(toAppointmentCanlander);
				}
			}
		}
		else if(ByDay == false && ByMonth == false && ByYear == true) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getYear() <= date.getYear() &&  date.getYear() <= toAppointments.getTimeSlot().getEndDate().getYear()) {
					TOAppointmentCanlander toAppointmentCanlander = new TOAppointmentCanlander(toAppointments.getCustomerName(),toAppointments.getTimeSlot(),toAppointments.getServiceName());
					appointmentCanlanders.add(toAppointmentCanlander);
				}
			}
		}
		return appointmentCanlanders;
	}
	
	/**
	 * This is a query method which can gives a list of all TOAppointment 
	 * @return
	 * @author mikewang
	 */
	public static List<TOAppointment> getTOAppointment(){
		ArrayList<TOAppointment> appointments = new ArrayList<TOAppointment>();
		for (Appointment appointment: FlexiBookApplication.getFlexiBook().getAppointments()) {
			TOAppointment toAppointment = new TOAppointment(appointment.getCustomer().getUsername(), appointment.getBookableService().getName(), CovertToTOTimeSlot(appointment.getTimeSlot()));
			appointments.add(toAppointment);
		}
		return appointments;
		
	}	
	
	/**
	 * This is a query method which can covert a TimeSlot object to it's Transfer Object
	 * @param timeSlot
	 * @return
	 * @author mikewang
	 */
	public static TOTimeSlot CovertToTOTimeSlot(TimeSlot timeSlot) {
		TOTimeSlot toTimeSlot = new TOTimeSlot(timeSlot.getStartDate(),timeSlot.getStartTime(),timeSlot.getEndDate(),timeSlot.getEndTime());
		return toTimeSlot;
	}
	
	
	/**
	 * This is a query method which can get a list of TOTimeSlot from all timeSlots in the systems
	 * @return
	 * @author mikewang
	 */
	public static List<TOTimeSlot> getTOTimeSlot(){
		//@ TODO
		ArrayList<TOTimeSlot> timeSlots = new ArrayList<TOTimeSlot>();
		for (TimeSlot timeSlot : FlexiBookApplication.getFlexiBook().getTimeSlots()) {
			TOTimeSlot toTimeSlot = new TOTimeSlot(timeSlot.getStartDate(),timeSlot.getStartTime(), timeSlot.getEndDate(), timeSlot.getEndTime());
			timeSlots.add(toTimeSlot);
		}
		return timeSlots;
	}
	
	
	
	/**
	 * This is a query method which can get all ComboItems from a specific appointment into a list of TOComboItem
	 * @param appointment
	 * @return
	 */
	public static List<TOComboItem> getToTOComboItem(Appointment appointment){
		//@ TODO
		ArrayList<TOComboItem> comboItems = new ArrayList<TOComboItem>();
		for (ComboItem comboitems: appointment.getChosenItems()) {
			TOComboItem toComboItem = new TOComboItem(comboitems.getMandatory(), comboitems.getService().getName());
			comboItems.add(toComboItem);
		}
		return comboItems;
	}
	
	
	
	
	
/*----------------------------------------------- private helper methods -----------------------------------------------------*/
	
	/**
	 * This method finds the service with specified name
	 * @param name - the name of the service to found 
	 * @return the service found 
	 * 
	 * @author chengchen
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
			if (bservice.getName().equals(name) && bservice instanceof Service) {
				s = (Service)bservice;
				break;
			}
		}
		return s;
	}

	/**
	 * @author AntoineW
	 */
	private static ServiceCombo findServiceCombo(String name) {
		
		for (BookableService bservice : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (bservice.getName().equals(name) && bservice instanceof ServiceCombo) {
				return (ServiceCombo)bservice;
			}
		}
		return null;
	}
	
	/**
	 * @author AntoineW
	 */
	private static Appointment findAppointment(String serviceName, Date date, Time time) {
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			// check service name, date, time and customer
			if (app.getBookableService().getName().compareTo(serviceName) == 0 &&
					app.getTimeSlot().getStartDate().equals(date) &&
					app.getTimeSlot().getStartTime().equals(time) ){
				
				return app;
			}
		}
		return null;
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
	
	
	/**
	 * appointment cannot be made on holidays or during vacation
	 * @param timeSlot
	 * @return
	 * @author AntoineW
	 */
	private static boolean isDuringWorkTime(TimeSlot timeSlot) {
		
		boolean isDuringWorkTime = false;
		
		//First get the weekday
		DayOfWeek dOfWeek = ControllerUtils.getDoWByDate(timeSlot.getStartDate());
		// then check all businessHour list
		List<BusinessHour> bhList = FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours();
		
		for(BusinessHour bh: bhList) {
			// check weekday
			if(dOfWeek == bh.getDayOfWeek()) {
				// if the appointment is on that day, compare if the time slot is included by business hour
				if(timeSlot.getStartTime().toLocalTime().isAfter(bh.getStartTime().toLocalTime()) &&
						timeSlot.getEndTime().toLocalTime().isBefore(bh.getEndTime().toLocalTime())) {
					isDuringWorkTime = true;
					break;
				}
			}
			
		}
		return isDuringWorkTime;
	}
	
	/**
	 * Check if the appointment is made before now (which is not allowed)
	 * @param timeSlot
	 * @return
	 * 
	 * @author AntoineW
	 */
	private static boolean isInTheFuture(TimeSlot timeSlot) {
		boolean isInFuture = true;
		
		Date currentDate = FlexiBookApplication.getCurrentDate();
		Time currentTime = FlexiBookApplication.getCurrentTime();
		LocalDateTime now = ControllerUtils.combineDateAndTime(currentDate, currentTime);
		
		LocalDateTime appointmentDateTime = ControllerUtils.combineDateAndTime(timeSlot.getStartDate(), timeSlot.getStartTime());
		if(appointmentDateTime.isBefore(now)) {
			isInFuture = false;
		}
		
		return isInFuture;
		
	}
	
	/**
	 * wrapper method of isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot), isDuringDowntime(TimeSlot timeSlot), isDuringWorkTime(TimeSlot timeSlot) and isInTheFuture(TimeSlot timeSlot)
	 * The method will return true if the timeslot passes through all 4 tests in a specific order.
	 * @return
	 * 
	 * @author AntoineW
	 */
	private static boolean isInGoodTiming(TimeSlot timeSlot) {
		
		// here handle Scenario: A customer attempts to make various invalid appointments for services
		// there are three time constraints to check:
		// 1. if in the business time, if not, fail directly
		// 		2. if overlap with other time slot (other appointment/vacation/holiday). if there is overlap, we check the downtime!
		// 		3. if not in the downtime of other app, fail
		if (!isDuringWorkTime(timeSlot)) {
			return false;
		}else {
			if(!isNotOverlapWithOtherTimeSlots (timeSlot)) {
				if (!isDuringDowntime(timeSlot)) {
					return false;
				}
			}
		}
								
		// Make sure appointment is made in the future not in the past
		if (!isInTheFuture(timeSlot)) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method is a helper method determining the actual time of a appointment
	 * It will only be used for a serviceCombo.<p>
	 * This is implemented because customer can choose to not have certain optional services in a combo.
	 * @return
	 * 
	 * @author AntoineW
	 */
	private static int calcActualTimeOfAppointment(List<ComboItem> comboItemList, String chosenItemNames) {
		
		int actualTime = 0;
		List<String> itemNameList = ControllerUtils.parseString(chosenItemNames);
	
		for (ComboItem ci : comboItemList) {
			
			if(ci.getMandatory()) {
				actualTime = actualTime + ci.getService().getDuration();
			}else {
				// check the chosen list if a NON-mandatory service is chosen
				// if yes then we add time
				for (String name : itemNameList ) {
					// loop through all chosen name, see if equals to the current item
					if (name.compareTo(ci.getService().getName()) == 0) {
						actualTime = actualTime + ci.getService().getDuration();
					}
				}
				
			}
		}
		
		return actualTime;
		
	}
	
	/**
	 * This method is a helper method determining the actual time of a appointment
	 * This is the overloaded version, which simply adds all services time
	 * @return
	 * @author AntoineW
	 */
	private static int calcActualTimeOfAppointment(List<ComboItem> comboItemList) {
		
		int actualTime = 0;
	
		for (ComboItem ci : comboItemList) {
			actualTime = actualTime + ci.getService().getDuration();
		}
		
		return actualTime;
		
	}
	

	
	
	
	/**
	 * This method is a helper method of finding a particular customer 
	 * @param userName
	 * @return
	 * @author mikewang
	 */
	
	private static Customer findCustomer (String userName){
		Customer foundCustomer = null;
		for (Customer user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (user.getUsername() == userName ) {
				foundCustomer = user;
				break;
			}else {
				foundCustomer = null;
			}
		}
		return foundCustomer;
	}
	
	/**
	 * This method is a helper method of finding the owner 
	 * @param userName
	 * @return
	 * @author mikewang
	 */
	private static Owner findOwner(String userName) {
		Owner foundOwner = null;
		if (userName == "owner") {
			foundOwner = FlexiBookApplication.getFlexiBook().getOwner();
		}
		return foundOwner;
	}
	
	
	/**
	 * This method is a helper method of finding is the date we specifying is today. 
	 * @param date
	 * @return
	 * @author mikewang
	 */
	private static Boolean isToday(Date date) {
		java.util.Date tempToday = getCurrentDate();
		Boolean check =false; 
		if (date == tempToday) {
			check = true;
		}
		return check; 
	}
	
	
	/**
	 * This is a helper method of finding the current date
	 * @return
	 * @author BTMS.getCurrentDate()
	 */
   private static java.util.Date getCurrentDate(){
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    java.util.Date date = cal.getTime();
	    return date;
	  }
}


