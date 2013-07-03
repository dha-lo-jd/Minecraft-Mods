package org.lo.d.site;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public abstract class AbstractModContent implements Content {
	public static class ModFile extends VersionFile {
		private final MinecaraftVersion minecraftVersion;
		private final String modName;

		private ModFile(String fileName, String modName, MinecaraftVersion minecraftVersion, Version version) {
			super(version, fileName);
			this.modName = modName;
			this.minecraftVersion = minecraftVersion;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ModFile other = (ModFile) obj;
			if (minecraftVersion == null) {
				if (other.minecraftVersion != null) {
					return false;
				}
			} else if (!minecraftVersion.equals(other.minecraftVersion)) {
				return false;
			}
			return true;
		}

		public MinecaraftVersion getMinecraftVersion() {
			return minecraftVersion;
		}

		public String getModName() {
			return modName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((minecraftVersion == null) ? 0 : minecraftVersion.hashCode());
			return result;
		}

		@Override
		public String toString() {
			return String.format("%s, mod[%s], mcv[%s], v[%s]", getFileName(), getModName(), getVersion(),
					getMinecraftVersion());
		}

	}

	private static class MinecaraftVersion extends Version {
		public MinecaraftVersion(String value) {
			super(value);
		}
	}

	private static final String VERSION_REGEX = "^(?<fileName>%s\\-(?<minecraftVersion>[^.]+\\.[^.]+\\.[^.]+)\\-(?<version>[^.]+\\.[^.]+\\.[^.]+)\\.zip)";

	private final Pattern modFilePattern;

	public AbstractModContent() {
		modFilePattern = Pattern.compile(String.format(VERSION_REGEX, getModFileName()));
	}

	public ModFile getFirst(MinecaraftVersion minecraftVersion) {
		Collection<ModFile> modFiles = getModFiles().get(minecraftVersion);
		Iterator<ModFile> iterator = modFiles.iterator();
		if (!iterator.hasNext()) {
			return null;
		}
		return iterator.next();
	}

	public List<MinecaraftVersion> getMinecraftVersions() {
		List<MinecaraftVersion> vs = Lists.newArrayList();
		for (MinecaraftVersion v : getModFiles().keySet()) {
			vs.add(v);
		}
		Collections.sort(vs);
		Collections.reverse(vs);
		return vs;
	}

	public Multimap<MinecaraftVersion, ModFile> getModFiles() {
		File dir;
		try {
			dir = Paths.get(getClass().getResource("").toURI()).toFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return ArrayListMultimap.create();
		}
		if (!dir.exists() || !dir.isDirectory()) {
			return ArrayListMultimap.create();
		}

		ArrayListMultimap<MinecaraftVersion, ModFile> result = ArrayListMultimap.create();
		for (File file : dir.listFiles()) {
			String fileName = file.getName();
			Matcher matcher = modFilePattern.matcher(fileName);
			if (matcher.matches()) {
				String version = matcher.group("version");
				MinecaraftVersion minecraftVersion = new MinecaraftVersion(matcher.group("minecraftVersion"));
				ModFile modFile = new ModFile(matcher.group("fileName"), getModName(), minecraftVersion, new Version(
						version));
				result.put(minecraftVersion, modFile);
			}
		}

		for (MinecaraftVersion v : result.keys()) {
			List<ModFile> l = result.get(v);
			Collections.sort(l);
			Collections.reverse(l);
		}
		return result;
	}

	public String getPath() {
		try {
			return getRootPath().relativize(Paths.get(getClass().getResource("").toURI())).toString()
					.replaceAll("\\\\", "/");
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public List<Path> getResources() {
		File dir;
		try {
			dir = Paths.get(getClass().getResource("").toURI()).toFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
		if (!dir.exists() || !dir.isDirectory()) {
			return Lists.newArrayList();
		}

		List<Path> list = Lists.newArrayList();
		for (ModFile f : getModFiles().values()) {
			list.add(Paths.get(new File(dir, f.getFileName()).toURI()));
		}

		return list;
	}

	public boolean isFirst(ModFile update) {
		ModFile first = getFirst(update.getMinecraftVersion());
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

	protected abstract String getModFileName();

	protected abstract String getModName();

}
