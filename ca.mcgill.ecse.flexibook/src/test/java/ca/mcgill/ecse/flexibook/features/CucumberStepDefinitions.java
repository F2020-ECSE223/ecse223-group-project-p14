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
import java.util.List;
import java.util.Map;


import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOTimeSlot;
import ca.mcgill.ecse.flexibook.model.Appointment;
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





public class CucumberStepDefinitions {
	private FlexiBook flexiBook;

	private Owner owner;
	private Business business;
	private String error;
	private int errorCntr; 
	private Service aService;




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

/*---------------------------Test Log in--------------------------*/
	/**
	 * As an owner, I want to log in so that I can access the space to manage my business. 
  	 * As a customer, I want to log in so that I can manage my appointments.
     * The owner account is created automatically if it does not exist.
	 *
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

	@Then("the user should be successfully logged in")
	public void the_user_should_be_successfully_logged_in() {
		assertEquals(false, FlexiBookApplication.getCurrentLoginUser()==null);
	}
	
	@Then("the user should not be logged in")
	public void the_user_should_not_be_logged_in() {
		assertEquals(true, FlexiBookApplication.getCurrentLoginUser()==null);
	}
	
	@Then("a new account shall be created")
	public void a_new_account_shall_be_created() {
		assertEquals(true, flexiBook.getOwner()!=null);
	}

	@Then("the user shall be successfully logged in")
	public void the_user_shall_be_successfully_logged_in() {
		assertEquals(true, FlexiBookApplication.getCurrentLoginUser() instanceof Owner);	    
	}

/*---------------------------Test view appointment calendar--------------------------*/
	

	@When("{string} requests the appointment calendar for the week starting on {string}")
	public void requests_the_appointment_calendar_for_the_week_starting_on(String user, String date) {
			FlexiBookController.viewAppointmentCalnader(date, false, true);
	}
	@Then("the following slots shall be unavailable:")
	public void the_following_slots_shall_be_unavailable(List<Map<String, String>> datatable) throws InvalidInputException {
		Boolean isUnavailable = false;
		for(Map<String, String> map : datatable) {
			for (TOTimeSlot time:FlexiBookController.getUnavailbleTime(map.get("date"), true, false)) {
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
	@Then("the following slots shall be available:")
	public void the_following_slots_shall_be_available(io.cucumber.datatable.DataTable dataTable) {
		    
	}




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
		assertEquals(name, FlexiBookController.findSingleService(name).getName());
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
		assertEquals(null, FlexiBookController.findBookableService(name));;
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
			 assertEquals(Integer.parseInt(map.get("duration")),FlexiBookController.findSingleService(name).getDuration());
			 assertEquals(Integer.parseInt(map.get("downtimeDuration")),FlexiBookController.findSingleService(name).getDowntimeDuration());
			 assertEquals(Integer.parseInt(map.get("downtimeStart")),FlexiBookController.findSingleService(name).getDowntimeStart()); 
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
		 Customer customer = FlexiBookController.findCustomer(username);
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
	 * @author chengchen
	 */
	@Then("the number of appointments in the system with service {string} shall be {string}")
	public void the_number_of_appointments_in_the_system_with_service_shall_be(String serviceName, String numService) {
		List<Appointment> appointments= FlexiBookController.findAppointmentByServiceName(serviceName);
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
	 * @author chengchen
	 */
	@Then("the service combos {string} shall not exist in the system")
	public void the_service_combos_shall_not_exist_in_the_system(String comboName) {
		assertTrue(FlexiBookController.findServiceCombo(comboName)==null);
	}
	/**
	 * @author chengchen
	 */
	@Then("the service combos {string} shall not contain service {string}")
	public void the_service_combos_shall_not_contain_service(String comboName,String serviceName) {
		ServiceCombo serviceCombo = FlexiBookController.findServiceCombo(comboName);
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
	//----------------------------------------make app -----------------------------------------------------------------------------


	/**
	 * @author Catherine & @ TODO Who else wrote this one?
	 */
	@Given("a Flexibook system exists")
	public void a_Flexibook_System_Exists() {
		flexiBook = FlexiBookApplication.getFlexiBook();
		FlexiBookApplication.clearCurrentLoginUser();
		error = "";
		errorCount = 0;
		appointmentCount = flexiBook.getAppointments().size();
	}

	@Given("the system's time and date is {string}")
	public void system_Time_And_Date_Is(String string) {
		
		List<String> dateTime = ControllerUtils.parseString(string, "+");

		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));

		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		
	}
	
	 @Given ("a business exists in the system")
	 public void a_Business_Exists_In_The_System() {
		 Business b = new Business("test", "test", "test", "test", flexiBook);
		 flexiBook.setBusiness(b);
	 }
	
	 @Given ("the following customers exist in the system:")
	 public void the_Following_Customers_Exist(List<Map<String, String>> datatable){
		 for(Map<String, String> map : datatable) {
			 Customer c = new Customer(map.get("username"), map.get("password"), flexiBook);
			 flexiBook.addCustomer(c);
		 }
	 }
	 
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
					 ComboItem optSer = new ComboItem(true, FlexiBookController.findSingleService(servicename), sc);
					 sc.addService(optSer);
				 }else if(mandatorylist.get(index).equals("false")) {
					 ComboItem optSer = new ComboItem(false, FlexiBookController.findSingleService(servicename), sc);
					 sc.addService(optSer);
				 }			 
			 }
			 
			 // set main service
			 try {
				ComboItem mSer = ControllerUtils.findComboItemByServiceName(sc, map.get("mainService"));
				sc.setMainService(mSer);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
	
			 flexiBook.addBookableService(sc);
			 
		 }
	 }
	 
	 
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
	 
	 @Given ("the business has the following holidays")
	 public void the_business_has_the_following_holidays(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {

			TimeSlot ts = new TimeSlot(stringToDate(map.get("startDate")), 
					stringToTime(map.get("startTime")), stringToDate(map.get("endDate")), stringToTime(map.get("endTime")), flexiBook);
			flexiBook.getBusiness().addHoliday(ts);
			//flb.addTimeSlot(ts);
		 }			 
	 }
	 
	 
	 @Given ("the following appointments exist in the system:")
	 public void the_following_appointments_exist_in_the_system(List<Map<String, String>> datatable) throws InvalidInputException {
		 for(Map<String, String> map : datatable) {
			 
			 String custname = map.get("customer");
			
			 Customer c = FlexiBookController.findCustomer(custname);
			 
			 BookableService bs = FlexiBookController.findBookableService(map.get("serviceName"));
			  
			 
			 TimeSlot ts = new TimeSlot(stringToDate(map.get("date")),stringToTime(map.get("startTime")), 
					 stringToDate(map.get("date")), stringToTime(map.get("endTime")), flexiBook);		 
	
			 Appointment app = new Appointment(c,bs, ts, flexiBook );
			 
			 if(!map.containsKey("optServices")||map.get("optServices").equals("none")) {
				 
			 }else {
				 
				 ServiceCombo sc = FlexiBookController.findServiceCombo(map.get("serviceName"));
				 
				 List<String> serviceNameList = ControllerUtils.parseString(map.get("optServices"), ",");
				 
				 for (ComboItem item: sc.getServices() ) {
					 //add main service no matter what
					 if(item.getService().getName().equals(sc.getMainService().getService().getName())) {
						 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, item.getService().getName()));
					 }else {
						 //if not, we check all input option string component in certain order. If
						 // a component exist then we add
						 for(String name :serviceNameList ) {
							 if (item.getService().getName().equals(name)) {
								 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, name));
							 }
							 // if the optional service name is not mentioned, we skip
						 }					 
					 }			 
				 }
				 for(String name :serviceNameList ) {
					 //Service s =  FlexiBookController.findSingleService(name);
					 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, name));
				 }
				 flexiBook.addAppointment(app);
				 
			 }
				 
		 }
		 
		 appointmentCount = flexiBook.getAppointments().size();
	 }
	 
	 
	 
	 @Given ("{string} is logged in to their account")
	 public void user_is_logged_in_to_their_account(String name){
		 
		 if(FlexiBookController.findCustomer(name) !=null) {
			 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(name));	
		 }else if (name.equals("owner")) {
			 FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		 }
		 	 
	 }

	 @When ("{string} schedules an appointment on {string} for {string} at {string}")
	 public void customer_schedules_an_appointment_on_date_for_service_at_time(String customer, String date, String Servicename, String time) {
		 
		 // customer name is not used since it is in the current user
		 try {
			FlexiBookController.addAppointmentForService(Servicename, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
		}		 
	 }
	 
	 @Then ("{string} shall have a {string} appointment on {string} from {string} to {string}")
	 public void customer_shall_have_a_service_appointment_on_date_from_startTime_to_endTime(String customer, String Servicename, 
			 String date,  String timeStart, String timeEnd) {
		 boolean isTheCase = false;
		 for (Appointment app :FlexiBookController.findCustomer(customer).getAppointments()) {
 
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
	 
	 @Then ("there shall be {int} more appointment in the system")
	 public void there_shall_be_some_more_appointment_in_the_system(int i) {
		 assertEquals(flexiBook.getAppointments().size() - appointmentCount, i);
		 appointmentCount = flexiBook.getAppointments().size();
	 }
	 

	 @Then("the system shall report {string}")
	 public void the_system_shall_report_error(String str) {
		 assertTrue(error.contains(str));
	 }


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
	@When("{string} attempts to update their {string} appointment on {string} at {string} to {string} at {string}")
	public void attempts_to_update_their_appointment_on_at_to_at(String customer, String serviceName, String oldDate, 
			String oldTime, String newD, String newT) {

		try {
			statusOfAppointment = FlexiBookController.updateAppointment(serviceName, stringToDate(oldDate),stringToTime( oldTime), stringToDate(newD) ,stringToTime(newT));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}

	}


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




	@Given("{string} has a {string} appointment with optional sevices {string} on {string} at {string}")
	public void has_a_appointment_with_optional_sevices_on_at(String customer, String serviceName, String optService, 
			String date, String time) {

		try {
			FlexiBookController.addAppointmentForComboService(serviceName, optService, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
		}
	}



	@When("{string} attempts to {string} {string} from their {string} appointment on {string} at {string}")
	public void attempts_to_from_their_appointment_on_at(String customer, String action, String comboItem, String serviceName,
			String date, String time) {
		try {
			statusOfAppointment = FlexiBookController.updateAppointmentForServiceCombo(serviceName, 
					stringToDate(date), stringToTime(time), action, comboItem);

			appointmentCount = flexiBook.getAppointments().size();
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}
	}


	@When("{string} attempts to update {string}'s {string} appointment on {string} at {string} to {string} at {string}")
	public void attempts_to_update_s_appointment_on_at_to_at(String user, String custmerName, String serviceName, 
			String oldDate, String  oldTime, String newD, String newT) {

		if(FlexiBookController.findCustomer(user) !=null) {
			FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(user));	
		}else if (user.equals("owner")) {
			FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		}
		try {
			statusOfAppointment = FlexiBookController.updateAppointment(serviceName, stringToDate(oldDate),stringToTime( oldTime), 
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

	@When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	public void attempts_to_cancel_their_appointment_on_at(String user, String serviceName, String date, String time) {

		try {
			statusOfAppointment = FlexiBookController.cancelAppointment(serviceName, stringToDate(date),stringToTime(time));
		} catch (InvalidInputException e) {
			error = error + e.getMessage();
		}

	}


	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) {

		Appointment app = FlexiBookController.findAppointment(string2, stringToDate(string3), stringToTime(string4)); 
		// should be removed thus no longer found in the system -> assert to be null
		assertEquals(null, app);
	}

	@Then("there shall be {int} less appointment in the system")
	public void there_shall_be_less_appointment_in_the_system(Integer int1) {
		// one less -> thus subtraction should be negative number
		assertEquals(flexiBook.getAppointments().size() - appointmentCount, int1 * (-1));
		appointmentCount = flexiBook.getAppointments().size();
	}




	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attempts_to_cancel_s_appointment_on_at(String curUser, String customer, String serviceName, String date, String time) {
		if(FlexiBookController.findCustomer(curUser) !=null) {
			FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(curUser));	
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
		if(FlexiBookController.findUser(username) != null) {
			if(username != "owner") customerCount--;
			FlexiBookController.findUser(username).delete();
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
		if(FlexiBookController.findUser(username) instanceof Owner) {
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
		if(FlexiBookController.findUser(username) == null) {
			if(username.equals("owner")) { 
				owner = new Owner("owner", "owner", flexiBook);
				flexiBook.setOwner(owner);
			}
			else {
				flexiBook.addCustomer(username, "password");
				customerCount++;
			}
		}	
	}

	/**
	 * @author Catherine
	 */
	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String username) {
		FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findUser(username));
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
		Appointment appointment = new Appointment((FlexiBookController.findCustomer(username)), service , timeSlot, FlexiBookApplication.getFlexiBook());
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
		assertNull(FlexiBookController.findUser(username));
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
		assertFalse(FlexiBookController.findUser(username) == null);
	}

	/*---------------------------Test Define Service Combo--------------------------*/

	/**
	 * 
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

	@Then("the service combo {string} shall exist in the system")
	public void the_service_combo_name_shall_exist_in_the_system(String name){
		assertEquals(FlexiBookController.findServiceCombo(name).getName(), name);
	}

	@Then("the service combo {string} shall not exist in the system")
	public void the_service_combo_name_shall_not_exist_in_the_system(String name){
		assertEquals(FlexiBookController.findServiceCombo(name), null);
	}

	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_comboName_shall_contain_the_services_serviceName_with_mandatory_setting_mandatorySetting(String name, String servicesString, String mandatorySettingsString){
		List<String> servicesList = Arrays.asList(servicesString.split(","));
		List<String> mandatorySettingsList = Arrays.asList(mandatorySettingsString.split(","));
		ServiceCombo serviceCombo = FlexiBookController.findServiceCombo(name);
		List<ComboItem> comboList= serviceCombo.getServices();
		for(int i = 0; i < comboList.size(); i++){
			assertEquals(comboList.get(i).getService().getName(), servicesList.get(i));
			assertEquals(comboList.get(i).getMandatory(), Boolean.parseBoolean(mandatorySettingsList.get(i)));
		}
	}

	@Then("the main service of the service combo {string} shall be {string}")
	public void the_main_service_of_the_service_combo_name_shall_be_mainService(String name, String mainService){
		assertEquals(FlexiBookController.findServiceCombo(name).getMainService().getService().getName(), mainService);
	}

	@Then("the service {string} in service combo {string} shall be mandatory")
	public void the_service_mainService_in_service_combo_name_shall_be_mandatory(String mainService, String name){
		List<ComboItem> comboItems = FlexiBookController.findServiceCombo(name).getServices();
		for(ComboItem c: comboItems){
			if(c.getService().getName().equals(mainService)){
				assertTrue(c.getMandatory());
			}
		}
	}

	@Then("the number of service combos in the system shall be {string}")
	public void the_number_of_services_combos_in_the_system_shall_be_num(String num){
		int n = Integer.parseInt(num);
		assertEquals(FlexiBookController.getServiceCombos().size(), n);
	}

	@Then("the service combo {string} shall preserve the following properties:")
	public void the_service_combo_name_shall_preserve_the_following_properties(String name, List<Map<String, String>> datatable){
		ServiceCombo serviceCombo = FlexiBookController.findServiceCombo(name);
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
			newBusinessHour = FlexiBookController.isTheBusinessHour(stringToDay(day), stringToTime(startTime));
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
				stringToDate(endDate), stringToTime(endTime), FlexiBookApplication.getFlexiBook());

		if (type.equals("vacation")) {
			FlexiBookApplication.getFlexiBook().getBusiness().addVacation(VacationOrHoliday);
		} else if (type.equals("holiday")) {
			FlexiBookApplication.getFlexiBook().getBusiness().addHoliday(VacationOrHoliday);
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
	 * As an owner i want to update existing business hours
	 * @author jedla
	 */
	 
	 @When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
	public void the_user_tries_to_change_the_business_hour_at_to_be_on_starting_at_and_ending_at(String oldDay, String oldStartTime, String newDay, String newStartTime, String newEndTime) {
		try {
			FlexiBookController.updateBusinessHour(stringToDay(oldDay), stringToTime(oldStartTime), stringToDay(newDay), stringToTime(newStartTime), stringToTime(newEndTime)); 
			isSetUp = true;
			newBusinessHour = FlexiBookController.isTheBusinessHour(stringToDay(newDay), stringToTime(newStartTime));
																								
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
	 * As an owner i want to remove an existing time slot (vacation or holiday)
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
	 
	

	
	

	/*---------------------------Test Delete Service Combo--------------------------*/

	/**
	 * 
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
	 * 
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

	@Then("the service combo {string} shall be updated to name {string}")
	public void the_service_combo_shall_be_updated_to_name(String name, String newName) {
		if(!name.equals(newName)){
			assertNull(FlexiBookController.findServiceCombo(name));
		}
		assertTrue(FlexiBookController.findServiceCombo(newName) != null);
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


	

	@After
	public void tearDown() {
		flexiBook.delete();
	}
	
	
	/**
	 * Helper method to find an appointment associated to an account
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
	 * Helper method to get a day of the week from a string
	 * @param day
	 * @return dayOfWeek
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
	
	


}
