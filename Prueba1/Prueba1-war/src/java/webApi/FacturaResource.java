package webApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("facturas")
public class FacturaResource {

    @Context
    private UriInfo context;
    private Gson gson;

    public FacturaResource() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    } 

    @GET
    @Produces("application/json")
    public Response getJson() {
        Factura factura1 = new Factura(new Date());
        factura1.setId(Long.MAX_VALUE - 3);
        Factura factura2 = new Factura(new Date());
        factura2.setId(Long.MAX_VALUE);
        ArrayList<Factura> facturas = new ArrayList<Factura>();
        facturas.add(factura1);
        facturas.add(factura2);
        String facturasJson = gson.toJson(facturas);
        return Response
                .status(Response.Status.OK)
                .entity(facturasJson)
                .build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getJsonById(@PathParam("id") Long id) {
        Factura factura1 = new Factura(new Date());
        factura1.setId(id);
        String facturaJson = gson.toJson(factura1);
        return Response
            .status(Response.Status.OK)
            .entity(facturaJson)
            .build();
    }
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postJson(String content) {
        Factura factura = gson.fromJson(content, Factura.class);
        factura.setId(7847847848L);
        String facturaJson = gson.toJson(factura);
        return Response
            .status(Response.Status.OK)
            .entity(facturaJson)
            .build();
    }
    
    @PUT
    @Path("{id}")
    @Produces("application/json")
    public Response putJson(@PathParam("id") Long id, String content) {
        Factura factura = gson.fromJson(content, Factura.class);
        factura.setId(id);
        String facturaJson = gson.toJson(factura);
        return Response
            .status(Response.Status.OK)
            .entity(facturaJson)
            .build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteFactura(@PathParam("id") Long id) {
        return Response
            .status(204)
            .build();
    }
    
    @POST
    @Path("{id}/lineas")
    @Consumes("application/json")
    @Produces("application/json")
    public Response postLineaJson(@PathParam("id") Long id, String content) {
        Linea linea = gson.fromJson(content, Linea.class);
        linea.setId(id);
        String lineaJson = gson.toJson(linea);
        return Response
            .status(Response.Status.OK)
            .entity(lineaJson)
            .build();
    }
}
