package ca.mcgill.ecse.flexibook.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;



import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.BookableService;
import ca.mcgill.ecse.flexibook.model.Business;
import ca.mcgill.ecse.flexibook.model.FlexiBook;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;





public class CucumberStepDefinitions {
	private FlexiBook flexiBook = new FlexiBook(); 
	private Owner owner;
	private Business business;
	private String error;
	private int errorCntr; 
	private Service aService;
	
	/**
	 * @author chengchen
	 */
	
/*---------------------------Test Add Service--------------------------*/
	
	
	@Before
	public static void setUp() {
		// clear all data
		FlexiBookApplication.getFlexiBook().delete();
	}
	
	@Given("a Flexibook system exists")
	public void aFlexiBookExists() {
		flexiBook = FlexiBookApplication.getFlexiBook();
		error = "";
		errorCntr = 0;
				
	}

	@Given("an owner account exists in the system")
	public void anOwnerAccountExists() {
		owner = new Owner("owner", "owner", flexiBook);
		flexiBook.setOwner(owner);

	}
	@Given("a business exists in the system")
	public void aBusinessExistsInTheSystem () {
		 Business b = new Business("test", "test", "test", "test", flexiBook);
		 flexiBook.setBusiness(b);

	}
	
	@Given("the Owner with username {string} is logged in")
	public void theOwnerWithUsernameIsLoggedIn(String username) {
		FlexiBookApplication.setCurrentLoginUser(owner);
	}
	
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
		

		
	
	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String name) {
		assertEquals(name, flexiBook.getBookableServices().get(0).getName());
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
		assertTrue(FlexiBookController.findBookableService(name)==null);
	}
	
	@Then("the number of services in the system shall be zero {string}")
	public void the_number_of_services_shall_be_zero(String numService) {
		assertEquals(Integer.parseInt(numService), flexiBook.numberOfBookableServices());
	}
	@Given ("the following services exist in the system:")
	 public void theFollowingServicesExist(List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 Service s = new Service(map.get("name"), flexiBook, Integer.parseInt(map.get("duration")), Integer.parseInt(map.get("downtimeDuration")), Integer.parseInt(map.get("downtimeStart")) );
			 flexiBook.addBookableService(s);
		 }
	 }
	
	 @Then("the service {string} shall still preserve the following properties:")
	 public void the_service_shall_still_preserve_the_following_properties(String name, List<Map<String, String>> datatable) {
		 for(Map<String, String> map : datatable) {
			 assertEquals(Integer.parseInt(map.get("duration")),FlexiBookController.findSingleService(name).getDuration());
			 assertEquals(Integer.parseInt(map.get("downtimeDuration")),FlexiBookController.findSingleService(name).getDowntimeDuration());
			 assertEquals(Integer.parseInt(map.get("downtimeStart")),FlexiBookController.findSingleService(name).getDowntimeStart()); 
		 }
		 
	 }
	 @Then("the number of services in the system shall be {int}")
	 public void the_number_of_services_in_the_system_shall_be(int number) {
		 assertEquals(1, flexiBook.getBookableServices().size());
	 }


	@Given("the following customers exist in the system:")
	public void the_following_customers_exist_in_the_system(List<Map<String, String>> dataTable) {
	
	}



	
	
	@After
	public void tearDown() {
		flexiBook.delete();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
