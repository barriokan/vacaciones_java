package utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidacionesAdministrador {

	public static boolean validarCuenta(int cuenta) {
		
		try {
			Connection conn = Datos.getConnection();
			String sql ="Select * from cuentas where numeroCuenta =?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,cuenta);
			ResultSet rs=st.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean validarCliente(int dni) {
		try {
			Connection conn = Datos.getConnection();
			String sql ="select * from clientes where dni=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,dni);
			ResultSet rs=st.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean validarTitularCuenta(int cuenta,int dni) {
		try {
			Connection conn = Datos.getConnection();
			String sql ="select * from titulares where idCuenta =? and idCliente=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,cuenta);
			st.setInt(2,dni);
			ResultSet rs=st.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
