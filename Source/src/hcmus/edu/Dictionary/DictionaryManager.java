package hcmus.edu.Dictionary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DictionaryManager {

	private List<Dictionary> listDicVA = new ArrayList<Dictionary>();
	private List<Dictionary> listDicAV = new ArrayList<Dictionary>();

	private HashMap<String, Integer> mapDate = new HashMap<String, Integer>();
	private List<List<String>> listWordOfDate = new ArrayList<List<String>>();
	private List<Dictionary> listDicForStatistics = new ArrayList<Dictionary>();
	private List<String> countDate = new ArrayList<String>();
	private List<HashMap<String, Integer>> listCountWord = new ArrayList<HashMap<String, Integer>>();

	final private String pathVietAnh = "./data/Viet_Anh.xml";
	final private String pathAnhViet = "./data/Anh_Viet.xml";
	final private String pathLikeWord = "./data/like_word.csv";
	final private String pathStatistics = "./data/statistics.xml";

	public String getPathVietAnh() {

		return pathVietAnh;
	}

	public String getPathAnhViet() {

		return pathAnhViet;
	}

	public List<String> getCountDate() {
		return countDate;
	}

	public List<HashMap<String, Integer>> getListCountWord() {
		return listCountWord;
	}

	// Read.
	public void LoadDictionary() {

		try {
			listDicVA.clear();
			listDicAV.clear();
			// Read Viet_Anh.
			File file = new File(pathVietAnh);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList dictionaryList = doc.getElementsByTagName("record");

			for (int i = 0; i < dictionaryList.getLength(); i++) {

				Node nNode = dictionaryList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Dictionary dic = new Dictionary();
					dic.setDictionary(eElement.getElementsByTagName("word").item(0).getTextContent(),
							eElement.getElementsByTagName("meaning").item(0).getTextContent());
					listDicVA.add(dic);
				}
			}
			file.exists();

			// Read Anh_Viet.
			file = new File(pathAnhViet);
			doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			dictionaryList = doc.getElementsByTagName("record");

			for (int i = 0; i < dictionaryList.getLength(); i++) {

				Node nNode = dictionaryList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Dictionary dic = new Dictionary();
					dic.setDictionary(eElement.getElementsByTagName("word").item(0).getTextContent(),
							eElement.getElementsByTagName("meaning").item(0).getTextContent());
					listDicAV.add(dic);
				}
			}
			file.exists();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Search word.
	public String searchWord(String word, String nameDic) {
		if (word != null) {
			word = word.replaceAll("\\n", "");
			switch (nameDic) {
			case "VIET-ANH":
				for (Dictionary dic : listDicVA) {
					if (dic.getWord().equals(word)) {
						return dic.getMeaning();
					}
				}
				break;
			case "ANH-VIET":
				for (Dictionary dic : listDicAV) {
					if (dic.getWord().equals(word)) {
						return dic.getMeaning();
					}
				}
				break;
			}
		}

		return null;
	}

	// Like word.
	public boolean LikeWord(String word, String meaning) {

		try {
			File file = new File(pathLikeWord);
			if (file.exists() && meaning != null && meaning != "") {
				try (FileWriter fw = new FileWriter(file, true)) {
					fw.write(word);
					fw.write("\r\n");
					fw.flush();
				}
				file.exists();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Show Like Word.
	public List<String> ShowLike() {

		List<String> listLike = new ArrayList<String>();

		try {
			File file = new File("./data/like_word.csv");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				listLike.add(scanner.nextLine());
			}
			scanner.close();
			file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listLike;
	}

	// Add word.
	public void AddElement(String word, String meaning, String path) {

		try {
			File file = new File(path);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			Element root = doc.getDocumentElement();

			Element newRecord = doc.createElement("record");
			Element eWord = doc.createElement("word");
			eWord.setTextContent(word);
			Element eMeaning = doc.createElement("meaning");
			eMeaning.setTextContent(meaning);

			newRecord.appendChild(eWord);
			newRecord.appendChild(eMeaning);

			root.appendChild(newRecord);

			Transformer tFormer = TransformerFactory.newInstance().newTransformer();
			tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
			Source source = new DOMSource(doc);
			Result result = new StreamResult(file);
			tFormer.transform(source, result);

			file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete word.
	public boolean DeleteElement(String word, String meaning, String path) {

		try {
			File file = new File(path);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList dictionaryList = doc.getElementsByTagName("record");
			for (int i = 0; i < dictionaryList.getLength(); i++) {

				Node nNode = dictionaryList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("word").item(0).getTextContent().equals(word)) {
						nNode.getParentNode().removeChild(eElement);
						Transformer tFormer = TransformerFactory.newInstance().newTransformer();
						tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
						Source source = new DOMSource(doc);
						Result result = new StreamResult(file);
						tFormer.transform(source, result);
						file.exists();
						return true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Thống kê.
	private void AddStatistics(String word, String date, String path) {

		try {
			File file = new File(path);
			if (!file.exists()) {

				file.createNewFile();

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element root = doc.createElement("statistics");

				Element newRecord = doc.createElement("record");
				Element eWord = doc.createElement("word");
				eWord.setTextContent(word);
				Element eDate = doc.createElement("date");
				eDate.setTextContent(date);

				newRecord.appendChild(eWord);
				newRecord.appendChild(eDate);

				root.appendChild(newRecord);
				doc.appendChild(root);

				Transformer tFormer = TransformerFactory.newInstance().newTransformer();
				tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
				Source source = new DOMSource(doc);
				Result result = new StreamResult(file);
				tFormer.transform(source, result);
			} else {

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(file);
				Element root = doc.getDocumentElement();

				Element newRecord = doc.createElement("record");
				Element eWord = doc.createElement("word");
				eWord.setTextContent(word);
				Element eDate = doc.createElement("date");
				eDate.setTextContent(date);

				newRecord.appendChild(eWord);
				newRecord.appendChild(eDate);

				root.appendChild(newRecord);

				Transformer tFormer = TransformerFactory.newInstance().newTransformer();
				tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
				Source source = new DOMSource(doc);
				Result result = new StreamResult(file);
				tFormer.transform(source, result);
			}

			file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WriteStatistics(String word, String date) {
		AddStatistics(word, date, pathStatistics);
	}

	public void ReadStatistics() {

		try {

			mapDate.clear();
			listWordOfDate.clear();
			listDicForStatistics.clear();
			countDate.clear();
			listCountWord.clear();

			String date;
			String word;
			int indexDate = 0;
			File file = new File(pathStatistics);
			if (!file.exists()) {
				file.createNewFile();
			}

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList dictionaryList = doc.getElementsByTagName("record");
			
			for (int i = 0; i < dictionaryList.getLength(); i++) {
				Node nNode = dictionaryList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Dictionary dic = new Dictionary();
					word = eElement.getElementsByTagName("word").item(0).getTextContent();
					date = eElement.getElementsByTagName("date").item(0).getTextContent();
					dic.setDictionary(word, date);
					listDicForStatistics.add(dic);

					if (mapDate.get(date) == null) {
						mapDate.put(date, indexDate);
						indexDate++;
					}
				}
			}
			file.exists();

			for (String str : mapDate.keySet()) {

				List<String> newList = new ArrayList<String>();
				newList.add(str);
				listWordOfDate.add(newList);
			}

			for (int i = 0; i < listDicForStatistics.size(); i++) {

				// get meaning = get date.
				for (int j = 0; j < listWordOfDate.size(); j++) {
					if (listDicForStatistics.get(i).getMeaning().equals(listWordOfDate.get(j).get(0))) {
						listWordOfDate.get(j).add(listDicForStatistics.get(i).getWord());
					}
				}
			}


			int count = 1;
			for (List<String> lstr : listWordOfDate) {

				HashMap<String, Integer> countWord = new HashMap<String, Integer>();
				for (int i = 0; i < lstr.size(); i++) {

					if (i == 0)
						countDate.add(lstr.get(i));
					else {
						if (countWord.get(lstr.get(i)) == null) {
							countWord.put(lstr.get(i), count);
						} else {
							countWord.put(lstr.get(i), countWord.get(lstr.get(i)) + 1);
						}
					}

				}
				listCountWord.add(countWord);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
