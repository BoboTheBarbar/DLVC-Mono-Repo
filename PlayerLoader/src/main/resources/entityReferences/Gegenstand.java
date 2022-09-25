package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import view.controller.HaendlerTabController;
import view.controller.Hauptprogramm;
import controller.manipulators.EinfacherGegenstandManipulator;
import model.interfaces.DBObject;


/**
 * Einfaches Item fuer den Haendler: Name, Beschreibung, Preis.
 */
@Entity
@Table(name="GEGENSTAND")
public class Gegenstand implements DBObject, Comparable<Gegenstand> {
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_BESCHREIBUNG_LENGTH = 400;
    public static final int MAX_KATEGORIE_LENGTH = 400;
    public static final int MAX_WERT_LENGTH = 400;
    
    public static final String RUESTUNG = "R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung"; 
    public static final String WAFFE = "Waffe";
    public static final String HANDSCHUH = "Handschuh";
    public static final String SCHILD = "Schild";
    public static final String SCHUH = "Schuh";
    public static final String GUERTEL = "G" + Hauptprogramm.UMLAUT_SMALL_UE + "rtel";
    public static final String HARNISCH = "Harnisch";
    public static final String GEGENSTAND_NEU = "Neuer Gegenstand";
    public static final String HELM = "Helm";
    
    private static EinfacherGegenstandManipulator dbManipulator_ = EinfacherGegenstandManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL default 'Einfacher Gegenstand'")
    private String name_;
    @Column(name = "BESCHREIBUNG", columnDefinition = "VARCHAR(" + MAX_BESCHREIBUNG_LENGTH + ")")
    private String beschreibung_;
    @Column(name = "KATEGORIE", columnDefinition = "VARCHAR(" + MAX_KATEGORIE_LENGTH + ")")
    private String kategorie_;



    @Column(name = "VORKOMMEN", columnDefinition = "VARCHAR(400)")
    private String vorkommen_;
    
    @Column(name = "KOSTEN", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int kosten_;
    @Column(name = "TRAGLAST", columnDefinition = "INTEGER DEFAULT '0' CHECK(TRAGLAST >= 0)")
    private int traglast_;
    @Column(name = "STAERKE", columnDefinition = "INTEGER DEFAULT '0' CHECK(STAERKE >= 0)")
    private int staerke_;
    @Column(name = "WERT", columnDefinition = "VARCHAR("+ MAX_WERT_LENGTH + ")")
    private String wert_;
    
    
    
    public Gegenstand() {
        name_ = "Einfacher Gegenstand";
        kosten_ = 0;
        traglast_ = 0;
        beschreibung_ = "";
        staerke_ = 0;
        wert_ = "0";
        kategorie_ = "Ohne Kategorie";
        vorkommen_ = "";
    }
    
    
    
    public String getKategorie_() {
        return kategorie_;
    }



    public void setKategorie_(String kategorie_) {
        this.kategorie_ = kategorie_;
        updateInDB();
    }



    public int getStaerke_() {
        return staerke_;
    }



    public void setStaerke_(int staerke_) {
        this.staerke_ = staerke_;
        updateInDB();
    }



    public String getWert_() {
        return wert_;
    }



    public void setWert_(String wert_) {
        this.wert_ = wert_;
        updateInDB();
    }    
    
    
    
    public int getTraglast_() {
        return traglast_;
    }



    public void setTraglast_(int traglast_) {
        this.traglast_ = traglast_;
        updateInDB();
    }



    public String toString() {
        return getName_();
    }
    
    
    public String getName_() {
        return name_;
    }
    
    
    
    public void setName_(String name_) {
        this.name_ = name_;
        updateInDB();
    }
    
    
    
    public String getBeschreibung_() {
        return beschreibung_;
    }
    
    
    
    public void setBeschreibung_(String description_) {
        this.beschreibung_ = description_;
        updateInDB();
    }
    
    
    
    public int getKosten_() {
        return kosten_;
    }
    
    
    
    public void setKosten_(int kosten_) {
        this.kosten_ = kosten_;
        updateInDB();
    }
    
    
    
    private void updateInDB() {
        if(ID_ != 0)
            dbManipulator_.update(this);
    }



    public int getID_() {
        return ID_;
    }



    public void addToDB() {
        dbManipulator_.add(this);
    }



    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }



    @Override
    public int compareTo(Gegenstand otherGegenstand) {
        return getName_().compareTo(otherGegenstand.getName_());
    }
    
    
    
    public static boolean isValid(int kosten, int traglast, String name) {
        boolean isValid = true;
        isValid = (kosten >= 0) && isValid;
        isValid = (traglast >= 0) && isValid;
        isValid = (!name.equals(GEGENSTAND_NEU)) && isValid;
        return isValid;
    }
    
    
    
    public static List<Gegenstand> getAllAusruestung() {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(item.isAusruestung()){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }
    
    
    
    public static List<Gegenstand> getAllWaffen() {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(item.isWaffe()){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }
    
    
    
    private boolean isWaffe() {
        return kategorie_.contains(WAFFE);
    }
    
    
    
    public static List<Gegenstand> getAll(String kategorie) {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(item.getKategorie_().contains(kategorie)){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }

    
    
    
    public static List<Gegenstand> getAllInventar() {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(!item.isAusruestung()){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }
    
    
    
    public static List<Gegenstand> getAll() {
        List<Gegenstand> allGegenstaende = dbManipulator_.getAll();
        return allGegenstaende;
    }
    
    
    
    public static void sortByKosten(List<Gegenstand> allItems_) {
        allItems_.sort(new Comparator<Gegenstand>(){
            public int compare(Gegenstand o1, Gegenstand o2) {
                return Integer.compare(o1.getKosten_(), o2.getKosten_());
            } 
        });
    }    
    
    
    
    // Erhoeht readability ungemein
    public boolean isContainedInKategorie(String subKategorie) {
        return getKategorie_().contains(subKategorie);
    }
    
    
    
    public static List<String> getKategorien(List<Gegenstand> gegenstaende) {
        List<String> kategorien = new ArrayList<String>();
        if(gegenstaende != null){
            for(Gegenstand current : gegenstaende) {
                String currentKategorie = current.getKategorie_();
                eraseKategorieOnGegenstand(current, HaendlerTabController.ALLE_KATEGORIEN);
                if(!kategorien.contains(currentKategorie))
                    kategorien.add(currentKategorie);
            }
        }
        return kategorien;
    }



    private static void eraseKategorieOnGegenstand(Gegenstand current, String erasingKategorie) {
        if(current == null || erasingKategorie == null)
            return;
        
        String oldKategorie = current.getKategorie_();
        if(oldKategorie.contains(erasingKategorie)){
            List<String> subKategorien = getSubKategories(oldKategorie);
            List<String> newSubKategorien = getRemainingSubkategories(erasingKategorie, subKategorien);
            String newKategorie = buildFullKategoriePath(subKategorien, newSubKategorien);
            current.setKategorie_(newKategorie);
        }
    }



    private static String buildFullKategoriePath(List<String> subKategorien,
            List<String> newSubKategorien) {
        String newKategorie = "";
        if(newSubKategorien.size() == 0){
            newKategorie = "Ohne Kategorie";
        }
        else{
            newKategorie += newSubKategorien.get(0);
            for(int i = 1; i < newSubKategorien.size(); ++i){
                newKategorie += "/" + subKategorien.get(i);
            }
        }
        return newKategorie;
    }



    private static List<String> getRemainingSubkategories(
            String erasingKategorie, List<String> subKategorien) {
        List<String> newSubKategorien = new ArrayList<String>();
        for(String sub : subKategorien){
            if(!sub.contentEquals(erasingKategorie)){
//                    subKategorien.remove(subKategorien.indexOf(sub)); // throws exception
                newSubKategorien.add(sub);
            }
        }
        return newSubKategorien;
    }
    
    
    
    public static List<String> getSubKategories(String kategory) {
        List<String> subKategories = Arrays.asList(kategory.split("/"));
        return subKategories;
    }
    
    
    
    public static String getFullSubkategoriePath(String subKategorie, List<Gegenstand> alleGegenstaende) {
        for(Gegenstand item : alleGegenstaende) {
            String kategorie = item.getKategorie_(); 
            if(kategorie.contains(subKategorie)){
                List<String> subList = Gegenstand.getSubKategories(kategorie);
                String result = "";
                for(int i = 0; i < subList.size(); ++i){
                    result += subList.get(i);
                    if(subList.get(i).contentEquals(subKategorie))
                        return result;
                    result += "/";
                }
            }
        }
        return null;
    }
    
    
    
    public static boolean isAusruestung(String kategorie) {
        return kategorie.contains(WAFFE) || kategorie.contains(RUESTUNG);
    }
    

    
    public boolean isAusruestung() {
        return Gegenstand.isAusruestung(getKategorie_());
    }
    
    
    
    public static List<String> getSearchMatchingKategorien(String search, List<String> kategorien) {
        search = search.toLowerCase();
        if(search.contentEquals(""))
            return kategorien;
        List<String> result = new ArrayList<String>();
        for(String kategorie : kategorien){
            if(kategorie.toLowerCase().contains(search))
                result.add(kategorie);
        }
        return result;
    }



    public int computeValue() {
        String[] values = getWert_().split("\\+");
        int baseValue = Integer.parseInt(values[0].trim() );
        return baseValue;
    }



    public static void sortByValue(List<Gegenstand> allItems_) {
        allItems_.sort(new Comparator<Gegenstand>(){
            public int compare(Gegenstand o1, Gegenstand o2) {
                return Integer.compare(o1.computeValue(), o2.computeValue());
            } 
        });
    }



    public boolean isValid() {
        return Gegenstand.isValid(kosten_, traglast_, name_);
    } 
}
