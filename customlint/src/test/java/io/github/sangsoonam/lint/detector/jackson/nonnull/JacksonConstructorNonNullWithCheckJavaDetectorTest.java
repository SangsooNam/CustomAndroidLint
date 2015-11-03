package io.github.sangsoonam.lint.detector.jackson.nonnull;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonConstructorNonNullWithCheckJavaDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new JacksonConstructorNonNullWithCheckJavaDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(JacksonConstructorNonNullWithCheckJavaDetector.ISSUE);
	}

	public void testWhenNotNullWithoutCheck() throws Exception {
		assertThat(lintFiles("jackson/nonnull/PersonNonNull.java"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenNonNullWithCheck() throws Exception {
		assertThat(lintFiles("jackson/nonnull/PersonNonNullWithCheck.java"))
			.isEqualTo("No warnings.");
	}
}
