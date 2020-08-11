package serviceusuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Movimiento;
import utilidades.Datos;
import utilidades.SaldoNegativoException;

public class OperacionesCajero {
	
	public boolean validarCuenta(int cuenta) {
		
		try {
			Connection conn = Datos.getConnection();
			String sql="select * from cuentas where numeroCuenta =?";
			PreparedStatement st  = conn.prepareStatement(sql);  
			st.setInt(1, cuenta);
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
	
	
	public double ingresarDinero(int cuenta, double cantidad) throws SaldoNegativoException {
		
		try {
			Connection conn = Datos.getConnection();
			String sql="insert into movimientos(idCuenta,fecha,cantidad,operacion) values(?,?,?,?)";
			PreparedStatement st=conn.prepareStatement(sql);
			
			st.setInt(1, cuenta);
			
			java.util.Date date =new Date();
			java.sql.Timestamp fecha_actual = new java.sql.Timestamp(date.getTime());
			st.setTimestamp(2,fecha_actual);
			
			st.setDouble(3, cantidad);
			st.setString(4, "ingreso");
			st.execute();
			actualizarSaldo(cuenta,cantidad);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0;
		}
		return cantidad;
	}
	
	public double sacarDinero(int cuenta, double cantidad) throws SaldoNegativoException {
		try {
			Connection conn = Datos.getConnection();
			cantidad = cantidad * (-1);
			actualizarSaldo(cuenta,cantidad);
			cantidad = cantidad *(-1);
			String sql="insert into movimientos(idCuenta,fecha,cantidad,operacion) values(?,?,?,?)";
			PreparedStatement st=conn.prepareStatement(sql);
			
			st.setInt(1, cuenta);
			
			java.util.Date date =new Date();
			java.sql.Timestamp fecha_actual = new java.sql.Timestamp(date.getTime());
			st.setTimestamp(2,fecha_actual);
			
			st.setDouble(3, cantidad);
			st.setString(4, "extracción");
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0;
		}
		return (cantidad * (-1));
	}
	
	public double mostrarSaldo(int cuenta) {
		try {
			Connection conn = Datos.getConnection();
			String sql="select * from cuentas where numeroCuenta =?";
			PreparedStatement st  = conn.prepareStatement(sql);  
			st.setInt(1, cuenta);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				return rs.getDouble("saldo");
			}
			else {			
				return 0.0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	private boolean actualizarSaldo (int cuenta,double cantidad) throws SaldoNegativoException {
		
		Connection conn;
		try {
			conn = Datos.getConnection();
			String sql="update cuentas set saldo = ? where numeroCuenta =?";
			PreparedStatement st  = conn.prepareStatement(sql);
			double saldo_actual = mostrarSaldo(cuenta) + cantidad;
			if (saldo_actual < 0) {
				throw new SaldoNegativoException("La cuenta no se puede quedar en numero rojos.");
			}
			else {
				st.setDouble(1,saldo_actual);
				st.setInt(2, cuenta);
				st.execute();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public List<Movimiento> ultimosMovimientos(int cuenta){
		try {
			Connection conn = Datos.getConnection();
			List<Movimiento> movimientos=new ArrayList<>();	
			String sql="select * from movimientos where idCuenta =? and fecha > ?";
			PreparedStatement st  = conn.prepareStatement(sql);  
			st.setInt(1, cuenta);
			LocalDateTime ldt = LocalDateTime.now();
			ldt=ldt.minusDays(30);
			Timestamp fecha_actual_menos30 = Timestamp.valueOf(ldt);
			st.setTimestamp(2,fecha_actual_menos30);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				movimientos.add(new Movimiento(rs.getInt("idCuenta"),
						rs.getDate("fecha"),
						rs.getDouble("cantidad"),
						rs.getString("operacion")));
			}
			return movimientos;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean realizarTransferencia(int cuenta_origen,int cuenta_destino, double cantidad) throws SaldoNegativoException {
		
		sacarDinero(cuenta_origen,cantidad);
		
		ingresarDinero(cuenta_destino,cantidad);
		
		return true;
	}

}

