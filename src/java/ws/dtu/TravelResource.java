package ws.dtu;

import TravelAgencyObjects.Booking;
import TravelAgencyObjects.Itinerary;
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
    @Path("createItinerary")
    @POST
    public String createItinerary(@Context HttpServletRequest req) { 
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = new Itinerary();
        session.setAttribute("itinerary", itinerary);
        return "New itinerary created";
    }
    
    @Path("cancelItinerary")
    @DELETE
    public String cancelItinerary(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = (Itinerary) session.getAttribute("itinerary");
        if (itinerary == null) 
            return "No itinerary. Please create an itinerary.";
        String result = itinerary.cancel();
        session.setAttribute("itinerary", itinerary);
        return result;
    }
    
    @Path("getItinerary")
    @GET
    public String getItinerary(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = (Itinerary) session.getAttribute("itinerary");
        if (itinerary == null) 
            return "No itinerary. Please create an itinerary.";
        return itinerary.get();
    }
    
    @Path("addFlight")
    @PUT
    public String addFlight(@Context HttpServletRequest req, @QueryParam("flightnumber") String flightNumber) {
        if (flightNumber == null || "".equals(flightNumber) || flightNumber.isEmpty())
            return "Please set the flightnumber parameter.";
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = (Itinerary) session.getAttribute("itinerary");
        if (itinerary == null) 
            return "No itinerary. Please create an itinerary.";
        Booking result = itinerary.addFlight(flightNumber);
        session.setAttribute("itinerary", itinerary);
        return "Flight with booking number "+result.getBookingNumber()+" has been booked and added to list of flights with status "+result.getBookingStatus()+".";
    }
    
    @Path("addHotel")
    @PUT
    public String addHotel(@Context HttpServletRequest req, @QueryParam("hotelnumber") String hotelNumber) {
        if (hotelNumber == null || "".equals(hotelNumber) || hotelNumber.isEmpty())
            return "Please set the hotelnumber parameter.";
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = (Itinerary) session.getAttribute("itinerary");
        if (itinerary == null) 
            return "No itinerary. Please create an itinerary.";
        Booking result = itinerary.addHotel(hotelNumber);
        session.setAttribute("itinerary", itinerary);
        return "Hotel with booking number "+result.getBookingNumber()+" has been booked and added to list of hotels with status "+result.getBookingStatus()+".";
    }
    
    @Path("getFlights")
    @Produces("application/json")
    @GET
    public String getFlights(@Context HttpServletRequest req, @QueryParam("startairport") String startAirport,
    @QueryParam("destairport") String destAirport, @QueryParam("liftoffdate") String liftoffDate) {
        if (startAirport == null && destAirport == null && liftoffDate == null)
            return "All parameters havent been set.";
        return Itinerary.getFlight(startAirport, destAirport, liftoffDate);
    }
    
    @Path("getHotels")
    @Produces("application/json")
    @GET
    public String getHotels(@Context HttpServletRequest req, @QueryParam("city") String city,
    @QueryParam("arrivaldate") String arrivalDate, @QueryParam("departuredate") String departureDate) throws Exception_Exception { //TODO: Handle exception
        if (city == null || arrivalDate == null || departureDate == null)
            return "All parameters havent been set.";
        return Itinerary.getHotels(city, arrivalDate, departureDate);
    }
    
    @Path("book")
    @PUT
    public String book(@Context HttpServletRequest req, @QueryParam("name") String name,
     @QueryParam("number") String number,  @QueryParam("month") int month,  @QueryParam("year") int year) {
        if (!(name != null && number != null && month > 0 && year > 0))
            return "All parameters havent been set.";
        HttpSession session = req.getSession(true);
        Itinerary itinerary;
        itinerary = (Itinerary) session.getAttribute("itinerary");
        if (itinerary == null) 
            return "No itinerary. Please create an itinerary.";
        String result = itinerary.book(name, number, month, year);
        session.setAttribute("itinerary", itinerary);
        return result;
    }
}