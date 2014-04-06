package Express;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import Express.HttpRequest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Dialog.ModalExclusionType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JEditorPane;
import javax.swing.DropMode;
import java.awt.Toolkit;

public class ExpressQuery extends JFrame {
	public static String Query = "";
	public static String a = "";
	private JPanel contentPane;
	private JTextField txtCjus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExpressQuery frame = new ExpressQuery();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static String readTxt() {
		String filePath = "E:\\zm\\1.txt"; // 文件和该类在同个目录下
		BufferedReader reader = null;
		String str = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,

			while ((str = reader.readLine()) != null) {
				a = a + str;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	/**
	 * Create the frame.
	 */
	public ExpressQuery() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\zm\\6_0220114F213U.jpg.png"));
		setResizable(false);
		setTitle("ExpressQuery designed by Troevil thanks to \u503E\u6708");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtCjus = new JTextField();
		txtCjus.setText("CJ239336160US");
		txtCjus.setBounds(229, 10, 190, 21);
		contentPane.add(txtCjus);
		txtCjus.setColumns(10);

		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "EMS",
				"\u5706\u901A" }));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(10, 10, 109, 21);
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel("\u8FD0\u5355\u53F7:");
		lblNewLabel.setBounds(161, 13, 42, 15);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 46, 583, 180);
		contentPane.add(scrollPane);

		final JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		scrollPane.setViewportView(textPane);

		final JButton btnNewButton = new JButton("\u67E5\u8BE2");
		btnNewButton.setBounds(465, 5, 125, 31);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtCjus.getText() == null
						|| txtCjus.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "未选输入快递单号", "提示",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				long deskey = System.currentTimeMillis();
				System.out.println("deskey=" + deskey);
				String querytime = Long.toString(deskey + 2014);
				System.out.println("querytime=" + querytime);

				try {
					Query = DES.encrypt(txtCjus.getText(), querytime);
					Query = DES.encrypt(RandomS.Random() + "=" + Query,
							"20140406");
					System.out.println(DES.decrypt(Query, "20140406"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String urlt = "http://troevil.jd-app.com/", matcha = "", matchb = "", urlw = "";
				if (comboBox.getSelectedIndex() == 0) {
					urlw = "ems.php";
					matcha = "<div id=\"ems1\">";
					matchb = "</body>";
				} else {
					if (comboBox.getSelectedIndex() == 1) {
						urlw = "yt.php";
						matcha = "<table cellspacing";
						matchb = "</tr></table>";
					} else {
						JOptionPane.showMessageDialog(null, "未选择快递", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				String a = HttpRequest.sendPost(urlt + urlw, Query,
						Long.toString(deskey));
	

					a = matcha + DES.gettext(matcha, matchb, a) + matchb;
				textPane.setText(a);

			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
	}
}
