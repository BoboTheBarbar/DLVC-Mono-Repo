package model;

import java.util.List;
import java.util.Random;

import controller.Dice;

public abstract class Beute {
    
    static private Random random = new Random();

    protected static double generateGaussianValue(double mean, double variance) {
        return random.nextGaussian()*Math.sqrt(variance) + mean;
    }
    
    
    
    protected static Gegenstand getItemWithApproximateValue(int value, List<Gegenstand> sortedList) {
        if(sortedList.isEmpty())
            return null;
        
        for(int i = 0; i<sortedList.size(); i++) {
            Gegenstand item = sortedList.get(i);
            if(item.computeValue() >= value) {
                if(i == 0)
                    return item;
                Gegenstand previousItem = sortedList.get(i-1);
                boolean previousItemIsBetter = Math.abs(value - item.computeValue()) >
                    Math.abs(value - previousItem.computeValue());
                return previousItemIsBetter ? previousItem : item;
            }
        }
        return sortedList.get(sortedList.size() - 1);
    }
    
    protected static Gegenstand getRandomItemWithMaxValue(int maxValue, List<Gegenstand> sortedList) {
        int range = sortedList.size();
        while(range > 0) {
            int sample = Dice.rollDice(range) - 1;
            Gegenstand chosen = sortedList.get(sample);
            if(chosen.getKosten_() <= maxValue)
                return chosen;
            range = sample;
        }
        return null;
    }
}
