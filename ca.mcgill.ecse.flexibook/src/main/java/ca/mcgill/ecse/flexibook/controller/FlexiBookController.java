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
	 * @param duration - duration of the service to be added
	 * @param downtimeDuration - duration of the downtime of the service to be added
	 * @param downtimeStart - the start time of the downtime of the service to be added
	 * @throws InvalidInputException
	 * 
	 * @author chengchen
	 *
	 */

	public static boolean addService(String name,int duration,int downtimeStart,int downtimeDuration) throws InvalidInputException{
		Boolean isSuccess = false;
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		if (!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)) {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}

		else if (duration <= 0) {
			throw new InvalidInputException("Duration must be positive");
		}


		else if (downtimeStart > 0 && downtimeDuration <= 0) {
			throw new InvalidInputException("Downtime duration must be positive");

		}

		else if (downtimeStart == 0 && downtimeDuration < 0) {

			throw new InvalidInputException("Downtime duration must be 0");

		}
		else if (downtimeStart == 0 && downtimeDuration > 0) {
			throw new InvalidInputException("Downtime must not start at the beginning of the service");

		}
		else if (downtimeStart < 0) {
			
			throw new InvalidInputException("Downtime must not start before the beginning of the service");

		}
		else if (downtimeStart + downtimeDuration > duration && downtimeStart < duration) {

			throw new InvalidInputException("Downtime must not end after the service");

		}
		else if (downtimeStart > duration) {

			throw new InvalidInputException("Downtime must not start after the end of the service");

		}
		else {
			try {
				BookableService service = new Service(name, flexiBook, duration, downtimeDuration, downtimeStart);
				flexiBook.addBookableService(service);
				isSuccess = true;
			} catch (Exception e) {
				if (e.getMessage().equals("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html")) {
					throw new InvalidInputException("Service "+name+" already exists");

				}
			}


		}


		return isSuccess;
	}


	/**
	 * 
	 * This method removes the service with specified name from the database
	 * @param name - name of the service to be removed
	 * @throws InvalidInputException 
	 * @author chengchen
	 */
	public static boolean deleteService(String name) throws InvalidInputException{
		Boolean isSuccess = false;
		Service service = findSingleService(name);
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		List<BookableService> bookableServices = flexiBook.getBookableServices();
		List<String> serviceComboNamesToDelete = new ArrayList<String>();
		List<ComboItem> comboItemsToDelete = new ArrayList<ComboItem>();
		if (!FlexiBookApplication.getCurrentLoginUser().getUsername().equals("owner")) {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		
		for (Appointment appointment:service.getAppointments()) {
			if (FlexiBookApplication.getCurrentDate(true).before(appointment.getTimeSlot().getStartDate())) {
				throw new InvalidInputException("The service contains future appointments");
			}
		}

		for (BookableService bookableService:bookableServices) {
			if (bookableService instanceof ServiceCombo) {
				if (((ServiceCombo) bookableService).getMainService().getService().getName().equals(name)) {
					serviceComboNamesToDelete.add(bookableService.getName());
				}

				for (ComboItem aComboItem:((ServiceCombo) bookableService).getServices()) {
					if (aComboItem.getService().getName().equals(name)) {
						comboItemsToDelete.add(aComboItem);
					}
				}


			}
		}
		if (!serviceComboNamesToDelete.isEmpty()) {
			for (String comboName:serviceComboNamesToDelete) {
				ServiceCombo bserCombo = findServiceCombo(comboName);
				bserCombo.delete();
				isSuccess = true;
			}

		}

		if (!comboItemsToDelete.isEmpty()) {
			for (ComboItem comboItem:comboItemsToDelete) {
				comboItem.delete();
				isSuccess = true;
			}
		}

			
		service.delete();
		isSuccess = true;
		return isSuccess;
	}
	
	


	/**
	 * 
	 * This method updates the attributes of the specified service
	 * @param name - name of the service to be updated
	 * @param durantion - duration of the service to be updated
	 * @param downtimeDuration - duration of the downtime of the service to be updated
	 * @param downtimeStart - the start time of the downtime of the service to be updated
	 * @throws InvalidInputException 
	 * @author chengchen
	 * 
	 */
	public static boolean updateService(String serviceName,String newName, int newDuration, int newDowntimeDuration, int newDowntimeStart) throws InvalidInputException {
		Boolean isSuccess = false;
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		BookableService bookableService = findBookableService(serviceName);
		if (!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)) {
			throw new InvalidInputException("You are not authorized to perform this operation");
		}

		else if (newDuration <= 0) {
			throw new InvalidInputException("Duration must be positive");
		}


		else if (newDowntimeStart > 0 && newDowntimeDuration <= 0) {
			throw new InvalidInputException("Downtime duration must be positive");

		}

		else if (newDowntimeStart == 0 && newDowntimeDuration < 0) {

			throw new InvalidInputException("Downtime duration must be 0");

		}
		else if (newDowntimeStart == 0 && newDowntimeDuration > 0) {
			throw new InvalidInputException("Downtime must not start at the beginning of the service");

		}
		else if (newDowntimeStart < 0) {

			throw new InvalidInputException("Downtime must not start before the beginning of the service");

		}
		else if (newDowntimeStart + newDowntimeDuration > newDuration && newDowntimeStart < newDuration) {

			throw new InvalidInputException("Downtime must not end after the service");

		}
		else if (newDowntimeStart > newDuration) {

			throw new InvalidInputException("Downtime must not start after the end of the service");

		}
		
		else {
			List<String> serviceNames = new ArrayList<String>();
			for (BookableService aBookableService:flexiBook.getBookableServices()) {
				if (bookableService instanceof Service) {
					serviceNames.add(aBookableService.getName());
				}
			}
				if (serviceNames.contains(newName)&&!serviceName.equals(newName)) {
					throw new InvalidInputException("Service "+newName+" already exists");
				}
			
			((Service) bookableService).setDuration(newDuration);
			if (!serviceName.equals(newName)) {
				bookableService.setName(newName);
			}
			((Service) bookableService).setDowntimeStart(newDowntimeStart);
			((Service) bookableService).setDowntimeDuration(newDowntimeDuration);
			isSuccess = true;

		}
		return isSuccess;
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
	public static Appointment addAppointmentForService(String serviceName, Date date, Time time) 
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
		int index = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(timeSlot);
		if(!isInGoodTiming(timeSlot, index,-1)) {
			// the added timeslot is not good. So we remove it because the appointment booking fails
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			if(time.toString().charAt(0) == '0') {
				String timeStr = (new StringBuilder(time.toString())).deleteCharAt(0).toString();
				throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + timeStr);
			}else {
				throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + time);
			}
		}

		// after making sure time is OK, lets add appointment for a single service.
		try {

			Appointment appointment = new Appointment((Customer) user, s, timeSlot,FlexiBookApplication.getFlexiBook() );
			FlexiBookApplication.getFlexiBook().addAppointment(appointment);
			return appointment;
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
	public static Appointment addAppointmentForComboService(String serviceName, String optService, Date date, Time time) 
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
		int index = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(timeSlot);
		if(!isInGoodTiming(timeSlot,index, -1)) {
			// the added timeslot is not good. So we remove it because the appointment booking fails
			FlexiBookApplication.getFlexiBook().removeTimeSlot(timeSlot);
			// tweak format to pass tests
			if(time.toString().charAt(0) == '0') {
				String timeStr = (new StringBuilder(time.toString())).deleteCharAt(0).toString();
				throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + timeStr);
			}else {
				throw new InvalidInputException("There are no available slots for " + serviceName + " on "+ date + " at " + time);
			}

		}


		try {
			Appointment appointment = new Appointment((Customer) user, sCombo, timeSlot, FlexiBookApplication.getFlexiBook());


			// very much similar to calcActualTimeOfAppointment(List<ComboItem> comboItemList, String chosenItemNames)
			// add all mandatory and chosen optional combo item to appointment
			for (ComboItem item: sCombo.getServices()) {

				if(item.getService().getName().equals(sCombo.getMainService().getService().getName()) || item.getMandatory()) {
					appointment.addChosenItem(ControllerUtils.findComboItemByServiceName(sCombo, item.getService().getName()));
				}else{
					for(String name : ControllerUtils.parseString(optService, ",")) {
						if (item.getService().getName().equals(name)) {
							appointment.addChosenItem(item);
						}
					}
				}
			}	


			FlexiBookApplication.getFlexiBook().addAppointment(appointment);


			return appointment;
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
	public static Appointment makeAppointment(String serviceName, String optService, Date date, Time time) throws InvalidInputException {
		BookableService bs = findBookableService(serviceName);

		if(bs instanceof Service) {
			return(addAppointmentForService(serviceName, date, time));
		}else if(bs instanceof ServiceCombo) {
			return(addAppointmentForComboService(serviceName, optService, date, time));
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
		if(FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
			throw new InvalidInputException("Error: An owner cannot update a customer's appointment");
		}else if(! (appInSystem.getCustomer().getUsername() .equals( FlexiBookApplication.getCurrentLoginUser().getUsername()))) {
			throw new InvalidInputException("Error: A customer can only update their own appointments");
		}


		// get duration of the original service
		TimeSlot oldTimeSlot = appInSystem.getTimeSlot();
		Duration d = Duration.between(oldTimeSlot.getStartTime().toLocalTime(), oldTimeSlot.getEndTime().toLocalTime());
		// get the duration to set new end time. Since there is no change in combo item, the time is same
		int durationMinutes = (int) d.toMinutes();
		Time newEndTime = Time.valueOf(newStartTime.toLocalTime().plusMinutes(durationMinutes));


		TimeSlot timeSlot = new TimeSlot(newDate, newStartTime, newDate, newEndTime, FlexiBookApplication.getFlexiBook());
		int index = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(timeSlot);
		int oldIndex = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(oldTimeSlot);

		if (!isInGoodTiming(timeSlot, index, oldIndex)) {
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
		if(FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
			throw new InvalidInputException("Error: An owner cannot update a customer's appointment");
		}else if(! (appInSystem.getCustomer().getUsername() .equals( FlexiBookApplication.getCurrentLoginUser().getUsername()))) {
			throw new InvalidInputException("Error: A customer can only update their own appointments");
		}

		List<String> serviceNameList = ControllerUtils.parseString(optService, ",");
		List<ComboItem> newlyAddedItem = new ArrayList<ComboItem>();
		// Scenario: check if the request on adding and removing is legitimate, aka can not remove a mandatory service
		if (action.equals("remove")) {
			for (String name: serviceNameList) {
				if(findServiceCombo(serviceName).getMainService().getService().getName().equals(name)) {
					// bad request: cannot remove main service
					return false;
				}
			}

			// since appInSystem.getChosenItems() is inmutable by umple, have to create a deep copy here to iterate
			List<ComboItem> copy= new ArrayList<ComboItem>();
			for(ComboItem item: appInSystem.getChosenItems()) {
				copy.add(item);
			}
			for(ComboItem item: copy) {
				for (String name: serviceNameList) {
					if(item.getService().getName().equals(name)) {
						if(item.getMandatory()) {
							// bad request: cannot remove mandatory service
							return false;
						}else {					
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

		TimeSlot timeSlot = new TimeSlot(oldTimeSlot.getStartDate(), oldTimeSlot.getStartTime(), oldTimeSlot.getEndDate(), 
				newEndTime, FlexiBookApplication.getFlexiBook());
		int index = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(timeSlot);
		int oldIndex = FlexiBookApplication.getFlexiBook().indexOfTimeSlot(oldTimeSlot);
		if (!isInGoodTiming(timeSlot, index, oldIndex)) {
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
	 * This method handles the cancellation of an existing appointment
	 * @param serviceName
	 * @param date
	 * @param time
	 * 
	 * @author AntoineW
	 * @throws InvalidInputException 
	 */
	public static boolean cancelAppointment(String serviceName, Date date, Time time) throws InvalidInputException {

		Appointment appInSystem = findAppointment(serviceName,date, time);

		// Scenario: check if the current user is tweaking his/her own appointment
		if(FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
			throw new InvalidInputException("An owner cannot cancel an appointment  ");
		}else if(! (appInSystem.getCustomer().getUsername() .equals( FlexiBookApplication.getCurrentLoginUser().getUsername()))) {
			throw new InvalidInputException("A customer can only cancel their own appointments");
		}

		Date today = FlexiBookApplication.getCurrentDate(true);
		if(date.equals(today)) {
			throw new InvalidInputException("Cannot cancel an appointment on the appointment date");
		}else if(date.after(today)){
			//make sure the customer can only cancel appointment in the future
			appInSystem.delete();
			return true;

		}
		return false;

	}


	/**
	 * This method creates a new customer account when a customer signs up
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean signUpCustomer(String username, String password) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		boolean signUpSuccessful = false;
		if (FlexiBookApplication.getCurrentLoginUser() != null) {
			if (FlexiBookApplication.getCurrentLoginUser().getUsername() == "owner" || FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
				throw new InvalidInputException("You must log out of the owner account before creating a customer account");
			}
		}
		else if (username == null || username.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The user name cannot be empty");
		}
		else if (password == null || password.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The password cannot be empty");
		}
		else if (flexiBook.getCustomers().stream().anyMatch(p -> p.getUsername().equals(username))) { //consider using helper method findCustomer
			//if (user.hasWithUsername(newUsername)){ //can maybe use this instead? it's simpler!
			throw new InvalidInputException("The username already exists");
		}
		else {
			Customer aCustomer = new Customer(username, password, flexiBook);
			flexiBook.addCustomer(aCustomer); //this seems unecessary
			//assuming signing up also logs you in:
			FlexiBookApplication.setCurrentLoginUser(aCustomer); 
			signUpSuccessful = true;
		}
		return signUpSuccessful;
	}


	/**
	 * This method updates the username and/or password for a customer account, 
	 * or the password for an owner account
	 * @param currentUsername
	 * @param newUsername
	 * @param newPassword
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean updateUserAccount(String currentUsername, String newUsername, String newPassword) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		boolean updateSuccessful = false;
		if (user.getUsername() != currentUsername) {
			throw new InvalidInputException("You do not have permission to update this account"); 
		}
		else if (newUsername == null || newUsername.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The user name cannot be empty");
		}
		else if (currentUsername.equals("owner") && !newUsername.equals("owner")) {
			throw new InvalidInputException("Changing username of owner is not allowed");
		}
		else if (newPassword == null || newPassword.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The password cannot be empty");
		}
		else if (flexiBook.getCustomers().stream().anyMatch(p -> p.getUsername().equals(newUsername))) { 
			//if (user.hasWithUsername(newUsername)){ //can maybe use this instead? it's simpler!
			throw new InvalidInputException("Username not available");
		}
		else {
			user.setUsername(newUsername);
			user.setPassword(newPassword);
			updateSuccessful = true;
		}
		return updateSuccessful;
	}


	/**
	 * This method deletes the current customer's account so their personal information is deleted
	 * @param username
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean deleteCustomerAccount(String username) throws InvalidInputException{ 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		boolean deleteSuccessful = false;
		if (!user.getUsername().equals(username) || user.getUsername().equals("owner") || user instanceof Owner) { 
			throw new InvalidInputException("You do not have permission to delete this account");
		}
		else {
			((Customer)user).delete(); 
			FlexiBookApplication.clearCurrentLoginUser();
			deleteSuccessful = true;
		}
		return deleteSuccessful;
	}



	/**
	 * This method logins the user based on their Username and password information
	 * @param username
	 * @param password
	 * @throws InvalidInputException
	 * @author mikewang
	 */
	
	public static void logIn(String username, String password) throws InvalidInputException {
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		Customer ThisCustomer = findCustomer(username);
		Owner ThisOwner2 = findOwner(username);

		if (currentUser == null) {
			if (ThisOwner2 != null && ThisOwner2.getPassword() == password) {
				FlexiBookApplication.setCurrentLoginUser(ThisOwner2);
			}
			else if(ThisOwner2 == null){
				signUpOwner(username, password);
				
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
			throw new InvalidInputException("The User is already logged out!");
		}
		else {
			FlexiBookApplication.clearCurrentLoginUser();
		}
	}
	
	/**
	 * This method creates a Service Combo given a name, a mainService, and a list of otherServices with a boolean list of
	 	which otherServices are mandatory.
	 * @author gtjarvis
	 */
	public static boolean defineServiceCombo(String name, String mainServiceName, List<String> orderedServices, List<Boolean> listOfMandatory) throws InvalidInputException{ 
		//make sure current user is owner
		if(!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		//throws an exception if length of orderedServices does not match length of listOfMandatory
		if(orderedServices.size() != listOfMandatory.size()){
			throw new InvalidInputException("Error with additional services.");
		}
		//throws an exception if name is empty or null
		if(name == null || name.equals("")){
			throw new InvalidInputException("Name is invalid.");
		}
		//throws an exception if Service Combo already exists
		if(findServiceCombo(name) != null){
			throw new InvalidInputException("Service combo " + name + " already exists");
		}
		//throws an exception if number of services less then 2
		if(orderedServices.size() < 2){
			throw new InvalidInputException("A service Combo must contain at least 2 services");
		}

		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		//creates new serviceCombo object
		ServiceCombo serviceCombo = new ServiceCombo(name,flexibook);
		//finds mainService
		Service mainService = findSingleService(mainServiceName);

		//throws an exception if main service cannot be found
		if(mainService == null){
			throw new InvalidInputException("Main service not found.");
		}
		//goes through list of orderedServices and creates a ComboItem for every service
		boolean hasMainService = false;
		boolean success = false;
		boolean mandatory;
		Service service;
		ComboItem comboItem;
		for(int i = 0; i < orderedServices.size(); i++){
			mandatory = listOfMandatory.get(i);
			service = findSingleService(orderedServices.get(i));
			if(service == null) {
				throw new InvalidInputException("Service " + orderedServices.get(i) + " does not exist");
			}
			comboItem = serviceCombo.addService(mandatory, service);
			//sets appropirate main service
			if(service.equals(mainService)){
				if(mandatory){
					serviceCombo.setMainService(comboItem);
					hasMainService = true;
				} else {
					throw new InvalidInputException("Main service must be mandatory");
				}
			}

		}
		//throws an exception if mainService not found in list
		if(!hasMainService){
			throw new InvalidInputException("Main service must be included in the services");
		}
		success = true;
		return success;
	}

	/**
	 * This method updates a Service Combo
	 * @author gtjarvis
	 */
	public static boolean updateServiceCombo(String name, List<String> orderedServices, List<Boolean> listOfMandatory) throws InvalidInputException { 
		//make sure current user is owner
		if(!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)){
			throw new InvalidInputException("Only Owner may define a Service Combo");
		}
		ServiceCombo serviceCombo = findServiceCombo(name);
		//throws an exception if comboService does not exist
		if(serviceCombo == null){
			throw new InvalidInputException("Updating service that does not exist.");
		}
		//throws an exception if length of orderedServices does not match length of listOfMandatory
		if(orderedServices.size() != listOfMandatory.size()){
			throw new InvalidInputException("Error with additional services.");
		}
		//deletes current services
		while(serviceCombo.getServices().size() > 0){
			serviceCombo.removeService(serviceCombo.getServices().get(0));
		}
		//resets additional services with new list of ordered services
		boolean hasMainService = false;
		boolean mandatory;
		boolean success = false;
		Service service;
		ComboItem comboItem;
		for(int i = 0; i < orderedServices.size(); i++){
			mandatory = listOfMandatory.get(i);
			service = findSingleService(orderedServices.get(i));
			comboItem = serviceCombo.addService(mandatory, service);
			//sets appropirate main service
			if(service.equals(service) && mandatory){
				serviceCombo.setMainService(comboItem);
				hasMainService = true;
			}
		}
		//throws an exception if mainService not found in list
		if(!hasMainService){
			throw new InvalidInputException("Main Service not in list of services or is not mandatory.");
		}
		success = true;
		return success;
	}

	/**
	 * This method deletes a Service Combo given a name
	 * @author gtjarvis
	 */
	public static boolean deleteServiceCombo(String name) throws InvalidInputException{ 
		//make sure current user is owner
		if(!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)){
			throw new InvalidInputException("Only Owner may define a Service Combo");
		}
		BookableService comboService = findBookableService(name);
		boolean success = false;
		if(comboService == null){
			throw new InvalidInputException("Deleting service that does not exist.");
		}
		comboService.delete();

		success = true;
		return success;
	}


	/**
	 * This method is used to setup the business with all the information
	 * @param buisnessName
	 * @param email
	 * @param phoneNumber
	 * @throws InvalidInputException
	 * @author jedla
	 */
	//email needs to be fixed <anything>@<anything>.<anything>
	public static void setUpBusinessInfo(String businessName, String adress, String email, String phoneNumber) throws InvalidInputException { 
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 

		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else {
			Business business = new Business(businessName,adress, phoneNumber, email, flexiBook);
			if (!(flexiBook.hasBusiness())) {
				flexiBook.setBusiness(business);
			}else {
				throw new InvalidInputException("A business already exists");
			}

		}}

	/**
	 * This method is used to setup the business hour for a day
	 * @param startTime
	 * @param endTime
	 * @param DayOfWeek
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void setUpBusinessHours(Time startTime, Time endTime, DayOfWeek day) throws InvalidInputException{// need to add that the business hours can't overlap
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		BusinessHour bh = new BusinessHour(day,startTime, endTime, flexiBook);
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business hour");
		}
		else {
			if (startTime.compareTo(endTime) < 0) { 
				if ( isOverlappingWithBusinessHours(bh)) {
					throw new InvalidInputException(" The business hours cannot overlap");
				} 
				else {
					flexiBook.addHour(bh);
				}
			}
			else 
			{ throw new InvalidInputException("Start time must be before end time ");
			}
		}
	}


	/**
	 * This method is used to create a Vacation or a Holiday
	 * @param startDatea 
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void setUpHolidayVacation( String type ,Date startDate, Time startTime, Date endDate, Time endTime ) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business business = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		TimeSlot VacationHoliday = new TimeSlot(startDate, startTime, endDate, endTime, flexiBook);

		if (currentUser instanceof Customer) { 
			throw new InvalidInputException("No permission to set up Holidays or Vacation");
		}
		else {
			if (!isInTheFuture(VacationHoliday)) {
				throw new InvalidInputException("The time slot is not in the futre");
			}
			else {
				if (!isNotOverlapWithOtherTimeSlots(VacationHoliday)){
					throw new InvalidInputException("The time slot is overlapping with another time slot");
				}

				else {
					if ((startTime.toLocalTime().isBefore(endTime.toLocalTime()))&&startDate.toLocalDate().isBefore(endDate.toLocalDate())) { 

						if (type.equals("vacation")) {
							business.addVacation(VacationHoliday);				
						}

						else if (type.equals("holiday")) {
							business.addHoliday(VacationHoliday);
						}


					} else {throw new InvalidInputException("Start time must be before end time "); 	}
				}	}}
	}

	/**
	 * This method is used to update a Vacation or a Holiday
	 * @param type
	 * @param oldStartDate
	 * @param oldStartTIme
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void UdpateHolidayVacation( String type ,Date oldStartDate, Time oldStartTime, Date startDate, Time startTime, Date endDate, Time endTime ) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business business = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		TimeSlot VacationHoliday = new TimeSlot(oldStartDate, oldStartTime, endDate, endTime, flexiBook);
		TimeSlot newVacationHoliday = new TimeSlot(startDate, startTime, endDate, endTime, flexiBook);
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else { 
			if ((startTime.toLocalTime().isBefore(endTime.toLocalTime()))&&startDate.toLocalDate().isBefore(endDate.toLocalDate())) {
				if(isNotOverlapWithOtherTimeSlots(newVacationHoliday)) {
					if (type.equals("vacation")) {
						TimeSlot vacation = (isTheVacation(VacationHoliday));
						vacation.setEndDate(endDate);
						vacation.setEndTime(endTime);
						vacation.setStartDate(startDate);
						vacation.setStartTime(startTime);
					}

					else if (type.equals("holiday")){
						TimeSlot holiday = (isTheHoliday(VacationHoliday));
						holiday.setEndDate(endDate);
						holiday.setEndTime(endTime);
						holiday.setStartDate(startDate);
						holiday.setStartTime(startTime);
					}

				} else {throw new InvalidInputException("The time slot is overlapping with another time slot");}
			}
			else {
				throw new InvalidInputException("Start time must be before end time ");
			}
		}
	}

	/**
	 * This method is used to remove a Holiday or a Vacation
	 * @param day
	 * @param startTime
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void removeHolidayVacation(String type, Date startDate, Time startTime, Date endDate, Time endTime) throws InvalidInputException {

		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business business = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		TimeSlot VacationHoliday = new TimeSlot(startDate, startTime, endDate, endTime, flexiBook);
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else { 
			if (type.equals("vacation")) {
				business.removeVacation(isTheVacation(VacationHoliday));			
			}

			else if (type.equals("holiday")){
				business.removeHoliday(isTheHoliday(VacationHoliday));
			}
		}		
	}


	/**
	 * This method is used to update the Business Hours
	 * @param oldStart
	 * @param day
	 * @param newStart
	 * @param newEnd
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void updateBusinessHour(Time oldStart, DayOfWeek day, Time newStart, Time newEnd)throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		BusinessHour oldB = new BusinessHour(day,oldStart, newEnd, flexiBook);
		BusinessHour newB = new BusinessHour(day,newStart, newEnd, flexiBook);
		BusinessHour temp = null;
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to udpate business hour");
		}
		else {
			if (newStart.compareTo(newEnd) < 0) { 
				temp =  isTheBusinessHour(oldB);
				flexiBook.getBusiness().removeBusinessHour(temp);
				if ( isOverlappingWithBusinessHours(newB)) {
					throw new InvalidInputException(" The business hours cannot overlap");
				} 
				else {
					flexiBook.addHour(newB);}}
			else 
			{ throw new InvalidInputException("Start time must be before end time ");}	
		}
	}


//	Tried to do updateBusinessHour with tryCatch if the one above doesn't work
//	public static void updateBusinessHour(Time oldStart, DayOfWeek day, Time newStart, Time newEnd)throws InvalidInputException {
//		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
//		User currentUser = FlexiBookApplication.getCurrentLoginUser();
//		BusinessHour oldB = new BusinessHour(day,oldStart, newEnd, flexiBook);
//		BusinessHour newB = new BusinessHour(day,newStart, newEnd, flexiBook);
//		BusinessHour temp = null;
//		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to udpate business hour");
//		}
//		else { 
//			if (newStart.compareTo(newEnd) < 0) { 
//				try {
//					temp = isTheBusinessHour(oldB);
//					temp.setDayOfWeek(day);
//					temp.setEndTime(newEnd);
//					temp.setStartTime(newStart);
//					if (isOverlappingWithBusinessHours(temp)) {
//						throw new InvalidInputException(" The business hours cannot overlap");
//					}	
//				}
//				catch (InvalidInputException e) {
//				}
//			} 
//			else 
//			{ throw new InvalidInputException("Start time must be before end time ");}	
//		}
//	}

	/**
	 * This method is used to updates the business information
	 * @param businessName
	 * @param adress
	 * @param email
	 * @throws phoneNumber
	 * @author jedla
	 */
	public static void updateBusinessInfo(String businessName, String adress, String email, String phoneNumber) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business currentBusiness = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else {
			//throw exception that there is no business under this name or that you don't have permission 
			//or that the email doesn't work
			if (currentBusiness != null) {
				currentBusiness.setAddress(adress);
				currentBusiness.setEmail(email);
				currentBusiness.setName(businessName);
				currentBusiness.setPhoneNumber(phoneNumber);
			}
		}

	}

	/**
	 * This method is used to remove a Business Hours
	 * @param day
	 * @param startTime
	 * @throws InvalidInputException
	 * @author jedla
	 */

	public static void removeBusinessHour(DayOfWeek day, Time startTime) throws InvalidInputException {

		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business currentBusiness = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		Time endTime = startTime;
		BusinessHour bh = new BusinessHour(day, startTime, endTime, flexiBook);
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else {
			currentBusiness.removeBusinessHour(isTheBusinessHour(bh));		
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
	 * DON'T TOUCH MIKE WILL FINISH THIS 
	 * This is a query method which returns a list of TOAppointmentCalendar with a chosen data and a chosen mode
	 * TODO: missing features of showing availble times, also we don't need to show the service name and the name of the customer 
	 * 			THE ONLY thing need to be shown is the time slots. Use getUnavailbleTime and getAvailbleTim
	 * 
	 * @param date
	 * @param ByDay
	 * @param ByMonth
	 * @param ByYear
	 * @return
	 * @author mikewang
	 */
	public static List<TOAppointmentCalender> viewAppointmentCalnader(Date date, Boolean ByDay, Boolean ByMonth, Boolean ByYear){
		//@ TODO
		ArrayList<TOAppointmentCalender> appointmentCalendars = new ArrayList<TOAppointmentCalender>();
		if (ByDay == true && ByMonth == false && ByYear == false) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getDate() <= date.getDate() &&  date.getDate() <= toAppointments.getTimeSlot().getEndDate().getDate()) {
					TOAppointmentCalender toAppointmentCalendar = new TOAppointmentCalender(toAppointments.getCustomerName(), toAppointments.getServiceName(), toAppointments.getTimeSlot());
					appointmentCalendars.add(toAppointmentCalendar);
				}
			}
		}
		else if(ByDay == false && ByMonth == true && ByYear == false) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getMonth() <= date.getMonth() &&  date.getMonth() <= toAppointments.getTimeSlot().getEndDate().getMonth()) {
					TOAppointmentCalender toAppointmentCalendar = new TOAppointmentCalender(toAppointments.getCustomerName(),toAppointments.getServiceName(), toAppointments.getTimeSlot());
					appointmentCalendars.add(toAppointmentCalendar);
				}
			}
		}
		else if(ByDay == false && ByMonth == false && ByYear == true) {
			for (TOAppointment toAppointments: getTOAppointment()) {
				if (toAppointments.getTimeSlot().getStartDate().getYear() <= date.getYear() &&  date.getYear() <= toAppointments.getTimeSlot().getEndDate().getYear()) {
					TOAppointmentCalender toAppointmentCalendar = new TOAppointmentCalender(toAppointments.getCustomerName(),toAppointments.getServiceName(), toAppointments.getTimeSlot());
					appointmentCalendars.add(toAppointmentCalendar);
				}
			}
		}
		return appointmentCalendars;
	}


	
	/**
	 * DON'T TOUCH MIKE WILL FINISH THIS 
	 * This is a query method which can return all unavailble time slot to an ArrayList
	 * @param date
	 * @param ByDay
	 * @param ByWeek
	 * @author mikewang
	 * @return
	 */
	public static List<TOTimeSlot> getUnavailbleTime(Date date, Boolean ByDay, Boolean ByWeek){
		ArrayList<TOTimeSlot> unavailbleTimeSlots = new ArrayList<TOTimeSlot>();
		if (ByDay == true && ByWeek == false) {
			//TODO
		}
		if (ByDay == false && ByWeek == true) {
			//TODO
		}
	}
	
	
	/**
	 * DON'T TOUCH MIKE WILL FINISH THIS 
	 * This is a query method which can return all availble time slot to an ArrayList
	 * @param date
	 * @param ByDay
	 * @param ByWeek
	 * @author mikewang
	 * @return
	 */
	public static List<TOTimeSlot> getAvailbleTime(Date date, Boolean ByDay, Boolean ByWeek){
		if (ByDay == true && ByWeek == false) {
			//TODO
		}
		if (ByDay == false && ByWeek == true) {
			//TODO
		}
	}
	
	


	/**
	 * This is a query method which can gives a list of all TOAppointment 
	 * @return
	 * @author mikewang
	 * @author AntoineW later made a change
	 */
	public static List<TOAppointment> getTOAppointment(){
		ArrayList<TOAppointment> appointments = new ArrayList<TOAppointment>();
		for (Appointment appointment: FlexiBookApplication.getFlexiBook().getAppointments()) {
			
			TOAppointment toAppointment = new TOAppointment(appointment.getCustomer().getUsername(),
					appointment.getBookableService().getName(), CovertToTOTimeSlot(appointment.getTimeSlot()));
			// ToAppointment need to show all the service item (comboitem)
			// by AnTW
			for (TOComboItem toc:getToTOComboItem(appointment)) {
				toAppointment.addChosenItem(toc);
			}
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

	/**
	 * This is a query method which can get the BusinessInformation from the business
	 * @param Business Business
	 * @return
	 * @author jedla
	 */
	public static TOBusiness getBusinessInfo(){
		TOBusiness business = new TOBusiness(FlexiBookApplication.getFlexiBook().getBusiness().getName(), FlexiBookApplication.getFlexiBook().getBusiness().getAddress(), 
				FlexiBookApplication.getFlexiBook().getBusiness().getPhoneNumber(), FlexiBookApplication.getFlexiBook().getBusiness().getEmail());
		return business;
	}

	/**
	 * This is a query method which returns all TOServiceCombo objects
	 * @param
	 * @return
	 * @author gtjarvis
	 */
	public static List<TOServiceCombo> getTOServiceCombos(){
		List<BookableService> bookableServices = FlexiBookApplication.getFlexiBook().getBookableServices();
		List<TOServiceCombo> serviceCombos;
		List<ComboItem> comboItems;
		for(BookableService s: bookableServices){
			if(s instanceof ServiceCombo) {
				TOServiceCombo sc = new TOServiceCombo(s.getName());
				ServiceCombo currentServiceCombo = (ServiceCombo) s;
				comboItems = currentServiceCombo.getServices();
				for(ComboItem c: comboItems){
					TOComboItem comboItemTO = new TOComboItem(c.getMandatory(),c.getService().getName());
					sc.addService(comboItemTO);
				}
				serviceCombos.add(sc);
			}
		}
		return serviceCombos;
	}

	/**
	 * This is a query method which returns a specific TOServiceCombo by name
	 * @param
	 * @return
	 * @author gtjarvis
	 */
	public static TOServiceCombo getTOServiceCombo(String name){
		List<BookableService> bookableServices = FlexiBookApplication.getFlexiBook().getBookableServices();
		List<ComboItem> comboItems;
		TOServiceCombo serviceCombo;
		for(BookableService s: bookableServices){
			if(s instanceof ServiceCombo && s.getName().equals(name)) {
				serviceCombo = new TOServiceCombo(name);
				ServiceCombo currentServiceCombo = (ServiceCombo) s;
				comboItems = currentServiceCombo.getServices();
				for(ComboItem c: comboItems){
					TOComboItem comboItemTO = new TOComboItem(c.getMandatory(),c.getService().getName());
					serviceCombo.addService(comboItemTO);
				}
			}
			break;
		}
		return serviceCombo;
	}




	/*----------------------------------------------- private helper methods -----------------------------------------------------*/
	//
	//	public static List<TOServiceCombo> getTOServiceCombos(){
	//		//@ TODO
	//	}
	//
	//	public static List<TOService> getTOServices(){
	//		//@ TODO
	//	}
	//	
	//	
	//	


	/*----------------------------------------------- private helper methods -----------------------------------------------------*/


	/**
	 * This method finds the service with specified name
	 * @param name - the name of the service to found 
	 * @return the service found 
	 * 
	 * @author chengchen
	 */
	public static BookableService findBookableService(String name) {
		BookableService foundBookableService = null;
		for (BookableService service : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (service.getName().equals(name)) {
				foundBookableService = service;
				break;
			}
		}
		return foundBookableService;
	}
	
	/**
	 * This method finds the appointments that has specified services
	 * @param serviceName
	 * @return a list of appointments 
	 * 
	 * @author chengchen
	 */
	public static List<Appointment> findAppointmentByServiceName(String serviceName) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			if (app.getBookableService().getName().equals(serviceName)) {
				appointments.add(app);
			}
		}
		return appointments;
	}

	/** 
	 * This method finds the service with specified name
	 * @param name - the name of the service to found 
	 * @return the service found 
	 *
	 * @author AntoineW
	 */
	public static Service findSingleService(String name) {
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
	public static ServiceCombo findServiceCombo(String name) {
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
	public static Appointment findAppointment(String serviceName, Date date, Time time) {
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
	private static boolean isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot, int index, int oldIndex) {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
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
	 * Check if the appointment is made before now (which is not allowed)
	 * @param timeSlot
	 * @return
	 * 
	 * @author AntoineW
	 */
	private static boolean isInTheFuture(TimeSlot timeSlot) {
		boolean isInFuture = true;
		Date currentDate = FlexiBookApplication.getCurrentDate(true);
		Time currentTime = FlexiBookApplication.getCurrentTime(true);
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
	private static boolean isInGoodTiming(TimeSlot timeSlot, int index, int oldIndex) {

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
		List<String> itemNameList = ControllerUtils.parseString(chosenItemNames,",");

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
	public static Customer findCustomer (String userName){
		Customer foundCustomer = null;
		for (Customer user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (user.getUsername() .equals( userName) ) {
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
	 * This method is a helper method for finding a particular user by username
	 * User can be the owner or a customer 
	 * @param username
	 * @return 
	 * @author Catherine
	 */
	public static User findUser(String username){
		User foundUser = null;
		if (username.equals("owner")) {
			foundUser = FlexiBookApplication.getFlexiBook().getOwner();	
		}
		else {
			for (User user : FlexiBookApplication.getFlexiBook().getCustomers()) {
				if (user.getUsername().equals(username) ) {
					foundUser = user;
					break;
				}
				else foundUser = null;
			}

		}
		return foundUser;
	}


	/**
	 * This method is a helper method of finding is the date we specifying is today. 
	 * @param date
	 * @return
	 * @author mikewang
	 */
	private static Boolean isToday(Date date) {
		java.util.Date tempToday = FlexiBookApplication.getCurrentDate();
		Boolean check =false; 
		if (date == tempToday) {
			check = true;
		}
		return check; 
	}


	// not a useful method DO NOT USE!!! USE the method defined in the FlexiBook application instead
	/**
	 * This is a helper method of finding the current date
	 * @return
	 * @author mikewang
	 * @deprecated use the FlexiBookApplication.getcurrentTime(Boolean) instead
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
	
	/**
	 * This is an helper method which provides an opportunity for the owner to set up it's owner account
	 * @param username
	 * @param password
	 * @return
	 * @throws InvalidInputException
	 * @author mikewang
	 */
	public static boolean signUpOwner(String username, String password) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		boolean signUpSuccessful = false;
		if (FlexiBookApplication.getCurrentLoginUser() != null) {
			if (FlexiBookApplication.getCurrentLoginUser().getUsername() == "owner" || FlexiBookApplication.getCurrentLoginUser() instanceof Owner) {
				throw new InvalidInputException("you are currently logedin as an owner");
			}
			else {
				throw new InvalidInputException("you must log off of your customer account inorder to create an owner account");
			}
		}
		else if (username == null || username.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The user name cannot be empty");
		}
		else if (password == null || password.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The password cannot be empty");
		}
		else if (flexiBook.getOwner()!= null) { //consider using helper method findCustomer
			//if (user.hasWithUsername(newUsername)){ //can maybe use this instead? it's simpler!
			throw new InvalidInputException("There already exist an owner account");
		}
		else {
			Owner aOwner = new Owner(username, password, flexiBook);
			//assuming signing up also logs you in:
			FlexiBookApplication.setCurrentLoginUser(aOwner); 
			signUpSuccessful = true;
		}
		return signUpSuccessful;
	}
	

	/**
	 * This is a helper method to know if the current BusinessHour overlaps with other Business Hour
	 * @param BusinessHour
	 * @return
	 * @author jedla
	 */
	private static boolean isOverlappingWithBusinessHours(BusinessHour hour) {

		boolean isOverlaping = false;
		List<BusinessHour> hoursList = FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours();


		for(BusinessHour x: hoursList) {
			// check weekday
			if(x.getDayOfWeek() == hour.getDayOfWeek()) {
				// if the appointment is on that day, compare if the time slot is included by business hour
				if (hour.getStartTime().toLocalTime().isBefore(x.getStartTime().toLocalTime())&& hour.getEndTime().toLocalTime().isAfter(x.getStartTime().toLocalTime())) {
					isOverlaping = true;
					break;

				}
				else if (hour.getStartTime().toLocalTime().isAfter(x.getStartTime().toLocalTime())&& hour.getEndTime().toLocalTime().isBefore(x.getEndTime().toLocalTime()) )
				{
					isOverlaping = true;
					break;
				}
				else if (hour.getStartTime().toLocalTime().isAfter(x.getStartTime().toLocalTime())&& hour.getEndTime().toLocalTime().isAfter(x.getEndTime().toLocalTime()) )
				{
					isOverlaping = true;
					break;
				}


			}

		} return isOverlaping;
	}

	/**
	 * This helper method finds the corresponding BusinessHour, that needs to be updated.
	 * @param BusinessHour
	 * @return
	 * @author jedla
	 */
	private static BusinessHour isTheBusinessHour(BusinessHour hour) {

		BusinessHour y = null;

		List<BusinessHour> hoursList = FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours();
		for(BusinessHour x: hoursList) {
			if(x.getDayOfWeek() == hour.getDayOfWeek()&& x.getStartTime()== hour.getStartTime()) {
				y = x;
				break;
			}
		} return y;}

	/**
	 * This helper method finds the corresponding Vacation
	 * @param vacation
	 * @return
	 * @author jedla
	 */
	private static TimeSlot isTheVacation(TimeSlot vacation) {

		TimeSlot y = null;

		List<TimeSlot> vacationList = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();
		for(TimeSlot x: vacationList) {
			if( x.getStartDate() == vacation.getStartDate() && x.getStartTime() == vacation.getStartTime()) {
				y = x;
				break;
			}
		} 
		return y;
	}

	private static TimeSlot isTheHoliday(TimeSlot holiday) {

		TimeSlot y = null;

		List<TimeSlot> holidayList = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		for(TimeSlot x: holidayList) {
			if( x.getStartDate() == holiday.getStartDate() && x.getStartTime() == holiday.getStartTime()) {
				y = x;
				break;
			}
		} 
		return y;
	}


}


