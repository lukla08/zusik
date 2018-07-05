package com.example.jac.place.backend.model.utils;

import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.SalaryItems;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalaryItemsCalcUtils {
    public static SalaryItems prepareSalaryItems4Employee(Employee employee) {

        SalaryItems items = new SalaryItems();

        items.setEmployeeId(employee.getEmployeeId());
        items.setEmployee_salary(employee.getSalary());
        items.setEmployee_salary12m(employee.getAvg12MSalary());

        items.setEmployee_illnessDays(employee.getIllnessDays());

        //	http://www.wskazniki.gofin.pl/8,223,2,przykladowe-obliczenie-wynagrodzenia-netto.html
        //	http://meritumbiuro.com.pl/jak-obliczyc-wynagrodzenie-chorobowe/

        // Szczegoly dotyczace skladek przy niskim wynagrodzeniu :
        //  https://www.kadrywpraktyce.pl/liczyc-zaliczke-podatek-dochodowy-przy-niskim-wynagrodzeniu-pracownika/

        // przecietne wynagrodzenie za ostatnie 12 mies bez skladek
        double calcSalary12mNetto = round2Digits(items.getEmployee_salary12m() * (1 - 0.1371));
        items.setCalc_salaray12m_netto(calcSalary12mNetto);

        // dzienna stawka chorobowa
        double calcIllnessDailyRate = round2Digits((calcSalary12mNetto * 0.8)  / 30);
        items.setCalc_ilnessDailyRate(calcIllnessDailyRate);

        // wynagrodzenie za czas choroby
        double calcSalaryIllnessPart = round2Digits(calcIllnessDailyRate * items.getEmployee_illnessDays());
        items.setCalc_salaryIllnessPart(calcSalaryIllnessPart);

        // dzienna stawka przepracowane
        double calcNormalDailyRate = round2Digits(items.getEmployee_salary() / 30);
        items.setCalc_normalDailyRate(calcNormalDailyRate);

        // normalne wynagrodzenie przypadające w okresie choroby
        double calcSalaryNormalOnIllness = calcNormalDailyRate * items.getEmployee_illnessDays();
        items.setCalc_salaryNormalOnIllness(calcSalaryNormalOnIllness);

        // wynagrodzenie za przepracowany czas
        double calcSalaryNormalPart = round2Digits(items.getEmployee_salary() - calcSalaryNormalOnIllness);
        items.setCalc_salaryNormalPart(calcSalaryNormalPart);

        // całkowite wynagrodzenie
        double calcSalaryTotal = round2Digits(calcSalaryIllnessPart + calcSalaryNormalPart);
        items.setCalc_salaryTotal(calcSalaryTotal);

        // podstawa na spoleczne
        double calcBase4SocialTaxes = calcSalaryNormalPart;
        items.setCalc_base4SocialTaxes(calcBase4SocialTaxes);

        // wyliczona skladka pracowonika: emerytalne
        double calcEmployeeSocialPension = round2Digits(calcBase4SocialTaxes * 9.76 / 100);
        items.setCalc_employeeSocialPension(calcEmployeeSocialPension);

        // wyliczona skladka pracowonika: rentowe
        double calcEmployeeSocialRent = round2Digits(calcBase4SocialTaxes * 1.5 / 100);
        items.setCalc_employeeSocialRent(calcEmployeeSocialRent );

        // wyliczona skladka pracowonika: chorobowe
        double calcEmployeeSocialIllness = round2Digits(calcBase4SocialTaxes * 2.45 / 100);
        items.setCalc_employeeSocialIllness(calcEmployeeSocialIllness);

        // suma skladek spolecznych pracownika
        double calcEmployeeSocialTotal =
                round2Digits(calcEmployeeSocialPension + calcEmployeeSocialRent + calcEmployeeSocialIllness);
        items.setCalc_employeeSocialTotal(calcEmployeeSocialTotal);

        // podstawa  na ubezpieczenie zdrowotne
        double calcBase4healthTax = round2Digits(calcSalaryTotal - calcEmployeeSocialTotal);
        items.setCalc_base4healthTax(calcBase4healthTax );

        // podstawa opodatkowania
        long calcBase4IncomeTax = round2Long(calcSalaryTotal - items.getCalc_costsOfObtaining() - calcEmployeeSocialTotal);
        items.setCalc_base4IncomeTax(calcBase4IncomeTax );

        // zaliczka na dochodowy brutto
        double calcAdvance4IncomeTaxBrutto = Math.max((calcBase4IncomeTax * 0.18) - 46.33, 0);
        items.setCalc_advance4IncomeTaxBrutto(calcAdvance4IncomeTaxBrutto);

        // skladka zdrowotna do pobrania
        double calcHealthTaxToTake = Math.min(round2Digits(calcBase4healthTax * 0.09), calcAdvance4IncomeTaxBrutto);
        items.setCalc_healthTaxToTake(calcHealthTaxToTake);

        // skladka zdrowotna do odliczenia
        double calcHealthTaxToDeduct = Math.min(round2Digits(calcBase4healthTax * 0.0775), calcAdvance4IncomeTaxBrutto);
        items.setCalc_healthTaxToDeduct(calcHealthTaxToDeduct);


        // zaliczka na dochodowy
        long calcAdvance4IncomeTax = round2Long(Math.max((calcAdvance4IncomeTaxBrutto - calcHealthTaxToDeduct), 0));
        items.setCalc_advance4IncomeTax(calcAdvance4IncomeTax);

        // kwota do wyplaty
        double calcAmountDue = round2Digits(calcSalaryTotal - calcEmployeeSocialTotal - calcHealthTaxToTake - calcAdvance4IncomeTax);
        items.setCalc_AmountDue(calcAmountDue);

        // wyliczona skladka pracodawcy: emerytalne
        double calcBossSocialPension = round2Digits(calcBase4SocialTaxes * 9.76 / 100);
        items.setCalc_BossSocialPension(calcBossSocialPension);

        // wyliczona skladka pracodawcy: rentowa
        double calcBossSocialRent = round2Digits(calcBase4SocialTaxes *  6.5 / 100);
        items.setCalc_BossSocialRent(calcBossSocialRent);

        // wyliczona skladka pracodawcy: wypadkowa
        double calcBossSocialAccident = round2Digits(calcBase4SocialTaxes *  1.8 / 100);
        items.setCalc_BossSocialAccident(calcBossSocialAccident);

        // wyliczona skladka pracodawcy: FP
        double calcBossFP = round2Digits(calcBase4SocialTaxes *  2.45 / 100);
        items.setCalc_BossFP(calcBossFP);

        // wyliczona skladka pracodawcy: FGSP
        double calcBossFGSP = round2Digits(calcBase4SocialTaxes *  0.1 / 100);
        items.setCalc_BossFGSP(calcBossFGSP);

        double totalCost = round2Digits(calcSalaryTotal + calcBossSocialPension + calcBossSocialRent + calcBossSocialAccident + calcBossFGSP + calcBossFP);
        items.setCalc_TotalCost(totalCost);

        return items;
    }

    public static final double DELTA_2_DIGITS = 0.0001d;
    private static double round2Digits(double v) {
        BigDecimal bd;
        if(isPositive(v))
            bd = new BigDecimal(v+DELTA_2_DIGITS).setScale(2, RoundingMode.HALF_UP);
        else
            bd = new BigDecimal(v-DELTA_2_DIGITS).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isPositive(double value) {
        return BigDecimal.valueOf(value).compareTo(BigDecimal.ZERO) > 0;
    }


    private static long round2Long(double v) {
        if (v > 0)
            return Math.round(v + 0.00001);

        return Math.round(v - 0.00001);
    }


};




//public class ActivityImporter extends DataSetImporter<IntermediateActivity> {
//    public ActivityImporter() {
//        super(IntermediateActivity.class, ActivityImportDefinition.class, ImportDataSetCodes.ACTIVITY);
//    }
//
//    static long round2Int(double v) {
//        return Math.round(v + 0.001);
//    }
//
//    public static void main(String[] args) {
////		http://www.wskazniki.gofin.pl/8,223,2,przykladowe-obliczenie-wynagrodzenia-netto.html
////		http://meritumbiuro.com.pl/jak-obliczyc-wynagrodzenie-chorobowe/
//
//        double kosztyUzyskania_pozG = 111.25;
//        double wynagrodzenieZaPrace_pozA = 1680;
//        double wynagrodzenieZaPrace12Mies = 1620;
//        int iloscChorobowe = 3;
//
//        System.out.println("wynagrodzenieZaPrace_pozA : " + wynagrodzenieZaPrace_pozA);
//        System.out.println("wynagrodzenieZaPrace12Mies : " + wynagrodzenieZaPrace12Mies);
//        System.out.println("iloscChorobowe : " + iloscChorobowe);
//        System.out.println("");
//
//        double przecietneWynagrodzenieBezSkladek =  (wynagrodzenieZaPrace12Mies * (1 - 0.1371));
//        double stawkaDziennaChorobowe = DoubleUtils.round2Digits((przecietneWynagrodzenieBezSkladek *0.8) / 30);
//        double wynagrodzenieChorobowe = stawkaDziennaChorobowe * iloscChorobowe;
//        System.out.println("stawkaDziennaChorobowe : " + stawkaDziennaChorobowe);
//        System.out.println("wynagrodzenieChorobowe : " + wynagrodzenieChorobowe);
//
//        double stawkaDziennaPrzepracowane = DoubleUtils.round2Digits((wynagrodzenieZaPrace_pozA) / 30);
//        double wynagrodzeniePrzypadajaceNaNieobecnosci = stawkaDziennaPrzepracowane * iloscChorobowe;
//        double wynagrodzenieZaPrzepracowanaCzescMies = wynagrodzenieZaPrace_pozA - wynagrodzeniePrzypadajaceNaNieobecnosci;
//
//
//        System.out.println("stawkaDziennaPrzepracowane : " + stawkaDziennaPrzepracowane);
//        System.out.println("wynagrodzenieZaPrzepracowanaCzescMies : " + wynagrodzenieZaPrzepracowanaCzescMies);
//        System.out.println("");
//
//        double wynagrodzenie = wynagrodzenieZaPrzepracowanaCzescMies  + wynagrodzenieChorobowe;
//        System.out.println("wynagrodzenie:" + wynagrodzenie);
//
//        double podstawaSpoleczne = wynagrodzenieZaPrzepracowanaCzescMies;
//        System.out.println("podstawaSpoleczne:" + podstawaSpoleczne);
//
//        double emerytalna = DoubleUtils.round2Digits(podstawaSpoleczne * 9.76 / 100);
//        double rentowa = DoubleUtils.round2Digits(podstawaSpoleczne * 1.5 / 100);
//        double chorobowa = DoubleUtils.round2Digits(podstawaSpoleczne * 2.45 / 100);
//        double spoleczne = emerytalna + rentowa + chorobowa;
//        System.out.println("skladkiNaSpoleczne_pozC:" + spoleczne);
//
////		double podstawaSkladkiDlaZdrowotne_pozD = wynagrodzenieZaPrace_pozA - skladkiNaSpoleczne_pozC;
//        double podstawaWymiaruSkladkiNaZdrowotne_pozD = wynagrodzenie - spoleczne;
//        System.out.println("podstawaWymiaruSkladkiNaZdrowotne_pozD:" + podstawaWymiaruSkladkiNaZdrowotne_pozD);
//
//        double skladkaZdrowotnaDoPobrania_pozE = DoubleUtils.round2Digits(podstawaWymiaruSkladkiNaZdrowotne_pozD * 9 / 100);
//        double skladkaZdrowotnaDoOdliczenia_pozF = DoubleUtils.round2Digits(podstawaWymiaruSkladkiNaZdrowotne_pozD * 7.75 / 100);
//
//        System.out.println("skladkaZdrowotnaDoPobrania_pozE:" + skladkaZdrowotnaDoPobrania_pozE);
//        System.out.println("skladkaZdrowotnaDoOdliczenia_pozF:" + skladkaZdrowotnaDoOdliczenia_pozF);
//
//        long podstawaOpodatkowania = round2Int(wynagrodzenie - kosztyUzyskania_pozG - spoleczne);
//        System.out.println("podstawaOpodatkowania:" + podstawaOpodatkowania);
//
//        double zaliczkNaDochBrutto = Math.max((podstawaOpodatkowania * 0.18) - 46.33, 0);
//        System.out.println("zaliczkNaDochBrutto:" + zaliczkNaDochBrutto);
//
//        long zaliczkNaDoch = round2Int(Math.max(zaliczkNaDochBrutto - skladkaZdrowotnaDoOdliczenia_pozF, 0));
//        System.out.println("zaliczkNaDoch:" + zaliczkNaDoch);
//
//        double kwotaDoWyplaty = wynagrodzenie - spoleczne - skladkaZdrowotnaDoPobrania_pozE - zaliczkNaDoch;
//        System.out.println("kwotaDoWyplaty:" + kwotaDoWyplaty);
//
//
//        double pracodawcaEmerytalne = DoubleUtils.round2Digits(podstawaSpoleczne * 9.76 / 100);
//        double pracodawcaRentowe = DoubleUtils.round2Digits(podstawaSpoleczne * 6.5 / 100);
//        double pracodawcaWypadkowe = DoubleUtils.round2Digits(podstawaSpoleczne * 1.8 / 100);
//        double pracodawcaFp = DoubleUtils.round2Digits(podstawaSpoleczne * 2.45 / 100);
//        double pracodawcaFgsp = DoubleUtils.round2Digits(podstawaSpoleczne * 0.1 / 100);
//        double skladkiPracodawcy_pozL = DoubleUtils.round2Digits(pracodawcaEmerytalne + pracodawcaRentowe + pracodawcaWypadkowe + pracodawcaFp + pracodawcaFgsp);
//        System.out.println("skladkiPracodawcy_pozL:" + skladkiPracodawcy_pozL);
//
//        double kosztPracodawcy = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA + skladkiPracodawcy_pozL);
//        System.out.println("kosztPracodawcy:" + kosztPracodawcy);
//
//
////		System.out.println("spoleczne : " + skladkiNaSpoleczne_pozC);
////		System.out.println("podstawaSkladkiDlaZdrowotne_pozD : " + podstawaWymiaruSkladkiNaZdrowotne_pozD);
////		System.out.println("skladkaZdrowotnaDoPobrania_pozE : " + skladkaZdrowotnaDoPobrania_pozE);
////		System.out.println("skladkaZdrowotnaDoOdliczenia_pozF : " + skladkaZdrowotnaDoOdliczenia_pozF);
////		System.out.println("kosztyUzyskania : " + kosztyUzyskania_pozG);
////		System.out.println("podstawaDlaZaliczkiNaDoch_pozH : " + podstawaDlaZaliczkiNaDoch_pozH);
////		System.out.println("zaliczkNaDochPrzedOdliczeniemZdrow_pozI : " + zaliczkNaDochPrzedOdliczeniemZdrow_pozI);
////		System.out.println("zaliczkaNaDochDoPobrania_pozJ : " + zaliczkaNaDochDoPobrania_pozJ);
////		System.out.println("doWyplaty_pozK : " + doWyplaty_pozK);
////
//////		System.out.println("pracodawcaEmerytalne : " + pracodawcaEmerytalne);
//////		System.out.println("pracodawcaRentowe : " + pracodawcaRentowe);
//////		System.out.println("pracodawcaWypadkowe : " + pracodawcaWypadkowe);
//////		System.out.println("pracodawcaFp : " + pracodawcaFp);
//////		System.out.println("pracodawcaFgsp : " + pracodawcaFgsp);
//////		System.out.println("skladkiPracodawcy_pozL : " + skladkiPracodawcy_pozL);
//////		System.out.println("kosztPracodawcy : " + kosztPracodawcy);
////
////		System.out.println("przecietneWynagrodzenieBezSkladek: " + przecietneWynagrodzenieBezSkladek);
////		System.out.println("stawkaDziennaChorobowe:" + stawkaDziennaChorobowe);
////		System.out.println("stawkaDziennaPrzepracowane:" + stawkaDziennaPrzepracowane);
////
////		System.out.println("wynagrodzenieZaPrzepracowanaCzescMies:" + wynagrodzenieZaPrzepracowanaCzescMies);
////		System.out.println("wynagrodzenieChorobowe:" + wynagrodzenieChorobowe);
////		System.out.println("wynagrodzenie:" + wynagrodzenie);
//
////		System.out.println("podstawaDlaZaliczkiDoch: " + podstawaDlaZaliczkiNaDoch);
////		System.out.println("zaliczkaNaDoch: " + zaliczkaNaDoch);
////		System.out.println("doWyplaty:" + doWyplaty);
////		System.out.println("zdrowotna:" + skladkaZdrowotna);
//    }
//
//}
