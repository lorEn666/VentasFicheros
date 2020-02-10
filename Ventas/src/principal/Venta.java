package principal;

/**
 * Clase que contiene los parámetros de una línea de venta.
 * 
 * @author Borja Loren
 * @version 1.0.0
 * @since 06-02-2020
 *
 */
public class Venta {

	private String nombre;
	private int codigoProducto;
	private int cantidad;
	private double precioUnitario;

	/**
	 * Único constructor de la clase que recibe ciertos parámetros.
	 * 
	 * @param nombre
	 * @param codigoProducto
	 * @param cantidad
	 * @param precioUnitario
	 */
	public Venta(String nombre, int codigoProducto, int cantidad, double precioUnitario) {

		this.nombre = nombre;
		this.codigoProducto = codigoProducto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	/**
	 * Método 'toString'.
	 */
	@Override
	public String toString() {
		return "\nNombre cliente: " + nombre + "\tCodigo del producto: " + codigoProducto + "\tCantidad vendida: "
				+ cantidad + "\tPrecio unitario: " + precioUnitario;
	}
}
