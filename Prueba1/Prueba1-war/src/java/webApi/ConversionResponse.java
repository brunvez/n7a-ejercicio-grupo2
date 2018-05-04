/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webApi;

/**
 *
 * @author user
 */
public class ConversionResponse {
    private float total;
    private String currency;

    public ConversionResponse(float total, String currency) {
        this.total = total;
        this.currency = currency;
    }
}
