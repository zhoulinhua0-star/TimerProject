# Timer Project

This project consists of several different console-based timer implementations in Java. Each file demonstrates a different approach to working with timers written in java. 

## Files:

1. **CountDownTimer.java** - This file demonstrates the traditional way to implement a timer in Java. It uses basic Java classes to set a timer and handle time-based events.

2. **CountdownTimer2.java** - This file showcases a more advanced approach, using Java's `ScheduledExecutorService` or similar modern methods for creating countdown timers with better performance and flexibility.

3. **StopWatch.java** - A console-based stopwatch designed for interactive use. Within the console, after the user sees the prompt "Enter # to make your move: " showing up, directly enter 0 after the prompt for starting the stopwatch, enter 1 for stopping it, enter 2 for reset and finally if you want to directly exit just enter 3 (hopefully it will work just fine). Essentially rather than saying it's a rigorously designed stopwatch, it's more like an interactive game designated for users and learners who are interested in getting familiar with the usage of Duration and Instant class as well as manipulating time-related stuff via Java.

4. **SmartAlarmClock.java** - A simple console-based Java alarm clock application that allows users to set alarms, enable snooze(not yet, i believe it requires integration with external hardware or system components, so the implementation is left as a placeholder, i add this functionality basically to make it more smart alarm alike), and automatically stop the alarm after a specified duration. It simulates a real alarm with beeping sounds and a live clock display. Run the program using a Java IDE or command line. The program will display the current date and time. Enter the desired alarm time in HH:mm:ss format. Choose whether to enable snooze (Y/N). Enter auto-stop duration in seconds (default is 10 seconds if invalid input). Wait until the alarm triggers: If snooze is enabled, it will reschedule automatically after the set snooze duration. Press ENTER to stop the alarm manually at any time.

## How to Use:

You can edit the code to suit your specific use case. All files allows potential customization, while the other two provide basic timer functionalities. Considering they are just rudimentary popularization of java knowledge, it can just basically serve as beginner-friendly study material for readers who are interested in related topics.

## Contributions:

Feel free to fork this project, make changes, and open a pull request if you have suggestions or further improvements.

## License:

This project is open-source and available for free.
