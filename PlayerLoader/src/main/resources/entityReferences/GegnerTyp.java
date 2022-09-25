package model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import view.tabledata.SharedGegnerTableEntry;
import controller.manipulators.GegnerManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GEGNER_TYP")

public class GegnerTyp extends Charakter implements DBObject, SharedGegnerTableEntry {
    public static final int MAX_NAME_LENGTH = 90;
    private static GegnerManipulator dbManipulator_ = GegnerManipulator.getInstance();
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL default 'GegnerTyp Nr. 420'")
    private String name_;
    @Column(name = "KREIS", columnDefinition = "INTEGER NOT NULL default '" + MIN_KREIS + "' check(KREIS >=" + MIN_KREIS + "and KREIS<=" + MAX_KREIS + ")")
    private int kreis_;
    @Column(name = "LEVEL", columnDefinition = "INTEGER NOT NULL default '" + MIN_LEVEL + "' check(LEVEL >=" + MIN_LEVEL + "and LEVEL<=" + MAX_LEVEL + ")")
    private int level_;
    @Column(name = "SCHADEN", columnDefinition = "INTEGER NOT NULL default '0' check(SCHADEN >= 0)")
    private int schaden_;
    @Column(name = "ERFAHRUNG", columnDefinition = "INTEGER NOT NULL default '1' check(ERFAHRUNG >= 1)")
    private int erfahrung_;
    @Column(name = "STAERKE", columnDefinition = "INTEGER NOT NULL default '1' check(STAERKE >= 1)")
    private int staerke_;
    @Column(name = "GESCHICK", columnDefinition = "INTEGER NOT NULL default '1' check(GESCHICK >= 1)")
    private int geschick_;
    @Column(name = "MAX_LEBENSPUNKTE", columnDefinition = "INTEGER NOT NULL default '1' CHECK(MAX_LEBENSPUNKTE >= 0)")
    private int maxLebenspunkte_;
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_;

    
    
    public GegnerTyp() {
        name_ = "Default";
        level_ = MIN_LEVEL;
        kreis_ = MIN_KREIS;
        erfahrung_ = 1;
        staerke_ = 1;
        geschick_ = 1;
        maxLebenspunkte_ = 25;
        schaden_ = 0;
        ausruestung_ = new Ausruestung();
    }
    
    
    
    @PrePersist
    public void onCreate() {
        if (kreis_ == 0) {
            kreis_ = MIN_KREIS;
        }
        if (erfahrung_ == 0) {
            erfahrung_ = 1;
        }
        if (staerke_ == 0) {
            staerke_ = 1;
        }
        if (geschick_ == 0) {
            geschick_ = 1;
        }
        if (schaden_ < 0) {
            schaden_ = 0;
        }
    }
    
    
    
    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.update(this);
    }

    

    public String toString() {
    	return name_;
    }

    
    
    public int getID_() {
        return ID_;
    }

    

    public void setID_(int iD_) {
        ID_ = iD_;
    }

    

    public String getName_() {
        return name_;
    }

    
    
    public void setName_(String name_) {
        if(name_ == null) this.name_ = "GegnerNotNull";
        else if(!name_.equals(this.name_)) {
            this.name_ = name_;
            updateInDB();
        }
    }

    

    public int getKreis_() {
        return kreis_;
    }

    

    public void setKreis_(int kreis_) {
        if(kreis_ != this.kreis_) {
            this.kreis_ = kreis_;
            updateInDB();
        }
    }

    

    public int getLevel_() {
        return level_;
    }

    

    public void setLevel_(int level_) {
        if(level_ < 0) this.level_ = Math.abs(level_);
        if(level_ != this.level_) {
            this.level_ = level_;
            updateInDB();
        }
    }

    

    public int getErfahrung_() {
        return erfahrung_;
    }

    

    public void setErfahrung_(int erfahrung_) {
        if(erfahrung_ <0) this.erfahrung_ = Math.abs(erfahrung_);
        if(erfahrung_ != this.erfahrung_) {
            this.erfahrung_ = erfahrung_;
            updateInDB();
        }
    }

    

    public int getStaerke_() {
        return staerke_;
    }

    

    public void setStaerke_(int staerke_) {
        if(staerke_ != this.staerke_) {
            this.staerke_ = staerke_;
            updateInDB();
        }
    }

    

    public int getGeschick_() {
        return geschick_;
    }

    

    public void setGeschick_(int geschick_) {
        if(geschick_ == 0) this.geschick_ = 1;
        if(geschick_ != this.geschick_) {
            this.geschick_ = geschick_;
            updateInDB();
        }
    }

    

    public int getMaxLebenspunkte_() {
        return maxLebenspunkte_;
    }

    

    public void setMaxLebenspunkte_(int lebenspunkte_) {
        if (lebenspunkte_ < 0) this.maxLebenspunkte_ = Math.abs(lebenspunkte_);
        if (lebenspunkte_ != this.maxLebenspunkte_) {
            this.maxLebenspunkte_ = lebenspunkte_;
            updateInDB();
        }
    }

    

    public static List<GegnerTyp> getAll() {
        List<GegnerTyp> allGegner = dbManipulator_.getAll();
        allGegner.sort(null);
        
        return allGegner;
    }

    

    public int getSchaden_() {
            return schaden_;
    }

    

    public void setSchaden_(int newSchaden_) {
        if (newSchaden_ != this.maxLebenspunkte_) {
            this.schaden_ = newSchaden_;
            updateInDB();
        }
    }
    
    
    
	@Override
	public Ausruestung getAusruestung_() {
		return ausruestung_;
	}


	
	@Override
	public void setAusruestung_(Ausruestung ausruestung) {
	    if (ausruestung == null) return;
	    else if (!ausruestung.equals(ausruestung_)) {
            boolean gegnerInDbButAusruestungIsNot = getID_() != 0
                    && ausruestung.getID_() == 0;
            if (gegnerInDbButAusruestungIsNot)
                ausruestung.addToDB();
            ausruestung_ = ausruestung;
        }
	}


	
    public static boolean detailsAreValid
        (int level, int kreis, int geschick, int staerke, int erfahrung) {
       
        boolean validation = true;
        
        if(level > MAX_LEVEL || level < MIN_LEVEL)
            validation = false;
        
        if(kreis > MAX_KREIS || kreis < MIN_KREIS)
            validation = false;
        
        if(geschick < 1)
            validation = false;
        
        if(staerke < 1)
            validation = false;
        
        if(erfahrung < 1)
            validation = false;
        
        return validation;
    }

    


    public void addToDB() {
        dbManipulator_.add(this);
    }
    
    

    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }



    @Override
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name_);
    }



    @Override
    public StringProperty dealtSchadenProperty() {
        return new SimpleStringProperty("");
    }



    @Override
    public StringProperty lebenspunkteProperty() {
        return new SimpleStringProperty("");
    }
}
