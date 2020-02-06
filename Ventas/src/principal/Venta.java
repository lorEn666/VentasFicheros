package principal;

public class Venta {
	
	private String nombre;
	private int codigoProducto;
	private int cantidad;
	private double precioUnitario;
	
	public Venta(String nombre, int codigoProducto, int cantidad, double precioUnitario) {
	
		this.nombre = nombre;
		this.codigoProducto = codigoProducto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	@Override
	public String toString() {
		return "\nNombre cliente: " + nombre + "\tCodigo del producto: " + codigoProducto + "\tCantidad vendida: " + cantidad
				+ "\tPrecio unitario: " + precioUnitario;
	}
	
	
	

}
