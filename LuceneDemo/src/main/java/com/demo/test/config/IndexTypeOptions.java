package com.demo.test.config;

import org.apache.lucene.index.IndexOptions;

public enum IndexTypeOptions {
	NONE(0, IndexOptions.NONE), DOCS(1, IndexOptions.DOCS), DOCS_AND_FREQS(2,
			IndexOptions.DOCS_AND_FREQS), DOCS_AND_FREQS_AND_POSITIONS(3,
			IndexOptions.DOCS_AND_FREQS_AND_POSITIONS), DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS(
			4, IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);

	private IndexTypeOptions(int type, IndexOptions option) {
		this.type = type;
		this.option = option;
	}

	private int type;
	private IndexOptions option;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public IndexOptions getOption() {
		return option;
	}

	public void setOption(IndexOptions option) {
		this.option = option;
	}

	public static IndexOptions fromType(int type) {
		for (IndexTypeOptions ito : IndexTypeOptions.values()) {
			if (ito.getType() == type)
				return ito.getOption();
		}
		return IndexTypeOptions.NONE.getOption();
	}
}
