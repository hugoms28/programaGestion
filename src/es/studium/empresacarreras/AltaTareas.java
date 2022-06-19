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
import java.sql.ResultSet;
import java.sql.SQLException;

public class AltaTareas implements WindowListener, ActionListener {
	Frame ventana = new Frame("Alta de Tarea");
	Label lblDescripcion = new Label("Descripción:");
	Label lblTipo = new Label("Tipo de Tarea:");
	Choice choEmpleados = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar= new Button("Limpiar");
	TextField txtDescripcion = new TextField(20);
	TextField txtTipo = new TextField(20);
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	public AltaTareas() {
		
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		
		
		
		ventana.setSize(450,380);
		ventana.setResizable(true);
		
		ventana.setLayout(new FlowLayout());
		
		
		ventana.add(lblDescripcion);
		ventana.add(txtDescripcion);
		ventana.add(lblTipo);
		ventana.add(txtTipo);
		
		
		choEmpleados.add("Elegir persona...");
		//Conectar BD
		bd.conectar();
		//Sacar los datos de la tabla clientes
		rs=bd.rellenarEmpleados();
		//Meterlos en el choice registro a registro
		try {
			while(rs.next())
			{
				choEmpleados.add(rs.getInt("idEmpleado")+"-"+rs.getString("salarioEmpleado") + " " + rs.getString("idPersonaFK"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Desconectar BD
		ventana.add(choEmpleados);
		ventana.add(btnAceptar);
		

		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(btnAceptar))
		{
		
			{
				String descripcion = txtDescripcion.getText();
				String tipo = txtTipo.getText();
				String[] seleccionado = choEmpleados.getSelectedItem().split("-");
				int idEmpleadoFK = Integer.parseInt(seleccionado[0]);
				// Hacer el insert
				String sentencia = "INSERT INTO tareas VALUES (null, '" ;
				sentencia+=  descripcion + "','" + tipo + "', " + "'" + idEmpleadoFK + "'" +");";
				if((bd.insertarTareas( sentencia))==0) 
				{
					// Todo bien
					lblMensaje.setText("Alta confirmada");
					ventana.setVisible(false);	
					String usuario;
					usuario= Login.txtUsuario.getText();
					bd.registroEscritura("["+usuario+"]"+"[INSERT INTO tareas]");
					System.out.println("["+usuario+"]"+"[INSERT INTO tareas]");
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
		txtDescripcion.setText("");
		txtTipo.setText("");
	}

}
