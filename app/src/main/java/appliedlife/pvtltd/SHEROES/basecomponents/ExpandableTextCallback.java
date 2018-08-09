package appliedlife.pvtltd.SHEROES.basecomponents;


public interface ExpandableTextCallback {

    /**
     * Return true is the text is collapsed , for expanded text return false
     * @param isCollapsed true if textview is collapsed
     */
    void onTextResize(boolean isCollapsed);
}
