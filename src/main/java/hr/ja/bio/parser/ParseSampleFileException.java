package hr.ja.bio.parser;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseSampleFileException extends Exception {

	private static final long serialVersionUID = -7991107393062327335L;

	private String line;

	private int lineNumber;

	public ParseSampleFileException(String msg, String line) {
		super(msg);
		this.line = line;
	}

	public ParseSampleFileException(String msg) {
		super(msg);
	}

	public ParseSampleFileException(String msg, Throwable cause, String line, int lineNumber) {
		super(msg, cause);
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public ParseSampleFileException(Throwable cause) {
		super(cause);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getLine() {
		return line;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}
//	
//	@Override
//	public String getMessage() {
//		return String.format("%s. \nLine:%d Line:%s", super.getMessage(), lineNumber, line);
//	}
}
