import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    private static FlightSearchContext context = new FlightSearchContext();
    private static List<FlightObserver> observers = new ArrayList<>();
    private static List<String> notifications = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        List<FlightObserver> observers = new ArrayList<>();
        FlightObserver currentObserver = loginOrCreateObserver(scanner);
        flights.add(new Flight("FL100", 300.00, 180, LocalDateTime.now(), LocalDateTime.now().plusHours(3), false,"ELAL"));
        flights.add(new Flight("FL200", 200.00, 150, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4), false,"ARKIA"));
        flights.add(new Flight("FL300", 400.00, 90, LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), true,"UNITED"));
        flights.add(new Flight("FL400", 250.00, 120, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(5), true,"AMERICAN AIRLINES"));
        // Add more flights as needed

        int mainChoice;

        do {
            mainChoice = displayMainMenu(scanner);
            switch (mainChoice) {
                case 1: // Strategy Search
                    int searchChoice1 = displaySearchMenu(scanner);
                    handleSearchStrategy(searchChoice1, flights);
                    break;
                case 2: // Flight options
                    int searchChoice2 = displayFlightComponent(scanner,currentObserver);
                    handleFlightComponent(searchChoice2, flights, observers, currentObserver);
                    break;
                case 3: // Notifications
                    int searchChoice3 = displayNotificationsMenu(scanner);
                    handleNotificationSystem(searchChoice3, flights, observers);
                    break;
                case 4: // Exit
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (mainChoice != 4);
        scanner.close();


    }


    private static void handleSearchStrategy(int searchChoice, List<Flight> flights) {

        switch (searchChoice) {
            case 1:
                context.setStrategy(new SearchByPrice());
                break;
            case 2:
                context.setStrategy(new SearchByDuration());
                break;
            case 3:
                context.setStrategy(new SearchForDirectFlight());
                break;
            case 4:
                context.setStrategy(new SearchByTakeoffTime());
                break;
            case 5: // Exit
                System.out.println("Exiting the system. Goodbye!");
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        List<Flight> searchResults = context.executeStrategy(flights);
        displaySearchResults(searchResults);
    }

    private static void handleFlightComponent(int searchChoice, List<Flight> flights, List<FlightObserver> observers, FlightObserver observer) {
        if ("Employee".equals(observer.getType())) {
            switch (searchChoice) {
                case 1:
                    displayFlights(flights);
                    break;
                case 2:
                    addFlight(scanner, flights);
                    break;
                case 3: // Cancel flight
                    cancelFlight(scanner, flights, observers);
                    break;
                case 4: // Apply discount
                    applyDiscount(scanner, flights, observers);
                    break;
                case 5: // Exit
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }


        }else if ("Passenger".equals(observer.getType())) {
            // Only viewing option available for Passengers
            if (searchChoice == 1) {
                System.out.println("View flights:");
                displayFlights(flights);
            } else {
                System.out.println("Invalid option for passengers. Please try again.");
            }
        }
    }


    private static void handleNotificationSystem(int searchChoice, List<Flight> flights, List<FlightObserver> observers) {
        switch (searchChoice) {
            case 1:
                registerObserver(scanner);
                break;
            case 2:
                removeObserver(scanner);
                break;
            case 3:
                viewNotifications();
                break;
            case 4: // Exit
                System.out.println("Exiting the system. Goodbye!");
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static int displayMainMenu(Scanner scanner) {
        System.out.println("\nWelcome to the Airline Management System!");
        System.out.println("Please select an option to proceed:");
        System.out.println("1. View flights sort options:");
        System.out.println("2. Flight options(based on role):");
        System.out.println("3. Observer options:");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private static int displaySearchMenu(Scanner scanner) {
        System.out.println("\nWelcome to the search flight system!");
        System.out.println("Please select an option to proceed:");
        System.out.println("1. View flights sorted by price");
        System.out.println("2. View flights sorted by duration");
        System.out.println("3. View direct flights");
        System.out.println("4. View flights sorted by takeoff time");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private static int displayFlightComponent(Scanner scanner, FlightObserver observer) {
        System.out.println("Please select an option to proceed:");
        if ("Employee".equals(observer.getType())) {
            System.out.println("1. View flights:");
            System.out.println("2. Add flight");
            System.out.println("3. cancel flight");
            System.out.println("4. apply discount");
            System.out.println("5. Exit");
        }else if ("Passenger".equals(observer.getType())) {
            System.out.println("1. View flights:");
            System.out.println("2. Exit");
        }
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private static int displayNotificationsMenu(Scanner scanner) {
        System.out.println("Please select an option to proceed:");
        System.out.println("1. Register Observer");
        System.out.println("2. Remove Observer");
        System.out.println("3. View Notifications");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }


    private static void displayFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println(flight); // Assuming Flight has a proper toString implementation
        }
    }

    private static void displaySearchResults(List<Flight> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No flights found.");
        } else {
            for (Flight flight : searchResults) {
                System.out.println(flight); // Ensure that Flight class has a proper toString() method
            }
        }
    }

    private static void addFlight(Scanner scanner, List<Flight> flights) {
        System.out.println("Please enter the new flight details:");

        System.out.print("Flight Number: ");
        String flightNumber = scanner.next();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Duration (minutes): ");
        int duration = scanner.nextInt();

        System.out.println("Takeoff Time (yyyy-MM-ddTHH:mm): ");
        System.out.print("Example: (2024-04-22T15:30): ");
        String takeoffStr = scanner.next();
        LocalDateTime takeoffTime = LocalDateTime.parse(takeoffStr);

        System.out.println("Landing Time (yyyy-MM-ddTHH:mm): ");
        System.out.print("Example: (2024-04-22T17:30): ");
        String landingStr = scanner.next();
        LocalDateTime landingTime = LocalDateTime.parse(landingStr);

        System.out.print("Is direct (true/false): ");
        boolean direct = scanner.nextBoolean();

        System.out.print("Airline Name: ");
        String airlineName = scanner.next();

        Flight newFlight = new Flight(flightNumber, price, duration, takeoffTime, landingTime, direct, airlineName);
        flights.add(newFlight);

        String message = "New flight added: Flight Number " + flightNumber + ", Price: $" + price +
                ", Duration: " + duration + " minutes, Takeoff: " + takeoffTime + ", Landing: " + landingTime +
                ", Direct: " + direct;
        notifyObservers(observers, message);
        notifications.add(message);  // Storing the notification

        System.out.println("New flight added successfully:");
        System.out.println(newFlight);
    }


    private static void notifyObservers(List<FlightObserver> observers, String message) {
        for (FlightObserver observer : observers) {
            observer.update(message);
        }
    }


    private static void cancelFlight(Scanner scanner, List<Flight> flights, List<FlightObserver> observers) {
        displayFlights(flights);
        System.out.println("Enter the number of the flight to cancel:");
        String flightId = scanner.next();
        boolean isRemoved = flights.removeIf(flight -> Objects.equals(flight.getFlightNumber(), flightId));
        if (isRemoved) {
            String message = "Flight ID " + flightId + " has been canceled.";
            notifyObservers(observers, message);
            notifications.add(message);  // Storing the notification
        } else {
            System.out.println("Flight ID not found.");
        }
    }


    private static void applyDiscount(Scanner scanner, List<Flight> flights, List<FlightObserver> observers) {
        displayFlights(flights);
        System.out.println("Enter the number of the flight to apply discount:");
        String flightId = scanner.next();
        System.out.println("Enter discount percentage:");
        double discount = scanner.nextDouble();
        flights.forEach(flight -> {
            if (Objects.equals(flight.getFlightNumber(), flightId)) {
                double oldPrice = flight.getPrice();
                double newPrice = oldPrice * (1 - discount / 100);
                flight.setPrice(newPrice);
                String message = "Discount of " + discount + "% applied to Flight ID " + flightId + "; new price: $" + newPrice;
                notifyObservers(observers, message);
                notifications.add(message);  // Storing the notification
            }
        });
    }

    private static void registerObserver(Scanner scanner) {
        System.out.print("Enter name for new observer: ");
        String name = scanner.next();
        System.out.print("Is the observer an Employee or a Passenger? (E/P): ");
        String type = scanner.next();
        FlightObserver observer = type.equalsIgnoreCase("E") ? new Employee(name) : new Passenger(name);
        observers.add(observer);
        System.out.println("Observer registered: " + name);
    }

    private static void removeObserver(Scanner scanner) {
        System.out.print("Enter name of observer to remove: ");
        String name = scanner.next();
        observers.removeIf(o -> o.getName().equals(name));
        System.out.println("Observer removed: " + name);
    }

    private static void viewNotifications() {
        System.out.println("Notifications:");
        notifications.forEach(System.out::println);
    }

    private static FlightObserver loginOrCreateObserver(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("Are you an Employee or a Passenger? (E/P): ");
        String type = scanner.next();
        FlightObserver observer = type.equalsIgnoreCase("E") ? new Employee(name) : new Passenger(name);
        observers.add(observer);
        return observer;


    }
}
