package restaurant.interfaces;

import restaurant.exceptions.SinSuficientesIngredientesException;

public interface Vendible {
	public float getPrecioVenta();
	public void preparar() throws SinSuficientesIngredientesException;
}
