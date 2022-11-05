package org.example.jmh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JdomXpathBenchmarkTest {

    @Test
    void testXpathValueJdom() {
        JdomXpathBenchmark xpathClass = JdomXpathBenchmark.create("sample-weather.xml", "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathValueJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testXpathValueCompiledJdom() {
        JdomXpathBenchmark xpathClass = JdomXpathBenchmark.create("sample-weather.xml", "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathValueCompiledJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testXpathValueJdomLargeXml() {
        JdomXpathBenchmark xpathClass = JdomXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathValueJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueCompiledJdomLargeXml() {
        JdomXpathBenchmark xpathClass = JdomXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathValueCompiledJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
    @Test
    void testXpathValueJdomJmh() {
        JdomXpathBenchmark xpathClass = new JdomXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathValueJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueCompiledJdomJmh() {
        JdomXpathBenchmark xpathClass = new JdomXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathValueCompiledJdom();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

}
