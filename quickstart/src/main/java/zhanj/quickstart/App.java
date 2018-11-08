package zhanj.quickstart;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class App {
    /**
     * java Quickstart --group-id=zhanj --artifact-id=template --version=1.0-SNAPSHOT --spring-boot-version=2.0.5
     * @param args
     */
    public static void main( String[] args) {
        Options options = new Options();
        options.addRequiredOption("g", "group-id", true, "group name of this project");
        options.addRequiredOption("a", "artifact-id", true, "name of this project");
        options.addOption("v", "version",true, "version of this project");
        options.addOption("sv", "spring-boot-version", true, "version of spring boot");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            String groupId = line.getOptionValue("g");
            String artifactId = line.getOptionValue("a");
            String version = line.getOptionValue("v", "1.0-SNAPSHOT");
            String springBootVersion = line.getOptionValue("sv", "2.0.5");

            // make directory

            String sourceDir = artifactId + File.separator
                    + "src" + File.separator
                    + "main" + File.separator
                    + "java" + File.separator
                    + groupId + File.separator
                    + artifactId;
            createDirectory(sourceDir);

            String resourcesDir = artifactId + File.separator
                    + "src" + File.separator
                    + "main" + File.separator
                    + "resources";
            createDirectory(resourcesDir);

            // generate pom.xml

            InputStream pom = ClassLoader.getSystemResourceAsStream("pom.xml.template");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pom, StandardCharsets.UTF_8));
            Optional<String> result = reader.lines().map(l -> l.replaceAll("\\{\\{ group-id }}", groupId))
                    .map(l -> l.replaceAll("\\{\\{ artifact-id }}", artifactId))
                    .map(l -> l.replaceAll("\\{\\{ version }}", version))
                    .map(l -> l.replaceAll("\\{\\{ spring-boot-version }}", springBootVersion))
                    .reduce((l1, l2)-> l1 + "\n" + l2);
            reader.close();
            if (result.isPresent()) {
                writeToFile(result.get(), artifactId + File.separator + "pom.xml");
            }

            // generate application.properties
            File file = new File(resourcesDir + File.separator + "application.properties");
            if (!file.createNewFile()) {
                System.err.println("failed to create file " + file.getPath());
                return;
            }

            // generate App.java
            InputStream app = ClassLoader.getSystemResourceAsStream("App.java.template");
            reader = new BufferedReader(new InputStreamReader(app, StandardCharsets.UTF_8));
            result = reader.lines().map(l -> l.replaceAll("\\{\\{ group-id }}", groupId))
                    .map(l -> l.replaceAll("\\{\\{ artifact-id }}", artifactId))
                    .reduce((l1, l2) -> l1 + "\n" + l2);
            reader.close();
            if (result.isPresent()) {
                writeToFile(result.get(), sourceDir + File.separator + "App.java");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static void createDirectory(String dir) throws Exception {
        File file = new File(dir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new Exception("failed to create directory " + dir);
            }
        }
    }

    private static void writeToFile(String content, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
