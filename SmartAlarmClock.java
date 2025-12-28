import java.awt.Toolkit;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SmartAlarmClock {

    private static final Scanner scanner = new Scanner(System.in);

    private LocalTime alarmTime;
    private int snoozeMinutes = 5;
    private int autoStopSeconds = 10;
    private volatile boolean alarmStopped = false;
    private boolean snoozeEnabled = false;

    public static void main(String[] args) {
        SmartAlarmClock sac = new SmartAlarmClock();
        sac.showCurrentTime();
        sac.setAlarm();
        sac.chooseSnooze();
        sac.setAutoStopTime();
        sac.startAlarmThread();
    }

    public void showCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        System.out.println("Current date and time: " + now.format(dtf) + " (" + dayOfWeek + ")");
        switch (dayOfWeek) {
            case SATURDAY, SUNDAY -> System.out.println("Have a nice rest!\n");
            default -> System.out.println("Fight on!\n");
        }
    }

    public void setAlarm() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (alarmTime == null) {
            try {
                System.out.print("Enter alarm time (HH:mm:ss): ");
                alarmTime = LocalTime.parse(scanner.nextLine().trim(), formatter);
                System.out.println("Alarm set for " + alarmTime + "\n");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format.\n");
            }
        }
    }

    public void chooseSnooze() {
        System.out.print("Enable snooze? (Y/N): ");
        String input = scanner.nextLine().trim();
        snoozeEnabled = input.equalsIgnoreCase("Y");
        System.out.println("Snooze " + (snoozeEnabled ? "enabled" : "disabled") + "\n");
    }

    public void setAutoStopTime() {
        try {
            System.out.print("Auto-stop after how many seconds? ");
            autoStopSeconds = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Using default auto-stop time: 10 seconds.");
            scanner.nextLine();
        }

    }

    public void startAlarmThread() {
        new Thread(() -> {
            waitUntilAlarmTime();
            ringAlarm();
        }).start();
    }

    public void waitUntilAlarmTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (LocalTime.now().isBefore(alarmTime)) {
            LocalTime now = LocalTime.now();
            System.out.print("\rCurrent time: " + now.format(dtf));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                System.out.println("Thread interruped...");
            }
        }
        System.out.println();
    }

    public void ringAlarm() {
        System.out.println("\n--------------------------\n** ALARM NOISES **\n--------------------------");
        System.out.println("Press [ENTER] to stop the alarm.\n");

        long startTime = System.currentTimeMillis();
        while (!alarmStopped) {
            Toolkit.getDefaultToolkit().beep(); // beep every iteration
            if (System.currentTimeMillis() - startTime >= autoStopSeconds * 1000L) {
                System.out.println("\nAlarm time reached.");
                if (snoozeEnabled) {
                    snooze();
                } else {
                    stopAlarm();
                }
                return;
            }
            try {
                if (System.in.available() > 0) {
                    scanner.nextLine();
                    stopAlarm();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sleep(500);
        }
    }

    public void snooze() {
        alarmTime = LocalTime.now().plusMinutes(snoozeMinutes);
        // This action requires integration with external hardware or system components.
        // Implementation is left as a placeholder.
        System.out.println("Next alarm at " + alarmTime);
        startAlarmThread();
    }

    public void stopAlarm() {
        alarmStopped = true;
        System.out.println("Alarm stopped.");
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            System.out.println("Thread Interrupted...");
        }
    }

    // Getter and setter methods
    public LocalTime getAlarmTime() { return alarmTime; }
    public int getSnoozeMinutes() { return snoozeMinutes; }
    public int getAutoStopSeconds() { return autoStopSeconds; }
    public boolean getAlarmStopped() { return alarmStopped; }
    public boolean getSnoozeEnabled() { return snoozeEnabled; }

    public void setAlarmTime(LocalTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public void setSnoozeMinutes(int snoozeMinutes) {
        this.snoozeMinutes = snoozeMinutes;
    }

    public void setAutoStopSeconds(int autoStopSeconds) {
        this.autoStopSeconds = autoStopSeconds;
    }

    public void setSnoozeEnabled(boolean snoozeEnabled) {
        this.snoozeEnabled = snoozeEnabled;
    }

}
