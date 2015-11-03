package io.github.sangsoonam.lint.detector.jackson.nonnull;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.HashSet;
import java.util.Set;

import lombok.ast.Annotation;
import lombok.ast.AstVisitor;
import lombok.ast.ConstructorDeclaration;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.VariableDefinition;
import lombok.ast.VariableDefinitionEntry;

public class JacksonConstructorNonNullWithCheckJavaDetector extends Detector implements Detector.JavaScanner {

	public static Issue ISSUE = Issue.create(
		"JacksonConstructorWithNonNullWithCheck",
		"Precondition.checkNotNull() should be used with @NotNull",
		"@NotNull annotation is not enough to valid dynamic data. " +
			"If the data is null, errors could happen in somewhere else. " +
			"Precondition.checkNotNull() should be used to ensure that data is not null.",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			JacksonConstructorNonNullWithCheckJavaDetector.class,
			Scope.JAVA_FILE_SCOPE
		)
	);

	@Override
	public AstVisitor createJavaVisitor(JavaContext context) {
		return new JavaVisitor(context);
	}

	private static class JavaVisitor extends ForwardingAstVisitor {

		private final JavaContext mContext;

		public JavaVisitor(JavaContext context) {
			mContext = context;
		}

		@Override
		public boolean visitConstructorDeclaration(ConstructorDeclaration node) {
			for (VariableDefinition variableDefinition : node.astParameters()) {
				if (variableDefinition.astTypeReference().isPrimitive()) {
					// Skip primitive value
					continue;
				}

				Set<String> typeNames = new HashSet<>();
				for (Annotation annotation : variableDefinition.astModifiers().astAnnotations()) {
					typeNames.add(annotation.astAnnotationTypeReference().getTypeName());
				}

				if (typeNames.contains("JsonProperty") && typeNames.contains("NonNull")) {
					VariableDefinitionEntry entry = variableDefinition.astVariables().first();
					if (node.astBody().toString().contains("checkNotNull(" + entry.astName() + ")")) {
						continue;
					}
					mContext.report(ISSUE, mContext.getLocation(variableDefinition), "Should use Precondition.checkNotNull for @NotNull parameters.");
				}
			}

			return false;
		}
	}
}
