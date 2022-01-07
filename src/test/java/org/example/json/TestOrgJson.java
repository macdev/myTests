package org.example.json;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestOrgJson {

    @Test
    public void checkNullValue() {
        Map<String, Object> objMap = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        objMap.put("body", body);
        body.put("name", "john");
        body.put("age", "");
        JSONObject json = new JSONObject(objMap);

        assertEquals("john", json.getJSONObject("body").getString("name"));
        assertEquals("", json.getJSONObject("body").getString("age"));
        assertEquals(true, json.getJSONObject("body").isNull("nothing"));
        assertEquals(false, json.getJSONObject("body").has("nothing"));
    }
}
