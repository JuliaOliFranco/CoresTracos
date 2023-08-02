package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private JPasswordField txtSenha;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JList listUsers;
	private final JScrollPane scrollPaneUsers = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usuarios");
		setBounds(100, 100, 435, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//clicar no painel do jdialog
				scrollPaneUsers.setVisible(false);
			}
		});
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(66, 101, 177, 22);
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
		lblNewLabel.setBounds(10, 27, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(10, 86, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(10, 55, 46, 14);
		contentPanel.add(lblNewLabel_2);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 114, 46, 14);
		contentPanel.add(lblSenha);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(66, 24, 86, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// pressionar uma tecla	
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// soltar uma tecla
				listarUsuarios();
			}
		});
		txtNome.setBounds(66, 83, 177, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBounds(66, 52, 177, 20);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		// uso do validador para limitar o número de caracteres
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
		btnNewButton_1.setBounds(47, 207, 48, 48);
		contentPanel.add(btnNewButton_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(66, 111, 177, 20);
		contentPanel.add(txtSenha);
		// uso do validador para limitar o número de caracteres
		txtSenha.setDocument(new Validador(8));

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
		btnAdicionar.setBounds(227, 207, 48, 48);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setEnabled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(316, 207, 48, 48);
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
		btnExcluir.setBounds(138, 207, 48, 48);
		contentPanel.add(btnExcluir);
		
		JComboBox cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"Admin", "User"}));
		cboPerfil.setBounds(301, 155, 63, 22);
		contentPanel.add(cboPerfil);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(145, 23, 35));
		lblNewLabel_3.setBounds(0, 202, 419, 59);
		contentPanel.add(lblNewLabel_3);
		
		JCheckBox chckSenha = new JCheckBox("Alterar senha");
		chckSenha.setBounds(25, 155, 97, 23);
		contentPanel.add(chckSenha);
	}//

	/**
	 * Limpar campos
	 */
	private void limparcampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtSenha.setText(null);
		txtLogin.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		scrollPaneUsers.setVisible(false);
	}// fim do método limpar campos()

	/**
	 * Método pra adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Senha do Usuário");
			txtSenha.requestFocus();
		} else {

			// lógica pricipal
			// CRUD Creat
			String create = "insert into Usuarios (nome,login,senha) values (?,?,md5(?))";
			// tratamento com exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				// executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				// Confirmar
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				// limpar campos
				limparcampos();
				// fechar a conexão

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void editarContato() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário");
			txtSenha.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update Usuarios set nome=?, login=?, senha = md5(?) where id=?";
			// tratamentos de exceçoes
			try {

				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");
				// limpar campos
				limparcampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	// Método usado para excluir um contato

	private void excluirUsuarios() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuarios ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			// CRUD - Delete
			String delete = "delete from Usuarios where id=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// limpar Campos
				limparcampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, " usuario excluido");
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelo = new DefaultListModel<>();
		//setar o model (vetor na lista)
		listUsers.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select* from Usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneUsers.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				//esconder a lista se nenhuma letra for digitada
				if(txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	/**
	 * metodo que busca o usuario selecionado na linha
	 */
	private void buscarUsuarioPelaLista() {
		//System.out.println("testar botao");
		//variavel que captura o indice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			//query (instrução sql
			//limit (0,1) -> seleciona o indice 0 e 1 usuario da lista
			String readListaUsuario = "select * from Usuarios where nome like '" + txtNome.getText() + "%'"	+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneUsers.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			//se nao existir no banco um usuario da lista
			scrollPaneUsers.setVisible(false);
		}
	}
}