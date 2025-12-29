public class A{

    private boolean undefSeqLen = true;

    private boolean undefItemLen = true;

    private boolean fmi = true;

    /** Creates a new instance of Acr2Dcm */
    public Acr2Dcm() {
    }

    public void setStudyUID(String uid) {
        this.studyUID = uid;
    }

    public void setSeriesUID(String uid) {
        this.seriesUID = uid;
    }

    public void setInstUID(String uid) {
        this.instUID = uid;
    }

    public void setClassUID(String uid) {
        this.classUID = uid;
    }

    public void setSkipGroupLen(boolean skipGroupLen) {
}