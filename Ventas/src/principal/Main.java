package principal;

import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
		int opcion = 0;
		Venta[] vVentas = null;
		IoDatos ioDatos = new IoDatos();

		
		do {
			try {
				switch (opcion = Menu.menu()) {
				case 1:
					ioDatos.escrituraDeFichero();
					break;
				case 2:
					ioDatos.calcularVentasPorCliente();
					break;
				case 3:
					vVentas = ioDatos.crearArrayDeVentas();
					break;
				case 4:
					ioDatos.devuelveNumeroClientesDiferentes();
					break;
				}
			} catch (InputMismatchException e) {
			} catch (Exception e) {
			}
		} while (opcion != 5);
	}
}
