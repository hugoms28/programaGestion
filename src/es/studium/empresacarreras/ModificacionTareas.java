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

public class ModificacionTareas implements WindowListener, ActionListener {

	Frame ventana = new Frame("Modificaci?n de Tarea");
	Label lblCabecera = new Label("Elegir Tarea...");
	Choice choTareas = new Choice();
	Button btnEditar = new Button("Editar");

	Dialog dlgEditar = new Dialog(ventana, "Edici?n Tarea", true);
	Label lblCabecera2 = new Label ("Editando tarea n? ");
	Label lblId = new Label("N? Tarea: ");
	Label lblDescripcion = new Label("Descripci?n: ");
	Label lblTipo = new Label("Tipo de tarea: ");
	TextField txtId = new TextField(20);
	TextField txtDescripcion = new TextField(20);
	TextField txtTipo = new TextField(20);
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");


	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;


	public ModificacionTareas(){
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
		rellenarChoiceTareas();
		//Desconectar BD
		bd.desconectar();
		ventana.add(choTareas);
		ventana.add(btnEditar);

		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);


	}
	private void rellenarChoiceTareas() {
		choTareas.removeAll();
		//Rellenar Choice
		choTareas.add("Elegir tarea...");
		//Conectar BD
		bd.conectar();
		//Sacar los datos de la tabla clientes
		rs=bd.rellenarTareas();
		//Meterlos en el choice registro a registro
		try {
			while(rs.next())
			{
				choTareas.add(rs.getInt("idTarea")+"-"+rs.getString("nombreTarea") + " " + rs.getString("tipoTarea")+ " " + rs.getString("idEmpleadoFK"));
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
			if(!choTareas.getSelectedItem().equals("Elegir Tarea...")) 
			{
				String[] seleccionado = choTareas.getSelectedItem().split("-");
				//Conectar BD
				bd.conectar();
				rs=bd.consultarTarea(seleccionado[0]);
				try
				{
					rs.next();
					txtId.setText(rs.getString("idTarea"));
					txtDescripcion.setText(rs.getString("nombreTarea"));
					txtTipo.setText(rs.getString("tipoTarea"));
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
				dlgEditar.add(lblDescripcion);
				dlgEditar.add(txtDescripcion);
				dlgEditar.add(lblTipo);
				dlgEditar.add(txtTipo);
				dlgEditar.add(btnCancelar);

				dlgEditar.add(btnModificar);

				dlgEditar.setLocationRelativeTo(null);
				dlgEditar.setVisible(true);
			}
		}
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar();
			int resultado =bd.actualizarTarea(txtId.getText(), txtDescripcion.getText(), txtTipo.getText());
			rellenarChoiceTareas();
			if(resultado == 0)
			{
				lblMensaje.setText("Modificaci?n confirmada");
			}
			else
			{
				lblMensaje.setText("Error en Modificaci?n");
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
