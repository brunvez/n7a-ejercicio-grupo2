package com.Facturacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Factura {
    private Long id;
    private Date date;
    private ArrayList<Linea> lineas;

    public Factura(Date date) {
        this.date = date;
        this.lineas = new ArrayList<Linea>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        float total = 0;
        for (int i = 0; i < this.lineas.size(); i++) {
            Linea linea = this.lineas.get(i);
            float totalLinea = linea.getCantidad() * linea.getPrecioUnitario();
            total += totalLinea;
        }
        
        return total;
    }
}
