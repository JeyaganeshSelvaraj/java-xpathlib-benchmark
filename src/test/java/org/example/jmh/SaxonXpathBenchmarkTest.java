package org.example.jmh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SaxonXpathBenchmarkTest {

    @Test
    void testSaxonXpathValue() {
        SaxonXpathBenchmark xpathClass = SaxonXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.saxonXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testSaxonXpathValueCompiled() {
        SaxonXpathBenchmark xpathClass = SaxonXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.saxonXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testSaxonXpathValueLargeXml() {
        SaxonXpathBenchmark xpathClass = SaxonXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.saxonXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testSaxonXpathValueCompiledLargeXml() {
        SaxonXpathBenchmark xpathClass = SaxonXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.saxonXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
    @Test
    void testSaxonXpathJmh() {
        SaxonXpathBenchmark xpathClass = new SaxonXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.saxonXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testSaxonXpathValueCompiledJmh() {
        SaxonXpathBenchmark xpathClass = new SaxonXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.saxonXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
}
