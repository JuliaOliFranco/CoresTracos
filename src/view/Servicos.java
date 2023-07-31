package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Servicos extends JDialog {
	
	// Instanciar objetos JDBC
			DAO dao = new DAO();
			private Connection con;
			private PreparedStatement pst;
			private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtPeca;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JTextField txtID;
	private JButton btnLimpar;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton bntOS;
	private JTextField txtNome;
	private JList listUsers;
	private JScrollPane scrollPaneUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Servicos dialog = new Servicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/2931174_clipboard_copy_paste_analysis_report_icon (1).png")));
		setModal(true);
		setTitle("Serviços");
		setBounds(100, 100, 471, 392);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("OS:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 23, 46, 14);
		contentPanel.add(lblNewLabel);
		
		txtOS = new JTextField();
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtOS.setEditable(false);
		txtOS.setBounds(24, 37, 46, 20);
		contentPanel.add(txtOS);
		txtOS.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(24, 83, 102, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(24, 97, 123, 20);
		contentPanel.add(txtData);
		txtData.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Peça:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(24, 136, 86, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Defeito:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(24, 182, 61, 14);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Valor:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(24, 231, 46, 14);
		contentPanel.add(lblNewLabel_4);
		
		txtPeca = new JTextField();
		txtPeca.setBounds(24, 151, 408, 20);
		contentPanel.add(txtPeca);
		txtPeca.setColumns(10);
		txtPeca.setDocument(new Validador(20));
		
		txtDefeito = new JTextField();
		txtDefeito.setBounds(24, 200, 408, 20);
		contentPanel.add(txtDefeito);
		txtDefeito.setColumns(10);
		txtDefeito.setDocument(new Validador(30));
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtValor.setBounds(24, 245, 98, 20);
		contentPanel.add(txtValor);
		txtValor.setColumns(10);
		txtValor.setDocument(new Validador(10));
		
		JButton btnBuscar = new JButton("");
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/743893_search_find_glass_magnifier_magnifying_icon.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(91, 23, 39, 37);
		contentPanel.add(btnBuscar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/3994437_add_create_new_plus_positive_icon.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(118, 294, 48, 48);
		contentPanel.add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setBorderPainted(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/9055458_bxs_edit_alt_icon.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(205, 294, 48, 48);
		contentPanel.add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBorderPainted(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/3669378_clear_ic_icon (1).png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(293, 294, 48, 48);
		contentPanel.add(btnExcluir);
		
		btnLimpar = new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/8665346_eraser_icon.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(34, 294, 48, 48);
		contentPanel.add(btnLimpar);
		
		bntOS = new JButton("");
		bntOS.setBorderPainted(false);
		bntOS.setContentAreaFilled(false);
		bntOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/211880_printer_icon.png")));
		bntOS.setToolTipText("Imprimir");
		bntOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		bntOS.setBounds(373, 294, 48, 48);
		contentPanel.add(bntOS);
		
		getRootPane().setDefaultButton(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(195, 24, 237, 90);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(10, 40, 217, 39);
		panel.add(scrollPaneUsers);
		
		listUsers = new JList();
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioPelaLista();
			}
		});
		scrollPaneUsers.setViewportView(listUsers);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(10, 22, 217, 20);
		panel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Id:");
		lblNewLabel_5.setBounds(10, 46, 46, 14);
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtID.setEditable(false);
		txtID.setBounds(10, 59, 55, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		
	}//fim construtor
	
	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
		// Dica - testar o evento preimeiro
		// System.out.println("teste do botão buscar");
		// Criar ua variável com a query (instruções do banco)
		String read = "select * from servicos where os = ?";
		// Tratamento de exceções
		try {
			// Abrir a conexão
			con = dao.conectar();

			// Preparar a exucução da query(instrução sql - CRUD Read)
			// O paraêmtro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else parar verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2)); 
				txtPeca.setText(rs.getString(3)); 
				txtDefeito.setText(rs.getString(4)); 
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				//Validação (liberarção dos botões alterar e excluir)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {

				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "OS Inexistente");
				//Validação(libereação do botão adicionar)
				btnAdicionar.setEnabled(true);
			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}// Fim do método buscar
	
	/**
	 * metodo limpar campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtPeca.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}//fim do metodo limpar
	
	/**
	 * metodo editar contato
	 */
	private void editar() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a peça");
			txtDefeito.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o defeito");
			txtDefeito.requestFocus();
		}else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor");
			txtValor.requestFocus();
		}else {
			// Lógica principal
			// CRUD - Update
			String upadate = "update servicos set peca=?, defeito=?, valor=? where os=?";
			// tratamentos de exceçoes
			try {

				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)	
				pst = con.prepareStatement(upadate);
				pst.setString(1, txtPeca.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtOS.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados da OS editados com sucesso");
				// limpar campos
				limparCampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}//fim do metodo editar
	
	/**
	 * metodo adicionar
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if (txtPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a peça");
			txtPeca.requestFocus();
		}else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o defeito");
			txtDefeito.requestFocus();
		}else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor");
			txtValor.requestFocus();
		} else {
			
			// lógica pricipal
			// CRUD Creat
			String create = "insert into servicos (peca,defeito,valor,id) value (?, ?, ?, ?)";
			// tratamento com exceções
			try {
				//abrir conexão 
				con = dao.conectar();
				//preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtPeca.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				//executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				//Confirmar
				JOptionPane.showMessageDialog(null, "OS adicionado");
				limparCampos();
				//fechar a conexão
			} catch (Exception e) {
				System.out.println(e);
			}
			}
	}//fim do adicionar
	
	/**
	 * metodo excluir
	 */
	private void excluir() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			//CRUD - Delete
			String delete = "delete from servicos where os=?";
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
				JOptionPane.showMessageDialog(null, " OS excluida");
				//fechar a conexão 
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}//fim do excluir
	
	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtID.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a OS");
			txtOS.requestFocus();
		}
		else {
			
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_RIGHT);
					document.add(data);
						
					Paragraph usuario = new Paragraph ("Peça: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph defeito = new Paragraph ("Defeito: " + rs.getString(4));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(5));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
				
					//imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/186269_measure_mannequin_icon.png"));
					imagem.scaleToFit(250,200);
					imagem.setAbsolutePosition(400, 30);
					document.add(imagem);					
				}
				// fechar a conexão com o banco
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
			}
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
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			//se nao existir no banco um usuario da lista
			scrollPaneUsers.setVisible(false);
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

}
