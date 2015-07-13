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

    private final String prefix;
    private Zipper.ZipResult zipperResult;
    private Args args;

    public XmlBuilder(Zipper.ZipResult zipperResult, Args args) {
        this.zipperResult = zipperResult;
        this.args = args;
        prefix = args.getPrefix();
    }

    public void build(File file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            new BuildAction(documentBuilder).buildXml(zipperResult.getAddedFiles(), file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private class BuildAction {
        private Document document;
        private Element resources;

        BuildAction(DocumentBuilder documentBuilder) {
            document = documentBuilder.newDocument();
            resources = document.createElement("resources");
            document.appendChild(resources);
        }

        private void buildXml(List<Zipper.ZipResult.ZipEntryDetails> addedFiles, File target) throws TransformerException {

            createResourceElement("integer", AndroidResourceKey.fromString(args.getForename() + "ObbSize"), String.valueOf(zipperResult.getSize()));
            createResourceElement("integer", AndroidResourceKey.fromString(args.getForename() + "ObbVersion"), String.valueOf(args.getPackageVersion()));

            for (Zipper.ZipResult.ZipEntryDetails file : addedFiles)
                createResourceElement("string", getNameAttribute(file), encodeFileName(file));

            saveXml(target);
        }

        private void createResourceElement(String type, AndroidResourceKey name, String value) {
            Element string = document.createElement(type);
            string.appendChild(document.createTextNode(value));
            string.setAttribute("name", name.toString());
            resources.appendChild(string);
        }

        private void saveXml(File target) throws TransformerException {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(target);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(source, result);
        }

        private AndroidResourceKey getNameAttribute(Zipper.ZipResult.ZipEntryDetails file) {
            String relativeFileName = file.getRelativeFileName();
            int extension = relativeFileName.lastIndexOf('.');
            if (extension != -1)
                relativeFileName = relativeFileName.substring(0, extension);
            return AndroidResourceKey.fromString(relativeFileName);
        }
    }

    private String encodeFileName(Zipper.ZipResult.ZipEntryDetails file) {
        String textValue = prefix + file.getRelativeFileName();
        return encodeAndroidResourceValue(textValue);
    }

    private static String encodeAndroidResourceValue(String textValue) {
        return textValue.replace("\\", "\\\\").replace("'", "\\'");
    }
}
