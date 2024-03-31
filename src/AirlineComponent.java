import java.util.ArrayList;
import java.util.List;

// Component
public abstract class AirlineComponent {
    public abstract void add(AirlineComponent component);
    public abstract void remove(AirlineComponent component);
    public abstract List<Flight> getFlights();
}


class Airline extends AirlineComponent implements FlightSubject {
    private String airlineName;
    private List<AirlineComponent> components = new ArrayList<>();
    private List<FlightObserver> observers = new ArrayList<>();

    public Airline(String airlineName) {
        this.airlineName = airlineName;
    }
    public String getAirlineName() {
        return airlineName;
    }

    // Setter for airlineName
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    @Override
    public void add(AirlineComponent component) {
        components.add(component);
    }

    @Override
    public void remove(AirlineComponent component) {
        components.remove(component);
    }

    @Override
    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        for (AirlineComponent component : components) {
            flights.addAll(component.getFlights());
        }
        return flights;
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

    public void announceDiscount() {
        notifyObservers("Special discount available for flights in " + airlineName);
    }

    public void changeFlightSchedule(/* parameters for schedule changes */) {
        // Logic to change flight schedule
        notifyObservers("Flight schedule changed in " + airlineName);
    }

    public void cancelFlights(/* parameters to identify flights to cancel */) {
        // Logic to cancel flights
        notifyObservers("Flights cancelled in " + airlineName);
    }
}




