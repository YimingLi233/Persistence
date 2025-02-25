package com.example.persistence;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
public class JSONNameStore extends NameStore{
    private static String namesFileName = "names.json";
    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        if (!values.isEmpty()) {
            Gson gson = new Gson();
            String json = gson.toJson(values);
            try (FileOutputStream fos =
                         context.openFileOutput(namesFileName, Context.MODE_PRIVATE);)
            {
                fos.write(json.getBytes());
                 fos.close();
            } catch (IOException e) {
                        throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ArrayList<Name> getNames(Context context) {
        ArrayList<Name> values = new ArrayList<Name>();
        try (FileInputStream fis = context.openFileInput(namesFileName)){
            InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader =
                         new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    // readLine() discards terminator, so add a newline
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                String json = stringBuilder.toString();
                Gson gson = new Gson();
                TypeToken<ArrayList<Name>> alType =
                        new TypeToken<ArrayList<Name>>(){};
                values = gson.fromJson(json, alType);
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            // File will be created on next write.
            return values;
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return values;
    }
}
