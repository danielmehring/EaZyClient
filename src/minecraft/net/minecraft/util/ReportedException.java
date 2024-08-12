package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException extends RuntimeException {

public static final int EaZy = 1647;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Instance of CrashReport. */
	private final CrashReport theReportedExceptionCrashReport;
	// private static final String __OBFID = "http://https://fuckuskid00001579";

	public ReportedException(final CrashReport p_i1356_1_) {
		theReportedExceptionCrashReport = p_i1356_1_;
	}

	/**
	 * Gets the CrashReport wrapped by this exception.
	 */
	public CrashReport getCrashReport() {
		return theReportedExceptionCrashReport;
	}

	@Override
	public Throwable getCause() {
		return theReportedExceptionCrashReport.getCrashCause();
	}

	@Override
	public String getMessage() {
		return theReportedExceptionCrashReport.getDescription();
	}
}
