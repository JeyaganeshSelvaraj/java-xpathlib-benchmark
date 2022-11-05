package org.example.jmh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JavaXpathBenchmarkTest {

    @Test
    void testXpathJavaValue() {
        JavaXpathBenchmark xpathClass = JavaXpathBenchmark.create("sample-weather.xml", "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathJavaValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testXpathValueCompiled() {
        JavaXpathBenchmark xpathClass = JavaXpathBenchmark.create("sample-weather.xml", "/current_observation/temp_f");
        var xpathVal = xpathClass.xpathValueJavaCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("42.0", xpathVal.get());
    }

    @Test
    void testJavaXpathValueLargeXml() {
        JavaXpathBenchmark xpathClass = JavaXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathJavaValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueCompiledLargeXml() {
        JavaXpathBenchmark xpathClass = JavaXpathBenchmark.create("sample.xml",
                "/response/row/row[@_uuid='00000000-0000-0000-96F7-767F6D7170BD']/hospital");
        var xpathVal = xpathClass.xpathValueJavaCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
    
    @Test
    void testJavaXpathValueJmh() {
        JavaXpathBenchmark xpathClass = new JavaXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathJavaValue();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }

    @Test
    void testXpathValueCompiledJmh() {
        JavaXpathBenchmark xpathClass = new JavaXpathBenchmark();
        xpathClass.setup(null);
        var xpathVal = xpathClass.xpathValueJavaCompiled();
        assertTrue(xpathVal.isPresent());
        assertEquals("All SF Hospitals", xpathVal.get());
    }
}
