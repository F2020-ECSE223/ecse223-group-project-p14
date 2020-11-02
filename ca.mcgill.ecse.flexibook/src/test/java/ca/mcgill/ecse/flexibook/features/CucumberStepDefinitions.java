package ca.mcgill.ecse.flexibook.features;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.StringContains;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.controller.CustomComparator;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointment;
import ca.mcgill.ecse.flexibook.controller.TOBusinessHour;
import ca.mcgill.ecse.flexibook.controller.TOComboItem;
import ca.mcgill.ecse.flexibook.controller.TOTimeSlot;
import ca.mcgill.ecse.flexibook.model.Appointment;
import ca.mcgill.ecse.flexibook.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse.flexibook.model.BookableService;
import ca.mcgill.ecse.flexibook.model.Business;
import ca.mcgill.ecse.flexibook.model.BusinessHour;
import ca.mcgill.ecse.flexibook.model.ComboItem;
import ca.mcgill.ecse.flexibook.model.Customer;
import ca.mcgill.ecse.flexibook.model.User;
import ca.mcgill.ecse.flexibook.model.FlexiBook;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.model.ServiceCombo;
import ca.mcgill.ecse.flexibook.model.TimeSlot;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.event.Result;



public class CucumberStepDefinitions {
	private FlexiBook flexiBook;

	private Owner owner;
	private Business business;
	private String error;
	private int errorCntr; 
	private Service aService;
	private Appointment previousAppointment;
	private Appointment currentAppointment;
	
	private Appointment specificAppointment;
	



	private int appointmentCount = 0;
	private int errorCount = 0;
	private int customerCount = 0;
	private boolean statusOfAppointment = false;

	private boolean isSetUp = false;
	private BusinessHour newBusinessHour;

	private boolean statusOfAccount = false;
	
	
	@Before
	public static void setUp() {
		// clear all data
		FlexiBookApplication.getFlexiBook().delete();
	}
	
	@After
	public void tearDown() {
		flexiBook.delete();
	}

	/**
	 * As an owner, I want to log in so that I can access the space to manage my business. 
	 * As a customer, I want to log in so that I can manage my appointments.
	 * The owner account is created automatically if it does not exist.
	 *
	 * @author mikewang
	 */
	
	/*---------------------------Test Log in--------------------------*/

	/**
	 * 
	 * @param username
	 * @param password
	 * @author mikewang
	 */
	@When("the user tries to log in with username {string} and password {string}")
	public void the_user_tries_to_log_in_with_username_and_password(String username, String password) {
		try {
			FlexiBookController.logIn(username, password);
		} catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	/**
	 * 
	 * @author mikewang
	 */
	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
		assertEquals(false, FlexiBookApplication.getCurrentLoginUser()==null);
	}

	/**
	 * @author mikewang
	 */
	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		assertEquals(true, FlexiBookApplication.getCurrentLoginUser()==null);
	}

	/**
	 * @author mikewang
	 */
	@Then("a new account shall be created")
	public void a_new_account_shall_be_created() {
		assertEquals(true, flexiBook.getOwner()!=null);
	}

	/**
	 * @author mikewang
	 */
	@Then("the user shall be successfully logged in")
	public void the_user_shall_be_successfully_logged_in() {
		assertEquals(true, FlexiBookApplication.getCurrentLoginUser() instanceof Owner);	    
	}

	
	
	
	/**
	 * 
	 * As a user, I want to view the appointment calendar so that I can select a time 
	 * slot for my appointment and/or browse my scheduled appointments.
	 * 
	 * @author mikewang
	 */
	/*---------------------------Test view appointment calendar--------------------------*/
	
	
	/**
	 * 
	 * @author mikewang
	 */

	@Given ("the business has the following opening hours:")
	public void the_business_has_the_following_opening_hours_1(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {

			BusinessHour bh = new BusinessHour(null, stringToTime(map.get("startTime")), stringToTime(map.get("endTime")), flexiBook);
			if(map.get("day").equals("Monday")) {
				bh.setDayOfWeek(DayOfWeek.Monday);
			}else if (map.get("day").equals("Tuesday")) {
				bh.setDayOfWeek(DayOfWeek.Tuesday);
			}else if (map.get("day").equals("Wednesday")) {
				bh.setDayOfWeek(DayOfWeek.Wednesday);
			}else if (map.get("day").equals("Thursday")) {
				bh.setDayOfWeek(DayOfWeek.Thursday);
			}else if (map.get("day").equals("Friday")) {
				bh.setDayOfWeek(DayOfWeek.Friday);
			}else if (map.get("day").equals("Saturday")) {
				bh.setDayOfWeek(DayOfWeek.Saturday);
			}else if (map.get("day").equals("Sunday")) {
				bh.setDayOfWeek(DayOfWeek.Sunday);
			}
			flexiBook.getBusiness().addBusinessHour(bh);
			flexiBook.addHour(bh);
		}
	}

	/**
	 * 
	 * @param datatable
	 * @author mikewang
	 */
	@Given ("the business has the following holidays:")
	public void the_business_has_the_following_holidays_1(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {

			TimeSlot ts = new TimeSlot(stringToDate(map.get("startDate")), 
					stringToTime(map.get("startTime")), stringToDate(map.get("endDate")), stringToTime(map.get("endTime")), flexiBook);
			flexiBook.getBusiness().addHoliday(ts);

		}			 
	}
	
	/**
	 * 
	 * @param user
	 * @param date
	 * @author mikewang
	 */
	@When("{string} requests the appointment calendar for the week starting on {string}")
	public void requests_the_appointment_calendar_for_the_week_starting_on(String user, String date) {
		try{
			FlexiBookController.viewAppointmentCalendar(date, false, true);
		}catch(InvalidInputException e){
			error += e.getMessage();
			errorCntr++;
		}
		
	}

	/**
	 * 
	 * @param datatable
	 * @throws InvalidInputException
	 * @author mikewang
	 * 
	 */
	@Then("the following slots shall be unavailable:")
	public void the_following_slots_shall_be_unavailable(List<Map<String, String>> datatable) throws InvalidInputException {
		Boolean isUnavailable = false;
		for(Map<String, String> map : datatable) {
			for (TOTimeSlot time:getUnavailbleTime(map.get("date"), true, false)) {
				if (stringToTime(map.get("startTime")).after(time.getStartTime())) {
					if (stringToTime(map.get("startTime")).before(time.getEndTime())) {
						isUnavailable = true;
						assertEquals(true, isUnavailable);
					}

				}
				else if (stringToTime(map.get("startTime")).before(time.getStartTime())) {
					if (stringToTime(map.get("endTime")).after(time.getStartTime())){
						isUnavailable = true;
						assertEquals(true, isUnavailable);
					}
				}
			}
		}
	}

	
	/**
	 * 
	 * @param username
	 * @param date
	 * @author mikewang
	 * 
	 */
	
	@When("{string} requests the appointment calendar for the day of {string}")
	public void requests_the_appointment_calendar_for_the_day_of(String username, String date){
		try{
			FlexiBookController.viewAppointmentCalendar(date, true, false);
		} catch(InvalidInputException e){
			error += e.getMessage();
			errorCntr++;
		}
		
	}



	/**
	 * 
	 * @param datatable
	 * @throws InvalidInputException
	 * @author mikewang
	 * 
	 */
	@Then("the following slots shall be available:")
	public void the_following_slots_shall_be_available(List<Map<String, String>> datatable) throws InvalidInputException {
		Boolean isAvailable = false;
		for(Map<String, String> map : datatable) {
			for (TOTimeSlot time:getAvailbleTime(map.get("date"), true, false)) {
				if (stringToTime(map.get("startTime")).after(time.getStartTime())) {
					if (stringToTime(map.get("startTime")).before(time.getEndTime())) {
						isAvailable = true;
						assertEquals(true, isAvailable);
					}

				}
				else if (stringToTime(map.get("startTime")).before(time.getStartTime())) {
					if (stringToTime(map.get("endTime")).after(time.getStartTime())){
						isAvailable = true;
						assertEquals(true, isAvailable);
					}
				}
			}
		}
	}




	/**
	 * As a user, I want to log out of the application so that the next user 
	 * does not have access to my information
	 * @author mikewang
	 * 
	 */
	/*---------------------------Test Log out--------------------------*/
	/**
	 * As a user, I want to log out of the application so that the next user 
	 * does not have access to my information
	 * @author mikewang
	 */
	@Given("the user is logged out")
	public void the_user_is_logged_out() {
		assertEquals(null, FlexiBookApplication.getCurrentLoginUser());
	}

	/**
	 * @author mikewang
	 * 
	 */
	@When("the user tries to log out")
	public void the_user_tries_to_log_out() {
		try {
			FlexiBookController.logOut();
		} catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}   
	}




	/**
	 * 
	 * 
	 *  As a business owner, I wish to add services to my business 
	 *  so that my customers can make appointments for them.
	 *  
	 *  @author chengchen
	 */

	/*---------------------------Test Add Service--------------------------*/
	/**
	 * @author chengchen
	 */
	@Given("an owner account exists in the system")
	public void anOwnerAccountExists() {
		owner = new Owner("owner", "owner", flexiBook);
		flexiBook.setOwner(owner);

	}
	/**
	 * @author chengchen
	 */
	@Given("the Owner with username {string} is logged in")
	public void theOwnerWithUsernameIsLoggedIn(String username) {
		FlexiBookApplication.setCurrentLoginUser(owner);
	}
	/**
	 * @author chengchen
	 */
	@When("{string} initiates the addition of the service {string} with duration {string}, start of down time {string} and down time duration {string}")
	public void initiates_the_addition_of_the_service_with_duration_start_of_down_time_and_down_time_duratrion(String username, String name, String duration, String downtimeStart, String downtimeDuration) throws Throwable{

		try {
			FlexiBookController.addService(name,Integer.parseInt(duration),Integer.parseInt(downtimeStart),Integer.parseInt(downtimeDuration));
		}
		catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	/**
	 * @author chengchen
	 */
	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String name) {
		assertEquals(name, findSingleService(name).getName());
	}
	/**
	 * @author chengchen
	 */
	@Then("the service {string} shall have duration {string}, start of down time {string} and down time duration {string}")
	public void the_service_shall_have_duration_start_of_down_time_and_down_time_duration(String name, String duration, String downtimeStart, String downtimeDuration) {
		Service service = (Service)flexiBook.getBookableServices().get(0);
		assertEquals(Integer.parseInt(duration),service.getDuration());
		assertEquals(Integer.parseInt(downtimeDuration),service.getDowntimeDuration());
		assertEquals(Integer.parseInt(downtimeStart),service.getDowntimeStart());

	}
	/**
	 * @author chengchen
	 */
	@Then("the number of services in the system shall be {string}")
	public void the_number_of_services_shall_be(String numService) {
		List<Service> services = new ArrayList<Service>();
		for (BookableService bookableService :flexiBook.getBookableServices()) {
			if (bookableService instanceof Service) {
				services.add((Service) bookableService);
			}
		}
		assertEquals(Integer.parseInt(numService), services.size());
	}
	/**
	 * @author chengchen
	 */
	@Then("an error message with content {string} shall be raised")
	public void an_error_message_with_content_shall_be_raised(String errorMsg) {
		assertEquals(errorMsg, error);
	}
	/**
	 * @author chengchen
	 */
	@Then("the service {string} shall not exist in the system")
	public void the_service_shall_not_exist(String name) {
		assertEquals(null, findBookableService(name));;
	}
	/**
	 * @author chengchen
	 */
	@Then("the number of services in the system shall be zero {string}")
	public void the_number_of_services_shall_be_zero(String numService) {
		assertEquals(Integer.parseInt(numService), flexiBook.numberOfBookableServices());
	}
	/**
	 * @author chengchen
	 */
	@Then("the service {string} shall still preserve the following properties:")
	public void the_service_shall_still_preserve_the_following_properties(String name, List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {
			assertEquals(Integer.parseInt(map.get("duration")),findSingleService(name).getDuration());
			assertEquals(Integer.parseInt(map.get("downtimeDuration")),findSingleService(name).getDowntimeDuration());
			assertEquals(Integer.parseInt(map.get("downtimeStart")),findSingleService(name).getDowntimeStart()); 
		}

	}
	/**
	 * @author chengchen
	 */
	@Then("the number of services in the system shall be {int}")
	public void the_number_of_services_in_the_system_shall_be(int number) {
		assertEquals(1, flexiBook.getBookableServices().size());
	}
	/**
	 * @author chengchen
	 */
	@Given("Customer with username {string} is logged in")
	public void customer_with_username_is_logged_in(String username) {
		Customer customer = findCustomer(username);
		FlexiBookApplication.setCurrentLoginUser(customer);
	}



	/**
	 * @author chengchen
	 * As a business owner, I wish to delete a service 
	 * so that I can keep my customers up to date.
	 */
	/*---------------------------Test delete Service--------------------------*/
	/**
	 * @author chengchen
	 */
	@When("{string} initiates the deletion of service {string}")
	public void initiates_the_deletion_of_service(String username, String serviceName) throws Throwable {
		try {
			FlexiBookController.deleteService(serviceName);
		} catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	/**
	 * 
	 * helper method bing used 
	 * @author chengchen
	 */
	@Then("the number of appointments in the system with service {string} shall be {string}")
	public void the_number_of_appointments_in_the_system_with_service_shall_be(String serviceName, String numService) {
		List<Appointment> appointments= findAppointmentByServiceName(serviceName);
		assertEquals(Integer.parseInt(numService), appointments.size());
	}
	/**
	 * @author chengchen
	 */
	@Then("the number of appointments in the system shall be {string}")
	public void the_number_of_appointments_in_the_system_shall_be(String numAppointment) {
		assertEquals(Integer.parseInt(numAppointment), flexiBook.getAppointments().size());
	}

	/**
	 * 
	 * @author chengchen
	 */
	@Then("the service combos {string} shall not exist in the system")
	public void the_service_combos_shall_not_exist_in_the_system(String comboName) {
		assertTrue(findServiceCombo(comboName)==null);
	}
	
	
	/**
	 * @author chengchen
	 */
	@Then("the service combos {string} shall not contain service {string}")
	public void the_service_combos_shall_not_contain_service(String comboName,String serviceName) {
		ServiceCombo serviceCombo = findServiceCombo(comboName);
		for (ComboItem comboItem:serviceCombo.getServices()) {
			assertEquals(false, comboItem.getService().getName().equals(serviceName));
		}

	}


	/**
	 * @author chengchen
	 * As a business owner, I wish to update my existing services 
	 * in my business so that I can keep my customers up to date.
	 *
	 */
	/*------------------------------Test update Service--------------------------*/

	/**
	 * @author chengchen
	 */
	@When("{string} initiates the update of the service {string} to name {string}, duration {string}, start of down time {string} and down time duration {string}")
	public void initiates_the_update_of_the_service_to_name_duration_start_of_down_time_and_down_time_duration(String username, String serviceName, String newServiceName, String newDuration, String newDowntimeStart, String newDowntimeDuration) throws InvalidInputException{
		try {
			FlexiBookController.updateService(serviceName,newServiceName, Integer.parseInt(newDuration), Integer.parseInt(newDowntimeDuration), Integer.parseInt(newDowntimeStart));
		} catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}

	}
	/**
	 * @author chengchen
	 */
	@Then("the service {string} shall be updated to name {string}, duration {string}, start of down time {string} and down time duration {string}")
	public void the_service_shall_be_updated_to_name_duration_start_of_down_time_and_down_time_duration(String serviceName, String newServiceName, String newDuration, String newDowntimeStart, String newDowntimeDuration) {
		for (BookableService bookableService:flexiBook.getBookableServices()) {
			if (bookableService instanceof Service) {
				if (bookableService.getName().equals(serviceName)) {
					assertEquals(Integer.parseInt(newDowntimeDuration), ((Service) bookableService).getDowntimeDuration());
					assertEquals(Integer.parseInt(newDowntimeStart), ((Service) bookableService).getDowntimeStart());
					assertEquals(Integer.parseInt(newDuration), ((Service) bookableService).getDuration());
				}
			}
		}


	}





	/**
	 * 
	 * @author AntoineW
	 *
	 * @Feature: Make appointment
  		As a customer, I wish to be able to make an appointment so that I can schedule a service
	 */
	//-------------------------------------------- make app -----------------------------------------------------------------------------


	/**
	 * @author Catherine 
	 * @author AntoineW
	 * @author chengchen
	 */
	@Given("a Flexibook system exists")
	public void a_Flexibook_System_Exists() {
		flexiBook = FlexiBookApplication.getFlexiBook();
		FlexiBookApplication.clearCurrentLoginUser();
		error = "";
		errorCount = 0;
		appointmentCount = flexiBook.getAppointments().size();
	}

	/**
	 * 
	 * @param string
	 * @author AntoineW
	 */
	@Given("the system's time and date is {string}")
	public void system_Time_And_Date_Is(String string) {

		List<String> dateTime = ControllerUtils.parseString(string, "+");

		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));

		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));

	}

	/**
	 * @author AntoineW
	 */
	@Given ("a business exists in the system")
	public void a_Business_Exists_In_The_System() {
		Business b = new Business("test", "test", "test", "test", flexiBook);
		flexiBook.setBusiness(b);
	}
	
	/**
	 * 
	 * @param datatable
	 * @author AntoineW
	 */
	@Given ("the following customers exist in the system:")
	public void the_Following_Customers_Exist(List<Map<String, String>> datatable){
		for(Map<String, String> map : datatable) {
			Customer c = new Customer(map.get("username"), map.get("password"), 0, flexiBook);
			flexiBook.addCustomer(c);
		}
	}

	/**
	 * 
	 * @param datatable
	 * @author AntoineW
	 */
	@Given ("the following services exist in the system:")
	public void the_Following_Services_Exist(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {
			Service s = new Service(map.get("name"), flexiBook, 
					Integer.parseInt(map.get("duration")),
					Integer.parseInt(map.get("downtimeDuration")), 
					Integer.parseInt(map.get("downtimeStart")) );

			flexiBook.addBookableService(s);
		}
	}

	/**
	 * 
	 * @param datatable
	 * @author AntoineW
	 */
	@Given ("the following service combos exist in the system:")
	public void the_Following_ServiceCombos_Exist(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {

			ServiceCombo sc = new ServiceCombo(map.get("name"), flexiBook);

			// adding a list of comboItems
			List<String> namelist = ControllerUtils.parseString(map.get("services"), ",");
			List<String> mandatorylist = ControllerUtils.parseString(map.get("mandatory"), ",");
			for(String servicename: namelist) {
				int index = namelist.indexOf(servicename);

				if(mandatorylist.get(index).equals("true")) {
					ComboItem optSer = new ComboItem(true, findSingleService(servicename), sc);
					sc.addService(optSer);
				}else if(mandatorylist.get(index).equals("false")) {
					ComboItem optSer = new ComboItem(false, findSingleService(servicename), sc);
					sc.addService(optSer);
				}			 
			}

			
			for(ComboItem ci: sc.getServices()) {
				if(ci.getService().getName().equals(map.get("mainService"))) {
					sc.setMainService(ci);
					break;
				}
			}

			flexiBook.addBookableService(sc);

		}
	}


	/**
	 * @author AntoineW
	 * @param datatable
	 */
	@Given ("the business has the following opening hours")
	public void the_business_has_the_following_opening_hours(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {

			BusinessHour bh = new BusinessHour(null, stringToTime(map.get("startTime")), stringToTime(map.get("endTime")), flexiBook);
			if(map.get("day").equals("Monday")) {
				bh.setDayOfWeek(DayOfWeek.Monday);
			}else if (map.get("day").equals("Tuesday")) {
				bh.setDayOfWeek(DayOfWeek.Tuesday);
			}else if (map.get("day").equals("Wednesday")) {
				bh.setDayOfWeek(DayOfWeek.Wednesday);
			}else if (map.get("day").equals("Thursday")) {
				bh.setDayOfWeek(DayOfWeek.Thursday);
			}else if (map.get("day").equals("Friday")) {
				bh.setDayOfWeek(DayOfWeek.Friday);
			}else if (map.get("day").equals("Saturday")) {
				bh.setDayOfWeek(DayOfWeek.Saturday);
			}else if (map.get("day").equals("Sunday")) {
				bh.setDayOfWeek(DayOfWeek.Sunday);
			}
			flexiBook.getBusiness().addBusinessHour(bh);
			flexiBook.addHour(bh);
		}
	}

	/**
	 * @author AntoineW
	 * @param datatable
	 */
	@Given ("the business has the following holidays")
	public void the_business_has_the_following_holidays(List<Map<String, String>> datatable) {
		for(Map<String, String> map : datatable) {

			TimeSlot ts = new TimeSlot(stringToDate(map.get("startDate")), 
					stringToTime(map.get("startTime")), stringToDate(map.get("endDate")), stringToTime(map.get("endTime")), flexiBook);
			flexiBook.getBusiness().addHoliday(ts);
			//flb.addTimeSlot(ts);
		}			 
	}

	/**
	 * @author AntoineW
	 * @param datatable
	 * @throws InvalidInputException
	 */
	@Given ("the following appointments exist in the system:")
	public void the_following_appointments_exist_in_the_system(List<Map<String, String>> datatable) throws InvalidInputException {
		for(Map<String, String> map : datatable) {

			String custname = map.get("customer");

			Customer c = findCustomer(custname);

			BookableService bs = findBookableService(map.get("serviceName"));


			TimeSlot ts = new TimeSlot(stringToDate(map.get("date")),stringToTime(map.get("startTime")), 
					stringToDate(map.get("date")), stringToTime(map.get("endTime")), flexiBook);		 

			Appointment app = new Appointment(c,bs, ts, flexiBook );

			if(!map.containsKey("optServices")||map.get("optServices").equals("none")) {
					
			}else {

				ServiceCombo sc = findServiceCombo(map.get("serviceName"));

				List<String> serviceNameList = ControllerUtils.parseString(map.get("optServices"), ",");

				for (ComboItem item: sc.getServices() ) {
					//add main service no matter what
					if(item.getService().getName().equals(sc.getMainService().getService().getName())) {
						app.addChosenItem(findComboItemByServiceName((ServiceCombo)bs, item.getService().getName()));
					}else {
						//if not, we check all input option string component in certain order. If
						// a component exist then we add
						for(String name :serviceNameList ) {
							if (item.getService().getName().equals(name)) {
								app.addChosenItem(findComboItemByServiceName((ServiceCombo)bs, name));
							}
							// if the optional service name is not mentioned, we skip
						}					 
					}			 
				}
				for(String name :serviceNameList ) {
					//Service s =  FlexiBookController.findSingleService(name);
					app.addChosenItem(findComboItemByServiceName((ServiceCombo)bs, name));
				}
				
				flexiBook.addAppointment(app);

			}

		}

		appointmentCount = flexiBook.getAppointments().size();
	}



	/**
	 * @author AntoineW
	 * @param name
	 */
	@Given ("{string} is logged in to their account")
	public void user_is_logged_in_to_their_account(String name){

		if(findCustomer(name) !=null) {
			FlexiBookApplication.setCurrentLoginUser(findCustomer(name));	
		}else if (name.equals("owner")) {
			FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		}

	}

	/**
	 * @author AntoineW
	 * @param customer
	 * @param date
	 * @param Servicename
	 * @param time
	 */
	@When ("{string} schedules an appointment on {string} for {string} at {string}")
	public void customer_schedules_an_appointment_on_date_for_service_at_time(String customer, String date, String Servicename, String time) {

		// customer name is not used since it is in the current user
		try {
			FlexiBookController.addAppointmentForService(Servicename, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
		}		 
	}
	
	/**
	 * @author AntoineW
	 * @param customer
	 * @param Servicename
	 * @param date
	 * @param timeStart
	 * @param timeEnd
	 */
	@Then ("{string} shall have a {string} appointment on {string} from {string} to {string}")
	public void customer_shall_have_a_service_appointment_on_date_from_startTime_to_endTime(String customer, String Servicename, 
			String date,  String timeStart, String timeEnd) {
		boolean isTheCase = false;
		for (Appointment app :findCustomer(customer).getAppointments()) {
			if(app.getCustomer().getUsername() .equals (customer) &&
					app.getBookableService().getName() .equals (Servicename) &&
					app.getTimeSlot().getStartDate().equals(stringToDate(date)) &&
					app.getTimeSlot().getStartTime().equals(stringToTime(timeStart)) && 
					app.getTimeSlot().getEndTime().equals(stringToTime(timeEnd))){

				isTheCase = true;
			}
		}

		assertEquals(isTheCase, true);
	}

	/**
	 * @author AntoineW
	 * @param i
	 */
	@Then ("there shall be {int} more appointment in the system")
	public void there_shall_be_some_more_appointment_in_the_system(int i) {
		assertEquals(flexiBook.getAppointments().size() - appointmentCount, i);
		appointmentCount = flexiBook.getAppointments().size();
	}


	/**
	 * @author AntoineW
	 * @param str
	 */
	@Then("the system shall report {string}")
	public void the_system_shall_report_error(String str) {
		assertTrue(error.contains(str));
	}


	/**
	 * @author AntoineW
	 * @param customerName
	 * @param date
	 * @param serviceName
	 * @param optService
	 * @param time
	 */
	@When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
	public void schedules_an_appointment_on_for_with_at(String customerName, String date, String serviceName, 
			String optService, String time) {
		try {
			FlexiBookController.addAppointmentForComboService(serviceName, optService, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
		}

	}


	/**
	 * @author AntoineW
	 * @Feature: Update appointment
	 * As a customer, I wish to be able to update my appointment so that I can edit my optional combo items or change my appointment time
	 */
	//---------------------------------------- updating app -----------------------------------------------------------------------------
	/**
	 * @author AntoineW
	 * @param customer
	 * @param serviceName
	 * @param oldDate
	 * @param oldTime
	 * @param newD
	 * @param newT
	 */
	@When("{string} attempts to update their {string} appointment on {string} at {string} to {string} at {string}")
	public void attempts_to_update_their_appointment_on_at_to_at(String customer, String serviceName, String oldDate, 
			String oldTime, String newD, String newT) {

		try {
			statusOfAppointment = FlexiBookController.updateAppointmentTime(serviceName, stringToDate(oldDate),stringToTime( oldTime), stringToDate(newD) ,stringToTime(newT));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}

	}

	/**
	 * @author AntoineW
	 * @param string
	 */
	@Then("the system shall report that the update was {string}")
	public void the_system_shall_report_that_the_update_was(String string) {
		String statusStr = "";
		// returned from the controller method!
		if (statusOfAppointment) {
			statusStr = "successful";
		}else {
			statusStr = "unsuccessful";
		}

		assertEquals(statusStr, string);
	}



	/**
	 * @param customer
	 * @param serviceName
	 * @param optService
	 * @param date
	 * @param time
	 * @author AntoineW
	 */
	@Given("{string} has a {string} appointment with optional sevices {string} on {string} at {string}")
	public void has_a_appointment_with_optional_sevices_on_at(String customer, String serviceName, String optService, 
			String date, String time) {
		
		// user and combo
		Customer c = findCustomer(customer);
		ServiceCombo sCombo = findServiceCombo(serviceName);
		
		//setting up the timeslot
		List<ComboItem> itemList = sCombo.getServices();
		int actualTime = 0;
		List<String> itemNameList = ControllerUtils.parseString(optService,",");

		for (ComboItem ci : itemList) {

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
		LocalTime aEndtime = stringToTime(time).toLocalTime().plusMinutes(actualTime);
		Time endTime = Time.valueOf(aEndtime);

		// Here handle constraints: start and end date of an appointment have to be the same
		TimeSlot timeSlot = new TimeSlot(stringToDate(date), stringToTime(time), stringToDate(date), endTime, 
				flexiBook);
		
		Appointment appointment = new Appointment(c, sCombo, timeSlot,flexiBook);


		// very much similar to calcActualTimeOfAppointment(List<ComboItem> comboItemList, String chosenItemNames)
		// add all mandatory and chosen optional combo item to appointment
		for (ComboItem item: sCombo.getServices()) {

			if(item.getService().getName().equals(sCombo.getMainService().getService().getName()) || item.getMandatory()) {
				try {
					appointment.addChosenItem(findComboItemByServiceName(sCombo, item.getService().getName()));
				} catch (InvalidInputException e) {
					error = error+e.getMessage();
					errorCount++;
				}
			}else{
				for(String name : ControllerUtils.parseString(optService, ",")) {
					if (item.getService().getName().equals(name)) {
						appointment.addChosenItem(item);
					}
				}
			}
		}	


		FlexiBookApplication.getFlexiBook().addAppointment(appointment);
		
	}



	/**
	 * @author AntoineW
	 * @param customer
	 * @param action
	 * @param comboItem
	 * @param serviceName
	 * @param date
	 * @param time
	 */
	@When("{string} attempts to {string} {string} from their {string} appointment on {string} at {string}")
	public void attempts_to_from_their_appointment_on_at(String customer, String action, String comboItem, String serviceName,
			String date, String time) {
		try {
			statusOfAppointment = FlexiBookController.updateAppointmentContent(serviceName, 
					stringToDate(date), stringToTime(time), action, comboItem);

			appointmentCount = flexiBook.getAppointments().size();
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}
	}


	/**
	 * @author AntoineW
	 * @param user
	 * @param custmerName
	 * @param serviceName
	 * @param oldDate
	 * @param oldTime
	 * @param newD
	 * @param newT
	 */
	@When("{string} attempts to update {string}'s {string} appointment on {string} at {string} to {string} at {string}")
	public void attempts_to_update_s_appointment_on_at_to_at(String user, String custmerName, String serviceName, 
			String oldDate, String  oldTime, String newD, String newT) {

		if(findCustomer(user) !=null) {
			FlexiBookApplication.setCurrentLoginUser(findCustomer(user));	
		}else if (user.equals("owner")) {
			FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		}
		try {
			statusOfAppointment = FlexiBookController.updateAppointmentTime(serviceName, stringToDate(oldDate),stringToTime( oldTime), 
					stringToDate(newD) ,stringToTime(newT));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}
	}



	/**
	 * @author AntoineW
	 * @Feature: Cancel appointment
  		As a customer, I wish to be able to cancel an appointment so that my appointment time slot becomes available for other customers
	 */
	//--------------------------------------- cancel Appointment ------------------------------------------------
	/**
	 * @author AntoineW
	 * @param user
	 * @param serviceName
	 * @param date
	 * @param time
	 */
	@When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	public void attempts_to_cancel_their_appointment_on_at(String user, String serviceName, String date, String time) {

		try {
			statusOfAppointment = FlexiBookController.cancelAppointment(serviceName, stringToDate(date),stringToTime(time));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}

	}


	/**
	 * @author AntoineW
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 */
	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) {

		Appointment app = findAppointment(string2, stringToDate(string3), stringToTime(string4)); 
		// should be removed thus no longer found in the system -> assert to be null
		assertEquals(null, app);
	}

	/**
	 * @author AntoineW
	 * @param int1
	 */
	@Then("there shall be {int} less appointment in the system")
	public void there_shall_be_less_appointment_in_the_system(Integer int1) {
		// one less -> thus subtraction should be negative number
		assertEquals(flexiBook.getAppointments().size() - appointmentCount, int1 * (-1));
		appointmentCount = flexiBook.getAppointments().size();
	}



	/**
	 * @author AntoineW
	 * @param curUser
	 * @param customer
	 * @param serviceName
	 * @param date
	 * @param time
	 */
	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attempts_to_cancel_s_appointment_on_at(String curUser, String customer, String serviceName, String date, String time) {
		if(findCustomer(curUser) !=null) {
			FlexiBookApplication.setCurrentLoginUser(findCustomer(curUser));	
		}else if (curUser.equals("owner")) {
			FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		}
		try {
			statusOfAppointment = FlexiBookController.cancelAppointment(serviceName, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}

	}


	/*---------------------------Test Sign Up Customer Account--------------------------*/

	/**
	 * Feature: Sign up for customer account
	 * As a prospective customer, I want to create an account with username and password
	 * so that I can log in later
	 * 
	 * @author Catherine
	 */

	/**
	 * @author Catherine
	 */
	@Given("there is no existing username {string}") 
	public void there_is_no_existing_username(String username){
		customerCount = flexiBook.getCustomers().size();
		if(findUser(username) != null) {
			if(username != "owner") customerCount--;
			findUser(username).delete();
		}

	}

	/**
	 * @author Catherine
	 */
	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String username, String password) {
		try {
			FlexiBookController.signUpCustomer(username, password);
		}
		catch(InvalidInputException e){
			error += e.getMessage();
		}
	}

	/**
	 * @author Catherine
	 */
	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		assertEquals(1, flexiBook.getCustomers().size() - customerCount);
	}

	/**
	 * @author Catherine
	 */
	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String username, String password) {
		if(findUser(username) instanceof Owner) {
			assertEquals(username, flexiBook.getOwner().getUsername());
			assertEquals(password, flexiBook.getOwner().getPassword());
		}
		else {
			assertEquals(username, flexiBook.getCustomer(0).getUsername());
			assertEquals(password, flexiBook.getCustomer(0).getPassword());
		}
	}

	/**
	 * @author Catherine
	 */
	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		assertEquals(0, flexiBook.getCustomers().size() - customerCount);
	}

	/**
	 * @author Catherine
	 */
	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String errorMsg) {
		assertTrue(error.contains(errorMsg));
	}

	/**
	 * @author Catherine
	 */
	@Given("there is an existing username {string}")
	public void there_is_an_existing_username(String username) {
		customerCount = flexiBook.getCustomers().size();
		if(findUser(username) == null) {
			if(username.equals("owner")) { 
				owner = new Owner("owner", "owner", flexiBook);
				flexiBook.setOwner(owner);
			}
			else {
				flexiBook.addCustomer(username, "password", 0);
				customerCount++;
			}
		}	
	}

	/**
	 * @author Catherine
	 */
	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String username) {
		FlexiBookApplication.setCurrentLoginUser(findUser(username));
	}


	/*---------------------------Test Update Account--------------------------*/

	/**
	 * Feature: Update customer or owner account
	 * As a user, I want to be update my username and password so that I can login later with the 
	 * new information
	 * 
	 * @author Catherine
	 */

	/**
	 * @author Catherine
	 */
	@Given("an owner account exists in the system with username {string} and password {string}")
	public void an_owner_account_exists_in_the_system_with_username_and_password(String username, String password) {
		owner = new Owner(username, password, flexiBook);
		flexiBook.setOwner(owner);
	}

	/**
	 * @author Catherine
	 */
	@When("the user tries to update account with a new username {string} and password {string}")
	public void the_user_tries_to_update_account_with_a_new_username_and_password(String newUsername, String newPassword) {
		try {
			FlexiBookController.updateUserAccount(FlexiBookApplication.getCurrentLoginUser().getUsername(), newUsername, newPassword);
		}
		catch(InvalidInputException e){
			error += e.getMessage();
		}
	}

	/**
	 * @author Catherine
	 */
	@Then("the account shall not be updated")
	public void the_account_shall_not_be_updated() {
		assertEquals(FlexiBookApplication.getCurrentLoginUser().getUsername(), "User1");
		assertEquals(FlexiBookApplication.getCurrentLoginUser().getPassword(), "apple"); //using literals to keep it simple
	}

	/*---------------------------Test Update Account--------------------------*/

	/**
	 * Feature: Delete customer account
	 * As a user, I want to delete my own account so that 
	 * the personal information is deleted from the system
	 * 
	 * @author Catherine
	 */

	/**
	 * @author Catherine
	 */
	@Given("the account with username {string} has pending appointments")
	public void the_account_with_username_has_pending_appointments(String username) {
		Date date = new Date(1634814000); //Thursday, October 21, 2021 11:00:00 AM 
		Time time = new Time(1634814000); //Thursday, October 21, 2021 11:00:00 AM 
		Time endTime = new Time(1634814000 + 3600); //one hour later
		TimeSlot timeSlot = new TimeSlot(date, time, date, endTime, FlexiBookApplication.getFlexiBook());
		Service service = new Service("service", FlexiBookApplication.getFlexiBook(), 2, 0, 0);
		FlexiBookApplication.getFlexiBook().addBookableService(service);
		Appointment appointment = new Appointment((findCustomer(username)), service , timeSlot, FlexiBookApplication.getFlexiBook());
		FlexiBookApplication.getFlexiBook().addAppointment(appointment);
	}

	/**
	 * @author Catherine
	 */
	@When("the user tries to delete account with the username {string}")
	public void the_user_tries_to_delete_account_with_the_username(String username) {
		try {
			FlexiBookController.deleteCustomerAccount(username);
		}
		catch(InvalidInputException e){
			error += e.getMessage();
		}
	}

	/**
	 * @author Catherine
	 */
	@Then("the account with the username {string} does not exist")
	public void the_account_with_the_username_does_not_exist(String username) {
		assertNull(findUser(username));
	}

	/**
	 * @author Catherine
	 */
	@Then("all associated appointments of the account with the username {string} shall not exist")
	public void all_associated_appointments_of_the_account_with_the_username_shall_not_exist(String username) {
		assertTrue(findAppointmentsForCustomer(username).isEmpty());
	}

	/**
	 * @author Catherine
	 */
	@Then("the user shall be logged out")
	public void the_user_shall_be_logged_out() {
		assertNull(FlexiBookApplication.getCurrentLoginUser());
	}

	/**
	 * @author Catherine
	 */
	@Then("the account with the username {string} exists")
	public void the_account_with_the_username_exists(String username) {
		assertFalse(findUser(username) == null);
	}

	/*---------------------------Test Define Service Combo--------------------------*/

	/**
	 * Test method for initiating a creation of a service combo
	 * @param user -user who initiates the service combo creation
	 * @param serviceComboName -name of service combo
	 * @param mainServiceName -main service of service combo
	 * @param servicesString -ordered list of services for the service combo
	 * @param mandatorySettingsString -determines which services are mandatory
	 * @author gtjarvis
	 */
	@When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
	public void user_initiates_the_definition_of_a_service_combo(String user, String serviceComboName, String mainServiceName, String servicesString, String mandatorySettingsString) throws InvalidInputException{
		if (FlexiBookApplication.getCurrentLoginUser().getUsername().equals(user)){
			List<String> services = Arrays.asList(servicesString.split(","));
			List<String> mandatorySettingsStringList = Arrays.asList(mandatorySettingsString.split(","));
			List<Boolean> mandatorySettings = new ArrayList<Boolean>();
			for(int i = 0; i < mandatorySettingsStringList.size(); i++){
				mandatorySettings.add(Boolean.parseBoolean(mandatorySettingsStringList.get(i)));
			}
			try {
				FlexiBookController.defineServiceCombo(serviceComboName, mainServiceName, services, mandatorySettings);
			}
			catch (InvalidInputException e) {
				error += e.getMessage();
				errorCntr++;
			}
		}
	}

	/**
	 * Check for if the given service combo exists in the system
	 * @param name -name of given service combo
	 * @author gtjarvis
	 */
	@Then("the service combo {string} shall exist in the system")
	public void the_service_combo_name_shall_exist_in_the_system(String name){
		String nameFound = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				nameFound = b.getName();
			}
		}
		assertEquals(nameFound, name);
	}

	/**
	 * Check for if the given service combo does not exist in the system
	 * @param name -name of given service combo
	 * @author gtjarvis
	 */
	@Then("the service combo {string} shall not exist in the system")
	public void the_service_combo_name_shall_not_exist_in_the_system(String name){
		String nameFound = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				nameFound = b.getName();
			}
		}
		assertNull(nameFound);
	}

	/**
	 * Check for if the given service combo conatins the given services and whether or not they are mandatory
	 * @param name -name of given service combo
	 * @param servicesString -ordered list of services
	 * @param mandatorySettingsString -determines which services are mandatory
	 * @author gtjarvis
	 */
	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_comboName_shall_contain_the_services_serviceName_with_mandatory_setting_mandatorySetting(String name, String servicesString, String mandatorySettingsString){
		List<String> servicesList = Arrays.asList(servicesString.split(","));
		List<String> mandatorySettingsList = Arrays.asList(mandatorySettingsString.split(","));
		ServiceCombo serviceCombo = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				serviceCombo = (ServiceCombo) b;
			}
		}
		List<ComboItem> comboList = serviceCombo.getServices();
		for(int i = 0; i < comboList.size(); i++){
			assertEquals(comboList.get(i).getService().getName(), servicesList.get(i));
			assertEquals(comboList.get(i).getMandatory(), Boolean.parseBoolean(mandatorySettingsList.get(i)));
		}
	}
	/**
	 * Check for if the given service combo has the given main service
	 * @param name -name of given service combo
	 * @param mainService -main service name
	 * @author gtjarvis
	 */
	@Then("the main service of the service combo {string} shall be {string}")
	public void the_main_service_of_the_service_combo_name_shall_be_mainService(String name, String mainService){
		String nameFound = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				nameFound = ((ServiceCombo)b).getMainService().getService().getName();
			}
		}
		assertEquals(nameFound, mainService);
	}

	/**
	 * Check for if the main service in a given service combo is mandatory
	 * @param mainService -main service name
	 * @param name -name of given service combo
	 * @author gtjarvis
	 */
	@Then("the service {string} in service combo {string} shall be mandatory")
	public void the_service_mainService_in_service_combo_name_shall_be_mandatory(String mainServiceName, String name){
		ComboItem mainService = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo && ((ServiceCombo)b).getMainService().getService().getName().equals(mainServiceName)) {
				mainService = ((ServiceCombo)b).getMainService();
			}
		}
		assertTrue(mainService.getMandatory());
	}

	/**
	 * Check for the number of service combos
	 * @param num -number of service combos
	 * @author gtjarvis
	 */
	@Then("the number of service combos in the system shall be {string}")
	public void the_number_of_services_combos_in_the_system_shall_be_num(String num){
		int n = Integer.parseInt(num);
		int count = 0;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b instanceof ServiceCombo) {
				count++;
			}
		}
		assertEquals(count, n);
	}

	/**
	 * Check for if a given service combo preserves a number of properties
	 * @param name -name of given service combo
	 * @param datatable -datatable of properties
	 * @author gtjarvis
	 */
	@Then("the service combo {string} shall preserve the following properties:")
	public void the_service_combo_name_shall_preserve_the_following_properties(String name, List<Map<String, String>> datatable){
		ServiceCombo serviceCombo = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				serviceCombo = (ServiceCombo) b;
			}
		}
		Map<String, String> map = datatable.get(0);
		List<String> services = Arrays.asList(map.get("services").split(","));
		List<String> mandatory = Arrays.asList(map.get("mandatory").split(","));
		assertEquals(serviceCombo.getName(), map.get("name"));
		assertEquals(serviceCombo.getMainService().getService().getName(), map.get("mainService"));
		List<ComboItem> comboItems = serviceCombo.getServices();
		for(int i = 0; i < comboItems.size(); i++){
			assertEquals(comboItems.get(i).getService().getName(), services.get(i));
			assertEquals(comboItems.get(i).getMandatory(), Boolean.parseBoolean(mandatory.get(i)));
		}
	}

	/*---------------------------Test Delete Service Combo--------------------------*/

	/**
	 * Test method for deleting a given service combo
	 * @param user -user who initiated the deletion
	 * @param name -name of given service combo
	 * @author gtjarvis
	 */
	@When("{string} initiates the deletion of service combo {string}")
	public void user_initiates_the_deletion_of_service_combo(String user, String name){
		if (FlexiBookApplication.getCurrentLoginUser().getUsername().equals(user)){
			try {
				FlexiBookController.deleteServiceCombo(name);
			}
			catch (InvalidInputException e) {
				error += e.getMessage();
				errorCntr++;
			}
		}
	}

	/*---------------------------Test Update Service Combo--------------------------*/

	/**
	 * Test method for updating a given service combo
	 * @param user -user who initiated the deletion
	 * @param serviceCombo -name of given service combo
	 * @param newName -new name of the service combo
	 * @param newMainService -new main service of the service combo
	 * @param newServices -new ordered list of services of the service combo
	 * @param newMandatory -determines which services are mandatory for the new services in the service combo
	 * @author gtjarvis
	 */
	@When ("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
	public void user_initiates_the_update_of_service_combo_name(String user, String serviceCombo, String newName, String newMainService, String newServices, String newMandatory){
		if (FlexiBookApplication.getCurrentLoginUser().getUsername().equals(user)){
			List<String> services = Arrays.asList(newServices.split(","));
			List<String> mandatoryList = Arrays.asList(newMandatory.split(","));
			List<Boolean> mandatory = new ArrayList<Boolean>();
			for(int i = 0; i < mandatoryList.size(); i++){
				mandatory.add(Boolean.parseBoolean(mandatoryList.get(i)));
			}
			try {
				FlexiBookController.updateServiceCombo(serviceCombo, newName, newMainService, services, mandatory);
			}
			catch (InvalidInputException e) {
				error += e.getMessage();
				errorCntr++;
			}
		}
	}

	/**
	 * Check for whether Service Combo name has been updated
	 * @param name -old name of service combo
	 * @param newName -new name of service combo
	 * @author gtjarvis
	 */
	@Then("the service combo {string} shall be updated to name {string}")
	public void the_service_combo_shall_be_updated_to_name(String name, String newName) {
		ServiceCombo serviceCombo = null;
		ServiceCombo newServiceCombo = null;
		for (BookableService b : FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (b.getName().equals(name) && b instanceof ServiceCombo) {
				serviceCombo = (ServiceCombo) b;
			}
			if(b.getName().equals(newName) && b instanceof ServiceCombo){
				newServiceCombo = (ServiceCombo) b;
			}
		}
		if(!name.equals(newName)){
			assertNull(serviceCombo);
		}
		assertTrue(newServiceCombo != null);
	}
	
	
	/*---------------------------Test Set Up Business Information--------------------------*/

	/**
	 * Feature: Set up basic business information 
	 * As an owner I whish to create a Business with information 
	 * @author jedla
	 */

	@Given("no business exists")
	public void no_business_exists() {
		if (FlexiBookApplication.getFlexiBook().hasBusiness() == true) {
			FlexiBookApplication.getFlexiBook().getBusiness().delete();
		}
	}
	 
	 /**
	  * @author jedla
	  */

	@When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
	public void the_user_tries_to_set_up_the_business_information_with_new_and_and_and(String name, String address,
			String phoneNumber, String email) {
		try {
			FlexiBookController.setUpBusinessInfo(name, address, phoneNumber, email);
			isSetUp = true;
		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}

	
	/**
	  * @author jedla
	  */
	@Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
	public void a_new_business_with_new_and_and_and_shall_created(String name, String address, String phoneNumber,
			String email, String string1) {
		String result1 = "";

		if (isSetUp == false) {
			result1 = "not be";
		}

		else {
			assertEquals(name, FlexiBookApplication.getFlexiBook().getBusiness().getName());
			assertEquals(address, FlexiBookApplication.getFlexiBook().getBusiness().getAddress());
			assertEquals(phoneNumber, FlexiBookApplication.getFlexiBook().getBusiness().getPhoneNumber());
			assertEquals(email, FlexiBookApplication.getFlexiBook().getBusiness().getEmail());
			result1 = "be";
		}
		assertEquals(string1, result1);

	}

	/**
	  * @author jedla
	  */
	@Then("an error message {string} shall {string} raised")
	public void an_error_message_shall_raised(String error, String resultError) {
		String shallError = "";

		if (isSetUp == false) {
			assertTrue(error.contains(error));
			shallError = "be";

		} else {
			shallError = "not be";
		}
		assertEquals(shallError, resultError);
	}

	/**
	 * Feature: Add new business hours 
	 *As an owner I want to add new business hours
	 * 
	 * @author jedla
	 */

	@Given("a business exists with the following information:")
	public void a_business_exists_with_the_following_information(List<Map<String, String>> datatable) {
		for (Map<String, String> instance : datatable) {
			Business business = new Business(instance.get("name"), instance.get("address"),
					instance.get("phone number"), instance.get("email"), FlexiBookApplication.getFlexiBook());
			FlexiBookApplication.getFlexiBook().setBusiness(business);
		}
	}
	
	/**
	  * @author jedla
	  */

	@Given("the business has a business hour on {string} with start time {string} and end time {string}")
	public void the_business_has_a_business_hour_on_with_start_time_and_end_time(String day, String startTime,
			String endTime) {

		BusinessHour bh = new BusinessHour(stringToDay(day), stringToTime(startTime), stringToTime(endTime),
				FlexiBookApplication.getFlexiBook());
		FlexiBookApplication.getFlexiBook().getBusiness().addBusinessHour(bh);
		FlexiBookApplication.getFlexiBook().addHour(bh);
	}
	
	
	/**
	  * @author jedla
	  */

	@When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
	public void the_user_tries_to_add_a_new_business_hour_on_with_start_time_and_end_time(String day, String startTime,
			String endTime) {
		try {
			FlexiBookController.setUpBusinessHours(stringToTime(startTime), stringToTime(endTime),
					stringToDay(day));
			newBusinessHour = isTheBusinessHour(stringToDay(day), stringToTime(startTime));
			isSetUp =true;
		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp =false;
		}
	}

	
	/**
	  * @author jedla
	  */
	@Then("a new business hour shall {string} created")
	public void a_new_business_hour_shall_created(String string3) {
		String result_hours = " ";
		if (FlexiBookApplication.getFlexiBook().getHours().contains(newBusinessHour)) {
			result_hours = "be";
		} else {
			result_hours = "not be";
		}
		assertEquals(result_hours, string3);
	}

	/**
	 * Feature: View basic business information 
	 *As a user I want to view basic business information
	 * 
	 * @author jedla
	 */

	@When("the user tries to access the business information")
	public void the_user_tries_to_access_the_business_information() {
		FlexiBookController.getBusinessInfo();
	}
	
	/**
	  * @author jedla
	  */

	@Then("the {string} and {string} and {string} and {string} shall be provided to the user")
	public void the_and_and_and_shall_be_provided_to_the_user(String name, String address, String phoneNumber,
			String email) {
		assertEquals(FlexiBookApplication.getFlexiBook().getBusiness().getName(), name);
		assertEquals(FlexiBookApplication.getFlexiBook().getBusiness().getAddress(), address);
		assertEquals(FlexiBookApplication.getFlexiBook().getBusiness().getPhoneNumber(), phoneNumber);
		assertEquals(FlexiBookApplication.getFlexiBook().getBusiness().getEmail(), email);

	}
	/**
	 * Feature: Add new time slot 
	 *As an owner I want to add a holiday or a vacation as TimeSlot
	 * 
	 * @author jedla
	 */

	@Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
	public void a_time_slot_exists_with_start_time_at_and_end_time_at(String type, String startDate, String startTime,
			String endDate, String endTime) {
		TimeSlot VacationOrHoliday = new TimeSlot(stringToDate(startDate), stringToTime(startTime),
				stringToDate(endDate), stringToTime(endTime), flexiBook);

		if (type.equals("vacation")) {
			flexiBook.getBusiness().addVacation(VacationOrHoliday);
		} else if (type.equals("holiday")) {
			flexiBook.getBusiness().addHoliday(VacationOrHoliday);
		}
	}

	 
	 /**
	  * @author jedla
	  */

	@When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_add_a_new_with_start_date_at_and_end_date_at(String type, String startDate,
			String startTime, String endDate, String endTime) {
		try {
			FlexiBookController.setUpHolidayVacation(type, stringToDate(startDate),
					stringToTime(startTime), stringToDate(endDate), stringToTime(endTime));
			isSetUp =true;

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}

	/**
	  * @author jedla
	  */
	

	@Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
	public void a_new_shall_be_added_with_start_date_at_and_end_date_at(String type, String result, String startDate,
			String startTime, String endDate, String endTime) {

		String temporaryResult = "not be";

		List<TimeSlot> holiday = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		List<TimeSlot> vacation = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();

		if (type.equals("vacation")){

			for (TimeSlot instance : vacation) {
				if(instance.getEndDate().equals(stringToDate(endDate))&& instance.getStartDate().equals(stringToDate(startDate))
						&& instance.getStartTime().equals(stringToTime(startTime))&& instance.getEndTime().equals(stringToTime(endTime))) {
					temporaryResult	= "be";
					break;
				}
			}

		}
		else if (type.equals("holiday")){
			for (TimeSlot instance : holiday) {
				if(instance.getEndDate().equals(stringToDate(endDate))&& instance.getStartDate().equals(stringToDate(startDate))
						&& instance.getStartTime().equals(stringToTime(startTime))&& instance.getEndTime().equals(stringToTime(endTime))) {
					temporaryResult	= "be";
					break;
				}
			}
		}
		assertEquals(result, temporaryResult);

	}

	/*---------------------------Test Updating Business Information --------------------------*/

	/**
	 * Feature: Update basic business information
	 * As an owner I want to update the basic business information
	 * @author jedla
	 */

	@When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
	public void the_user_tries_to_update_the_business_information_with_new_and_and_and(String name, String address, String phoneNumber, String email) {
		try {
			FlexiBookController.updateBusinessInfo(name, address, phoneNumber, email); 
			isSetUp = true;

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}
	 
	 /**
	  * @author jedla
	  */

	@Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
	public void the_business_information_shall_updated_with_new_and_and_and(String result, String name, String address, String phoneNumber, String email) {
		String temp = "";

		if (isSetUp == false) {
			temp= "not be";
		}

		else {
			assertEquals(name, FlexiBookApplication.getFlexiBook().getBusiness().getName());
			assertEquals(address, FlexiBookApplication.getFlexiBook().getBusiness().getAddress());
			assertEquals(phoneNumber, FlexiBookApplication.getFlexiBook().getBusiness().getPhoneNumber());
			assertEquals(email, FlexiBookApplication.getFlexiBook().getBusiness().getEmail());
			temp = "be";
		}
		assertEquals(temp, result);
	}

	/**
	 * Feature: Update existing business hours
	 * As an owner I want to update existing business hours
	 * @author jedla
	 */

	@When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
	public void the_user_tries_to_change_the_business_hour_at_to_be_on_starting_at_and_ending_at(String oldDay, String oldStartTime, String newDay, String newStartTime, String newEndTime) {
		try {
			FlexiBookController.updateBusinessHour(stringToDay(oldDay), stringToTime(oldStartTime), stringToDay(newDay), stringToTime(newStartTime), stringToTime(newEndTime)); 
			isSetUp = true;
			newBusinessHour = isTheBusinessHour(stringToDay(newDay), stringToTime(newStartTime));

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}
	 
	 /**
	  * @author jedla
	  */

	@Then("the business hour shall {string} be updated")
	public void the_business_hour_shall_be_updated(String resultUpdate) {
		String result = "";

		if (FlexiBookApplication.getFlexiBook().getHours().contains(newBusinessHour)) {
			result = "be";

		} else{
			result = "not be";
		}
		assertEquals(resultUpdate, result);
	}
	/**
	 * Feature: Removing existing business hours
	 * As an owner I want to remove an existing business hour
	 * @author jedla
	 */

	@When("the user tries to remove the business hour starting {string} at {string}")
	public void the_user_tries_to_remove_the_business_hour_starting_at(String day, String time) {
		try {
			FlexiBookController.removeBusinessHour(stringToDay(day), stringToTime(time));
			isSetUp = true;

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}
	 
	 /**
	  * @author jedla
	  */

	@Then("the business hour starting {string} at {string} shall {string} exist")
	public void the_business_hour_starting_at_shall_exist(String day, String time, String result) {
		String temp = "not";
		List<BusinessHour> ListBH = FlexiBookApplication.getFlexiBook().getHours();

		for (BusinessHour instance : ListBH ) {
			if (instance.getDayOfWeek().equals(stringToDay(day))&&instance.getStartTime().equals(stringToTime(time))){
				temp = "";
			}
			else if (!isSetUp) {
				temp = "";
			}	
		}	
		assertEquals(result, temp);
	}

	
	/**
	  * @author jedla
	  */

	@Then("an error message {string} shall {string} be raised")
	public void an_error_message_shall_be_raised(String e, String result) {
		String temp = "";
		if (isSetUp) {
			temp = "not";
		} else if (!isSetUp) {
			assertTrue(error.contains(e));
			temp = "";
		}
		assertEquals(temp, result);
	}
	/**
	 * Feature: Update vacation and holiday
	 * As an owner I want to update my vacations and holidays
	 * @author jedla
	 */

	@When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_change_the_on_at_to_be_with_start_date_at_and_end_date_at(String type, String oldStartDate, String oldStartTime, String startDate, String startTime, String endDate, String endTime) {
		try {
			FlexiBookController.updateHolidayVacation(type, stringToDate(oldStartDate), stringToTime(oldStartTime), stringToDate(startDate), stringToTime(startTime),stringToDate(endDate), stringToTime(endTime));
			isSetUp = true;

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}
	 
	 /**
	  * @author jedla
	  */

	@Then("the {string} shall {string} be updated with start date {string} at {string} and end date {string} at {string}")
	public void the_shall_be_updated_with_start_date_at_and_end_date_at(String type, String result, String startDate, String startTime, String endDate, String endTime) {
		String temporaryResult = "not be";

		List<TimeSlot> holiday = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		List<TimeSlot> vacation = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();

		if (type.equals("vacation")){

			for (TimeSlot instance : vacation) {
				if(instance.getEndDate().equals(stringToDate(endDate))&& instance.getStartDate().equals(stringToDate(startDate))
						&& instance.getStartTime().equals(stringToTime(startTime))&& instance.getEndTime().equals(stringToTime(endTime))) {
					temporaryResult	= "be";
					break;
				}
			}

		}
		else if (type.equals("holiday")){
			for (TimeSlot instance : holiday) {
				if(instance.getEndDate().equals(stringToDate(endDate))&& instance.getStartDate().equals(stringToDate(startDate))
						&& instance.getStartTime().equals(stringToTime(startTime))&& instance.getEndTime().equals(stringToTime(endTime))) {
					temporaryResult	= "be";
					break;
				}
			}
		}
		assertEquals(result, temporaryResult);
	}

	/**
	 * Feature: Remove existing time slot
	 * As an owner I want to remove an existing time slot (vacation or holiday)
	 * @author jedla
	 */

	@When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
	public void the_user_tries_to_remove_an_existing_with_start_date_at_and_end_date_at(String type, String startDate, String startTime, String endDate, String endTime) {
		try {
			FlexiBookController.removeHolidayVacation(type, stringToDate(startDate), stringToTime(startTime),stringToDate(endDate), stringToTime(endTime));
			isSetUp = true;

		} catch (InvalidInputException e) {
			error += e.getMessage();
			isSetUp = false;
		}
	}
	
	/**
	  * @author jedla
	  */

	@Then("the {string} with start date {string} at {string} shall {string} exist")
	public void the_with_start_date_at_shall_exist(String type, String startDate, String startTime, String result) {
		String temporaryResult = "not";

		List<TimeSlot> holiday = FlexiBookApplication.getFlexiBook().getBusiness().getHolidays();
		List<TimeSlot> vacation = FlexiBookApplication.getFlexiBook().getBusiness().getVacation();

		if (type.equals("vacation")){

			for (TimeSlot instance : vacation) {
				if(instance.getStartDate().equals(stringToDate(startDate)) && instance.getStartTime().equals(stringToTime(startTime))) {
					temporaryResult	= "";
					break;
				}
			}

		}
		else if (type.equals("holiday")){
			for (TimeSlot instance : holiday) {
				if(instance.getStartDate().equals(stringToDate(startDate))&& instance.getStartTime().equals(stringToTime(startTime))) {
					temporaryResult	= "";
					break;
				}
			}
		}
		assertEquals(result, temporaryResult);

	}
	
	/*-----------------------Appointment Management Process----------------------*/
	
	/**
	 * Scenario: Change the appointment for a service at least one day ahead
	 */
	
	/**
	 * @author Catherine
	 */
	@Given("{string} has {int} no-show records")
	public void has_no_show_records(String username, Integer noShowCount) {
		if(findCustomer(username) !=null) {
			findCustomer(username).setNoShowCount(noShowCount);	
		}else{
			new Customer(username, "password", noShowCount, FlexiBookApplication.getFlexiBook());
		}
	}
	/**
	 * @author chengchen
	 */
	@When("{string} makes a {string} appointment for the date {string} and time {string} at {string}")
	public void makes_a_appointment_for_the_date_and_time_at(String username, String serviceName, String date, String time, String currentDateTime) {
//		String[] arrOfDateTime = currentDateTime.split("\\+");
//		String currentDate = "";
//		String currentTime = ""; 
//		currentDate = arrOfDateTime[0];
//		currentTime = arrOfDateTime[1];
		TOTimeSlot RegisterTime = currentRegisterTime(currentDateTime);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		TimeSlot timeSlot = new TimeSlot(stringToDate(date), stringToTime(time),stringToDate(date), Time.valueOf(stringToTime(time).toLocalTime().plusMinutes(((Service) findBookableService(serviceName)).getDuration())), flexiBook);
		
		if (findSingleService(serviceName)!= null) {
	    		Appointment appointment = new Appointment(findCustomer(username), findBookableService(serviceName), timeSlot, flexiBook);
				flexiBook.addAppointment(appointment);
				
				specificAppointment = appointment; // @AntoineW
	    }
		
//		else if (findServiceCombo(serviceName) != null) {
//			try {
//				FlexiBookController.addAppointmentForComboService(serviceName, null, stringToDate(date), stringToTime(time));
//			} catch (InvalidInputException e) {
//				error += e.getMessage();
//				errorCntr++;
//			}		
//		}
	}
	
	
	/**
	 * 
	 * @param username
	 * @param serviceName
	 * @param currentDateTime
	 * 
	 * @author chengchen
	 * @throws InvalidInputException 
	 */
	@When("{string} attempts to change the service in the appointment to {string} at {string}")
	public void attempts_to_change_the_service_in_the_appointment_to_at(String username, String newServiceName, String currentDateTime) throws InvalidInputException {
		TOTimeSlot RegisterTime = currentRegisterTime(currentDateTime);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		// added by Mike start ---
		if (specificAppointment.getTimeSlot().getStartDate().after(FlexiBookApplication.getCurrentDate(true))) {
			specificAppointment.updateContent("", newServiceName);
		}
		// added by Mike end ---
//		for (Appointment appointment:findAppointmentByUserName(username)) {
//			if (appointment.getChosenItems().size() == 0) {
//				if (appointment.getTimeSlot().getStartDate().after(FlexiBookApplication.getCurrentDate(true))) {
//						appointment.updateContent("", newServiceName);
//				}
//			}
//		
//		}

	}

	/**
	 * 
	 * @param String date
	 * @author gtjarvis & AntoineW
	 */
	@When("the owner starts the appointment at {string}")
	public void the_owner_starts_the_appointment_at(String dateString) {
		
		// AntoineW did this start------
		List<String> dateTime = ControllerUtils.parseString(dateString, "+");
		
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));

		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		// AntoineW did this end------
//		try{
//			FlexiBookController.startAppointment(specificAppointment.getBookableService().getName(), specificAppointment.getTimeSlot().getStartDate(), specificAppointment.getTimeSlot().getStartTime());
//		} catch (InvalidInputException e) {
//			error += e.getMessage();
//			errorCntr++;
//		}	
		specificAppointment.startAppointment(FlexiBookApplication.getCurrentDate(true), FlexiBookApplication.getCurrentTime(true));
	}

	/**
	 * 
	 * @param String date
	 * @author gtjarvis & AntoineW
	 */
	@When("the owner ends the appointment at {string}")
	public void the_owner_ends_the_appointment_at(String dateString) {
		
		// AntoineW did this start------
		List<String> dateTime = ControllerUtils.parseString(dateString, "+");
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		// AntoineW did this end------
		
    	try{
    		FlexiBookController.endAppointment(specificAppointment.getBookableService().getName(), specificAppointment.getTimeSlot().getStartDate(), specificAppointment.getTimeSlot().getStartTime());
		} catch (InvalidInputException e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	
	/**
	 * @author chengchen
	 */
	@Then("the appointment shall be booked")
	public void the_appointment_shall_be_booked() {
	    assertEquals(false, flexiBook.getAppointments().size()==0);
	}
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * Here I'm assuming that this is the case where the customer makes appointment with only one
	 * service and changed only one service.
	 * 
	 * @param string
	 * @author mikewang
	 */
	@Then("the service in the appointment shall be {string}")
	public void the_service_in_the_appointment_shall_be(String serviceName) {
	    // Write code here that turns the phrase above into concrete actions
		for( Appointment appointment : flexiBook.getAppointments()) {
			if (appointment.getChosenItems().size() ==0) {
				for (ComboItem comboItem: appointment.getChosenItems()) {
					assertEquals(serviceName, comboItem.getService().getName());
				}
			}

		}
		
	}
	
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @author mikewang
	 */
	@Then("the appointment shall be for the date {string} with start time {string} and end time {string}")
	public void the_appointment_shall_be_for_the_date_with_start_time_and_end_time(String date, String startTime, String endTime) {
		if (findAppointmentByStartDate(stringToDate(date)).size()==2) {
			assertEquals(stringToTime(startTime),specificAppointment.getTimeSlot().getStartTime());
			assertEquals(stringToTime(endTime),specificAppointment.getTimeSlot().getEndTime());
		}
		else {
			for (Appointment appointment:findAppointmentByStartDate(stringToDate(date))) {
				if(appointment.getChosenItems().size() ==0) {
					assertEquals(stringToTime(startTime), appointment.getTimeSlot().getStartTime());
					assertEquals(stringToTime(endTime),appointment.getTimeSlot().getEndTime());
				}
			}
		}
		
//		assertEquals(stringToDate(date),flexiBook.getAppointment(1).getTimeSlot().getStartDate());
//		assertEquals(stringToTime(startTime),flexiBook.getAppointment(1).getTimeSlot().getStartTime());
//		assertEquals(stringToTime(endTime),flexiBook.getAppointment(1).getTimeSlot().getEndTime());
	}
	
	
	
	
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @param string
	 * @author mikewang
	 */
	
	@Then("the username associated with the appointment shall be {string}")
	public void the_username_associated_with_the_appointment_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
		for (Appointment appointment: flexiBook.getAppointments()) {
			if (appointment.getChosenItems().size() ==0) {
				assertEquals(appointment.getCustomer().getUsername(), string);
		
			}
		}
	}
	
	
	/**
	 * @author Catherine
	 */
	@Then("the user {string} shall have {int} no-show records")
	public void the_user_shall_have_no_show_records(String username, Integer noShowCount) {
		assertEquals(noShowCount, findCustomer(username).getNoShowCount());
	}
	
	
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @param int1
	 * @author mikewang
	 */
	@Then("the system shall have {int} appointments")
	public void the_system_shall_have_appointments(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(flexiBook.getAppointments().size(), int1);
	}
	
	
	/**
	 * Scenario: Change the date and time of appointment for a service on its day
	 */
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @param customer
	 * @param newDate
	 * @param newTime
	 * @param currentDateTime
	 * @author mikewang
	 */
	@When("{string} attempts to update the date to {string} and time to {string} at {string}")
	public void attempts_to_update_the_date_to_and_time_to_at(String customer, String newDate, String newTime, String currentDateTime) {
	    // Write code here that turns the phrase above into concrete actions
		TOTimeSlot RegisterTime = currentRegisterTime(currentDateTime);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		for (Appointment appointment: flexiBook.getAppointments()) {
			if(appointment.getChosenItems().size() == 0) {
				if (appointment.getTimeSlot().getStartDate().after(FlexiBookApplication.getCurrentDate(true))) {
					appointment.updateTime(stringToDate(newDate),stringToTime(newTime));
				}
			}
		}
	}
	
	/**
	 * Scenario: Cancel the appointment for a service at least one day ahead
	 */
	/**
	 *  PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @param customer
	 * @param currentDateTime
	 * @author mikewang & jedla & AntoineW
	 * 
	 */
	@When("{string} attempts to cancel the appointment at {string}")
	public void attempts_to_cancel_the_appointment_at(String customer, String currentDateTime) {
	    // Write code here that turns the phrase above into concrete actions
		TOTimeSlot RegisterTime = currentRegisterTime(currentDateTime);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		List<Appointment> deleteAppointment = new ArrayList<Appointment>();
		
		specificAppointment.cancelAppointment(FlexiBookApplication.getCurrentDate(true));
//		for (Appointment appointment: flexiBook.getAppointments()) {
//			if(appointment.getChosenItems().size() == 0) {
//				if (appointment.getTimeSlot().getStartDate().after(FlexiBookApplication.getCurrentDate(true))) {
//					deleteAppointment.add(appointment);
//				}
//			}
//		}
//		if (deleteAppointment != null) {
//			for (Appointment aAppointment : deleteAppointment) {
//				aAppointment.delete();
//			}
//		}
		
		
		
	}

	
	/**
	 * PLEASE DON'T TOUCH MIKE WILL FINISH THIS 
	 * @param int1
	 * @author mikewang
	 */
	@Then("the system shall have {int} appointment")
	public void the_system_shall_have_appointment_1(Integer int1) {
		assertEquals(flexiBook.getAppointments().size(), int1);
	}
	
	/**
	 * @author jedla & AntoineW
	 */
	@When("{string} makes a {string} appointment without choosing optional services for the date {string} and time {string} at {string}")
	public void makes_a_appointment_without_choosing_optional_services_for_the_date_and_time_at(String username, String serviceName, String date, String time, String currentDateTime) {
		TOTimeSlot RegisterTime = currentRegisterTime(currentDateTime);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		TimeSlot timeSlot = new TimeSlot(stringToDate(date), stringToTime(time),stringToDate(date), Time.valueOf(stringToTime(time).toLocalTime().plusMinutes((getDurationOfServiceCombo(serviceName)))), flexiBook);
		//if (flexiBook.getAppointments() == null){

			if (findServiceCombo(serviceName)!= null) {

				Appointment appointment = new Appointment(findCustomer(username), findServiceCombo(serviceName), timeSlot, flexiBook);
				for (ComboItem c: findServiceCombo(serviceName).getServices()) {
					if (c.isMandatory()){
					appointment.addChosenItem(c);
					}
				}
				flexiBook.addAppointment(appointment);
				specificAppointment = appointment;
			}
		
		
		}
//	else {
//		for (Appointment appointment : flexiBook.getAppointments()) {
//			deleteAppointment.add(appointment);		
//		}
//		for (Appointment aAppointment : deleteAppointment) {
//			aAppointment.delete();
//		}
//		if (findServiceCombo(serviceName)!= null) {
//			Appointment appointment = new Appointment(findCustomer(username), findServiceCombo(serviceName), timeSlot, flexiBook);
//			for (ComboItem c: findServiceCombo(serviceName).getServices()) {
//				if (c.isMandatory()){
//					appointment.addChosenItem(c);
//				}
//			}
//			flexiBook.addAppointment(appointment);
//			//specificAppointment = appointment;
//		}
//
//	}
//
//}
	
	/**
	 * @author jedla & AntoineW
	 */
	@When("{string} attempts to add the optional service {string} to the service combo in the appointment at {string}")
	public void attempts_to_add_the_optional_service_to_the_service_combo_in_the_appointment_at(String username, String optionalService, String time) {
		TOTimeSlot RegisterTime = currentRegisterTime(time);
		FlexiBookApplication.setCurrentDate(RegisterTime.getStartDate());
		FlexiBookApplication.setCurrentTime(RegisterTime.getStartTime());
		//for (Appointment appointment:findAppointmentByUserName(username)) {
			//if (!appointment.getTimeSlot().getStartDate().equals(FlexiBookApplication.getCurrentDate(true)) && appointment.getBookableService() instanceof ServiceCombo) {//Probably need to change that this for 
			//{
					//appointment.updateContent("add", optionalService);
					//boolean here = appointment.updateAppointmentContent("add", optionalService, FlexiBookApplication.getCurrentDate(), FlexiBookApplication.getCurrentTime());
		
		specificAppointment.updateAppointmentContent("add", optionalService, FlexiBookApplication.getCurrentDate(true), FlexiBookApplication.getCurrentTime(true));
				//}
			//}
		//}

//	public void attempts_to_add_the_optional_service_to_the_service_combo_in_the_appointment_at(String customer, String optserviceName, String dateAndTime) {
//	    
//		List<String> dateTime = ControllerUtils.parseString(dateAndTime, "+");
//		
//		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
//		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
//		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
//		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
//		Appointment app = null;
//		
//		int appNumber = flexiBook.getAppointments().size();
//		flexiBook.getAppointments().get(appNumber-1).updateContent("add",  optserviceName);
//		
	    
	}
	



		/**
		 * @author jedla
		 */
		
	@Then("the service combo in the appointment shall be {string}")
	public void the_service_combo_in_the_appointment_shall_be(String mainService) {
		for (Appointment instanceOfAppointment : flexiBook.getAppointments()) {
			assertEquals(instanceOfAppointment.getBookableService().getName(), (mainService));
			}
		}
		
	//@Then("the service combo in the appointment shall be {string}")
	//public void the_service_combo_in_the_appointment_shall_be(String serviceCombo) {
	//	assertEquals(FlexiBookApplication.getFlexiBook().getAppointment(0).getBookableService().getName(), serviceCombo);
	
	/**
	 * 
	 * @param itemList
	 * @author jedla
	 */
	@Then("the service combo shall have {string} selected services")
	public void the_service_combo_shall_have_selected_services(String itemList) {
		for (Appointment instanceOfAppointment :flexiBook.getAppointments()) {
			String result = "";
			if (specificAppointment.equals(instanceOfAppointment)){
				int counter = 1;
				for (ComboItem aItem : instanceOfAppointment.getChosenItems()) {
					if (counter != 1){
						result = result + "," + aItem.getService().getName();
					}
					else {
						result = result+ aItem.getService().getName();
						counter= 0;		
					}
				}
				assertEquals(result, itemList);
			}
			
		}
		
		
	}

	
	
	/**
	 * 
	 * @author AntoineW
	 */
	@Then("the appointment shall be in progress")
	public void the_appointment_shall_be_in_progress() {
		// should not be correct here
		// How should we know what is the current appointment
		// Add a currentAppointment but nit using. Currently trying this:
		//int appNumber = flexiBook.getAppointments().size();
		
		// assume the last app in the system is the one we are talking about at this place
		// since we just add it.
		// (please work, amen)
		//assertEquals(flexiBook.getAppointments().get(appNumber-1).getAppointmentStatus(), AppointmentStatus.InProgress);
		assertEquals(specificAppointment.getAppointmentStatus(), AppointmentStatus.InProgress);

	}

	
	/**
	 * 
	 * @param string
	 * @author AntoineW
	 */
	@When("the owner attempts to register a no-show for the appointment at {string}")
	public void the_owner_attempts_to_register_a_no_show_for_the_appointment_at(String string) {
		
		
		List<String> dateTime = ControllerUtils.parseString(string, "+");
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		
		// Again, might dont know which appointment we suppose to get
		//int appNumber = flexiBook.getAppointments().size();
		specificAppointment.registeredNoShow();
		
		 
	}
	
	@When("the owner attempts to end the appointment at {string}")
	public void the_owner_attempts_to_end_the_appointment_at(String string) {
		List<String> dateTime = ControllerUtils.parseString(string, "+");
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		
		// Again, might dont know which appointment we suppose to get
		//int appNumber = flexiBook.getAppointments().size();
		specificAppointment.finishedAppointment();
	}

  
	 
  

	
	/*---------------------------private helper methods--------------------------*/

	/**
	 * Converts string to date
	 * @param str
	 * @return
	 * @author AntoineW
	 */
	private static Date stringToDate(String str) {
		return (Date.valueOf(LocalDate.parse(str, DateTimeFormatter.ISO_DATE)));
	}

	/**
	 * Converts string to time
	 * @param str
	 * @return
	 * @author AntoineW
	 */

	private static Time stringToTime(String str) {
		if (str.charAt(2) != ':') {
			str = "0" + str;
		}
		return (Time.valueOf(LocalTime.parse(str, DateTimeFormatter.ISO_TIME)));
	}

	/**
	 * This is a helper method which would return the time and date when the customer made an appointment
	 * @param DateTime
	 * @return TOTimeSlot timeSlotOfRegister
	 * @author mikewang
	 */
	private TOTimeSlot currentRegisterTime(String DateTime) {
		String[] sepDateTime = DateTime.split("\\+");
		String DateString = "";
		String TimeString = ""; 
		DateString = sepDateTime[0];
		TimeString = sepDateTime[1];
		Date dateOfRegister = stringToDate(DateString);
		Time timeOfRegister = stringToTime(TimeString);
		TOTimeSlot timeSlotOfRegister = new TOTimeSlot(dateOfRegister, timeOfRegister, dateOfRegister, timeOfRegister);
		return timeSlotOfRegister;
	}
	/**
	 * This method is a helper method for finding a particular user by username.
	 * User can be the owner or a customer 
	 * 
	 * @param username
	 * @return User with username or null
	 * @author Catherine
	 */
	private static User findUser(String username){
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
	 * Helper method to find all appointments associated to an account
	 * @param username
	 * @return List of associated appointments
	 * @author Catherine
	 */
	private static List<Appointment> findAppointmentsForCustomer(String username) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			if (app.getCustomer().getUsername().equals(username)){
				appointments.add(app);
			}
		}
		return appointments;
	}
	
	
	/**
	 * Helper method to find a single service using the service name string
	 * @param name
	 * @return Service
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
	 * Helper method to find a single serviceCombo using the service name string
	 * @param name
	 * @return
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
	 * 
	 * @param sc
	 * @param name
	 * @return
	 * @throws InvalidInputException
	 * @author AntoineW
	 */
	public static ComboItem findComboItemByServiceName(ServiceCombo sc, String name) throws InvalidInputException {
		
		for (ComboItem ci: sc.getServices()) {
			if(ci.getService().getName().equals(name)) {
				return ci;
			}
		}
		
		throw new InvalidInputException("No Such Service in the given combo");
		
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
	 * Helper method to find customer given username
	 * 
	 * @param userName
	 * @return
	 * @author MikeWang
	 */
	public static Customer findCustomer(String userName){
		for (Customer user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (user.getUsername().equals( userName) ) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
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
	 * Helper method to get a day of the week from a string
	 * @param day
	 * @return 
	 * @author jedla & AntoineW
	 */
	private static DayOfWeek stringToDay(String day) {
		DayOfWeek dw = null;
		if (day.equals("Monday")) {
			return DayOfWeek.Monday;
		} else if (day.equals("Tuesday")) {
			return DayOfWeek.Tuesday;

		} else if (day.equals("Wednesday")) {
			dw = DayOfWeek.Wednesday;
		} else if (day.equals("Thursday")) {
			dw = DayOfWeek.Thursday;
		} else if (day.equals("Friday")) {
			dw = DayOfWeek.Friday;
		} else if (day.equals("Saturday")) {
			dw = DayOfWeek.Saturday;
		} else if (day.equals("Sunday")) {
			dw = DayOfWeek.Sunday;
		}
		return dw;

	}
	
	/**
	 * This helper method finds the corresponding BusinessHour
	 * @param day
	 * @param startTime
	 * @return
	 * @author jedla
	 */
	private static BusinessHour isTheBusinessHour(DayOfWeek day, Time startTime) {

		List<BusinessHour> hoursList = FlexiBookApplication.getFlexiBook().getBusiness().getBusinessHours();
		for(BusinessHour x: hoursList) {
			if(x.getDayOfWeek().equals(day) && x.getStartTime().equals(startTime)) {
				return x;
			}
		} return null;
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

	
	/**
	 * This is a query method which can return all availble time slot to an ArrayList
	 * @param date
	 * @param ByDay
	 * @param ByWeek
	 * @author mikewang
	 * @return <TOTimeSlot> availble time  
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
	 * This is a query method which can get all ComboItems from a specific appointment into a list of TOComboItem
	 * @param appointment
	 * @return
	 * @author mikewang
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
	 * This method finds the appointments that starts in specific time 
	 * it will return appointments starting at some time even if they are not in the same day
	 * @param startTime
	 * @return
	 * @author mikewang
	 */
	public static List<Appointment> findAppointmentByStartTime(Time startTime){
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			if (app.getTimeSlot().getStartTime().equals(startTime)) {
				appointments.add(app);
			}
		}
		return appointments;
	}
	
	/**
	 * This method finds the appointments which belong to a specific customer
	 * @param username
	 * @return
	 * @author mikewang
	 */
	public static List<Appointment> findAppointmentByUserName(String username){
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			if (app.getCustomer().getUsername().equals(username)) {
				appointments.add(app);
			}
		}
		return appointments;
	}

	/**
	 * This method find the appointments which start at the specified date
	 * @param date
	 * @return
	 * @author chengchen
	 */
	public static List<Appointment> findAppointmentByStartDate(Date date){
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (Appointment app : FlexiBookApplication.getFlexiBook().getAppointments()) {
			if (app.getTimeSlot().getStartDate().equals(date)) {
				appointments.add(app);
			}
		}
		return appointments;
	}
	
	/**
	 * This method finds the duration of a serviceCombo
	 * @param name
	 * @return
	 * @author jedla & chengchen
	 * 
	 */
	public static int getDurationOfServiceCombo(String name) {
		int result = 0;
		for (BookableService bookableService:FlexiBookApplication.getFlexiBook().getBookableServices()) {
			if (bookableService instanceof ServiceCombo && bookableService.getName().equals(name)) {
				for (ComboItem comboItem:((ServiceCombo) bookableService).getServices()) {
					if (comboItem.isMandatory()) {
						result = result + comboItem.getService().getDuration();
					}
					
				}
			}
		}
//		ServiceCombo comboName = findServiceCombo(name);
//				for (ComboItem combo: comboName.getServices()) {
//			if (combo.isMandatory()) {
//				result =+ combo.getService().getDuration();
//			}
//		}
		return result;
	}
	
	/**
	 * This method gets a String representing the List of ComboItem
	 * @param listComboItem
	 * @return
	 * @author jedla
	 */
	
	public static String getStringOfComboItem(List<ComboItem> list) {
		
		String result = "";
		
		for (ComboItem aComboItem : list) {
			result = aComboItem.getService().getName();
		}
		return result;
	}

	





}
