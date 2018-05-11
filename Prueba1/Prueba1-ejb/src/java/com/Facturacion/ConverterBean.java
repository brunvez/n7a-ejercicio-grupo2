package com.Facturacion;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class ConverterBean implements ConverterBeanLocal {

    private Map<String, Float> cotizaciones;
    
    // Se ejecuta cuando se crea el singleton (una unica vez). Sin el @Startup
    // se ejecuta la primera vez que alguien lo llama
    @PostConstruct
    private void init() {
        this.cotizaciones = new HashMap<>();
        this.cotizaciones.put("USD", 28f);
        this.cotizaciones.put("EU", 36f);
    }
    
    @Override
    public float convertTo(String currency, float amount) throws UnrecognizedCurrencyException {
        if (this.cotizaciones.containsKey(currency)) {
            float exchange = this.cotizaciones.get(currency);
            return amount / exchange;
        }
        throw new UnrecognizedCurrencyException();
    }

    
}
