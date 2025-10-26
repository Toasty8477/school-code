/*
 * Course: CSC-1120A
 * Lab 11: Morse Encoder
 * Name: Alexander Horton
 * Last Updated: 04/15/2025
 */

package test;

import hortona.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Test suite for MorseEncoder
 */
public class TestSuite {
    private HashMap<Character, String> hashMap;
    private final Path path = Paths.get("data", "morseCode.txt");

    @BeforeEach
    void setup() {
        hashMap = new HashMap<>();
    }

    @Test
    @DisplayName("Testing Entry class internals")
    void entryInternals() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> outer = HashMap.class;
        Class<?> entryClass = null;
        for (Class<?> declaredClass : outer.getDeclaredClasses()) {
            if (declaredClass.getSimpleName().equals("HashMapEntry")) {
                entryClass = declaredClass;
            }
        }
        Assertions.assertNotNull(entryClass,
                "Inner class should be called HashMapEntry");
        Constructor<?> constructor = entryClass.getDeclaredConstructor(Object.class, Object.class);
        constructor.setAccessible(true);
        Object entry = constructor.newInstance("sampleKey", "sampleValue");

        Assertions.assertInstanceOf(Map.Entry.class, entry,
                "Entry class should implement Map.Entry");

        Field key = entry.getClass().getDeclaredField("key");
        int modifiers = key.getModifiers();
        Field value = entry.getClass().getDeclaredField("value");
        key.setAccessible(true);
        value.setAccessible(true);
        Map.Entry<Object, Object> mapEntry = (Map.Entry<Object, Object>) entry;
        Assertions.assertTrue(Modifier.isFinal(modifiers),
                "Variable key should be final");
        Assertions.assertAll(
                () -> Assertions.assertEquals("sampleKey", key.get(entry),
                        "Key should contain \"sampleKey\" but contains" + key.get(entry)),
                () -> Assertions.assertEquals("sampleValue", value.get(entry),
                        "Value should contain \"sampleValue\" but contains" + value.get(entry)),
                () -> Assertions.assertEquals("sampleKey", mapEntry.getKey(),
                        "getKey() returns incorrect value"),
                () -> Assertions.assertEquals("sampleValue", mapEntry.getValue(),
                        "getValue() returns incorrect value"),
                () -> Assertions.assertEquals("sampleValue", mapEntry.setValue("test"),
                        "setValue() should return the previous value \"sampleValue\""),
                () -> Assertions.assertEquals("test", mapEntry.getValue(),
                        "After calling setValue(), the value of the entry should be \"test\" " +
                                "but is " + mapEntry.getValue()),
                () -> Assertions.assertEquals("sampleKey=>test", mapEntry.toString(),
                        "Incorrect toString result")
        );
    }

    @Test
    @DisplayName("Testing HashMap internals")
    void hashMapInternals() throws NoSuchFieldException, IllegalAccessException {
        Field entriesField = hashMap.getClass().getDeclaredField("entries");
        entriesField.setAccessible(true);
        Assertions.assertInstanceOf(List[].class, entriesField.get(hashMap),
                "The array of lists of entries should be called \"entries\" and be an array of " +
                        "Lists of HashMapEntry objects");
        List[] list = (List[]) entriesField.get(hashMap);
        final int capacity = 1024;
        Assertions.assertEquals(capacity, list.length, 
                  "Startingcapacity should be 1024.");
    }

    @Test
    @DisplayName("Testing HashMap")
    void hashMap() {
        Assertions.assertTrue(hashMap.isEmpty());
        Assertions.assertEquals(".-", hashMap.put('A', ".-"));
        Assertions.assertEquals(".-" , hashMap.get('A'));
        Assertions.assertEquals(".-.-", hashMap.put('\n', ".-.-"));
        Assertions.assertEquals(".-.-", hashMap.get('\n'));
        Assertions.assertEquals(2, hashMap.size());
        Assertions.assertNull(hashMap.get('B'));
        Assertions.assertEquals(".-.-", hashMap.remove('\n'));
        Assertions.assertNull(hashMap.get('\n'));
        Assertions.assertEquals(1, hashMap.size());
        Assertions.assertTrue(hashMap.containsKey('A'));
    }
}
