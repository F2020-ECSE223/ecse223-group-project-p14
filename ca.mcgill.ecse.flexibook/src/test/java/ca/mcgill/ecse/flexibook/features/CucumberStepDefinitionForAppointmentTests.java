package ca.mcgill.ecse.flexibook.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.*;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author AntoineW
 *
 */
public class CucumberStepDefinitionForAppointmentTests {
	
	private int appointmentCount = 0;
	private int errorCount = 0;
	private String error = "";
	
	private FlexiBook flb;
	
	
	@Before
	public static void setUp() {
		// clear all data
		FlexiBookApplication.getFlexiBook().delete();
	}
	
	@Given("a Flexibook system exists")
	public void aFlexibookSystemExists() {
		flb = FlexiBookApplication.getFlexiBook();
		error = "";
		errorCount = 0;
		appointmentCount = flb.getAppointments().size();
	}
	
	@Given("the system's time and date is {string}")
	public void systemTimeAndDateIs(String string) {
		
		List<String> dateTime = ControllerUtils.parseString(string, "+");
		
		LocalDate d = LocalDate.parse(dateTime.get(0), DateTimeFormatter.ISO_DATE);
		FlexiBookApplication.setCurrentDate(Date.valueOf(d));
		
		LocalTime t = LocalTime.parse(dateTime.get(1), DateTimeFormatter.ISO_TIME);
		FlexiBookApplication.setCurrentTime(Time.valueOf(t));
		
	}
	
	 @Given ("an owner account exists in the system")
	 public void aOwnerExist() {
		 Owner aNewOwner = new Owner("owner", "owner", flb);
		 flb.setOwner(aNewOwner);
	 }
	 
	 @Given ("a business exists in the system")
	 public void aBusinessExistsInTheSystem() {
		 Business b = new Business("test", "test", "test", "test", flb);
		 flb.setBusiness(b);
	 }
	
	 @Given ("the following customers exist in the system:")
	 public void theFollowingCustomersExist(List<Map<String, String>> datatable){
		 for(Map<String, String> map : datatable) {
			 Customer c = new Customer(map.get("username"), map.get("password"), flb);
			 flb.addCustomer(c);
		 }
	 }
	 
	 @Given ("the following services exist in the system:")
	 public void theFollowingServicesExist(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 Service s = new Service(map.get("name"), flb, 
					 Integer.parseInt(map.get("duration")),
					 Integer.parseInt(map.get("downtimeDuration")), 
					 Integer.parseInt(map.get("downtimeStart")) );
			 
			 flb.addBookableService(s);
		 }
	 }
	 
	 @Given ("the following service combos exist in the system:")
	 public void theFollowingServiceCombosExist(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			
			 ServiceCombo sc = new ServiceCombo(map.get("name"), flb);
			 
			
			 
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
	
			 flb.addBookableService(sc);
			 
		 }
	 }
	 
	 
	 @Given ("the business has the following opening hours")
	 public void theBusinessHasFollowingOpeningHours(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 
			LocalTime tSt = LocalTime.parse(map.get("startTime"));
			Time startTime = Time.valueOf(tSt);
			
			LocalTime tEn = LocalTime.parse(map.get("endTime"));
			Time endTime = Time.valueOf(tEn);
			
			BusinessHour bh = new BusinessHour(null, startTime, endTime, flb);
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
			flb.getBusiness().addBusinessHour(bh);
			flb.addHour(bh);
		 }
	 }
	 
	 @Given ("the business has the following holidays")
	 public void theBusinessHasFollowingHolidays(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 
			LocalDate startd = LocalDate.parse(map.get("startDate"), DateTimeFormatter.ISO_DATE);
			Date startDate = Date.valueOf(startd);
			
			LocalDate endd = LocalDate.parse(map.get("endDate"), DateTimeFormatter.ISO_DATE);
			Date endDate = Date.valueOf(endd);
				
			LocalTime startt = LocalTime.parse(map.get("startTime"), DateTimeFormatter.ISO_TIME);
			Time startTime = Time.valueOf(startt);
			
			LocalTime endt = LocalTime.parse(map.get("endTime"), DateTimeFormatter.ISO_TIME);
			Time endTime = Time.valueOf(endt);
				
			 
			TimeSlot ts = new TimeSlot(startDate, startTime, endDate, endTime, flb);
			flb.getBusiness().addHoliday(ts);
			//flb.addTimeSlot(ts);
		 }			 
	 }
	 
	 
	 @Given ("the following appointments exist in the system:")
	 public void theFollowingAppointmentExist(List<Map<String, String>> datatable) throws InvalidInputException {
		 for(Map<String, String> map : datatable) {
			 
			 String custname = map.get("customer");
			
			 Customer c = FlexiBookController.findCustomer(custname);
			 
			 
			 BookableService bs = FlexiBookController.findBookableService(map.get("serviceName"));
			 
			
			 
			 LocalDate startd = LocalDate.parse(map.get("date"), DateTimeFormatter.ISO_DATE);
			 Date date = Date.valueOf(startd);
			 LocalTime startt = LocalTime.parse(map.get("startTime"), DateTimeFormatter.ISO_TIME);
			 Time startTime = Time.valueOf(startt);
			 LocalTime endt = LocalTime.parse(map.get("endTime"), DateTimeFormatter.ISO_TIME);
			 Time endTime = Time.valueOf(endt);
			 
			 TimeSlot ts = new TimeSlot(date,startTime, date, endTime, flb);
			 
	
			 Appointment app = new Appointment(c,bs, ts, flb );
			 
	
			 List<String> serviceNameList = ControllerUtils.parseString(map.get("optServices"), ",");
			 for(String name :serviceNameList ) {
				 //Service s =  FlexiBookController.findSingleService(name);
				 app.addChosenItem(ControllerUtils.findComboItemByServiceName((ServiceCombo)bs, name));
			 }
			 flb.addAppointment(app);	 
		 }
		 
		 appointmentCount = flb.getAppointments().size();
	 }
	 
	 
	 
	 @Given ("{string} is logged in to their account")
	 public void whoIsCurrentlyLoggedIn(String name){
		 FlexiBookApplication.setCurrentLoginUser(FlexiBookController.findCustomer(name));		 
	 }

	 @When ("{string} schedules an appointment on {string} for {string} at {string}")
	 public void customerScheduleOnDateForServiceAtTime(String customer, String date, String Servicename, String time) {
		 
		 // customer name is not used since it is in the current user
		 try {
			FlexiBookController.addAppointmentForService(Servicename, stringToDate(date), stringToTime(time));
		} catch (InvalidInputException e) {
			error = error+ e.getMessage();
			if(error.length()>0) {
				System.out.print(error);
			}
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
		 assertEquals(flb.getAppointments().size() - appointmentCount, i);
		 
		 appointmentCount = flb.getAppointments().size();
	 }
	 



	 
	 
	 private static Date stringToDate(String str) {
		 return (Date.valueOf(LocalDate.parse(str, DateTimeFormatter.ISO_DATE)));
	 }
	 
	 private static Time stringToTime(String str) {
		 return (Time.valueOf(LocalTime.parse(str, DateTimeFormatter.ISO_TIME)));
	 }
}
