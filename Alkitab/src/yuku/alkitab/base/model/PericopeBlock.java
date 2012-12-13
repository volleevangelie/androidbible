package yuku.alkitab.base.model;

import java.util.Arrays;

public class PericopeBlock {
	public String title;
	public String[] parallels;

	@Override public String toString() {
		return "Blok{title=" + title + " parallels=" + Arrays.toString(parallels) + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
