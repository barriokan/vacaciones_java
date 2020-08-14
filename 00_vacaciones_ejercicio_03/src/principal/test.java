package principal;

import java.util.ArrayList;
import java.util.List;

import model.Pais;
import service.ServicePais;

public class test {

	public static void main(String[] args) {
		
		ServicePais sp = new ServicePais();
		
		System.out.println("El numero de paises en total son: "+ sp.numeroPaises());
		
		List<Pais> paises = new ArrayList<>();
		paises = sp.listaPaisesRegion("Europe");
		System.out.println("Los paises de Europa son: ");
		for(Pais p:paises) {
			
			System.out.println("                                      ");
			System.out.println("--------------------------------------");
			System.out.println(p.getNombre()+" - "+p.getCapital()+" - "+p.getArea()+" - "+p.getPoblacion());
			System.out.println("--------------------------------------");
		}

		Pais pais = sp.paisMasPoblado();
		System.out.println("**********************************");
		System.out.println("El pais mas poblado del mundo es: ");
		System.out.println(pais.getNombre());
		System.out.println(pais.getCapital());
		System.out.println(pais.getArea());
		System.out.println("con una poblacion de: "+pais.getPoblacion());
		System.out.println("**********************************");
		
		int poblacion = 1000000000;
		System.out.println("El numero de paises con una poblacion superios a "+poblacion+" son: "+sp.numPaisesHabitantes(poblacion));
	
		String combinacion = "Bur";
		paises = sp.listaPaisesNombre(combinacion);
		System.out.println("Los paises que contienen la palabra "+combinacion+" son: ");
		paises.forEach(p->System.out.println(p.getNombre()+" - "+
										 p.getCapital()+" - "+
										 p.getArea()+" - "+
										 p.getPoblacion()));
	}

}
