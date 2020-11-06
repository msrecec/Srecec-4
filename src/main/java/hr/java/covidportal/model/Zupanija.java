package main.java.hr.java.covidportal.model;

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
