package org.example.jmh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JaxenXpathBenchmarkTest {

    @Test
    void testJaxenXpathValue() {
        JaxenXpathBenchmark xpathClass = JaxenXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.jaxenXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testJaxenXpathValueCompiled() {
        JaxenXpathBenchmark xpathClass = JaxenXpathBenchmark.create("sample-weather.xml",
                "/current_observation/temp_f");
        var xpathVal = xpathClass.jaxenXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testJaxenXpathValueLargeXml() {
        JaxenXpathBenchmark xpathClass = JaxenXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.jaxenXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testJaxenXpathValueCompiledLargeXml() {
        JaxenXpathBenchmark xpathClass = JaxenXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.jaxenXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
    @Test
    void testJaxenXpathValueJmh() {
        JaxenXpathBenchmark xpathClass = new JaxenXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.jaxenXpathValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testJaxenXpathValueCompiledJmh() {
        JaxenXpathBenchmark xpathClass = new JaxenXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.jaxenXpathValueCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
}
