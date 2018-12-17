package com.ricardo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ricardo.models.Pedido;
import com.ricardo.models.StatusMessage;
import com.ricardo.persistence.PedidoManager;

@Path("/pedidos")
public class PedidosService extends JSONService {

	private static final List<Pedido> pedidos = new ArrayList<Pedido>();

	static {
		pedidos.add(new Pedido(1, "Producto 1", 23));
		pedidos.add(new Pedido(2, "Producto 2", 24));
		pedidos.add(new Pedido(3, "Producto 3", 25));
		pedidos.add(new Pedido(4, "Producto 4", 26));
		pedidos.add(new Pedido(5, "Producto 5", 27));
	}

	// Aquí el recurso pedidos es público (no se necesita token para acceder)
//	@GET
//	@Produces("application/json")
//	public List<Pedido> getPedidos() {
//		return this.pedidos;
//	}
	
	// curl http://localhost:8080/PedidosREST/api/pedidos -v

	// Aquí el recurso pedidos es privado (no se necesita autentificación para
	// acceder, por eso se pide el token)
	@GET
	@Produces("application/json")
	public Response getPedidos() {

		Response mResponse = null;

			PedidoManager pm = PedidoManager.getInstance();
			List<Pedido> pedidosADevolver = pm.getPedidos();
			
			mResponse = Response.status(200).entity(pedidosADevolver).build();
		
		return mResponse;

	}
	
	//curl -H"token: eyJraWQiOiIxIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJuZXRtaW5kLmNvbSIsImV4cCI6MTU0NDc4MjIyNywianRpIjoibUxEUktpQ0RfNFhMaUlEYndTLV9XdyIsImlhdCI6MTU0NDc4MTYyNywibmJmIjoxNTQ0NzgxNTA3LCJzdWIiOiJqdWFuQGdtYWlsLmNvbSIsInJvbGVzIjpbImNsaWVudCJdfQ.ddJqm8gKx7Q1LrMJsZd1e3dsFQ7LPj3Hflhj5Up9eGLtEx1S6RI4QxrpGdKpFrZL7L1bcELhh12fuHjdeTtia9Zb12-ZEMf1gV8eh_qEEbG12jnUHSq9E0g0p-0q7icPl3j-qtRHK3y6b9kmWLN-pS_07X_h6gQ42z-UYR6kTrhROGNV9Q331hZMBodJG1sBsHg4BXrnliZqxrG-2VAErt8P0RG_SHo2Cnnv67UBNvgPecBHAHIvj67T-a2CXip2NK9Rx57Jr5g8o2fMyS0plEkGNW_hzAJDitSUytWUKMRnoqPymtbQnlJHW1GMK939s4vztsnj5LOxTg8MwYRpsA" http://localhost:8080/PedidosREST/api/pedidos -v
	
//	@Path("/{pid}")
//	@GET
//	@Produces("application/json")
//	public Response getPedido(@PathParam("pid") int pid, @HeaderParam("token") String token) {
//		Response resp = null;
//
//		if (token != null) {
//			Pedido pedidoRet = null;
//
//			for (Pedido pedido : pedidos) {
//				if (pedido.getPid() == pid) {
//					pedidoRet = pedido;
//					break;
//				}
//			}
//
//			if (pedidoRet == null) {
//				resp = Response.status(404).entity(new StatusMessage(404, "El pedido no existe")).build();
//			} else {
//				resp = Response.status(200).entity(pedidoRet).build();
//			}
//			
//		} else {
//			resp = Response.status(403).entity(new StatusMessage(403, "Necesitas permisos")).build();
//		}
//
//		return resp;
//	}

	@Path("/{pid}")
	@GET
	@Produces("application/json")
	public Response getPedido(@PathParam("pid") int pid, @HeaderParam("token") String token) {
		String userEmail = this.getUserEmailFromToken(token);
		Response mResponse = null;

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage(Status.FORBIDDEN.getStatusCode(),
					"Access Denied for this functionality !!!");
			mResponse = Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();

		} else {
			Pedido pedidoRest = null;

			for (Pedido pedido : pedidos) {
				if (pedido.getPid() == pid) {
					pedidoRest = pedido;
					break;
				}
			}

			if (pedidoRest == null) {
				mResponse = Response.status(404).entity(new StatusMessage(404, "El pedido no existe")).build();
			} else {
				mResponse = Response.status(200).entity(pedidoRest).build();
			}
		}

		return mResponse;
	}
	
	//curl -H"token: eyJraWQiOiIxIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJuZXRtaW5kLmNvbSIsImV4cCI6MTU0NDc4MjM2MCwianRpIjoibnh0MW1SNmdMcUZEQXVIYWJyQXZjUSIsImlhdCI6MTU0NDc4MTc2MCwibmJmIjoxNTQ0NzgxNjQwLCJzdWIiOiJqdWFuQGdtYWlsLmNvbSIsInJvbGVzIjpbImNsaWVudCJdfQ.I-nAy3CHOsUf9wkBFxB7nCjjF33F0mR2nWwaSc_7LSibM8sUql6Hysn4EFB6cKjkDxzCwv6_gDkDyVwa8Jt71o3oRAfsq8n_LVSpQAgpsm-U5vOop3DppT3CNPhUSFZ2SJPKnNddPqPidAP6Xbi7QMb-_w2ywp6siM0z0hcbap_0Q1MxfTVjt-JaYPiQ2g7X9B4rRqycVzuNdjEpyrIKirYgnDGHHFn_D5w5A4ik8heQNZDeLfVTuMfZU9oJ0ycriNw5zeXpIGq_hZ2-V8LgGMk0gMbxt26AWI_WnMnBsH7UdnpcZao27bERgcELzjPmG75vKBSy2wKZFkOkZ7wZqg" http://localhost:8080/PedidosREST/api/pedidos/2 -v
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedido(Pedido nuevoPedido, @HeaderParam("token") String token) {

		Response resp = null;

		if (token != null) {

			if (nuevoPedido.validate()) {
				nuevoPedido.setPid(pedidos.size() + 1);
				pedidos.add(nuevoPedido);

				resp = Response.status(200).entity(new StatusMessage(200, "Lo estás haciendo ok")).build();
			} else {

			}

		} else {
			resp = Response.status(403).entity(new StatusMessage(403, "Necesitas permisos")).build();
		}

		return resp;
	}

//	curl -X POST -d "{\"pid\":0,\"descripcion\":\"Producto 6\",\"monto\":27.0}" -H "Content-Type:application/json" http://localhost:8080/PedidosREST/api/pedidos -v

	@Path("/{pid}")
	@DELETE
	@Produces("application/json")
	public boolean deletePedido(@PathParam("pid") int pid) {
		boolean OK = false;

		for (Pedido pedido : pedidos) {
			if (pedido.getPid() == pid) {
				pedidos.remove(pedido);
				OK = true;
				break;
			}
		}
		return OK;
	}

	// curl -X DELETE -H "Content-Type:application/json"
	// http://localhost:8080/PedidosREST/api/pedidos/2 -v

	@Path("/{pid}")
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	public boolean actualizarPedido(@PathParam("pid") int pid, Pedido unPedido) {
		boolean OK = false;

		for (Pedido pedido : pedidos) {
			if (pedido.getPid() == pid) {
				pedidos.remove(pedido);
				pedidos.add(unPedido);
				OK = true;
				break;
			}
		}
		return OK;
	}

	// curl -X PUT -d "{\"pid\":2,\"descripcion\":\"Cambio
	// Descripcion\",\"monto\":35.0}" -H "Content-Type: application/json"
	// http://localhost:8080/PedidosREST/api/pedidos/2 -v

}
