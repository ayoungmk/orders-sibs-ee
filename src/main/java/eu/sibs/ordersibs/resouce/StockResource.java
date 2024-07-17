package eu.sibs.ordersibs.resouce;


import eu.sibs.ordersibs.exceptions.StockNotFoundException;
import eu.sibs.ordersibs.model.dto.StockDTO;
import eu.sibs.ordersibs.service.impl.StockServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/v1/stock")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Stateless
public class StockResource {


    @EJB
    private StockServiceImpl stockServiceImpl;

    private @Context UriInfo uriInfo;


	public StockResource() {
	}

    @GET
	public Response getAllStock(){
        return Response.ok().entity(stockServiceImpl.findAll()).build();
    }

    @GET
    @Path("/{id}")
	public Response getStockById(@PathParam("id") Long id) throws StockNotFoundException {
        return Response.ok().entity(stockServiceImpl.findById(id)).build();
	}

    @POST
	public Response createStock(@Valid StockDTO stockDto){
        return Response.created(uriInfo.getAbsolutePathBuilder().path(getUriForSelf()).build())
                .entity(stockServiceImpl.save(stockDto))
                .build();
	}

    @PUT
    @Path("/{id}")
	public Response updateStock(@PathParam("id") Long id, StockDTO stockDtoDetails){
        return Response.ok().entity(stockServiceImpl.updateStock(id, stockDtoDetails)).build();
    }

    @DELETE
    @Path("/{id}")
	public Response deleteStock(@PathParam("id") Long id){
			stockServiceImpl.deleteById(id);
        return Response.noContent().build();
	}

    /**
     * Method that gets the URI of the car with the given ID.
     * @return String that contains the URI of the given car.
     */
    private String getUriForSelf() {
        String urlSelf = uriInfo.getBaseUriBuilder()
                .path(StockResource.class)
                .build()
                .toString();
        return urlSelf;
    }
}