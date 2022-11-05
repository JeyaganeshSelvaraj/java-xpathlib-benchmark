package org.example.jmh;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.dom4j.Node;
import org.dom4j.xpath.DefaultXPath;
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
public class Dom4jXpathBenchmark {

    private String xpath;
    private Document xmlDoc;
    private DefaultXPath dom4jXPath;
    private org.dom4j.Document dom4jDoc;

    // this constructor is for junit testing
    static Dom4jXpathBenchmark create(String xmlFile, String xpath) {
        var xpathClass = new Dom4jXpathBenchmark();
        xpathClass.xpath = xpath;
        xpathClass.xmlDoc = XMLUtil.getRequestDocument(xmlFile);
        xpathClass.dom4jXPath = new DefaultXPath(xpath);
        xpathClass.dom4jDoc = xpathClass.convertDOMtoDOM4j();
        return xpathClass;
    }

    @Setup
    public void setup(BenchmarkParams params) {
        xpath = "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital";
        xmlDoc = XMLUtil.getRequestDocument("sample.xml");
        dom4jXPath = new DefaultXPath(xpath);
        dom4jDoc = convertDOMtoDOM4j();
    }

    @Benchmark
    public Optional<String> xpathValueDom4j() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        DefaultXPath xPath = new DefaultXPath(xpath);
        Node node = xPath.selectSingleNode(convertDOMtoDOM4j());
        return Optional.of(node.getStringValue());
    }

    @Benchmark
    public Optional<String> xpathValueDom4jCompiled() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        Node node = dom4jXPath.selectSingleNode(dom4jDoc);
        return Optional.of(node.getStringValue());
    }

    private org.dom4j.Document convertDOMtoDOM4j() {
        org.dom4j.io.DOMReader reader = new org.dom4j.io.DOMReader();
        return reader.read(xmlDoc);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
