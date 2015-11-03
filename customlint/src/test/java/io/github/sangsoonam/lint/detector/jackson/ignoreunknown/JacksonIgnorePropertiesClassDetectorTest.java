package io.github.sangsoonam.lint.detector.jackson.ignoreunknown;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.TextFormat;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;
import io.github.sangsoonam.lint.detector.jackson.ignoreunknown.JacksonIgnorePropertiesClassDetector;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonIgnorePropertiesClassDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new JacksonIgnorePropertiesClassDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(JacksonIgnorePropertiesClassDetector.ISSUE);
	}

	public void testWhenIgnoreUnknownIsFalseThenError() throws Exception {
		assertThat(lintFiles("jackson/ignoreunknown/Person.class"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenIgnoreUnknownIsTrueThenNoError() throws Exception {
		assertThat(lintFiles("jackson/ignoreunknown//PersonIgnoreUnknown.class"))
			.isEqualTo("No warnings.");
	}
}
