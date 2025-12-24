package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class WorkWithFile {
    public void getStatistic(String fromFileName, String toFileName) {
        int supply = 0;
        int buy = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fromFileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                String[] parts = line.split(",");
                if (!line.isEmpty() && parts.length == 2) {
                    String op = parts[0].trim();
                    int val = Integer.parseInt(parts[1].trim());

                    try {
                        if ("supply".equalsIgnoreCase(op)) {
                            supply += val;
                        } else if ("buy".equalsIgnoreCase(op)) {
                            buy += val;
                        }
                    } catch (NumberFormatException e) {
                        // skip invalid numeric value
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int result = supply - buy;

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(toFileName),
                        StandardCharsets.UTF_8))) {
            writer.write("supply," + supply);
            writer.newLine();
            writer.write("buy," + buy);
            writer.newLine();
            writer.write("result," + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
