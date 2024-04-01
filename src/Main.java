import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        // Create airline and notification system
        Airline airline = new Airline("Delta Airlines");
        FlightNotificationSystem notificationSystem = new FlightNotificationSystem();

        // Create flights with varying details
        Flight flight1 = new Flight("FL100", 300.00, 180, LocalDateTime.now(), LocalDateTime.now().plusHours(3),false);
        Flight flight2 = new Flight("FL200", 300.00, 120, LocalDateTime.now(), LocalDateTime.now().plusHours(2),true);
        Flight flight3 = new Flight("FL300", 350.00, 90, LocalDateTime.now(), LocalDateTime.now().plusHours(1).plusMinutes(30),true);
        Flight flight4 = new Flight("FL400", 450.00, 200, LocalDateTime.now(), LocalDateTime.now().plusHours(4),false);

        System.out.println("Airline Name: " + airline.getAirlineName());


        airline.setAirlineName("Updated Airlines");
        System.out.println("Updated Airline Name: " + airline.getAirlineName());


        System.out.println("Flight Number: " + flight1.getFlightNumber());
        System.out.println("Flight Price: " + flight1.getPrice());



        flight1.setFlightNumber("FL100-Updated");
        flight1.setPrice(350.00);

        System.out.println("Updated Flight Number: " + flight1.getFlightNumber());
        System.out.println("Updated Flight Price: " + flight1.getPrice());


        airline.add(flight1);
        airline.add(flight2);
        airline.add(flight3);
        airline.add(flight4);


        Passenger passenger = new Passenger();
        Employee employee = new Employee();

        flight1.registerObserver(passenger);
        flight2.registerObserver(employee);
        flight3.registerObserver(passenger);
        flight4.registerObserver(employee);

        notificationSystem.registerObserver(passenger);
        notificationSystem.registerObserver(employee);

        // Triggering notifications
        notificationSystem.notifyObservers("Test Notification");

        // Trigger notifications
        flight1.changeFlightDetails();
        flight2.cancelFlight();
        airline.announceDiscount();

        // Apply different search strategies
        FlightSearchContext context = new FlightSearchContext();

        // Search by Price
        context.setStrategy(new SearchByPrice());
        System.out.println("Flights sorted by price:");
        context.executeStrategy(airline.getFlights()).forEach(f -> System.out.println(f.getFlightNumber()));

        // Search by Duration
        context.setStrategy(new SearchByDuration());
        System.out.println("\nFlights sorted by duration:");
        context.executeStrategy(airline.getFlights()).forEach(f -> System.out.println(f.getFlightNumber()));

        // Search for Direct Flights
        context.setStrategy(new SearchForDirectFlight());
        System.out.println("\nDirect flights:");
        context.executeStrategy(airline.getFlights()).forEach(f -> System.out.println(f.getFlightNumber()));

        // Search for Best Flights
        context.setStrategy(new SearchForBestFlight());
        System.out.println("\nBest flights (price & duration):");
        context.executeStrategy(airline.getFlights()).forEach(f -> System.out.println(f.getFlightNumber()));
    }
}
