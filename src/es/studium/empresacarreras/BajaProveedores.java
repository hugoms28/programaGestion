package es.studium.empresacarreras;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BajaProveedores implements WindowListener, ActionListener {
	Frame ventana = new Frame("Baja de Proveedores");
	Label lblCabecera = new Label("Elegir el Proveedor a Borrar");
	Choice choProveedores = new Choice();
	Button btnBorrar = new Button("Borrar");
	
	
	
	
	Dialog dlgConfirmacion = new Dialog(ventana, "Confirmación", true);
	Label lblConfirmacion = new Label("XXXXXXXXXXXXXXXX");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	public BajaProveedores()
	{
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);
		dlgMensaje.addWindowListener(this);
		
		
		
		ventana.setSize(450,380);
		ventana.setResizable(false);
		ventana.setLayout(new FlowLayout());
		
		ventana.add(lblCabecera);
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
				choProveedores.add(rs.getInt("idProveedor")+"-"+rs.getString("tipoProveedor") + " " + rs.getString("paisProveedor")+ " "+ rs.getString("idPersonaFK"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Desconectar BD
		
		ventana.add(choProveedores);
		ventana.add(btnBorrar);
		
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
		}
		if(dlgConfirmacion.isActive())
		{
			dlgConfirmacion.setVisible(false);
		}
		else
		{
			ventana.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(btnBorrar))
		{
		//Mostrar el diálogo de confirmación
		dlgConfirmacion.addWindowListener(null);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		
		dlgConfirmacion.setSize(670,100);
		dlgConfirmacion.setResizable(false);
		
		dlgConfirmacion.setLayout(new FlowLayout());
		lblConfirmacion.setText("¿Seguro que desea borrar el proveedor" + choProveedores.getSelectedItem()+"?");
		dlgConfirmacion.add(lblConfirmacion);	
		dlgConfirmacion.add(btnSi);
		dlgConfirmacion.add(btnNo);
		
		dlgConfirmacion.setLocationRelativeTo(null);
		dlgConfirmacion.setVisible(true);
		
		}
		else if (evento.getSource().equals(btnNo))
		{
			dlgConfirmacion.setVisible(false);
		}
		else if (evento.getSource().equals(btnSi))
		{
			//Conectar
			bd.conectar();
			//Hacer el DELETE
			String[] array = choProveedores.getSelectedItem().split("-");
			int resultado = bd.borrarProveedor(Integer.parseInt(array[0]));
			if(resultado == 0)
			{
				lblMensaje.setText("Borrado con éxito");
				String usuario;
				usuario= Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[DELETE FROM proveedores]");
				System.out.println("["+usuario+"]"+"[DELETE FROM proveedores]");
			}
			else
			{
				lblMensaje.setText("Borrado Fallido");
			}
			//Desconectar
			bd.desconectar();
			
			dlgMensaje.addWindowListener(this);
			dlgMensaje.setSize(670,100);
			dlgMensaje.setResizable(false);
			
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.add(lblMensaje);	
			
			dlgConfirmacion.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
			dlgConfirmacion.setVisible(true);
		}
}
}
