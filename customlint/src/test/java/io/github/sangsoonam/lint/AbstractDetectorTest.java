package io.github.sangsoonam.lint;

import com.android.SdkConstants;
import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.google.common.base.Joiner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractDetectorTest extends LintDetectorTest {

	/**
	 * @param relativePath relativePath.
	 * @return Return a proper test resource path based on extension
	 */
	private String getTestResourcePath(String relativePath) {
		final String projectDir = new File("sample").getAbsolutePath();

		if (relativePath.endsWith(SdkConstants.DOT_CLASS)) {
			return Joiner.on(File.separatorChar).join(projectDir, "build", "intermediates", "classes", "debug" , relativePath);
		} else if (relativePath.endsWith(SdkConstants.DOT_JAVA)) {
			return Joiner.on(File.separatorChar).join(projectDir, "src", "main", "java" , relativePath);
		} else  {
			return Joiner.on(File.separatorChar).join(projectDir, "src", "main", "res" , relativePath);
		}
	}

	@Override
	protected File getTestfile(File targetDir, String relativePath) throws IOException {
		if (!relativePath.contains("=>")) {
			if (relativePath.endsWith(SdkConstants.DOT_CLASS)) {
				return super.getTestfile(targetDir, relativePath + "=>" + Joiner.on(File.separatorChar).join(SdkConstants.CLASS_FOLDER, relativePath));
			} else if (relativePath.endsWith(SdkConstants.DOT_JAVA)) {
				return super.getTestfile(targetDir, relativePath + "=>" + Joiner.on(File.separatorChar).join(SdkConstants.SRC_FOLDER, relativePath));
			} else {
				return super.getTestfile(targetDir, relativePath + "=>" + Joiner.on(File.separatorChar).join(SdkConstants.RES_FOLDER, relativePath));
			}
		}
		return super.getTestfile(targetDir, relativePath);
	}

	@Override
	protected InputStream getTestResource(String relativePath, boolean expectExists) {
		try {
			return new FileInputStream(new File(getTestResourcePath(relativePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
