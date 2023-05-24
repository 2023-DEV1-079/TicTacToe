package com.bnppf.tictactoe.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestsHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
