import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flight extends AirlineComponent implements FlightSubject {
    private final boolean isDirect;
    private String flightNumber;
    private double price;
    private int duration; // in minutes
    private LocalDateTime takeoffTime;
    private LocalDateTime landingTime;
    private List<FlightObserver> observers = new ArrayList<>();
    private String airlineName;

    public Flight(String flightNumber, double price, int duration, LocalDateTime takeoffTime, LocalDateTime landingTime, boolean isDirect, String airlineName) {
        super(airlineName);
        this.flightNumber = flightNumber;
        this.price = price;
        this.duration = duration;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.isDirect = isDirect;
        this.airlineName = airlineName;
    }
    // Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getTakeoffTime() {
        return takeoffTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }
    public String getAirlineName() {
        return airlineName;
    }

    // Setters
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTakeoffTime(LocalDateTime takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime = landingTime;
    }
    public boolean isDirectFlight() {
        return this.isDirect;

    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Flight Number: " + flightNumber +
                ", Price: $" + String.format("%.2f", price) +
                ", Duration: " + duration + " minutes" +
                ", Takeoff Time: " + takeoffTime.format(formatter) +
                ", Landing Time: " + landingTime.format(formatter) +
                ", Airline Name: " + airlineName;
    }

    @Override
    public void add(AirlineComponent component) {
        // Not applicable
    }

    @Override
    public void remove(AirlineComponent component) {
        // Not applicable
    }

    @Override
    public List<Flight> getFlights() {
        return Collections.singletonList(this);
    }

    @Override
    public void registerObserver(FlightObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(FlightObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (FlightObserver observer : observers) {
            observer.update(message);
        }
    }

    public void changeFlightDetails(/* new details */) {
        // Update logic
        notifyObservers("Flight details changed for " + flightNumber);
    }

    public void cancelFlight() {
        // Cancellation logic
        notifyObservers("Flight " + flightNumber + " is cancelled");
    }

}
