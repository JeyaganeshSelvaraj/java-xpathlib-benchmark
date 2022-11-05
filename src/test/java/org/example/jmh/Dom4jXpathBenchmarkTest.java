package org.example.jmh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Dom4jXpathBenchmarkTest {

    @Test
    void testXpathValueDom4j() {
        Dom4jXpathBenchmark xpathClass = Dom4jXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathValueDom4j();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testXpathValueDom4jCompiled() {
        Dom4jXpathBenchmark xpathClass = Dom4jXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathValueDom4jCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testXpathValueDom4jLargeXml() {
        Dom4jXpathBenchmark xpathClass = Dom4jXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathValueDom4j();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueDom4jCompiledLargeXml() {
        Dom4jXpathBenchmark xpathClass = Dom4jXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathValueDom4jCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
    @Test
    void testXpathValueDom4jJmh() {
        Dom4jXpathBenchmark xpathClass = new Dom4jXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathValueDom4j();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueDom4jCompiledJmh() {
        Dom4jXpathBenchmark xpathClass = new Dom4jXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathValueDom4jCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
}
