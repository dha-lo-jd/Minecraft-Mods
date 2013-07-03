package org.lo.d.site.mods.kawo_commons;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lo.d.site.Content;
import org.lo.d.site.Version;
import org.lo.d.site.VersionFile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;

@Component("kawo_commons.updates")
public class Updates implements Content {
	public static class UpdateInfo extends VersionFile {
		private UpdateInfo(Version version, String fileName) {
			super(version, fileName);
		}
	}

	private static final Pattern PETTERN_UPDATES_HTML = Pattern
			.compile("^(?<fileName>updates_v_(?<version>[^.]+\\.[^.]+\\.[^.]+))\\.html");

	public UpdateInfo getFirst() {
		List<UpdateInfo> updates = getUpdates();
		if (updates == null || updates.isEmpty()) {
			return null;
		}
		return updates.get(0);
	}

	public String getPath() {
		try {
			return getRootPath().relativize(Paths.get(getClass().getResource("").toURI())).toString()
					.replaceAll("\\\\", "/");
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public List<UpdateInfo> getUpdates() {
		List<UpdateInfo> result = Lists.newArrayList();
		try {
			Path path = Paths.get(Resources.getResource(Updates.class, "").toURI());
			File dir = path.toFile();
			if (dir.exists() && dir.isDirectory()) {
				for (File file : dir.listFiles()) {
					String fileName = file.getName();
					Matcher matcher = PETTERN_UPDATES_HTML.matcher(fileName);
					if (matcher.matches()) {
						String versionString = matcher.group("version");
						UpdateInfo updateInfo = new UpdateInfo(new Version(versionString), matcher.group("fileName"));
						result.add(updateInfo);
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Collections.sort(result);
		Collections.reverse(result);
		return result;
	}

	public boolean isFirst(UpdateInfo update) {
		UpdateInfo first = getFirst();
		if (first == null) {
			return false;
		}
		return first.equals(update);
	}

	private Path getRootPath() {
		try {
			return Paths.get(getClass().getClassLoader().getResource("").toURI());
		} catch (URISyntaxException e) {
			return null;
		}
	}

}
