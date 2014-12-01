package ws.dtu;

import TravelAgencyObjects.Booking;
import TravelAgencyObjects.Itinery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 *
 * @author jacobhonore
 */
@Path("travel")
public class TravelResource {
    @Path("createItinery")
    @PUT
    public String createItinery(@Context HttpServletRequest req) { 
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = new Itinery();
        session.setAttribute("itinery", itinery);
        return "New itinery created";
    }
    
    @Path("cancelItinery")
    @DELETE
    public String cancelItinery(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        String result = itinery.cancel();
        session.setAttribute("itinery", itinery);
        return result;
    }
    
    @Path("getItinery")
    @GET
    public String getItinery(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        return itinery.get();
    }
    
    @Path("addFlight")
    @POST
    public String addFlight(@Context HttpServletRequest req, @QueryParam("flightnumber") String flightNumber) {
        if (flightNumber == null || "".equals(flightNumber) || flightNumber.isEmpty())
            return "Please set the flightnumber parameter.";
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        Booking result = itinery.addFlight(flightNumber);
        session.setAttribute("itinery", itinery);
        return "Flight with booking number "+result.getBookingNumber()+" has been booked and added to list of flights with status "+result.getBookingStatus()+".";
    }
    
    @Path("addHotel")
    @POST
    public String addHotel(@Context HttpServletRequest req, @QueryParam("hotelnumber") String hotelNumber) {
        if (hotelNumber == null || "".equals(hotelNumber) || hotelNumber.isEmpty())
            return "Please set the hotelnumber parameter.";
        HttpSession session = req.getSession(true);
        Itinery itinery;
        itinery = (Itinery) session.getAttribute("itinery");
        if (itinery == null) 
            return "No itinery. Please create an itinery.";
        Booking result = itinery.addHotel(hotelNumber);
        session.setAttribute("itinery", itinery);
        return "Hotel with booking number "+result.getBookingNumber()+" has been booked and added to list of hotels with status "+result.getBookingStatus()+".";

    }
    
    @Path("getFlights")
    @Produces("application/json")
    @GET
    public String getFlights(@Context HttpServletRequest req, @QueryParam("startairport") String startAirport,
    @QueryParam("destairport") String destAirport, @QueryParam("liftoffdate") String liftoffDate) {
        if (startAirport == null && destAirport == null && liftoffDate == null)
            return "All parameters havent been set.";
        return Itinery.getFlight(startAirport, destAirport, liftoffDate);
    }
    
    @Path("getHotels")
    @Produces("application/json")
    @GET
    public String getHotels(@Context HttpServletRequest req, @QueryParam("city") String city,
    @QueryParam("arrivaldate") String arrivalDate, @QueryParam("departuredate") String departureDate) {
        if (city == null || arrivalDate == null || departureDate == null)
            return "All parameters havent been set.";
        return Itinery.getHotels(city, arrivalDate, departureDate);
    }
    
    @Path("setCreditCardInfo")
    @POST
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