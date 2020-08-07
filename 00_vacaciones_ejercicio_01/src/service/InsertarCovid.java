package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	
	public int agregarDatos(String ruta) throws FileNotFoundException,IOException{
		int alta = 0;
		FileReader fr = new FileReader(ruta);
		BufferedReader bf = new BufferedReader(fr);
		String linea =" ";
		while ((linea=bf.readLine()) != null) {
			//transformamos la línea en un array con los datos del fichero del covid:
			String[] datos=linea.split(",");
			//obtenemos los diferentes campos de cada registro del fichero
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			try {
				String comunidad =datos[0];
				Date fecha =sdf.parse(datos[1]);
				int n_casos = Integer.parseInt(datos[2]);
				int n_casos_pru_pcr = Integer.parseInt(datos[3]);
				int n_casos_pru_test = Integer.parseInt(datos[4]);
				int n_casos_pru_otras = Integer.parseInt(datos[5]);
				int n_casos_pru_desconocida = Integer.parseInt(datos[6]);
					
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
		bf.close();
		return alta;
	}
}
