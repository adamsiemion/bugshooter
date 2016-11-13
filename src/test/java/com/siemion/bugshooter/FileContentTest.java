package com.siemion.bugshooter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Adam Siemion
 */
public class FileContentTest {

    @Test
    void get_shouldReturnExpectedText_whenTestFileIsProvided() {
        final FileContent fileContent = new FileContent(new File("src/test/resources/FileContentTest.txt"));

        final char[] content = fileContent.get();

        assertEquals("file content test", new String(content));
    }
}