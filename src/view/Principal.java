package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.DAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;

public class Principal extends JFrame {
	
	
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	
	public JLabel lblUsuario;
	public JButton btnRelatorio;
	public JButton btnUsuarios;
	public JLabel lblRodape;
	private JButton btnProdutos;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}

			private void status() {
				try {
					con = dao.conectar();
					if (con == null) {
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dboff.png")));
					} else {
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dbon.png")));
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
				
				private void setarData() {
					Date date = new Date();
					DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
					lblData.setText(formatador.format(date));
		
				}	
				
		});
		setTitle("Sistema - Cores&Traços");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/1173389_button_buttons_sewing_tailor icon_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("");
		lblStatus.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
			
		});
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(63, 507, 135, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNewLabel_3 = new JLabel("Usuário:");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(9, 507, 61, 14);
		contentPane.add(lblNewLabel_3);
		lblStatus.setBounds(736, 513, 48, 48);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		lblStatus.setToolTipText("DBon");
		contentPane.add(lblStatus);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setEnabled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/309035_user_account_human_person_icon.png")));
		btnUsuarios.setToolTipText("Usuaríos");
		btnUsuarios.setBounds(23, 57, 99, 88);
		contentPane.add(btnUsuarios);
		
		JButton bntSobre = new JButton("");
		bntSobre.setContentAreaFilled(false);
		bntSobre.setBorder(null);
		bntSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);

			}
		});
		bntSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		bntSobre.setToolTipText("Sobre");
		bntSobre.setBounds(726, 11, 48, 48);
		contentPane.add(bntSobre);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/186269_measure_mannequin_icon.png")));
		lblNewLabel.setBounds(643, 325, 112, 138);
		contentPane.add(lblNewLabel);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setForeground(SystemColor.text);
		lblData.setBackground(new Color(128, 255, 0));
		lblData.setBounds(10, 524, 274, 26);
		contentPane.add(lblData);
		
		lblRodape = new JLabel("");
		lblRodape.setOpaque(true);
		lblRodape.setBackground(new Color(121, 11, 38));
		lblRodape.setBounds(0, 493, 784, 68);
		contentPane.add(lblRodape);
		
		JButton btnCliente = new JButton("");
		btnCliente.setToolTipText("Cadastro de Clientes");
		btnCliente.setContentAreaFilled(false);
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliente.setIcon(new ImageIcon(Principal.class.getResource("/img/309041_users_group_people_icon (1).png")));
		btnCliente.setBounds(201, 57, 99, 88);
		contentPane.add(btnCliente);
		
		JButton btnOS = new JButton("");
		btnOS.setContentAreaFilled(false);
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setToolTipText("Ordem de Serviço (OS)");
		btnOS.setIcon(new ImageIcon(Principal.class.getResource("/img/9859783_craft_sewing_machine_tailor_tailoring_icon.png")));
		btnOS.setBounds(72, 238, 99, 88);
		contentPane.add(btnOS);
		
		btnRelatorio = new JButton("");
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setEnabled(false);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setToolTipText("Relatório");
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/2931174_clipboard_copy_paste_analysis_report_icon (1).png")));
		btnRelatorio.setBounds(248, 238, 99, 88);
		contentPane.add(btnRelatorio);
		
		JLabel lblNewLabel_2 = new JLabel("Cores&Traços");
		lblNewLabel_2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_2.setBounds(653, 462, 121, 20);
		contentPane.add(lblNewLabel_2);
		
		JButton btnFornecedores = new JButton("");
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/4172394_goods_merchandise_stock_supply_vendibles_icon.png")));
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setBounds(390, 57, 99, 88);
		contentPane.add(btnFornecedores);
		
		btnProdutos = new JButton("");
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/3018582_buy_cart_checkout_products_purchase_icon.png")));
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setBounds(438, 238, 99, 88);
		contentPane.add(btnProdutos);
	}
}

