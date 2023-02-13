package com.example.mc_assignment_4;

public class Quranmetadata {
    private String num;
    private String text;
    private String parah;
    private String surah;
    private String translation;

    public Quranmetadata(String num,String text,String parah,String surah,String translation) {
        this.num = num;
        this.text = text;
        this.parah = parah;
        this.surah = surah;
        this.translation = translation;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParah() {
        return parah;
    }

    public void setParah(String parah) {
        this.parah = parah;
    }

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
