package hcmus.edu.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

public class ShowLikeGUI {

	private JFrame mainFrame;
	private JFrame oldFrame;
	private JButton btnAZ;
	private JButton btnZA;
	private JPanel top;
	private JPanel bot;
	private List<String> listLikeWord = new ArrayList<String>();
	private List<JLabel> listLb = new ArrayList<JLabel>();

	public ShowLikeGUI(JFrame oldFrame) {
		this.oldFrame = oldFrame;
		this.oldFrame.setEnabled(false);

		mainFrame = new JFrame("Từ yêu thích");
		mainFrame.setSize(350, 700);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				oldFrame.setEnabled(true);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
	}

	public void ReceiveDataLikeWord(List<String> listWord) {
		listLikeWord = listWord;
	}

	private void ShowList() {
		for (int i = 0; i < listLikeWord.size(); i++) {
			if (!listLikeWord.get(i).equals("")) {
				listLb.get(i).setText(String.valueOf(i) + ". " + listLikeWord.get(i));
			}
		}
		
	}

	public void Show() {

		
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.setPreferredSize(new Dimension(350, -550));
		btnAZ = new JButton("A - Z");
		btnZA = new JButton("Z - A");
		top.add(btnAZ);
		top.add(Box.createHorizontalGlue());
		top.add(btnZA);
		top.add(Box.createHorizontalGlue());
		
		
		bot = new JPanel();
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		for (int i = 0; i < listLikeWord.size(); i++) {
			if (!listLikeWord.get(i).equals("")) {
				JLabel lb = new JLabel(String.valueOf(i) + ". " + listLikeWord.get(i));
				lb.setFont(new Font(lb.getFont().getName(), Font.PLAIN, 17));
				bot.add(lb);
				listLb.add(lb);
			}
		}
		JScrollPane scrollList = new JScrollPane(bot);
		
		mainFrame.add(top);
		mainFrame.add(scrollList);

		btnAZ.setActionCommand("AZ");
		btnZA.setActionCommand("ZA");
		
		btnAZ.addActionListener(new ButtonClickListener());
		btnZA.addActionListener(new ButtonClickListener());
	}

	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			if (command.equals("AZ")) {
				Collections.sort(listLikeWord, new Comparator<String>() {
					@Override
					public int compare(String w1, String w2) {
						if (w1.compareTo(w2) > 0)
							return 1;
						if (w1.compareTo(w2) < 0)
							return -1;
						return 0;
					}
				});
				ShowList();
			}
			if (command.equals("ZA")) {

				Collections.sort(listLikeWord, new Comparator<String>() {
					@Override
					public int compare(String w1, String w2) {
						if (w1.compareTo(w2) > 0)
							return -1;
						if (w1.compareTo(w2) < 0)
							return 1;
						return 0;
					}
				});
				ShowList();
			}
		}
	}
}
