/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Facturacion;

import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FacturaBeanLocal {
    public Factura createFactura(Factura factura) throws InvalidBillException;
    
    public float getTotalInCurrency(long billId, String currency) 
            throws UnrecognizedCurrencyException, BillNotFoundException;
    
    public Factura getFactura(long id) throws BillNotFoundException;
    
    public Collection<Factura> getFacturas();
}
