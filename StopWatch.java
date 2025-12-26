import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class StopWatch {
    private Instant startTime;
    private Duration elapsed = Duration.ZERO;
    private boolean isRunning = false;
    private static Scanner scanner = new Scanner(System.in);

    private void start() {
        if (isRunning) {
            System.out.println("Stopwatch already running...");
            return;
        }
        isRunning = true;
        startTime = Instant.now();
        System.out.println("Timer started...\n");
    }

    private void stop() {
        if (!isRunning) {
            System.out.println("Not started yet...\n");
            return;
        }
        elapsed = elapsed.plus(Duration.between(startTime, Instant.now()));
        isRunning = false;
        startTime = null;
        System.out.println("Stopwatch stopped.\n");
        showTimeElapsed();
    }

    private void showTimeElapsed() {
        System.out.println("Time elapsed: " +
                            elapsed.toMillis() + " ms (" +
                            elapsed.toSeconds() + " s, " +
                            elapsed.toMinutes() + " min)\n");
    }

    private void reset() {
        elapsed = Duration.ZERO;
        isRunning = false;
        startTime = null;
        System.out.println("Stopwatch reset...\n");
    }

    public static void main(String[] args) {
        StopWatch stopwatch = new StopWatch();
        System.out.println("0.start 1.stop 2.reset 3.exit\n");

        while (true) {
            System.out.print("Enter # to make your move: ");
            int input = scanner.nextInt();

            switch (input) {
                case 0 -> stopwatch.start();
                case 1 -> stopwatch.stop();
                case 2 -> stopwatch.reset();
                case 3 -> {
                    if (stopwatch.isRunning) {
                        stopwatch.stop();
                    }
                    System.out.println("Exit the system...\nThank you for using!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid input...Please try again!");
            }
        }
    }
}