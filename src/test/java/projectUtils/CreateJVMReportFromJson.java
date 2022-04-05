package projectUtils;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.reducers.ReducingMethod;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.json.simple.parser.ParseException;
import projectUtils.processors.PropertiesProcessor;
import projectUtils.processors.PropertyTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateJVMReportFromJson {

    public static void generateJVMReport(File reportOutputDirectory) throws IOException, ParseException {
        List<String> jsonFiles = new ArrayList<>();
        String pathToJsonFile = "target/cucumber-JSON-report/cucumber.json";
//        removeHooksFromJson(Paths.get(pathToJsonFile));
        jsonFiles.add(pathToJsonFile);

        String buildNumber = "1";
        String projectName = "TH - automation";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);

        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Environment", PropertiesProcessor.getPropertyByKey(PropertyTypes.RUN_PROPS, "environment.to.use"));
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.addReducingMethod(ReducingMethod.HIDE_EMPTY_HOOKS);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
        convertHtmlToPDF(reportOutputDirectory);
    }

    private static void removeHooksFromJson(Path pathToJsonFile) throws IOException, ParseException {
        JsonParser jsonParser = new JsonParser();
        String content = new String(Files.readAllBytes(pathToJsonFile));
        JsonElement mainContent = jsonParser.parse(content);

        JsonArray features = mainContent.getAsJsonArray();
        for (int i = 0; features.size() > i; i++) {
            JsonObject feature = features.get(i).getAsJsonObject();
            feature.remove("before");
            feature.remove("after");

            JsonArray scenarios = feature.get("elements").getAsJsonArray();
            for (int j = 0; scenarios.size() > j; j++) {
                JsonObject scenario = scenarios.get(j).getAsJsonObject();
                scenario.remove("before");
                scenario.remove("after");

                JsonArray steps = scenario.get("steps").getAsJsonArray();
                for (int k = 0; steps.size() > k; k++) {
                    JsonObject step = steps.get(k).getAsJsonObject();
                    JsonElement sshot = step.get("after").getAsJsonArray().get(0).getAsJsonObject().get("embeddings");
                    step.getAsJsonObject().add("embeddings", sshot);
                    step.remove("after");
                }
            }
        }

        Files.write(pathToJsonFile, mainContent.getAsJsonArray().toString().getBytes());
    }

    public static void convertHtmlToPDF(File reportOutputDirectory) {
        File directoryWithHtmlReports = new File(reportOutputDirectory.toString() + "\\cucumber-html-reports");
        File[] files = directoryWithHtmlReports.listFiles((d, name) -> name.endsWith(".html"));
        Arrays.stream(files)
            .forEach(el -> {
                String absolutePath = el.getAbsolutePath();
                ProcessBuilder builder = new ProcessBuilder(
                    "C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe",
                    "--headless",
                    "--run-all-compositor-stages-before-draw",
                    "--virtual-time-budget=20100",
                    "--print-to-pdf=\"" +
                        absolutePath.replace("cucumber-html-reports\\", "")
                            .replace("html", "pdf") + "\"",
                    absolutePath
                );
                try {
                    Process p = builder.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
