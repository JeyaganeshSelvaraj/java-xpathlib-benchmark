package org.example.jmh;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class XMLUtil {

    private static final BlockingQueue<DocumentBuilder> documentBuilderPool;
    private static final Map<Short, String> typeList = new HashMap<>();
    private static long timeOut = 5000L;

    static {
        int poolSize = Runtime.getRuntime().availableProcessors();
        documentBuilderPool = new ArrayBlockingQueue<>(poolSize);
        try {
            for (int i = 0; i < poolSize; ++i) {
                returnDocumentBuilder(createDocumentBuilder());
            }
        } catch (ParserConfigurationException var2) {
        }

        typeList.put(Short.valueOf((short) 1), "ELEMENT_NODE");
        typeList.put(Short.valueOf((short) 2), "ATTRIBUTE_NODE");
        typeList.put(Short.valueOf((short) 3), "TEXT_NODE");
        typeList.put(Short.valueOf((short) 4), "CDATA_SECTION_NODE");
        typeList.put(Short.valueOf((short) 5), "ENTITY_REFERENCE_NODE");
        typeList.put(Short.valueOf((short) 6), "ENTITY_NODE");
        typeList.put(Short.valueOf((short) 7), "PROCESSING_INSTRUCTION_NODE");
        typeList.put(Short.valueOf((short) 8), "COMMENT_NODE");
        typeList.put(Short.valueOf((short) 9), "DOCUMENT_NODE");
        typeList.put(Short.valueOf((short) 10), "DOCUMENT_TYPE_NODE");
        typeList.put(Short.valueOf((short) 11), "DOCUMENT_FRAGMENT_NODE");
        typeList.put(Short.valueOf((short) 12), "NOTATION_NODE");
    }

    private XMLUtil() {
    }

    public static Document getDocument(InputStream inputStream) throws IOException, SAXException {
        DocumentBuilder documentBuilder = getDocumentBuilder();

        Document var2;
        try {
            var2 = documentBuilder.parse(inputStream);
        } finally {
            returnDocumentBuilder(documentBuilder);
        }

        return var2;
    }
    public static Document getRequestDocument(String filePath) {
        try {
            return getDocument(XMLUtil.class.getClassLoader().getResourceAsStream(filePath));
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
    static DocumentBuilder getDocumentBuilder() {
        DocumentBuilder builder = null;

        try {
            builder = documentBuilderPool.poll(timeOut, TimeUnit.MILLISECONDS);
            if (builder == null) {
                builder = createDocumentBuilder();
            }

            return builder;
        } catch (ParserConfigurationException var3) {

            throw new XMLUtilException(var3.getMessage(), var3);
        } catch (InterruptedException var4) {
            String msg = "Interrupted waiting for XML document builder";

            throw new XMLUtilException(msg, var4);
        }
    }

    static void returnDocumentBuilder(DocumentBuilder builder) {
        if (builder != null) {
            documentBuilderPool.offer(builder);
        }

    }

    static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new DefaultEntityResolverImpl());
        builder.setErrorHandler(new DefaultErrorHandlerImpl());
        return builder;
    }

    public static final class XMLUtilException extends RuntimeException {
        private static final long serialVersionUID = 8611809617978513986L;

        public XMLUtilException(String message) {
            super(message);
        }

        public XMLUtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

class DefaultEntityResolverImpl implements EntityResolver {
    public DefaultEntityResolverImpl() {
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return new InputSource(new StringReader(""));
    }
}

class DefaultErrorHandlerImpl implements ErrorHandler {

    private boolean errors = false;

    public DefaultErrorHandlerImpl() {
    }

    public void warning(SAXParseException exception) throws SAXException {
        this.logError("Warning-", exception);
    }

    public void error(SAXParseException exception) throws SAXException {
        this.errors = true;
        this.logError("Error-", exception);
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        this.errors = true;
        this.logError("Fatal-", exception);
    }

    public boolean hadErrors() {
        return this.errors;
    }

    private void logError(String message, SAXParseException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append("line [");
        sb.append(ex.getLineNumber());
        sb.append("] column [");
        sb.append(ex.getColumnNumber());
        sb.append("] : ");
        sb.append(ex.getMessage());
        sb.append("\n");
    }
    
}
