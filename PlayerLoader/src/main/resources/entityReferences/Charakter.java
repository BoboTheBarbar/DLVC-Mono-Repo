package model;

public abstract class Charakter implements Comparable<Charakter> {
    public static final int MIN_KREIS = 1;
    public static final int MAX_KREIS = 4;
    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 11;
    
    public static final int LOWERBOUND_KRITISCH = 35;
    public static final int LOWERBOUND_DIREKT = 25;
    public static final int LOWERBOUND_HELM = 15;
    public static final int LOWERBOUND_RUESTUNG = 4;
    
    public abstract String getName_();
    abstract Ausruestung getAusruestung_();
    abstract void setAusruestung_(Ausruestung ausruestung);
    
    
    
    /**
     * Falls keine Ausruestung vorhanden ist, wird eine neue erstellt und mit
     * dem Charakter verbunden.
     * 
     * @return Ausruestung des Charakter, niemals null.
     */
    protected Ausruestung getAusruestungForModification() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null) {
            ausruestung = new Ausruestung();
            setAusruestung_(ausruestung);
        }
        return ausruestung;
    }
    
    
    
    public int getDefR() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;

        return getAusruestung_().getDefR_();
    }

    
    
    public void setDefR(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefR_(def);
    }
    
    
    public int getDefH() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;
        
        return getAusruestung_().getDefH_();
    }
    
    

    public void setDefH(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefH_(def);
    }

    
    
    public int getDefS() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 0;
        
        return getAusruestung_().getDefS_();
    }
    
    
    
    public void setDefS(int def) {
        Ausruestung ausruestung = getAusruestungForModification();
        
        if (def >= 0)
            ausruestung.setDefS_(def);
    }
    
    
    
    public static boolean ausruestungIsValid(int newDefR, int newDefH, int newDefS) {
        return newDefR > 0 && newDefH > 0 && newDefS >= 0;
    }
    
    
    
    public int getLebensverlust(int schaden, int wuerfelErgebnis, int schadenModifier) {
        Ausruestung ausruestung = this.getAusruestung_();
        schaden -= ausruestung.getDefS_();
        schaden *= (1+(double)schadenModifier/100);
        if(wuerfelErgebnis < LOWERBOUND_RUESTUNG || schaden <= 0) {
            return 0;
        }
        else if(wuerfelErgebnis < LOWERBOUND_HELM) {
            return schaden/ausruestung.getDefR_();
        }
        else if(wuerfelErgebnis < LOWERBOUND_DIREKT) {
            return schaden/ausruestung.getDefH_();
        }
        else if(wuerfelErgebnis < LOWERBOUND_KRITISCH) {
            return (int)(schaden);
        }
        else
            return (int) (schaden*1.1);
    }
    
    
    
    public int compareTo(Charakter other) {
        return getName_().compareTo(other.getName_());
    }
    
}