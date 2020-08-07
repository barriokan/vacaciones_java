package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utilidades.Datos;

public class InsertarCovid {

	private boolean buscarDatos(Date fecha,String comunidad) {
		
		Connection con;
		try {
			con = Datos.getConnection();
			String sql="select * from datos_positivos where fecha =? and comunidad=?";
			PreparedStatement st=con.prepareStatement(sql);
			st.setDate(1,new java.sql.Date(fecha.getTime()));
			st.setString(2, comunidad);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
		
	}
	
	
	public int agregarDatos(String ruta) throws FileNotFoundException{
		int alta = 0;
		String ccaa_iso; 		            
		String fecha_iso; 	                     
		int num_casos; 		             
		int num_casos_prueba_pcr; 	
		int num_casos_prueba_test_acr;
		int num_casos_prueba_otras; 		 
		int num_casos_prueba_desconocida; 
		
		//Definimos un parseReader de JsonParser para que pasandole la ruta del fichero, 
		//este nos lo pase a un objeto de la clase JsonArray:
		JsonArray gsonarray = JsonParser.parseReader(new FileReader(ruta)).getAsJsonArray();
		
		for (JsonElement obj:gsonarray) {
			
			JsonObject gsonobj = obj.getAsJsonObject();
			
			ccaa_iso 		             = gsonobj.get("ccaa_iso").getAsString();
			fecha_iso 	                 = gsonobj.get("fecha").getAsString();
			num_casos 		             = gsonobj.get("num_casos").getAsInt();
			num_casos_prueba_pcr 		 = gsonobj.get("num_casos_prueba_pcr").getAsInt();
			num_casos_prueba_test_acr 	 = gsonobj.get("num_casos_prueba_test_ac").getAsInt();
			num_casos_prueba_otras 		 = gsonobj.get("num_casos_prueba_otras").getAsInt();
			num_casos_prueba_desconocida = gsonobj.get("num_casos_prueba_desconocida").getAsInt();

			//obtenemos los diferentes campos de cada registro del fichero
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				String comunidad =ccaa_iso;
				Date fecha =sdf.parse(fecha_iso);
				int n_casos = num_casos;
				int n_casos_pru_pcr = num_casos_prueba_pcr;
				int n_casos_pru_test = num_casos_prueba_test_acr;
				int n_casos_pru_otras = num_casos_prueba_otras;
				int n_casos_pru_desconocida = num_casos_prueba_desconocida;
					
				//vamos a buscar en la tabla si el registro leido ya está insertado
				if (!buscarDatos(fecha,comunidad)) {
					Connection con;
					try {
						con = Datos.getConnection();
						String sql="insert into datos_positivos(fecha,comunidad,num_casos,num_casos_pru_pcr,num_casos_pru_test,num_casos_pru_otras,num_casos_pru_desconocida) values(?,?,?,?,?,?,?)";
						PreparedStatement st=con.prepareStatement(sql);
						st.setDate(1,new java.sql.Date(fecha.getTime()));
						st.setString(2, comunidad);
						st.setInt(3, n_casos);
						st.setInt(4, n_casos_pru_pcr);
						st.setInt(5, n_casos_pru_test);
						st.setInt(6, n_casos_pru_otras);
						st.setInt(7, n_casos_pru_desconocida);
						st.execute();
						alta++;
					} catch (SQLException e) {
						e.printStackTrace();
					}
						
				}
					
			} catch (ParseException e) {
				e.printStackTrace();
			}
				
		}
		return alta;
	}
}
