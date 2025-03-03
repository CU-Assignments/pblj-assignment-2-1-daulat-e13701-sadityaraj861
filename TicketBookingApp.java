import java.util.concurrent.*;

class TicketBookingSystem {
    private final boolean[] seats;
    private final Object lock = new Object();

    public TicketBookingSystem(int numSeats) {
        this.seats = new boolean[numSeats]; // false indicates available, true means booked
    }

    public boolean bookSeat(int seatNumber, String customer) {
        synchronized (lock) {
            if (seatNumber < 0 || seatNumber >= seats.length) {
                System.out.println(customer + " selected an invalid seat number!");
                return false;
            }
            if (!seats[seatNumber]) {
                seats[seatNumber] = true;
                System.out.println(customer + " successfully booked seat " + seatNumber);
                return true;
            } else {
                System.out.println(customer + " failed to book seat " + seatNumber + " (Already booked)");
                return false;
            }
        }
    }
}

class Customer extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;
    private final String customerName;

    public Customer(TicketBookingSystem bookingSystem, int seatNumber, String customerName, int priority) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.setPriority(priority); // Assigning thread priority
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, customerName);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        int totalSeats = 10;
        TicketBookingSystem bookingSystem = new TicketBookingSystem(totalSeats);

        // Creating multiple customers trying to book seats
        Customer c1 = new Customer(bookingSystem, 5, "VIP_Alice", Thread.MAX_PRIORITY);
        Customer c2 = new Customer(bookingSystem, 5, "Regular_Bob", Thread.NORM_PRIORITY);
        Customer c3 = new Customer(bookingSystem, 3, "VIP_Charlie", Thread.MAX_PRIORITY);
        Customer c4 = new Customer(bookingSystem, 3, "Regular_Dave", Thread.NORM_PRIORITY);

        // Start threads
        c1.start();
        c2.start();
        c3.start();
        c4.start();

        // Ensure all threads finish execution
        try {
            c1.join();
            c2.join();
            c3.join();
            c4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
