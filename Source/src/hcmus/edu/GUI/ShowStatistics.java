package hcmus.edu.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import hcmus.edu.Dictionary.DictionaryManager;

public class ShowStatistics {

	private JFrame mainFrame;
	private JFrame oldFrame;
	private JPanel top;
	private JPanel bot;
	private JPanel bot2;
	private JLabel lbDate;
	private JButton btnDate;
	private JButton btnExit;
	private JComboBox cbxDate1;
	private JComboBox cbxDate2;
	private JTable tbStatistics;
	private DictionaryManager dicMn = new DictionaryManager();
	private List<String> countDate = new ArrayList<String>();
	private List<HashMap<String, Integer>> listCountWord = new ArrayList<HashMap<String, Integer>>();
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	final private String column[] = { "Từ đã dịch", "Số lần" };
	private String[][] dataOffical;

	public ShowStatistics(JFrame oldFrame, DictionaryManager dicmanager) {

		this.oldFrame = oldFrame;
		oldFrame.setEnabled(false);
		mainFrame = new JFrame("Thống kê dịch từ");
		mainFrame.setSize(500, 700);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				oldFrame.setEnabled(true);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

		this.dicMn = dicmanager;
		dicMn.ReadStatistics();
		countDate = dicMn.getCountDate();
		listCountWord = dicMn.getListCountWord();
	}

	private void PrepairData(String date1, String date2) {

		try {

			data.clear();
			// sort date list and word list.
			for (int i = 0; i < countDate.size(); i++) {
				for (int j = i; j < countDate.size(); j++) {
					Date ddate1 = new SimpleDateFormat("dd/MM/yyyy").parse(countDate.get(i));
					Date ddate2 = new SimpleDateFormat("dd/MM/yyyy").parse(countDate.get(j));

					if (ddate1.after(ddate2)) {
						Collections.swap(countDate, i, j);
						Collections.swap(listCountWord, i, j);
					}
				}
			}

			Date ddate1 = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
			Date ddate2 = new SimpleDateFormat("dd/MM/yyyy").parse(date2);

			if (ddate1.after(ddate2)) {
				String temp = date1;
				date1 = date2;
				date2 = temp;

				Date tempDate = ddate1;
				ddate1 = ddate2;
				ddate2 = tempDate;
			}

			for (int i = 0; i < countDate.size(); i++) {
				Date ddCompair = new SimpleDateFormat("dd/MM/yyyy").parse(countDate.get(i));
				if (ddate1.compareTo(ddCompair) <= 0 && ddate2.compareTo(ddCompair) >= 0) {
					Object[] key = listCountWord.get(i).keySet().toArray();
					Object[] values = listCountWord.get(i).values().toArray();
					for (int j = 0; j < key.length; j++) {
						if (data.get(key[j]) == null) {
							data.put(String.valueOf(key[j]), Integer.valueOf(values[j].toString()));
						} else {
							data.put(String.valueOf(key[j]), data.get(key[j]) + Integer.valueOf(values[j].toString()));
						}
					}
				}
			}

			Object[] key = data.keySet().toArray();
			Object[] values = data.values().toArray();
			List<List<String>> ddata = new ArrayList<List<String>>();
			for (int i = 0; i < key.length; i++) {
				List<String> list = new ArrayList<String>();
				list.add(String.valueOf(key[i]));
				list.add(String.valueOf(values[i].toString()));
				ddata.add(list);
			}
			dataOffical = new String[ddata.size()][2];
			for (int i = 0; i < dataOffical.length; i++) {
				dataOffical[i][0] = ddata.get(i).get(0);
				dataOffical[i][1] = ddata.get(i).get(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TableModel getTableModel(String[][] aData, String[] aColumnNames) {
		TableModel model = new DefaultTableModel(aData, aColumnNames) {
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		return model;
	}

	public void Show() {

		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.setPreferredSize(new Dimension(500, 50));
		lbDate = new JLabel("Chọn khoảng ngày cần thống kê: ");
		cbxDate1 = new JComboBox(countDate.toArray());
		cbxDate2 = new JComboBox(countDate.toArray());
		btnDate = new JButton("Thống kê");
		top.add(lbDate);
		top.add(cbxDate1);
		top.add(cbxDate2);
		top.add(btnDate);

		bot = new JPanel(new BorderLayout());
		bot.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bảng thống kê",
				TitledBorder.CENTER, TitledBorder.TOP));
		tbStatistics = new JTable();
		tbStatistics.getTableHeader().setReorderingAllowed(true);
		tbStatistics.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tbStatistics.setFillsViewportHeight(true);
		JScrollPane scrollTb = new JScrollPane();
		scrollTb.add(tbStatistics);
		scrollTb.setViewportView(tbStatistics);
		bot.add(scrollTb);

		bot2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnExit = new JButton("Thoát");
		bot2.add(btnExit);

		mainFrame.add(top);
		mainFrame.add(bot);
		mainFrame.add(bot2);
		mainFrame.setVisible(true);

		btnDate.setActionCommand("date");
		btnExit.setActionCommand("exit");
		btnDate.addActionListener(new ButtonClickListener());
		btnExit.addActionListener(new ButtonClickListener());
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			String date1 = cbxDate1.getItemAt(cbxDate1.getSelectedIndex()).toString();
			String date2 = cbxDate2.getItemAt(cbxDate2.getSelectedIndex()).toString();
			if (command.equals("date")) {

				// set date and resault.
				PrepairData(date1, date2);
				TableModel model = getTableModel(dataOffical, column);
				tbStatistics.setModel(model);
			}
			if (command.equals("exit")) {
				oldFrame.setEnabled(true);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				mainFrame.dispose();
			}
		}
	}

}
