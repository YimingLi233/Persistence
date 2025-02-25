package com.example.persistence;
import android.content.Context;
import java.util.ArrayList;
public class DBNameStore extends NameStore {
    private static DBHandler names; // The "letter."
    DBNameStore(Context context) {
        if (names == null) {
            names = new DBHandler(context);
        }
    }
    // NameStore accepts a list of names to add, while DBHandler adds
// one Name at a time.
    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        if (!values.isEmpty()) {
            for (Name n : values) {
                names.add(n);
            }
        }
    }
    @Override
    public ArrayList<Name> getNames(Context context) {
        return names.getNames();
    }
}