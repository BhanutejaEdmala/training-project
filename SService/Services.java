package busticketbooking.SService;

import busticketbooking.DAO.Dao;
import busticketbooking.Models.Bus;
import busticketbooking.Models.User;

import java.util.*;
import java.util.stream.Collectors;
public class Services implements Service {

    Dao dao = new Dao();
    Scanner s = new Scanner(System.in);

    public void addBus(Bus b) {
        dao.buses.add(b);
        //System.out.println("Bus Added Successfully");
    }
    public void removeBus(Bus b) {
        dao.buses.remove(b);
        System.out.println("Bus Removed Successfully");
    }
    void setSeats(int sd){
        System.out.println("Enter source");
        String so=s.next();
        System.out.println("Enter destination");
        String ds=s.next();
        System.out.println("Enter date");
        String da=s.next();
        System.out.println("Enter Time");
        String t=s.next();
        for(Bus b:dao.buses){
            if(b.from.equals(so)&&b.to.equals(ds)&&b.date.equals(da)&&b.time.equals(t))
                b.setSeats(sd);
        }
    }
    public void availableBuses(String date, String from, String to) {
        int c = 0;
        List<Bus> g = dao.buses.stream().filter(b -> b.date.equals(date) && b.from.equals(from) && b.to.equals(to) && b.seats > 0).collect(Collectors.toList());
        if (g.isEmpty())
            System.out.println("No Buses Available");
        else
            g.stream().forEach(b -> System.out.println(b));
    }
    public void availableSeats(String date, String from, String to, String time) {
        long count = dao.buses.stream()
                .filter(b -> b.date.equals(date) && b.from.equals(from) && b.to.equals(to) && b.time.equals(time))
                .peek(b -> System.out.println("Available Seats: " + b.seats))
                .count();

        if (count == 0)
            System.out.println("No Buses Available");
        else{
            System.out.print("Booked Seats=>");
            dao.userDetails.stream()
                    .filter(r -> r.bus.date.equals(date) && r.bus.to.equals(to) && r.bus.from.equals(from) && r.bus.time.equals(time))
                    .forEach(r -> System.out.print(r.seatNumbers));
            System.out.println();
        }
    }
    public void totalBuses() {
        System.out.println("Total Buses " + dao.buses.size());
        for (Bus b : dao.buses) {
            if (b.seats > 0)
                System.out.println("Bus Starting From: " + b.from + ", To: " + b.to + ", Date: " + b.date + ", Time: " + b.time);
        }
    }

    public void bookTicket() {
        final int g[] = {0};
        String name = "", date = "", from = "", to = "", time = "";
        System.out.println("Enter User Name");
        name = s.next();
        int k = 0;
        while (k == 0) {
            try {
                System.out.println("Enter Date");
                date = s.next();
                System.out.println("From");
                from = s.next();
                System.out.println("To");
                to = s.next();
                thrower(date, from, to);
                k++;
            } catch (Exception ed) {
                System.out.println("please enter Correctly");
            }
        }
        System.out.println("Enter time");
        time = s.next();
        Services service = new Services();
        System.out.println("Enter Seat Numbers you want to book, separating them with comma");
        String sn = s.next();
        String[] nm = sn.split(",");
        final int[] y = {0};
        // Using streams for processing buses
        String finalDate = date;
        String finalFrom = from;
        String finalTo = to;
        String finalTime = time;
        String[] finalNm1 = nm;
        //AtomicInteger y= new AtomicInteger();
        dao.buses.stream()
                .filter(bus -> bus.date.equals(finalDate) && bus.from.equals(finalFrom) && bus.to.equals(finalTo) && bus.time.equals(finalTime))
                .forEach(bus -> {
                    g[0]++;
                    if (bus.seats < finalNm1.length) {
                        System.out.println("Seats are Not Sufficient");
                        return;
                    }
                });
        if (g[0] == 0) {
            System.out.println("No Bus Available");
            return;
        }

        String[] finalNm = nm;
        String finalName = name;
        // Using streams for processing bookings
        String finalDate1 = date;
        String finalTo1 = to;
        String finalFrom1 = from;
        String finalTime1 = time;
        dao.userDetails.stream()
                .filter(userdetail -> userdetail.bus.date.equals(finalDate1) && userdetail.bus.to.equals(finalTo1) && userdetail.bus.from.equals(finalFrom1) && userdetail.bus.time.equals(finalTime1))
                .forEach(userdetail -> {
                    String bk = "";
                    String nbk = "";
                    for (int i = 0; i < finalNm.length; i++) {
                        String f = "" + finalNm[i];
                        if (userdetail.seatNumbers.contains(Integer.parseInt(f))) {
                            bk += f + " ";
                        } else {
                            nbk += f + " ";
                        }
                    }
                    if (bk.length() > 0) {
                        System.out.println(bk + "=>Already Booked");
                        System.out.println(nbk + "=>Available");
                        y[0]++;
                    }
                });

        if (y[0] == 1)
            return;
        // Using streams for booking
        String finalDate2 = date;
        String finalFrom2 = from;
        String finalTo2 = to;
        String finalTime2 = time;
        dao.buses.stream()
                .filter(bus -> bus.date.equals(finalDate2) && bus.from.equals(finalFrom2) && bus.to.equals(finalTo2) && bus.time.equals(finalTime2))
                .forEach(bus -> {
                    bus.seats -= finalNm.length;
                    LinkedHashSet<Integer> h = new LinkedHashSet<>();
                    for (int i = 0; i < finalNm.length; i++) {
                        String f = finalNm[i] + "";
                        h.add(Integer.parseInt(f));
                    }
                    User r = new User(finalName, bus, h);
                    Dao.userDetails.add(r);
                    System.out.println(finalName + " Your Booking Is Successful");
                    System.out.println("Your Reference Id: " + r.uid);
                    System.out.println("Bus Details are => " + "Bus No: " + bus.bno + " Starting From: " + bus.from + " To: " + bus.to + " Time: " + bus.time);
                    System.out.println("Happy Journey");
                });
    }
    public void specificUsers(String u) {
        long count = dao.userDetails.stream()
                .filter(r -> r.user.equals(u))
                .peek(System.out::println)
                .count();

        if (count == 0)
            System.out.println("No Bookings Found");
    }


    public void totalUsers() {
        List<User> a = dao.userDetails.stream().collect(Collectors.toList());
        if (a.isEmpty())
            System.out.println("No Bookings Found");
        else
            a.stream().forEach(System.out::println);
    }
    public void cancelTicket() {
        System.out.println("Enter User Name");
        String user = s.next();
        System.out.println("Enter Date");
        String date = s.next();
        System.out.println("From");
        String from = s.next();
        System.out.println("To");
        String to = s.next();
        System.out.println("Enter Time");
        String time = s.next();

        User d = dao.userDetails.stream()
                .filter(r -> r.bus.date.equals(date) && r.bus.to.equals(to) && r.bus.from.equals(from) && r.bus.time.equals(time))
                .findFirst()
                .orElse(null);

        synchronized (dao.userDetails) {
            if (d != null) {
                dao.userDetails.remove(d);
                System.out.println("Booking Cancelled Successfully");
            } else {
                System.out.println("No Bookings Found");
            }
        }
    }


    public void cancelTicket(String uname) {
        System.out.println("Enter Date");
        String date = s.next();
        System.out.println("From");
        String from = s.next();
        System.out.println("To");
        String to = s.next();
        System.out.println("Enter Time");
        String time = s.next();

        // Use streams to find and remove the booking
        dao.userDetails.stream()
                .filter(r -> r.bus.date.equals(date) && r.bus.to.equals(to) && r.bus.from.equals(from) && r.bus.time.equals(time) && r.user.equals(uname))
                .findFirst()
                .ifPresent(r -> {
                    dao.userDetails.remove(r);
                    System.out.println("Booking Cancelled Successfully");
                });

        if (dao.userDetails.stream().noneMatch(r -> r.bus.date.equals(date) && r.bus.to.equals(to) && r.bus.from.equals(from) && r.bus.time.equals(time) && r.user.equals(uname))) {
            System.out.println("No matching booking found for cancellation.");
        }
    }
    public int thrower(String date, String from, String to) throws Exception {
        int f = 0;
        String k = "";
        String[] j = date.split("/");
        for (int i = 0; i < date.length(); i++) {
            if (!(date.charAt(i) == '/'))
                k += date.charAt(i);
        }
        // System.out.println(k);
        for (int i = 0; i < k.length(); i++) {
            //String j =""+k.charAt(i);
            if (Character.isLetter(k.charAt(i))) {
                System.out.println(j);
                System.out.println("date");
                f++;
                throw new Exception();
            }
        }
        // System.out.println(Arrays.toString(j));
        for (int i = 0; i < j.length; i++) {
            if (Integer.parseInt(j[i]) > 31) {
                f++;
                throw new Exception();
            }
        }
        // System.out.println("before from");
        //System.out.println(from);

        for (int g = 0; g < from.length(); g++) {
            char t = from.charAt(g);
            //  boolean n =!(Character.isLetter(to.charAt(g)))||Character.isDigit(to.charAt(g));
            if (!(t >= 65 && t <= 90 || t >= 97 && t <= 122)) {
                f++;
                System.out.println("hi");
                throw new Exception("Invalid Date");
            }
        }
        for (int g = 0; g < to.length(); g++) {
            char t = to.charAt(g);
            //  boolean n =!(Character.isLetter(to.charAt(g)))||Character.isDigit(to.charAt(g));
            if (!(t >= 65 && t <= 90 || t >= 97 && t <= 122)) {
                f++;
                System.out.println("hi");
                throw new Exception();
            }
        }
        return f;
    }
    public void custom(){
        dao.buses.stream()
                .filter(b -> {
                    String[] a = b.date.split("/");
                    return Integer.parseInt(a[0]) > 10;
                })
                .forEach(System.out::println);
    }
    public void view() {
        Scanner s = new Scanner(System.in);
        Services service = new Services();
        dao.defaultAdd(service);
        service.custom();
        String date;
        String to;
        String from;
        String time;
        boolean innerCondition1 = true;
        boolean innerCondition2 = true;
        boolean outerCondition = true;
        while (outerCondition) {
            System.out.println("Enter 1 for Admin");
            System.out.println("Enter 2 for User");
            System.out.println("Enter 3 to exit");
            int aa = s.nextInt();
            if (aa == 1) {
                innerCondition2 = true;
                while (innerCondition2) {
                    System.out.println("Enter 1 to add a bus");
                    System.out.println("Enter 2 to remove a bus");
                    System.out.println("Enter 3 to view all users");
                    System.out.println("Enter 4 to exit");
                    System.out.println("Enter 5 to view all buses");
                    System.out.println("Enter 6 to change seats in a bus");
                    int bb = s.nextInt();
                    switch (bb) {
                        case 1: {
                            System.out.println("Enter Bus no");
                            String bno = s.next();
                            System.out.println("enter source ");
                            String bfrom = s.next();
                            System.out.println("enter destination");
                            String bto = s.next();
                            System.out.println("enter date");
                            String bdate = s.next();
                            System.out.println("enter time");
                            String btime = s.next();
                            System.out.println("enter no of seats");
                            int bs = s.nextInt();
                            service.addBus(new Bus(bno, bfrom, bto, bdate, btime, bs));
                            System.out.println("enter 1 for menu");
                            System.out.println("enter 2 to exit");
                            int na = s.nextInt();
                            if (na == 2)
                                innerCondition2 = false;
                            break;
                        }
                        case 2: {
                            System.out.println("Enter Bus no");
                            String bno = s.next();
                            System.out.println("enter source ");
                            String bfrom = s.next();
                            System.out.println("enter destination");
                            String bto = s.next();
                            System.out.println("enter date");
                            String bdate = s.next();
                            System.out.println("enter time");
                            String btime = s.next();
                            System.out.println("enter no of seats");
                            int bs = s.nextInt();
                            service.removeBus(new Bus(bno, bfrom, bto, bdate, btime, bs));
                            System.out.println("enter 1 for menu");
                            System.out.println("enter 2 to exit");
                            int na = s.nextInt();
                            if (na == 2)
                                innerCondition2 = false;
                            break;
                        }
                        case 3: {
                            service.totalUsers();
                            System.out.println("enter 1 for menu");
                            System.out.println("enter 2 to exit");
                            int na = s.nextInt();
                            if (na == 2)
                                innerCondition2 = false;
                            break;
                        }
                        case 4:
                            innerCondition2 = false;
                            break;
                        case 5: {
                            service.totalBuses();
                            System.out.println("enter 1 for menu");
                            System.out.println("enter 2 to exit");
                            int na = s.nextInt();
                            if (na == 2)
                                innerCondition2 = false;
                            break;
                        }
                        case 6:{
                            System.out.println("Enter No of seats");
                            int so =s.nextInt();
                            service.setSeats(so);
                            System.out.println("enter 1 for menu");
                            System.out.println("enter 2 to exit");
                            int na = s.nextInt();
                            if (na == 2)
                                innerCondition2 = false;
                            break;
                        }
                    }
                }
            } else if (aa == 2) {
                innerCondition1 = true;
                while (innerCondition1) {
                    System.out.println("Ticket Booking System");
                    System.out.println("Enter 1 to view selected buses");
                    System.out.println("Enter 2 to Book A Ticket");
                    System.out.println("Enter 3 to view seats in a bus");
                    System.out.println("Enter 4 to view total buses");
                    System.out.println("Enter 5 to view specific user bookings");
                    System.out.println("Enter 6 to exit");
                    System.out.println("Enter 7 to cancel bookings");
                    System.out.println("Enter 8 to view all bookings");
                    int l = s.nextInt();
                    int i = 0;
                    switch (l) {
                        case 1: {
                            boolean g = true;
                            while (g) {
                                try {
                                    System.out.println("Enter date");
                                    date = s.next();
                                    System.out.println("Enter Source");
                                    from = s.next();
                                    System.out.println("Enter Destination");
                                    to = s.next();
                                    System.out.println("before");
                                    try {
                                        if (service.thrower(date, from, to) == 0)
                                            g = false;
                                        service.availableBuses(date, from, to);
                                    } catch (Exception ed) {
                                        System.out.println("please enter correctly");
                                    }
                                } catch (Exception ed) {
                                    System.out.println(ed);
                                }
                            }
                            System.out.println("press any number for menu");
                            System.out.println("press 2 to book tickets");
                            System.out.println("press 3 to exit");
                            i = s.nextInt();
                            if (i == 2) {
                                service.bookTicket();
                            } else if (i == 3)
                                innerCondition1 = false;
                            break;
                        }
                        case 2:
                            service.bookTicket();
                            System.out.println();
                            //
                            System.out.println("press 1 for menu");
                            System.out.println("press 2 to exit");
                            System.out.println("press 4 to book more tickets");
                            System.out.println("press 5 to view all your bookings");
                            System.out.println("press 3 to cancel bookings");
                            i = s.nextInt();
                            if (i == 3)
                                service.cancelTicket();
                            if (i == 5) {
                                System.out.println("Please Enter Your UserName");
                                String name = s.next();
                                service.specificUsers(name);
                            }
                            if (i == 4) {
                                service.bookTicket();
                                while (true) {
                                    System.out.println("Do You Want To Book One More Ticket");
                                    System.out.println("Enter yes or no");
                                    String m = s.next();
                                    if (m.equals("yes"))
                                        service.bookTicket();
                                    else if (m.equals("no"))
                                        break;
                                    else
                                        System.out.println("Enter Correctly");
                                }
                            }
                            if (i == 2)
                                innerCondition1 = false;
                            break;
                        case 3:
                            int x = 0;
                            while (x == 0) {
                                System.out.println("Enter date");
                                date = s.next();
                                System.out.println("Enter Source");
                                from = s.next();
                                System.out.println("Enter Destination");
                                to = s.next();
                                System.out.println("Enter time");
                                time = s.next();
                                try {
                                    service.thrower(date, from, to);
                                    x++;
                                    service.availableSeats(date, from, to, time);
                                } catch (Exception ed) {
                                    System.out.println("Please Enter Correctly " + ed);
                                }
                            }
                            System.out.println("press 2 to book tickets");
                            System.out.println("press 3 to exit");
                            System.out.println("press any other number for menu");
                            i = s.nextInt();
                            if (i == 2) {
                                service.bookTicket();
                            } else if (i == 3)
                                innerCondition1 = false;
                            break;
                        case 4:
                            service.totalBuses();
                            System.out.println("press 1 for menu");
                            System.out.println("press 2 to exit");
                            i = s.nextInt();
                            if (i == 2)
                                innerCondition1 = false;
                            break;
                        case 5:
                            int t = 0;
                            String r = "";
                            System.out.println("Enter User Name");
                            r = s.next();
                            service.specificUsers(r);
                            System.out.println("press 1 for menu");
                            System.out.println("press 2 to exit");
                            System.out.println("press 3 to cancel");
                            i = s.nextInt();
                            if (i == 3)
                                service.cancelTicket(r);
                            if (i == 2)
                                innerCondition1 = false;
                            break;
                        case 6:
                            innerCondition1 = false;
                            break;
                        case 7:
                            service.cancelTicket();
                            System.out.println("press 1 for menu");
                            System.out.println("press 2 to exit");
                            int j = s.nextInt();
                            if (j == 2)
                                innerCondition1 = false;
                            break;
                        case 8:
                            service.totalUsers();
                            System.out.println("enter any key for menu");
                            System.out.println("enter 0 to exit");
                            String ak = s.next();
                            if (ak.equals("0"))
                                innerCondition1 = false;
                            break;

                    }
                }
            } else if (aa == 3)
                outerCondition = false;
        }
    }
}


