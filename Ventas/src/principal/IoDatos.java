package principal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase desde la cual se ejecutarán todos los métodos necesarios para leer o
 * escribir ficheros binarios. Se comprobará su existencia, se crearán de no
 * existir y se escribirá sobre estos ficheros en base a la interacción con el
 * usuario.
 * 
 * @author Borja Loren
 * @version 1.0.0
 * @since 06-02-2020
 *
 */
public class IoDatos {

	private final String RUTA = "./ventas/ventas.dat";

	private File fichero;
	private DataInputStream lectura;
	private DataOutputStream escritura;

	/**
	 * Principal y único constructor de la clase. Los atributos encargados de
	 * lectura/escritura se iniciarán a 'null' mientras no se les requiera. Se
	 * asignará la ruta al nuevo fichero y si no existe, se crea.
	 */
	public IoDatos() {

		this.fichero = new File(RUTA);

		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
			}
		}

		this.lectura = null;
		this.escritura = null;
	}

	/**
	 * Método que inicia las clases 'DataInputStream' y 'FileInputStream'. Será
	 * usado antes de trabajar con el fichero.
	 */
	private void abrirStreamDeLectura() {
		try {
			lectura = new DataInputStream(new FileInputStream(fichero));
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * Método que inicia las clases 'DataOutputStream' y 'FileOutputStream'. Será
	 * usado antes de trabajar con el fichero.
	 */
	private void abrirStreamDeEscritura() {
		try {
			escritura = new DataOutputStream(new FileOutputStream(fichero, true));
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * Método que se encargará de preguntar al usuario sobre ciertas
	 * características. La introducción de datos por parte del usuario se escribirá
	 * directamente en el fichero.
	 */
	public void escrituraDeFichero() {
		Scanner leer = new Scanner(System.in);

		abrirStreamDeEscritura();

		try {
			System.out.println("Inserte nombre del cliente:");
			escritura.writeUTF(leer.next());

			System.out.println("Inserte código del producto vendido:");
			escritura.writeInt(leer.nextInt());

			System.out.println("Inserte unidades vendidas de ese producto:");
			escritura.writeInt(leer.nextInt());

			System.out.println("Inserte precio unitario del producto:");
			escritura.writeDouble(leer.nextDouble());
		} catch (IOException e) {
			System.out.println("No tiene permisos de lectura o escritura sobre el fichero.");
			return;
		} catch (InputMismatchException e) {
			System.out.println("Tipo de dato introducido no coincide con el solicitado.");
			return;
		}
		try {
			escritura.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Método que busca al cliente introducido y devuelve el valor de todas sus
	 * compras en Euros.
	 */
	public void calcularVentasPorCliente() {
		Scanner leer = new Scanner(System.in);
		String nombreCliente;
		int cantidad;
		double precioUnitario, total = 0;

		System.out.println("Inserte nombre del cliente que desea buscar:");
		nombreCliente = leer.next();

		abrirStreamDeLectura();

		try {
			while (true) {
				if (lectura.readUTF().equals(nombreCliente)) {
					lectura.readInt();
					cantidad = lectura.readInt();
					precioUnitario = lectura.readDouble();
					total += cantidad * precioUnitario;
				} else {
					lectura.readInt();
					lectura.readInt();
					lectura.readDouble();
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				lectura.close();
				System.out.println("Las compras realizadas por " + nombreCliente + " ascienden a " + total + "€");
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Método que asigna todas las líneas de venta encontradas en el fichero binario
	 * a un Array de tipo 'Venta'.
	 * 
	 * @return vVenta[]
	 */
	public Venta[] crearArrayDeVentas() {

		Venta[] vVenta = new Venta[20];

		abrirStreamDeLectura();

		try {
			for (int i = 0;; i++) {
				if (vVenta[i] == null) {
					vVenta[i] = new Venta(lectura.readUTF(), lectura.readInt(), lectura.readInt(),
							lectura.readDouble());
				}
			}
		} catch (IOException e) {
			System.out.println("Fin de lectura.");
		} finally {
			try {
				lectura.close();
			} catch (IOException e) {
			}
		}
		return vVenta;
	}

	/**
	 * Método que devuelve el número de clientes diferentes. Es decir, los clientes
	 * de manera individual sin tener en cuenta si han realizado más de una compra.
	 */
	public void devuelveNumeroClientesDiferentes() {
		String[] vNombre = new String[20];
		int clientesDiferentes = 0;

		abrirStreamDeLectura();

		try {
			for (int i = 0;; i++) {
				if (vNombre[i] == null) {
					vNombre[i] = lectura.readUTF();
					lectura.readInt();
					lectura.readInt();
					lectura.readDouble();
				}
			}
		} catch (IOException e) {
			try {
				lectura.close();
			} catch (IOException e1) {
			}
			for (int i = 0; i < vNombre.length; i++) {
				for (int j = 0; j < vNombre.length; j++) {
					if (vNombre[i] != null && vNombre[j] != null && i != j && vNombre[i].equals(vNombre[j])) {
						vNombre[j] = null;
					}
				}
			}
			for (int i = 0; i < vNombre.length; i++) {
				if (vNombre[i] != null) {
					clientesDiferentes++;
				}
			}
			System.out.println("El número de clientes diferentes es: " + clientesDiferentes);
		}
	}
}
