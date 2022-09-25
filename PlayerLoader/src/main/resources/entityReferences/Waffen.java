package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import view.controller.Hauptprogramm;
import controller.manipulators.WaffenManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "WAFFEN")
public class Waffen implements DBObject, Comparable<Waffen> {
    public static final int MAX_NAME_LENGTH = 90;
    
    public enum EffektTyp {
        RUA_SCHADEN,
        MALUS_STAERKE,
        MALUS_GESCHICK,
        AOE_SCHADEN_RUA,
        AOE_SCHADEN_RUE,
        NO_EFFEKT;
        
        @Override
        public String toString() {
            switch(this) {
                case RUA_SCHADEN:
                    return "R" + Hauptprogramm.UMLAUT_SMALL_UE + "stungsunabh" + Hauptprogramm.UMLAUT_SMALL_AE + "ngig";
                case MALUS_STAERKE:
                    return "St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkemalus";
                case MALUS_GESCHICK:
                    return "Geschickmalus";
                case AOE_SCHADEN_RUA:
                    return "Schadet allen Gegner r" + Hauptprogramm.UMLAUT_SMALL_UE + "stungsunabh" + Hauptprogramm.UMLAUT_SMALL_AE + "ngig";
                case AOE_SCHADEN_RUE:
                    return "Schadet allen Gegnern";
                case NO_EFFEKT:
                    return "Kein Effekt";
                default:
                    return "";
            }
        }
    };
    
    private static WaffenManipulator dbManipulator_ = WaffenManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL default 'Deus Ex Machina'")
    private String waffenName_;
    @Column(name = "SCHADEN", columnDefinition = "INTEGER NOT NULL default '0' check(SCHADEN >= 0)")
    private int waffenSchaden_;
    @Column(name = "EFFEKT_TYP")
    private EffektTyp effektTyp_;
    @Column(name = "EFFEKT_WERT", columnDefinition = "INTEGER NOT NULL default 0 check (EFFEKT_WERT >= 0)")
    private int effektWert_;
    @ManyToOne(optional = false)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_;

    public Waffen() {
        waffenSchaden_ = 0;
        effektWert_ = 0;
        effektTyp_ = EffektTyp.NO_EFFEKT;
        ausruestung_ = new Ausruestung();
    }
    
    
    
    public Waffen(Ausruestung ausruestung) {
        waffenName_ = "Default GegnerTyp Waffe";
        waffenSchaden_ = 0; 
        effektWert_ = 0;
        ausruestung_ = ausruestung;
    }



    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.add(this);
    }
    
    
    @Override
    public String toString() {
        return waffenName_;
    }
    
    
    
    @PrePersist
    public void onCreate() {
        if (waffenName_ == null) {
            waffenName_ = "Deus Ex Machina";
        }
    }

    
    
    public EffektTyp getEffektTyp_() {
        return effektTyp_;
    }

    
    
    public void setEffektTyp_(EffektTyp effektTyp_) {
        if(this.effektTyp_ != effektTyp_) {
            this.effektTyp_ = effektTyp_;
            updateInDB();
        }
    }
    
    
    
    public int getEffektWert_() {
        return effektWert_;
    }



    public void setEffektWert_(int effektWert_) {
        if(this.effektWert_ != effektWert_) {
            this.effektWert_ = effektWert_;
            updateInDB();
        }
    }
    


    public String getWaffenName_() {
        return waffenName_;
    }

    public void setWaffenName_(String waffenName_) {
        if(!waffenName_.equals(this.waffenName_)) {
            this.waffenName_ = waffenName_;
            updateInDB();
        }
    }

    public int getWaffenSchaden_() {
        return waffenSchaden_;
    }

    public void setWaffenSchaden_(int waffenSchaden_) {
        if(this.waffenSchaden_ != waffenSchaden_) {
            this.waffenSchaden_ = waffenSchaden_;
            updateInDB();
        }
    }

    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }

    public void setAusruestung_(Ausruestung ausruestung_) {
        if(!ausruestung_.equals(this.ausruestung_)) {
            this.ausruestung_ = ausruestung_;
            updateInDB();
        }
    }

    
    public int getID_() {
        return ID_;
    }



    public void deleteFromDB() {
        if(getID_() != 0)
            dbManipulator_.delete(this);
    }
    
    
    
    public void addToDB() {
        dbManipulator_.add(this);
    }



    public int compareTo(Waffen secondWaffe) {
        return getWaffenName_().compareTo(secondWaffe.getWaffenName_());
    }



    public boolean isRUA() {
        if(effektTyp_ == EffektTyp.RUA_SCHADEN) {
            return true;
        }
        return false;
    }
    
    
    
    public boolean isAoE() {
        return (effektTyp_ == EffektTyp.AOE_SCHADEN_RUA) || (effektTyp_ == EffektTyp.AOE_SCHADEN_RUE);
    }


    public boolean hasEffekt() {
        if(this.effektTyp_ != null  && this.effektTyp_ != EffektTyp.NO_EFFEKT)  {
            return true;
        }
        return false;
    }
}
