import java.util.ArrayList;
import java.util.List;

public class Customer {
    String name;
    String address;
    List<Trip> trips = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Customer(String _name) {
        name = _name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void assignTrip(Trip trip) {
        this.trips.add(trip);
    }

    public String getInfo() {
        StringBuilder customerInfoBuilder = new StringBuilder(String.format("Customer %s, residing at: %s, has the following trips:%n", name, address));
        for (Trip trip : trips) {
            customerInfoBuilder.append(trip.getInfo());
            customerInfoBuilder.append("\\n");
        }
        return customerInfoBuilder.toString();
    }

    public String getHtmlInfo() {
        StringBuilder customerInfoBuilder = new StringBuilder(String.format(
                "<h1 style=\"font-family: serif\">Customer %s, residing at: %s, has the following trips:%n</h1>"
                , name, address));
        for (Trip trip : trips) {
            customerInfoBuilder.append(trip.getInfo());
            customerInfoBuilder.append("");
        }
        return customerInfoBuilder.toString();
    }
}