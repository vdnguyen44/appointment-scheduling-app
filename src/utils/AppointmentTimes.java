package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class AppointmentTimes {



    public static ObservableList<LocalTime> getAppointmentTimes() {
        ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
        LocalTime appointmentTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        while (appointmentTime.isBefore(endTime)) {
            appointmentTimes.add(appointmentTime);
            appointmentTime = appointmentTime.plusMinutes(15);
        }
        appointmentTimes.add(endTime);
        return appointmentTimes;
    }
}
