package com.siemion.bugshooter;

import java.io.File;
import java.util.Arrays;

/**
 * @author Adam Siemion
 */
public class BugshooterApp {

    public static void main(final String[] args) {
        if(args.length == 0) {
            System.out.println("usage: [file1] [file2] ... [fileN]");
            System.exit(1);
        }
        final ClassAnalyzer classAnalyzer = new ClassAnalyzer();
        Arrays.stream(args)
            .map(arg -> new FileContent(new File(arg)))
            .map(file -> classAnalyzer.analyze(file))
            .filter(violations -> !violations.isEmpty())
            .forEach(violations -> System.out.println(violations));
    }
}
