package org.lo.d.site;

public class VersionFile implements Comparable<VersionFile> {
	private final Version version;
	private final String fileName;

	public VersionFile(Version version, String fileName) {
		this.version = version;
		this.fileName = fileName;
	}

	@Override
	public int compareTo(VersionFile o) {
		return version.compareTo(o.version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VersionFile other = (VersionFile) obj;
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	public String getFileName() {
		return fileName;
	}

	public Version getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s,  v[%s]", getFileName(), getVersion());
	}

}