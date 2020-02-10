package principal;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que los men�s con los que interactuar� el usuario.
 * 
 * @author Borja Loren
 * @version 1.0.0
 * @since 06-02-2020
 *
 */
public class Menu {

	/**
	 * M�todo que pinta el men� de la aplicaci�n y devuelve la opci�n escogida por
	 * el usuario. Lanza excepci�n por tipo de dato err�neo que se gestionar� desde
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
		System.out.println("4) Devuelve n�mero de clientes");
		System.out.println("5) Salir");
		return leer.nextInt();
	}
}
