package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Movimiento;
import serviceusuario.OperacionesCajero;
import utilidades.SaldoNegativoException;

public class GestorCajero {

	public static void main(String[] args) {
		
		OperacionesCajero cajero=new OperacionesCajero();;
		int cuenta;
		int op;
		Scanner sc=new Scanner(System.in);
		System.out.println("Introduce el numero de cuenta con el que operar: ");
		cuenta=Integer.parseInt(sc.nextLine());
		
		if (!cajero.validarCuenta(cuenta)) {
			
			System.out.println("¡La cuenta con la que se intenta operar NO EXISTE! ");
			
		}
		else {
			do {
				menu();
				op=Integer.parseInt(sc.nextLine());
				switch(op) {
					case 1:
						System.out.println("Introduce la cantidad a ingresar: ");
						double ingreso;
						try {
							ingreso = cajero.ingresarDinero(cuenta, Double.parseDouble(sc.nextLine()));
							System.out.println("Se ha ingresado correctamente la cantidad de: "+ingreso);
						} catch (SaldoNegativoException e) {
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						System.out.println("Introduce la cantidad a extraer: ");
						double extraccion;
						try {
							extraccion = cajero.sacarDinero(cuenta, Double.parseDouble(sc.nextLine()));
							System.out.println("Se ha extraido correctamente la cantidad de: "+extraccion);
						} catch (SaldoNegativoException e) {
							System.out.println(e.getMessage());
						}
						break;
					case 3:
						System.out.println("El saldo de la cuenta " + cuenta +" es de ");
						System.out.println(cajero.mostrarSaldo(cuenta));
						break;
					case 4:
						List<Movimiento> movimientos=new ArrayList<>();
						movimientos = cajero.ultimosMovimientos(cuenta);
						System.out.println("Los movimientos en los ultimos 30 dias son: ");
						movimientos.forEach(c -> System.out.println(c.getCuenta()+" - "+
																	c.getOperacion()+" - "+
																	c.getFecha()+" - "+
																	c.getCantidad()));
						
						break;
					case 5:
						System.out.println("Introduce el numero de cuenta de destino: ");
						int cuenta_destino = Integer.parseInt(sc.nextLine());
						if (!cajero.validarCuenta(cuenta_destino)) {
							
							System.out.println("¡La cuenta destino a la que se intenta hacer la transferencia NO EXISTE! ");
							
						}
						else {
							System.out.println("Introduce la cantidad a traspasar: ");
							double transferencia = Double.parseDouble(sc.nextLine());
							try {
								if(cajero.realizarTransferencia(cuenta, cuenta_destino,transferencia)) {
									System.out.println("Se ha realizado correctamente la transferencia.");
									System.out.println("Se ha transferifdo la cantidad de "+transferencia+" de la cuenta "+cuenta+" a la cuenta "+cuenta_destino);
								}
								else {
									System.out.println("Se ha producido un error al realizar la transferencia.");
								}
							} catch (SaldoNegativoException e) {
								System.out.println(e.getMessage());
							}
						}
						break;
				}
			}while(op!=6);
	
		}
		sc.close();
		
	}
		
	
	private static void menu() {
		System.out.println("1.- Ingreso. ");
		System.out.println("2.- Extraccion. ");
		System.out.println("3.- Consulta de saldo. ");
		System.out.println("4.- Consulta de últimos movimientos(30 días). ");
		System.out.println("5.- Realizar Transferencia. ");
		System.out.println("6.- Salir. ");
	}
	

}
