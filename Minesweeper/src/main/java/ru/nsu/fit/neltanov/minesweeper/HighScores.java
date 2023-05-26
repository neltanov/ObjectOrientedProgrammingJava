package ru.nsu.fit.neltanov.minesweeper;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.Arrays;

public class HighScores {
    private DocumentBuilder docBuilder;
    private Document document;
    private String filename;

    TransformerFactory transformerFactory;
    Transformer transformer;


    HashMap<String, ArrayList<String>> highscore = new HashMap<>();

    public HighScores(String filename) {
        try {
            this.filename = filename;
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            docBuilder = dbFactory.newDocumentBuilder();
            document = docBuilder.parse(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            assert docBuilder != null;
            resetHighscores();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToXmlFile() {
        try {
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToConsole() {
        try {
            DOMSource source = new DOMSource(document);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHighscores() {
        Element levels = document.createElement("levels");
        document.appendChild(levels);

        Element easyLevel = createLevelElement("easy");
        Element mediumLevel = createLevelElement("medium");
        Element hardLevel = createLevelElement("hard");
        levels.appendChild(easyLevel);
        levels.appendChild(mediumLevel);
        levels.appendChild(hardLevel);
    }

    private Element createLevelElement(String name) {
        Element level = document.createElement("level");
        Attr levelName = document.createAttribute("levelName");
        levelName.setValue(name);
        level.setAttributeNode(levelName);

        level.appendChild(createPlayer("none", 0));
        level.appendChild(createPlayer("none", 0));
        level.appendChild(createPlayer("none", 0));
        level.appendChild(createPlayer("none", 0));
        level.appendChild(createPlayer("none", 0));

        return level;
    }

    private Element createPlayer(String name, double value) {
        Element player = document.createElement("player");

        Attr nameAttr = document.createAttribute("name");
        nameAttr.setValue(name);
        player.setAttributeNode(nameAttr);

        player.setTextContent(((Double) value).toString());

        return player;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> parseXmlDocument() {
        HashMap<String, ArrayList<ArrayList<String>>> highscores = new HashMap<>();
        try {
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("level");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element eElement = (Element) nNode;
                NodeList pList = eElement.getElementsByTagName("player");
                ArrayList<ArrayList<String>> arr = new ArrayList<>();
                for (int i = 0; i < pList.getLength(); i++) {
                    Node pNode = pList.item(i);
                    if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element pElement = (Element) pNode;
                        if (!pElement.getAttribute("name").equals("none")) {
                            ArrayList<String> highscore = new ArrayList<>(2);
                            highscore.add(pElement.getAttribute("name"));
                            highscore.add(pElement.getTextContent());
                            arr.add(highscore);
                        }
                    }
                }
                highscores.put(eElement.getAttribute("levelName"), arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highscores;
    }

    public void updateHighscore(String levelName, String playerName, double value) {
        try {
            Node levels = document.getFirstChild();

            NodeList levelList = levels.getChildNodes();

            for (int curLevel = 0; curLevel < levelList.getLength(); curLevel++) {
                Node level = levelList.item(curLevel);
                if (level.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element lElement = (Element) level;
                if (!("level".equals(lElement.getNodeName())) || !(levelName.equals(lElement.getAttribute("levelName")))) {
                    continue;
                }

                NodeList playerList = level.getChildNodes();
                for (int curPlayer = 0; curPlayer < playerList.getLength(); curPlayer++) {
                    Node player = playerList.item(curPlayer);
                    if (player.getNodeType() == Node.ELEMENT_NODE) {
                        Element pElement = (Element) player;
                        if ("player".equals(pElement.getNodeName())) {
                            if (value <= Double.parseDouble(pElement.getTextContent()) || Objects.equals(pElement.getTextContent(), "0.0")) {
                                lElement.insertBefore(createPlayer(playerName, value), pElement);
                                lElement.removeChild(lElement.getLastChild());
                                lElement.removeChild(lElement.getLastChild());
                                writeToXmlFile();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetHighscores() {
        document = docBuilder.newDocument();
        initHighscores();
        writeToXmlFile();
    }
}
