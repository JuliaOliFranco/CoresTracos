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
import java.awt.Font;

public class Login extends JFrame {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
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
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(318, 176, 70, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(318, 226, 70, 26);
		contentPane.add(lblNewLabel_1);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(398, 174, 261, 28);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(398, 224, 261, 28);
		contentPane.add(txtSenha);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		
		getRootPane().setDefaultButton(btnAcessar);
		
		btnAcessar.setBounds(539, 320, 107, 33);
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
		lblStatus.setBounds(732, 508, 46, 48);
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setToolTipText("DBon");
		contentPane.add(lblStatus);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setForeground(SystemColor.text);
		lblData.setBounds(10, 530, 194, 20);
		contentPane.add(lblData);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(149, 17, 37));
		lblNewLabel_2.setBounds(0, 484, 784, 77);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/img/3005767_account_door_enter_login_icon.png")));
		lblNewLabel_3.setBounds(100, 167, 128, 128);
		contentPane.add(lblNewLabel_3);
		
	}
	
	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private void setarData() {
		Date date = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

	}
	
	private void logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, txtSenha.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));;
						principal.btnRelatorio.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.lblRodape.setBackground(Color.BLACK);
						this.dispose();
					} else { 
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
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
	
}
