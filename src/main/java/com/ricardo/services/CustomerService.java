package com.ricardo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ricardo.models.Customer;
import com.ricardo.models.Pedido;

@Path ("/customer")
public class CustomerService {

	private static final List<Customer> clientes = new ArrayList<Customer>();
	
	static {
		clientes.add(new Customer(1, "Pepe", "012345678P", "p@p.es"));
		clientes.add(new Customer(2, "María", "012345678M", "m@m.es"));
		clientes.add(new Customer(3, "Lucía", "012345678L", "l@l.es"));
		clientes.add(new Customer(4, "Ramón", "012345678R", "r@r.es"));
		clientes.add(new Customer(5, "Sofía", "012345678S", "s@s.es"));
	}
	
	@GET
	@Produces("application/json")
	public List<Customer> getCustomers() {
		return this.clientes;
	}
	
	//curl http://localhost:8080/PedidosREST/api/customer -v
	
	
	@Path("/{cid}")
	@GET
	@Produces("application/json")
	public Customer getCustomers(@PathParam("cid") int cid) {
		Customer customerRest = null;
		
		for (Customer cliente:clientes) {
			if (cliente.getCid()==cid) {
				customerRest = cliente;
				break;
			}
		}
		return customerRest;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addCustomer (Customer nuevoCustomer) {
		nuevoCustomer.setCid(clientes.size() + 1);
		clientes.add(nuevoCustomer);
		return true;
	}
	
	//curl -X POST -d"{\"cid\":0,\"nombre\":\"Ramona\",\"dni\":\"012345678R\",\"email\":\"r@r.es\"}" -H"Content-Type: application/json" http://localhost:8080/PedidosREST/api/customer -v
	
	@Path("/{cid}")
	@DELETE
	@Produces("application/json")
	public boolean deletePedido(@PathParam("cid") int cid) {
		boolean OK = false;
		
		for (Customer cliente:clientes) {
			if (cliente.getCid() == cid) {
				clientes.remove(cliente);
				OK = true;
				break;
			}
		}
		return OK;
	}	
	
	@Path("/{cid}")
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	public boolean actualizarPedido(@PathParam("cid") int cid, Customer unCustomer) {
		boolean OK = false;
		
		for (Customer cliente:clientes) {
			if (cliente.getCid() == cid) {
				clientes.remove(cliente);
				clientes.add(unCustomer);
				OK = true;
				break;
			}
		}
		return OK;
	}
	
}
