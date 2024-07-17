package eu.sibs.ordersibs.resouce;


import eu.sibs.ordersibs.exceptions.ItensNotFoundException;
import eu.sibs.ordersibs.model.dto.ItemDTO;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import eu.sibs.ordersibs.service.impl.ItemServiceImpl;
import org.apache.log4j.Logger;


@Path("/v1/itens")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Stateless
public class ItemResource {

	private final static Logger LOGGER = Logger.getLogger(ItemResource.class);

	private @Context UriInfo uriInfo;
	@EJB
	private ItemServiceImpl itemServiceImpl;


	@GET
	public Response getAllItens(){
		return Response.ok().entity(itemServiceImpl.findAll()).build();
	}

	@GET
	@Path("/{id}")
	public Response getItensById(@PathParam("id")Long id) throws ItensNotFoundException {
		return Response.ok().entity(itemServiceImpl.findById(id)).build();
	}

	@POST
	public Response createItens(@Valid ItemDTO itemDto){
		return Response.created(uriInfo.getAbsolutePathBuilder().path(getUriForSelf()).build())
				.entity(itemServiceImpl.save(itemDto))
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateItens(@PathParam("id")Long id,ItemDTO itemDtoDetails){
		return Response.ok().entity(itemServiceImpl.updateItens(id, itemDtoDetails)).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteItens(@PathParam("id") Long id){
		itemServiceImpl.deleteById(id);
		return Response.noContent().build();
	}

	private String getUriForSelf() {
		return uriInfo.getBaseUriBuilder()
				.path(ItemResource.class)
				.build()
				.toString();
	}

}