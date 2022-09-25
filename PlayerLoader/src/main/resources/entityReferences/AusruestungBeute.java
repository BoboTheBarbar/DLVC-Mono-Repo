package model;

import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import controller.Dice;

public class AusruestungBeute extends Beute {
    public static final double WAFFEN_WKT = 0.4;
    public static final int RUESTUNG_TEILE = 5;  // Jedes Teil ist gleich Wahrscheinlich -> 1\5
    public static final int SCHILD_RUESTUNG_TEILE = 6;
    private int anzahlRuestungsTeile_;
//    public static final double HELM_WKT = 0.12;
//    public static final double HARNISCH_RATIO = 0.12;
//    public static final double SCHUH_RATIO = 0.12;
//    public static final double HANDSCHUH_RATIO = 0.12;
//    public static final double GUERTEL_RATIO = 0.12;
    
    
    private Gegenstand ruestungsTeil_;
    private int gesamtMalus_;
    
    
    public AusruestungBeute(int malus, int streuung) {
        anzahlRuestungsTeile_ = RUESTUNG_TEILE;
        gesamtMalus_ = (int)generateGaussianValue(malus, streuung);
    }
    
    public AusruestungBeute() {
        anzahlRuestungsTeile_ = RUESTUNG_TEILE;
        gesamtMalus_ = 0;
    }
    
    
    
    public Gegenstand getAusruestungBeute() {
        return ruestungsTeil_;
    }
    
    
    
    public Gegenstand generateAusruestungBeute(GegnerEinheit gegner) {
        if(gegner.getDefS() > 0) anzahlRuestungsTeile_ = SCHILD_RUESTUNG_TEILE;
        else anzahlRuestungsTeile_ = RUESTUNG_TEILE;
        if(ruestungIsDropped()) {
            if(specifiItemDropped())
                ruestungsTeil_ = generateHelmBeute(gegner.getDefH());
            else if(specifiItemDropped() && gegner.getDefS() > 0) 
                ruestungsTeil_ = generateSchildBeute(gegner.getDefS());
            else
                ruestungsTeil_ = generateRuestungsBeute(gegner.getDefR());
        } else {
            ruestungsTeil_ = generateWaffenBeute(gegner.getSchaden_());
        }
        return ruestungsTeil_;
    }
    
    

    private Gegenstand generateSchildBeute(int defS) {
        List<Gegenstand> sortedList = Gegenstand.getAll(Gegenstand.SCHILD);
        Gegenstand.sortByValue(sortedList);
        return getItemWithApproximateValue((int)(defS*getMalusFactor()), sortedList);
    }
    
    

    protected Gegenstand generateHelmBeute(int defH) {
        List<Gegenstand> sortedList = Gegenstand.getAll(Gegenstand.HELM);
        Gegenstand.sortByValue(sortedList);
        return getItemWithApproximateValue((int)(defH*getMalusFactor()), sortedList);
    }

    
    
    protected Gegenstand generateWaffenBeute(int waffenSchaden) {
        List<Gegenstand> sortedList = Gegenstand.getAllWaffen();
        Gegenstand.sortByValue(sortedList);
        return getItemWithApproximateValue((int)(waffenSchaden*getMalusFactor()), sortedList);
    }


    
    protected Gegenstand generateRuestungsBeute(int totalRuestungsValue) {
        String ruestungsKategorie = randomRuestungsKategorie();
        List<Gegenstand> sortedList = Gegenstand.getAll(ruestungsKategorie);
        Gegenstand.sortByValue(sortedList);
        int ruestungsTeilValue = ruestungsValue(totalRuestungsValue, ruestungsKategorie);
        
        return getItemWithApproximateValue((int)(ruestungsTeilValue*getMalusFactor()), sortedList);
    }

    
    
    protected double getMalusFactor() {
        return 1.- gesamtMalus_/100.;
    }


    
    private String randomRuestungsKategorie() {
        int wert = Dice.rollDice(4);
        switch(wert) {
            case 1:
                return Gegenstand.HARNISCH;
            case 2:
                return Gegenstand.HANDSCHUH;
            case 3:
                return Gegenstand.GUERTEL;
            case 4:
                return Gegenstand.SCHUH;
            default:
                throw new NotImplementedException();
        }
    }

    
    
    private int ruestungsValue(int totalRuestungsValue, String subkategorie) {
        if(subkategorie.equals(Gegenstand.HARNISCH))
            return totalRuestungsValue / 2;
        else
            return totalRuestungsValue / 6;
    }
    
    
    
    private boolean ruestungIsDropped() {
        int wert = Dice.rollDice(100);
        return wert <= (1-WAFFEN_WKT)*100;
    }
    
    
    
    private boolean specifiItemDropped() {
        double interval = 100./anzahlRuestungsTeile_;
        int wert = Dice.rollDice(100);
        return wert <= interval;
    }
    
    
    
    public int getGesamtMalus() {
        return gesamtMalus_;
    }
}
