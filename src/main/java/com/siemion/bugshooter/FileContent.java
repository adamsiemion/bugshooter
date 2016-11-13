package com.siemion.bugshooter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Adam Siemion
 */
public class FileContent {

    private final File filePath;

    public FileContent(final File filePath) {
        this.filePath = filePath;
    }

    public char[] get() {
        try {
            final String sourceCode = FileUtils.readFileToString(filePath);
            return sourceCode.toCharArray();
        } catch (final IOException e) {
            throw new RuntimeException(String.format("IOException while reading file %s", filePath.getAbsoluteFile()), e);
        }
    }
}
