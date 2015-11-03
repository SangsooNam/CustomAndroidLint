package io.github.sangsoonam.lint.detector.jackson.nonnull;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.TextFormat;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonConstructorNonNullJavaDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new JacksonConstructorNonNullJavaDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(JacksonConstructorNonNullJavaDetector.ISSUE);
	}

	public void testWhenHasNotNonNullAnnotations() throws Exception {
		assertThat(lintFiles("jackson/nonnull/Person.java"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenHasNonNullAnnotations() throws Exception {
		assertThat(lintFiles("jackson/nonnull/PersonNonNull.java"))
			.isEqualTo("No warnings.");
	}
}
