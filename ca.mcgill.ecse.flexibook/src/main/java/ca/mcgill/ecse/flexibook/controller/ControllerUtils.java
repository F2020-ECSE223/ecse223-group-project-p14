package ca.mcgill.ecse.flexibook.controller;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.model.*;


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
	 * getDowntimes() returns the downtime time slots for a given appointment as List<TimeSlot>
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
			for(ComboItem ci: ((ServiceCombo)bs).getServices()) {
				
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
				}
				
			}
		}
		
		return ret;
	
	}
	

}
