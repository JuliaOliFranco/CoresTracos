package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Clientes extends JDialog {
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtID;
	
	// Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;
		private JButton btnExcluir;
		private JButton btnEditar;
		private JButton btnAdicionar;
		private JTextField txtCep;
		private JTextField txtEndereco;
		private JTextField txtNumero;
		private JTextField txtComplemento;
		private JTextField txtBairro;
		private JTextField txtCidade;
		private JComboBox cboUf;
		private JScrollPane scrollPaneUsers;
		private JList listUsers;
		
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//clicar no painel do jdialog
				scrollPaneUsers.setVisible(false);
			}
		});
		getContentPane().setForeground(new Color(198, 210, 225));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/309041_users_group_people_icon (1).png")));
		setTitle("Cadastro Clientes");
		setBounds(100, 100, 596, 359);
		getContentPane().setLayout(null);
		
		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(104, 42, 243, 64);
		getContentPane().add(scrollPaneUsers);
		
		listUsers = new JList();
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioPelaLista();
			}
		});
		scrollPaneUsers.setViewportView(listUsers);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(104, 11, 46, 14);
		getContentPane().add(lblNome);
		
		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFone.setBounds(384, 11, 57, 14);
		getContentPane().add(lblFone);
		
		JLabel lblemail = new JLabel("E-mail:");
		lblemail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblemail.setBounds(31, 61, 126, 14);
		getContentPane().add(lblemail);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(104, 26, 243, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador (50));
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(384, 25, 116, 20);
		getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);
		txtTelefone.setDocument(new Validador (15));
		
		txtEmail = new JTextField();
		txtEmail.setBounds(32, 75, 211, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador (30));
		
		JButton btnLimpar = new JButton("");
		btnLimpar.setBounds(65, 252, 48, 48);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/8665346_eraser_icon.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(btnLimpar);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblID.setBounds(31, 11, 46, 14);
		getContentPane().add(lblID);
		
		txtID = new JTextField();
		txtID.setBounds(31, 26, 46, 20);
		txtID.setEditable(false);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		btnEditar = new JButton("");
		btnEditar.setBounds(343, 252, 48, 48);
		btnEditar.setEnabled(false);
		btnEditar.setToolTipText("Editar Cliente");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
		getContentPane().add(btnEditar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(480, 252, 48, 48);
		btnAdicionar.setToolTipText("Adicionar Cliente");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.setBounds(195, 252, 48, 48);
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirClientes();
			}
		});
		btnExcluir.setToolTipText("Excluir Cliente");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/3669378_clear_ic_icon (1).png")));
		getContentPane().add(btnExcluir);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCep.setBounds(306, 58, 46, 14);
		getContentPane().add(lblCep);
		
		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEndereco.setBounds(31, 117, 74, 14);
		getContentPane().add(lblEndereco);
		
		JLabel lblNumero = new JLabel("Nº:");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumero.setBounds(271, 119, 46, 14);
		getContentPane().add(lblNumero);
		
		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblComplemento.setBounds(362, 119, 104, 14);
		getContentPane().add(lblComplemento);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBairro.setBounds(31, 174, 102, 14);
		getContentPane().add(lblBairro);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCidade.setBounds(197, 174, 46, 14);
		getContentPane().add(lblCidade);
		
		JLabel lblUF = new JLabel("UF:");
		lblUF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUF.setBounds(402, 172, 46, 14);
		getContentPane().add(lblUF);
		
		txtCep = new JTextField();
		txtCep.setBounds(306, 72, 93, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(31, 134, 201, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(271, 136, 57, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(362, 136, 168, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(31, 189, 126, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(196, 189, 158, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);
		
		JButton btnBuscarCep = new JButton("");
		btnBuscarCep.setBounds(418, 58, 39, 37);
		btnBuscarCep.setDefaultCapable(false);
		btnBuscarCep.setBorderPainted(false);
		btnBuscarCep.setContentAreaFilled(false);
		btnBuscarCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/743893_search_find_glass_magnifier_magnifying_icon.png")));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 buscarCep();
			}
		});
		getContentPane().add(btnBuscarCep);
		
		cboUf = new JComboBox();
		cboUf.setBounds(402, 188, 70, 21);
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		getContentPane().add(cboUf);

	}//Fim do construtor
	
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtEmail.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtComplemento.setText(null);
		txtNumero.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		cboUf.setSelectedItem("");
		
	}

	
	private void editarContato() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o fone do contato");
			txtTelefone.requestFocus();
		}else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,"Digite o seu CEP");
		}else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o seu Número");
		}else {
			// Lógica principal
			// CRUD - Update
			String upadate = "update Clientes set nome=?, telefone=?, email=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where id=?";
			// tratamentos de exceçoes
			try {

				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)	
				pst = con.prepareStatement(upadate);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtTelefone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8,txtBairro.getText());
				pst.setString(9,txtCidade.getText());
				pst.setString(10, cboUf.getSelectedItem().toString());
				pst.setString(11, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Cliente editados com sucesso");
				// limpar campos
				limparCampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
		private void adicionar() {
			// System.out.println("teste");
			// Validação de campos obrigatóriios
			if (txtNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente");
				txtNome.requestFocus();
			}else if (txtTelefone.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente");
				txtTelefone.requestFocus();
			}else if(txtCep.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente");
			}else if(txtEndereco.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			}else if(txtNumero.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Número do Cliente");
			}else if(txtBairro.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o Nùmero do Cliente");
			}else if(txtCidade.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preenca a Cidade do Cliente");
			} else {
				
				// lógica pricipal
				// CRUD Creat
				String create = "insert into Clientes (nome,telefone,email,cep,endereco,numero,complemento,bairro,cidade,uf) value (?, ?, ?, ? ,? ,? , ?, ?, ?, ?)";
				// tratamento com exceções
				try {
					//abrir conexão 
					con = dao.conectar();
					//preparar a execução da query(instrução sql - CRUD Create)
					pst = con.prepareStatement(create);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtTelefone.getText());
					pst.setString(3, txtEmail.getText());
					pst.setString(4, txtCep.getText());
					pst.setString(5, txtEndereco.getText());
					pst.setString(6, txtNumero.getText());
					pst.setString(7, txtComplemento.getText());
					pst.setString(8,txtBairro.getText());
					pst.setString(9,txtCidade.getText());
					pst.setString(10, cboUf.getSelectedItem().toString());
					//executar a query(instruição sql (CRUD - Creat))
					pst.executeUpdate();
					//Confirmar
					JOptionPane.showMessageDialog(null, "Cliente adicionado");
					limparCampos();
					//fechar a conexão
				} catch (Exception e) {
					System.out.println(e);
				}
				}
		}
			// Método usado para excluir um contato

			private void excluirClientes() {
				// System.out.println("Teste do botão excluir");
				// validação de exclusão - a variável confima captura a opção escolhida

				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente ?", "Atenção !",
						JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_NO_OPTION) {
					//CRUD - Delete
					String delete = "delete from Clientes where id=?";
					//tratamento de exceções
					try {
						//abrir a conexão 
						con = dao.conectar();
						//preparar a query (instrução sql)
						pst = con.prepareStatement(delete);
						//substituir a ? pelo id do contato
						pst.setString(1, txtID.getText());
						//executar a query
						pst.executeUpdate();
						limparCampos();
						//exibir uma mensagem ao usuário
						JOptionPane.showMessageDialog(null, " Cliente excluido");
						//fechar a conexão 
						con.close();
					}catch (java.sql.SQLIntegrityConstraintViolationException e1) {
						JOptionPane.showMessageDialog(null, "Pedido não entregue.");
					} catch (Exception e2) {
						System.out.println(e2);
				}
}

	}// Fim do Método editar contato
			
			/**
			 * buscarCep
			 */
			private void buscarCep() {
				String logradouro = "";
				String tipoLogradouro = "";
				String resultado = null;
				String cep = txtCep.getText();
				try {
					URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
					SAXReader xml = new SAXReader();
					Document documento = xml.read(url);
					Element root = documento.getRootElement();
					for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
						Element element = it.next();
						if (element.getQualifiedName().equals("cidade")) {
							txtCidade.setText(element.getText());
						}
						if (element.getQualifiedName().equals("bairro")) {
							txtBairro.setText(element.getText());
						}
						if (element.getQualifiedName().equals("uf")) {
							cboUf.setSelectedItem(element.getText());
						}
						if (element.getQualifiedName().equals("tipo_logradouro")) {
							txtEndereco.setText(element.getText());
						}
						if (element.getQualifiedName().equals("logradouro")) {
							logradouro = element.getText();
						}
						if (element.getQualifiedName().equals("resultado")) {
							resultado = element.getText();
							if (resultado.equals("1")) {
								System.out.println("OK");
								} else {
									JOptionPane.showMessageDialog(null, "CEP não encontrado");
								}
							}
						}
						txtEndereco.setText(tipoLogradouro + " " + logradouro);
				} catch (Exception e) {
						System.out.println(e);
				}
			}
			
			/**
			 * 
			 */
			private void listarUsuarios() {
				// System.out.println("Teste");
				// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
				// obejto irá temporariamente armazenar os dados
				DefaultListModel<String> modelo = new DefaultListModel<>();
				//setar o model (vetor na lista)
				listUsers.setModel(modelo);
				// Query (instrução sql)
				String readLista = "select* from Clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
					String readListaUsuario = "select * from Clientes where nome like '" + txtNome.getText() + "%'"	+ "order by nome limit " + (linha) + " , 1";
					try {
						con = dao.conectar();
						pst = con.prepareStatement(readListaUsuario);
						rs = pst.executeQuery();
						if (rs.next()) {
							scrollPaneUsers.setVisible(false);
							txtID.setText(rs.getString(1));
							txtNome.setText(rs.getString(2));
							txtTelefone.setText(rs.getString(3));
							txtEmail.setText(rs.getString(4));
							txtCep.setText(rs.getString(5));
							txtEndereco.setText(rs.getString(6));
							txtNumero.setText(rs.getString(7));
							txtComplemento.setText(rs.getString(8));
							txtBairro.setText(rs.getString(9));
							txtCidade.setText(rs.getString(10));
							cboUf.setSelectedItem(rs.getString(11));
							btnAdicionar.setEnabled(false);
							btnEditar.setEnabled(true);
							btnExcluir.setEnabled(true);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				} else {
					//se nao existir no banco um usuario da lista
					scrollPaneUsers.setVisible(false);
					
				}
			}
}//Fim do Codigo