package utils;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class UserLogInActivity {

    public static void updateLogInActivity(String attemptedUser, String loginAttemptStatus) throws IOException {
        String filename = "src/login_activity.txt";

        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedNow = ZonedDateTime.of(now, localZoneID);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String attemptedLogin = String.format("Attempted login for %s at %s %s", attemptedUser, formatter.format(zonedNow), loginAttemptStatus);

        outputFile.println(attemptedLogin);

        outputFile.close();
    }
}
