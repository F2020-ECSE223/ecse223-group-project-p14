package ca.mcgill.ecse.flexibook.features;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;




import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.BookableService;
import ca.mcgill.ecse.flexibook.model.Business;
import ca.mcgill.ecse.flexibook.model.Customer;
import ca.mcgill.ecse.flexibook.model.FlexiBook;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.model.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class CucumberStepDefinitions {
	private FlexiBook flexiBook = new FlexiBook(); 
	private Owner owner;
	private Business business;
	private String error;
	private int errorCntr; 
	
	/**
	 * @author chengchen
	 */
	
/*---------------------------Test Add Service--------------------------*/
	
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
		business = flexiBook.getBusiness();
	}
	
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
	
	
	
/*---------------------------Test Sign Up Customer--------------------------*/
	
	/**
	 * Feature: Sign up for customer account
	 * As a prospective customer, I want to create an account with username and password
	 * so that I can log in later
	 * 
	 * @author Catherine
	 */
	
	//Scenario: Create a new account successfully
	@Given("there is no existing username {string}") 
	public void there_is_no_existing_username(String username){
		for (Customer user : FlexiBookApplication.getFlexiBook().getCustomers()) {
			if (user.getUsername().equals("username") ) {
				user.delete();
				break;
			}
		}
	}

	@When("the user provides a new username {string} and a password {string}")
	public void the_user_provides_a_new_username_and_a_password(String username, String password) {
		try {
			FlexiBookController.signUpCustomer(username, password);
		}
		catch(InvalidInputException e){
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	@Then("a new customer account shall be created")
	public void a_new_customer_account_shall_be_created() {
		assertEquals(1, flexiBook.getCustomers().size());
	}
	
	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String username, String password) {
		assertEquals(username, flexiBook.getCustomer(0).getUsername());
		assertEquals(password, flexiBook.getCustomer(0).getPassword());
	}
	
	@Then("no new account shall be created")
	public void no_new_account_shall_be_created() {
		assertEquals(0, flexiBook.getCustomers().size()); //@ TODO what if there was a customer already?
	}
	
	@Then("an error message {string} shall be raised")
	public void an_error_message_shall_be_raised(String errorMsg) {
		assertTrue(error.contains(errorMsg));
	}

	//there is an existing username "owner"
	@Given("there is an existing username {string}")
	public void there_is_an_existing_username(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	//logged in as owner
	@Given("the user is logged in to an account with username {string}")
	public void the_user_is_logged_in_to_an_account_with_username(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	
	
	
	
	
}
