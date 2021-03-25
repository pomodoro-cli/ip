import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// some planned features include tracking programme state,
// which will affect the behaviour of what command the parser should output. this is for later on tho
public class Pomo {

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    private static final ArrayList<Task> tasklist = new ArrayList<Task>();

    // abstract into stopwatch soon
    private static long startTime;
    private static long endTime;

    private static Task currTask;

    private static Status status = Status.IDLING;


    public static void printBlue(String s) {
        System.out.println(String.format("%s%s%s", ANSI_BLUE, s, ANSI_RESET)); // just testing colour changes
    }

    public static void printPurple(String s) {
        System.out.println(String.format("%s%s%s", ANSI_PURPLE, s, ANSI_RESET)); // just testing colour changes
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        intro();

        while (true) {
            System.out.print("  ");
            parse(sc.nextLine());
        }
        //        Scanner sc = new Scanner(System.in);
        //        String desc = "";
        //
        //        printPurple("please enter a description");
        //        System.out.print("  ");
        //        desc = sc.nextLine();
        //
        //        printBlue("starting!");
        //        printPurple("please enter any character to stop the timer");
        //
        //        System.out.print("  ");
        //
        //        long start = System.nanoTime();
        //        // Stopwatch s = new Stopwatch();
        //
        //        // scanner pauses until there's a next thing
        //        String s1 = sc.next();
        //
        //        sc.close();
        //
        //        printBlue("stopped!"); // just testing colour changes
        //        long finish = System.nanoTime();
        //        long timeElapsed = finish - start;
        //
        //        tasklist.add(new Task(desc, timeElapsed));
        //
        //        printTaskList();


    }

    private static void intro() {
        printBlue("Welcome!");
        printMessageForCurrStatus();
    }

    private static void parse(String nextLine) {
        String[] args = nextLine.split(" ", 2);
        switch (args[0]) {
        case "start":
            startTimer(args[1]);
            break;
        case "list":
            printTaskList();
            break;
        default:
            if (status == Status.STARTED) {
                stopTimer();
            } else if (status == Status.IDLING) {
                startTimer(nextLine);
            }
        }
        printMessageForCurrStatus();
    }

    private static void stopTimer() {
        endTime = System.nanoTime();
        status = Status.IDLING;
        currTask.setTimeElapsed(endTime - startTime);
        printStop();
    }

    private static void printStop() {
        printBlue("stopped!");
        System.out.println(currTask);
    }

    private static void printIdling() {
        printPurple("please enter your next task description or 'start' or 'list' to get started");
    }

    private static void printStart() {
        printBlue("started! current task: " + currTask);
        printPurple("please enter any character to stop the timer");
    }

    private static void printMessageForCurrStatus() {
        switch (status) {
        case IDLING:
            printIdling();
            break;
        case STARTED:
            printStart();
            break;
        //        case STOPPED:
        //            printStop();
        //            status = Status.IDLING;
        }

        // since stopping a timer goes back to idling this
    }

    private static void startTimer(String desc) {
        startTime = System.nanoTime();
        currTask = new Task(desc);
        status = Status.STARTED;
        tasklist.add(currTask);
    }

    private static void printTaskList() {
        System.out.println("here's your current task list");
        String indent = "  ";
        // tasklist.stream().map(Task::toString).forEach(Pomo::printPurple);
        for (int i = 0; i < tasklist.size(); i++) {
            System.out.println(String.format("%s%d. %s", indent, i + 1, tasklist.get(i)));
        }
    }
}
