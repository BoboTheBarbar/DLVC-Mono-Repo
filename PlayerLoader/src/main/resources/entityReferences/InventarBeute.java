package model;

import java.util.ArrayList;
import java.util.List;

import controller.Dice;

public class InventarBeute extends Beute {
    private int geldWert_;
    private int inventarWert_;
    private List<Gegenstand> inventar_;
    
    public InventarBeute() {
        geldWert_ = 0;
        inventarWert_ = 0;
        inventar_ = new ArrayList<Gegenstand>();
    }
    
    
    
    public InventarBeute(int gesamtWert, int streuung) {
        gesamtWert = (int)generateGaussianValue(gesamtWert, streuung);
        inventar_ = new ArrayList<Gegenstand>();
        splitGesamtWert(gesamtWert);
    }
    
    
    
    public void splitGesamtWert(int gesamtWert) {
        setGeldWert(Dice.rollDice(gesamtWert));
        setInventarWert(gesamtWert - geldWert_);
    }
    
    
    
    public void generateInventarBeute() {
        List<Gegenstand> sortedList = Gegenstand.getAllInventar();
        Gegenstand.sortByKosten(sortedList);
        
        int remainingValue = inventarWert_;
        while(remainingValue > 0) {
            Gegenstand chosen = Beute.getRandomItemWithMaxValue(remainingValue, sortedList);
            remainingValue = updateResumingValue(remainingValue, chosen);
        }
    }
    
    
    
    private int updateResumingValue(int remainingValue, Gegenstand chosen) { 
        if(chosen == null) {  // TODO: should be tested as well
            geldWert_ += remainingValue;
            inventarWert_ -= remainingValue;
            remainingValue = 0;
        }
        else {  // TODO: What to do with value 0 stuff?
            if(chosen.getKosten_() == 0){
                remainingValue -= 1;
                geldWert_ += 1;
                inventarWert_ -= 1;
            }
            remainingValue -= chosen.getKosten_();
            inventar_.add(chosen);
        }
        return remainingValue;
    }
    
    
    public int getGeldWert() {
        return geldWert_;
    }

    public void setGeldWert(int geldWert) {
        this.geldWert_ = geldWert;
    }
    
    public List<Gegenstand> getInventarBeute() {
        return inventar_;
    }
    
    public int getInventarWert() {
        return inventarWert_;
    }

    public void setInventarWert(int inventarWert) {
        this.inventarWert_ = inventarWert;
    }
}
