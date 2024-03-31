import java.util.ArrayList;
import java.util.List;

// Observer Interface
public interface FlightObserver {
    void update(String message);
}

// Concrete Observers
class Passenger implements FlightObserver {
    @Override
    public void update(String message) {
        System.out.println("Passenger notified: " + message);
    }
}

class Employee implements FlightObserver {
    @Override
    public void update(String message) {
        System.out.println("Employee notified: " + message);
    }
}

// Subject Interface
interface FlightSubject {
    void registerObserver(FlightObserver observer);
    void removeObserver(FlightObserver observer);
    void notifyObservers(String message);
}

// Concrete Subject
class FlightNotificationSystem implements FlightSubject {
    private List<FlightObserver> observers = new ArrayList<>();

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
}

