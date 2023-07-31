package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.DAO;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

public class Login extends JFrame {
	
	//objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	//objeto tela principal
	Principal principal = new Principal();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblNewLabel_2;
	private JLabel lblStatus;
	private JLabel lblData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/1173389_button_buttons_sewing_tailor icon_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setBounds(123, 38, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setBounds(123, 88, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(179, 35, 227, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(179, 85, 227, 20);
		contentPane.add(txtSenha);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		
		getRootPane().setDefaultButton(btnAcessar);
		
		btnAcessar.setBounds(317, 146, 89, 23);
		contentPane.add(btnAcessar);
		
		lblStatus = new JLabel("");
		lblStatus.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		lblStatus.setBounds(388, 213, 46, 48);
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setToolTipText("DBon");
		contentPane.add(lblStatus);
		
		lblData = new JLabel("New label");
		lblData.setForeground(SystemColor.text);
		lblData.setBounds(10, 236, 194, 14);
		contentPane.add(lblData);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(149, 17, 37));
		lblNewLabel_2.setBounds(0, 224, 434, 37);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/img/3005767_account_door_enter_login_icon (2).png")));
		lblNewLabel_3.setBounds(32, 38, 64, 64);
		contentPane.add(lblNewLabel_3);
		
	}//fim do construtor
	
	/**
	 * Método responsável por exibir o status da conexão
	 */
	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método status()
	
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
	
	/**
	 * metodo para autenticar um usuario
	 */
	private void logar() {
		//criar variavel para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());
		
		//valiadação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			//logica principal
			String read = "select * from Usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, txtSenha.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					//capturar o perfil
					//System.out.println(rs.getString(5)); //apoio a logica
					//tratamento do perfil do usuario
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						//logar
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));;
						//habilitar botoes
						principal.btnRelatorio.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						//mudar cor do rodapé
						principal.lblRodape.setBackground(Color.BLACK);
						//fechar tela de login
						this.dispose();
					} else { //perfil diferente de admin
						//logar
						principal.setVisible(true);
						//setar a label da tela principal com o nome de usuario
						principal.lblUsuario.setText(rs.getString(2));
						//fechar a tela de login
						this.dispose();
						
					}
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s)");
				}
				con.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
}//fim do código
