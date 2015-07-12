package com.coltsoftware.jobb;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlBuilder {

    private Zipper.ZipResult zipperResult;

    public XmlBuilder(Zipper.ZipResult zipperResult) {
        this.zipperResult = zipperResult;
    }

    public void build(File file) {
        try {
            buildXml(zipperResult.getAddedFiles(), file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static void buildXml(List<Zipper.ZipResult.ZipEntryDetails> addedFiles, File target) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element resources = document.createElement("resources");
        document.appendChild(resources);

        for (Zipper.ZipResult.ZipEntryDetails file : addedFiles) {
            Element string = document.createElement("string");
            String text = encodeFileName(file);
            string.appendChild(document.createTextNode(text));
            string.setAttribute("name", getNameAttribute(file));
            resources.appendChild(string);
        }

        saveXml(target, document);
    }

    private static String encodeFileName(Zipper.ZipResult.ZipEntryDetails file) {
        return "obb:" + file.getRelativeFileName().replace("\\", "\\\\");
    }

    private static void saveXml(File target, Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(target);

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(source, result);
    }

    private static String getNameAttribute(Zipper.ZipResult.ZipEntryDetails file) {
        String relativeFileName = file.getRelativeFileName();
        int extension = relativeFileName.lastIndexOf('.');
        if (extension != -1)
            relativeFileName = relativeFileName.substring(0, extension);
        relativeFileName = relativeFileName.replaceAll("[\\\\/]", "_").toLowerCase();
        return relativeFileName;
    }
}
