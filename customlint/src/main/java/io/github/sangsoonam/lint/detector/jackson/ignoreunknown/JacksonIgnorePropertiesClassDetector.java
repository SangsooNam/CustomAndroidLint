package io.github.sangsoonam.lint.detector.jackson.ignoreunknown;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

@SuppressWarnings("unchecked")
public class JacksonIgnorePropertiesClassDetector extends Detector implements Detector.ClassScanner {

	public static Issue ISSUE = Issue.create(
		"JacksonIgnorePropertiesClass",
		"Should use @JsonIgnoreProperties(ignoreUnknown = true)",
		"By default, @JsonIgnoreProperties(ignoreUnknown = false) is used. With that, " +
			"there is no problem for now. However, that will be a serious problem once we want to " +
			"send more data from the backend. Devices having older versions will be crashed.",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			JacksonIgnorePropertiesClassDetector.class,
			Scope.CLASS_FILE_SCOPE
		)
	);

	@Override
	public void checkClass(ClassContext context, ClassNode classNode) {
		boolean hasJsonPropertyAnnotation = false;
		LoopMethods:
		for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
			if (methodNode.name.equals("<init>") && methodNode.visibleParameterAnnotations != null) {
				for (List<AnnotationNode> annotationNodes : (List<AnnotationNode>[]) methodNode.visibleParameterAnnotations) {
					for (AnnotationNode annotationNode : annotationNodes) {
						if ("Lcom/fasterxml/jackson/annotation/JsonProperty;".equals(annotationNode.desc)) {
							hasJsonPropertyAnnotation = true;
							break LoopMethods;
						}
					}
				}
			}
		}

		if (hasJsonPropertyAnnotation) {
			if (classNode.visibleAnnotations != null) {
				for (AnnotationNode annotationNode : (List<AnnotationNode>) classNode.visibleAnnotations) {
					if ("Lcom/fasterxml/jackson/annotation/JsonIgnoreProperties;".equals(annotationNode.desc)) {
						for (int i = 0; i < annotationNode.values.size(); i++) {
							if ("ignoreUnknown".equals(annotationNode.values.get(i)) || Boolean.TRUE.equals(annotationNode.values.get(i + 1))) {
								return;
							}
						}
					}
				}
			}
			context.report(ISSUE, context.getLocation(classNode), "Should use @JsonIgnoreProperties(ignoreUnknown = true)");
		}
	}
}
