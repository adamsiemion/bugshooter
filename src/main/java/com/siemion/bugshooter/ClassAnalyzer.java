package com.siemion.bugshooter;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adam Siemion
 */
public class ClassAnalyzer {
    private static final int DEFAULT_JAVA_VERSION = AST.JLS8;

    private final WildcardsImportRule wildcardsImportRule;

    public ClassAnalyzer() {
        this(new WildcardsImportRule());
    }

    protected ClassAnalyzer(final WildcardsImportRule wildcardsImportRule) {
        this.wildcardsImportRule = wildcardsImportRule;
    }

    public List<String> analyze(final FileContent fileContent) {
        Objects.requireNonNull(fileContent);

        final ASTParser parser = ASTParser.newParser(DEFAULT_JAVA_VERSION);
        parser.setSource(fileContent.get());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setCompilerOptions(options());
        parser.setUnitName("bugshooter");

        final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
        return wildcardsImportRule.analyze(compilationUnit);
    }

    private Map<String, String> options() {
        final Map<String, String> options = new HashMap<>();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        return options;
    }
}
