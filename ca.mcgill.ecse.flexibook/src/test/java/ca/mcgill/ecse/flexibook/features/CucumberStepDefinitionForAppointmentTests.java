package ca.mcgill.ecse.flexibook.features;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.ControllerUtils;
import ca.mcgill.ecse.flexibook.model.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

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
	
	@Given("the system's time and date is {String}")
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
			 Service s = new Service(map.get("name"), flb, Integer.parseInt(map.get("duration")), Integer.parseInt(map.get("downtimeDuration")), Integer.parseInt(map.get("downtimeStart")) );
			 flb.addBookableService(s);
		 }
	 }
	 
	 @Given ("the following service combos exist in the system:")
	 public void theFollowingServiceCombosExist(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 
			 
			 
			 
			 
		 }
		 
	 }
}
