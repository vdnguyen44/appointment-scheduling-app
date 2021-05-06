package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalTime;

/**
 * A class that works with appointment time operations
 */

public class AppointmentTimes {

    /**
     * <p>This method creates a list of local times in intervals of 15 minutes.</p>
     * @return List of available appointment times for selection
     */
    public static ObservableList<LocalTime> getAppointmentTimes() {
        ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
        LocalTime appointmentTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 45);

        while (appointmentTime.isBefore(endTime)) {
            appointmentTimes.add(appointmentTime);
            appointmentTime = appointmentTime.plusMinutes(15);
        }
        appointmentTimes.add(endTime);
        return appointmentTimes;
    }

    /**
     * <p>This method takes the converted start/end times of the appointment and compares it to the office's business hours.</p>
     * @param convertedStartLT Local start time of the appointment converted to eastern time
     * @param convertedEndLT Local end time of the appointment converted to eastern time
     * @return Boolean value of whether both the start and end times of appointment are within the office's business hours
     */
    public static boolean isWithinBusinessHrs(LocalTime convertedStartLT, LocalTime convertedEndLT) {
        LocalTime businessHrStart = LocalTime.of(8,0);
        LocalTime businessHrEnd = LocalTime.of(22, 0);

        boolean startWithinHrs = convertedStartLT.equals(businessHrStart) || convertedStartLT.isAfter(businessHrStart) && convertedStartLT.isBefore(businessHrEnd);
        boolean endWithinHrs = convertedEndLT.isAfter(businessHrStart) && convertedEndLT.equals(businessHrEnd) || convertedEndLT.isBefore(businessHrEnd);

        return startWithinHrs && endWithinHrs;
    }
}
