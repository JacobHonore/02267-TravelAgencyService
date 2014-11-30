package ws.dtu;

import TravelAgencyObjects.Itinery;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 *
 * @author jacobhonore
 */
@Path("travel")
public class TravelResource {
    @Path("createItinery")
    @GET
    public String createItinery(@Context HttpServletRequest req) { 
        HttpSession session = req.getSession(true);
        Itinery itinery;
        //itinery = (Itinery) session.getAttribute("itinery");
        //if (itinery == null) 
            itinery = new Itinery();
        session.setAttribute("itinery", itinery);
        return "New itinery created";
    }
    @Path("addFlight")
    @GET
    public String addFlight(@Context HttpServletRequest req, @QueryParam("flightnumber") String flightNumber) {
        if (flightNumber == null || "".equals(flightNumber) || flightNumber.isEmpty())
            return "Please set the flightnumber parameter.";
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        if (!itinery.isCreditCardInfoSet())
            return "Credit card info havent been set, please call setCreditCardInfo.";
        try {
            itinery.addFlight(flightNumber);
        } catch (Exception_Exception ex) {
            return ex.getMessage();
        }
        session.setAttribute("itinery", itinery);
        return "Flight has been booked and added to list of flights.";
    }
    @Path("setCreditCardInfo")
    @GET
    public String setCreditCardInfo(@Context HttpServletRequest req, @QueryParam("name") String name,
     @QueryParam("number") String number,  @QueryParam("month") int month,  @QueryParam("year") int year) {
        if (!(name != null && number != null && month > 0 && year > 0))
            return "All parameters havent been set.";
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        itinery.setCreditCardInfo(name, number, month, year);
        session.setAttribute("itinery", itinery);
        return "Creditcard information has been stored.";
    }
}