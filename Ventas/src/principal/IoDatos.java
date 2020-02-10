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
 * Clase desde la cual se ejecutar�n todos los m�todos necesarios para leer o
 * escribir ficheros binarios. Se comprobar� su existencia, se crear�n de no
 * existir y se escribir� sobre estos ficheros en base a la interacci�n con el
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
	 * Principal y �nico constructor de la clase. Los atributos encargados de
	 * lectura/escritura se iniciar�n a 'null' mientras no se les requiera. Se
	 * asignar� la ruta al nuevo fichero y si no existe, se crea.
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
	 * M�todo que inicia las clases 'DataInputStream' y 'FileInputStream'. Ser�
	 * usado antes de trabajar con el fichero.
	 */
	private void abrirStreamDeLectura() {
		try {
			lectura = new DataInputStream(new FileInputStream(fichero));
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * M�todo que inicia las clases 'DataOutputStream' y 'FileOutputStream'. Ser�
	 * usado antes de trabajar con el fichero.
	 */
	private void abrirStreamDeEscritura() {
		try {
			escritura = new DataOutputStream(new FileOutputStream(fichero, true));
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * M�todo que se encargar� de preguntar al usuario sobre ciertas
	 * caracter�sticas. La introducci�n de datos por parte del usuario se escribir�
	 * directamente en el fichero.
	 */
	public void escrituraDeFichero() {
		Scanner leer = new Scanner(System.in);

		abrirStreamDeEscritura();

		try {
			System.out.println("Inserte nombre del cliente:");
			escritura.writeUTF(leer.next());

			System.out.println("Inserte c�digo del producto vendido:");
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
	 * M�todo que busca al cliente introducido y devuelve el valor de todas sus
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
				System.out.println("Las compras realizadas por " + nombreCliente + " ascienden a " + total + "�");
			} catch (IOException e) {
			}
		}
	}

	/**
	 * M�todo que asigna todas las l�neas de venta encontradas en el fichero binario
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
	 * M�todo que devuelve el n�mero de clientes diferentes. Es decir, los clientes
	 * de manera individual sin tener en cuenta si han realizado m�s de una compra.
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
			System.out.println("El n�mero de clientes diferentes es: " + clientesDiferentes);
		}
	}
}
