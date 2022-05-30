import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TravelOffice {

    ArrayList<Customer> customers = new ArrayList<>();

    HashMap<String, Trip> trips = new HashMap<>();

    ArrayList<Trip> getTripsByPrice(int price, boolean cheaper){
        ArrayList<Trip> result = new ArrayList<>();
        for( Trip trip : trips.values()){
            if (cheaper){
                if ( trip.price < price){
                    result.add( trip);
                }
            } else {
                if (trip.price > price){
                    result.add( trip);
                }
            }
        }
        return result;
    }

    void addTrip(Trip trip){
        trips.put( trip.getDestination(), trip);
    }

    public boolean removeTrip(String destinationNameFragment){
        int keysSizeBeforeRemoval = trips.keySet().size();
        trips.keySet().removeIf( destination -> destination.contains(destinationNameFragment));
        return trips.keySet().size() < keysSizeBeforeRemoval;
    }


    List<String> getTripsNamesWithPriceBelow(int price){
       return trips.values().stream()
               .filter( t -> t.price < price)
               .map( t -> t.getDestination())
               .collect(Collectors.toList());
    }



    public boolean removeCustomer(String completeCustomerName){
        int sizeBeforeRemoval = customers.size();
        customers.removeIf( c -> c.getName().equals( completeCustomerName));
        return customers.size() < sizeBeforeRemoval;
    }

    List<Customer> getAllByAddressFragment(String addressFragment){
        ArrayList<Customer> result = new ArrayList<>();
        for( Customer customer : customers){
            if (customer.address.contains( addressFragment)){
                result.add( customer);
            }
        }
        return result;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public String getInfo(){
        String result = "";
        for( int i = 0; i < customers.size(); ++i){
            Customer customer = customers.get(i);
            if ( customer != null){
                result += customer.getInfo();
                result += "\n\n";
            }
        }
        return result;
    }

    public String getInfoStreamsVersion(){
        StringBuilder result = new StringBuilder("");
        customers.stream()
                .filter( c -> c != null)
                .forEach( c -> result.append( c.getInfo()).append("\n\n"));

        return result.toString();
    }


    void saveCustomersToFile(String filename) throws Exception{
        ArrayList<String> customersInfo = new ArrayList<>();
        for( Customer customer : customers){
            customersInfo.add( customer.getInfo());
        }
        Files.write(Paths.get( filename), customersInfo, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    String readCustomersInfosFromFile(String filename) throws Exception{
        String result = "";
        BufferedReader reader = new BufferedReader( new FileReader( filename));
        String lineFromFile = null;
        while ( (lineFromFile = reader.readLine()) != null){
            result += lineFromFile;
        }
        return result;
    }

    public String getHtmlInfo(){
        StringBuilder sb = new StringBuilder("");
        for( Customer customer : customers){
            sb.append( customer.getHtmlInfo());
        }
        String result = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                            <style type="text/css">
                            </style>
                            <meta charset="UTF-8">
                            <title>customer</title>
                            </head>
                            <body>
                            <p> %s </p>
                            </body>
                            </html>
                """;

        return String.format(result, sb);
    }

    public static void main(String[] args) throws Exception{

        TravelOffice travelOffice = new TravelOffice();

        Trip trip  = new Trip( LocalDate.of(2022,7,13), LocalDate.of(2022, 8, 15), "Seszele", 2000);
        Trip trip2 = new Trip( LocalDate.of(2023,7,1), LocalDate.of(2022, 7, 15), "Egipt", 3000);
        Trip trip3 = new Trip( LocalDate.of(2022,9,10), LocalDate.of(2022, 9, 17), "Ciechocinek", 3999);
        Trip trip4 = new Trip( LocalDate.of(2022,9,10), LocalDate.of(2022, 9, 17), "Stary Ciechocinek", 1999);
        Trip trip5 = new Trip( LocalDate.of(2022,9,10), LocalDate.of(2022, 9, 17), "Nowy Ciechocinek", 1999);

        travelOffice.addTrip( trip);
        travelOffice.addTrip( trip2);
        travelOffice.addTrip( trip3);
        travelOffice.addTrip( trip4);
        travelOffice.addTrip( trip5);


        ArrayList<Trip> tripsPricierThen2200 = travelOffice.getTripsByPrice(2200, false);

        System.out.printf("Number of trips: %d%n", travelOffice.trips.size());

        boolean removedSomeTrips = travelOffice.removeTrip("cinek");
        if (removedSomeTrips) {
            System.out.printf("Number of trips (after removal): %d%n", travelOffice.trips.keySet().size());
        }

        Customer customer = new Customer("John Smith");
        customer.setAddress("ul. Stawki 2, 02-222 Warszawa");
        customer.assignTrip( trip);
        customer.assignTrip( trip2);

        Customer customer2 = new Customer("Mary Smith");
        customer2.setAddress("ul. Chłodna 51, 02-222 Warszawa");
        customer2.assignTrip( trip5);
        customer2.assignTrip( trip4);
        customer2.assignTrip( trip3);
        customer2.assignTrip( trip);

        Customer customer3 = new Customer("Ben Smith");
        customer3.setAddress("ul. Marszałkowska 33, 01-010 Warszawa");
        customer3.assignTrip( trip4);
        customer3.assignTrip( trip3);
        customer3.assignTrip( trip2);
        customer3.assignTrip( trip);

        Customer customer4 = new Customer("Ben Ben");
        customer4.setAddress("ul. Marszałkowska 33, 01-010 wojkowice");
        customer4.assignTrip( trip5);
        customer4.assignTrip( trip4);
        customer4.assignTrip( trip3);
        customer4.assignTrip( trip2);
        customer4.assignTrip( trip);

        travelOffice.addCustomer( customer);
        travelOffice.addCustomer( customer2);
        travelOffice.addCustomer( customer3);
        travelOffice.addCustomer( customer4);


        PrintWriter htmlPlik = new PrintWriter(("HtmlPlik.html"));
        htmlPlik.println(travelOffice.getHtmlInfo());
        htmlPlik.close();

    }

}
