package busticketbooking.DAO;

import busticketbooking.Models.Bus;
import busticketbooking.Models.User;
import busticketbooking.SService.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Dao implements Repo {
    public static ArrayList<Bus> buses = new ArrayList<>();
    public static ArrayList<User> userDetails = new ArrayList<>();

    public   void defaultAdd(Services se) {
        Bus a = new Bus("2054", "hyd", "vizag", "2/8/24", "9:00AM", 60);
        Bus d = new Bus("2056", "hyd", "vizag", "2/8/24", "10:00AM", 60);
        Bus b = new Bus("2078", "medak", "uppal", "6/8/24", "10:00AM", 60);
        Bus c = new Bus("7078", "hyd", "vemulawada", "9/8/24", "12:00AM", 60);
        Bus e = new Bus("8888", "sdpt", "hyd", "7/8/24", "8:00AM", 60);
        Bus f = new Bus("3456", "hyd", "nizam", "4/5/34", "10:00AM", 90);
        Bus g = new Bus("3486", "hyd", "mulugu", "9/5/34", "1:00AM", 90);
        Bus h = new Bus("3488", "hyd", "warangal", "23/5/34", "7:00AM", 90);
        Bus i = new Bus("3213", "hyd", "manchiryal", "17/5/34", "2:00AM", 90);
        Bus j = new Bus("7777", "hyd", "nalgonda", "13/5/34", "1:00AM", 90);

        //b.setSeats(0);
        //Services service = new Services();
//        User user1 = new User("Name", d);

        Scanner s = new Scanner(System.in);
        se.addBus(a);
        se.addBus(b);
        se.addBus(c);
        se.addBus(d);
        se.addBus(e);
        se.addBus(f);
        se.addBus(g);
        se.addBus(h);
        se.addBus(i);
        se.addBus(j);

    }



}

