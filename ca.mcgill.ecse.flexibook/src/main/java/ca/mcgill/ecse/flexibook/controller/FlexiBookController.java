package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.*;

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
	 * @return true if added successfully
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
	 * @return true if deleted successfully
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
	 * @return true if updated successfully
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
	 * @return signUpSuccessful boolean
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean signUpCustomer(String username, String password) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		boolean signUpSuccessful = false;
		if (FlexiBookApplication.getCurrentLoginUser() != null) {
			if (FlexiBookApplication.getCurrentLoginUser().getUsername().equals("owner") ) { // alternative: FlexiBookApplication.getCurrentLoginUser() instanceof Owner
				throw new InvalidInputException("You must log out of the owner account before creating a customer account");
			}
		}
		else if (username == null || username.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The user name cannot be empty");
		}
		else if (password == null || password.replaceAll("\\s+", "").length() == 0) {
			throw new InvalidInputException("The password cannot be empty");
		}
		else if (findCustomer(username) != null) { 
			throw new InvalidInputException("The username already exists");
		}
		else {
			Customer aCustomer = new Customer(username, password, flexiBook);
			flexiBook.addCustomer(aCustomer); 
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
	 * @return updateSuccessful boolean
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean updateUserAccount(String currentUsername, String newUsername, String newPassword) throws InvalidInputException { 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		boolean updateSuccessful = false;
		if (!user.getUsername().equals(currentUsername)) {
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
		else if (findCustomer(newUsername) != null) { 
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
	 * @return deleteSuccessful boolean
	 * @throws InvalidInputException
	 * 
	 * @author Catherine
	 */
	public static boolean deleteCustomerAccount(String username) throws InvalidInputException{ 
		User user = FlexiBookApplication.getCurrentLoginUser(); 
		boolean deleteSuccessful = false;
		if (!user.getUsername().equals(username) || user.getUsername().equals("owner")) { //alternative: user instanceof Owner
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
			if (ThisOwner2 != null && ThisOwner2.getPassword().equals(password)) {
				FlexiBookApplication.setCurrentLoginUser(ThisOwner2);
			}
			else if (ThisCustomer != null && ThisCustomer.getPassword().equals(password)) {
				FlexiBookApplication.setCurrentLoginUser(ThisCustomer);
			}
			else if(ThisOwner2 == null && username.equals("owner")){
				signUpOwner(username, password);
			}
			else {
				throw new InvalidInputException("Username/password not found");
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
			throw new InvalidInputException("The user is already logged out");
		}
		else {
			FlexiBookApplication.clearCurrentLoginUser();
		}
	}

	/**
	 * This method defines a new Service Combo.
	 * @param name -name of new Service Combo
	 * @param mainServiceName -name of Main Service associated with the new Service Combo
	 * @param orderedServices -ordered list of services associated with the new Service Combo
	 * @param listOfMandatory -determines whether each service in orderedServices is mandatory
	 * @return boolean whether or not defining new Service Combo was successful
	 * @throws InvalidInputException
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
		//finds mainService
		Service mainService = findSingleService(mainServiceName);
		//throws an exception if main service cannot be found
		if(mainService == null){
			throw new InvalidInputException("Service " + mainServiceName + " does not exist");
		}
		boolean containsMainService = false;
		for(int i = 0; i < orderedServices.size(); i++){
			if(orderedServices.get(i).equals(mainServiceName)){
				containsMainService = true;
				if(!listOfMandatory.get(i)){
					throw new InvalidInputException("Main service must be mandatory");
				}
			}
			Service service = findSingleService(orderedServices.get(i));
			if(service == null){
				throw new InvalidInputException("Service " + orderedServices.get(i) + " does not exist");
			}
		}
		if(!containsMainService){
			throw new InvalidInputException("Main service must be included in the services");
		}

		FlexiBook flexibook = FlexiBookApplication.getFlexiBook();
		//creates new serviceCombo object
		ServiceCombo serviceCombo = new ServiceCombo(name,flexibook);
		//goes through list of orderedServices and creates a ComboItem for every service
		boolean mandatory;
		Service service;
		ComboItem comboItem;
		for(int i = 0; i < orderedServices.size(); i++){
			mandatory = listOfMandatory.get(i);
			service = findSingleService(orderedServices.get(i));
			comboItem = serviceCombo.addService(mandatory, service);
			//sets appropirate main service
			if(service.equals(mainService)){
				serviceCombo.setMainService(comboItem);
			}

		}

		return true;
	}

	/**
	 * This method updates an existing Service Combo.
	 * @param name -name of the existing Service Combo
	 * @param newName -updated name of Service Combo
	 * @param mainServiceName -updated main service
	 * @param orderedServices -updated list of services
	 * @param listOfMandatory -updated corresponding mandatory list
	 * @return boolean whether or not updating existing Service Combo was successful
	 * @throws InvalidInputException
	 * @author gtjarvis
	 */
	public static boolean updateServiceCombo(String name, String newName, String mainServiceName, List<String> orderedServices, List<Boolean> listOfMandatory) throws InvalidInputException { 
		//make sure current user is owner
		if(!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		//throws an exception if length of orderedServices does not match length of listOfMandatory
		if(orderedServices.size() != listOfMandatory.size()){
			throw new InvalidInputException("Error with additional services.");
		}
		//throws an exception if Service Combo does not exist
		if(findServiceCombo(name) == null){
			throw new InvalidInputException("Service combo " + name + " does not exist");
		}
		//throws an exception if new Service Combo already exist
		if(!name.equals(newName) && findServiceCombo(newName) != null){
			throw new InvalidInputException("Service combo " + newName + " already exists");
		}
		//throws an exception if number of services less then 2
		if(orderedServices.size() < 2){
			throw new InvalidInputException("A service Combo must have at least 2 services");
		}
		//finds mainService
		Service mainService = findSingleService(mainServiceName);
		//throws an exception if main service cannot be found
		if(mainService == null){
			throw new InvalidInputException("Service " + mainServiceName + " does not exist");
		}
		boolean containsMainService = false;
		for(int i = 0; i < orderedServices.size(); i++){
			if(orderedServices.get(i).equals(mainServiceName)){
				containsMainService = true;
				if(!listOfMandatory.get(i)){
					throw new InvalidInputException("Main service must be mandatory");
				}
			}
			Service service = findSingleService(orderedServices.get(i));
			if(service == null){
				throw new InvalidInputException("Service " + orderedServices.get(i) + " does not exist");
			}
		}
		if(!containsMainService){
			throw new InvalidInputException("Main service must be included in the services");
		}

		//gets Service Combo
		ServiceCombo serviceCombo = findServiceCombo(name);
		//creates 2 temporary services to update Service Combo with
		Service tmp1 = new Service("tmp1", FlexiBookApplication.getFlexiBook(), 10, 0, 0);
		Service tmp2 = new Service("tmp2", FlexiBookApplication.getFlexiBook(), 10, 0, 0);
		ComboItem tmp1Combo = new ComboItem(true, tmp1, serviceCombo);
		ComboItem tmp2Combo = new ComboItem(true, tmp2, serviceCombo);
		//update Service Combo
		serviceCombo.setName(newName);
		//resets services
		while(serviceCombo.getServices().size() > 2){
			serviceCombo.getService(0).delete();
		}
		boolean mandatory;
		Service service;
		ComboItem comboItem;
		for(int i = 0; i < orderedServices.size(); i++){
			mandatory = listOfMandatory.get(i);
			service = findSingleService(orderedServices.get(i));
			comboItem = serviceCombo.addService(mandatory, service);
			//sets appropirate main service
			if(service.equals(mainService)){
				serviceCombo.setMainService(comboItem);
			}
		}
		serviceCombo.getService(0).delete();
		serviceCombo.getService(0).delete();

		return true;
	}

	/**
	 * This method deletes an existing Service Combo.
	 * @param name -name of the existing Service Combo
	 * @return boolean whether or not deleting existing Service Combo was successful
	 * @throws InvalidInputException
	 * @author gtjarvis
	 */
	public static boolean deleteServiceCombo(String name) throws InvalidInputException{ 
		//make sure current user is owner
		if(!(FlexiBookApplication.getCurrentLoginUser() instanceof Owner)){
			throw new InvalidInputException("You are not authorized to perform this operation");
		}
		BookableService comboService = findBookableService(name);
		if(comboService == null){
			throw new InvalidInputException("Service " + name + " that does not exist.");
		}
		if(getAppointmentsInTheFuture(comboService).size() != 0){
			throw new InvalidInputException("Service combo " + comboService.getName() + " has future appointments");
		}
		comboService.delete();
		return true;
	}


	/**
	 * This method is used to setup the business with all the information
	 * @param buisnessName
	 * @param address
	 * @param phoneNumber
	 * @param email
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void setUpBusinessInfo(String businessName, String address, String phoneNumber, String email) throws InvalidInputException { 
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		String regex = "^(.+)@(.+\\.)(.+)$";


		if (currentUser instanceof Customer) { 
			throw new InvalidInputException("No permission to set up business information");
		}
		else if (email.matches(regex) == false) {
			throw new InvalidInputException("Invalid email"); 
		} 

		else {
			Business business = new Business(businessName, address, phoneNumber, email, flexiBook);
			flexiBook.setBusiness(business);
		}
	}

	/**
	 * This method is used to setup the business hour for a specified day
	 * @param startTime
	 * @param endTime
	 * @param day
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void setUpBusinessHours(Time startTime, Time endTime, DayOfWeek day) throws InvalidInputException{// need to add that the business hours can't overlap
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to update business information");
		}
		else if (isOverlappingWithBusinessHours(day, startTime, endTime,null)) {
			throw new InvalidInputException(" The business hours cannot overlap");
		}
		else if (startTime.toLocalTime().isAfter(endTime.toLocalTime())) {
			throw new InvalidInputException("Start time must be before end time");
		}
		else {     
			BusinessHour bh = new BusinessHour(day,startTime, endTime, flexiBook);
			flexiBook.addHour(bh);
			flexiBook.getBusiness().addBusinessHour(bh);
		}
	}


	/**
	 * This method is used to create a vacation or a holiday
	 * @param type
	 * @param startDate 
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
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(startDate, startTime);
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(endDate, endTime);
		//delete this as it it not useful and might just confuse the whole thing

		if (currentUser instanceof Customer) { 
			throw new InvalidInputException("No permission to set up Holidays or Vacation");
		}
		else if (timeSlotStart.isAfter(timeSlotEnd)){

			throw new InvalidInputException("Start time must be before end time ");
		}
		else {
			if (type.equals("vacation")) {
				if(isOverlappingWithVacation(startDate, startTime, endDate, endTime, null)) {
					throw new InvalidInputException("Vacation times cannot overlap");
				}
				else if (isOverlappingWithHoliday(startDate, startTime, endDate, endTime, null)) {
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				}
				else if (!isInTheFuture(timeSlotStart)) {
					throw new InvalidInputException("Vacation cannot start in the past");
				}
				else {
					TimeSlot vacationHoliday = new TimeSlot(startDate, startTime, endDate, endTime, flexiBook);
					business.addVacation(vacationHoliday);
					flexiBook.addTimeSlot(vacationHoliday);		
				}
			}
			else if (type.equals("holiday")) {
				if(isOverlappingWithVacation(startDate, startTime, endDate, endTime, null)) {
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				}
				else if (isOverlappingWithHoliday(startDate, startTime, endDate, endTime, null)) {
					throw new InvalidInputException("Holiday times cannot overlap");
				}
				else if (!isInTheFuture(timeSlotStart)) {
					throw new InvalidInputException("Holiday cannot start in the past");
				}
				else {
					TimeSlot vacationHoliday = new TimeSlot(startDate, startTime, endDate, endTime, flexiBook);
					business.addHoliday(vacationHoliday);
					flexiBook.addTimeSlot(vacationHoliday);		
				}
			}
		}	 
	}

	/**
	 * This method is used to update a vacation or a holiday
	 * @param type
	 * @param oldStartDate
	 * @param oldStartTime
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void updateHolidayVacation( String type ,Date oldStartDate, Time oldStartTime, Date startDate, Time startTime, Date endDate, Time endTime ) throws InvalidInputException {
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business business = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(startDate, startTime);
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(endDate, endTime);


		if (currentUser instanceof Customer) {
			throw new InvalidInputException("No permission to set up business information");
		}
		else if (timeSlotStart.isAfter(timeSlotEnd)) {
			throw new InvalidInputException("Start time must be before end time ");
		}
		else {
			if (type.equals("vacation")) {
				TimeSlot vacation = isTheVacation(oldStartDate, oldStartTime);//Need to change the method call for isTheVacation

				if(isOverlappingWithVacation(startDate, startTime, endDate, endTime, vacation)) {//Need to change this so that vacation is part of it and so it doesn't overlap
					throw new InvalidInputException("Vacation times cannot overlap");
				}
				else if (isOverlappingWithHoliday(startDate, startTime, endDate, endTime, null)) {//Same as above
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				}
				else if (!isInTheFuture(timeSlotStart)) {//Need to write new isInTheFuture() but only for the timeSlotStart TimeSlotEnd
					throw new InvalidInputException("Vacation cannot start in the past");
				}
				else {
					vacation.setEndDate(endDate);
					vacation.setEndTime(endTime);
					vacation.setStartDate(startDate);
					vacation.setStartTime(startTime);		
				}
			}
			else if (type.equals("holiday")) {	
				TimeSlot holiday = isTheHoliday(oldStartDate, oldStartTime);
				if(isOverlappingWithVacation(startDate, startTime, endDate, endTime, null)) {
					throw new InvalidInputException("Holiday and vacation times cannot overlap");
				}
				else if (isOverlappingWithHoliday(startDate, startTime, endDate, endTime, holiday)) {
					throw new InvalidInputException("Holiday times cannot overlap");
				}
				else if (!isInTheFuture(timeSlotStart)) {
					throw new InvalidInputException("Holiday cannot start in the past");
				}
				else {
					holiday.setEndDate(endDate);
					holiday.setEndTime(endTime);
					holiday.setStartDate(startDate);
					holiday.setStartTime(startTime);		
				}
			}
		}	
	}

	/**
	 * This method is used to remove a holiday or a vacation
	 * @param type
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void removeHolidayVacation(String type, Date startDate, Time startTime, Date endDate, Time endTime) throws InvalidInputException {

		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business business = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to update business information");
		}
		else { 
			if (type.equals("vacation")) {
				business.removeVacation(isTheVacation(startDate, startTime));			
			}

			else if (type.equals("holiday")){
				business.removeHoliday(isTheHoliday(startDate, startTime));
			}
		}		
	}

	/**
	 * This method is used to update business hours
	 * @param oldDay
	 * @param oldStart
	 * @param day
	 * @param newStart
	 * @param newEnd
	 * @throws InvalidInputException
	 * @author jedla
	 */
	public static void updateBusinessHour(DayOfWeek oldDay, Time oldStart, DayOfWeek day, Time newStart, Time newEnd)throws InvalidInputException {

		User currentUser = FlexiBookApplication.getCurrentLoginUser();

		BusinessHour temp = isTheBusinessHour(oldDay, oldStart);

		if (currentUser instanceof Customer) { 
			throw new InvalidInputException("No permission to udpate business hour");
		}
		else if (newStart.toLocalTime().isAfter(newEnd.toLocalTime())) {
			throw new InvalidInputException("Start time must be before end time");
		}
		else if (isOverlappingWithBusinessHours(day, newStart, newEnd, temp)) {
			throw new InvalidInputException("The business hours cannot overlap");

		}
		else {
			temp.setDayOfWeek(day);
			temp.setEndTime(newEnd);
			temp.setStartTime(newStart);
		}
	}

	/**
	 * This method is used to updates the business information
	 * @param businessName
	 * @param address
	 * @param phoneNumber
	 * @throws email
	 * @author jedla
	 */
	public static void updateBusinessInfo(String businessName, String address, String phoneNumber, String email) throws InvalidInputException{
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook(); 
		Business currentBusiness = flexiBook.getBusiness();
		User currentUser = FlexiBookApplication.getCurrentLoginUser();

		String regex = "^(.+)@(.+\\.)(.+)$";
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to set up business information");
		}
		else if (email.matches(regex) == false) {
			throw new InvalidInputException("Invalid email"); 
		} 
		else {
			if (currentBusiness != null) {
				currentBusiness.setAddress(address);
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
		if (currentUser instanceof Customer) { throw new InvalidInputException("No permission to update business information");
		}
		else if (currentUser instanceof Owner){
			currentBusiness.removeBusinessHour(isTheBusinessHour(day, startTime));		
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
	 * This is a method which returns a boolean if the time input is available
	 * TODO: missing features of showing available times, also we don't need to show the service name and the name of the customer 
	 * 
	 * @param date
	 * @param ByDay
	 * @param ByMonth
	 * @param ByYear
	 * @return
	 * @author mikewang
	 */
	public static void viewAppointmentCalendar(String date1, Boolean ByDay, Boolean ByWeek) throws InvalidInputException{
		getUnavailbleTime(date1,ByDay,ByWeek);
		getAvailbleTime(date1,ByDay,ByWeek);
	}



	/**
	 * 
	 * This is a query method which can return all unavailble time slot to an ArrayList
	 * @param date
	 * @param ByDay
	 * @param ByWeek
	 * @author mikewang
	 * @return <TOTimeSlot> getUnavailbleTime
	 */
	public static List<TOTimeSlot> getUnavailbleTime(String date1, Boolean ByDay, Boolean ByWeek) throws InvalidInputException{
		Date date;


		ArrayList<TOTimeSlot> unavailbleTimeSlots = new ArrayList<TOTimeSlot>();

		if (ByDay == true && ByWeek == false) {
			if (isValidDate(date1)) {
				// first check if the input is valid
				date = Date.valueOf(date1);
				// second check if it is in Holiday or Vacation

				if (checkIsInHoliday(date)||checkIsInVacation(date)) {
					DayOfWeek dayOfWeek = ControllerUtils.getDoWByDate(date); 
					for (TOBusinessHour BH: getTOBusinessHour()) {
						if ( BH.getDayOfWeek() == dayOfWeek) {
							TOTimeSlot toHolidayOrVacationTS = new TOTimeSlot(date,BH.getStartTime(),date,BH.getEndTime());
							unavailbleTimeSlots.add(toHolidayOrVacationTS);
						}
					}
				} 
				// if above cases both failed 
				else {
					for (TOAppointment toAppointments: getTOAppointment()) {
						if (toAppointments.getTimeSlot().getStartDate().equals(date)) {
							if (toAppointments.getDownTimeTimeSlot() != null) {
								// TOTimeSlot getUnavailbleTimes = new TOTimeSlot(toAppointments.getTimeSlot().getStartDate(), toAppointments.getTimeSlot().getStartTime(), toAppointments.getTimeSlot().getEndDate(),toAppointments.getTimeSlot().getEndTime());
								// unavailbleTimeSlots.add(getUnavailbleTimes);
								for (TOTimeSlot downTimeTimeSlot: toAppointments.getDownTimeTimeSlot()) {
									if (downTimeTimeSlot.getStartTime().after(toAppointments.getTimeSlot().getStartTime())) {
										TOTimeSlot unavailbleTimeBeforeDownTime = new TOTimeSlot(date, toAppointments.getTimeSlot().getStartTime(), date, downTimeTimeSlot.getStartTime());
										TOTimeSlot unavailbleTimeAfterDownTime = new TOTimeSlot(date, downTimeTimeSlot.getEndTime(), date, toAppointments.getTimeSlot().getEndTime());
										unavailbleTimeSlots.add(unavailbleTimeBeforeDownTime);
										unavailbleTimeSlots.add( unavailbleTimeAfterDownTime);
									}
								}
							}
							else {
								unavailbleTimeSlots.add(toAppointments.getTimeSlot());
							}
						}
					}
				}

			}
			else if(!isValidDate(date1)){

				throw new InvalidInputException(date1 + " is not a valid date");
			}

		}
		else if (ByDay == false && ByWeek == true) {
			//TODO
			// i need to get some sleep, i will resume my part after i get up
			// first check if the input is valid


			if (!isValidDate(date1)) {
				throw new InvalidInputException(date1 + " is not a valid date");
			}
			else {
				for(int i=0;i<7;i++) {
					getUnavailbleTime(date1, true, false);
					date1 = NextDate(date1);
				}
			}
		}
		return unavailbleTimeSlots;
	}




	//implement next time

	/**
	 * DON'T TOUCH MIKE WILL FINISH THIS 
	 * This is a query method which can return all availble time slot to an ArrayList
	 * @param date
	 * @param ByDay
	 * @param ByWeek
	 * @author mikewang
	 * @return
	 */
	public static List<TOTimeSlot> getAvailbleTime(String date1, Boolean ByDay, Boolean ByWeek) throws InvalidInputException{
		List<TOTimeSlot> unavilbleTimes = new ArrayList<TOTimeSlot>();
		List<TOBusinessHour> BusinessHours = new ArrayList<TOBusinessHour>();
		List<TOTimeSlot> DayBusinessHour = new ArrayList<TOTimeSlot>();
		List<TOTimeSlot> DayAvailbleTimes = new ArrayList<TOTimeSlot>();
		Date date;

		if (ByDay == true && ByWeek == false) {
			if (isValidDate(date1)) {
				date = Date.valueOf(date1);
				DayOfWeek dayOfWeek = ControllerUtils.getDoWByDate(date);
				for(TOBusinessHour TBH: getTOBusinessHour()) {
					if (TBH.getDayOfWeek() == dayOfWeek) {
						TOTimeSlot todayBusinessHours = new TOTimeSlot(date, TBH.getStartTime(),date,TBH.getEndTime());

						for (TOTimeSlot dayUnavailbleTimes: sortTimeSlot(getUnavailbleTime(date1,true,false))) {
							if (!todayBusinessHours.getStartTime().equals(todayBusinessHours.getEndTime())){
								if (dayUnavailbleTimes.getStartTime().after(todayBusinessHours.getStartTime())) {
									TOTimeSlot nowAvailableTimeSlot = new TOTimeSlot(date,todayBusinessHours.getStartTime(), date,dayUnavailbleTimes.getStartTime());
									todayBusinessHours.setStartTime(dayUnavailbleTimes.getEndTime());
									DayAvailbleTimes.add(nowAvailableTimeSlot);
								}else if (dayUnavailbleTimes.getStartTime().equals(todayBusinessHours.getStartTime())) {
									todayBusinessHours.setStartTime(dayUnavailbleTimes.getEndTime());
								}
							}
							else {
								break;
							}

						}
						if (!todayBusinessHours.getStartTime().equals(todayBusinessHours.getEndTime())){
							DayAvailbleTimes.add(todayBusinessHours);
						}

					}
				}


			}
			else {
				throw new InvalidInputException(date1 + " is not a valid date");
			}


		}

		if (ByDay == false && ByWeek == true) {
			//TODO
			if (!isValidDate(date1)) {
				throw new InvalidInputException(date1 + " is not a valid date");
			}
			for(int i=0;i<7;i++) {
				getAvailbleTime(date1, true, false);
				date1 = NextDate(date1);
			}

		}
		return DayAvailbleTimes;
	}




	/**
	 * this is an qurey method with returns the BusinessHour 
	 * @return
	 * @author mikewang
	 */
	public static List<TOBusinessHour> getTOBusinessHour(){
		ArrayList<TOBusinessHour> businessHours = new ArrayList<TOBusinessHour>();
		for (BusinessHour BH: FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours()) {
			TOBusinessHour BusinessHour = new TOBusinessHour(BH.getDayOfWeek(),BH.getStartTime(),BH.getEndTime());
			businessHours.add(BusinessHour);
		}
		return businessHours;
	}


	/**
	 * This is a query method which can gives a list of all TOAppointment 
	 * Which includes all appointments combo items and time slot 
	 * @return
	 * @author mikewang
	 * @author AntoineW later made a change
	 */
	public static List<TOAppointment> getTOAppointment(){
		ArrayList<TOAppointment> appointments = new ArrayList<TOAppointment>();
		for (Appointment appointment: FlexiBookApplication.getFlexiBook().getAppointments()) {

			TOAppointment toAppointment = new TOAppointment(appointment.getCustomer().getUsername(),
					appointment.getBookableService().getName(), CovertToTOTimeSlot(appointment.getTimeSlot()));
			// Added feature TOAppointment can show all downtime
			// by mikewang
			for (TOTimeSlot toTimeSlots: ControllerUtils.getDowntimesByAppointment(appointment)) {
				toAppointment.addDownTimeTimeSlot(toTimeSlots); //= 
			}
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
	 * This is a query method which can get list of all services in the system
	 * @return a list of services
	 * @author chengchen
	 */
	public static List<TOService> getTOServices(){
		List<TOService> toServices = new ArrayList<TOService>();
		for (BookableService bookableService:FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (bookableService instanceof Service) {
				TOService toService = new TOService(bookableService.getName(), ((Service) bookableService).getDuration(), ((Service) bookableService).getDowntimeDuration(), ((Service) bookableService).getDowntimeStart());
				toServices.add(toService);
			}
		}
		return toServices;

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
	 * This is a query method which can get the business information from the business in the system
	 * @return
	 * @author jedla
	 */
	public static TOBusiness getBusinessInfo(){
		TOBusiness business = new TOBusiness(FlexiBookApplication.getFlexiBook().getBusiness().getName(), FlexiBookApplication.getFlexiBook().getBusiness().getAddress(), 
				FlexiBookApplication.getFlexiBook().getBusiness().getPhoneNumber(), FlexiBookApplication.getFlexiBook().getBusiness().getEmail());
		return business;
	}

	/**
	 * This is a query method which returns all Service Combos as TOServiceCombo objects
	 * @return list of TOServiceCombo objects
	 * @author gtjarvis
	 */
	public static List<TOServiceCombo> getTOServiceCombos(){
		List<ServiceCombo> serviceCombos = getServiceCombos();
		List<TOServiceCombo> serviceCombosTO = new ArrayList<TOServiceCombo>();
		List<ComboItem> comboItems;
		for(ServiceCombo s: serviceCombos){
			TOServiceCombo sc = new TOServiceCombo(s.getName());
			comboItems = s.getServices();
			for(ComboItem c: comboItems){
				TOComboItem comboItemTO = new TOComboItem(c.getMandatory(),c.getService().getName());
				sc.addService(comboItemTO);
			}
			serviceCombosTO.add(sc);
		}
		return serviceCombosTO;
	}

	/**
	 * This is a query method which returns a specific Service Combo by name as a TOServiceCombo object
	 * @return corresponding TOServiceCombo object
	 * @author gtjarvis
	 */
	public static TOServiceCombo getTOServiceCombo(String name){
		List<TOServiceCombo> scList = getTOServiceCombos();
		for(TOServiceCombo s: scList){
			if(s.getName().equals(name)){
				return s;
			}
		}
		return null;
	}



	/*----------------------------------------------- private helper methods -----------------------------------------------------*/


	/**
	 * This method finds the service with specified name
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
	 * @param name - the name of the service to found 
	 * @return the service found 
	 * 	
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
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
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
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
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
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
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
	 * This is a private helper method but we put it public in this stage for testing.
	 * Returns a list of all the existing Service Combos
	 * @return List of all existing Service Combos
	 * @author gtjarvis
	 */
	public static List<ServiceCombo> getServiceCombos() {
		List<ServiceCombo> scList = new ArrayList<ServiceCombo>();
		for (BookableService bservice : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (bservice instanceof ServiceCombo) {
				scList.add((ServiceCombo)bservice);
			}
		}
		return scList;
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
	 * Returns list of appointments in the future for a specific service
	 * @param b -specific bookable service
	 * @return list of appointments for that service
	 * @author gtjarvis
	 */
	private static List<Appointment> getAppointmentsInTheFuture(BookableService b) {
		List<Appointment> appointments = b.getAppointments();
		List<Appointment> futureAppointments = new ArrayList<Appointment>();
		for(Appointment a: appointments){
			if(isInTheFuture(a.getTimeSlot())){
				futureAppointments.add(a);
			}
		}
		return futureAppointments;
	}

	/**
	 * wrapper method of isNotOverlapWithOtherTimeSlots(TimeSlot timeSlot), isDuringDowntime(TimeSlot timeSlot), 
	 * isDuringWorkTime(TimeSlot timeSlot) and isInTheFuture(TimeSlot timeSlot)
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
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
	 * @param userName
	 * @return
	 * @author mikewang
	 */
	public static Customer findCustomer (String userName){
		for (Customer user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (user.getUsername().equals( userName) ) {
				return user;
			}
		}
		return null;
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
	 * This is a helper method witch sorts the TOTimeSlots based on their start time
	 * @param TimeSlots
	 * @return
	 * @author mikewang
	 */
	public static List<TOTimeSlot> sortTimeSlot(List<TOTimeSlot> TimeSlots){
		Collections.sort(TimeSlots, new CustomComparator());
		return TimeSlots;
	}

	/**
	 * This method is a helper method for finding a particular user by username.
	 * User can be the owner or a customer 
	 * 
	 * This is a private helper method but we put it public in this stage for testing.
	 * 
	 * @param username
	 * @return User with username or null
	 * @author Catherine
	 */
	public static User findUser(String username){
		User foundUser;
		if (username.equals("owner")) {
			foundUser = FlexiBookApplication.getFlexiBook().getOwner();	
		}
		else {
			foundUser = findCustomer(username);
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
	 * 
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
	 * This is a helper method which checks if a specific date in within a holiday
	 * @param date
	 * @return
	 * @author mikewang
	 */
	private static boolean checkIsInHoliday(Date date) {
		Boolean isInHoliday = false; 
		List<TimeSlot> holidayList = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		for(TimeSlot x: holidayList) {
			if ((date.after(x.getStartDate()) && date.before(x.getEndDate())) || date.equals(x.getStartDate()) || date.equals(x.getEndDate())) {
				isInHoliday = true; 
			}
		}
		return isInHoliday; 
	}


	/**
	 * This is a helper method which checks if a specific date in within a vacation
	 * @param date
	 * @return
	 * @author mikewang
	 */
	private static boolean checkIsInVacation(Date date) {
		Boolean isInVacation = false; 
		List<TimeSlot> VacationList = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();
		for(TimeSlot x: VacationList) {
			if ((date.after(x.getStartDate()) && date.before(x.getEndDate())) || date.equals(x.getStartDate()) || date.equals(x.getEndDate())) {
				isInVacation = true; 
			}
		}
		return isInVacation; 
	}


	/**
	 * This is a helper method which could detect if certain string writen date is valid
	 * @param s
	 * @return
	 * @author mikewang
	 */
	private static boolean isValidDate(String s){
		Boolean isValid = true; 
		if (s == null) {
			isValid = false; 
		}
		final int YEAR_LENGTH = 4;
		final int MONTH_LENGTH = 2;
		final int DAY_LENGTH = 2;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		final int MAX_YEAR = 8099;
		final int MIN_YEAR = 1970;
		int firstDash = s.indexOf('-');
		int secondDash = s.indexOf('-', firstDash + 1);
		int len = s.length();

		if ((firstDash <= 0) || (secondDash <= 0) || (secondDash >= len - 1)) {
			isValid = false; 
		}

		else if (firstDash != YEAR_LENGTH ||
				(secondDash - firstDash <= 1 || secondDash - firstDash > MONTH_LENGTH + 1) ||
				(len - secondDash <= 1 && len - secondDash > DAY_LENGTH + 1)) {
			isValid = false; 
		}

		int year = Integer.parseInt(s, 0, firstDash, 10);
		int month = Integer.parseInt(s, firstDash + 1, secondDash, 10);
		int day = Integer.parseInt(s, secondDash + 1, len, 10);

		if ((month < 1 || month > MAX_MONTH) || (day < 1 || day > MAX_DAY) || (year < MIN_YEAR || year > MAX_YEAR)) {
			isValid = false; 
		}

		return isValid;
	}

	/**
	 * This is a helper method which would return the string version of the date of the next day 
	 * @param date1
	 * @return
	 * @author mikewang
	 */
	public static String NextDate(String date1) {
		String resultDate;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		final int MAX_YEAR = 8099;
		Date d = null;

		int firstDash = date1.indexOf('-');
		int secondDash = date1.indexOf('-', firstDash + 1);
		int len = date1.length();

		int year = Integer.parseInt(date1, 0, firstDash, 10);
		int month = Integer.parseInt(date1, firstDash + 1, secondDash, 10);
		int day = Integer.parseInt(date1, secondDash + 1, len, 10);

		if (day <= MAX_DAY - 1) {
			day = day+1;
		}
		else if(month <= MAX_MONTH -1) {
			day = 1;
			month = month + 1;
		}
		else if (year <= MAX_YEAR - 1) {
			day = 1; 
			month = 1;
			year = year + 1;
		}
		resultDate = year+"-"+month+"-"+day;
		return resultDate;

	}




	public static Date valueOf(String s) {
		if (s == null) {
			throw new java.lang.IllegalArgumentException();
		}
		final int YEAR_LENGTH = 4;
		final int MONTH_LENGTH = 2;
		final int DAY_LENGTH = 2;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		Date d = null;

		int firstDash = s.indexOf('-');
		int secondDash = s.indexOf('-', firstDash + 1);
		int len = s.length();

		if ((firstDash > 0) && (secondDash > 0) && (secondDash < len - 1)) {
			if (firstDash == YEAR_LENGTH &&
					(secondDash - firstDash > 1 && secondDash - firstDash <= MONTH_LENGTH + 1) &&
					(len - secondDash > 1 && len - secondDash <= DAY_LENGTH + 1)) {
				int year = Integer.parseInt(s, 0, firstDash, 10);
				int month = Integer.parseInt(s, firstDash + 1, secondDash, 10);
				int day = Integer.parseInt(s, secondDash + 1, len, 10);

				if ((month >= 1 && month <= MAX_MONTH) && (day >= 1 && day <= MAX_DAY)) {
					d = new Date(year - 1900, month - 1, day);
				}
			}
		}
		if (d == null) {
			throw new java.lang.IllegalArgumentException();
		}

		return d;

	}





	/**
	 * This is a helper method to know if the current BusinessHour overlaps with other business hours
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @param notToInclude
	 * @return
	 * @author jedla
	 */
	private static boolean isOverlappingWithBusinessHours(DayOfWeek day, Time startTime, Time endTime, BusinessHour notToInclude) {

		boolean isOverlapping = true;
		Business business = FlexiBookApplication.getFlexiBook().getBusiness();
		List<BusinessHour> hoursList = business.getBusinessHours();
		LocalTime newStartTime = startTime.toLocalTime();
		LocalTime newEndTime = endTime.toLocalTime();


		for(BusinessHour x: hoursList) {
			// check weekday
			if(x.getDayOfWeek() == day && business.indexOfBusinessHour(notToInclude) != business.indexOfBusinessHour(x)) {
				LocalTime currentStartTime = x.getStartTime().toLocalTime();
				LocalTime currentEndTime = x.getEndTime().toLocalTime();
				// if the appointment is on that day, compare if the time slot is included by business hour
				if(currentStartTime.equals(newStartTime)|| currentEndTime.equals(newEndTime)||(newStartTime.isAfter(currentStartTime)&&newStartTime.isBefore(currentEndTime))||
						(newEndTime.isAfter(currentStartTime)&&newEndTime.isBefore(currentEndTime))) {
					isOverlapping = true;
					break;
				}
				else {
					isOverlapping = false;}
			}
			else {
				isOverlapping = false;
			}
		}return isOverlapping;
	}

	/**
	 * This helper method finds if the current TimeSlot is overlapping with a vacation
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @param notToInclude
	 * @return 
	 * @author jedla 
	 */
	private static boolean isOverlappingWithVacation(Date startDate, Time startTime, Date endDate, Time endTime, TimeSlot notToInclude) {
		boolean isOverlapping = true;
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(startDate, startTime);
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(endDate, endTime);
		Business business = FlexiBookApplication.getFlexiBook().getBusiness();

		for (TimeSlot vacation :flexiBook.getBusiness().getVacation()){
			if (business.indexOfVacation(vacation) != business.indexOfVacation(notToInclude)) {
				LocalDateTime vacationStart = ControllerUtils.combineDateAndTime(vacation.getStartDate(), vacation.getStartTime());
				LocalDateTime vacationEnd = ControllerUtils.combineDateAndTime(vacation.getEndDate(), vacation.getEndTime());

				if(timeSlotEnd.isEqual(vacationEnd) || timeSlotStart.isEqual(vacationStart)|| (timeSlotStart.isAfter(vacationStart)&&timeSlotStart.isBefore(vacationEnd) 
						||(timeSlotEnd.isAfter(vacationStart)&&timeSlotEnd.isBefore(vacationEnd)))) {
					isOverlapping = true;
					break;
				}
				else {
					isOverlapping = false;
				}}
			else isOverlapping = false;
		} return isOverlapping;
	}

	/**
	 * This helper method finds if the current TimeSlot is overlapping with a Holiday
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @param NotToInclude
	 * @return
	 * @author jedla
	 */
	private static boolean isOverlappingWithHoliday(Date startDate, Time startTime, Date endDate, Time endTime, TimeSlot notToInclude) {
		boolean isOverlapping = true ;
		FlexiBook flexiBook = FlexiBookApplication.getFlexiBook();
		LocalDateTime timeSlotStart = ControllerUtils.combineDateAndTime(startDate, startTime);
		LocalDateTime timeSlotEnd = ControllerUtils.combineDateAndTime(endDate, endTime);
		Business business = FlexiBookApplication.getFlexiBook().getBusiness();
		for (TimeSlot holiday :flexiBook.getBusiness().getHolidays()){
			if (business.indexOfHoliday(holiday) != business.indexOfHoliday(notToInclude)) {
				LocalDateTime holidayStart = ControllerUtils.combineDateAndTime(holiday.getStartDate(), holiday.getStartTime());
				LocalDateTime holidayEnd = ControllerUtils.combineDateAndTime(holiday.getEndDate(), holiday.getEndTime());

				if(timeSlotEnd.isEqual(holidayEnd) || timeSlotStart.equals(holidayStart)|| (timeSlotStart.isAfter(holidayStart)&&timeSlotStart.isBefore(holidayEnd) 
						||(timeSlotEnd.isAfter(holidayStart)&&timeSlotEnd.isBefore(holidayEnd)))) {
					isOverlapping = true;
					break;
				}
				else {
					isOverlapping = false;
				}}
			else {isOverlapping =false;

			}
		} return isOverlapping;

	}

	/**
	 * This helper method finds the corresponding BusinessHour
	 * @param day
	 * @param startTime
	 * @return
	 * @author jedla
	 */
	public static BusinessHour isTheBusinessHour(DayOfWeek day, Time startTime) {

		List<BusinessHour> hoursList = FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours();
		for(BusinessHour x: hoursList) {
			if(x.getDayOfWeek().equals(day) && x.getStartTime().equals(startTime)) {
				return x;
			}
		} return null;
	}

	/**
	 * This helper method finds the corresponding Vacation
	 * @param startDate
	 * @param startTime
	 * @return
	 * @author jedla
	 */
	private static TimeSlot isTheVacation(Date startDate, Time startTime) {


		List<TimeSlot> vacationList = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();
		for(TimeSlot x: vacationList) {
			if( x.getStartDate().equals(startDate) && x.getStartTime().equals(startTime)) {
				return x;
			}
		} 
		return null;
	}

	/**
	 * This helper method finds the corresponding Holiday
	 * @param startDate
	 * @param startTime
	 * @return
	 * @author jedla
	 */

	private static TimeSlot isTheHoliday(Date startDate, Time startTime) {

		List<TimeSlot> holidayList = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		for(TimeSlot x: holidayList) {
			if( x.getStartDate().equals(startDate)&& x.getStartTime().equals(startTime)) {
				return x;
			}
		} 
		return null;
	}

	/**
	 * This helper method finds the corresponding Holiday
	 * @param start
	 * @return
	 * @author jedla inspired by AntoineW
	 */

	private static boolean isInTheFuture(LocalDateTime start) {
		boolean isFuture = true;
		Date currentDate = FlexiBookApplication.getCurrentDate(true);
		Time currentTime = FlexiBookApplication.getCurrentTime(true);
		LocalDateTime now = ControllerUtils.combineDateAndTime(currentDate, currentTime);
		if(start.isBefore(now)) {
			isFuture = false;
		}
		return isFuture;

	}



}





