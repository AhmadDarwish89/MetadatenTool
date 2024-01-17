package main.java.de.lwv.MetadatenTool.model.vo;

public class Edok_metadatum {

    private int IDMETA;
    private String EDOK_VORLAGE_ID;
    private String META_CODE;
    private String VALUE;
    private String ANLAGE_DATUM_META;
    private String ANLAGE_USER_NR_Meta;
    private String ANLAGE_ORGNR_META;

    public int getIDMETA() {
        return IDMETA;
    }

    public void setIDMETA(int IDMETA) {
        this.IDMETA = IDMETA;
    }

    public String getEDOK_VORLAGE_ID() {
        return EDOK_VORLAGE_ID;
    }

    public void setEDOK_VORLAGE_ID(String EDOK_VORLAGE_ID) {
        this.EDOK_VORLAGE_ID = EDOK_VORLAGE_ID;
    }

    public String getMETA_CODE() {
        return META_CODE;
    }

    public void setMETA_CODE(String META_CODE) {
        this.META_CODE = META_CODE;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getANLAGE_DATUM_META() {
        return ANLAGE_DATUM_META;
    }

    public void setANLAGE_DATUM_META(String ANLAGE_DATUM_META) {
        this.ANLAGE_DATUM_META = ANLAGE_DATUM_META;
    }

    public String getANLAGE_USER_NR_Meta() {
        return ANLAGE_USER_NR_Meta;
    }

    public void setANLAGE_USER_NR_Meta(String ANLAGE_USER_NR_Meta) {
        this.ANLAGE_USER_NR_Meta = ANLAGE_USER_NR_Meta;
    }

    public String getANLAGE_ORGNR_META() {
        return ANLAGE_ORGNR_META;
    }

    public void setANLAGE_ORGNR_META(String ANLAGE_ORGNR_META) {
        this.ANLAGE_ORGNR_META = ANLAGE_ORGNR_META;
    }
}
