package com.example.persistence;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringNameStore extends NameStore {
    private static String namesFileName = "names.dsv";
    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        File file = new File(context.getFilesDir(), namesFileName);
        if (!values.isEmpty()) {
            String names = new String();
            for (int i = 0; i < values.size(); i++) {
                names += values.get(i).getFirst()
                        + "|"
                        + values.get(i).getLast()
                        + "\n";
            }
            try (FileOutputStream fos =
                         context.openFileOutput(namesFileName, Context.MODE_PRIVATE);) {
                fos.write(names.getBytes());
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Name> getNames(Context context) {
        ArrayList<Name> values = new ArrayList<Name>();
        try (FileInputStream fis =
                     context.openFileInput(namesFileName)) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader =
                         new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    String name[] = line.split("\\|");
                    values.add(new Name(name[0], name[1]));
                    line = reader.readLine();
                    }
                } catch (IOException e) {
                    // Error occurred when opening raw file for reading.
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                // File will be created on next write; return empty list.
                return values;
            } catch (Exception e) {
                new RuntimeException(e);
            }
            return values;
    }
}