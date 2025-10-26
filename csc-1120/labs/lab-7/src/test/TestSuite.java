/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 7 - Call Stack
 * Name: Alexander Horton
 * Updated: 3/11/2025
 */

package test;

import hortona.FileReaderUtils;
import hortona.IntStack;
import hortona.ProgramStack;
import hortona.Driver;

import java.nio.file.Paths;
import java.util.EmptyStackException;
import java.util.Optional;
import java.util.OptionalInt;
import java.lang.reflect.Field;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * Unit tests for Call Stack Lab
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {
    private IntStack is;
    private ProgramStack ps;

    @BeforeEach
    void setup() {
        is = new IntStack();
        ps = new ProgramStack();
    }


    @Test
    @Order(7)
    @DisplayName("Testing FileReaderUtils.isVoidReturn()")
    void testIsiVoidReturn() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(FileReaderUtils.isVoidReturn("return")),
                () -> Assertions.assertFalse(FileReaderUtils.isVoidReturn("return 5")),
                () -> Assertions.assertTrue(FileReaderUtils.isVoidReturn(" return")),
                () -> Assertions.assertTrue(FileReaderUtils.isVoidReturn("return ")),
                () -> Assertions.assertTrue(FileReaderUtils.isVoidReturn("  return  ")),
                () -> Assertions.assertFalse(FileReaderUtils.isVoidReturn("  return 5")),
                () -> Assertions.assertFalse(FileReaderUtils.isVoidReturn("one()"))
        );
    }

    @Test
    @Order(8)
    @DisplayName("Testing FileReaderUtils.parseReturnValue()")
    void testParseReturnValue() {
        final int[] values = {5, 7};
        Assertions.assertAll(
                () -> Assertions.assertEquals(OptionalInt.of(values[0]),
                        FileReaderUtils.parseReturnValue("return 5")),
                () -> Assertions.assertEquals(OptionalInt.empty(),
                        FileReaderUtils.parseReturnValue("return")),
                () -> Assertions.assertEquals(OptionalInt.empty(),
                        FileReaderUtils.parseReturnValue("one()")),
                () -> Assertions.assertEquals(OptionalInt.of(values[0]),
                        FileReaderUtils.parseReturnValue(" return 5")),
                () -> Assertions.assertEquals(OptionalInt.of(values[0]),
                        FileReaderUtils.parseReturnValue("return 5 ")),
                () -> Assertions.assertEquals(OptionalInt.of(values[0]),
                        FileReaderUtils.parseReturnValue("  return 5  ")),
                () -> Assertions.assertEquals(OptionalInt.of(values[1]),
                        FileReaderUtils.parseReturnValue("return 7"))
        );
    }

    @Test
    @Order(9)
    @DisplayName("Testing FileReaderUtils.parseMethodName()")
    void testParseMethodName() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("first()")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("void first()")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("int first()")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName(" first()")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("void first() ")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("first ()")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("void first ( )")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("  first(4)")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("first(4, 3)")),
                () -> Assertions.assertEquals(Optional.of("first"),
                        FileReaderUtils.parseMethodName("void first( 4 , 3 )"))
        );
    }


    @Test
    @Order(10)
    @DisplayName("Testing FileReaderUtils.parseArguments()")
    void testParseArguments() {
        final int[] empty = new int[0];
        final int[] singleValue = {5};
        final int[] multipleValues = {3, 7, 1};
        Assertions.assertAll(
                () -> Assertions.assertArrayEquals(singleValue,
                        FileReaderUtils.parseArguments("first(5)")),
                () -> Assertions.assertArrayEquals(singleValue,
                        FileReaderUtils.parseArguments("first (5) ")),
                () -> Assertions.assertArrayEquals(singleValue,
                        FileReaderUtils.parseArguments("first( 5)")),
                () -> Assertions.assertArrayEquals(singleValue,
                        FileReaderUtils.parseArguments("first(5 )")),
                () -> Assertions.assertArrayEquals(singleValue,
                        FileReaderUtils.parseArguments("first ( 5 )")),
                () -> Assertions.assertArrayEquals(multipleValues,
                        FileReaderUtils.parseArguments("first(3,7,1)")),
                () -> Assertions.assertArrayEquals(multipleValues,
                        FileReaderUtils.parseArguments("first (3,7,1) ")),
                () -> Assertions.assertArrayEquals(multipleValues,
                        FileReaderUtils.parseArguments("first(3, 7, 1)")),
                () -> Assertions.assertArrayEquals(multipleValues,
                        FileReaderUtils.parseArguments("first( 3 , 7 , 1 )")),
                () -> Assertions.assertArrayEquals(empty,
                        FileReaderUtils.parseArguments("first()")),
                () -> Assertions.assertNull(FileReaderUtils.parseArguments("return 5")),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> FileReaderUtils.parseArguments("first(d)"))
        );
    }


    @Test
    @Order(4)
    @DisplayName("Testing IntStack size()")
    void testingIntStackSize() {
        final int[] values = {5, 4};
        Assertions.assertEquals(0, is.size());
        is.push(values[0]);
        Assertions.assertEquals(1, is.size());
        is.push(values[1]);
        Assertions.assertEquals(2, is.size());
        is.pop();
        Assertions.assertEquals(1, is.size());
        is.pop();
        Assertions.assertEquals(0, is.size());
    }

    @Test
    @Order(2)
    @DisplayName("Testing push")
    void push() throws NoSuchFieldException, IllegalAccessException {
        final int[] values = {5, 4};
        Field top = IntStack.class.getDeclaredField("top");
        top.setAccessible(true);
        Class<?> node = IntStack.class.getDeclaredClasses()[0];
        Field data = node.getDeclaredField("data");
        Field next = node.getDeclaredField("next");
        data.setAccessible(true);
        next.setAccessible(true);
        Assertions.assertNull(top.get(is));
        Assertions.assertEquals(values[0], is.push(values[0]));
        Assertions.assertEquals(values[0], data.get(top.get(is)));
        Assertions.assertEquals(values[1], is.push(values[1]));
        Assertions.assertEquals(values[0], data.get(next.get(top.get(is))));
        Assertions.assertEquals(values[1], data.get(top.get(is)));
    }

    @Test
    @Order(3)
    @DisplayName("Testing pop")
    void pop() {
        final int[] values = {5, 4};
        is.push(values[0]);
        is.push(values[1]);
        Assertions.assertAll(
                () -> Assertions.assertEquals(values[1], is.pop()),
                () -> Assertions.assertEquals(values[0], is.pop()),
                () -> Assertions.assertThrows(EmptyStackException.class, () -> is.pop())
        );
    }

    @Test
    @Order(5)
    @DisplayName("Testing peek")
    void peek() {
        final int[] values = {5, 4};
        is.push(values[0]);
        Assertions.assertEquals(values[0], is.peek());
        is.push(values[1]);
        Assertions.assertEquals(values[1], is.peek());
        is.pop();
        Assertions.assertEquals(values[0], is.peek());
        is.pop();
        Assertions.assertThrows(EmptyStackException.class, () -> is.peek());
    }

    @Test
    @Order(6)
    @DisplayName("Testing isEmpty")
    void isEmpty() {
        Assertions.assertTrue(is.isEmpty());
        is.push(1);
        Assertions.assertFalse(is.isEmpty());
        is.pop();
        Assertions.assertTrue(is.isEmpty());
    }

    @Test
    @Order(1)
    @DisplayName("Testing internals")
    void internals() throws NoSuchFieldException, IllegalAccessException {
        Field top = IntStack.class.getDeclaredField("top");
        top.setAccessible(true);
        Assertions.assertNull(top.get(is));
        Field stack = ProgramStack.class.getDeclaredField("stack");
        stack.setAccessible(true);
        Assertions.assertTrue(((IntStack) stack.get(ps)).isEmpty());
    }

    @Test
    @Order(11)
    @DisplayName("Testing callMethod")
    void callMethod() throws NoSuchFieldException, IllegalAccessException {
        Field stack = ProgramStack.class.getDeclaredField("stack");
        stack.setAccessible(true);
        IntStack stack1 = (IntStack) stack.get(ps);
        ps.callMethod("one");
        Assertions.assertEquals(1, stack1.pop());
        final int one = 1530;
        Assertions.assertEquals(one, stack1.pop());
        ps.callMethod("two", 4);
        Assertions.assertEquals(2, stack1.pop());
        Assertions.assertEquals(4, stack1.pop());
        final int two = 1627;
        Assertions.assertEquals(two, stack1.pop());
        ps.callMethod("three", 3, 2);
        Assertions.assertEquals(3, stack1.pop());
        Assertions.assertEquals(2, stack1.pop());
        Assertions.assertEquals(3, stack1.pop());
        final int three = 6895;
        Assertions.assertEquals(three, stack1.pop());
    }

    @Test
    @Order(12)
    @DisplayName("Testing returnFromMethod")
    void returnFromMethod() throws NoSuchFieldException, IllegalAccessException {
        final int startingSize = 9;
        Field stack = ProgramStack.class.getDeclaredField("stack");
        stack.setAccessible(true);
        IntStack stack1 = (IntStack) stack.get(ps);
        ps.callMethod("one");
        ps.callMethod("two", 4);
        ps.callMethod("three", 3, 2);
        Assertions.assertEquals(startingSize, stack1.size());
        Assertions.assertEquals(3, stack1.peek());
        ps.returnFromMethod();
        Assertions.assertEquals(2, stack1.peek());
        ps.returnFromMethod();
        Assertions.assertEquals(1, stack1.peek());
        ps.returnFromMethod();
        Assertions.assertThrows(EmptyStackException.class, stack1::pop);
    }

    @Test
    @Order(13)
    @DisplayName("Testing return value from method")
    void returnValue() throws NoSuchFieldException, IllegalAccessException {
        Field stack = ProgramStack.class.getDeclaredField("stack");
        stack.setAccessible(true);
        IntStack stack1 = (IntStack) stack.get(ps);
        ps.callMethod("one");
        ps.callMethod("two", 4);
        ps.callMethod("three", 3, 2);
        ps.returnFromMethod(3);
        Assertions.assertEquals(3, stack1.pop());
        Assertions.assertEquals(3, stack1.pop());
        stack1.push(3);
        stack1.push(3);
        ps.returnFromMethod(4);
        Assertions.assertEquals(2, stack1.peek());
        Assertions.assertEquals(2, stack1.pop());
        Assertions.assertEquals(4, stack1.pop());
    }

    @Test
    @Order(14)
    @DisplayName("Testing program counter")
    void programCounter() {
        final int one = 1530;
        final int two = 1627;
        final int three = 6895;
        Assertions.assertAll(
                () -> Assertions.assertEquals(one, ps.methodToProgramCounter("one")),
                () -> Assertions.assertEquals(two, ps.methodToProgramCounter("two", 4)),
                () -> Assertions.assertEquals(three, ps.methodToProgramCounter("three", 2, 3)),
                () -> Assertions.assertEquals(three - 1, ps.methodToProgramCounter("three"))
        );
    }

    @Test
    @Order(15)
    @DisplayName("Testing Program Stack toString")
    void psToString() {
        ps.callMethod("one");
        ps.callMethod("two", 4);
        ps.callMethod("three", 3, 2);
        Assertions.assertEquals("""
                |          |
                |----------|
                |        3 |
                |        2 |
                |        3 |
                |     6895 |
                |        2 |
                |        4 |
                |     1627 |
                |        1 |
                |     1530 |
                +----------+""", ps.toString());
    }

    @Test
    @Order(16)
    @DisplayName("Testing Driver")
    void program() {
        PrintStream oldOut = System.out;
        InputStream oldIn = System.in;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                InputStream is = new FileInputStream(
                        Paths.get("src", "test", "input.txt").toFile())) {
            System.setOut(ps);
            System.setIn(is);
            Driver.main(new String[0]);
            System.out.flush();
            System.setOut(oldOut);
            System.setIn(oldIn);
            System.out.println(baos);
            Assertions.assertEquals("""
                     Please enter the name of the input file: one()
                     |          |
                     |----------|
                     |        1 |
                     |     1530 |
                     +----------+
                                        
                       int two(4)
                     |          |
                     |----------|
                     |        2 |
                     |        4 |
                     |     1627 |
                     |        1 |
                     |     1530 |
                     +----------+
                                        
                     blah, blah, blah
                     Invalid line, ignored
                                        
                       return 8
                     |          |
                     |----------|
                     |        2 |
                     |        8 |
                     |     1530 |
                     +----------+
                                        
                     return
                     |          |
                     |----------|
                     +----------+
                     
                     """, baos.toString());
        } catch (IOException e) {
            System.err.println("Cannot set stream");
        }
    }
}
