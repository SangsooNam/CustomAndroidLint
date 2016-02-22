package io.github.sangsoonam.lint.detector.rx;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class RxSubscribeWithoutErrorHandlingTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new RxSubscribeWithoutErrorHandlingDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(RxSubscribeWithoutErrorHandlingDetector.ISSUE);
	}

	public void testWhenWithoutErrorHandling() throws Exception {
		assertThat(lintFiles("rx/SubscribeWithoutErrorHandling.class"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenWithErrorHandling() throws Exception {
		assertThat(lintFiles("rx/SubscribeWithErrorHandling.class"))
			.isEqualTo("No warnings.");
	}
}

