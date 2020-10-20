package ca.mcgill.ecse.flexibook.features;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
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
	
	
	
	private int appointmentCount = 0;
	private int errorCount = 0;
	private int customerCount = 0;
	private boolean statusOfAppointment = false;
	
	
	
	/**
	 * @author chengchen
	 */
	
/*---------------------------Test Add Service--------------------------*/
//	
//	@Given("a Flexibook system exists")
//	public void aFlexiBookExists() {
//		flexiBook = FlexiBookApplication.getFlexiBook();
//		error = "";
//		errorCntr = 0;
//				
//	}

	@Given("an owner account exists in the system")
	public void anOwnerAccountExists() {
		owner = new Owner("owner", "owner", flexiBook);
		flexiBook.setOwner(owner);

	}
//	@Given("a business exists in the system")
//	public void aBusinessExistsInTheSystem () {
//		business = flexiBook.getBusiness();
//	}
	
	@Given("the Owner with username {string} is logged in")
	public void theOwnerWithUsernameIsLoggedIn(String username) {
		FlexiBookApplication.setCurrentLoginUser(owner);
	}
	
	@When("{string} initiates the addition of the service {string} with duration {string}, start of down time {string} and down time duration {string}")
	public void initiates_the_addition_of_the_service_with_duration_start_of_down_time_and_down_time_duratrion(String username, String name, String duration, String downtimeDuration, String downtimeStart) throws Throwable{
		
			if (FlexiBookApplication.getCurrentLoginUser().getUsername().equals(username)){
		
				try {
					Service service = new Service(name, flexiBook, Integer.parseInt(duration), Integer.parseInt(downtimeStart), Integer.parseInt(downtimeDuration));
					FlexiBookController.addService(service);
			
					}
				catch (InvalidInputException e) {
					error += e.getMessage();
					errorCntr++;
				}
			}
		
	}
		
	
	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String name) {
		assertEquals(name,flexiBook.getBookableServices().get(0).getName());
	}
	
	@Then("the service {string} shall have duration {string}, start of down time {string} and down time duration {string}")
	public void the_service_shall_have_duration_start_of_down_time_and_down_time_duration(String name, String duration, String downtimeStart, String downtimeDuration) {
		Service service = (Service)flexiBook.getBookableServices().get(0);
		assertEquals(Integer.parseInt(duration),service.getDuration());
		assertEquals(Integer.parseInt(downtimeDuration),service.getDowntimeDuration());
		assertEquals(Integer.parseInt(downtimeStart),service.getDowntimeStart());
		
	}
	
	@Then("the number of services in the system shall be {string}")
	public void the_number_of_services_shall_be(String numService) {
		assertEquals(Integer.parseInt(numService), flexiBook.numberOfBookableServices());
	}
	
	@Then("an error message with content {string} shall be raised")
	public void an_error_message_with_content_shall_be_raised(String errorMsg) {
		assertTrue(error.contains(errorMsg));
	}
	@Then("the service {string} shall not exist in the system")
	public void the_service_shall_not_exist(String name) {
		assertEquals(null, flexiBook.getBookableServices());
	}
	
	@Then("the number of services in the system shall be zero {string}")
	public void the_number_of_services_shall_be_zero(String numService) {
		assertEquals(Integer.parseInt(numService), flexiBook.numberOfBookableServices());
	}

	
	
	@After
	public void tearDown() {
		flexiBook.delete();
	}
	
	
	/**
	 * 
	 * @author AntoineW
	 *
	 */
	//----------------------------------------make app -----------------------------------------------------------------------------
	@Before
	public static void setUp() {
		// clear all data
		FlexiBookApplication.getFlexiBook().delete();
	}
	

	@Given("a Flexibook system exists")
	public void aFlexibookSystemExists() {
		flexiBook = FlexiBookApplication.getFlexiBook();
		FlexiBookApplication.clearCurrentLoginUser();
		error = "";
		errorCount = 0;
		appointmentCount = flexiBook.getAppointments().size();
	}
	
	@Given("the system's time and date is {string}")
	public void systemTimeAndDateIs(String string) {
		
		List<String> dateTime = ControllerUtils.parseString(string, "+");
		
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
		
		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		
	}
	
//	 @Given ("an owner account exists in the system")
//	 public void aOwnerExist() {
//		 Owner aNewOwner = new Owner("owner", "owner", flb);
//		 flb.setOwner(aNewOwner);
//	 }
//	 
	 @Given ("a business exists in the system")
	 public void aBusinessExistsInTheSystem() {
		 Business b = new Business("test", "test", "test", "test", flexiBook);
		 flexiBook.setBusiness(b);
	 }
	
	 @Given ("the following customers exist in the system:")
	 public void theFollowingCustomersExist(List<Map<String, String>> datatable){
		 for(Map<String, String> map : datatable) {
			 Customer c = new Customer(map.get("username"), map.get("password"), flexiBook);
			 flexiBook.addCustomer(c);
		 }
	 }
	 
	 @Given ("the following services exist in the system:")
	 public void theFollowingServicesExist(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 Service s = new Service(map.get("name"), flexiBook, 
					 Integer.parseInt(map.get("duration")),
					 Integer.parseInt(map.get("downtimeDuration")), 
					 Integer.parseInt(map.get("downtimeStart")) );
			 
			 flexiBook.addBookableService(s);
		 }
	 }
	 
	 @Given ("the following service combos exist in the system:")
	 public void theFollowingServiceCombosExist(List<Map<String, String>> datatable) {
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
	 public void theBusinessHasFollowingOpeningHours(List<Map<String, String>> datatable) {
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
	 public void theBusinessHasFollowingHolidays(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 
			TimeSlot ts = new TimeSlot(stringToDate(map.get("startDate")), 
					stringToTime(map.get("startTime")), stringToDate(map.get("endDate")), stringToTime(map.get("endTime")), flexiBook);
			flexiBook.getBusiness().addHoliday(ts);
			//flb.addTimeSlot(ts);
		 }			 
	 }
	 
	 
	 @Given ("the following appointments exist in the system:")
	 public void theFollowingAppointmentExist(List<Map<String, String>> datatable) throws InvalidInputException {
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
	 public void whoIsCurrentlyLoggedIn(String name){
		 
		 if(FlexiBookController.findCustomer(name) !=null) {
			 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(name));	
		 }else if (name.equals("owner")) {
			 FlexiBookApplication.setCurrentLoginUser(flexiBook.getOwner());	
		 }
		 	 
	 }

	 @When ("{string} schedules an appointment on {string} for {string} at {string}")
	 public void customerScheduleOnDateForServiceAtTime(String customer, String date, String Servicename, String time) {
		 
		 // customer name is not used since it is in the current user
		 try {
			FlexiBookController.addAppointmentForService(Servicename, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
		}
		 
	 }
	 
	 @Then ("{string} shall have a {string} appointment on {string} from {string} to {string}")
	 public void customerShallHaveServiceAppointmentOnDateFromStoE(String customer, String Servicename, String date,  String timeStart, String timeEnd) {
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
	 public void checkHowMuchMoreAppointments(int i) {
		 assertEquals(flexiBook.getAppointments().size() - appointmentCount, i);
		 appointmentCount = flexiBook.getAppointments().size();
	 }
	 

	 @Then("the system shall report {string}")
	 public void systemShouldReportErrorMessage(String str) {
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

	 
	 private static Date stringToDate(String str) {
		 return (Date.valueOf(LocalDate.parse(str, DateTimeFormatter.ISO_DATE)));
	 }
	 
	 private static Time stringToTime(String str) {
		 if (str.charAt(2) != ':') {
			 str = "0" + str;
		 }
		 return (Time.valueOf(LocalTime.parse(str, DateTimeFormatter.ISO_TIME)));
	 }

/*---------------------------Test Sign Up Customer--------------------------*/
	
	/**
	 * Feature: Sign up for customer account
	 * As a prospective customer, I want to create an account with username and password
	 * so that I can log in later
	 * 
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

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String username, String password) {
		try {
			FlexiBookController.signUpCustomer(username, password);
		}
		catch(InvalidInputException e){
			error += e.getMessage();
		}
	}
	
	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		assertEquals(1, flexiBook.getCustomers().size() - customerCount);
	}
	
	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String username, String password) {
		assertEquals(username, flexiBook.getCustomer(0).getUsername());
		assertEquals(password, flexiBook.getCustomer(0).getPassword());
	}
	
	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		assertEquals(0, flexiBook.getCustomers().size() - customerCount);
	}

	
	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String errorMsg) {
		assertFalse(FlexiBookApplication.getCurrentLoginUser() instanceof Customer); //for debugging only
		assertTrue(error.contains(errorMsg));
	}

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
	
	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String username) {
		FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findUser(username));
	}
	
	
	
	
	
	
	
}
