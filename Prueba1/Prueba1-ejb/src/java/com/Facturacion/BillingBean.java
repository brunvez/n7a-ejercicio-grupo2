/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Facturacion;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class BillingBean implements BillingBeanLocal {
    public static final float MIN_TOTAL = 1000;
    
    @EJB
    private ConverterBeanLocal converterBean;
    
    private Map<Long, Factura> facturas;
    
    // Se ejecuta cuando se crea el singleton (una unica vez). Sin el @Startup
    // se ejecuta la primera vez que alguien lo llama
    @PostConstruct
    private void init() {
        this.facturas = new HashMap<>();
    }
    
    @Override
    public Factura createFactura(Factura factura) throws InvalidBillException {
        float total = factura.getTotal();
        if (total <= MIN_TOTAL) {
            throw new InvalidBillException("El monto total debe ser superior a " + MIN_TOTAL);
        }
        Date currentDate = new Date();
        if (currentDate.before(factura.getDate())) {
            throw new InvalidBillException("La fecha de la factura no puede ser futura");
        }
        factura.setId(new Long(this.facturas.size()));
        this.facturas.put(factura.getId(), factura);
        
        return factura;
    }

    @Override
    public float getTotalInCurrency(long billId, String currency) 
            throws UnrecognizedCurrencyException, BillNotFoundException {
        if (this.facturas.containsKey(billId)) {
            Factura bill = this.facturas.get(billId);
            float convertedTotal = this.converterBean.convertTo(currency, bill.getTotal());
            return convertedTotal;
        }
        else {
            throw new BillNotFoundException();
        }
    }
}
