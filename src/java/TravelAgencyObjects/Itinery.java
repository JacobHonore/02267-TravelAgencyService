/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TravelAgencyObjects;
import java.util.Random;
/**
 *
 * @author jacobhonore
 */
public class Itinery {
    private int iteneryNum;
    private String customerName;
    private int cardNumber;
    private int cardMonth;
    private int cardYear;
    public Itinery() {
        Random rand = new Random();
        this.iteneryNum = rand.nextInt((10000 - 1) + 1) + 1;
    }
    public void setCreditCardInfo(String customerName, int cardNumber, int cardMonth, int cardYear) {
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
    }
}
