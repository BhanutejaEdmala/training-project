package busticketbooking.Models;

import java.util.Objects;

public class Bus {
public String bno;
public String from;
public String to;
public String date;
public String time;
public int seats;
    public Bus(String bno,String from,String to,String date,String time,int seats) {
        this.bno = bno;
        this.from=from;
        this.to=to;
        this.date=date;
        this.time=time;
        this.seats=seats;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "" +
                "Bus no=" + bno +""+
                ", from='" + from + '\'' +
                ", to=" + to + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return seats == bus.seats && Objects.equals(bno, bus.bno) && Objects.equals(from, bus.from) && Objects.equals(to, bus.to) && Objects.equals(date, bus.date) && Objects.equals(time, bus.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno, from, to, date, time, seats);
    }

}
