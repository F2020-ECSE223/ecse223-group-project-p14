package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.model.*;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;


public class ControllerUtils {
	
	/**
	 * Helper method for checking Constraint:  dateTime(Date, Time) returns a complete DateTime object
	 * @author AntoineW
	 */
	public static LocalDateTime combineDateAndTime(Date date, Time time) {
		LocalDate datePart = date.toLocalDate();
	    LocalTime timePart = time.toLocalTime();
	    LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);
	    return dateTime;
	}

	/**
	 * returns the downtime time slots for a given appointment as List<TOTimeSlot>
	 * The reason why it returns TO is because
	 * we dont want to use the actual model since it will add timeslot in the system which 
	 * messes up other check
	 * @author AntoineW
	 */
	public static List<TOTimeSlot> getDowntimesByAppointment(Appointment app) {
		List<TOTimeSlot> ret = new ArrayList<TOTimeSlot>();
		
		BookableService bs = app.getBookableService();
		if(bs instanceof Service ) {
			
			Date startDate = app.getTimeSlot().getStartDate();
			Date endDate = app.getTimeSlot().getEndDate();
			
			// down time start at: the start of appointment(only 1 service) + downtimeStartAt
			LocalTime downtimeStart = app.getTimeSlot().getStartTime().toLocalTime().plusMinutes(((Service) bs).getDowntimeStart());
			Time downtimeStartAt = Time.valueOf(downtimeStart);
			
			// down time end at: DTstart + duration
			LocalTime downtimeEnd = downtimeStartAt.toLocalTime().plusMinutes(((Service) bs).getDowntimeDuration());
			Time downtimeEndAt = Time.valueOf(downtimeEnd);
			
			ret.add(new TOTimeSlot(startDate, downtimeStartAt, endDate, downtimeEndAt));
		}else if(bs instanceof ServiceCombo) {
			Date startDate = app.getTimeSlot().getStartDate();
			Date endDate = app.getTimeSlot().getEndDate();
			int totalTimeElapsed = 0;
			
			// ordered list of all service in the service combo
			for(ComboItem ci: app.getChosenItems()) {
				Service s = ci.getService();
				int dtStartAt = s.getDowntimeStart();
				int dtDuration = s.getDowntimeDuration();
				int serviceDuration = s.getDuration();
				
				
				if(dtDuration != 0) {
					// down time start at: the start of appointment + all prior service duration + current servicedowntimeStartAt
					LocalTime downtimeStart = app.getTimeSlot().getStartTime().toLocalTime().plusMinutes(totalTimeElapsed + dtStartAt);
					Time downtimeStartAt = Time.valueOf(downtimeStart);
					
					// down time end at: this service's DTstart + duration
					LocalTime downtimeEnd = downtimeStartAt.toLocalTime().plusMinutes(dtDuration);
					Time downtimeEndAt = Time.valueOf(downtimeEnd);
					
					ret.add(new TOTimeSlot(startDate, downtimeStartAt, endDate, downtimeEndAt));	
					
					totalTimeElapsed = totalTimeElapsed + serviceDuration;
				}if(dtDuration == 0) {
					totalTimeElapsed = totalTimeElapsed + serviceDuration;
				}
				
			}
		}
		return ret;
	}
	
	

	/**
	 * Helper method to get the day of week by passing the date
	 * @return Enum DayOfWeek defined in ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek
	 * @author AntoineW
	 */
	public static DayOfWeek getDoWByDate(Date date) {
		
		DayOfWeek ret = null;
		java.time.DayOfWeek dow = date.toLocalDate().getDayOfWeek();
		
		switch(dow) {
			case SUNDAY:
				ret = DayOfWeek.Sunday;
				break;
			case MONDAY:
				ret = DayOfWeek.Monday;
				break;
			case TUESDAY:
				ret = DayOfWeek.Tuesday;
				break;
			case WEDNESDAY:
				ret = DayOfWeek.Wednesday;
				break;
			case THURSDAY:
				ret = DayOfWeek.Thursday;
				break;
			case FRIDAY:
				ret = DayOfWeek.Friday;
				break;
			case SATURDAY:
				ret = DayOfWeek.Saturday;
				break;
			
		}
		return ret;
	}
	
	/**
	 * This method parse the input string.
	 * A java String Tokenizer is used.
	 * This method can be used for general propose, but it was create to parse "cut, wash, ..." for the chosen item
	 * in creating appointment out of a service combo.
	 * @param string
	 * @return
	 * @author AntoineW
	 * 
	 * 
	 */
	public static List<String> parseString(String string ,String delim){
		
		List<String> tokens = new ArrayList<String>();
	    StringTokenizer tokenizer = new StringTokenizer(string, delim);
	    while (tokenizer.hasMoreElements()) {
	        tokens.add(tokenizer.nextToken());
	    }
	    return tokens;
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

}
