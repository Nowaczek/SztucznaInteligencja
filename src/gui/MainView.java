package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import core.Main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
	private static JTextField textIloscKrajow;
	private static JTextField textIloscParSasiadow;
	private static JTextField textParySasiadow;
	private static JTextField textWynik;
	private static JTextField textWejscieSAT;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("4 Kolorowanie Mapy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,600);
        frame.getContentPane().setLayout(null);
        
        JLabel lblIloscKrajw = new JLabel("Ilosc Kraj\u00F3w:");
        lblIloscKrajw.setBounds(10, 11, 150, 14);
        frame.getContentPane().add(lblIloscKrajw);
        
        JLabel lblIloscParSsiadw = new JLabel("Ilosc Par S\u0105siad\u00F3w:");
        lblIloscParSsiadw.setBounds(10, 36, 150, 14);
        frame.getContentPane().add(lblIloscParSsiadw);
        
        textIloscKrajow = new JTextField();
        textIloscKrajow.setBounds(170, 8, 86, 20);
        frame.getContentPane().add(textIloscKrajow);
        textIloscKrajow.setColumns(10);
        
        textIloscParSasiadow = new JTextField();
        textIloscParSasiadow.setBounds(170, 33, 86, 20);
        frame.getContentPane().add(textIloscParSasiadow);
        textIloscParSasiadow.setColumns(10);
        
        JLabel lblParySasiadow = new JLabel("Pary S\u0105siad\u00F3w:");
        lblParySasiadow.setBounds(10, 61, 100, 14);
        frame.getContentPane().add(lblParySasiadow);
        
        JLabel lblPostaPastwopastwo = new JLabel("Posta\u0107: Pa\u0144stwo1#Pa\u0144stwo2");
        lblPostaPastwopastwo.setBounds(10, 73, 150, 14);
        frame.getContentPane().add(lblPostaPastwopastwo);
        
        textParySasiadow = new JTextField();
        textParySasiadow.setBounds(170, 61, 86, 270);
        frame.getContentPane().add(textParySasiadow);
        textParySasiadow.setColumns(10);
        
        JLabel lblWynik = new JLabel("Wynik:");
        lblWynik.setBounds(10, 342, 46, 14);
        frame.getContentPane().add(lblWynik);
        
        textWynik = new JTextField();
        textWynik.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textWynik.setEditable(false);
        textWynik.setBounds(10, 367, 664, 183);
        frame.getContentPane().add(textWynik);
        textWynik.setColumns(10);
        
        JLabel lblWejscieSAT = new JLabel("Wej\u015Bcie do SAT Solvera:");
        lblWejscieSAT.setBounds(330, 11, 120, 14);
        frame.getContentPane().add(lblWejscieSAT);
        
        textWejscieSAT = new JTextField();
        textWejscieSAT.setEditable(false);
        textWejscieSAT.setBounds(460, 11, 170, 323);
        frame.getContentPane().add(textWejscieSAT);
        textWejscieSAT.setColumns(10);
        
        JButton btnWykonaj = new JButton("Wykonaj");
        btnWykonaj.setBounds(10, 98, 150, 40);
        frame.getContentPane().add(btnWykonaj);
        btnWykonaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Main.core(Integer.parseInt(textIloscKrajow.getText()), Integer.parseInt(textIloscParSasiadow.getText()), textParySasiadow);
				
			}
		});
        
        frame.setVisible(true);
    }

	
}
