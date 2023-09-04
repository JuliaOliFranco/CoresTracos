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
import java.awt.Font;

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

	
	public static void main(String[] args) {
		try {
			Fornecedor dialog = new Fornecedor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Fornecedor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedor.class.getResource("/img/309041_users_group_people_icon (1).png")));
		setTitle("Fornecedor");
		setBounds(100, 100, 800, 600);
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
		scrollPaneFornecedor.setBounds(192, 79, 257, 40);
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
		btnAdicionar.setBounds(94, 502, 48, 48);
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
		btnExcluir.setBounds(265, 502, 48, 48);
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
		btnEditar.setBounds(452, 502, 48, 48);
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
		btnLimpar.setBounds(613, 502, 48, 48);
		contentPanel.add(btnLimpar);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setBounds(69, 41, 37, 14);
		contentPanel.add(lblID);
		
		JLabel lblRazao = new JLabel("Razão Social:");
		lblRazao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRazao.setBounds(192, 41, 90, 14);
		contentPanel.add(lblRazao);
		
		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFone.setBounds(69, 114, 75, 14);
		contentPanel.add(lblFone);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(260, 114, 68, 14);
		contentPanel.add(lblEmail);
		
		JLabel lblCNPJ = new JLabel("CNPJ:");
		lblCNPJ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCNPJ.setBounds(532, 41, 46, 14);
		contentPanel.add(lblCNPJ);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCep.setBounds(532, 114, 46, 14);
		contentPanel.add(lblCep);
		
		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereco.setBounds(69, 194, 88, 16);
		contentPanel.add(lblEndereco);
		
		JLabel lblNumero = new JLabel("Nº:");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumero.setBounds(364, 202, 46, 14);
		contentPanel.add(lblNumero);
		
		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblComplemento.setBounds(473, 201, 95, 14);
		contentPanel.add(lblComplemento);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairro.setBounds(69, 275, 62, 14);
		contentPanel.add(lblBairro);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCidade.setBounds(275, 281, 95, 14);
		contentPanel.add(lblCidade);
		
		JLabel lblUF = new JLabel("UF:");
		lblUF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUF.setBounds(483, 281, 46, 14);
		contentPanel.add(lblUF);
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setBounds(483, 295, 62, 29);
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
		txtID.setBounds(69, 55, 62, 29);
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
		txtFone.setBounds(70, 129, 132, 29);
		contentPanel.add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(15));
		
		txtEmail = new JTextField();
		txtEmail.setDocument(new Validador(50));
		txtEmail.setBounds(260, 129, 226, 29);
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
		txtCep.setBounds(533, 128, 86, 29);
		contentPanel.add(txtCep);
		txtCep.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(69, 211, 259, 29);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(473, 217, 190, 29);
		contentPanel.add(txtComplemento);
		txtComplemento.setColumns(10);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(69, 289, 171, 29);
		contentPanel.add(txtBairro);
		txtBairro.setColumns(10);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(275, 296, 168, 29);
		contentPanel.add(txtCidade);
		
		txtNumero = new JTextField();
		txtNumero.setDocument(new Validador(5));
		txtNumero.addKeyListener(new KeyAdapter() {
			
		});
		txtNumero.setBounds(364, 217, 62, 29);
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
		btnBuscarCep.setBounds(645, 125, 40, 40);
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
		txtRazao.setBounds(192, 56, 257, 29);
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
		txtCNPJ.setBounds(532, 56, 182, 29);
		contentPanel.add(txtCNPJ);
		txtCNPJ.setColumns(10);
		txtCNPJ.setDocument(new Validador(15));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(125, 0, 34));
		lblNewLabel.setBounds(0, 492, 784, 69);
		contentPanel.add(lblNewLabel);
		
		JLabel lblFantasia = new JLabel("Nome Fantasia:");
		lblFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFantasia.setBounds(69, 361, 130, 14);
		contentPanel.add(lblFantasia);
		
		txtFantasia = new JTextField();
		txtFantasia.setBounds(69, 377, 168, 29);
		contentPanel.add(txtFantasia);
		txtFantasia.setColumns(10);
		txtFantasia.setDocument(new Validador(50));
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVendedor.setBounds(284, 361, 113, 14);
		contentPanel.add(lblVendedor);
		
		txtVendedor = new JTextField();
		txtVendedor.setBounds(281, 377, 143, 29);
		contentPanel.add(txtVendedor);
		txtVendedor.setColumns(10);
		txtVendedor.setDocument(new Validador(20));
		
		JLabel lblIE = new JLabel("Inscrição Estadual:");
		lblIE.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIE.setBounds(576, 281, 132, 14);
		contentPanel.add(lblIE);
		
		txtIE = new JTextField();
		txtIE.setBounds(575, 295, 133, 29);
		contentPanel.add(txtIE);
		txtIE.setColumns(10);
		txtIE.setDocument(new Validador(20));
		
		JLabel lblSite = new JLabel("Site:");
		lblSite.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSite.setBounds(479, 361, 235, 14);
		contentPanel.add(lblSite);
		
		txtSite = new JTextField();
		txtSite.setBounds(479, 376, 235, 29);
		contentPanel.add(txtSite);
		txtSite.setColumns(10);
		txtSite.setDocument(new Validador(50));
	}
	
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
	
	private void buscarFornecedor() {
		
		int linha = listFornecedor.getSelectedIndex();
		if (linha >= 0) {
			
			String readListafornecedor =  "select * from fornecedor where razao like '" + txtRazao.getText() + "%'" + "order by razao";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListafornecedor);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFornecedor.setVisible(false);
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
			scrollPaneFornecedor.setVisible(false);
		}
		
	}
	
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
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedor.setModel(modelo);
		String readLista = "select* from fornecedor where razao like '" + txtRazao.getText() + "%'" + "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneFornecedor.setVisible(true);
				modelo.addElement(rs.getString(2));
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
			
			String create = "insert into fornecedor (razao,fantasia,fone,vendedor,email,site,cnpj,ie,cep,endereco,numero,bairro,cidade,uf) value (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			try {
				con = dao.conectar();
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
				
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado!");  
				limparCampos();
			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CNPJ já está sendo utilizado.");
				txtCNPJ.setText(null);
				txtCNPJ.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}
	
	private void excluirFornecedor() {
		
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			
			String delete = "delete from fornecedor where razao=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtRazao.getText());
				pst.executeUpdate();
				limparCampos();
				
				JOptionPane.showMessageDialog(null, " Fornecedor excluido");
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

			String update = "update fornecedor set razao=?, fantasia=?, fone=?, vendedor=?, email=?, site=?, cnpj=? , ie=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where idfor=?";
			
			try {
				con = dao.conectar();
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
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Fornecedor editado com sucesso");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
				
			} 
		}
	}
}