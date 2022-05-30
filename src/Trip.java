import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Trip {
    LocalDate start;
    LocalDate end;
    String destination;
    int price;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Trip(LocalDate _start, LocalDate _end, String _destination, int _price){
        start = _start;
        end = _end;
        destination = _destination;
        price = _price;
    }

    public String getDestination() {
        return destination;
    }

    String getInfo(){
        return String.format(
                "<div style=\"text-align: left; background-color: yellow;\">Trip to %s, lasts from %s to %s, costs %d</div>"
                , destination, start.format(dateFormatter), end.format(dateFormatter), price);
    }

    public String toString(){
        return String.format(
                "Trip to  %s, lasts from %s to %s, costs %d"
                , destination, start.format(dateFormatter), end.format(dateFormatter), price);
    }

}
