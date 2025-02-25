package com.example.persistence;

public class Name extends Object {
    private String first;
    private String last;
    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }
    public void setName(String f, String l) {
        first = f;
        last = l;
    }
    public String getFirst() {
        return first;
    }
    public void setFirst(String f) {
        first = f;
    }
    public String getLast() {
        return last;
    }
    public void setLast(String l) {
        last = l;
    }

}
