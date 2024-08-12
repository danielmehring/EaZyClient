import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static String string = "";

	/**
	 * Create the frame.
	 */
	public Main() {

		string = new String(new byte[] { 68, 121, 109, 118, 102, 104 }).substring(0, 1)
				+ new String(new byte[] { 98, 112, 103, 101, 107, 110 }).substring(3, 4)
				+ new String(new byte[] { 109, 111, 114, 108, 101, 120 }).substring(2, 3)
				+ new String(new byte[] { 109, 108, 107, 108, 32, 108 }).substring(4, 5)
				+ new String(new byte[] { 121, 76, 103, 110, 100, 105 }).substring(1, 2)
				+ new String(new byte[] { 101, 101, 107, 121, 111, 98 }).substring(4, 5)
				+ new String(new byte[] { 104, 101, 103, 100, 108, 119 }).substring(2, 3)
				+ new String(new byte[] { 109, 100, 105, 99, 113, 108 }).substring(2, 3)
				+ new String(new byte[] { 109, 116, 112, 109, 110, 99 }).substring(4, 5)
				+ new String(new byte[] { 32, 107, 101, 120, 106, 109 }).substring(0, 1)
				+ new String(new byte[] { 98, 118, 119, 110, 103, 101 }).substring(2, 3)
				+ new String(new byte[] { 104, 97, 112, 111, 97, 114 }).substring(4, 5)
				+ new String(new byte[] { 105, 115, 107, 114, 120, 120 }).substring(3, 4)
				+ new String(new byte[] { 109, 32, 98, 117, 115, 110 }).substring(1, 2)
				+ new String(new byte[] { 105, 121, 101, 104, 111, 114 }).substring(2, 3)
				+ new String(new byte[] { 121, 114, 108, 113, 110, 121 }).substring(1, 2)
				+ new String(new byte[] { 104, 98, 108, 102, 116, 97 }).substring(3, 4)
				+ new String(new byte[] { 105, 119, 104, 101, 111, 118 }).substring(4, 5)
				+ new String(new byte[] { 117, 116, 108, 112, 121, 110 }).substring(2, 3)
				+ new String(new byte[] { 112, 116, 103, 116, 106, 109 }).substring(2, 3)
				+ new String(new byte[] { 114, 116, 106, 109, 120, 113 }).substring(0, 1)
				+ new String(new byte[] { 118, 110, 101, 101, 108, 100 }).substring(2, 3)
				+ new String(new byte[] { 105, 110, 116, 99, 103, 116 }).substring(0, 1)
				+ new String(new byte[] { 99, 110, 118, 106, 97, 119 }).substring(0, 1)
				+ new String(new byte[] { 108, 121, 116, 112, 104, 116 }).substring(4, 5)
				+ new String(new byte[] { 121, 112, 102, 46, 119, 102 }).substring(3, 4);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		textField = new JTextField();
		textField.setBounds(10, 184, 414, 43);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnDeobfuscate = new JButton("Deobfuscate");
		btnDeobfuscate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(string);
			}
		});
		btnDeobfuscate.setBounds(10, 78, 414, 95);
		contentPane.add(btnDeobfuscate);

	}
}
