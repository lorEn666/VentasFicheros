package principal;

import java.util.InputMismatchException;

/**
 * Aplicaci�n que permite la lectura y escritura mediante ficheros binarios
 * simulando l�neas de venta.
 * 
 * @author Borja Loren
 * @version 1.0.0
 * @since 06-02-2020
 *
 */
public class Main {
	/**
	 * M�todo principal donde inicia la ejecuci�n de la aplicaci�n. Desde aqu� se
	 * llamar� al m�todo que pinta el men� y podremos interactuar en base a la
	 * opci�n escogida.
	 * 
	 * @param String[] args
	 */
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
