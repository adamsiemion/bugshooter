package com.siemion.bugshooter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Adam Siemion
 */
public class WildcardsImportRule {

    public List<String> analyze(final CompilationUnit compilationUnit) {
        Objects.requireNonNull(compilationUnit);

        return new WildcardImportsVisitor().findWildcardImports(compilationUnit)
            .stream()
            .map(wildcardImport -> String.format(
                "Wildcard imports (%s) are discouraged", removeNewLine(wildcardImport.toString())
            ))
            .collect(Collectors.toList());
    }

    private String removeNewLine(final String string) {
        return string.replace("\n", "");
    }

    private static class WildcardImportsVisitor extends ASTVisitor {
        private final List<ImportDeclaration> wildcardImports = new ArrayList<>();

        @Override
        public boolean visit(ImportDeclaration node) {
            if(node.isOnDemand()) {
                wildcardImports.add(node);
            }
            return true;
        }

        public List<ImportDeclaration> findWildcardImports(final CompilationUnit compilationUnit) {
            wildcardImports.clear();
            compilationUnit.accept(this);
            return wildcardImports;
        }
    }

}
