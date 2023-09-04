package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;

public class Relatorios extends JDialog {
	
	//obj JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;  

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnClientes = new JButton("");
		btnClientes.setContentAreaFilled(false);
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/309041_users_group_people_icon (1).png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setBounds(44, 50, 99, 88);
		contentPanel.add(btnClientes);
		
		JButton btnServicos = new JButton("");
		btnServicos.setContentAreaFilled(false);
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/2931174_clipboard_copy_paste_analysis_report_icon (1).png")));
		btnServicos.setToolTipText("Serviços");
		btnServicos.setBounds(208, 50, 99, 88);
		contentPanel.add(btnServicos);
		
		JLabel lblRodape = new JLabel("");
		lblRodape.setOpaque(true);
		lblRodape.setBackground(new Color(121, 11, 38));
		lblRodape.setBounds(0, 493, 784, 68);
		contentPanel.add(lblRodape);
		
		JButton btnRepor = new JButton("");
		btnRepor.setContentAreaFilled(false);
		btnRepor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Repor();
			}
		});
		btnRepor.setIcon(new ImageIcon(Relatorios.class.getResource("/img/4172394_goods_merchandise_stock_supply_vendibles_icon.png")));
		btnRepor.setToolTipText("Reposição");
		btnRepor.setBounds(380, 50, 99, 88);
		contentPanel.add(btnRepor);
		
		JButton btnCustoP = new JButton("New button");
		btnCustoP.setContentAreaFilled(false);
		btnCustoP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustoPatrimonio();
			}
		});
		btnCustoP.setBounds(137, 214, 99, 88);
		contentPanel.add(btnCustoP);
		
		JButton btnVendaP = new JButton("New button");
		btnVendaP.setContentAreaFilled(false);
		btnVendaP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VendaPatrimonio();
			}
		});
		btnVendaP.setBounds(303, 214, 99, 88);
		contentPanel.add(btnVendaP);
		
		JButton btnFornecedores = new JButton("New button");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores();
			}
		});
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setBounds(488, 214, 99, 88);
		contentPanel.add(btnFornecedores);
	}//fim do construtor
	
	/**
	 * 
	 */
	private void relatorioClientes() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" ")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readClientes = "select nome,telefone,email from Clientes order by nome";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readClientes);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(3); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	/**
	 * 
	 */
	private void relatorioServicos() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Serviços:"));
			document.add(new Paragraph(" ")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readServicos = "select os,dataOS,peca,defeito,valor,nome from servicos inner join Clientes\r\n" + "  on servicos.id = Clientes.id;";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readServicos);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(6); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("DataOs"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Peça"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Nome"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("servicos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void Repor() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("produtos.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Produtos:"));
			document.add(new Paragraph("")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readProdutos = "select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade,\n"
					+ "date_format(dataent, '%d/%m/%Y') as entrada,\n"
					+ "estoque, estoquemin as estoque_mínimo \n"
					+ "from produtos where dataval < dataent";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readProdutos);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(6); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Validade"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Entrada"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Estoque min"));
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);	
				
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("produtos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void CustoPatrimonio() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph("")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readPatrimonio = "select sum(custo * estoque) as Total from produtos;";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readPatrimonio);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(1); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));
			
				
				tabela.addCell(col1);
			
				
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void VendaPatrimonio() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph("")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readPatrimonio = "select sum((custo +(custo * lucro)/100) * estoque) as total from produtos;";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readPatrimonio);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(1); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));
			
				
				tabela.addCell(col1);
			
				
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	private void Fornecedores() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("fornecedores.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Fornecedores:"));
			document.add(new Paragraph("")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readPatrimonio = " select \n"
					+ " idfor,\n"
					+ " razao,\n"
					+ " fantasia,\n"
					+ " fone,\n"
					+ " email,\n"
					+ " site,\n"
					+ " cnpj,\n"
					+ " ie,\n"
					+ " cep"
					+ " from fornecedor;";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readPatrimonio);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(9); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("ID Fornecedor"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Razão Social"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Nome Fantasia"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Número de Telefone"));
				PdfPCell col5 = new PdfPCell(new Paragraph("E-mail"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Site"));
				PdfPCell col7 = new PdfPCell(new Paragraph("CPNJ"));
				PdfPCell col8 = new PdfPCell(new Paragraph("Inscrição Estadual"));
				PdfPCell col9 = new PdfPCell(new Paragraph("CEP"));

			
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				tabela.addCell(col9);
			
				
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(9));
					
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("fornecedores.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	
}//Fim do codigo
	

