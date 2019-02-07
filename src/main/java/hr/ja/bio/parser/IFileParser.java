package hr.ja.bio.parser;

/**
 * @param <R> parser result
 */
public interface IFileParser<R> {

	/**
	 * 
	 * @param currentLine
	 * @param lineNumber start at 0
	 */
	void parseLine(String currentLine, int lineNumber);

	/**
	 * @param currentLine
	 * @param lineNumber start at 0
	 * @return true if line must be dropped.
	 */
	boolean filterLine(String currentLine, int lineNumber);

	public R getResult();

}
