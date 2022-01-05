package com.malevtool.JSON;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONBuilder {
    private final StringBuilder builder = new StringBuilder();
    private boolean isOpened = false, isClosed = false;
    private int avpCount = 0, arrayElemCount = 0;

    public JSONBuilder open() {
        if(!isOpened) {
            builder.append("{");
            isOpened = true;
        }
        return this;
    }

    public JSONBuilder close() {
        if(!isClosed) {
            builder.append("\n}");
            isClosed = true;
        }
        return this;
    }

    public String getString() {
        if(!isClosed)
            close();
        return builder.toString();
    }

    public JSONArray getJSON() {
        try {
            return (JSONArray) new JSONParser().parse(getString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONBuilder addAVP(String argument, Object value) {
        if(!isOpened)
            open();
        if(avpCount > 0)
            builder.append(",");
        builder.append("\n\"").append(argument).append("\":\"").append(value.toString()).append("\"");
        avpCount++;
        return this;
    }

    public JSONBuilder openArray(String arrName) {
        if(!isOpened)
            open();
        if(avpCount > 0)
            builder.append(",");
        builder.append("\n\"").append(arrName).append("\":[");
        return this;
    }

    public JSONBuilder addArrayElement(String value) {
        if(arrayElemCount > 0)
            builder.append(",");
        builder.append("\n\"").append(value).append("\"");
        arrayElemCount++;
        return this;
    }

    public JSONBuilder addSubJSONElement(String json) {
        if(arrayElemCount > 0)
            builder.append(",");
        builder.append("\n").append(json);
        arrayElemCount++;
        return this;
    }

    public JSONBuilder closeArray() {
        builder.append("\n]");
        avpCount++;
        return this;
    }
}