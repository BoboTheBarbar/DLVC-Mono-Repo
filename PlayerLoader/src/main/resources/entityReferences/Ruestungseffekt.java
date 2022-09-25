package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import controller.manipulators.RuestungseffektManipulator;
import view.controller.Hauptprogramm;
import model.interfaces.DBObject;

@Entity
@Table(name = "RUESTUNGSEFFEKT")
public class Ruestungseffekt implements DBObject {
    public enum EffektTyp {
        MALUS_STAERKE,
        MALUS_GESCHICK,
        EXP_BOOST;
        
        @Override
        public String toString() {
            switch(this) {
                case MALUS_STAERKE:
                    return "St" + Hauptprogramm.UMLAUT_SMALL_AE + "rkemalus";
                case MALUS_GESCHICK:
                    return "Geschickmalus";
                case EXP_BOOST:
                    return "Mehr Erfahrung";
                default:
                    return "";
            }
        }
    };
    
    private static RuestungseffektManipulator dbManipulator_ = RuestungseffektManipulator.getInstance();
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
    @ManyToOne(optional = false,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_; 
    @Column(name = "EFFEKT_TYP")
    private EffektTyp effektTyp_;
    @Column(name = "EFFEKT_WERT", columnDefinition = "INTEGER check (EFFEKT_WERT >= 0)")
    private int effektWert_;
   
    
    public Ruestungseffekt() {
        ausruestung_ = new Ausruestung();
    }
    
    
    
    public Ruestungseffekt(Ausruestung ausruestung) {
        if (ausruestung != null) {
            ausruestung_ = ausruestung;
        }
        else    {
            ausruestung = new Ausruestung(); 
        }
    }
    
    
    
    public EffektTyp getEffektTyp_() {
        return effektTyp_;
    }
    
    
    
    public void setEffektTyp_(EffektTyp effektTyp_) {
        if(effektTyp_ != this.effektTyp_) {
            this.effektTyp_ = effektTyp_;
            updateInDB();
        }
    }
    
    
    
    public int getEffektWert_() {
        return effektWert_;
    }
    
    
    
    public void setEffektWert_(int effektWert_) {
        if(effektWert_ != this.effektWert_) {
            this.effektWert_ = effektWert_;
            updateInDB();
        }
    }



    public void setAusruestung_(Ausruestung ausruestung_) {
        if(!ausruestung_.equals(this.ausruestung_)) {
            this.ausruestung_ = ausruestung_;
            updateInDB();
        }
    }

    

    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }


    
    public void addToDB() {
        dbManipulator_.add(this);
    }


    
    public void deleteFromDB() {
        if(ID_ != 0)
            dbManipulator_.delete(this);
    }
    
    
    
    private void updateInDB() {
        if(ID_ != 0)
            dbManipulator_.add(this);
    }


    public int getID_() {
        return ID_;
    }
}
