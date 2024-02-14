package busticketbooking.SService;

public interface Service {
    void availableSeats(String date,String from,String to,String time);
    void totalBuses();
    void bookTicket();
    void specificUsers(String u);
    public void availableBuses(String date, String from, String to);
    void cancelTicket();
    int  thrower(String date,String from,String to) throws Exception;
}
