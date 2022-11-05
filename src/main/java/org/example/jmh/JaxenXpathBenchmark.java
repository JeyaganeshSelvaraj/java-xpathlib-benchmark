package org.example.jmh;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
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
public class JaxenXpathBenchmark {

    private String xpath;
    private Document xmlDoc;

    private DOMXPath compiledXpath;

    // this constructor is for junit testing
    static JaxenXpathBenchmark create(String xmlFile, String xpath) {
        var xpathClass = new JaxenXpathBenchmark();
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
    public Optional<String> jaxenXpathValue() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        try {
            DOMXPath javaxXpath = new DOMXPath(xpath);
            String value = javaxXpath.stringValueOf(xmlDoc.getDocumentElement());
            return Optional.of(value);
        } catch (JaxenException ignored) {

        }
        return Optional.empty();
    }

    @Benchmark
    public Optional<String> jaxenXpathValueCompiled() {
        if (Strings.isNullOrEmpty(xpath)) {
            return Optional.empty();
        }
        try {
            String value = compiledXpath.stringValueOf(xmlDoc.getDocumentElement());
            return Optional.of(value);
        } catch (JaxenException ignored) {

        }
        return Optional.empty();
    }

    // There is no compile xpath for Jaxen. Create single instance of DOMXPath
    private void compileXpath() {
        try {
            compiledXpath = new DOMXPath(xpath);
        } catch (JaxenException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
