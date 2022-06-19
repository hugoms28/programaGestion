package es.studium.empresacarreras;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDatos {

	String driver = "com.mysql.cj.jdbc.Driver"; 
	String url = "jdbc:mysql://localhost:3306/empresa_carreras_pr"; 
	String login = "admin2"; //User MySQL
	String password = "Studium2022"; 
	String sentencia = ""; 
	Connection connection = null; 
	Statement statement = null; 
	
	public BaseDatos()
	{
		
	}
	public void conectar()
	{
		try
		{
			//Cargar controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD
			connection = DriverManager.getConnection(url, login, password);
			
		}
		catch (ClassNotFoundException cnfe){}
		catch (SQLException sqle){}
		
	}
	public Connection conectar2()
	{
		try
		{
			//Cargar controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD
			connection = DriverManager.getConnection(url, login, password);
			
		}
		catch (ClassNotFoundException cnfe){}
		catch (SQLException sqle){}
		return connection;
	}
	
	public int consultar(String sentencia)
	{
		int resultado = -1;
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			if(rs.next()) //Si existe al menos 1
			{
				resultado = rs.getInt("tipoUsuario");
			}
		}
		catch (SQLException sqle)
		{System.out.println("Error 2 -" + sqle.getMessage());}
		return resultado;
	}
	
	public String consultarPersonas() 
	{
		String contenido = "";
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM personas");
			while(rs.next()) //Si existe al menos 1
			{
				contenido = contenido + rs.getString("idPersona") + "- " +  rs.getString("nombrePersona") + " "+
						rs.getString("apellidosPersona") + " " + rs.getString("edadPersona") + "\n";
			}
		}
		catch (SQLException sqle)
		{}
		return (contenido);
	}
	
	public int insertarPersonas(String sentencia)
	{
		int resultado = 0; //éxito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	return(resultado);
	}
	

	public ResultSet rellenarPersonas()
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM personas");
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	
	public int borrarPersona(int idPersona)
	{
		int resultado = 0;
		//Devolver un 0 - Exito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar DELETE con Update
			String sentencia = "DELETE FROM personas WHERE idPersona="+idPersona;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	return(resultado);
	}
		
	public ResultSet consultarPersona(String seleccionado)
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM personas WHERE idPersona = " + seleccionado);
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	
	public int actualizarPersona(String idPersona, String nombreNuevo,String apellidosNuevo, String edadNueva, 
			String domicilioNuevo,String emailNuevo,String telefonoNuevo )
	{
		int resultado = 0;
		String sentencia = "UPDATE personas SET nombrePersona = '"+nombreNuevo+"', apellidosPersona='" + apellidosNuevo+"', edadPersona= '"+edadNueva+
				"', domicilioPersona='"+domicilioNuevo+"', emailPersona='"+emailNuevo+"', telefonoPersona='"+telefonoNuevo +"' WHERE idPersona = " + idPersona;
			
		try
				{
					//Crear la sentencia
					statement = connection.createStatement();
					//Ejecutar el Update
					statement.executeUpdate(sentencia);
				}
				catch(SQLException sqle)
				{
					resultado = -1; //Error
				}

		return(resultado);
	}
	public void desconectar()
	{
		try
		{
			if (connection!=null)
			{
				connection.close();
			}
			
		}
		catch (SQLException e)
		{}
	}
	public int insertarEmpleados( String sentencia)
	{
		int resultado = 0; //éxito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	return(resultado);
		}
		
	
	
	public int borrarEmpleado(int idEmpleado)
	{
		int resultado = 0;
		//Devolver un 0 - Exito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar DELETE con Update
			String sentencia = "DELETE FROM empleados WHERE idEmpleado="+idEmpleado;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	
	return(resultado);
	}
	public ResultSet rellenarEmpleados()
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM empleados");
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	public ResultSet consultarEmpleado(String seleccionado)
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM empleados WHERE idEmpleado = " + seleccionado);
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	public int actualizarEmpleado(String idEmpleado, String salarioNuevo)
	{
		int resultado = 0;
		String sentencia = "UPDATE empleados SET salarioEmpleado = '"+salarioNuevo+"' WHERE idEmpleado = " + idEmpleado;
			
		try
				{
					//Crear la sentencia
					statement = connection.createStatement();
					//Ejecutar el Update
					statement.executeUpdate(sentencia);
				}
				catch(SQLException sqle)
				{
					resultado = -1; //Error
				}

		return(resultado);
	}
		
	public int insertarTareas( String sentencia)
	{
		int resultado = 0; //éxito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	return(resultado);
		}
		
	public int borrarTarea(int idTarea)
	{
		int resultado = 0;
		//Devolver un 0 - Exito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar DELETE con Update
			String sentencia = "DELETE FROM tareas WHERE idTarea="+idTarea;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	
	return(resultado);
	}
	public ResultSet rellenarTareas()
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM tareas");
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	
	public ResultSet consultarTarea(String seleccionado)
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM tareas WHERE idTarea = " + seleccionado);
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	public int actualizarTarea(String idTarea, String nombreTarea, String tipoTarea)
	{
		int resultado = 0;
		String sentencia = "UPDATE tareas SET nombreTarea = '"+nombreTarea+"' , tipoTarea='" + tipoTarea+"' WHERE idTarea = " + idTarea;
			
		try
				{
					//Crear la sentencia
					statement = connection.createStatement();
					//Ejecutar el Update
					statement.executeUpdate(sentencia);
				}
				catch(SQLException sqle)
				{
					resultado = -1; //Error
				}

		return(resultado);
	}
	public int insertarProveedores(String sentencia)
	{
		int resultado = 0; //éxito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	return(resultado);
		}
	public int borrarProveedor(int idProveedor)
	{
		int resultado = 0;
		//Devolver un 0 - Exito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar DELETE con Update
			String sentencia = "DELETE FROM proveedores WHERE idProveedor="+idProveedor;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //error
		}
	
	return(resultado);
	}
	public ResultSet rellenarProveedores()
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM proveedores");
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	public String consultarTareas() 
	{
		String contenido = "";
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM tareas");
			while(rs.next()) //Si existe al menos 1
			{
				contenido = contenido + rs.getString("idTarea") + "- " +  rs.getString("nombreTarea") + " "+
						rs.getString("tipoTarea") + " " + rs.getString("idEmpleadoFK") + "\n";
			}
		}
		catch (SQLException sqle)
		{}
		return (contenido);
	}
	public String consultarEmpleados() 
	{
		String contenido = "";
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM empleados");
			while(rs.next()) //Si existe al menos 1
			{
				contenido = contenido + rs.getString("idEmpleado") + "- " +  rs.getString("salarioEmpleado") + " "+
						rs.getString("idPersonaFK") +   "\n";
			}
		}
		catch (SQLException sqle)
		{}
		return (contenido);
	}
	public String consultarProveedores() 
	{
		String contenido = "";
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM proveedores");
			while(rs.next()) //Si existe al menos 1
			{
				contenido = contenido + rs.getString("idProveedor") + "- " +  rs.getString("tipoProveedor") + " "+
						rs.getString("paisProveedor") + " " + rs.getString("idPersonaFK") + "\n";
			}
		}
		catch (SQLException sqle)
		{}
		return (contenido);
	}
	public ResultSet consultarProveedor(String seleccionado)
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar INSERT con Update
			rs = statement.executeQuery("SELECT * FROM proveedores WHERE idProveedor = " + seleccionado);
		}
		catch (SQLException sqle) {}
	return(rs);
	}
	public int actualizarProveedor(String idProveedor, String tipoProveedor, String paisProveedor)
	{
		int resultado = 0;
		String sentencia = "UPDATE proveedores SET tipoProveedor = '"+tipoProveedor+"' , paisProveedor='" + paisProveedor+"' WHERE idProveedor = " + idProveedor;
			
		try
				{
					//Crear la sentencia
					statement = connection.createStatement();
					//Ejecutar el Update
					statement.executeUpdate(sentencia);
				}
				catch(SQLException sqle)
				{
					resultado = -1; //Error
				}

		return(resultado);
		}
	public String consultarFecha()
	{
		Date fechaHora;
		SimpleDateFormat calendarioHora;
		fechaHora = new Date();
		calendarioHora = new SimpleDateFormat("dd/MM/yyyy H:mm");
		System.out.println(calendarioHora.format(fechaHora));
		return calendarioHora.format(fechaHora);
		}
	//Fichero EPE, donde se guardan los registros
	public String registroEscritura(String resultado)
	{
		String contenido = "";
		try
		{
			//Destino datos
			FileWriter fw = new FileWriter("FicheroEPE.txt", true);
			//Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			//Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			String calendarioHora;
			calendarioHora = consultarFecha();
			contenido= "["+calendarioHora+"]"+ resultado;
			salida.println(contenido);
			//Cerrar salida, fw y bw
			salida.close();
			bw.close();
			fw.close();
			salida.println("Información guardada en el fichero");
		
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return (contenido);
	}

}
