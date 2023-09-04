package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class Produtos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	
	private FileInputStream fis;

	private int tamanho;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtCusto;
	private JTextField txtLocal;
	private JTextField txtFornecedor;
	private JList listFornecedor;
	private JScrollPane scrollPaneFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JTextField txtBarcode;
	private JLabel lblNewLabel;
	private JTextArea txtDescricao;
	private JScrollPane scrollPaneProdutos;
	private JList listProdutos;
	private JTextField txtFabricante;
	private JTextField txtLote;
	private JLabel lblNewLabel_6;
	private JTextField txtLucro;
	private JDateChooser dateEntrada;
	private JDateChooser dateValidade;
	private JComboBox cboUN;

	
	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/9859783_craft_sewing_machine_tailor_tailoring_icon.png")));
		setTitle("Produtos");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			scrollPaneProdutos = new JScrollPane();
			scrollPaneProdutos.setVisible(false);
			scrollPaneProdutos.setBounds(27, 113, 299, 27);
			contentPanel.add(scrollPaneProdutos);
			
			listProdutos = new JList();
			scrollPaneProdutos.setViewportView(listProdutos);
			listProdutos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					buscarProdutos();
				}
			});
		}

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(513, 11, 222, 65);
		contentPanel.add(panel);
		panel.setLayout(null);

		txtFornecedor = new JTextField();
		txtFornecedor.setDocument(new Validador(50));
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(110, 19, 94, 17);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblFornecedor = new JLabel("Nome Fornecedor:");
		lblFornecedor.setBounds(10, 21, 100, 14);
		panel.add(lblFornecedor);

		scrollPaneFornecedor = new JScrollPane();
		scrollPaneFornecedor.setVisible(false);
		scrollPaneFornecedor.setBounds(110, 32, 94, 26);
		panel.add(scrollPaneFornecedor);

		listFornecedor = new JList();
		listFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedor();
			}
		});
		scrollPaneFornecedor.setViewportView(listFornecedor);

		JLabel lblIDFornecedor = new JLabel("ID:");
		lblIDFornecedor.setBounds(10, 40, 46, 14);
		panel.add(lblIDFornecedor);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.setEditable(false);
		txtIDFornecedor.setEnabled(false);
		txtIDFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtIDFornecedor.setBounds(33, 38, 46, 20);
		panel.add(txtIDFornecedor);
		txtIDFornecedor.setColumns(10);
		{
			JLabel lblID = new JLabel("Código:");
			lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblID.setBounds(279, 15, 89, 14);
			contentPanel.add(lblID);
		}
		{
			JLabel lblProduto = new JLabel("Produto:");
			lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblProduto.setBounds(27, 73, 59, 14);
			contentPanel.add(lblProduto);
		}
		{
			JLabel lblBarcode = new JLabel("Barcode:");
			lblBarcode.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblBarcode.setBounds(90, 15, 107, 14);
			contentPanel.add(lblBarcode);
		}
		{
			JLabel lblDescricao = new JLabel("Descrição:");
			lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDescricao.setBounds(31, 338, 222, 23);
			contentPanel.add(lblDescricao);
		}
		{
			JLabel lblEstoque = new JLabel("Estoque:");
			lblEstoque.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEstoque.setBounds(352, 144, 86, 14);
			contentPanel.add(lblEstoque);
		}
		{
			JLabel lblEstoqueMin = new JLabel("Estoque Min:");
			lblEstoqueMin.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEstoqueMin.setBounds(211, 144, 86, 14);
			contentPanel.add(lblEstoqueMin);
		}
		{
			JLabel lblCusto = new JLabel("Custo:");
			lblCusto.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCusto.setBounds(27, 275, 46, 14);
			contentPanel.add(lblCusto);
		}
		{
			JLabel lblUnidade = new JLabel("Unidade Medida:");
			lblUnidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblUnidade.setBounds(331, 275, 107, 14);
			contentPanel.add(lblUnidade);
		}
		{
			JLabel lblLocal = new JLabel("Local:");
			lblLocal.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLocal.setBounds(331, 215, 86, 14);
			contentPanel.add(lblLocal);
		}
		{
			txtCodigo = new JTextField();
			txtCodigo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtCodigo.setBounds(279, 30, 86, 29);
			contentPanel.add(txtCodigo);
			txtCodigo.setColumns(10);

		}
		{
			txtProduto = new JTextField();
			txtProduto.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					listarProdutos();
				}
			});
			txtProduto.setBounds(27, 87, 299, 29);
			contentPanel.add(txtProduto);
			txtProduto.setColumns(10);
			txtProduto.setDocument(new Validador(50));
		}
		{
			txtEstoque = new JTextField();
			txtEstoque.setDocument(new Validador(5));
			txtEstoque.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtEstoque.setColumns(10);
			txtEstoque.setBounds(351, 159, 102, 29);
			contentPanel.add(txtEstoque);
		}
		{
			txtEstoqueMin = new JTextField();
			txtEstoqueMin.setDocument(new Validador(7));
			txtEstoqueMin.setColumns(10);
			txtEstoqueMin.setBounds(211, 159, 102, 29);
			contentPanel.add(txtEstoqueMin);
		}
		{
			txtCusto = new JTextField();
			txtCusto.setDocument(new Validador(7));
			txtCusto.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtCusto.setColumns(10);
			txtCusto.setBounds(27, 290, 89, 29);
			contentPanel.add(txtCusto);
		}
		{
			txtLocal = new JTextField();
			txtLocal.setColumns(10);
			txtLocal.setBounds(331, 230, 107, 29);
			contentPanel.add(txtLocal);
			txtLocal.setDocument(new Validador(20));
		}
		{
			btnExcluir = new JButton("");
			btnExcluir.setContentAreaFilled(false);
			btnExcluir.setBorderPainted(false);
			btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnExcluir.setEnabled(false);
			btnExcluir.setToolTipText("Excluir");
			btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/3669378_clear_ic_icon (1).png")));
			btnExcluir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirProduto();
				}
			});
			btnExcluir.setBounds(267, 499, 48, 48);
			contentPanel.add(btnExcluir);
		}
		{
			btnLimpar = new JButton("");
			btnLimpar.setContentAreaFilled(false);
			btnLimpar.setBorderPainted(false);
			btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/8665346_eraser_icon.png")));
			btnLimpar.setToolTipText("Limpar");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			btnLimpar.setBounds(583, 502, 48, 48);
			contentPanel.add(btnLimpar);
		}
		{
			btnAdicionar = new JButton("");
			btnAdicionar.setBorderPainted(false);
			btnAdicionar.setContentAreaFilled(false);
			btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdicionar.setToolTipText("Adicionar");
			btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
			btnAdicionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionar();
				}
			});
			btnAdicionar.setBounds(108, 499, 48, 48);
			contentPanel.add(btnAdicionar);
		}
		{
			btnEditar = new JButton("");
			btnEditar.setBorderPainted(false);
			btnEditar.setContentAreaFilled(false);
			btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnEditar.setEnabled(false);
			btnEditar.setToolTipText("Editar");
			btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editarProduto();
				}
			});
			btnEditar.setBounds(428, 499, 48, 48);
			contentPanel.add(btnEditar);
		}

		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setForeground(SystemColor.textHighlight);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/9110937_camera_create_icon.png")));
		lblFoto.setBounds(497, 113, 256, 256);
		contentPanel.add(lblFoto);

		btnCarregar = new JButton("Carregar Foto");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setBounds(568, 380, 132, 23);
		contentPanel.add(btnCarregar);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarProdutoBarcode();
				}
			}
		});
		txtBarcode.setBounds(90, 30, 142, 29);
		contentPanel.add(txtBarcode);
		txtBarcode.setColumns(10);
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setOpaque(true);
			lblNewLabel.setBackground(new Color(130, 0, 33));
			lblNewLabel.setBounds(0, 481, 784, 80);
			contentPanel.add(lblNewLabel);
		}
		{
			txtDescricao = new JTextArea();
			txtDescricao.setBounds(27, 362, 426, 95);
			contentPanel.add(txtDescricao);
		}
		
		JLabel lblNewLabel_1 = new JLabel("Entrada:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(27, 214, 61, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Validade:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(187, 215, 63, 14);
		contentPanel.add(lblNewLabel_2);
		
		dateEntrada = new JDateChooser();
		dateEntrada.setBounds(27, 227, 108, 29);
		contentPanel.add(dateEntrada);
		
		dateValidade = new JDateChooser();
		dateValidade.setBounds(186, 228, 108, 29);
		contentPanel.add(dateValidade);
		
		JLabel lblNewLabel_3 = new JLabel("Fabricante:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(27, 144, 68, 14);
		contentPanel.add(lblNewLabel_3);
		
		txtFabricante = new JTextField();
		txtFabricante.setBounds(27, 159, 142, 29);
		contentPanel.add(txtFabricante);
		txtFabricante.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Lote:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(368, 73, 46, 14);
		contentPanel.add(lblNewLabel_4);
		
		txtLote = new JTextField();
		txtLote.setBounds(366, 88, 86, 29);
		contentPanel.add(txtLote);
		txtLote.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Produtos.class.getResource("/img/9054229_bx_barcode_icon.png")));
		lblNewLabel_5.setBounds(30, 11, 48, 46);
		contentPanel.add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("Lucro:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(187, 275, 46, 14);
		contentPanel.add(lblNewLabel_6);
		
		txtLucro = new JTextField();
		txtLucro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtLucro.setText("0");
		txtLucro.setBounds(187, 290, 59, 29);
		contentPanel.add(txtLucro);
		txtLucro.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("%");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_7.setBounds(249, 296, 30, 19);
		contentPanel.add(lblNewLabel_7);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}
		});
		btnNewButton.setBounds(384, 29, 89, 23);
		contentPanel.add(btnNewButton);
		
		cboUN = new JComboBox();
		cboUN.setModel(new DefaultComboBoxModel(new String[] {"", "UN", "CX ", "PC ", "KG", "M"}));
		cboUN.setBounds(331, 290, 86, 29);
		contentPanel.add(cboUN);

	}
	

	private void listarFornecedor() {
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedor.setModel(modelo);
		String readLista = "select* from fornecedor where razao like '" + txtFornecedor.getText() + "%'"
				+ "order by razao ";
		try {
			
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				scrollPaneFornecedor.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFornecedor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarFornecedor() {
		
		int linha = listFornecedor.getSelectedIndex();
		if (linha >= 0) {
			String readListafornecedor = "select * from fornecedor where razao like '" + txtFornecedor.getText()
					+ "%'" + "order by razao";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListafornecedor);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFornecedor.setVisible(false);
					txtIDFornecedor.setText(rs.getString(1));
					txtFornecedor.setText(rs.getString(2));

				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneFornecedor.setVisible(false);
		}
	}

	
	private void limparCampos() {
		cboUN.setSelectedItem("");
		txtProduto.setText(null);
		txtDescricao.setText(null);
		txtIDFornecedor.setText(null);
		txtCodigo.setText(null);
		txtLote.setText(null);
		txtLocal.setText(null);
		txtCusto.setText(null);
		txtEstoqueMin.setText(null);
		txtEstoque.setText(null);
		txtFornecedor.setText(null);
		txtFabricante.setText(null);
		dateValidade.setDate(null);
		dateEntrada.setDate(null);
		txtLucro.setText(null);
		txtBarcode.setText(null);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/9110937_camera_create_icon.png")));
	}

	private void adicionar() {
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor do produto");
			txtProduto.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque atual");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque minimo atual");
			txtEstoqueMin.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtCusto.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (dateValidade.getDate()== null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto");

			
				
		} else {

			String create = "insert into produtos (barcode,produto,lote,descricao,foto,fabricante,dataval,estoque,estoquemin,unidade,localarm,custo,lucro,idfor) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(create);
				
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setBlob(5, fis, tamanho);
				pst.setString(6, txtFabricante.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtEstoque.getText());
				pst.setString(9, txtEstoqueMin.getText());
				pst.setString(10, cboUN.getSelectedItem().toString());
				pst.setString(11, txtLocal.getText());
				pst.setString(12, txtCusto.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtIDFornecedor.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Produto adicionado");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.print(e);
			}
		}
	}

	
	private void excluirProduto() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			String delete = "delete from produtos where produto=?";
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtProduto.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, " Produto excluido");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarProduto() {
		
				if (txtProduto.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o Brinquedo do Serviço");
					txtProduto.requestFocus();
				} else if (txtLote.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o Lote de Serviço");
					txtLote.requestFocus();
				} else if (txtCusto.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite a custo de Serviço");
					txtCusto.requestFocus();
				} else if (txtFabricante.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o fabricante ");
					txtFabricante.requestFocus();
				} else if (txtDescricao.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o descrição do Serviço");
					txtDescricao.requestFocus();
				} else if (txtEstoque.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o estoque do Serviço");
					txtEstoque.requestFocus();
				} else if (txtLocal.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o local do Serviço");
					txtLocal.requestFocus();
				} else if (txtEstoqueMin.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o estoque minimo do Serviço");
					txtEstoqueMin.requestFocus();
				} else if (txtLucro.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o lucro do Serviço");
					txtLucro.requestFocus();
				} else {

			
			String update = "update produtos set produto=?,lote=?, descricao=?, fabricante=?, estoque=?, estoquemin=?, unidade=?, custo=?, lucro=?, localarm=? where codigo=?";
			
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtLote.getText());
				pst.setString(3, txtDescricao.getText());
				pst.setString(4, txtFabricante.getText());
				pst.setString(5, txtEstoque.getText());
				pst.setString(6, txtEstoqueMin.getText());
				pst.setString(7, cboUN.getSelectedItem().toString());
				pst.setString(8, txtCusto.getText());
				pst.setString(9, txtLucro.getText());
				pst.setString(10, txtLocal.getText());
				pst.setString(11, txtCodigo.getText());

				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Produto editado com sucesso");
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagens(*.PNG, *JPG, *JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	
	private void listarProdutos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listProdutos.setModel(modelo);
		String readLista = "select* from produtos where produto like '" + txtProduto.getText() + "%'"
				+ "order by produto";
		try {
			
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next
					()) {
				
				scrollPaneProdutos.setVisible(true);
				modelo.addElement(rs.getString(3));
				if (txtProduto.getText().isEmpty()) {
					scrollPaneProdutos.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void buscarProdutos() {
		
		int linha = listProdutos.getSelectedIndex();
		if (linha >= 0) {
			String readListaprodutos = "select * from produtos where produto like '" + txtProduto.getText() + "%'"
					+ "order by produto";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaprodutos);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneProdutos.setVisible(false);
					txtCodigo.setText(rs.getString(1));
					txtBarcode.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtLote.setText(rs.getString(4)); 
					txtDescricao.setText(rs.getString(5));
					Blob blob = (Blob) rs.getBlob(6);
					txtFabricante.setText(rs.getString(7));
					txtEstoque.setText(rs.getString(10));
					txtEstoqueMin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFornecedor.setText(rs.getString(16));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnAdicionar.setEnabled(false);

					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
					}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneProdutos.setVisible(false);
			
		}
	}
	
	private void pesquisarProduto() {
		String readcodigo = "select * from produtos where codigo = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readcodigo);
			pst.setString(1, txtCodigo.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtBarcode.setText(rs.getString(2));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtDescricao.setText(rs.getNString(5));
				Blob blob = (Blob) rs.getBlob(6);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				String setarDataEnt = rs.getString(8);
				Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
				dateEntrada.setDate(dataEntrada);
				
				String setarDataVal = rs.getString(9);
				Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
				dateValidade.setDate(dataValidade);
				
				txtFabricante.setText(rs.getString(7));
				txtEstoque.setText(rs.getString(10));
				txtEstoqueMin.setText(rs.getString(11));
				cboUN.setSelectedItem(rs.getString(12));
				txtLocal.setText(rs.getString(13));
				txtCusto.setText(rs.getString(14));
				txtIDFornecedor.setText(rs.getString(16));
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);
				
			} else {
				JOptionPane.showMessageDialog(null, "Produto não encontrado!");
				
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
		private void pesquisarProdutoBarcode() {
			String readBarcode = "select * from produtos where barcode = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBarcode);
				pst.setString(1, txtBarcode.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtCodigo.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtLote.setText(rs.getString(4));
					txtDescricao.setText(rs.getNString(5));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
							Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					String setarDataEnt = rs.getString(8);
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					
					String setarDataVal = rs.getString(9);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					
					txtFabricante.setText(rs.getString(7));
					txtEstoque.setText(rs.getString(10));
					txtEstoqueMin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtIDFornecedor.setText(rs.getString(16));
					
				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado!");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
}
