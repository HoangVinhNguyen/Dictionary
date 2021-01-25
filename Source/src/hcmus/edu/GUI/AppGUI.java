package hcmus.edu.GUI;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import hcmus.edu.Dictionary.DictionaryManager;

public class AppGUI {

	private JFrame mainFrame;
	private JRadioButton rDicAnhViet;
	private JRadioButton rDicVietAnh;
	private ButtonGroup btnG;
	private JLabel lbTop;
	private JLabel lbLang1;
	private JLabel lbLang2;
	private JTextArea txtA1;
	private JTextArea txtA2;
	private JButton btnTranslate;
	private JButton btnLike;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnShowLike;
	private JButton btnShowHis;
	private JLabel lbInfo;
	private JTextArea txtInfo;
	private DictionaryManager dicMn = new DictionaryManager();
	private String word;
	private String meaning;
	private String nameDic;
	private List<String> newWord = new ArrayList<String>();

	public void Start() {
		dicMn.LoadDictionary();
		show();
		ThongTin("Khoi dong", "", true);
	}

	private void show() {

		mainFrame = new JFrame("DICTIONARY VIET-ANH ANH-VIET");
		mainFrame.setSize(850, 700);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		mainFrame.setBackground(Color.GRAY);
		mainFrame.setResizable(false);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent windowEvent) {
			}
		});

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		rDicAnhViet = new JRadioButton("ANH-VIET");
		rDicAnhViet.setFont(new Font(rDicAnhViet.getFont().getName(), Font.PLAIN, 20));
		rDicVietAnh = new JRadioButton("VIET-ANH");
		rDicVietAnh.setFont(new Font(rDicVietAnh.getFont().getName(), Font.PLAIN, 20));
		rDicVietAnh.setSelected(true);
		btnG = new ButtonGroup();
		btnG.add(rDicVietAnh);
		btnG.add(rDicAnhViet);
		lbTop = new JLabel("Chọn từ điển: ");
		top.add(lbTop);
		top.add(rDicVietAnh);
		top.add(rDicAnhViet);

		JPanel mid1 = new JPanel();
		mid1.setLayout(new BoxLayout(mid1, BoxLayout.X_AXIS));
		lbLang1 = new JLabel("Tiếng Việt", JLabel.CENTER);
		lbLang1.setFont(new Font(lbLang1.getFont().getName(), Font.PLAIN, 20));
		lbLang2 = new JLabel("Tiếng Anh", JLabel.CENTER);
		lbLang2.setFont(new Font(lbLang2.getFont().getName(), Font.PLAIN, 20));
		mid1.add(lbLang1);
		mid1.add(Box.createHorizontalGlue());
		mid1.add(lbLang2);
		mid1.add(Box.createHorizontalGlue());

		JPanel mid2 = new JPanel();
		mid2.setLayout(new FlowLayout(FlowLayout.LEFT));
		mid2.setPreferredSize(new Dimension(845, 160));
		txtA1 = new JTextArea(8, 16);
		txtA1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		txtA1.setLineWrap(true);
		txtA1.setWrapStyleWord(true);
		JScrollPane scroll1 = new JScrollPane(txtA1);
		btnTranslate = new JButton("Dịch");
		btnTranslate.setFont(new Font(btnTranslate.getFont().getName(), Font.PLAIN, 20));
		// btnTranslate.setPreferredSize(new Dimension(70, 30));
		txtA2 = new JTextArea(8, 16);
		txtA2.setWrapStyleWord(true);
		txtA2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		txtA2.setEditable(false);
		txtA2.setBackground(new Color(250, 250, 250));
		txtA2.setLineWrap(true);
		txtA2.setWrapStyleWord(true);
		JScrollPane scroll2 = new JScrollPane(txtA2);
		mid2.add(scroll1);
		mid2.add(btnTranslate);
		mid2.add(scroll2);

		JPanel bot2 = new JPanel();
		bot2.setLayout(new FlowLayout());
		bot2.setPreferredSize(new Dimension(10, -70));
		btnLike = new JButton("Thêm vào yêu thích");
		btnLike.setPreferredSize(new Dimension(150, 40));
		btnAdd = new JButton("Thêm từ mới");
		btnAdd.setPreferredSize(new Dimension(150, 40));
		btnDel = new JButton("Xóa từ này");
		btnDel.setPreferredSize(new Dimension(150, 40));
		btnShowLike = new JButton("Xem từ yêu thích");
		btnShowLike.setPreferredSize(new Dimension(150, 40));
		btnShowHis = new JButton("Xem lịch sử");
		btnShowHis.setPreferredSize(new Dimension(150, 40));
		bot2.add(btnLike);
		bot2.add(btnAdd);
		bot2.add(btnDel);
		bot2.add(btnShowLike);
		bot2.add(btnShowHis);

		JPanel info = new JPanel();
		info.setLayout(new FlowLayout(FlowLayout.LEFT));
		info.setPreferredSize(new Dimension(5, 50));
		lbInfo = new JLabel("Thông tin");
		txtInfo = new JTextArea(10, 82);
		txtInfo.setEditable(false);
		txtInfo.setBackground(new Color(250, 250, 250));
		JScrollPane scrollInfo = new JScrollPane(txtInfo);
		info.add(lbInfo);
		info.add(scrollInfo);

		mainFrame.add(top);
		mainFrame.add(mid1);
		mainFrame.add(mid2);
		mainFrame.add(bot2);
		mainFrame.add(info);
		mainFrame.setVisible(true);

		// Event button.
		btnTranslate.setActionCommand("dich");
		btnLike.setActionCommand("like");
		btnAdd.setActionCommand("add");
		btnDel.setActionCommand("del");
		btnShowLike.setActionCommand("showLike");
		btnShowHis.setActionCommand("showHis");
		rDicAnhViet.setActionCommand(rDicAnhViet.getText());
		rDicVietAnh.setActionCommand(rDicVietAnh.getText());

		btnTranslate.addActionListener(new ButtonClickListener());
		btnLike.addActionListener(new ButtonClickListener());
		btnAdd.addActionListener(new ButtonClickListener());
		btnDel.addActionListener(new ButtonClickListener());
		btnShowLike.addActionListener(new ButtonClickListener());
		btnShowHis.addActionListener(new ButtonClickListener());
		rDicAnhViet.addActionListener(new RadioButtonClickListener());
		rDicVietAnh.addActionListener(new RadioButtonClickListener());
	}

	private void ThongTin(String step, String info, boolean isSuccessful) {

		String textCurrent = txtInfo.getText();
		switch (step) {
		case "Khoi dong":
			if (isSuccessful) {
				info = "===============================" + "\n" + "Từ điển đã sẵn sàng" + "\n";
				txtInfo.setText(textCurrent + info);
			} else {
				info = "===============================" + "\n" + "Lỗi khởi động từ điển" + "\n";
				txtInfo.setText(textCurrent + info);
			}
			break;
		case "dich":
			if (isSuccessful) {
				info = "===============================" + "\n" + "Đã dịch: " + info + "\n";
				txtInfo.setText(textCurrent + info);
			} else {
				info = "===============================" + "\n" + "Không tìm thấy." + "\n";
				txtInfo.setText(textCurrent + info);
			}
			break;
		case "like":
			if (isSuccessful) {
				info = "===============================" + "\n" + "Đã thêm " + "\"" + info + "\"" + " vào yêu thích."
						+ "\n";
				txtInfo.setText(textCurrent + info);
			} else {
				info = "===============================" + "\n" + "Thêm vào yêu thích không thành công." + "\n";
				txtInfo.setText(textCurrent + info);
			}
			break;
		case "add":
			
			break;
		case "del":
			if (isSuccessful && info != "") {
				info = "===============================" + "\n" + "Đã xóa từ " + "\"" + info + "\"" + "\n";
				txtInfo.setText(textCurrent + info);
			} if (info.equals("") && isSuccessful == false) {
				info = "===============================" + "\n" + "Từ muốn xóa phải có trong từ điển\n";
				txtInfo.setText(textCurrent + info);
			} else if (info != "" && isSuccessful == false) {
				info = "===============================" + "\n" + "Xóa từ " + "\"" + info + "\"" + " không thành công\n";
				txtInfo.setText(textCurrent + info);
			}
			break;
		case "showLike":
			if (isSuccessful) {
				info = "===============================" + "\n" + "DANH SÁCH CÁC TỪ YÊU THÍCH \n" + info + "\n";
				txtInfo.setText(textCurrent + info);
			} else {
				info = "===============================" + "\n"
						+ "Danh sách đang rỗng, nhấn \"Yêu thích\" để khởi tạo danh sách" + "\n";
				txtInfo.setText(textCurrent + info);
			}
			break;
		case "showHis":
			break;
		}
	}

	private class RadioButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			switch (command) {
			case "ANH-VIET":
				lbLang1.setText("Tiếng Anh");
				lbLang2.setText("Tiếng Việt");
				break;
			case "VIET-ANH":
				lbLang1.setText("Tiếng Việt");
				lbLang2.setText("Tiếng Anh");
				break;
			}
		}
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			word = txtA1.getText();
			nameDic = btnG.getSelection().getActionCommand().toString();
			boolean isSuccessful = true;
			
			switch (command) {
			case "dich":
				meaning = dicMn.searchWord(word, nameDic);
				txtA2.setText(meaning);
				if (meaning != null) {
					isSuccessful = true;
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					dicMn.WriteStatistics(word, formatter.format(date));
				}
				else
					isSuccessful = false;
				ThongTin(command, word, isSuccessful);
				break;
			case "like":
				isSuccessful = dicMn.LikeWord(word, meaning);
				ThongTin(command, word, isSuccessful);
				break;
			case "add":
				if (nameDic.equals("ANH-VIET")) {
					AddGUI addDic = new AddGUI(mainFrame, nameDic, dicMn.getPathAnhViet(), dicMn);
					addDic.Show();
				}
				if (nameDic.equals("VIET-ANH")) {
					AddGUI addDic = new AddGUI(mainFrame, nameDic, dicMn.getPathVietAnh(), dicMn);
					addDic.Show();
				}
				break;
			case "del":
				if (nameDic.equals("ANH-VIET")) {
					boolean tf = dicMn.DeleteElement(word, meaning, dicMn.getPathAnhViet());
					ThongTin(command, word, tf);
					if (tf)
						dicMn.LoadDictionary();
				}
				if (nameDic.equals("VIET-ANH")) {
					boolean tf = dicMn.DeleteElement(word, meaning, dicMn.getPathVietAnh());
					ThongTin(command, word, tf);
					if (tf)
						dicMn.LoadDictionary();
				}
				
				break;
			case "showLike":
				List<String> listLike = new ArrayList<String>();
				listLike = dicMn.ShowLike();
				String list = String.join("\n", listLike);
				ThongTin(command, list, listLike.size() <= 0 ? false : true);
				ShowLikeGUI showLikeGUI = new ShowLikeGUI(mainFrame);
				showLikeGUI.ReceiveDataLikeWord(listLike);
				showLikeGUI.Show();
				break;
			case "showHis":
				ShowStatistics stt = new ShowStatistics(mainFrame, dicMn);
				stt.Show();
				break;
			}
		}
	}
}
