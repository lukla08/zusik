package com.example.jac.place.backend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.Settings;

import java.util.List;

@Dao
public interface FirmsDao {
    @Query("select * from firm order by firm_name")
    LiveData<List<Firm>> getFirmsLiveData();

    @Query("select * from firm order by firm_name")
    List<Firm> getFirmsData();

    @Query("Select count(*) from firm")
    int getRecordCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Firm ... firms);

    @Query("select * from firm where firm_id = :id")
    Firm getSelectedFirm(long  id);


}

//    static long round2Int(double v) {
//        return Math.round(v + 0.001);
//    }
//
//    static long round2Int(double v) {
//        return Math.round(v + 0.001);
//    }


//    public static void main(String[] args) {
////		http://www.wskazniki.gofin.pl/8,223,2,przykladowe-obliczenie-wynagrodzenia-netto.html
////		http://meritumbiuro.com.pl/jak-obliczyc-wynagrodzenie-chorobowe/
//
//        double kosztyUzyskania_pozG = 111.25;
//        double wynagrodzenieZaPrace_pozA = 2100;
//        double wynagrodzenieZaPrace12Mies = 2100;
//        int iloscChorobowe = 0;
//
//        System.out.println("wynagrodzenieZaPrace_pozA : " + wynagrodzenieZaPrace_pozA);
//        System.out.println("wynagrodzenieZaPrace12Mies : " + wynagrodzenieZaPrace12Mies);
//        System.out.println("iloscChorobowe : " + iloscChorobowe);
//        System.out.println("");
//
//        double przecietneWynagrodzenieBezSkladek =  (wynagrodzenieZaPrace12Mies * (1 - 0.1371));
//        double stawkaDziennaChorobowe = DoubleUtils.round2Digits((przecietneWynagrodzenieBezSkladek *0.8) / 30);
//        double wynagrodzenieChorobowe = stawkaDziennaChorobowe * iloscChorobowe;
//
//        double stawkaDziennaPrzepracowane = DoubleUtils.round2Digits((wynagrodzenieZaPrace_pozA) / 30);
//        double wynagrodzeniePrzypadajaceNaNieobecnosci = stawkaDziennaPrzepracowane * iloscChorobowe;
//        double wynagrodzenieZaPrzepracowanaCzescMies = wynagrodzenieZaPrace_pozA - wynagrodzeniePrzypadajaceNaNieobecnosci;
//
//        System.out.println("stawkaDziennaChorobowe : " + stawkaDziennaChorobowe);
//        System.out.println("stawkaDziennaPrzepracowane : " + stawkaDziennaPrzepracowane);
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
