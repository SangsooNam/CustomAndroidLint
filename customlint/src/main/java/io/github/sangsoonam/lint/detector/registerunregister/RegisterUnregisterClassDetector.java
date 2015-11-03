package io.github.sangsoonam.lint.detector.registerunregister;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.google.common.collect.Lists;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

@SuppressWarnings("unchecked")
public class RegisterUnregisterClassDetector extends Detector implements Detector.ClassScanner {

	public static Issue ISSUE = Issue.create(
		"RegisterUnregister",
		"Should call registerListener() and unregisterListener() in the same class.",
		"To avoid memory leak, the class which call registerListener() should also call " +
			"unregisterListener(). vice versa.",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			RegisterUnregisterClassDetector.class,
			Scope.CLASS_FILE_SCOPE
		)
	);

	private final static String CALL_NAME_REGISTER_LISTENER = "registerListener";
	private final static String CALL_NAME_UNREGISTER_LISTENER = "unregisterListener";
	private final static String CALL_OWNER = "registerunregister/FollowService";

	private boolean mIsRegisterCalled;
	private boolean mIsUnregisterCalled;

	@Override
	public List<String> getApplicableCallNames() {
		return Lists.newArrayList(CALL_NAME_REGISTER_LISTENER, CALL_NAME_UNREGISTER_LISTENER);
	}

	@Override
	public List<String> getApplicableCallOwners() {
		return Lists.newArrayList(CALL_OWNER);
	}

	@Override
	public void checkCall(ClassContext context, ClassNode classNode, MethodNode method, MethodInsnNode call) {
		if (call.owner.equals(CALL_OWNER)) {
			if (call.name.equals(CALL_NAME_REGISTER_LISTENER)) {
				mIsRegisterCalled = true;
			} else if (call.name.equals(CALL_NAME_UNREGISTER_LISTENER)) {
				mIsUnregisterCalled = true;
			}
		}
	}

	@Override
	public void afterCheckFile(Context context) {
		super.afterCheckFile(context);
		ClassContext classContext = (ClassContext) context;
		// One is false.
		if (mIsRegisterCalled ^ mIsUnregisterCalled) {
			if (!mIsRegisterCalled) {
				context.report(ISSUE, classContext.getLocation(classContext.getClassNode()), "registerListener() should be called  in the same class.");
			} else {
				context.report(ISSUE, classContext.getLocation(classContext.getClassNode()), "unregisterListener() should be called in the same class.");
			}
		}
	}
}
