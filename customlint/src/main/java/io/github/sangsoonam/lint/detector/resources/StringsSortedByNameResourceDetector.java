package io.github.sangsoonam.lint.detector.resources;

import com.android.SdkConstants;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

public class StringsSortedByNameResourceDetector extends Detector implements Detector.XmlScanner {

	public static Issue ISSUE = Issue.create(
		"StringsSortedByName",
		"Strings should be ordered by name.",
		"To keep consistency, strings should be ordered by name.",
		Category.CORRECTNESS,
		6,
		Severity.ERROR,
		new Implementation(
			StringsSortedByNameResourceDetector.class,
			Scope.RESOURCE_FILE_SCOPE
		)
	);

	@Override
	public boolean appliesTo(ResourceFolderType folderType) {
		return folderType == ResourceFolderType.VALUES;
	}

	@Override
	public Collection<String> getApplicableElements() {
		return Arrays.asList(SdkConstants.TAG_PLURALS, SdkConstants.TAG_STRING, SdkConstants.TAG_STRING_ARRAY);
	}

	@Override
	public void visitElement(XmlContext context, Element element) {
		Node node = element.getNextSibling();
		while (node != null && node.getNodeType() != Node.ELEMENT_NODE) {
			node = node.getNextSibling();
		}
		if (node == null) {
			// No next element to compare
			return;
		}

		Element nextElement = (Element) node;
		String name = element.getAttribute("name");
		String nextName = nextElement.getAttribute("name");
		if (name.compareTo(nextName) > 0) {
			context.report(ISSUE, context.getLocation(element), "Resource element name (" + nextName + ") must be sorted after (" + name + ").");
		}
	}
}