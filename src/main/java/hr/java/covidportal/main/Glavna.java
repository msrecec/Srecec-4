package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.iznimke.BolestIstihSimptoma;
import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobe;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Služi za unos Županija, Simptoma, Bolesti, Osoba i služi za ispis Osoba
 *
 * @author Mislav Srecec
 * @version 3.0
 * @see <a href="https://grader.tvz.hr/">Odrediste svih verzija</a>
 */

public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static final int BROJ_ZUPANIJA = 3, BROJ_SIMPTOMA = BROJ_ZUPANIJA, BROJ_BOLESTI = 2, BROJ_VIRUSA = 2, BROJ_OSOBA = BROJ_ZUPANIJA;

    /**
     * Služi za pokretanje programa koji će od korisnika tražiti unos Županija <code>Zupanija[] zupanije</code>,
     * Simptoma <code>Simptom[] simptomi</code>, Bolesti <code>Bolest[] bolesti</code>
     * preko metoda <code>unosZupanija(input, zupanije);</code> <code>unosSimptoma(input, simptomi);</code> i <code>unosBolesti(input, simptomi, bolesti);</code>
     * na osnovi kojih će tražiti unos Osoba <code>Osoba[] osobe</code> preko metode <code>unosOsoba(input, zupanije, bolesti, osobe);</code>
     *
     * @param args argumenti komandne linije (ne koriste se)
     */

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Zupanija[] zupanije = new Zupanija[BROJ_ZUPANIJA];
        Simptom[] simptomi = new Simptom[BROJ_SIMPTOMA];
        Bolest[] bolesti = new Bolest[BROJ_BOLESTI + BROJ_VIRUSA];
        Osoba[] osobe = new Osoba[BROJ_OSOBA];


        // Unos Zupanija

        unosZupanija(input, zupanije);

        // Unos Simptoma

        unosSimptoma(input, simptomi);

        // Unos Bolesti

        unosBolesti(input, simptomi, bolesti);

        // Unos osoba

        unosOsoba(input, zupanije, bolesti, osobe);

        // Ispis osoba

        ispisOsoba(osobe);

    }

    /**
     * Unosi županije u polje županija <code>Zupanija[] zupanije</code>
     * <p>
     * Unosi nazive županija <code>String nazivZupanije</code> i broj stanovnika <code>int brojStanovnika</code>
     * iz korisnickog unosa <code>Scanner input</code>.
     * <p>
     * Ao je uneseni broj stanovnika <code>int brojStanovnika</code> manji od 0 , u logger upisuje ispisuje gresku
     * <code>logger.error("Prilikom unosa broja stanovnika, unesen je negativan broj")</code>
     *
     * @param input    korisnicki unos
     * @param zupanije referenca na polje županija
     */

    private static void unosZupanija(Scanner input, Zupanija[] zupanije) {
        String nazivZupanije;
        int brojStanovnika;
        boolean ispravanUnos = true;

        // Unos županija

        System.out.printf("Unesite podatke o %d zupanije:%n", zupanije.length);
        for (int i = 0; i < zupanije.length; ++i) {

            // Unos naziva županija

            System.out.printf("Unesite naziv zupanije: ");

            nazivZupanije = input.nextLine();

            // Unos i validacija unosa broja stanovnika

            do {

                try {

                    System.out.printf("Unesite broj stanovnika: ");

                    brojStanovnika = input.nextInt();

                    input.nextLine();

                    if (brojStanovnika < 0) {

                        System.out.println("Pogrešan unos! Molimo unesite pozitivan cijeli broj.");

                        logger.error("Prilikom unosa broja stanovnika, unesen je negativan broj.");

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesen je broj stanovnika: " + Integer.toString(brojStanovnika));

                        ispravanUnos = true;

                        zupanije[i] = new Zupanija(nazivZupanije, brojStanovnika);

                    }


                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa broja stanovnika je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }
            } while (!ispravanUnos);
        }
    }

    /**
     * Unosi simptome u polje simptoma <code>Simptom[] simptomi</code>
     * <p>
     * Unosi nazive simptoma <code>String nazivSimptoma</code> i vrijednosti simptoma <code>String vrijednostSimptoma</code>
     * (RIJETKO, SREDNJE, CESTO) iz korisnickog unosa <code>Scanner input</code> i sprema ih u polje Simptoma <code>Simptom[] simptomi</code>
     * <p>
     * Ako je unesena vrijednost simptoma izvan dozvoljenog raspona (RIJETKO, SREDNJE, ČESTO), u log upisuje gresku
     * <code>logger.error("Prilikom unosa pojave vrijednosti simptoma je broj izvan raspona dopustenih vrijednosti.")</code>
     *
     * @param input    korisnički unos
     * @param simptomi referenca na polje simptoma
     */

    private static void unosSimptoma(Scanner input, Simptom[] simptomi) {
        String nazivSimptoma;
        String vrijednostSimptoma;

        // Unos simptoma

        System.out.printf("Unesite podatke o %d simptoma:%n", simptomi.length);

        for (int i = 0; i < simptomi.length; ++i) {

            // Unos naziva simptoma

            System.out.printf("Unesite naziv simptoma: ");

            nazivSimptoma = input.nextLine();

            // Unos i validacija unosa vrijednosti simptoma

            do {

                System.out.printf("Unesite vrijednost simptoma(%s, %s, %s): ", Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO);
                vrijednostSimptoma = input.nextLine();

                // if(!vrijednostSimptoma.in([RIJETKO, SREDNJE, CESTO])) Provjera pojave vrijednosti simptoma analogno IN Operatoru u SQL

                if (!Arrays.asList(Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO).contains(vrijednostSimptoma)) {

                    System.out.println("Pogrešan unos simptoma !");

                    logger.error("Prilikom unosa pojave vrijednosti simptoma je broj izvan raspona dopuštenih vrijednosti.");

                }

            } while (!Arrays.asList(Simptom.RIJETKO, Simptom.SREDNJE, Simptom.CESTO).contains(vrijednostSimptoma));

            simptomi[i] = new Simptom(nazivSimptoma, vrijednostSimptoma);

        }
    }

    /**
     * Unosi bolesti u polje bolesti <code>Bolest[] bolesti</code>
     * <p>
     * Unosi nazive bolesti/virusa <code>String nazivBolestiIliVirusa</code> i simptome <code>Simptom[] simptomi</code>
     * koje sprema u polje bolesti <code>Bolest[] bolesti</code>
     * Ako je unesena vrijednost bolest/virus <code>int bolestIliVirus</code> izvan dozvoljenog raspona (1. Bolest 2. Virus) U log upisuje gresku
     * <code>logger.error("Prilikom unosa Bolesti ili Virusa unesen je broj izvan raspona dopustenih brojeva.");</code>
     * <p>
     * Ako unesena vrijednost bolesti/virusa <code>int bolestIliVirus</code> nije cijeli broj <code>int</code> obrađuje gresku i upisuje u log
     * <code>logger.error("Prilikom unosa bolesti ili virusa je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * <p>
     * Ako je unesena vrijednost bolest/virus <code>int bolestIliVirus</code> izvan dozvoljenog raspona (1. Bolest 2. Virus) u log upisuje gresku
     * <code>System.out.println("Pogresan unos broja simptoma ! Unesen je broj izvan raspona ukupnog broja mogućih simptoma.");</code>
     * <p>
     * Ako unesena vrijednost broja simptoma <code>int brojOdabranihSimptoma</code> nije cijeli broj <code>int</code> obrađuje gresku i upisuje u log
     * <code>logger.error("Prilikom unosa broja simptoma je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * <p>
     * Ako je unesena vrijednost broja simptoma <code>int brojOdabranihSimptoma</code> izvan dozvoljenog raspona (1, <code>simptomi.length</code>) u log upisuje gresku
     * <code>System.out.println("Pogresan unos broja simptoma ! Unesen je broj izvan raspona ukupnog broja mogućih simptoma.");</code>
     * <p>
     * Ako su uneseni simptomi <code>Simptom[] kopiraniSimptomi</code> već prisutni u prethodno navedenim bolestima ili virusima
     * <code>Bolest[] bolesti</code> preko provjere u metodi <code>provjeraBolestiIstihSimptoma(bolesti, kopiraniSimptomi, i);</code> baca gresku koju
     * upisuje u log <code>logger.error(ex.getMessage(), ex);</code>
     *
     * @param input    korisnički unos
     * @param simptomi referenca na polje simptoma
     * @param bolesti  referenca na polje bolesti
     */

    private static void unosBolesti(Scanner input, Simptom[] simptomi, Bolest[] bolesti) {
        String nazivBolestiIliVirusa;
        int brojOdabranihSimptoma = 0, odabraniSimptom = 0;
        int[] odabraniSimptomi = null;
        Simptom[] kopiraniSimptomi;
        int bolestIliVirus = 0;
        boolean ispravanUnos = true;

        System.out.printf("Unesite podatke o %d bolesti ili virusa:%n", bolesti.length);

        for (int i = 0; i < bolesti.length; ++i) {

            // Odabir unosa bolesti ili virusa i validacija unosa

            do {

                try {

                    System.out.printf("Unosite li bolest ili virus ?%n1)BOLEST%n2)VIRUS%n");

                    bolestIliVirus = input.nextInt();

                    input.nextLine();

                    if (bolestIliVirus != 1 && bolestIliVirus != 2) {

                        System.out.println("Pogresan unos! Molimo unesite jedan od ponuđenih brojeva.");

                        logger.error("Prilikom unosa Bolesti ili Virusa unesen je broj izvan raspona dopuštenih brojeva.");

                        ispravanUnos = false;

                    } else {

                        logger.info((bolestIliVirus == 1 ? "Unesena je  Bolest: " : "Unesen je Virus: ")
                                + Integer.toString(bolestIliVirus));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa bolesti ili virusa je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }

            } while (!ispravanUnos);

            do {

                System.out.printf("Unesite naziv bolesti ili virusa: ");

                nazivBolestiIliVirusa = input.nextLine();

                // Unos Broja Odabranih Simptoma i validacija unosa

                do {

                    try {
                        System.out.printf("Unesite broj simptoma: ");

                        brojOdabranihSimptoma = input.nextInt();

                        input.nextLine();

                        if (brojOdabranihSimptoma > simptomi.length || brojOdabranihSimptoma < 1) {

                            System.out.println("Pogresan unos broja simptoma ! Unesen je broj izvan raspona ukupnog broja mogućih simptoma.");

                            logger.error("Prilikom unosa broja simptoma unesen je broj izvan raspona ukupnog broja mogućih simptoma.");

                            ispravanUnos = false;

                        } else {

                            logger.info("Uneseni broj simptoma: " + Integer.toString(brojOdabranihSimptoma));

                            ispravanUnos = true;

                            odabraniSimptomi = new int[brojOdabranihSimptoma];
                        }

                    } catch (InputMismatchException ex) {

                        logger.error("Prilikom unosa broja simptoma je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                        System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                        input.nextLine();

                        ispravanUnos = false;
                    }

                } while (!ispravanUnos);

                // Unos odabranih simptoma i validacija

                for (int j = 0; j < brojOdabranihSimptoma; ++j) {

                    // Biranje Postojeceg Simptoma i validacija unosa

                    do {

                        System.out.printf("Odaberite %d. simptom:%n", j + 1);

                        // Ispis Postojecih Simptoma

                        for (int k = 0; k < simptomi.length; ++k) {
                            System.out.printf("%d. %s %s%n", k + 1, simptomi[k].getNaziv(), simptomi[k].getVrijednost());
                        }

                        try {
                            System.out.print("Odabir: ");

                            odabraniSimptom = input.nextInt();

                            input.nextLine();

                            if (odabraniSimptom > simptomi.length || odabraniSimptom < 1) {

                                System.out.println("Neispravan unos, molimo pokusajte ponovno!");

                                logger.error("Prilikom biranja simptoma unesen je broj izvan raspona ukupnog broja postojećih simptoma.");

                                ispravanUnos = false;

                            } else {

                                ispravanUnos = true;

                                // Provjera postojanosti Odabranog Postojeceg Simptoma u prethodno Odabranim Simptomima

                                for (int k = 0; k < odabraniSimptomi.length; ++k) {

                                    if (odabraniSimptomi[k] == odabraniSimptom) {

                                        System.out.println("Odabrani Simptom je vec unesen! Molimo odaberite ponovno.");

                                        logger.error("Odabran je Simptom koji je već unesen: " + Integer.toString(odabraniSimptom));

                                        ispravanUnos = false;

                                        break;
                                    }
                                }

                                if (ispravanUnos) {

                                    logger.info("Odabran je (broj) simptom is postojećih simptoma: " + Integer.toString(odabraniSimptom));

                                    odabraniSimptomi[j] = odabraniSimptom;
                                }
                            }
                        } catch (InputMismatchException ex) {

                            logger.error("Prilikom unosa brojčane vrijednosti kod biranja postojećih simptoma je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                            System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                            input.nextLine();

                            ispravanUnos = false;

                        }

                    } while (!ispravanUnos);
                }

                // Kopiranje Postojecih Simptoma u novo polje Kopiranih Simptoma

                kopiraniSimptomi = new Simptom[brojOdabranihSimptoma];

                for (int j = 0; j < brojOdabranihSimptoma; ++j) {
                    kopiraniSimptomi[j] = new Simptom(simptomi[odabraniSimptomi[j] - 1].getNaziv(), simptomi[odabraniSimptomi[j] - 1].getVrijednost());
                }

                // Provjera duplikata unosa Simptoma

                if (i > 0) {

                    try {

                        provjeraBolestiIstihSimptoma(bolesti, kopiraniSimptomi, i);

                        ispravanUnos = true;

                    } catch (BolestIstihSimptoma ex) {

                        logger.error(ex.getMessage(), ex);

                        ispravanUnos = false;

                    }

                }

            } while (!ispravanUnos);

            // Provjera da li je unos bolest ili virus i unos u polje bolesti

            bolesti[i] = bolestIliVirus == 1 ? new Bolest(nazivBolestiIliVirusa, kopiraniSimptomi) : new Virus(nazivBolestiIliVirusa, kopiraniSimptomi);
        }
    }

    /**
     * Provjerava postojanost unesenih simptoma <code>Simptom[] kopiraniSimptomi</code> u polju simptoma <code>bolesti[i].getSimptomi()</code> prethodno unesenih bolesti
     * <code>Bolest[] bolesti</code>
     * <p>
     * Ako su trenutno uneseni simptomi <code>Simptom[] kopiraniSimptomi</code> prisutni u simptomima polja prethodno unesenih bolesti <code>Bolest[] bolesti</code>
     * baca grešku <code>throw new BolestIstihSimptoma("Uneseni simptomi su duplikati iz prethodno unesenih bolesti!");</code>
     *
     * @param bolesti                     referenca na polje bolesti koje su trenutno unesene
     * @param kopiraniSimptomi            referenca na polje simptoma za bolest koja se trenutno unosi
     * @param brojTrenutnoUnesenihBolesti broj trenutno unesenih bolesti
     * @throws BolestIstihSimptoma iznimka koja se baca u slučaju kad su trenutno uneseni simptomi <code>Simptom[] kopiraniSimptomi</code>
     *                             prisutni u prethodno unesenim bolestima <code>Bolest[] bolesti</code>
     */

    private static void provjeraBolestiIstihSimptoma(Bolest[] bolesti, Simptom[] kopiraniSimptomi, int brojTrenutnoUnesenihBolesti) throws BolestIstihSimptoma {
        boolean flag = true;

        Arrays.sort(kopiraniSimptomi, new Comparator<Simptom>() {
            public int compare(Simptom s1, Simptom s2) {
                return s1.getNaziv().compareToIgnoreCase(s2.getNaziv());
            }
        });

        for (int i = 0; i < brojTrenutnoUnesenihBolesti; ++i) {

            Arrays.sort(bolesti[i].getSimptomi(), new Comparator<Simptom>() {
                public int compare(Simptom s1, Simptom s2) {
                    return s1.getNaziv().compareToIgnoreCase(s2.getNaziv());
                }
            });

            if (bolesti[i].getSimptomi().length == kopiraniSimptomi.length) {

                flag = true;

                for (int j = 0; j < kopiraniSimptomi.length; ++j) {
                    if (!bolesti[i].getSimptomi()[j].equals(kopiraniSimptomi[j])) {
                        flag = false;
                    }
                }

                if (flag) {

                    System.out.println("Unesena bolest ne smije imati simptome jednake prethodno unesenim bolestima!");

                    System.out.println("Molimo Vas da ponovno unesete bolest.");

                    throw new BolestIstihSimptoma("Uneseni simptomi su duplikati iz prethodno unesenih bolesti!");

                }
            }
        }
    }

    /**
     * Ispisuje osobe <code>Osoba[] osobe</code> koje su unesene u program
     *
     * @param osobe osobe koje su unesene u program
     */

    private static void ispisOsoba(Osoba[] osobe) {
        System.out.println("Popis osoba:");

        for (Osoba osoba : osobe) {
            System.out.print(osoba.toString());
        }
    }

    /**
     * Unosi osobe u polje osoba <code>Osoba[] osobe</code>
     * <p>
     * Unosi ime osobe <code>String ime</code> i prezime osobe <code>String prezime</code> i unosi starost osobe <code>Integer starost</code>
     * <p>
     * Ako je starost osobe <code>Integer starost</code> manja od 0 u log upisuje gresku
     * <code>logger.error("Prilikom unosa starosti osobe, unesen je negativan broj: " + Integer.toString(starost));</code>
     * <p>
     * Ako u starost osobe <code>Integer starost</code> nije unesena brojčana vrijednost obrađuje iznimku <code>InputMismatchException ex</code>
     * i upisuje gresku u log <code>logger.error("Prilikom unosa brojčane vrijednosti kod starosti osobe je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * <p>
     * Unosi županiju osobe <code>Zupanija zupanija</code> i ako je odabrana županija <code>int odabranaZupanija</code> izvan raspona dostupnih županija <code>Zupanija[] zupanije</code>
     * u log upisuje gresku <code>logger.error("Prilikom unosa županije osobe, unesen je broj izvan prethodno navedenog raspona: " + Integer.toString(odabranaZupanija));</code>
     * <p>
     * Ako odabrana županija <code>int odabranaZupanija</code> nije cijeli broj <code>int</code> obrađuje iznimku <code>InputMismatchException ex</code>
     * i upisuje gresku u log <code>logger.error("Prilikom unosa brojčane vrijednosti kod biranja županije osobe je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * <p>
     * Unosi odabir bolest ili virus osobe <code>int odabranaBolest</code> i ako je unesena vrijednost izvan raspona dostupnih bolesti
     * u log upisuje gresku <code>logger.error("Prilikom unosa bolesti/virusa osobe, unesen je broj izvan prethodno navedenog raspona: " + Integer.toString(odabranaBolest));</code>
     * <p>
     * Ako odabrana bolest ili virus <code>int odabranaBolest</code> nije cijeli broj <code>int</code> obrađuje iznimku <code>InputMismatchException ex</code>
     * i upisuje gresku u log <code>logger.error("Prilikom unosa brojčane vrijednosti kod biranja bolesti/virusa osobe je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * <p>
     * Ako je broj trenutno unesenih osoba veći ili jednak 1 <code>if(i > 0)</code> unosi broj kontaktiranih osoba <code>int brojKontaktiranihOsoba</code> i sprema ih u polje
     * <code>int[] odabraneKontaktiraneOsobe</code>
     * <p>
     * Unosi odabir kontaktiranih osoba i upisuje trenutno kontaktiranu osobu u <code>int odabranaKontaktiranaOsoba</code> i ako je unesena vrijednost izvan raspona dostupnih prethodno unesenih
     * osoba <code>if (brojKontaktiranihOsoba > i || brojKontaktiranihOsoba < 0)</code> u log upisuje gresku
     * <code> logger.error("Prilikom unosa broja kontaktiranih osoba, unesen je broj izvan raspona unesenog broja osoba: " + Integer.toString(brojKontaktiranihOsoba));</code>
     * <p>
     * Ako uneseni broj kontaktirane osobe <code>int odabranaKontaktiranaOsoba</code> nije cijeli broj <code>int</code> obrađuje iznimku <code>InputMismatchException ex</code>
     * i upisuje gresku u log <code>logger.error("Prilikom unosa brojčane vrijednosti kod unosa odabrane kontaktirane osobe je doslo do pogreske. Unesen je String koji se ne može parsirati!", ex);</code>
     * Provjerava unos duplikata <code>provjeraDuplikataKontaktiranihOsoba(odabranaKontaktiranaOsoba, odabraneKontaktiraneOsobe);</code> i obrađuje iznimku <code>DuplikatKontaktiraneOsobe ex</code>
     * i upisuje gresku u log <code> logger.error(ex.getMessage(), ex);</code>
     *
     * @param input    korisnički unos
     * @param zupanije referenca na polje unesenih županija
     * @param bolesti  referenca na polje unesenih bolesti
     * @param osobe    referenca na polje unesenih osoba
     */

    private static void unosOsoba(Scanner input, Zupanija[] zupanije, Bolest[] bolesti, Osoba[] osobe) {
        boolean ispravanUnos = true;
        int odabranaZupanija = 0;
        int odabranaBolest = 0;
        int odabranaKontaktiranaOsoba = 0;
        int[] odabraneKontaktiraneOsobe;
        int brojKontaktiranihOsoba = 0;
        String ime, prezime;
        Integer starost = 0;
        Zupanija zupanija;
        Bolest zarazenBolescu;
        Osoba[] kontaktiraneOsobe = null;

        for (int i = 0; i < osobe.length; ++i) {

            // Unos imena

            System.out.printf("Unesite ime %d. osobe: ", i + 1);
            ime = input.nextLine();

            // Unos prezimena

            System.out.printf("Unesite prezime %d. osobe: ", i + 1);
            prezime = input.nextLine();

            // Unos starosti i validacija unosa

            do {

                try {

                    System.out.printf("Unesite starost osobe: ");

                    starost = input.nextInt();

                    input.nextLine();

                    if (starost < 0) {
                        System.out.println("Unesena vrijednost ne smije biti negativan broj! Molimo ponovite unos.");

                        logger.error("Prilikom unosa starosti osobe, unesen je negativan broj: " + Integer.toString(starost));

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesena je starost osobe: " + Integer.toString(starost));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod starosti osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }

            } while (!ispravanUnos);


            // Unos zupanije prebivalista i validacija

            do {

                try {

                    System.out.printf("Unesite županiju prebivališta osobe:%n");

                    for (int j = 0; j < zupanije.length; ++j) {
                        System.out.printf("%d. %s%n", j + 1, zupanije[j].getNaziv());
                    }

                    System.out.print("Odabir: ");

                    odabranaZupanija = input.nextInt();

                    input.nextLine();

                    // Provjera ispravnosti unosa Odabrane Zupanije

                    if (odabranaZupanija < 1 || odabranaZupanija > zupanije.length) {

                        System.out.println("Pogresan unos županije!");

                        logger.error("Prilikom unosa županije osobe, unesen je broj izvan prethodno navedenog raspona: "
                                + Integer.toString(odabranaZupanija));

                        ispravanUnos = false;

                    } else {

                        logger.info("Unesen je odabir županije: " + Integer.toString(odabranaZupanija));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod biranja županije osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;
                }


            } while (!ispravanUnos);


            zupanija = zupanije[odabranaZupanija - 1];

            // Unos bolesti osobe

            do {

                try {

                    System.out.println("Unesite bolest ili virus osobe:");

                    for (int j = 0; j < bolesti.length; ++j) {
                        System.out.printf("%d. %s%n", j + 1, bolesti[j].getNaziv());
                    }

                    System.out.print("Odabir: ");

                    odabranaBolest = input.nextInt();

                    input.nextLine();

                    // Provjera ispravnosti unosa Odabrane Bolesti Osobe

                    if (odabranaBolest < 1 || odabranaBolest > bolesti.length) {

                        System.out.println("Pogrešan unos bolesti/virusa!");

                        logger.error("Prilikom unosa bolesti/virusa osobe, unesen je broj izvan prethodno navedenog raspona: "
                                + Integer.toString(odabranaBolest));

                        ispravanUnos = false;

                    } else {

                        logger.info(((bolesti[odabranaBolest - 1] instanceof Virus) ? "Unesen je virus: " : "Unesena je bolest: ")
                                + Integer.toString(odabranaBolest));

                        ispravanUnos = true;

                    }

                } catch (InputMismatchException ex) {

                    logger.error("Prilikom unosa brojčane vrijednosti kod biranja bolesti/virusa osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                    System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                    input.nextLine();

                    ispravanUnos = false;

                }

            } while (!ispravanUnos);


            zarazenBolescu = bolesti[odabranaBolest - 1] instanceof Virus ?
                    new Virus(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi()) :
                    new Bolest(bolesti[odabranaBolest - 1].getNaziv(), bolesti[odabranaBolest - 1].getSimptomi());


            // Provjera osoba s kojim je osoba usla u kontakt u slucaju da nije prva osoba - prva se ne gleda

            if (i > 0) {

                // Unos broja kontaktiranih osoba i validacija

                do {

                    try {

                        System.out.println("Unesite broj osoba koje su bile u kontaktu s tom osobom:");

                        brojKontaktiranihOsoba = input.nextInt();

                        input.nextLine();

                        // Provjera unosa broja kontaktiranih osoba

                        if (brojKontaktiranihOsoba > i || brojKontaktiranihOsoba < 0) {

                            System.out.println("Greska u unosu broja kontaktiranih osoba. Broj trenutno unesenih osoba je: " + Integer.toString(i));

                            logger.error("Prilikom unosa broja kontaktiranih osoba, unesen je broj izvan raspona unesenog broja osoba: "
                                    + Integer.toString(brojKontaktiranihOsoba));

                            ispravanUnos = false;

                        } else {

                            ispravanUnos = true;

                            logger.info("Unesen je broj kontaktiranih osoba: " + Integer.toString(brojKontaktiranihOsoba));

                        }

                    } catch (InputMismatchException ex) {

                        logger.error("Prilikom unosa brojčane vrijednosti kod biranja broja kontaktiranih osoba je došlo do pogreške. Unesen je String koji se ne može parsirati!", ex);

                        System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                        input.nextLine();

                        ispravanUnos = false;

                    }

                } while (!ispravanUnos);

                if (brojKontaktiranihOsoba > 0) {

                    // Unos i validacija Odabranih Kontaktiranih Osoba

                    odabraneKontaktiraneOsobe = new int[brojKontaktiranihOsoba];

                    for (int j = 0; j < brojKontaktiranihOsoba; ++j) {

                        do {

                            // Unos Odabrane Kontaktirane Osobe

                            try {

                                System.out.printf("Odaberite %d. osobu: %n", j + 1);

                                for (int k = 0; k < i; ++k) {
                                    System.out.printf("%d. %s %s%n", k + 1, osobe[k].getIme(), osobe[k].getPrezime());
                                }

                                System.out.print("Odabir: ");

                                odabranaKontaktiranaOsoba = input.nextInt();

                                input.nextLine();

                                // Provjera unosa Odabrane Kontaktirane Osobe

                                if (odabranaKontaktiranaOsoba < 1 || odabranaKontaktiranaOsoba > i) {

                                    System.out.println("Greška pri unosu odabrane kontaktirane osobe");

                                    logger.error("Prilikom unosa odabira kontaktirane osobe, unesen je broj izvan raspona unesenog broja osoba: "
                                            + Integer.toString(odabranaKontaktiranaOsoba));

                                    ispravanUnos = false;

                                } else {

                                    // Provjera Duplikata Kontaktiranih Osoba i obrada greške

                                    provjeraDuplikataKontaktiranihOsoba(odabranaKontaktiranaOsoba, odabraneKontaktiraneOsobe);

                                    ispravanUnos = true;

                                    logger.info("Unesen je odabir kontaktirane osobe: " + Integer.toString(odabranaKontaktiranaOsoba));

                                    odabraneKontaktiraneOsobe[j] = odabranaKontaktiranaOsoba;


                                }

                            } catch (InputMismatchException e) {

                                logger.error("Prilikom unosa brojčane vrijednosti kod unosa odabrane kontaktirane osobe je došlo do pogreške. Unesen je String koji se ne može parsirati!", e);

                                System.out.println("Došlo je do pogreške kod unosa brojčane vrijednosti! Molimo ponovite unos.");

                                input.nextLine();

                                ispravanUnos = false;

                            } catch (DuplikatKontaktiraneOsobe ex) {

                                logger.error(ex.getMessage(), ex);

                                ispravanUnos = false;

                            }

                        } while (!ispravanUnos);

                    }

                    // Spremanje Odabranih Kontaktiranih Osoba u polje Kontaktiranih Osoba

                    kontaktiraneOsobe = new Osoba[odabraneKontaktiraneOsobe.length];

                    for (int j = 0; j < odabraneKontaktiraneOsobe.length; ++j) {
                        kontaktiraneOsobe[j] = osobe[odabraneKontaktiraneOsobe[j] - 1];
                    }
                }
            }

            // Spremanje osoba u polje osoba

            if (i == 0) {
                osobe[i] = new Osoba.Builder(ime).prezime(prezime).starost(starost).zupanija(zupanija)
                        .zarazenBolescu(zarazenBolescu).build();
            } else {
                osobe[i] = new Osoba.Builder(ime).prezime(prezime).starost(starost).zupanija(zupanija)
                        .zarazenBolescu(zarazenBolescu).kontaktiraneOsobe(kontaktiraneOsobe).build();
            }
        }
    }

    /**
     * Provjerava postojanost odabrane kontaktirane osobe <code>int odabranaKontaktiranaOsoba</code> u polju
     * <code>int[] odabraneKontaktiraneOsobe</code> i provjerava duplikate
     * ako postoji duplikat baca iznimku <code>throw new DuplikatKontaktiraneOsobe("Prilikom unosa odabira kontaktirane osobe, unesena je prethodno odabrana osoba (duplikat): "
     * + Integer.toString(odabranaKontaktiranaOsoba));</code>
     *
     * @param odabranaKontaktiranaOsoba unesena odabrana kontaktirana osoba
     * @param odabraneKontaktiraneOsobe polje prethodno odabranih kontaktiranih osoba
     * @throws DuplikatKontaktiraneOsobe iznimka koja se baca u slučaju kada su uneseni duplikati
     */

    private static void provjeraDuplikataKontaktiranihOsoba(int odabranaKontaktiranaOsoba, int[] odabraneKontaktiraneOsobe) throws DuplikatKontaktiraneOsobe {

        // (Provjera duplikata) Provjera postojanosti Odabrane Kontaktirane Osobe u prethodno Odabranim Kontaktiranim Osobama

        for (int k = 0; k < odabraneKontaktiraneOsobe.length; ++k) {

            if (odabraneKontaktiraneOsobe[k] == odabranaKontaktiranaOsoba) {

                System.out.println("Osoba je već odabrana, molimo ponovno unesite!");

                throw new DuplikatKontaktiraneOsobe("Prilikom unosa odabira kontaktirane osobe, unesena je prethodno odabrana osoba (duplikat): "
                        + Integer.toString(odabranaKontaktiranaOsoba));

            }
        }
    }
}




























