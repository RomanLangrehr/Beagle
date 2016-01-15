package de.uka.ipd.sdq.beagle.core;

import java.io.File;
import java.io.Serializable;

/**
 * Describes a section in examined software’s source code. Code sections may span multiple
 * methods and types. They are defined by a start statement in one source code file and an
 * end statement in another source code file that may or may not be the same as the first
 * one. Code sections are immutable, meaning that once created, their attributes cannot be
 * changed.
 *
 * <p>A code section must describe a continuous part of source code, meaning that the last
 * statement in the section will always be executed if the first one was executed, given
 * that no Exceptions occur. All statements that may potentially be executed after the
 * first statement but before the last one are considered to be in the section. A code
 * section is considered to have been “completely executed” if the section’s first and
 * last statement was executed. This does generally not mean that all statements in the
 * section were executed.
 *
 * <p>It must be sure that in each run of the project under analysis the first line of
 * this code section is always executed before the last line and that the last line is
 * always executed after the first line, if the program did not crash during execution.
 * Otherwise the code section is not "valid".
 *
 * @author Joshua Gleitze
 * @author Roman Langrehr
 */
public class CodeSection implements Serializable {

	/**
	 * Serialisation version UID, see {@link java.io.Serializable}.
	 */
	private static final long serialVersionUID = -1823330022448293103L;

	/**
	 * The java file which contains the {@link #startCodeLine} of this code section.
	 */
	private File startFile;

	/**
	 * The line index, starting with {@code 0} in the {@link #startFile}, of the first
	 * line in this code section.
	 */
	private int startCodeLine;

	/**
	 * The java file which contains the {@link #endCodeLine} of this code section.
	 */
	private File endFile;

	/**
	 * The line index, starting with {@code 0} in the {@link #endFile}, of the last line
	 * in this code section.
	 */
	private int endCodeLine;

	/**
	 * Creates a code section that spans from the {@code startIndex}th statement in
	 * {@code startFile} to the {@code endIndex}th statement in {@code endFile}.
	 *
	 * @param startFile The file containing this section’s first statement. Must not be
	 *            {@code null} and {@link File#isFile() startFile.isFile()} must return
	 *            {@code true}.
	 * @param startIndex The index of the first statement in this section. Counting starts
	 *            at 0.
	 * @param endFile The file containing this section’s last statement. Must not be
	 *            {@code null} and {@link File#isFile() endFile.isFile()} must return
	 *            {@code true}.
	 * @param endIndex The index of the last statement in this section. Counting starts at
	 *            0.
	 * @throws IllegalArgumentException When {@code startFile.isFile()} or
	 *             {@code endFile.isFile()} returned {@code false}.
	 */
	public CodeSection(final File startFile, final int startIndex, final File endFile, final int endIndex) {{
		if (!startFile.isFile()) {
			throw new IllegalArgumentException("The given startFile is not a file");
		}
		if (!endFile.isFile()) {
			throw new IllegalArgumentException("The given endFile is not a file");
		}
		this.startFile = startFile;
		this.startCodeLine = startCodeLine;
		this.endFile = endFile;
		this.endCodeLine = endCodeLine;
	}

	/**
	 * Gets the file that contains this section’s first statement.
	 *
	 * @return The file containing this section’s first statement. Will never be
	 *         {@code null} and {@code getStartFile().isFile()} always
	 *         returns {@code true}.
	 */
	public File getStartFile() {
		return this.startFile;
	}

	
	/**
	 * Gets the index of the first statement in this code section. Counting starts at 0.
	 * The number thus describes how many statements precede the section’s first statement
	 * in the {@linkplain #getStartFile() start source code file}.
	 *
	 * @return The first statement’s index. A non-negative integer.
	 */
	public int getStartSectionIndex() {
		return this.startCodeLine;
	}

	/**
	 * Gets the file that contains this section’s last statement.
	 *
	 * @return The file containing this section’s last statement. Will never be
	 *         {@code null} and {@code getEndFile().isFile()} always
	 *         returns {@code true}.
	 */
	public File getEndFile() {
		return this.endFile;
	}

	/**
	 * Gets the index of the last statement in this code section. Counting starts at 0.
	 * The number thus describes how many statements precede the section’s last statement
	 * in the {@linkplain #getStartFile() end source code file}.
	 *
	 * @return The last statement’s index. A non-negative integer.
	 */
	public int getEndSectionIndex() {
		return this.endCodeLine;
	}

}
