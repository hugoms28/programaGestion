package es.studium.empresacarreras;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AltaPersonas implements WindowListener, ActionListener {

	Frame ventana = new Frame("Alta de Persona");
	Label lblNombre = new Label("Nombre:");
	Label lblApellidos = new Label("Apellidos:");
	Label lblEdad= new Label("Edad:");
	Label lblDomicilio = new Label("Domicilio:");
	Label lblEmail = new Label("Email:");
	Label lblTelefono = new Label("Teléfono");
	TextField txtNombre = new TextField(20);
	TextField txtApellidos = new TextField(20);
	TextField txtEdad = new TextField(20);
	TextField txtDomicilio = new TextField(20);
	TextField txtEmail = new TextField(20);
	TextField txtTelefono = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();

 
	public AltaPersonas()
	{	
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		
		
		ventana.setSize(300,380);
		ventana.setResizable(true);
		
		ventana.setLayout(new FlowLayout());
		
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		ventana.add(lblApellidos);
		ventana.add(txtApellidos);
		ventana.add(lblEdad);
		ventana.add(txtEdad);
		ventana.add(lblDomicilio);
		ventana.add(txtDomicilio);
		ventana.add(lblEmail);
		ventana.add(txtEmail);
		ventana.add(lblTelefono);
		ventana.add(txtTelefono);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
			limpiar();
		}
		else {
			ventana.setVisible(false);
		}
		
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(btnAceptar))
		{
			//Conectar
			bd.conectar();
			//Coger los datos del formulario
			String nombre = txtNombre.getText();
			String apellidos = txtApellidos.getText();
			String edad = txtEdad.getText();
			String domicilio = txtDomicilio.getText();
			String email = txtEmail.getText();
			String telefono = txtTelefono.getText();
			//Hacer INSERT con esos datos
			String sentencia = "INSERT INTO personas VALUES (null, '"+nombre+ "','"+apellidos+"','"+edad+"','"
			+domicilio+"','"+email+"','"+telefono+"');";
			int resultado = bd.insertarPersonas(sentencia);
			if(resultado == 0)
			{
				lblMensaje.setText("Alta confirmada");
				String usuario;
				usuario= Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[INSERT INTO personas]");
				System.out.println("["+usuario+"]"+"[INSERT INTO personas]");
			}
			else
			{
				lblMensaje.setText("Error en Alta");
			}
			//Desconectar
			bd.desconectar();
			dlgMensaje.setSize(290,75);
			dlgMensaje.setLayout(new FlowLayout());
			ventana.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}

		else if(evento.getSource().equals(btnLimpiar))
		{
			//Limpiar
			limpiar();
		}
	}
	private void limpiar() {
		txtNombre.setText("");
		txtApellidos.setText("");
		txtEdad.setText("");
		txtDomicilio.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtNombre.requestFocus();
	}
}
