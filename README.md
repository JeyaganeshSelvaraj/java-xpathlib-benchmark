# java-xpathlib-benchmark

## Prerequisites
<ul>
<li>Java 17</li>
<li>Maven</li>
</ul>


## Building Application

<code>mvn clean verify</code>

## Running Benchmark

Execute benchmark.jar from the project directory

<code>java -jar target/benchmarks.jar</code>

Once all the benchmark methods are executed, below summary will be produced by JMH

<pre>
Benchmark                                     Mode  Cnt   Score   Error   Units
Dom4jXpathBenchmark.xpathValueDom4j          thrpt   25   3.895 ± 0.170  ops/ms
Dom4jXpathBenchmark.xpathValueDom4jCompiled  thrpt   25  15.779 ± 0.997  ops/ms
JavaXpathBenchmark.xpathJavaValue            thrpt   25   2.380 ± 0.244  ops/ms
JavaXpathBenchmark.xpathValueJavaCompiled    thrpt   25   2.427 ± 0.200  ops/ms
JaxenXpathBenchmark.jaxenXpathValue          thrpt   25  18.138 ± 0.517  ops/ms
JaxenXpathBenchmark.jaxenXpathValueCompiled  thrpt   25  18.092 ± 1.629  ops/ms
JdomXpathBenchmark.xpathValueCompiledJdom    thrpt   25  31.475 ± 1.369  ops/ms
JdomXpathBenchmark.xpathValueJdom            thrpt   25   3.609 ± 0.259  ops/ms
SaxonXpathBenchmark.saxonXpathValue          thrpt   25  10.725 ± 1.294  ops/ms
SaxonXpathBenchmark.saxonXpathValueCompiled  thrpt   25  14.091 ± 1.353  ops/ms
</pre>