package serviceadministrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utilidades.ClienteInexistente;
import utilidades.ClienteYaExistente;
import utilidades.CuentaInexistente;
import utilidades.CuentaYaExistente;
import utilidades.Datos;
import utilidades.TitularCuentaYaExistente;
import utilidades.ValidacionesAdministrador;

public class OperacionesAdministrador {
	
	public boolean AltaCuenta(int cuenta,String tipo_cuenta) throws CuentaYaExistente {
		
		if(!ValidacionesAdministrador.validarCuenta(cuenta)) {
			try {
				Connection conn = Datos.getConnection();
				String sql="insert into cuentas(numeroCuenta,saldo,tipocuenta) values (?,?,?) ";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, cuenta);
				st.setDouble(2, 0.0);
				st.setString(3, tipo_cuenta);
				st.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			throw new CuentaYaExistente("La cuenta que se quiere dar de alta ya existe");
		}
		return true;
	}

	
	public boolean AltaCliente(int dni,String nombre,String direccion,int telefono) throws ClienteYaExistente {
		
		if(!ValidacionesAdministrador.validarCliente(dni)) {
		
			try {
				Connection conn = Datos.getConnection();
				String sql ="insert into clientes(dni,nombre,direccion,telefono) values (?,?,?,?)";
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, dni);
				st.setString(2, nombre);
				st.setString(3, direccion);
				st.setInt(4, telefono);
				st.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			throw new ClienteYaExistente("El cliente que se quiere dar de alta ya existe");
		}
		return true;
		
	}


	public boolean AltaTitular(int cuenta,int dni) throws ClienteInexistente, CuentaInexistente, TitularCuentaYaExistente {
		
		if(!ValidacionesAdministrador.validarCuenta(cuenta)) {
			throw new CuentaInexistente("La cuenta donde se quiere dar de alta como titular no existe.");
			
		}
		
		if(!ValidacionesAdministrador.validarCliente(dni)) {
			throw new ClienteInexistente("El cliente que se quiere dar de alta como titular de la cuenta no existe.");
		}
		
		if(ValidacionesAdministrador.validarTitularCuenta(cuenta,dni)) {
			throw new TitularCuentaYaExistente("El titular de la cuenta ya se ha dado de alta.");
		}
		
		try {
			Connection conn=Datos.getConnection();
			String sql = "insert into titulares(idCuenta,idCliente) values(?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, cuenta);
			st.setInt(2, dni);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	
	}
}
