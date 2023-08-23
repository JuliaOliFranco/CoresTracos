package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Cursor;

public class Fornecedor extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtFone;
	private JTextField txtEmail;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtComplemento;
	private JTextField txtCidade;
	private JTextField txtNumero;
	private JComboBox cboUF;
	private JTextField txtBairro;
	private JButton btnBuscarCep;
	private JScrollPane scrollPaneFornecedor;
	private JList listFornecedor;
	private JTextField txtRazao;
	private JTextField txtCNPJ;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JTextField txtFantasia;
	private JTextField txtIE;
	private JTextField txtSite;
	private JTextField txtVendedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Fornecedor dialog = new Fornecedor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Fornecedor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedor.class.getResource("/img/309041_users_group_people_icon (1).png")));
		setTitle("Fornecedor");
		setBounds(100, 100, 576, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneFornecedor.setVisible(false);
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		scrollPaneFornecedor = new JScrollPane();
		scrollPaneFornecedor.setVisible(false);
		scrollPaneFornecedor.setBounds(137, 50, 240, 30);
		contentPanel.add(scrollPaneFornecedor);
		
		listFornecedor = new JList();
		scrollPaneFornecedor.setViewportView(listFornecedor);
		listFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedor();
			}
		});
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar Fornecedor");
		btnAdicionar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}
		});
		btnAdicionar.setBounds(62, 302, 48, 48);
		contentPanel.add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setEnabled(false);
		btnExcluir.setToolTipText("Excluir Fornecedor");
		btnExcluir.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/3669378_clear_ic_icon (1).png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		btnExcluir.setBounds(184, 302, 48, 48);
		contentPanel.add(btnExcluir);
		
		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setEnabled(false);
		btnEditar.setToolTipText("Editar Fornecedor");
		btnEditar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFornecedor();
			}
		});
		btnEditar.setBounds(308, 302, 48, 48);
		contentPanel.add(btnEditar);
		
		btnLimpar = new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/8665346_eraser_icon.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(426, 302, 48, 48);
		contentPanel.add(btnLimpar);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(27, 17, 37, 14);
		contentPanel.add(lblID);
		
		JLabel lblRazao = new JLabel("Razão Social:");
		lblRazao.setBounds(137, 17, 90, 14);
		contentPanel.add(lblRazao);
		
		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setBounds(27, 62, 62, 14);
		contentPanel.add(lblFone);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(147, 62, 68, 14);
		contentPanel.add(lblEmail);
		
		JLabel lblCNPJ = new JLabel("CNPJ:");
		lblCNPJ.setBounds(408, 17, 46, 14);
		contentPanel.add(lblCNPJ);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(348, 62, 46, 14);
		contentPanel.add(lblCep);
		
		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(27, 114, 62, 14);
		contentPanel.add(lblEndereco);
		
		JLabel lblNumero = new JLabel("Nº:");
		lblNumero.setBounds(276, 114, 46, 14);
		contentPanel.add(lblNumero);
		
		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(348, 114, 95, 14);
		contentPanel.add(lblComplemento);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(27, 162, 62, 14);
		contentPanel.add(lblBairro);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(182, 162, 95, 14);
		contentPanel.add(lblCidade);
		
		JLabel lblUF = new JLabel("UF:");
		lblUF.setBounds(358, 162, 46, 14);
		contentPanel.add(lblUF);
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setBounds(358, 176, 62, 23);
		contentPanel.add(cboUF);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtID.setEditable(false);
		txtID.setBounds(27, 31, 52, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);
		
		txtFone = new JTextField();
		txtFone.setDocument(new Validador(16));
		txtFone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtFone.setBounds(27, 76, 103, 18);
		contentPanel.add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(15));
		
		txtEmail = new JTextField();
		txtEmail.setDocument(new Validador(50));
		txtEmail.setBounds(147, 76, 179, 20);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(30));
		
		txtCep = new JTextField();
		txtCep.setDocument(new Validador(10));
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtCep.setBounds(348, 75, 86, 20);
		contentPanel.add(txtCep);
		txtCep.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(27, 129, 223, 20);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(348, 127, 171, 20);
		contentPanel.add(txtComplemento);
		txtComplemento.setColumns(10);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(27, 176, 132, 20);
		contentPanel.add(txtBairro);
		txtBairro.setColumns(10);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(182, 177, 134, 20);
		contentPanel.add(txtCidade);
		
		txtNumero = new JTextField();
		txtNumero.setDocument(new Validador(5));
		txtNumero.addKeyListener(new KeyAdapter() {
			
		});
		txtNumero.setBounds(276, 129, 49, 20);
		contentPanel.add(txtNumero);
		txtNumero.setColumns(10);
		
		btnBuscarCep = new JButton("");
		btnBuscarCep.setBorderPainted(false);
		btnBuscarCep.setContentAreaFilled(false);
		btnBuscarCep.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/743893_search_find_glass_magnifier_magnifying_icon.png")));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(460, 62, 40, 40);
		contentPanel.add(btnBuscarCep);
		
		getRootPane().setDefaultButton(btnBuscarCep);
		
		txtRazao = new JTextField();
		txtRazao.setDocument(new Validador(50));
		txtRazao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtRazao.setBounds(137, 32, 240, 19);
		contentPanel.add(txtRazao);
		txtRazao.setColumns(10);
		txtRazao.setDocument(new Validador(50));
		
		txtCNPJ = new JTextField();
		txtCNPJ.setDocument(new Validador(20));
		txtCNPJ.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.-";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtCNPJ.setBounds(408, 32, 111, 20);
		contentPanel.add(txtCNPJ);
		txtCNPJ.setColumns(10);
		txtCNPJ.setDocument(new Validador(15));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(125, 0, 34));
		lblNewLabel.setBounds(0, 292, 560, 69);
		contentPanel.add(lblNewLabel);
		
		JLabel lblFantasia = new JLabel("Nome Fantasia:");
		lblFantasia.setBounds(27, 208, 90, 14);
		contentPanel.add(lblFantasia);
		
		txtFantasia = new JTextField();
		txtFantasia.setBounds(27, 224, 132, 20);
		contentPanel.add(txtFantasia);
		txtFantasia.setColumns(10);
		txtFantasia.setDocument(new Validador(50));
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setBounds(186, 207, 113, 14);
		contentPanel.add(lblVendedor);
		
		txtVendedor = new JTextField();
		txtVendedor.setBounds(183, 223, 143, 20);
		contentPanel.add(txtVendedor);
		txtVendedor.setColumns(10);
		txtVendedor.setDocument(new Validador(20));
		
		JLabel lblIE = new JLabel("Inscrição Estadual:");
		lblIE.setBounds(444, 165, 110, 14);
		contentPanel.add(lblIE);
		
		txtIE = new JTextField();
		txtIE.setBounds(443, 179, 90, 20);
		contentPanel.add(txtIE);
		txtIE.setColumns(10);
		txtIE.setDocument(new Validador(20));
		
		JLabel lblSite = new JLabel("Site:");
		lblSite.setBounds(358, 210, 48, 14);
		contentPanel.add(lblSite);
		
		txtSite = new JTextField();
		txtSite.setBounds(358, 225, 175, 20);
		contentPanel.add(txtSite);
		txtSite.setColumns(10);
		txtSite.setDocument(new Validador(50));
	}
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
					cboUF.setSelectedItem(element.getText());
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
	 * Método usado para buscar usuário pela lista
	 */
	private void buscarFornecedor() {
		// System.out.println("teste");
		// variável que captura o indice da linha da lista
		int linha = listFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
			String readListafornecedor =  "select * from fornecedor where razao like '" + txtRazao.getText() + "%'" + "order by razao";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListafornecedor);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneFornecedor.setVisible(false);
					// setar campos
					txtID.setText(rs.getString(1));
					txtRazao.setText(rs.getString(2));
					txtFantasia.setText(rs.getString(3));
					txtFone.setText(rs.getString(4));
					txtVendedor.setText(rs.getString(5));
					txtEmail.setText(rs.getString(6));
					txtSite.setText(rs.getString(7));
					txtCNPJ.setText(rs.getString(8));
					txtIE.setText(rs.getString(9));
					txtCep.setText(rs.getString(10));	
					txtEndereco.setText(rs.getString(11));
					txtNumero.setText(rs.getString(12));
					txtComplemento.setText(rs.getString(13));	
					txtBairro.setText(rs.getString(14));		
					txtCidade.setText(rs.getString(15));
					cboUF.setSelectedItem(rs.getString(16));
					
					
					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);	
					
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPaneFornecedor.setVisible(false);
		}
		
	}
	/**
	 * METODO LIMPAR CAMPOS
	 */
	private void limparCampos() {
		txtBairro.setText(null);
		txtCep.setText(null);
		txtCidade.setText(null);
		txtID.setText(null);
		txtCNPJ.setText(null);
		txtComplemento.setText(null);
		txtEmail.setText(null);	
		txtFone.setText(null);		
		txtRazao.setText(null);		
		txtNumero.setText(null);	
		txtEndereco.setText(null);		
		cboUF.setSelectedItem("");
		txtIE.setText(null);
		txtFantasia.setText(null);
		txtSite.setText(null);
		txtVendedor.setText(null);
		
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);

	}
	private void listarFornecedor() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o model (vetor na lista)
		listFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select* from fornecedor where razao like '" + txtRazao.getText() + "%'" + "order by razao";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneFornecedor.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder a lista se nenhuma letra for digitada
				if (txtRazao.getText().isEmpty()) {
					scrollPaneFornecedor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void Adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if  (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Razão Social do fornecedor");
			txtRazao.requestFocus();
		}else if(txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone do fornecedor");
			txtFone.requestFocus();
		}else if(txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o e-mail do fornecedor");
			txtEmail.requestFocus();
		}else if(txtCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor");
			txtCNPJ.requestFocus();
		}else if(txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do fornecedor");
			txtCep.requestFocus();
		}else if(txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do endereço do fornecedor");
			txtNumero.requestFocus();
		}else if(txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do endereço do fornecedor");
			txtFantasia.requestFocus();
		} else {
			
			// lógica pricipal
			// CRUD Creat
			String create = "insert into fornecedor (razao,fantasia,fone,vendedor,email,site,cnpj,ie,cep,endereco,numero,bairro,cidade,uf) value (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento com exceções
			try {
				//abrir conexão 
				con = dao.conectar();
				//preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCep.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtBairro.getText());
				pst.setString(13, txtCidade.getText());
				pst.setString(14, cboUF.getSelectedItem().toString());
				
				//executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				//Confirmar
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado!");  
				limparCampos();
				//fechar a conexão
			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CNPJ já está sendo utilizado.");
				txtCNPJ.setText(null);
				txtCNPJ.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
		}
	/*
	 * 
	 */
	private void excluirFornecedor() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			// CRUD - Delete
			String delete = "delete from fornecedor where razao=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtRazao.getText());
				// executar a query
				pst.executeUpdate();
				// limpar Campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, " Fornecedor excluido");
				// fechar a conexão
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não excluido. \nEste Fornecedor ainda tem um produto cadastrado");
			} catch (Exception e2) {
				System.out.println(e2);
			}
			limparCampos();
		}
	}
	
	private void editarFornecedor() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Razão Social do fornecedor");
			txtEmail.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o telefone do fornecedor");
			txtFone.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CEP do fornecedor");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtNumero.requestFocus();
		} else if (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtRazao.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtFantasia.requestFocus();
		} else if (txtIE.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtIE.requestFocus();
		} else if (txtSite.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtSite.requestFocus();
		} else if (txtVendedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o numero do endereço");
			txtVendedor.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update fornecedor set razao=?, fantasia=?, fone=?, vendedor=?, email=?, site=?, cnpj=? , ie=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where idfor=?";
			// tratamentos de exceçoes
			try {
				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCep.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUF.getSelectedItem().toString());
				pst.setString(16, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Fornecedor editado com sucesso");
				// limpar campos
				limparCampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				
				System.out.println(e);
				
			} 
		}
	}
}