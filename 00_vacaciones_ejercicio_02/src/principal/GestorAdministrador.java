package principal;

import java.util.Scanner;

import serviceadministrador.OperacionesAdministrador;
import utilidades.ClienteInexistente;
import utilidades.ClienteYaExistente;
import utilidades.CuentaInexistente;
import utilidades.CuentaYaExistente;
import utilidades.TitularCuentaYaExistente;

public class GestorAdministrador {

	public static void main(String[] args) {
		
		OperacionesAdministrador administrador = new OperacionesAdministrador();
		int op;
		int cuenta;
		String tipo_cuenta;
		int dni;
		String nombre;
		String direccion;
		int telefono;
		
		Scanner sc=new Scanner(System.in);
		
		do {
			menu();
			op=Integer.parseInt(sc.nextLine());
			switch(op) {
				case 1:
					try {
						System.out.println("Introduce la cuenta a dar de alta: ");
						cuenta = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el tipo de cuenta que quieres asociar: ");
						tipo_cuenta = sc.nextLine();
						if (administrador.AltaCuenta(cuenta, tipo_cuenta)) {
							System.out.println("La cuenta se ha dado de alta correctamente.");
						}
						else {
							System.out.println("Se ha producido algún error al dar de alta la cuenta.");
						}
						
					} catch (CuentaYaExistente e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					try {
						System.out.println("Introduce el DNI del cliente a dar de alta: ");
						dni = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el nombre del cliente: ");
						nombre = sc.nextLine();
						System.out.println("Introduce la direccion del cliente: ");
						direccion = sc.nextLine();
						System.out.println("Introduce el telefono del cliente: ");
						telefono = Integer.parseInt(sc.nextLine());
						if (administrador.AltaCliente(dni,nombre,direccion,telefono)) {
							System.out.println("El cliente se ha dado de alta correctamente.");
						}
						else {
							System.out.println("Se ha producido algún error al dar de alta el cliente.");
						}
						
					} catch (ClienteYaExistente e) {
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("Introduce la cuenta del cliente: ");
					cuenta = Integer.parseInt(sc.nextLine());
					System.out.println("Introduce el DNI del cliente: ");
					dni = Integer.parseInt(sc.nextLine());
					try {
						if(administrador.AltaTitular(cuenta,dni)) {
							System.out.println("Se ha dado de alta correctamente el titular de la cuenta.");
						}
						else {
							System.out.println("Se ha producido algun error al dar de alta el titular de la cuenta.");
						}
					} catch (ClienteInexistente e) {
						System.out.println(e.getMessage());
					} catch (CuentaInexistente e) {
						System.out.println(e.getMessage());
					} catch (TitularCuentaYaExistente e) {
						System.out.println(e.getMessage());
					}
					break;
			}
		}while(op!=4);

		sc.close();

	}

	private static void menu() {
		System.out.println("Elige la opcion que deseas ejecutar:");
		System.out.println("1.- Alta Cuenta. ");
		System.out.println("2.- Alta Cliente. ");
		System.out.println("3.- Alta Titular en la Cuenta. ");
		System.out.println("4.- Salir. ");
	}

}
