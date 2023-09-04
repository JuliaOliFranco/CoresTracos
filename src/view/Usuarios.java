package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.DAO;
import utils.Validador;
import java.awt.Font;

public class Usuarios extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JList listUsers;
	private final JScrollPane scrollPaneUsers = new JScrollPane();
	private JComboBox cboPerfil;
	private JPasswordField txtSenha2;
	private JCheckBox chckSenha;

	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usuarios");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}
		});
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(309, 205, 238, 35);
		contentPanel.add(scrollPaneUsers);
		
				listUsers = new JList();
				scrollPaneUsers.setViewportView(listUsers);
				listUsers.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarUsuarioPelaLista();
					}
				});

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(228, 132, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(228, 180, 46, 22);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(228, 233, 46, 22);
		contentPanel.add(lblNewLabel_2);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(228, 290, 46, 17);
		contentPanel.add(lblSenha);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(309, 127, 86, 29);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(309, 179, 238, 29);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBounds(310, 232, 237, 29);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparcampos();
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setIcon(
				new ImageIcon(Usuarios.class.getResource("/img/8665346_eraser_icon.png")));
		btnNewButton_1.setToolTipText("Apagar");
		btnNewButton_1.setBounds(75, 502, 48, 48);
		contentPanel.add(btnNewButton_1);

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
		btnAdicionar.setBounds(454, 502, 48, 48);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setEnabled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuario();

				} else {
					editarUsuarioExcetosenha();

				}
			}
		});
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(644, 502, 48, 48);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setEnabled(false);
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuarios();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/3669378_clear_ic_icon (1).png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(256, 502, 48, 48);
		contentPanel.add(btnExcluir);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"Admin", "User"}));
		cboPerfil.setBounds(461, 127, 86, 29);
		contentPanel.add(cboPerfil);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(145, 23, 35));
		lblNewLabel_3.setBounds(0, 484, 784, 77);
		contentPanel.add(lblNewLabel_3);
		
		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					txtSenha2.setText(null);
					txtSenha2.requestFocus();
					txtSenha2.setBackground(Color.yellow);
				} else {
					txtSenha2.setBackground(Color.white);
				}
			}
		});
		chckSenha.setBounds(565, 403, 127, 23);
		contentPanel.add(chckSenha);
		
		txtSenha2 = new JPasswordField();
		txtSenha2.setBounds(309, 286, 238, 29);
		contentPanel.add(txtSenha2);
	}
	
	private void limparcampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtSenha2.setText(null);
		txtLogin.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		scrollPaneUsers.setVisible(false);
	}

	
	private void adicionar() {
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Senha do Usuário");
			txtSenha2.requestFocus();
		} else {

			String create = "insert into usuarios (nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha2.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
		
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				limparcampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void editarUsuario() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário");
			txtSenha2.requestFocus();
		} else {

			String update = "update usuarios set nome=?, login=?, senha = md5(?), perfil=? where id=?";
			
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha2.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");
				limparcampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void editarUsuarioExcetosenha() {
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário");
			txtSenha2.requestFocus();
		} else {

			String update = "update usuarios set nome=?, login=?, perfil = ? where id=?";
			
			try {

				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");
				limparcampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void excluirUsuarios() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuarios ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			String delete = "delete from usuarios where id=?";
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				
				pst.executeUpdate();
				limparcampos();
				JOptionPane.showMessageDialog(null, " usuario excluido");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listUsers.setModel(modelo);
		String readLista = "select* from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			while (rs.next()) {
				scrollPaneUsers.setVisible(true);
				modelo.addElement(rs.getString(2));
				if(txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	private void buscarUsuarioPelaLista() {
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"	+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneUsers.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha2.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					
					
				}
			} catch (Exception e) {
				System.out.println(e);
				
				btnAdicionar.setEnabled(true);
			}
		} else {
			scrollPaneUsers.setVisible(false);
			
	
			
		}
	}
}