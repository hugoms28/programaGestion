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

public class Login implements WindowListener, ActionListener {
	//Atributos, Componentes, Campos, ...
		//Diseño de una ventana
		Frame ventana = new Frame("Login"); //Crear ventana
		Label lblUsuario = new Label("Usuario");
		Label lblClave = new Label("Clave");
		public static TextField txtUsuario = new TextField(15);
		public TextField txtClave = new TextField(15); 
		Button btnAceptar = new Button("Aceptar");
		Button btnCancelar = new Button("Cancelar");
		
		Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
		Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXX");
		

	
	public Login() {
		//Listener
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		ventana.setSize(230, 150); // Medida de la ventana en pixeles Width y Height
		ventana.setResizable(false); //No permitir redimensionar

		ventana.setLayout(new FlowLayout()); 
		ventana.add(lblUsuario);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		txtClave.setEchoChar('*'); //Se sustituyen los caracteres por * en Clave
		ventana.add(txtClave);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		
		ventana.setLocationRelativeTo(null); //fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); //Mostrarla
	}

	public static void main(String[] args) {
		
		new Login();
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(btnCancelar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
		else if(ae.getSource().equals(btnAceptar))
		{
			String usuario = txtUsuario.getText(); //Saber lo que escribe el usuario en la ventana
			String clave = txtClave.getText();
			BaseDatos bd =new BaseDatos();
			bd.conectar();
		int resultado =bd.consultar("SELECT * FROM usuarios WHERE nombreUsuario = '"+usuario+"' AND claveUsuario = SHA2('"+clave+"',256);");
			//Caso Negativo: Ventana Error
			if (resultado==-1)
			{
				dlgMensaje.setSize(290,75);
				dlgMensaje.setLayout(new FlowLayout());
				ventana.setResizable(false);
				lblMensaje.setText("Nombre de usuario y/o contraseña incorrectas");
				dlgMensaje.add(lblMensaje);
				dlgMensaje.setLocationRelativeTo(null);
				dlgMensaje.setVisible(true);
				bd.desconectar();
			}
			else 
			{
				//Caso Afirmativo: Menú Principal
				new MenuPrincipal(resultado);
				ventana.setVisible(false);
				String usuario1;
				usuario1= Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario1+"]"+"[LOGIN]");
				System.out.println("["+usuario1+"]"+"[SELECT * from empleados]");
				
			}
			bd.desconectar();
			
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
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
}
