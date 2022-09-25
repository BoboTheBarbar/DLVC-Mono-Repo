package model;

import java.util.ArrayList;
import java.util.List;

import controller.Dice;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import view.tabledata.SharedGegnerTableEntry;

public class GegnerEinheit extends Charakter implements SharedGegnerTableEntry {
    private int ID_;
    private String name_;
    private int kreis_;
    private int level_;
    private int erfahrung_;
    private int staerke_;
    private int geschick_;
    private int maxLebenspunkte_;
    private int currentLebenspunkte_;
    private int schaden_;
    private int dealtSchaden_;
    private Ausruestung ausruestung_;
    private final GegnerTyp typ_;
    
    
    private GegnerEinheit(GegnerTyp typ, int ID) {
        typ_ = typ;
        name_ = typ.getName_() + " " + Integer.toString(ID);
        level_ = typ.getLevel_();
        kreis_ = typ.getKreis_();
        erfahrung_ = typ.getErfahrung_();
        staerke_ = typ.getStaerke_();
        geschick_ = typ.getGeschick_();
        maxLebenspunkte_ = typ.getMaxLebenspunkte_();
        currentLebenspunkte_ = maxLebenspunkte_;
        schaden_ = typ.getSchaden_();
        ausruestung_ = typ.getAusruestung_();
        ID_ = ID;
    }
    
    
    
    public int getID_() {
        return ID_;
    }

    

    public void setID_(int iD_) {
        ID_ = iD_;
    }

    

    public String toString() {
        return name_;
    }

    
    
    public String getName_() {
        return name_;
    }
    
    
    
    public void setName_(String name) {
        this.name_ = name;
    }

    

    public int getKreis_() {
        return kreis_;
    }

    

    public void setKreis_(int kreis) {
        this.kreis_ = kreis;
    }

    

    public int getLevel_() {
        return level_;
    }

    

    public void setLevel_(int level_) {
        this.level_ = level_;
    }

    

    public int getErfahrung_() {
        return erfahrung_;
    }

    

    public void setErfahrung_(int erfahrung_) {
        this.erfahrung_ = erfahrung_;
    }

    

    public int getStaerke_() {
        return staerke_;
    }

    

    public void setStaerke_(int staerke_) {
        this.staerke_ = staerke_;
    }

    

    public int getGeschick_() {
        return geschick_;
    }

    

    public void setGeschick_(int geschick_) {
        this.geschick_ = geschick_;
    }

    

    public int getCurrentLebenspunkte_() {
        return currentLebenspunkte_;
    }

    

    public int getMaxLebenspunkte_() {
        return maxLebenspunkte_;
    }



    public void setMaxLebenspunkte_(int maxLebenspunkte_) {
        this.maxLebenspunkte_ = maxLebenspunkte_;
    }



    public void setSchaden_(int schaden_) {
        this.schaden_ = schaden_;
    }



    public void setCurrentLebenspunkte_(int lebenspunkte_) {
        this.currentLebenspunkte_ = lebenspunkte_;
    }

    
    
    public static final List<GegnerEinheit> createEinheiten(GegnerTyp typ, int anzahl) {
        List<GegnerEinheit> gegner = new ArrayList<GegnerEinheit>();
        for(int id = 1; id <= anzahl; ++id){
            GegnerEinheit tmp = new GegnerEinheit(typ, id);
            gegner.add(tmp);
        }
        return gegner;
    }

    
    
    public int getSchaden_() {
        return this.schaden_;
    }

    
    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }


    
    public void setAusruestung_(Ausruestung ausruestung) {
        this.ausruestung_ = ausruestung;
    }


    
    public void setDamage(int schaden) {
        this.schaden_ = schaden;
    }
    
    
    
    public int getDealtSchaden_() {
        return dealtSchaden_;
    }



    public void setDealtSchaden_(int dealtSchaden_) {
        this.dealtSchaden_ = dealtSchaden_;
    }



    public GegnerTyp getTyp_() {
        return typ_;
    }



    public int compareTo(GegnerEinheit other) {
        if(this.ID_ == other.getID_())
            return super.compareTo(other);
        else if(this.ID_ < other.getID_())
            return -1;
        else
            return 1;
    }



    @Override
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name_);
    }



    @Override
    public StringProperty dealtSchadenProperty() {
        return new SimpleStringProperty(Integer.toString(dealtSchaden_));
    }



    @Override
    public StringProperty lebenspunkteProperty() {
        return new SimpleStringProperty(Integer.toString(currentLebenspunkte_)
                + "/" + Integer.toString(maxLebenspunkte_));
    }



    public int simulateStaerkeProbe(int summe) {
        return Dice.RollW12() + getStaerkeModifier(staerke_-summe);
    }



    public static int getStaerkeModifier(int staerke) {
        staerke = Math.max(staerke, 1); 
        if(staerke <= 12) {
            return (staerke - 1) / 3 - 2;
        }
        else if(staerke <= 18) {
            return (staerke - 1) / 3 - 3;
        }
        else {
            return (staerke - 1) / 3 - 4;
        }
    }



    public boolean blockIsSuccessful(int geschickMalus) {
        if(this.getDefS() <= 0) return false;
        
        int roll = Dice.RollW20();
        int bound = getBoundForBlocking(geschickMalus);
        
        if(roll > bound) 
            return true;
        
        return false;
    }



    public int getBoundForBlocking(int geschickMalus) {
        int modifiedGeschick = geschick_-(1+geschickMalus);
        if(modifiedGeschick < 0)   {
            modifiedGeschick = 0;
        }
        return 18 - modifiedGeschick/20;
    }
    
}
