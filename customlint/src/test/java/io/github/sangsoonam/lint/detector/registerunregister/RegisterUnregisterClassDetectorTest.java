package io.github.sangsoonam.lint.detector.registerunregister;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterUnregisterClassDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new RegisterUnregisterClassDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(RegisterUnregisterClassDetector.ISSUE);
	}

	public void testWhenFragmentWithoutUnregister() throws Exception {
		assertThat(lintFiles("registerunregister/FragmentWithoutUnregister.class"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenFragmentWithRegisterAndUnregister() throws Exception {
		assertThat(lintFiles("registerunregister/FragmentWithRegisterUnregister.class"))
			.isEqualTo("No warnings.");
	}
}
