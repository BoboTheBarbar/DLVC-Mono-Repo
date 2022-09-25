package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import controller.manipulators.GruppenManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GRUPPEN")
public class Gruppe implements DBObject, Comparable<Gruppe> {
    public static final int MAX_NAME_LENGTH = 100;
    private static GruppenManipulator dbManipulator_ = GruppenManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
    @Column(name = "NAME", columnDefinition = " VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL DEFAULT 'Montags Gruppe'")
    private String name_;
    @ManyToMany(mappedBy = "membership_")
    private Set<Spieler> members_;    
    
    public Gruppe(){
        name_ = "DefaultGruppe";
        members_ = new HashSet<Spieler>();
    }
    
    
    
    private void updateInDB() {
        if(ID_ != 0)
            dbManipulator_.update(this);
    }
    
    public static List<Gruppe> getAll() {
        List<Gruppe> allGruppen = dbManipulator_.getAll();
        allGruppen.sort(null);
        
        return allGruppen;
    }
    
    @Override
    public String toString() {
        return name_;
    }

    public void addSpieler(Spieler spieler) {
        spieler.addToGruppe(this);
    }
    


    public void removePlayer(Spieler spieler) {
        spieler.removeFromGruppe(this);
    }
    
    

    public void deleteFromDB() {
        dbManipulator_.delete(this);
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
        this.name_ = name_;
        updateInDB();
    }



    public Set<Spieler> getMembers_() {
        if (members_ == null) {
            members_ = new HashSet<Spieler>();
        }
        
        return members_;
    }
    
    
    
    public List<Spieler> getOrderedMemberList() {
        List<Spieler> members = new ArrayList<Spieler>(getMembers_());
        members.sort(null);
        return members;
    }



    public void setMembers_(Set<Spieler> members_) {
        this.members_ = members_;
    }    

    
    
    public void addToDB() {
        dbManipulator_.add(this);
    }



    @Override
    public int compareTo(Gruppe otherGruppe) {
        return getName_().compareTo(otherGruppe.getName_());
    }



    public int getStufenSumme() {
        int stufe = 0;
        for(Spieler spieler : members_) {
            stufe += spieler.getLevel_() + (spieler.getKreis_() - 1)*12; 
        }
        return stufe/members_.size();
    }
    
    
    
    public static int getBenoetigteErfahrung(int Stufe, int Kreis){
        int erfahrung = 0;
        switch(Stufe){
            case 0:
                erfahrung = 100;
                break;
            case 1:
                erfahrung = 150;
                break;
            case 2:
                erfahrung = 225;
                break;
            case 3:
                erfahrung = 330;
                break;
            case 4:
                erfahrung = 500;
                break;
            case 5:
                erfahrung = 750;
                break;
            case 6:
                erfahrung = 1125;
                break;
            case 7:
                erfahrung = 1650;
                break;
            case 8:
                erfahrung = 2475;
                break;
            case 9:
                erfahrung = 3700;
                break;
            case 10:
                erfahrung = 5550;
                break;
            case 11:
                erfahrung = 8325;
                break;
        }
        switch(Kreis){
            case 1:
                return erfahrung;
            case 2:
                return erfahrung*100;
            case 3:
                return erfahrung*10000;
            case 4:
                return erfahrung*1000000;
        }
        return 0;
    }



    public static int getStufe(int stufenSumme) {
        return stufenSumme % 12;
    }



    public static int getKreis(int stufenSumme) {
        return stufenSumme / 12 + 1;
    }
}
