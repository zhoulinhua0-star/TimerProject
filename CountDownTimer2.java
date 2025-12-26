import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
public class CountDownTimer2 {


        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                runCountdownTimer(scanner);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }

        private static void runCountdownTimer(Scanner scanner) {
            System.out.print("Count down from: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                return;
            }

            int initialSeconds = scanner.nextInt();

            if (initialSeconds < 0) {
                System.out.println("Please enter a valid integer.");
                return;
            }

            startCountdown(initialSeconds);
        }

        private static void startCountdown(int initialSeconds) {
            AtomicInteger remainingSeconds = new AtomicInteger(initialSeconds);
            AtomicReference<ScheduledFuture<?>> scheduledFutureRef = new AtomicReference<>();
            Instant startTime = Instant.now();

            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r, "Countdown-Timer-Thread");
                thread.setDaemon(true);
                return thread;
            });

            System.out.printf("Starting countdown from %d seconds...%n%n", initialSeconds);

            ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
                int current = remainingSeconds.getAndDecrement();

                if (current > 0) {
                    Duration elapsed = Duration.between(startTime, Instant.now());
                    System.out.printf("Time remaining: %d seconds (Elapsed: %d.%03ds)%n",
                            current, elapsed.getSeconds(), elapsed.toMillisPart());
                } else if (current == 0) {
                    System.out.println("0");
                } else {
                    System.out.println("Time is up!");

                    ScheduledFuture<?> future = scheduledFutureRef.get();
                    if (future != null) {
                        future.cancel(false);
                    }

                    scheduler.submit(() -> {
                        scheduler.shutdown();
                        try {
                            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                                scheduler.shutdownNow();
                            }
                        } catch (InterruptedException e) {
                            scheduler.shutdownNow();
                            Thread.currentThread().interrupt();
                        }
                    });
                }
            }, 0, 1, TimeUnit.SECONDS);

            scheduledFutureRef.set(scheduledFuture);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                scheduler.shutdownNow();
                System.out.println("\nCountdown timer stopped.");
            }));

            try {
                long timeout = initialSeconds + 2L;
                if (!scheduler.awaitTermination(timeout, TimeUnit.SECONDS)) {
                    System.out.println("Countdown did not complete in expected time.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                scheduler.shutdownNow();
            }
        }
    }