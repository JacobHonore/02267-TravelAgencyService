package ws.dtu;

import TravelAgencyObjects.Itinery;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jacobhonore
 */
@Path("travel")
public class TravelResource {
    List<Itinery> itineries = new ArrayList<Itinery>();
    @Path("createItinery")
    @GET
    public int createItinery() { 
        int itineryNum = itineries.size()+1;
        itineries.add(new Itinery(itineryNum));
        return itineryNum;
    }  
    
}