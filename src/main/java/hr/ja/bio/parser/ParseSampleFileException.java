package hr.ja.bio.parser;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ParseSampleFileException extends Exception {

    private String line = "";
    private int lineNumber = 0;

    public ParseSampleFileException(String msg, String line) {
        super(msg + " line:" + line);
        this.line = line;
    }

    public ParseSampleFileException(String msg, String line, int lineNumber) {
        super(msg + " line number" + lineNumber + " line: " + line);
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public ParseSampleFileException(String msg) {
        super(msg);
    }

    public String getLine() {
        return line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
