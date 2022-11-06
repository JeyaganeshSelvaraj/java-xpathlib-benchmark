package org.example.jmh;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
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
import net.sf.saxon.xpath.XPathFactoryImpl;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SaxonXpathBenchmark {

    private String xpath;
    private Document xmlDoc;

    private static final XPathFactory XPATH_FACTORY = new XPathFactoryImpl();
    private XPathExpression compiledXpath;

    // this constructor is for junit testing
    static SaxonXpathBenchmark create(String xmlFile, String xpath) {
        var xpathClass = new SaxonXpathBenchmark();
        xpathClass.xpath = xpath;
        xpathClass.xmlDoc = XMLUtil.getRequestDocument(xmlFile);
        xpathClass.compileXpath();
        return xpathClass;
    }

    @Setup
    public void setup(BenchmarkParams params) {
        xpath = "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital";
        xmlDoc = XMLUtil.getRequestDocument("sample.xml");
        compileXpath();
    }

    @Benchmark
    public Optional<String> saxonXpathValue() {
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
    public Optional<String> saxonXpathValueCompiled() {
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

    private void compileXpath() {
        try {
            compiledXpath = XPATH_FACTORY.newXPath().compile(xpath);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
