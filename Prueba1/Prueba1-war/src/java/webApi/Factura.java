package webApi;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    private BigDecimal total;
}
