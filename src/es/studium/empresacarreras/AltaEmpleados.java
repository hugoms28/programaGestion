package es.studium.empresacarreras;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AltaEmpleados implements WindowListener, ActionListener {
	Frame ventana = new Frame("Alta de Empleado");
	Label lblSalario = new Label("Salario");
	Choice choPersonas = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar= new Button("Limpiar");
	TextField txtSalario = new TextField(20);
	
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	
public AltaEmpleados() {
		
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		
		
		
		ventana.setSize(450,380);
		ventana.setResizable(true);
		
		ventana.setLayout(new FlowLayout());
		
		
		ventana.add(lblSalario);
		ventana.add(txtSalario);
		
		choPersonas.add("Elegir persona...");
		//Conectar BD
		bd.conectar();
		//Sacar los datos de la tabla clientes
		rs=bd.rellenarPersonas();
		//Meterlos en el choice registro a registro
		try {
			while(rs.next())
			{
				choPersonas.add(rs.getInt("idPersona")+"-"+rs.getString("nombrePersona") + " " + rs.getString("apellidosPersona")+" "+
				rs.getInt("edadPersona") +" "+ rs.getString("domicilioPersona") +" " + rs.getString("emailPersona")+ " "+
						rs.getInt("telefonoPersona"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Desconectar BD
		
		ventana.add(choPersonas);
		ventana.add(btnAceptar);
		
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
}
	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(btnAceptar))
		{
		
			{
				String salario = txtSalario.getText();
				String[] seleccionado = choPersonas.getSelectedItem().split("-");
				int idPersonaFK = Integer.parseInt(seleccionado[0]);
				// Hacer el insert
				String sentencia = "INSERT INTO empleados VALUES (null, '" ;
				sentencia+=  salario + "'," + "'" + idPersonaFK + "'" +");";
				System.out.println(sentencia);
				if((bd.insertarEmpleados( sentencia))==0) 
				{
					// Todo bien
					lblMensaje.setText("Alta confirmada");	
					ventana.setVisible(false);	
					String usuario;
					usuario= Login.txtUsuario.getText();
					bd.registroEscritura("["+usuario+"]"+"[INSERT INTO empleados]");
					System.out.println("["+usuario+"]"+"[INSERT INTO empleados]");
				}
				else 
				{
					// si no sale mensaje de error
					lblMensaje.setText("Error en Alta");	
				}
			}
			//Desconectamos base de datos
			bd.desconectar();
			dlgMensaje.setSize(290,75);
			dlgMensaje.setLayout(new FlowLayout());
			ventana.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
	}
			
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
	
		else
		{
			ventana.setVisible(false);
		}
	}
	public void windowClosed(WindowEvent e) {
	
	}
	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {

	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {

	}
	private void limpiar() {
		txtSalario.setText("");
	}

}

