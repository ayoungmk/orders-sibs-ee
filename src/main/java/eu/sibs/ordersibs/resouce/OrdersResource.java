package eu.sibs.ordersibs.resouce;



import eu.sibs.ordersibs.exceptions.OrdersNotFoundException;
import eu.sibs.ordersibs.model.dto.OrderDTO;
import eu.sibs.ordersibs.service.impl.OrdersServiceImpl;
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

@Path("/v1/orders")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Stateless
public class OrdersResource {

	private @Context UriInfo uriInfo;
	@EJB
	private OrdersServiceImpl ordersServiceImpl;


	public OrdersResource() {
	}


	@GET
	public Response getAllOrders(){
		return Response.ok().entity(ordersServiceImpl.findAll()).build();
	}

	@GET
	@Path("/{id}")
	public Response getOrdersById(@PathParam("id")Long id) throws OrdersNotFoundException {
		return 	Response.ok().entity(ordersServiceImpl.findById(id)).build();
	}

	@POST
	public Response saveOrder(@Valid OrderDTO orderDto){
		return Response.created(uriInfo.getAbsolutePathBuilder().path(getUriForSelf()).build())
				.entity(ordersServiceImpl.createOrder(orderDto))
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateOrders(@PathParam("id")Long id, OrderDTO orderDtoDetails){
		return Response.ok().entity(ordersServiceImpl.updateOrders(id, orderDtoDetails)).build();

	}

	@DELETE
	@Path("/{id}")
	public Response deleteOrders(@PathParam("id")Long id){
		ordersServiceImpl.deleteById(id);
		return Response.noContent().build();
	}



	/**
	 * Method that gets the URI of the car with the given ID.
	 * @return String that contains the URI of the given car.
	 */
	private String getUriForSelf() {
		String urlSelf = uriInfo.getBaseUriBuilder()
				.path(OrdersResource.class)
				.build()
				.toString();
		return urlSelf;
	}
}