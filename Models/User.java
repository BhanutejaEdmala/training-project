package busticketbooking.Models;

import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

public class User {
   public  String user;
  public   String uid;
public Bus bus;
public  LinkedHashSet<Integer> seatNumbers = new LinkedHashSet<>();
    public User(String uname, Bus bus, LinkedHashSet<Integer> seatNumbers) {
        this.user=uname;
        long randomNum = ThreadLocalRandom.current().nextLong(1000000L, 10000000L);
        this.uid=Long.toString(randomNum);
        this.bus = bus;
        this.seatNumbers = seatNumbers;
    }

    @Override
    public String toString() {
        return "" +
                "User Name='" + user + '\'' +
                ", User Id='" + uid + '\'' +
                ", Bus Details=>" + bus +
                ", Seats Booked=" + seatNumbers +
                '}';
    }

}
