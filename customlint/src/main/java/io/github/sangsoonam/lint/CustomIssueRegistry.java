package io.github.sangsoonam.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import io.github.sangsoonam.lint.detector.jackson.ignoreunknown.JacksonIgnorePropertiesClassDetector;
import io.github.sangsoonam.lint.detector.jackson.ignoreunknown.JacksonIgnorePropertiesJavaDetector;
import io.github.sangsoonam.lint.detector.jackson.nonnull.JacksonConstructorNonNullJavaDetector;
import io.github.sangsoonam.lint.detector.registerunregister.RegisterUnregisterClassDetector;
import io.github.sangsoonam.lint.detector.resources.StringsSortedByNameResourceDetector;

import java.util.Arrays;
import java.util.List;

public class CustomIssueRegistry extends IssueRegistry {

	@Override
	public List<Issue> getIssues() {
		final List<Issue> list = Arrays.asList(
			JacksonIgnorePropertiesJavaDetector.ISSUE,
			JacksonConstructorNonNullJavaDetector.ISSUE,
			RegisterUnregisterClassDetector.ISSUE,
			StringsSortedByNameResourceDetector.ISSUE
		);

		System.out.println("> Custom Lint is working with (" + Joiner.on(", ").join(Iterables.transform(list, new Function<Issue, String>() {
			@Override
			public String apply(Issue issue) {
				return issue.getId();
			}
		})) + ")");
		return list;
	}
}
