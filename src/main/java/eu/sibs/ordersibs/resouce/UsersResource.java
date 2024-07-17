package eu.sibs.ordersibs.resouce;


import eu.sibs.ordersibs.exceptions.UsersNotFoundException;
import eu.sibs.ordersibs.model.dto.UserDTO;
import eu.sibs.ordersibs.service.impl.UsersServiceImpl;

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


@Path("/orderSibs/v1/users")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Stateless
public class UsersResource {
	@EJB
	private UsersServiceImpl usersServiceImpl;

	private @Context UriInfo uriInfo;


	public UsersResource() {
	}

	@GET
	public Response getAllUsers(){
		return Response.ok().entity(usersServiceImpl.findAll()).build();
	}

	@GET
	@Path("/{id}")
	public Response getUsersById(@PathParam("id") Long id) throws UsersNotFoundException {
		return Response.ok().entity(usersServiceImpl.findById(id)).build();
	}

	@POST
	public Response createUsers(@Valid UserDTO userDto){
		return Response.created(uriInfo.getAbsolutePathBuilder().path(getUriForSelf()).build())
				.entity(usersServiceImpl.save(userDto))
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response updateUsers(@PathParam("id") Long id, UserDTO userDtoDetails){
		return Response.ok().entity(usersServiceImpl.updateUsers(id, userDtoDetails)).build();

	}

	@DELETE
	@Path("/{id}")
	public Response deleteUsers(@PathParam("id") Long id){
		usersServiceImpl.deleteById(id);
		return Response.noContent().build();
	}

	/**
	 * Method that gets the URI of the car with the given ID.
	 * @return String that contains the URI of the given car.
	 */
	private String getUriForSelf() {
		String urlSelf = uriInfo.getBaseUriBuilder()
				.path(UsersResource.class)
				.build()
				.toString();
		return urlSelf;
	}

}
