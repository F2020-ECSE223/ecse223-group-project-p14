package ca.mcgill.ecse.flexibook.controller;

public class TOAppointmentCanlander {

	private String nameOfTheCustomer;
	private TOTimeSlot timeSlot;
	private String serviceName;
	
	public TOAppointmentCanlander (String aNameOfTheCustomer, TOTimeSlot aTimeSlot, String aServiceName) {
		nameOfTheCustomer = aNameOfTheCustomer;
		timeSlot = aTimeSlot;
		serviceName = aServiceName;
	}
	
	
}
