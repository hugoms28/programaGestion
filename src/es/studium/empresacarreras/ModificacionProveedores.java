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

public class ModificacionProveedores implements WindowListener, ActionListener {

	Frame ventana = new Frame("Modificación de Proveedor");
	Label lblCabecera = new Label("Elegir Proveedor...");
	Choice choProveedores = new Choice();
	Choice choPersonas = new Choice();
	Button btnEditar = new Button("Editar");

	Dialog dlgEditar = new Dialog(ventana, "Edición Proveedor", true);
	Label lblCabecera2 = new Label ("Editando proveedor nº ");
	Label lblId = new Label("Nº Proveedor: ");
	Label lblTipo = new Label("Tipo de Proveedor ");
	Label lblPais = new Label("País: ");
	TextField txtId = new TextField(20);
	TextField txtTipo = new TextField(20);
	TextField txtPais = new TextField(20);
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");


	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;


	public ModificacionProveedores(){
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
		rellenarChoiceProveedores();
		//Desconectar BD
		bd.desconectar();
		ventana.add(choProveedores);
		ventana.add(btnEditar);

		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
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
	private void rellenarChoiceProveedores() {
		choProveedores.removeAll();
		//Rellenar Choice
		choProveedores.add("Elegir proveedor...");
		//Conectar BD
		bd.conectar();
		//Sacar los datos de la tabla clientes
		rs=bd.rellenarProveedores();
		//Meterlos en el choice registro a registro
		try {
			while(rs.next())
			{
				choProveedores.add(rs.getInt("idProveedor")+"-"+rs.getString("tipoProveedor") + " " + rs.getString("paisProveedor")+ " " + rs.getString("idPersonaFK"));
			}
		}catch(SQLException e) {
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
			if(!choProveedores.getSelectedItem().equals("Elegir Proveedor...")) 
			{
				String[] seleccionado = choProveedores.getSelectedItem().split("-");
				//Conectar BD
				bd.conectar();
				rs=bd.consultarProveedor(seleccionado[0]);
				try
				{
					rs.next();
					txtId.setText(rs.getString("idProveedor"));
					txtTipo.setText(rs.getString("tipoProveedor"));
					txtTipo.setText(rs.getString("paisProveedor"));
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
				dlgEditar.add(lblTipo);
				dlgEditar.add(txtTipo);
				dlgEditar.add(lblPais);
				dlgEditar.add(txtPais);
				dlgEditar.add(btnCancelar);

				dlgEditar.add(btnModificar);

				dlgEditar.setLocationRelativeTo(null);
				dlgEditar.setVisible(true);
			}
		}
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar();
			int resultado =bd.actualizarTarea(txtId.getText(), txtTipo.getText(), txtTipo.getText());
			rellenarChoiceProveedores();
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
