package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.DAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class Principal extends JFrame {
	
	//Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	
	public JLabel lblUsuario;
	public JButton btnRelatorio;
	public JButton btnUsuarios;
	public JLabel lblRodape;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	/**
	 * Create the frame.
	 */
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}

			private void status() {
				try {
					//abrir a conexão 
					con = dao.conectar();
					if (con == null) {
						//System.out.println("Erro de conexão");
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dboff.png")));
					} else {
						//System.out.println("Banco conectado");
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dbon.png")));
					}
					// NUNCA esquecer de fechar a conexão 
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}//Fim do método status()
			
				/**
				 * Método responsável por setar a data do rodapé 
				 */
				private void setarData() {
					Date date = new Date();
					//Criar objeto para formatar a data
					DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
					//alterar o texto da label pela data atual formatada
					lblData.setText(formatador.format(date));
		
				}	
				
		});
		setTitle("Sistema - Cores&Traços");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/1173389_button_buttons_sewing_tailor icon_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("");
		lblStatus.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
			
		});
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(60, 387, 135, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNewLabel_3 = new JLabel("Usuário:");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(9, 387, 61, 14);
		contentPane.add(lblNewLabel_3);
		lblStatus.setBounds(576, 393, 48, 48);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		lblStatus.setToolTipText("DBon");
		contentPane.add(lblStatus);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//abrir a tela de Usuaríos
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/309035_user_account_human_person_icon.png")));
		btnUsuarios.setToolTipText("Usuaríos");
		btnUsuarios.setBounds(23, 33, 99, 88);
		contentPane.add(btnUsuarios);
		
		JButton bntSobre = new JButton("");
		bntSobre.setContentAreaFilled(false);
		bntSobre.setBorder(null);
		bntSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);

			}
		});
		bntSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		bntSobre.setToolTipText("Sobre");
		bntSobre.setBounds(547, 11, 48, 48);
		contentPane.add(bntSobre);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/186269_measure_mannequin_icon.png")));
		lblNewLabel.setBounds(483, 213, 112, 138);
		contentPane.add(lblNewLabel);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setForeground(SystemColor.text);
		lblData.setBackground(new Color(128, 255, 0));
		lblData.setBounds(10, 404, 274, 26);
		contentPane.add(lblData);
		
		lblRodape = new JLabel("");
		lblRodape.setOpaque(true);
		lblRodape.setBackground(new Color(121, 11, 38));
		lblRodape.setBounds(0, 373, 624, 68);
		contentPane.add(lblRodape);
		
		JButton btnCliente = new JButton("");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnCliente.setContentAreaFilled(false);
		btnCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliente.setIcon(new ImageIcon(Principal.class.getResource("/img/309041_users_group_people_icon (1).png")));
		btnCliente.setBounds(201, 33, 99, 88);
		contentPane.add(btnCliente);
		
		JButton btnOS = new JButton("");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnOS.setContentAreaFilled(false);
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setToolTipText("Ordem de Serviço (OS)");
		btnOS.setIcon(new ImageIcon(Principal.class.getResource("/img/9859783_craft_sewing_machine_tailor_tailoring_icon.png")));
		btnOS.setBounds(72, 190, 99, 88);
		contentPane.add(btnOS);
		
		btnRelatorio = new JButton("");
		btnRelatorio.setEnabled(false);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setToolTipText("Relatório");
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/2931174_clipboard_copy_paste_analysis_report_icon (1).png")));
		btnRelatorio.setBounds(248, 190, 99, 88);
		contentPane.add(btnRelatorio);
		
		JLabel lblNewLabel_2 = new JLabel("Cores&Traços");
		lblNewLabel_2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_2.setBounds(493, 350, 121, 20);
		contentPane.add(lblNewLabel_2);
	}
}

