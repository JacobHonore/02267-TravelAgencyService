/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelAgencyObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ws.dtu.Exception_Exception;
/**
 *
 * @author jacobhonore
 */
public class Itinery {
    private int itineryNum;
    private String customerName;
    private String cardNumber;
    private int cardMonth;
    private int cardYear;
    private List<Booking> flightList = new ArrayList<Booking>();
    private List<Booking> hotelList = new ArrayList<Booking>();
    public Itinery() {
        Random rand = new Random();
        this.itineryNum = rand.nextInt((10000 - 1) + 1) + 1;
    }
    public String book(String customerName, String cardNumber, int cardMonth, int cardYear) {
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        String msg = "";
        Booking booking;
        for(int i=0;i<flightList.size();i++) {
            booking = flightList.get(i);
            try {
                if (bookFlight(booking.getBookingNumber())) {
                    booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
                    flightList.set(i, booking);
                    msg += booking.getBookingNumber()+" has been "+booking.getBookingStatus()+"<br>";
                }
                else {
                    msg += booking.getBookingNumber()+" could not be booked.<br>";
                }
            } catch (Exception_Exception ex) {
                msg += booking.getBookingNumber()+" could not be booked. Because of the following error: "+ex.getMessage()+"<br>";
            }
        }
        for(int i=0;i<hotelList.size();i++) {
            booking = hotelList.get(i);
            try {
                if (bookFlight(booking.getBookingNumber())) {
                    booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
                    hotelList.set(i, booking);
                    msg += booking.getBookingNumber()+" has been "+booking.getBookingStatus()+"<br>";
                }
                else {
                    msg += booking.getBookingNumber()+" could not be booked.<br>";
                }
            } catch (Exception_Exception ex) {
                msg += booking.getBookingNumber()+" could not be booked. Because of the following error: "+ex.getMessage()+"<br>";
            }
        }
        return msg;
    }
    public Booking addFlight(String flightNumber) {
        Booking booking = new Booking(flightNumber);
        flightList.add(booking);
        return booking;
    }
    public Booking addHotel(String hotelNumber) {
        Booking booking = new Booking(hotelNumber);
        hotelList.add(booking);
        return booking;
    }
    public boolean isCreditCardInfoSet() {
        return (customerName != null && cardNumber != null && cardMonth > 0 && cardYear > 0);
    }
    public static String getFlight(String startAirport, String destAirport, String liftoffDate) {
        ws.dtu.AirlineResourceService service = new ws.dtu.AirlineResourceService();
        ws.dtu.AirlineResource port = service.getAirlineResourcePort();
        return port.getFlight(startAirport, destAirport, liftoffDate);
    }
    public static String getHotels(String city, String arrivalDate, String departureDate) {
        ws.dtu.HotelResource_Service service = new ws.dtu.HotelResource_Service();
        ws.dtu.HotelResource port = service.getHotelResourcePort();
        return port.getHotels(city, arrivalDate, departureDate);
    }
    public String cancel() {
        String returnMsg = "";
        for(int i=0;i<flightList.size();i++) {
            String flight = flightList.get(i);
            try {
                if (cancelFlight(flight)) {
                    returnMsg += flight+" cancelled<br>";
                    flightList.remove(flight);
                    i--;
                }
                else {
                    returnMsg += flight+" could not be cancelled<br>";
                }
            } catch (Exception_Exception ex) {
                returnMsg += ex.getMessage()+" ";
            }
        }
        for(int i=0;i<hotelList.size();i++) {
            String hotel = hotelList.get(i);
            try {
                if (cancelHotel(hotel)) {
                    returnMsg += hotel+" cancelled<br>";
                    hotelList.remove(hotel);
                    i--;
                }
                else {
                    returnMsg += hotel+" could not be cancelled<br>";
                }
            } catch (Exception_Exception ex) {
                returnMsg += ex.getMessage()+" ";
            }
        }
        return returnMsg;
    }
    public String get() {
        String returnMsg = "The following flights has been booked:<br>";
        for (Booking flight : flightList) {
            returnMsg += flight+"<br>";
        }
        returnMsg += "<br>The following hotels has been booked:<br>";
        for (Booking hotel : hotelList) {
            returnMsg += hotel+"<br>";
        }
        return returnMsg;
    }
    private dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard() {
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = new dk.dtu.imm.fastmoney.types.CreditCardInfoType();
        creditCard.setName(this.customerName);
        creditCard.setNumber(this.cardNumber);
        dk.dtu.imm.fastmoney.types.ExpirationDateType expirationDate = new dk.dtu.imm.fastmoney.types.ExpirationDateType();
        expirationDate.setMonth(this.cardMonth);
        expirationDate.setYear(this.cardYear);
        creditCard.setExpirationDate(expirationDate);
        return creditCard;
    }
    private boolean cancelFlight(String bookingNumber) throws Exception_Exception {
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = creditCard();
        ws.dtu.AirlineResourceService service = new ws.dtu.AirlineResourceService();
        ws.dtu.AirlineResource port = service.getAirlineResourcePort();
        return port.cancelFlight(bookingNumber, creditCard);
    }
    private boolean cancelHotel(String hotelNumber) throws Exception_Exception {
        ws.dtu.HotelResource_Service service = new ws.dtu.HotelResource_Service();
        ws.dtu.HotelResource port = service.getHotelResourcePort();
        return port.cancelHotel(hotelNumber);
    }
    private boolean bookFlight(String bookingNumber) throws Exception_Exception {
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = creditCard();
        ws.dtu.AirlineResourceService service = new ws.dtu.AirlineResourceService();
        ws.dtu.AirlineResource port = service.getAirlineResourcePort();
        return port.bookFlight(bookingNumber, creditCard);
    }
    private boolean bookHotel(String bookingNumber) throws Exception_Exception {
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = creditCard();
        ws.dtu.HotelResource_Service service = new ws.dtu.HotelResource_Service();
        ws.dtu.HotelResource port = service.getHotelResourcePort();
        return port.bookHotel(bookingNumber, creditCard);
    }
}
