package principal;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que los menús con los que interactuará el usuario.
 * 
 * @author Borja Loren
 * @version 1.0.0
 * @since 06-02-2020
 *
 */
public class Menu {

	/**
	 * Método que pinta el menú de la aplicación y devuelve la opción escogida por
	 * el usuario. Lanza excepción por tipo de dato erróneo que se gestionará desde
	 * la clase 'Main'.
	 * 
	 * @return
	 * @throws InputMismatchException
	 */
	public static int menu() throws InputMismatchException {
		Scanner leer = new Scanner(System.in);

		System.out.println("1) Registrar venta en fichero");
		System.out.println("2) Calcular ventas por cliente");
		System.out.println("3) Asignar ventas a vector");
		System.out.println("4) Devuelve número de clientes");
		System.out.println("5) Salir");
		return leer.nextInt();
	}
}
