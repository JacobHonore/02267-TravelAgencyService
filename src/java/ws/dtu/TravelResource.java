package ws.dtu;

import TravelAgencyObjects.Itinery;
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
    /*@Path("addFlight")
    @GET
    public String addFlight(@Context HttpServletRequest req) { 
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        return itinery.addFlight("Hi", "1324", "154");
        session.setAttribute("itinery", itinery);
        return "New itinery created";
    }*/
    @Path("addCreditCardInfo")
    @GET
    public String setCreditCardInfo(@Context HttpServletRequest req, @QueryParam("name") String name,
     @QueryParam("number") int number,  @QueryParam("month") int month,  @QueryParam("year") int year) {
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