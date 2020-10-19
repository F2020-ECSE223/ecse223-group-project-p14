//package ca.mcgill.ecse.flexibook.features;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
//import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
//import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
//import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
//import ca.mcgill.ecse.flexibook.model.*;
//import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//
//public class CucumberStepDefinitionForAppointmentTests {
//	
//	private int appointmentCount = 0;
//	private int errorCount = 0;
//	private boolean status = false;
//	private String error = "";
//	
//	private FlexiBook flb;
//	
//	/**
//	 * 
//	 * @author AntoineW
//	 *
//	 */
//	//----------------------------------------make app -----------------------------------------------------------------------------
//	@Before
//	public static void setUp() {
//		// clear all data
//		FlexiBookApplication.getFlexiBook().delete();
//	}
//	
//	@Given("a Flexibook system exists")
//	public void aFlexibookSystemExists() {
//		flb = FlexiBookApplication.getFlexiBook();
//		error = "";
//		errorCount = 0;
//		appointmentCount = flb.getAppointments().size();
//	}
//	
//	@Given("the system's time and date is {string}")
//	public void systemTimeAndDateIs(String string) {
//		
//		List<String> dateTime = ControllerUtils.parseString(string, "+");
//		
//		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
//		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
//		
//		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
//		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
//		
//	}
//	
//	 @Given ("an owner account exists in the system")
//	 public void aOwnerExist() {
//		 Owner aNewOwner = new Owner("owner", "owner", flb);
//		 flb.setOwner(aNewOwner);
//	 }
//	 
//	 @Given ("a business exists in the system")
//	 public void aBusinessExistsInTheSystem() {
//		 Business b = new Business("test", "test", "test", "test", flb);
//		 flb.setBusiness(b);
//	 }
//	
//	 @Given ("the following customers exist in the system:")
//	 public void theFollowingCustomersExist(List<Map<String, String>> datatable){
//		 for(Map<String, String> map : datatable) {
//			 Customer c = new Customer(map.get("username"), map.get("password"), flb);
//			 flb.addCustomer(c);
//		 }
//	 }
//	 
//	 @Given ("the following services exist in the system:")
//	 public void theFollowingServicesExist(List<Map<String, String>> datatable) {
//		 for(Map<String, String> map : datatable) {
//			 Service s = new Service(map.get("name"), flb, 
//					 Integer.parseInt(map.get("duration")),
//					 Integer.parseInt(map.get("downtimeDuration")), 
//					 Integer.parseInt(map.get("downtimeStart")) );
//			 
//			 flb.addBookableService(s);
//		 }
//	 }
//	 
//	 @Given ("the following service combos exist in the system:")
//	 public void theFollowingServiceCombosExist(List<Map<String, String>> datatable) {
//		 for(Map<String, String> map : datatable) {
//			
//			 ServiceCombo sc = new ServiceCombo(map.get("name"), flb);
//			 
//			
//			 
//			 // adding a list of comboItems
//			 List<String> namelist = ControllerUtils.parseString(map.get("services"), ",");
//			 List<String> mandatorylist = ControllerUtils.parseString(map.get("mandatory"), ",");
//			 for(String servicename: namelist) {
//				 int index = namelist.indexOf(servicename);
//				 
//				 
//				 
//				 if(mandatorylist.get(index).equals("true")) {
//					 ComboItem optSer = new ComboItem(true, FlexiBookController.findSingleService(servicename), sc);
//					 sc.addService(optSer);
//				 }else if(mandatorylist.get(index).equals("false")) {
//					 ComboItem optSer = new ComboItem(false, FlexiBookController.findSingleService(servicename), sc);
//					 sc.addService(optSer);
//				 }			 
//			 }
//			 
//			 // set main service
//			 try {
//				ComboItem mSer = ControllerUtils.findComboItemByServiceName(sc, map.get("mainService"));
//				sc.setMainService(mSer);
//			} catch (InvalidInputException e) {
//				error = e.getMessage();
//			}
//	
//			 flb.addBookableService(sc);
//			 
//		 }
//	 }
//	 
//	 
//	 @Given ("the business has the following opening hours")
//	 public void theBusinessHasFollowingOpeningHours(List<Map<String, String>> datatable) {
//		 for(Map<String, String> map : datatable) {
//			
//			BusinessHour bh = new BusinessHour(null, stringToTime(map.get("startTime")), stringToTime(map.get("endTime")), flb);
//			if(map.get("day").equals("Monday")) {
//				bh.setDayOfWeek(DayOfWeek.Monday);
//			}else if (map.get("day").equals("Tuesday")) {
//				bh.setDayOfWeek(DayOfWeek.Tuesday);
//			}else if (map.get("day").equals("Wednesday")) {
//				bh.setDayOfWeek(DayOfWeek.Wednesday);
//			}else if (map.get("day").equals("Thursday")) {
//				bh.setDayOfWeek(DayOfWeek.Thursday);
//			}else if (map.get("day").equals("Friday")) {
//				bh.setDayOfWeek(DayOfWeek.Friday);
//			}else if (map.get("day").equals("Saturday")) {
//				bh.setDayOfWeek(DayOfWeek.Saturday);
//			}else if (map.get("day").equals("Sunday")) {
//				bh.setDayOfWeek(DayOfWeek.Sunday);
//			}
//			flb.getBusiness().addBusinessHour(bh);
//			flb.addHour(bh);
//		 }
//	 }
//	 
//	 @Given ("the business has the following holidays")
//	 public void theBusinessHasFollowingHolidays(List<Map<String, String>> datatable) {
//		 for(Map<String, String> map : datatable) {
//			 
//			TimeSlot ts = new TimeSlot(stringToDate(map.get("startDate")), 
//					stringToTime(map.get("startTime")), stringToDate(map.get("endDate")), stringToTime(map.get("endTime")), flb);
//			flb.getBusiness().addHoliday(ts);
//			//flb.addTimeSlot(ts);
//		 }			 
//	 }
//	 
//	 
//	 @Given ("the following appointments exist in the system:")
//	 public void theFollowingAppointmentExist(List<Map<String, String>> datatable) throws InvalidInputException {
//		 for(Map<String, String> map : datatable) {
//			 
//			 String custname = map.get("customer");
//			
//			 Customer c = FlexiBookController.findCustomer(custname);
//			 
//			 BookableService bs = FlexiBookController.findBookableService(map.get("serviceName"));
//			  
//			 
//			 TimeSlot ts = new TimeSlot(stringToDate(map.get("date")),stringToTime(map.get("startTime")), 
//					 stringToDate(map.get("date")), stringToTime(map.get("endTime")), flb);		 
//	
//			 Appointment app = new Appointment(c,bs, ts, flb );
//			 
//			 if(!map.containsKey("optServices")||map.get("optServices").equals("none")) {
//				 
//			 }else {
//				 
//				 ServiceCombo sc = FlexiBookController.findServiceCombo(map.get("serviceName"));
//				 
//				 List<String> serviceNameList = ControllerUtils.parseString(map.get("optServices"), ",");
//				 
//				 for (ComboItem item: sc.getServices() ) {
//					 //add main service no matter what
//					 if(item.getService().getName().equals(sc.getMainService().getService().getName())) {
//						 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, item.getService().getName()));
//					 }else {
//						 //if not, we check all input option string component in certain order. If
//						 // a component exist then we add
//						 for(String name :serviceNameList ) {
//							 if (item.getService().getName().equals(name)) {
//								 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, name));
//							 }
//							 // if the optional service name is not mentioned, we skip
//						 }					 
//					 }			 
//				 }
//				 for(String name :serviceNameList ) {
//					 //Service s =  FlexiBookController.findSingleService(name);
//					 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, name));
//				 }
//				 flb.addAppointment(app);
//				 
//			 }
//				 
//		 }
//		 
//		 appointmentCount = flb.getAppointments().size();
//	 }
//	 
//	 
//	 
//	 @Given ("{string} is logged in to their account")
//	 public void whoIsCurrentlyLoggedIn(String name){
//		 
//		 if(FlexiBookController.findCustomer(name) !=null) {
//			 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(name));	
//		 }else if (name.equals("owner")) {
//			 FlexiBookApplication.setCurrentLoginUser(flb.getOwner());	
//		 }
//		 	 
//	 }
//
//	 @When ("{string} schedules an appointment on {string} for {string} at {string}")
//	 public void customerScheduleOnDateForServiceAtTime(String customer, String date, String Servicename, String time) {
//		 
//		 // customer name is not used since it is in the current user
//		 try {
//			FlexiBookController.addAppointmentForService(Servicename, stringToDate(date), stringToTime(time));
//		} catch (InvalidInputException e) {
//			error = error+ e.getMessage();
//		}
//		 
//	 }
//	 
//	 @Then ("{string} shall have a {string} appointment on {string} from {string} to {string}")
//	 public void customerShallHaveServiceAppointmentOnDateFromStoE(String customer, String Servicename, String date,  String timeStart, String timeEnd) {
//		 boolean isTheCase = false;
//		 for (Appointment app :FlexiBookController.findCustomer(customer).getAppointments()) {
// 
//			 if(app.getCustomer().getUsername() .equals (customer) &&
//					 app.getBookableService().getName() .equals (Servicename) &&
//					 app.getTimeSlot().getStartDate().equals(stringToDate(date)) &&
//					 app.getTimeSlot().getStartTime().equals(stringToTime(timeStart)) && 
//			 		 app.getTimeSlot().getEndTime().equals(stringToTime(timeEnd))){
//				 
//				 	isTheCase = true;
//			 		 }
//		 }
//		 
//		 assertEquals(isTheCase, true);
//	 }
//	 
//	 @Then ("there shall be {int} more appointment in the system")
//	 public void checkHowMuchMoreAppointments(int i) {
//		 assertEquals(flb.getAppointments().size() - appointmentCount, i);
//		 appointmentCount = flb.getAppointments().size();
//	 }
//	 
//
//	 @Then("the system shall report {string}")
//	 public void systemShouldReportErrorMessage(String str) {
//		 assertTrue(error.contains(str));
//	 }
//
//
//	 @When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
//	 public void schedules_an_appointment_on_for_with_at(String customerName, String date, String serviceName, 
//			 String optService, String time) {
//		 
//		try {
//			FlexiBookController.addAppointmentForComboService(serviceName, optService, stringToDate(date), stringToTime(time));
//		} catch (InvalidInputException e) {
//			error = error+ e.getMessage();
//		}
//		    
//	 }
//
//
////---------------------------------------- updating app -----------------------------------------------------------------------------
//
//	 @When("{string} attempts to update their {string} appointment on {string} at {string} to {string} at {string}")
//	 public void attempts_to_update_their_appointment_on_at_to_at(String customer, String serviceName, String oldDate, 
//			 String oldTime, String newD, String newT) {
//		 
//		 try {
//			status = FlexiBookController.updateAppointment(serviceName, stringToDate(oldDate),stringToTime( oldTime), stringToDate(newD) ,stringToTime(newT));
//		} catch (InvalidInputException e) {
//			error = error + e.getMessage();
//		}
//		     
//	 }
//
//
//	 @Then("the system shall report that the update was {string}")
//	 public void the_system_shall_report_that_the_update_was(String string) {
//		 String statusStr = "";
//		 // returned from the controller method!
//		 if (status) {
//			 statusStr = "successful";
//		 }else {
//			 statusStr = "unsuccessful";
//		 }
//		 
//		 assertEquals(statusStr, string);
//	 }
//
//
//	 
//
//	 @Given("{string} has a {string} appointment with optional sevices {string} on {string} at {string}")
//	 public void has_a_appointment_with_optional_sevices_on_at(String customer, String serviceName, String optService, 
//			 String date, String time) {
//		 
//		 try {
//				FlexiBookController.addAppointmentForComboService(serviceName, optService, stringToDate(date), stringToTime(time));
//			} catch (InvalidInputException e) {
//				error = error+ e.getMessage();
//			}
//	 }
//		 
//
//
//	 @When("{string} attempts to {string} {string} from their {string} appointment on {string} at {string}")
//	 public void attempts_to_from_their_appointment_on_at(String customer, String action, String comboItem, String serviceName,
//			 String date, String time) {
//		 try {
//				status = FlexiBookController.updateAppointmentForServiceCombo(serviceName, 
//						stringToDate(date), stringToTime(time), action, comboItem);
//				
//				appointmentCount = flb.getAppointments().size();
//			} catch (InvalidInputException e) {
//				error = error + e.getMessage();
//			}
//	 }
//	 
//
//	 @When("{string} attempts to update {string}'s {string} appointment on {string} at {string} to {string} at {string}")
//	 public void attempts_to_update_s_appointment_on_at_to_at(String user, String custmerName, String serviceName, 
//			 String oldDate, String  oldTime, String newD, String newT) {
//		 
//		 if(FlexiBookController.findCustomer(user) !=null) {
//			 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(user));	
//		 }else if (user.equals("owner")) {
//			 FlexiBookApplication.setCurrentLoginUser(flb.getOwner());	
//		 }
//		 try {
//			status = FlexiBookController.updateAppointment(serviceName, stringToDate(oldDate),stringToTime( oldTime), 
//					stringToDate(newD) ,stringToTime(newT));
//		} catch (InvalidInputException e) {
//			error = error + e.getMessage();
//		}
//		 
//		 
//		 
//	 }
//
//
////--------------------------------------- cancel Appointment ------------------------------------------------
//
//	 @When("{string} attempts to cancel their {string} appointment on {string} at {string}")
//	 public void attempts_to_cancel_their_appointment_on_at(String user, String serviceName, String date, String time) {
//		 
//		 try {
//				status = FlexiBookController.cancelAppointment(serviceName, stringToDate(date),stringToTime(time));
//		} catch (InvalidInputException e) {
//				error = error + e.getMessage();
//		}
//	
//	 }
//
//
//	 @Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
//	 public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) {
//
//		 Appointment app = FlexiBookController.findAppointment(string2, stringToDate(string3), stringToTime(string4)); 
//		 // should be removed thus no longer found in the system -> assert to be null
//		 assertEquals(null, app);
//	 }
//	 
//	 @Then("there shall be {int} less appointment in the system")
//	 public void there_shall_be_less_appointment_in_the_system(Integer int1) {
//		 // one less -> thus subtraction should be negative number
//		 assertEquals(flb.getAppointments().size() - appointmentCount, int1 * (-1));
//		 appointmentCount = flb.getAppointments().size();
//	 }
//
//
//
//
//	 @When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
//	 public void attempts_to_cancel_s_appointment_on_at(String curUser, String customer, String serviceName, String date, String time) {
//		 if(FlexiBookController.findCustomer(curUser) !=null) {
//			 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(curUser));	
//		 }else if (curUser.equals("owner")) {
//			 FlexiBookApplication.setCurrentLoginUser(flb.getOwner());	
//		 }
//		 try {
//			status = FlexiBookController.cancelAppointment(serviceName, stringToDate(date), stringToTime(time));
//		} catch (InvalidInputException e) {
//			error = error + e.getMessage();
//		}
//		 
//	 }
//
//
//	 @After
//	 public void tearDown() {
//		flb.delete();
//	}
//
//
//	 
//	 private static Date stringToDate(String str) {
//		 return (Date.valueOf(LocalDate.parse(str, DateTimeFormatter.ISO_DATE)));
//	 }
//	 
//	 private static Time stringToTime(String str) {
//		 if (str.charAt(2) != ':') {
//			 str = "0" + str;
//		 }
//		 return (Time.valueOf(LocalTime.parse(str, DateTimeFormatter.ISO_TIME)));
//	 }
//}
