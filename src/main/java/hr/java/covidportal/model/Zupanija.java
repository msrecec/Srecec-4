package main.java.hr.java.covidportal.model;

import java.util.Objects;

/**
 * Služi za definiranje instanci klase Zupanija
 *
 * @author Mislav Srečec
 * @version 1.0
 */

public class Zupanija extends ImenovaniEntitet {
    private Integer brojStanovnika;

    /**
     * Služi za instanciranje objekta instanci klase Zupanija
     *
     * @param naziv
     * @param brojStanovnika
     */

    public Zupanija(String naziv, Integer brojStanovnika) {
        super(naziv);
        this.brojStanovnika = brojStanovnika;
    }

    /**
     * Uspoređuje elemente
     *
     * @param o objekt usporedbe
     * @return true ako su elementi jednaki
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zupanija)) return false;
        Zupanija zupanija = (Zupanija) o;
        return Objects.equals(getBrojStanovnika(), zupanija.getBrojStanovnika());
    }

    /**
     * Radi hashcode
     *
     * @return integer vrijednost HashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getBrojStanovnika());
    }

    /**
     * Vraća broj stanovnika županije
     *
     * @return brojStanovnika
     */

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    /**
     * Postavlja broj stanovnika županije
     *
     * @param brojStanovnika
     */

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }
}
