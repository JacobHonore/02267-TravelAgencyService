package TravelAgencyObjects;
/**
 *
 * @author jacobhonore
 */
public class Booking {
    private String bookingNumber;
    private BookingStatus bookingStatus;
    public Booking(String bookingNumber) {
        this.bookingNumber = bookingNumber;
        this.bookingStatus = BookingStatus.UNCONFIRMED;
    }
    public String getBookingNumber() {
        return bookingNumber;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public enum BookingStatus {
        CONFIRMED, UNCONFIRMED, CANCELLED
    }
    @Override
    public String toString() {
        return "Booking number: "+bookingNumber+" Booking status: "+bookingStatus;
    }
}
