public class ExceptionHolder {

    private String stackTrace;
    private String solution;
    private long startLineNumber;
    private long endLineNumber;
    private ExceptionType exceptionType;


    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public long getStartLineNumber() {
        return startLineNumber;
    }

    public void setStartLineNumber(long startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    public long getEndLineNumber() {
        return endLineNumber;
    }

    public void setEndLineNumber(long endLineNumber) {
        this.endLineNumber = endLineNumber;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public boolean isThereSolutionForThisStackTrace(){
        return (solution != null);
    }
}
