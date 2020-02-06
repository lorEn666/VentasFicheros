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

public class IoDatos {

	private final String RUTA = "./ventas/ventas.dat";

	private File fichero;
	private DataInputStream lectura;
	private DataOutputStream escritura;

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

	private void abrirStreamDeLectura() {
		try {
			lectura = new DataInputStream(new FileInputStream(fichero));
		} catch (FileNotFoundException e) {
		}
	}

	private void abrirStreamDeEscritura() {
		try {
			escritura = new DataOutputStream(new FileOutputStream(fichero, true));
		} catch (FileNotFoundException e) {
		}
	}

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
				abrirStreamDeLectura();

				for (int i = 0;; i++) {
					for (int j = 0; j < vNombre.length; j++) {
						vNombre[j] = lectura.readUTF();
						if (vNombre[i] != null && i != j) {
							if (!vNombre[i].equals(vNombre[j])) {
								lectura.readInt();
								lectura.readInt();
								lectura.readDouble();
								clientesDiferentes++;
							} else {
								lectura.readInt();
								lectura.readInt();
								lectura.readDouble();
							}
						} else {
							lectura.readInt();
							lectura.readInt();
							lectura.readDouble();
						}
					}
				}
			} catch (IOException e1) {
				try {
					lectura.close();
					System.out.println("El número de clientes diferentes es: " + clientesDiferentes);
				} catch (IOException e2) {
				}
			}
		}
	}
}
