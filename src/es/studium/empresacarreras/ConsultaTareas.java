package es.studium.empresacarreras;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ConsultaTareas implements WindowListener, ActionListener {
	
	Frame ventana = new Frame("Listado de Tareas");
	TextArea txaListado = new TextArea(6,34);
	Button btnPdf = new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();
	
	public static final String DEST = "ConsultaTareas.pdf";
	PdfFont font;
	PdfWriter writer;
	//Conexiones a la base de datos
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	public ConsultaTareas()
	{
		ventana.addWindowListener(this);
		
		ventana.setSize(400, 220); // Medida de la ventana en pixeles Width y Height
		ventana.setResizable(false); //No permitir redimensionar
		
		connection = bd.conectar2();
		btnPdf.addActionListener(this);

		ventana.setLayout(new FlowLayout()); 
		//Rellenar textArea con la información de la BD
		//Conectar
		bd.conectar();
		//Sacar la información y meterla en el TextArea
		txaListado.setText(bd.consultarTareas());
		//Desconectar
		bd.desconectar();
		String usuario;
		usuario= Login.txtUsuario.getText();
		bd.registroEscritura("["+usuario+"]"+"[SELECT * from tareas]");
		System.out.println("["+usuario+"]"+"[SELECT * from tareas]");
		ventana.add(txaListado);
		ventana.add(btnPdf);
		ventana.setLocationRelativeTo(null); //fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); //Mostrarla
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		ventana.setVisible(false);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento) 
	{if(evento.getSource().equals(btnPdf))
	{
		
		//Initialize PDF writer
		try
		{
			writer = new PdfWriter(DEST);
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		// Initialize document
		Document document = new Document(pdf);
		//Add paragraph to the document
		document.add(new Paragraph("Tareas:"));
		// Create a PdfFont
		try
		{
			font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		} catch (java.io.IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			
			String sentencia;
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido 
			// y ejecutar la sentencia SQL 
			sentencia = "SELECT * FROM tareas join empleados on idEmpleado = idEmpleadoFK;";
			resultSet = statement.executeQuery(sentencia);
			// Mostramos en choice los clientes de la empresa
			while(resultSet.next())
			{
				//Guardamos en documento PDF
				document.add(new Paragraph((resultSet.getInt("idTarea")+
						"-"+resultSet.getString("nombreTarea") +
						"-" + resultSet.getString("tipoTarea")+
						resultSet.getInt("idEmpleadoFK") +
						"\n")));
			}
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Close document
		document.close();
		// Open the new PDF document just created
		try
		{
			Desktop.getDesktop().open(new File(DEST));
		} catch (java.io.IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	//Guardamos los registros cuando pulsen PDF
	bd.desconectar();}
}