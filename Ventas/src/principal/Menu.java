package principal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

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
