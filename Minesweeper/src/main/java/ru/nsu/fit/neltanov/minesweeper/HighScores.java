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
import java.util.Objects;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class HighScores {
    private DocumentBuilder docBuilder;
    private Document document;
    private String filename;

    TransformerFactory transformerFactory;
    Transformer transformer;

    public HighScores(String filename) {
        try {
            this.filename = filename;
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            docBuilder = dbFactory.newDocumentBuilder();
            document = docBuilder.parse(new File(filename));
//            writeToConsole();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            assert docBuilder != null;
            resetHighscores();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToXmlFile() {
        try {
            // Write the content into xml file

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

    public void parseXmlDocument() {
        try {
            File inputFile = new File("./src/main/resources/highScores.xml");

            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Student roll no : "
                            + eElement.getAttribute("rollno"));
                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("lastname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Nick Name : "
                            + eElement
                            .getElementsByTagName("nickname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Marks : "
                            + eElement
                            .getElementsByTagName("marks")
                            .item(0)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                                writeToXmlFile();
                                break;
                            }
                        }
                    }
                }
            }
            writeToConsole();
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
