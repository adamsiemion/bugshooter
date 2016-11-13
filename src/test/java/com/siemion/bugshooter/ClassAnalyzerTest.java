package com.siemion.bugshooter;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Adam Siemion
 */
public class ClassAnalyzerTest {

    @Test
    void analyze_shouldCallRuleWithCompilationUnitAllowingToCollectImports_whenContentWithImportsProvided() throws IOException {
        final FileContent fileContent = mock(FileContent.class);
        when(fileContent.get()).thenReturn("import java.util.List; import java.util.Set; class Sample { }".toCharArray());

        final WildcardsImportRule rule = mock(WildcardsImportRule.class);
        ArgumentCaptor<CompilationUnit> compilationUnitArgumentCaptor = ArgumentCaptor.forClass(CompilationUnit.class);
        when(rule.analyze(compilationUnitArgumentCaptor.capture())).thenReturn(Arrays.asList());
        final ClassAnalyzer classAnalyzer = new ClassAnalyzer(rule);

        classAnalyzer.analyze(fileContent);

        final CompilationUnit capturedCompilationUnit = compilationUnitArgumentCaptor.getValue();
        final List<String> actualImports = new CollectImports().collect(capturedCompilationUnit);
        final List<String> expectedImports = Arrays.asList("java.util.List", "java.util.Set");
        assertEquals(expectedImports, actualImports);
    }

    private class CollectImports extends ASTVisitor {
        private final List<String> imports = new ArrayList<>();

        @Override
        public boolean visit(final ImportDeclaration node) {
            imports.add(node.getName().getFullyQualifiedName());
            return true;
        }

        public List<String> collect(final CompilationUnit compilationUnit) {
            imports.clear();
            compilationUnit.accept(this);
            return imports;
        }
    }

}