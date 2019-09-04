# jars-pommefizer
Generates pom.xml file from those `/libs` directories containing a bunch of JAR-files

### Build
```shell script
mvn install
```

### Usage
```shell script
Usage: jars-pommefizer [options] <jars-dir-path>

  -o, --output <file>  Output POM-file path
  --help               prints this usage text
  <jars-dir-path>      Path to a directory that contains a list of JAR-files
```

### Example
```shell script
java -jar target/jars-pommefizer-1.0.jar ~/spark-2.4/jars
```
returns:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <dependencies>
        <dependency>
            <groupId>org.antlr</groupId>
            <artifactId>stringtemplate</artifactId>
            <version>3.2.1</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>net.sf.supercsv</groupId>
            <artifactId>super-csv</artifactId>
            <version>2.2.0</version>
            <scope>provided</scope>
        </dependency>

        ... and 224 other dependencies ...

    </dependencies>
</project>
```

Happy POMefizing!