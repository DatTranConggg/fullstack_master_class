package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigKeyTest {

    @Test
    public void testConfigKeyModel() {
        ConfigKey configKey = new ConfigKey();
        configKey.setKey("test");
        configKey.setType("test");
        configKey.setStyle(true);
        configKey.setRequired(true);
        configKey.setValidation("test");
        configKey.setFontColor(null);
        configKey.setBackGroundColor(null);
        assertEquals("test",configKey.getKey());
        assertEquals("test",configKey.getType());
        assertTrue(configKey.isStyle());
        assertTrue(configKey.isRequired());
        assertEquals("test",configKey.getValidation());
        assertNull(configKey.getFontColor());
        assertNull(configKey.getBackGroundColor());

    }
}