package ca.mcgill.ecse.flexibook.controller;

import java.util.Comparator;

/**
 * overrided comparator method which would compare to TOTimeSlot on their StartTIme
 * @author mikewang
 *
 */
public class CustomComparator implements Comparator<TOTimeSlot> {
    @Override
    public int compare(TOTimeSlot o1, TOTimeSlot o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}
