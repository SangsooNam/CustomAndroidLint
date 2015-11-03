package io.github.sangsoonam.lint.detector.resources;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.Lists;

import java.util.List;

import io.github.sangsoonam.lint.AbstractDetectorTest;

import static org.assertj.core.api.Assertions.assertThat;

public class StringsSortedByNameResourceDetectorTest extends AbstractDetectorTest {

	@Override
	protected Detector getDetector() {
		return new StringsSortedByNameResourceDetector();
	}

	@Override
	protected List<Issue> getIssues() {
		return Lists.newArrayList(StringsSortedByNameResourceDetector.ISSUE);
	}

	public void testWhenStringNamesUnsorted() throws Exception {
		assertThat(lintFiles("values/strings_unsorted.xml"))
			.contains(getIssues().get(0).getId());
	}

	public void testWhenStringNamesSorted() throws Exception {
		assertThat(lintFiles("values/strings_sorted.xml"))
			.isEqualTo("No warnings.");
	}

	public void testWhenStringNamesSortedWithLintProject() throws Exception {
		assertThat(lintProject(xml("res/values/strings.xml", "" +
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<resources>\n" +
				"    <string name=\"u_a1\">a1</string>\n" +
				"    <string name=\"u_a2\">a2</string>\n" +
				"    <string-array name=\"u_b\">\n" +
				"        <item>1</item>\n" +
				"        <item>2</item>\n" +
				"    </string-array>\n" +
				"    <string name=\"u_a\">a</string>\n" +
				"    <string name=\"u_c\">c</string>\n" +
				"    <string name=\"u_d\">d</string>\n" +
				"    <plurals name=\"u_e\">\n" +
				"        <item quantity=\"one\">11</item>\n" +
				"        <item quantity=\"other\">2</item>\n" +
				"    </plurals>\n" +
				"</resources>")
		)).contains(getIssues().get(0).getId());
	}
}