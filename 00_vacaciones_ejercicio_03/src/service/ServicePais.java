package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.Pais;

public class ServicePais {
	
	//Utilizamos la libreria JSONSIMPLE
	private Stream<JSONObject> getStream(){
		String RUTA ="Datos_Paises.json";
		FileReader reader;
		JSONParser parser=new JSONParser();		
		JSONArray array;
		try {
			reader = new FileReader(RUTA);
			array = (JSONArray)parser.parse(reader);
			return (Stream<JSONObject>)array.stream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Stream.empty();
		} catch (org.json.simple.parser.ParseException e) {				
			e.printStackTrace();
			return Stream.empty();
		} catch (IOException e) {				
			e.printStackTrace();
			return Stream.empty();
		}		
	}
	
	private Pais convertirAPais(JSONObject s) {
		return new Pais((String)s.get("name"),
				        (String)s.get("capital"),
				        (double)s.get("area"),
				        (long)s.get("population"));
	}
	
    //Lista de países pertenecientes a la región indicada como parámetro.
    public List<Pais> listaPaisesRegion(String region){
    	return getStream().filter(s-> s.get("region").equals(region) && s.get("area")!=null)
    	                  .map(s->convertirAPais(s)) 
    			          .collect(Collectors.toList());
    }
	
  //Numero de paises en el fichero.
    public long  numeroPaises() {
		return getStream().count();
    }
    
    
	//País más poblado.
    public Pais paisMasPoblado() {
		return getStream().max((n1,n2)->(int)((long)n1.get("population")-(long)n2.get("population")))
						  .map(s->convertirAPais(s))
				          .get();
    }
	
    
	//Total de países cuyo número de habitantes supere el valor recibido como parámetro.
    public long numPaisesHabitantes(int habitantes) {
    	return getStream().filter(s->(long)s.get("population")>habitantes)
    					  .count();
    }
	
    
	//Paises cuyos nombres contengan la combinación de letras recibida como parámetro.
    public List<Pais> listaPaisesNombre(String combinacion){
    	return getStream().filter(s->((String)s.get("name")).contains(combinacion))
    			          .map(s->convertirAPais(s))
    			          .collect(Collectors.toList());
    }
    
	/*
	//Posición (longitud y latitud) del país cuyo alfa2code se recibe como parámetro.
    public Double<> paisPosicion(String alfa2code){
    	return getStream().filter(s-> ((String)s.get("alpha2Code")).equals(alfa2code))
    			          .map(s->convertirALongitudLatitud(s))
    			          .toArray();
    	
    }
    
    private double [] convertirALongitudLatitud(JSONObject s) {
		double [] datos = new double[2];  
		JSONArray longlat =(JSONArray)s.get("latlng"); 
		int i = 0;
		for(Object obj:longlat) {
			datos[i]=(Double)obj;
			i++;
		}
		return datos;
    }
    
	
	//Población media de la región que se recibe como parámetro.
    public double mediaRegion(String region) {
    	
    }
    
	
	//Tabla con las regiones y la población total de cada una.
	public List<Tabla> regionPoblacionTotal(){
		
	}
	*/
}
