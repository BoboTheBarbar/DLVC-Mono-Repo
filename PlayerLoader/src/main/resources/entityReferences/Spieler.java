package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import controller.manipulators.SpielerManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "SPIELER")
public class Spieler extends Charakter implements DBObject {
    public static final int MAX_NAME_LENGTH = 90;
    private static SpielerManipulator spielerManipulator_ = SpielerManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL default 'Jane Doe'")
    private String name_;
    @Column(name = "KREIS", columnDefinition = "INTEGER NOT NULL default '" + MIN_KREIS + "' check(KREIS >= " + MIN_KREIS + " and KREIS <= " + MAX_KREIS + ")")
    private int kreis_;
    @Column(name = "LEVEL", columnDefinition = "INTEGER NOT NULL default '"+ MIN_LEVEL + "' check(LEVEL >= " + MIN_LEVEL + " and LEVEL <=" + MAX_LEVEL +")")
    private int level_;

    private int ruestungsWert;

//    private int angriffsWert;

    
    
    public Spieler() {
        name_ = "Default";
        level_ = MIN_LEVEL;
        kreis_ = MIN_KREIS;
    }
}
