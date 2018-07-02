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

    static long round2Int(double v) {
        return Math.round(v + 0.001);
    }

//    public static void main(String[] args) {
////		http://www.wskazniki.gofin.pl/8,223,2,przykladowe-obliczenie-wynagrodzenia-netto.html
//
//        double wynagrodzenieZaPrace_pozA = 300;
//        double kosztyUzyskania_pozG = 111.25;
//
//        double emerytalna = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 9.76 / 100);
//        double rentowa = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 1.5 / 100);
//        double chorobowa = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 2.45 / 100);
//        double skladkiNaSpoleczne_pozC = emerytalna + rentowa + chorobowa;
//
//        double podstawaSkladkiDlaZdrowotne_pozD = wynagrodzenieZaPrace_pozA - skladkiNaSpoleczne_pozC;
//        double skladkaZdrowotnaDoPobrania_pozE = DoubleUtils.round2Digits(podstawaSkladkiDlaZdrowotne_pozD * 9 / 100);
//        double skladkaZdrowotnaDoOdliczenia_pozF = DoubleUtils.round2Digits(podstawaSkladkiDlaZdrowotne_pozD * 7.75 / 100);
//        long podstawaDlaZaliczkiNaDoch_pozH = round2Int(wynagrodzenieZaPrace_pozA - kosztyUzyskania_pozG - skladkiNaSpoleczne_pozC);
//        double zaliczkNaDochPrzedOdliczeniemZdrow_pozI = Math.max((podstawaDlaZaliczkiNaDoch_pozH * 0.18) - 46.33, 0);
//        long zaliczkaNaDochDoPobrania_pozJ = Math.max(round2Int(zaliczkNaDochPrzedOdliczeniemZdrow_pozI - skladkaZdrowotnaDoOdliczenia_pozF), 0);
//        double doWyplaty_pozK = (wynagrodzenieZaPrace_pozA - skladkiNaSpoleczne_pozC - skladkaZdrowotnaDoPobrania_pozE - zaliczkaNaDochDoPobrania_pozJ);
//
//        double pracodawcaEmerytalne = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 9.76 / 100);
//        double pracodawcaRentowe = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 6.5 / 100);
//        double pracodawcaWypadkowe = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 1.8 / 100);
//        double pracodawcaFp = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 2.45 / 100);
//        double pracodawcaFgsp = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA * 0.1 / 100);
//        double skladkiPracodawcy_pozL = DoubleUtils.round2Digits(pracodawcaEmerytalne + pracodawcaRentowe + pracodawcaWypadkowe + pracodawcaFp + pracodawcaFgsp);
//        double kosztPracodawcy = DoubleUtils.round2Digits(wynagrodzenieZaPrace_pozA + skladkiPracodawcy_pozL);
//
//        System.out.println("spoleczne : " + skladkiNaSpoleczne_pozC);
//        System.out.println("podstawaSkladkiDlaZdrowotne_pozD : " + podstawaSkladkiDlaZdrowotne_pozD);
//        System.out.println("skladkaZdrowotnaDoPobrania_pozE : " + skladkaZdrowotnaDoPobrania_pozE);
//        System.out.println("skladkaZdrowotnaDoOdliczenia_pozF : " + skladkaZdrowotnaDoOdliczenia_pozF);
//        System.out.println("kosztyUzyskania : " + kosztyUzyskania_pozG);
//        System.out.println("podstawaDlaZaliczkiNaDoch_pozH : " + podstawaDlaZaliczkiNaDoch_pozH);
//        System.out.println("zaliczkNaDochPrzedOdliczeniemZdrow_pozI : " + zaliczkNaDochPrzedOdliczeniemZdrow_pozI);
//        System.out.println("zaliczkaNaDochDoPobrania_pozJ : " + zaliczkaNaDochDoPobrania_pozJ);
//        System.out.println("doWyplaty_pozK : " + doWyplaty_pozK);
//        System.out.println("pracodawcaEmerytalne : " + pracodawcaEmerytalne);
//        System.out.println("pracodawcaRentowe : " + pracodawcaRentowe);
//        System.out.println("pracodawcaWypadkowe : " + pracodawcaWypadkowe);
//        System.out.println("pracodawcaFp : " + pracodawcaFp);
//        System.out.println("pracodawcaFgsp : " + pracodawcaFgsp);
//        System.out.println("skladkiPracodawcy_pozL : " + skladkiPracodawcy_pozL);
//        System.out.println("kosztPracodawcy : " + kosztPracodawcy);
//
////		System.out.println("podstawaDlaZaliczkiDoch: " + podstawaDlaZaliczkiNaDoch);
////		System.out.println("zaliczkaNaDoch: " + zaliczkaNaDoch);
////		System.out.println("doWyplaty:" + doWyplaty);
////		System.out.println("zdrowotna:" + skladkaZdrowotna);
//    }
//
//}

