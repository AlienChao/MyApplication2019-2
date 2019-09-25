package com.chen.concise;

/**
 * @author AlienChao
 * date 2019/08/19 14:35
 */
public class ResponseList<T> extends Response<T> {


    /**
     * CURRENTROWS : 5
     * DATA :
     * ROWS : 5
     */

    private int CURRENTROWS;
    private T DATA;
    private String ROWS;

    public int getCURRENTROWS() {
        return CURRENTROWS;
    }

    public void setCURRENTROWS(int CURRENTROWS) {
        this.CURRENTROWS = CURRENTROWS;
    }




    public String getROWS() {
        return ROWS;
    }

    public void setROWS(String ROWS) {
        this.ROWS = ROWS;
    }
}
