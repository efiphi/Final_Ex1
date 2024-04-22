import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


// SearchStrategy Interface
public interface SearchStrategy {
    List<Flight> search(List<Flight> flights);
}

class SearchByPrice implements SearchStrategy {
    @Override
    public List<Flight> search(List<Flight> flights) {
        return flights.stream()
                .sorted(Comparator.comparingDouble(Flight::getPrice))
                .collect(Collectors.toList());
    }
}

class SearchByDuration implements SearchStrategy {
    @Override
    public List<Flight> search(List<Flight> flights) {
        return flights.stream()
                .sorted(Comparator.comparingInt(Flight::getDuration))
                .collect(Collectors.toList());
    }
}
class SearchByTakeoffTime implements SearchStrategy {
    @Override
    public List<Flight> search(List<Flight> flights) {
        return flights.stream()
                .sorted(Comparator.comparing(Flight::getTakeoffTime))
                .collect(Collectors.toList());
    }
}

class SearchForDirectFlight implements SearchStrategy {
    @Override
    public List<Flight> search(List<Flight> flights) {
        return flights.stream()
                .filter(Flight::isDirectFlight)
                .collect(Collectors.toList());
    }
}



// Context Class
class FlightSearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Flight> executeStrategy(List<Flight> flights) {
        return strategy.search(flights);
    }
}

