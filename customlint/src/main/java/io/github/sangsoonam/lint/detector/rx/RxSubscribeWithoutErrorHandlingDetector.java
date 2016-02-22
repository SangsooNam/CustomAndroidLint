package io.github.sangsoonam.lint.detector.rx;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.google.common.collect.Lists;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class RxSubscribeWithoutErrorHandlingDetector extends Detector implements Detector.ClassScanner {

	public static Issue ISSUE = Issue.create(
		"RxSubscribeWithoutErrorHandling",
		"Should handle `onError` to avoid `OnErrorNotImplementedException`.",
		"`subscribe(onNext)` will throw an `OnErrorNotImplementedException` if " +
			"Observable returns any error. Due to that, the application could be crashed. " +
			"`onError` should be handled every time to avoid it. ",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			RxSubscribeWithoutErrorHandlingDetector.class,
			Scope.CLASS_FILE_SCOPE
		)
	);

	@Override
	public List<String> getApplicableCallOwners() {
		return Lists.newArrayList("rx/Observable");
	}

	@Override
	public void checkCall(@NonNull ClassContext context, @NonNull ClassNode classNode, @NonNull MethodNode method, @NonNull MethodInsnNode call) {
		if ("(Lrx/functions/Action1;)Lrx/Subscription;".equals(call.desc) && "subscribe".equals(call.name)) {
			context.report(ISSUE, context.getLocation(method, classNode), "Don't use `subscribe(onNext)`");
		}
	}
}
