package io.github.sangsoonam.lint.detector.jackson.ignoreunknown;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonIgnorePropertiesJavaDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new JacksonIgnorePropertiesJavaDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(JacksonIgnorePropertiesJavaDetector.ISSUE);
	}

	public void testWhenIgnoreUnknownIsFalseThenError() throws Exception {
		assertThat(lintFiles("jackson/ignoreunknown/Person.java"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenIgnoreUnknownIsTrueThenNoError() throws Exception {
		assertThat(lintFiles("jackson/ignoreunknown/PersonIgnoreUnknown.java"))
			.isEqualTo("No warnings.");
	}
}
