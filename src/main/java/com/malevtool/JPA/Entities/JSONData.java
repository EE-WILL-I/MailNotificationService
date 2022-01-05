package com.malevtool.JPA.Entities;

import org.json.simple.parser.ParseException;

public interface JSONData {
    String toJSONString();
    Object[] fromJSONString(String json) throws ParseException, IllegalArgumentException;
}
