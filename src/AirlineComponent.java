import java.util.ArrayList;
import java.util.List;

// Component
public abstract class AirlineComponent {
    protected String name;
    protected List<AirlineComponent> children= new ArrayList<>();
    protected List<Flight> flights = new ArrayList<>();

    public AirlineComponent(String name) {
        this.name = name;
    }
    public void add(AirlineComponent component){
        children.add(component);
    };
    public void remove(AirlineComponent component){
        children.remove(component);
    };
    public List<Flight> getFlights(){
        List<Flight> allFlights = new ArrayList<>(flights); // Start with own flights
        for (AirlineComponent child : children) { // Include flights from children
            allFlights.addAll(child.getFlights());
        }
        return allFlights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public String getName() {
        return name;
    }
}


class Airline extends AirlineComponent {
    public Airline(String name) {
        super(name);
    }
}


