package eu.sibs.ordersibs.resouce;




import eu.sibs.ordersibs.exceptions.StockMovementsNotFoundException;
import eu.sibs.ordersibs.model.dto.StockMovementDTO;
import eu.sibs.ordersibs.service.impl.StockMovementsServiceImpl;

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


@Path("/v1/stockMovements")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Stateless
public class StockMovementsResource {

	@EJB
	private StockMovementsServiceImpl stockMovementsServiceImpl;

	private @Context UriInfo uriInfo;

	public StockMovementsResource() {
	}

	@GET
	public Response getAllStockMovements(){
		return Response.ok().entity(stockMovementsServiceImpl.findAll()).build();
	}

	@GET
	@Path("/{id}")
	public Response getStockMovementsById(@PathParam("id") Long id) throws StockMovementsNotFoundException {
		return Response.ok().entity(stockMovementsServiceImpl.findById(id)).build();
	}

	@POST
	public Response createStockMovements(@Valid StockMovementDTO stockMovementDto){
		return Response.created(uriInfo.getAbsolutePathBuilder().path(getUriForSelf()).build())
				.entity(stockMovementsServiceImpl.save(stockMovementDto))
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateStockMovements(@PathParam("id") Long id,StockMovementDTO stockMovementDtoDetails){
		return Response.ok().entity(stockMovementsServiceImpl.updateStockMovements(id, stockMovementDtoDetails)).build();

	}

	@DELETE
	@Path("/{id}")
	public Response deleteStockMovements(@PathParam("id") Long id){
			stockMovementsServiceImpl.deleteById(id);
		return Response.noContent().build();
	}

	/**
	 * Method that gets the URI of the car with the given ID.
	 * @return String that contains the URI of the given car.
	 */
	private String getUriForSelf() {
		String urlSelf = uriInfo.getBaseUriBuilder()
				.path(StockMovementsResource.class)
				.build()
				.toString();
		return urlSelf;
	}

}