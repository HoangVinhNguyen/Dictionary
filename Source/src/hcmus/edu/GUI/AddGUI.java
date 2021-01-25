package hcmus.edu.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hcmus.edu.Dictionary.DictionaryManager;

public class AddGUI {

	private JFrame mainFrame;
	private JFrame oldFrame;
	private JLabel lbWord;
	private JLabel lbMeaning;
	private JLabel lbTitle;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnNotSave;
	private JTextArea txtWord;
	private JTextArea txtMeaning;
	private JPanel title;
	private JPanel top;
	private JPanel bot;
	private String nameDic;
	private String pathDic;
	private DictionaryManager dicMn;
	private List<String> newWord = new ArrayList<String>();
	
	public List<String> getListNewWord(){
		return newWord;
	}

	public AddGUI(JFrame oldFrame, String nameDictionary, String path, DictionaryManager dicmanager) {
		this.oldFrame = oldFrame;
		this.oldFrame.setEnabled(false);

		mainFrame = new JFrame("Từ yêu thích");
		mainFrame.setSize(500, 560);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dicMn.LoadDictionary();
				oldFrame.setEnabled(true);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

		nameDic = nameDictionary;
		pathDic = path;
		dicMn = dicmanager;
	}

	public void Show() {

		mainFrame.getContentPane().setLayout(
				new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		lbWord = new JLabel("Từ cần thêm");
		lbWord.setFont(new Font(lbWord.getFont().getName(), Font.PLAIN, 15));
		lbMeaning = new JLabel("Nghĩa");
		lbMeaning.setFont(new Font(lbMeaning.getFont().getName(), Font.PLAIN, 15));

		title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbTitle = new JLabel(nameDic);
		lbTitle.setFont(new Font(lbTitle.getFont().getName(), Font.PLAIN, 30));
		title.add(lbTitle);

		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		txtWord = new JTextArea(8, 16);
		txtWord.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtWord.setLineWrap(true);
		txtWord.setWrapStyleWord(true);
		JScrollPane scrollWord = new JScrollPane(txtWord);
		txtMeaning = new JTextArea(8, 16);
		txtMeaning.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtMeaning.setLineWrap(true);
		txtMeaning.setWrapStyleWord(true);
		JScrollPane scrollMeaning = new JScrollPane(txtMeaning);
		top.add(lbWord);
		top.add(scrollWord);
		top.add(lbMeaning);
		top.add(scrollMeaning);

		bot = new JPanel();
		bot.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnOK = new JButton("Đồng ý thêm");
		btnCancel = new JButton("Đóng");
		btnNotSave = new JButton("Không lưu");
		bot.add(btnOK);
		bot.add(btnCancel);
		//bot.add(btnNotSave);

		mainFrame.add(title);
		mainFrame.add(top);
		mainFrame.add(bot);

		btnOK.setActionCommand("OK");
		btnCancel.setActionCommand("Cancel");
		btnNotSave.setActionCommand("Not Save");

		btnOK.addActionListener(new ButtonClickListener());
		btnCancel.addActionListener(new ButtonClickListener());
		btnNotSave.addActionListener(new ButtonClickListener());
	}

	

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if (command.equals("OK")) {
				System.out.println("Thêm");
				String word = txtWord.getText();
				String meaning = txtMeaning.getText();
				if (word != null && word != "" && meaning != null && meaning != "") {
					dicMn.AddElement(word, meaning, pathDic);
					newWord.add(word);
					newWord.add(meaning);
				}
			}
			if (command.equals("Cancel")) {
				dicMn.LoadDictionary();
				oldFrame.setEnabled(true);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				mainFrame.dispose();
			}
		}
	}
}
