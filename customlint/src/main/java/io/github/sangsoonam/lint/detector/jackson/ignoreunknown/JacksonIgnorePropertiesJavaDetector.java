package io.github.sangsoonam.lint.detector.jackson.ignoreunknown;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Objects;

import lombok.ast.Annotation;
import lombok.ast.AstVisitor;
import lombok.ast.BooleanLiteral;
import lombok.ast.ClassDeclaration;
import lombok.ast.ConstructorDeclaration;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Node;
import lombok.ast.VariableDefinition;

public class JacksonIgnorePropertiesJavaDetector extends Detector implements Detector.JavaScanner {

	public static Issue ISSUE = Issue.create(
		"JacksonIgnorePropertiesJava",
		"Should use @JsonIgnoreProperties(ignoreUnknown = true)",
		"By default, @JsonIgnoreProperties(ignoreUnknown = false) is used. With that, " +
			"there is no problem for now. However, that will be a serious problem once we want to " +
			"send more data from the backend. Devices having older versions will be crashed.",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			JacksonIgnorePropertiesJavaDetector.class,
			Scope.JAVA_FILE_SCOPE
		)
	);

	@Override
	public AstVisitor createJavaVisitor(JavaContext context) {
		return new JavaVisitor(context);
	}

	private static class JavaVisitor extends ForwardingAstVisitor {

		private final JavaContext mContext;
		private boolean mHasJsonIgnorePropertiesAnnotationWithTrueValue;

		public JavaVisitor(JavaContext context) {
			mContext = context;
		}

		@Override
		public boolean visitConstructorDeclaration(ConstructorDeclaration node) {
			boolean hasJsonPropertyAnnotation = false;

			LoopVariables:
			for (VariableDefinition variableDefinition : node.astParameters()) {
				for (Annotation annotation : variableDefinition.astModifiers().astAnnotations()) {
					if (annotation.astAnnotationTypeReference().getTypeName().equals("JsonProperty")) {
						hasJsonPropertyAnnotation = true;
						break LoopVariables;
					}
				}
			}

			if (hasJsonPropertyAnnotation && !mHasJsonIgnorePropertiesAnnotationWithTrueValue) {
				mContext.report(ISSUE, mContext.getLocation(node.getParent()), "Should use @JsonIgnoreProperties(ignoreUnknown = true)");
			}
			return true;
		}

		@Override
		public boolean visitClassDeclaration(ClassDeclaration node) {
			for (Annotation annotation : node.astModifiers().astAnnotations()) {
				if (annotation.astAnnotationTypeReference().getTypeName().equals("JsonIgnoreProperties")) {
					for (Node valueNode : annotation.getValues("ignoreUnknown")) {
						if (valueNode instanceof BooleanLiteral && Objects.equals(true, ((BooleanLiteral) valueNode).astValue())) {
							mHasJsonIgnorePropertiesAnnotationWithTrueValue = true;
							return false;
						}
					}
				}
			}
			return false;
		}
	}
}
