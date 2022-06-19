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

public class ModificacionPersonas implements WindowListener, ActionListener {

	Frame ventana = new Frame("Modificación de Persona");
	Label lblCabecera = new Label("Elegir Persona...");
	Choice choPersonas = new Choice();
	Button btnEditar = new Button("Editar");

	Dialog dlgEditar = new Dialog(ventana, "Edición Persona", true);
	Label lblCabecera2 = new Label ("Editando la persona nº ");
	Label lblId = new Label("Nº Persona: ");
	Label lblNombre = new Label("Nombre:");
	Label lblApellidos = new Label("Apellidos:");
	Label lblEdad= new Label("Edad:");
	Label lblDomicilio = new Label("Domicilio:");
	Label lblEmail = new Label("Email:");
	Label lblTelefono = new Label("Teléfono");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtApellidos = new TextField(20);
	TextField txtEdad = new TextField(20);
	TextField txtDomicilio = new TextField(20);
	TextField txtEmail = new TextField(20);
	TextField txtTelefono = new TextField(20);
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");


	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;


	public ModificacionPersonas(){
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnModificar.addActionListener(this);
		btnCancelar.addActionListener(this);
		dlgEditar.addWindowListener(this);
		dlgMensaje.addWindowListener(this);

		ventana.setSize(450,480);
		ventana.setResizable(false);
		ventana.setLayout(new FlowLayout());

		ventana.add(lblCabecera);
		rellenarChoicePersonas();
		//Desconectar BD
		bd.desconectar();
		ventana.add(choPersonas);
		ventana.add(btnEditar);

		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);


	}
	private void rellenarChoicePersonas() {
		choPersonas.removeAll();
		//Rellenar Choice
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
	}
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else if (dlgEditar.isActive())
		{
			dlgEditar.setVisible(false);
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
		if (evento.getSource().equals(btnEditar))
		{
			if(!choPersonas.getSelectedItem().equals("Elegir Persona...")) 
			{
				String[] seleccionado = choPersonas.getSelectedItem().split("-");
				//Conectar BD
				bd.conectar();
				rs=bd.consultarPersona(seleccionado[0]);
				try
				{
					rs.next();
					txtNombre.setText(rs.getString("nombrePersona"));
					txtApellidos.setText(rs.getString("apellidosPersona"));
					txtEdad.setText(rs.getString("edadPersona"));
					txtDomicilio.setText(rs.getString("domicilioPersona"));
					txtEmail.setText(rs.getString("emailPersona"));
					txtTelefono.setText(rs.getString("telefonoPersona"));
				}
				catch(SQLException sqle) {}
				bd.desconectar();

				dlgEditar.setSize(550,550);
				dlgEditar.setResizable(false);
				dlgEditar.setLayout(new FlowLayout());

				dlgEditar.add(lblCabecera2);
				dlgEditar.add(lblId);
				txtId.setEnabled(false);
				txtId.setText(seleccionado[0]);
				dlgEditar.add(txtId);
				dlgEditar.add(lblNombre);
				dlgEditar.add(txtNombre);
				dlgEditar.add(lblApellidos);
				dlgEditar.add(txtApellidos);
				dlgEditar.add(lblEdad);
				dlgEditar.add(txtEdad);
				dlgEditar.add(lblDomicilio);
				dlgEditar.add(txtDomicilio);
				dlgEditar.add(lblEmail);
				dlgEditar.add(txtEmail);
				dlgEditar.add(lblTelefono);
				dlgEditar.add(txtTelefono);
				dlgEditar.add(btnCancelar);

				dlgEditar.add(btnModificar);

				dlgEditar.setLocationRelativeTo(null);
				dlgEditar.setVisible(true);
			}
		}
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar();
			int resultado =bd.actualizarPersona(txtId.getText(), txtNombre.getText(), txtApellidos.getText(),
					txtEdad.getText(), txtDomicilio.getText(), txtEmail.getText(), txtTelefono.getText());
			rellenarChoicePersonas();
			if(resultado == 0)
			{
				lblMensaje.setText("Modificación confirmada");
			}
			else
			{
				lblMensaje.setText("Error en Modificación");
			}
			bd.desconectar();


			dlgMensaje.setSize(290,75);
			dlgMensaje.setLayout(new FlowLayout());
			ventana.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
	}
}


