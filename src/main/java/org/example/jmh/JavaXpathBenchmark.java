package org.example.jmh;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.w3c.dom.Document;

import joptsimple.internal.Strings;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JavaXpathBenchmark {

    private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
    private String xpath;
    private javax.xml.xpath.XPathExpression compiledXpath;
    private Document xmlDoc;

    // this constructor is for junit testing
    static JavaXpathBenchmark create(String xmlFile, String xpath) {
        var xpathClass = new JavaXpathBenchmark();
        xpathClass.xpath = xpath;
        xpathClass.xmlDoc = XMLUtil.getRequestDocument(xmlFile);
        xpathClass.compileXpath();
        return xpathClass;
    }

    @Setup
    public void setup(BenchmarkParams params) {
        // https://stackoverflow.com/questions/6340802/java-xpath-apache-jaxp-implementation-performance
        System.setProperty("org.apache.xml.dtm.DTMManager", "org.apache.xml.dtm.ref.DTMManagerDefault");
        xpath = "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital";
        xmlDoc = XMLUtil.getRequestDocument("sample.xml");
        compileXpath();
    }

    private void compileXpath() {
        try {
            compiledXpath = XPATH_FACTORY.newXPath().compile(xpath);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public Optional<String> xpathJavaValue() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        try {
            XPath javaxXpath = XPATH_FACTORY.newXPath();
            String value = javaxXpath.evaluate(xpath, xmlDoc.getDocumentElement());
            return Optional.of(value);
        } catch (XPathExpressionException ignored) {

        }
        return Optional.empty();
    }

    @Benchmark
    public Optional<String> xpathValueJavaCompiled() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        try {
            String value = compiledXpath.evaluate(xmlDoc.getDocumentElement());
            return Optional.of(value);
        } catch (XPathExpressionException ignored) {

        }
        return Optional.empty();
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
