package com.tcook.github.favlag.model;

public class Language {

	private String name;
	private Integer codeBytes;

	public Language() {
	}

	public Language(String name, Integer codeBytes) {
		super();
		this.name = name;
		this.codeBytes = codeBytes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCodeBytes() {
		return codeBytes;
	}

	public void setCodeBytes(Integer codeBytes) {
		this.codeBytes = codeBytes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codeBytes;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (codeBytes != other.codeBytes)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Language [name=" + name + ", codeBytes=" + codeBytes + "]";
	}
}
