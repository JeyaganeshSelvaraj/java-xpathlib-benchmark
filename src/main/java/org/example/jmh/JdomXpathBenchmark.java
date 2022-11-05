package org.example.jmh;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.xpath.XPathExpression;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.w3c.dom.Document;

import com.google.common.base.Strings;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JdomXpathBenchmark {
    private static final org.jdom2.xpath.XPathFactory JDOM_FACTORY = org.jdom2.xpath.XPathFactory.instance();
    private Document xmlDoc;
    private org.jdom2.Document jdomDoc;
    private XPathExpression<Object> jdomXPathExpression;
    private String xpath;

    // this constructor is for junit testing
    static JdomXpathBenchmark create(String xmlFile, String xpath) {
        var xpathClass = new JdomXpathBenchmark();
        xpathClass.xpath = xpath;
        xpathClass.xmlDoc = XMLUtil.getRequestDocument(xmlFile);
        xpathClass.jdomDoc = xpathClass.convertDOMtoJDOM(xpathClass.xmlDoc);
        xpathClass.jdomXPathExpression = JDOM_FACTORY.compile(xpath);
        return xpathClass;
    }

    @Setup
    public void setup(BenchmarkParams params) {
        xpath = "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital";
        xmlDoc = XMLUtil.getRequestDocument("sample.xml");
        jdomDoc = convertDOMtoJDOM(xmlDoc);
        jdomXPathExpression = JDOM_FACTORY.compile(xpath);
    }

    @Benchmark
    public Optional<String> xpathValueJdom() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        XPathExpression<Object> xPathExpression = JDOM_FACTORY.compile(xpath);
        Element o = (Element) xPathExpression.evaluateFirst(convertDOMtoJDOM(xmlDoc));
        return Optional.of(o.getValue());
    }

    @Benchmark
    public Optional<String> xpathValueCompiledJdom() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        Element o = (Element) jdomXPathExpression.evaluateFirst(jdomDoc);
        return Optional.of(o.getValue());
    }

    private org.jdom2.Document convertDOMtoJDOM(Document input) {
        DOMBuilder builder = new DOMBuilder();
        return builder.build(input);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
