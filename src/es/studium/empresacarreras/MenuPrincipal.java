package es.studium.empresacarreras;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuPrincipal implements WindowListener, ActionListener{

	Frame menuPrincipal = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();
	Menu mnuPersonas = new Menu("Personas");
	MenuItem mniAltaPersonas = new MenuItem("Nueva Persona");
	MenuItem mniConsultaPersonas = new MenuItem("Listado de Personas");
	MenuItem mniBajaPersonas = new MenuItem("Eliminar Persona");
	MenuItem mniModificacionPersonas = new MenuItem("Modificar Persona");
	
	Menu mnuEmpleados = new Menu("Empleados");
	MenuItem mniAltaEmpleados = new MenuItem("Nuevo Empleado");
	MenuItem mniConsultaEmpleados = new MenuItem("Listado de Empleados");
	MenuItem mniBajaEmpleados = new MenuItem("Eliminar Empleado");
	MenuItem mniModificacionEmpleados = new MenuItem("Modificar Empleado");
	
	Menu mnuProveedores = new Menu("Proveedores");
	MenuItem mniAltaProveedores = new MenuItem("Nuevo Proveedor");
	MenuItem mniConsultaProveedores = new MenuItem("Listado de Proveedores");
	MenuItem mniBajaProveedores = new MenuItem("Eliminar Proveedor");
	MenuItem mniModificacionProveedores = new MenuItem("Modificar Proveedor");
	
	Menu mnuTareas= new Menu("Tareas");
	MenuItem mniAltaTareas = new MenuItem("Nueva Tarea");
	MenuItem mniConsultaTareas = new MenuItem("Listado de Tareas");
	MenuItem mniBajaTareas = new MenuItem("Eliminar Tarea");
	MenuItem mniModificacionTareas = new MenuItem("Modificar Tarea");
	
	Label lblUsuario = new Label("XXXXXXXXXXXXXXX");
	
	public MenuPrincipal(int tipoUsuario)
	{
		menuPrincipal.addWindowListener(this);
		mniConsultaPersonas.addActionListener(this);
		mniAltaPersonas.addActionListener(this);
		mniBajaPersonas.addActionListener(this);
		mniModificacionPersonas.addActionListener(this);
		
		mniAltaEmpleados.addActionListener(this);
		mniBajaEmpleados.addActionListener(this);
		mniConsultaEmpleados.addActionListener(this);
		mniModificacionEmpleados.addActionListener(this);
		
		
		mniAltaProveedores.addActionListener(this);
		mniBajaProveedores.addActionListener(this);
		mniConsultaProveedores.addActionListener(this);
		mniModificacionProveedores.addActionListener(this);
		
		mniAltaTareas.addActionListener(this);
		mniBajaTareas.addActionListener(this);
		mniConsultaTareas.addActionListener(this);
		mniModificacionTareas.addActionListener(this);
		
		
		menuPrincipal.setSize(400, 220); // Medida de la ventana en pixeles Width y Height
		menuPrincipal.setResizable(false); //No permitir redimensionar
		
		menuPrincipal.setLayout(new FlowLayout()); 
		mnuTareas.add(mniAltaTareas);
		mnuTareas.add(mniConsultaTareas);
		if(tipoUsuario==1)
		{
			mnuTareas.add(mniBajaTareas);
			mnuTareas.add(mniModificacionTareas);
		}
		barraMenu.add(mnuTareas);
		
		//Personas es la unica tabla que contiene Baja y Modificación porque es la única sin FK
		mnuPersonas.add(mniAltaPersonas);
		mnuPersonas.add(mniConsultaPersonas);
		if(tipoUsuario==1)
		{
			mnuPersonas.add(mniBajaPersonas);
			mnuPersonas.add(mniModificacionPersonas);
		}
		barraMenu.add(mnuPersonas);
		
		mnuEmpleados.add(mniAltaEmpleados);
		mnuEmpleados.add(mniConsultaEmpleados);
		if(tipoUsuario==1)
		{
			mnuEmpleados.add(mniBajaEmpleados);
			mnuEmpleados.add(mniModificacionEmpleados);
		}
		barraMenu.add(mnuEmpleados);
		
		mnuProveedores.add(mniAltaProveedores);
		mnuProveedores.add(mniConsultaProveedores);
		if(tipoUsuario==1)
		{
			mnuProveedores.add(mniBajaProveedores);
			mnuProveedores.add(mniModificacionProveedores);
		}
		barraMenu.add(mnuProveedores);
		
		menuPrincipal.add(lblUsuario);
		menuPrincipal.setMenuBar(barraMenu);
		menuPrincipal.setLocationRelativeTo(null); //fijar que la ventana salga siempre en el medio
		menuPrincipal.setVisible(true); //Mostrarla
	
	}
	
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(mniConsultaPersonas))
	{
			new ConsultaPersonas();
	}
		else if(evento.getSource().equals(mniAltaPersonas))
		{
			new AltaPersonas();
		}
		else if (evento.getSource().equals(mniBajaPersonas)) 
		{
			new BajaPersonas();
		}
		else if (evento.getSource().equals(mniModificacionPersonas))
		{
			new ModificacionPersonas();
		}
		else if (evento.getSource().equals(mniAltaTareas))
		{
			new AltaTareas();
		}
		else if(evento.getSource().equals(mniAltaEmpleados))
		{
			new AltaEmpleados();
		}
		else if(evento.getSource().equals(mniAltaProveedores))
		{
			new AltaProveedores();
		}
		else if(evento.getSource().equals(mniBajaEmpleados))
		{
			new BajaEmpleados();
		}
		else if(evento.getSource().equals(mniBajaTareas))
		{
			new BajaTareas();
		}
		else if(evento.getSource().equals(mniBajaProveedores))
		{
			new BajaProveedores();
		}
		else if(evento.getSource().equals(mniConsultaTareas))
		{
			new ConsultaTareas();
		}
		else if(evento.getSource().equals(mniConsultaEmpleados))
		{
			new ConsultaEmpleados();
		}
		else if(evento.getSource().equals(mniConsultaProveedores))
		{
			new ConsultaProveedores();
		}
		else if(evento.getSource().equals(mniModificacionEmpleados))
		{
			new ModificacionEmpleados();
		}
		else if(evento.getSource().equals(mniModificacionTareas))
		{
			new ModificacionTareas();
		}
		else if(evento.getSource().equals(mniModificacionProveedores))
		{
			new ModificacionProveedores();
		}
	}
}
