import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CountDownTimer {

    private static Scanner scanner = new Scanner(System.in);
    private static int count;
    private static void countDownTimer() {
        System.out.print("Count down from: ");

        if (!scanner.hasNext()) {
            System.out.println("Invalid input...");
            return;
        }
        count = scanner.nextInt();

        if (count < 0) {
            System.out.println("Invalid input...");
            return;
        }

        System.out.println("Starting countdown from 3 seconds...\n");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time remaining: " + count + " seconds");
                count--;
                if (count < 0) {
                    System.out.println("Time is up!");
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static void main(String[] args) {
        countDownTimer();
        System.out.println("Countdown timer stopped");
    }
}