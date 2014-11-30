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
        dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard = new dk.dtu.imm.fastmoney.types.CreditCardInfoType();
        creditCard.setName(this.customerName);
        creditCard.setNumber(this.cardNumber);
        dk.dtu.imm.fastmoney.types.ExpirationDateType expirationDate = new dk.dtu.imm.fastmoney.types.ExpirationDateType();
        expirationDate.setMonth(this.cardMonth);
        expirationDate.setYear(this.cardYear);
        creditCard.setExpirationDate(expirationDate);
        boolean result = port.bookFlight(flightNumber, creditCard);
        if (result == true) {
            flightList.add(flightNumber);
        }
        return result;
    }
    public boolean isCreditCardInfoSet() {
        return (customerName != null && cardNumber != null && cardMonth > 0 && cardYear > 0);
    }
}
