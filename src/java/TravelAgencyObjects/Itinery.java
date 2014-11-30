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
    private List<String> flightList = new ArrayList<String>();
    private List<String> hotelList = new ArrayList<String>();
    public Itinery() {
        Random rand = new Random();
        this.itineryNum = rand.nextInt((10000 - 1) + 1) + 1;
    }
    public void setCreditCardInfo(String customerName, String cardNumber, int cardMonth, int cardYear) {
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
    }
    public boolean addFlight(String flightNumber) throws Exception_Exception {
        ws.dtu.AirlineResourceService service = new ws.dtu.AirlineResourceService();
        ws.dtu.AirlineResource port = service.getAirlineResourcePort();
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = creditCard();
        boolean result = port.bookFlight(flightNumber, creditCard);
        if (result == true) {
            flightList.add(flightNumber);
        }
        return result;
    }
    public boolean isCreditCardInfoSet() {
        return (customerName != null && cardNumber != null && cardMonth > 0 && cardYear > 0);
    }
    public String getFlight(String startAirport, String destAirport, String liftoffDate) {
        ws.dtu.AirlineResourceService service = new ws.dtu.AirlineResourceService();
        ws.dtu.AirlineResource port = service.getAirlineResourcePort();
        return port.getFlight(startAirport, destAirport, liftoffDate);
    }
    public String cancel() {
        String returnMsg = "";
        for (String flight : flightList) {
            try {
                if (cancelFlight(flight)) {
                    returnMsg += flight+" cancelled ";
                    flightList.remove(flight);
                }
                else {
                    returnMsg += flight+" could not be cancelled ";
                }
            } catch (Exception_Exception ex) {
                returnMsg += ex.getMessage()+" ";
            }
        }
        for (String hotel : hotelList) {
            try {
                if (cancelHotel(hotel)) {
                    returnMsg += hotel+" cancelled ";
                    hotelList.remove(hotel);
                }
                else {
                    returnMsg += hotel+" could not be cancelled ";
                }
            } catch (Exception_Exception ex) {
                returnMsg += ex.getMessage()+" ";
            }
        }
        return returnMsg;
    }
    //TODO: Add listing of hotels booked
    public String get() {
        String returnMsg = "The following flights has been booked: ";
        for (String flight : flightList) {
            returnMsg += flight+" ";
        }
        returnMsg += "The following hotels has been booked: ";
        for (String hotel : hotelList) {
            returnMsg += hotel+" ";
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
    private boolean cancelHotel(java.lang.String hotelNumber) throws Exception_Exception {
        ws.dtu.HotelResource_Service service = new ws.dtu.HotelResource_Service();
        ws.dtu.HotelResource port = service.getHotelResourcePort();
        return port.cancelHotel(hotelNumber);
    }
}
