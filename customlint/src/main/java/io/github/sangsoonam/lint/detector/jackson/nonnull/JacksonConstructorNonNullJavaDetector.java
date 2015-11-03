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

public class JacksonConstructorNonNullJavaDetector extends Detector implements Detector.JavaScanner {

	public static Issue ISSUE = Issue.create(
		"JacksonConstructorWithNonNullOrNullable",
		"Should use @NotNull or @Nullable annotation",
		"Without @NotNull or @Nullable annotations, it is not obvious to say a value is " +
			"not null or nullable. It could make any wrong assumption. To avoid that, " +
			"annotations should be used",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			JacksonConstructorNonNullJavaDetector.class,
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

				if (typeNames.contains("JsonProperty")) {
					if (typeNames.contains("NonNull") || typeNames.contains("Nullable")) {
						continue;
					}
					mContext.report(ISSUE, mContext.getLocation(variableDefinition), "Should use @NotNull or @Nullable annotation");
				}
			}

			return false;
		}
	}
}
