package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import service.InsertarCovid;

public class GestorCovid {

	public static void main(String[] args) {
		
		InsertarCovid covid = new InsertarCovid();
		
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(ir);
		System.out.println("Introduce la ruta del fichero a cargar en la tabla: ");
		String ruta;
		try {
			ruta = bf.readLine();
			int num_altas = covid.agregarDatos(ruta);
			
			if (num_altas > 0) {
				System.out.println("Se han dado de alta "+num_altas+" registros.");
			}
			else {
				System.out.println("No se ha dado de alta ningun registro en la tabla: ");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
