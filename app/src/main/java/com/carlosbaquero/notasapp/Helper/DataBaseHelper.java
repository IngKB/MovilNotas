package com.carlosbaquero.notasapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.carlosbaquero.notasapp.models.*;

import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notas.db";

    /*------TABLA MATERIAS--------*/
    public static final String TABLE_MATERIAS = "materias_table";
    public static final String MAT_COL_1 = "ID";
    public static final String MAT_COL_2 = "NOMBRE";


    /*------TABLA ITEMRUBRICA--------*/
    public static final String TABLE_ITEMRUBRICA = "itemRubrica_table";
    public static final String ITM_COL_1 = "ID";
    public static final String ITM_COL_2 = "RUBRICA_ID";
    public static final String ITM_COL_3 = "DESCRIPCION";
    public static final String ITM_COL_4 = "PORCENTAJE";

    /*------TABLA RUBRICA--------*/
    public static final String TABLE_RUBRICA = "rubrica_table";
    public static final String RUB_COL_1 = "ID";
    public static final String RUB_COL_2 = "MATERIA_ID";
    public static final String RUB_COL_3 = "CORTE";
    public static final String RUB_COL_4 = "PORCENTAJECORTE";

    /*------TABLA EVALMATERIA--------*/

    public static final String TABLE_EVALMATERIA = "evalMateria_table";
    public static final String EVM_COL_1 = "ID";
    public static final String EVM_COL_2 = "PRIMERCORTE_ID";
    public static final String EVM_COL_3 = "SEGUNDOCORTE_ID";
    public static final String EVM_COL_4 = "TERCERCORTE_ID";
    public static final String EVM_COL_5 = "NOTAFINAL";
    public static final String EVM_COL_6 = "MATERIA_ID";

    /*------TABLA EVALMATERIA--------*/

    public static final String TABLE_EVALCORTE = "evalCorte_table";
    public static final String EVC_COL_1 = "ID";
    public static final String EVC_COL_2 = "NOTACORTE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MATERIAS + " " +
                "("
                + MAT_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MAT_COL_2 + " TEXT"
                + ")"
        );
        db.execSQL("create table " + TABLE_ITEMRUBRICA + " " +
                "("
                + ITM_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITM_COL_2 + " INTEGER,"
                + ITM_COL_3 + " TEXT,"
                + ITM_COL_4 + " DOUBLE"
                + ")"
        );
        db.execSQL("create table " + TABLE_RUBRICA + " " +
                "("
                + RUB_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RUB_COL_2 + " INTEGER,"
                + RUB_COL_3 + " INTEGER,"
                + RUB_COL_4 + " DOUBLE"
                + ")"
        );
        db.execSQL("create table " + TABLE_EVALMATERIA + " " +
                "("
                + EVM_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EVM_COL_2 + " INTEGER,"
                + EVM_COL_3 + " INTEGER,"
                + EVM_COL_4 + " INTEGER,"
                + EVM_COL_5 + " DOUBLE,"
                + EVM_COL_6 + " INTEGER"
                + ")"
        );
        db.execSQL("create table " + TABLE_EVALCORTE + " " +
                "("
                + EVC_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EVC_COL_2 + " DOUBLE"
                + ")"
        );

    }

    public SQLiteDatabase getDataBase() {
        return this.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMRUBRICA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUBRICA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALMATERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALCORTE);

        onCreate(db);
    }

    /*-----------------------METODOS TABLA MATERIAS-----------------------*/

    public int AddMateria(Materia materia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MAT_COL_2, materia.getNombre());
        long result = db.insert(TABLE_MATERIAS, null, contentValues);

        if (result == -1) return -1;

        else {
            String selectQuery = "SELECT * FROM " + TABLE_MATERIAS;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null){
                c.moveToLast();
                return c.getInt(c.getColumnIndex(MAT_COL_1));
            }
        }
        return -1;
    }

    public Materia getMateria(long mat_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MATERIAS +
                " WHERE " + MAT_COL_1 + " = " + mat_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Materia materia = new Materia();
        materia.setId(c.getInt(c.getColumnIndex(MAT_COL_1)));
        materia.setNombre(c.getString(c.getColumnIndex(MAT_COL_2)));

        return materia;
    }

    public ArrayList<Materia> getAllMaterias() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Materia> materias = new ArrayList<Materia>();

        String selectQuery = "SELECT * FROM " + TABLE_MATERIAS;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Materia materia = new Materia();
                materia.setId(c.getInt(c.getColumnIndex(MAT_COL_1)));
                materia.setNombre(c.getString(c.getColumnIndex(MAT_COL_2)));

                // adding to todo list
                materias.add(materia);
            } while (c.moveToNext());
        }
        return materias;
    }

    public boolean UpdateMateria(Materia materia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MAT_COL_1, materia.getId());
        contentValues.put(MAT_COL_2, materia.getNombre());
        db.update(TABLE_MATERIAS, contentValues, MAT_COL_1 + " = ?", new String[]{String.valueOf(materia.getId())});
        return true;
    }

    public int DeleteMateria(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MATERIAS, MAT_COL_1 + " = ?", new String[]{id});
    }

    /*-----------------------METODOS TABLA RUBRICAS-----------------------*/


    public boolean AddRubrica(Rubrica rub, ArrayList<itemRubrica> items) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUB_COL_2, rub.getMateria_id());
        contentValues.put(RUB_COL_3, rub.getCorte());
        contentValues.put(RUB_COL_4, rub.getPorcentajeCorte());
        long result = db.insert(TABLE_RUBRICA, null, contentValues);
        if (result == -1)
            return false;
        else {
            String selectQuery = "SELECT * FROM " + TABLE_RUBRICA;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToLast()) {
                    int rub_id = c.getInt(c.getColumnIndex(RUB_COL_1));

                ContentValues contentVal;
                for (itemRubrica item:items) {
                    contentVal = new ContentValues();
                    contentVal.clear();
                    contentVal.put(ITM_COL_2,rub_id);
                    contentVal.put(ITM_COL_3,item.getDescripcion());
                    contentVal.put(ITM_COL_4,item.getPorcentaje());
                    db.insert(TABLE_ITEMRUBRICA, null, contentVal);
                }
            }
        }
        return true;
    }

    public Rubrica getRubrica(long rub_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RUBRICA +
                " WHERE " + RUB_COL_1 + " = " + rub_id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Rubrica rub = new Rubrica();
        rub.setId(c.getInt(c.getColumnIndex(RUB_COL_1)));
        rub.setMateria_id(c.getInt(c.getColumnIndex(RUB_COL_2)));
        rub.setCorte(c.getInt(c.getColumnIndex(RUB_COL_3)));
        rub.setPorcentajeCorte(c.getDouble(c.getColumnIndex(RUB_COL_4)));

        return rub;
    }

    public ArrayList<Rubrica> getRubricasMateria(int mat_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Rubrica> rubs = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_RUBRICA +
                " WHERE "+ RUB_COL_2 + " = "+mat_id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Rubrica rub = new Rubrica();
                rub.setId(c.getInt(c.getColumnIndex(RUB_COL_1)));
                rub.setMateria_id(c.getInt(c.getColumnIndex(RUB_COL_2)));
                rub.setCorte(c.getInt(c.getColumnIndex(RUB_COL_3)));
                rub.setPorcentajeCorte(c.getDouble(c.getColumnIndex(RUB_COL_4)));

                // adding to todo list
                rubs.add(rub);
            } while (c.moveToNext());
        }
        return rubs;
    }
    public ArrayList<Rubrica> getAllRubricas() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Rubrica> rubs = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_RUBRICA;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Rubrica rub = new Rubrica();
                rub.setId(c.getInt(c.getColumnIndex(RUB_COL_1)));
                rub.setMateria_id(c.getInt(c.getColumnIndex(RUB_COL_2)));
                rub.setCorte(c.getInt(c.getColumnIndex(RUB_COL_3)));
                rub.setPorcentajeCorte(c.getDouble(c.getColumnIndex(RUB_COL_4)));

                // adding to todo list
                rubs.add(rub);
            } while (c.moveToNext());
        }
        return rubs;
    }

    public boolean UpdateRubrica(Rubrica rub) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUB_COL_1, rub.getId());
        contentValues.put(RUB_COL_2, rub.getMateria_id());
        contentValues.put(RUB_COL_3, rub.getCorte());
        contentValues.put(RUB_COL_4, rub.getPorcentajeCorte());
        db.update(TABLE_RUBRICA, contentValues, RUB_COL_1 + " = ?", new String[]{String.valueOf(rub.getId())});
        return true;
    }

    public int DeleteRubrica(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RUBRICA, RUB_COL_1 + " = ?", new String[]{id});
    }

    /*-----------------------METODOS TABLA ITEMRBURICA-----------------------*/

    public boolean AdditemRubrica(itemRubrica item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITM_COL_2, item.getRubrica_id());
        contentValues.put(ITM_COL_3, item.getDescripcion());
        contentValues.put(ITM_COL_4, item.getPorcentaje());
        long result = db.insert(TABLE_ITEMRUBRICA, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public itemRubrica getitemRubrica(long item_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMRUBRICA +
                " WHERE " + ITM_COL_1 + " = " + item_id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        itemRubrica item = new itemRubrica();
        item.setId(c.getInt(c.getColumnIndex(ITM_COL_1)));
        item.setRubrica_id(c.getInt(c.getColumnIndex(ITM_COL_2)));
        item.setDescripcion(c.getString(c.getColumnIndex(ITM_COL_3)));
        item.setPorcentaje(c.getDouble(c.getColumnIndex(ITM_COL_4)));

        return item;
    }

    public ArrayList<itemRubrica> getItemsByRubricaId(int rub_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<itemRubrica> items = new ArrayList<itemRubrica>();

        String selectQuery = "SELECT * FROM " + TABLE_ITEMRUBRICA +
                " WHERE " + ITM_COL_2 + " = " + rub_id;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                itemRubrica item = new itemRubrica();
                item.setId(c.getInt(c.getColumnIndex(ITM_COL_1)));
                item.setRubrica_id(c.getInt(c.getColumnIndex(ITM_COL_2)));
                item.setDescripcion(c.getString(c.getColumnIndex(ITM_COL_3)));
                item.setPorcentaje(c.getDouble(c.getColumnIndex(ITM_COL_4)));

                // adding to todo list
                items.add(item);
            } while (c.moveToNext());
        }
        return items;
    }
    public ArrayList<itemRubrica> getAllitemRubricas() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<itemRubrica> items = new ArrayList<itemRubrica>();

        String selectQuery = "SELECT * FROM " + TABLE_ITEMRUBRICA;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                itemRubrica item = new itemRubrica();
                item.setId(c.getInt(c.getColumnIndex(ITM_COL_1)));
                item.setRubrica_id(c.getInt(c.getColumnIndex(ITM_COL_2)));
                item.setDescripcion(c.getString(c.getColumnIndex(ITM_COL_3)));
                item.setPorcentaje(c.getDouble(c.getColumnIndex(ITM_COL_4)));

                // adding to todo list
                items.add(item);
            } while (c.moveToNext());
        }
        return items;
    }

    public boolean UpdateitemRubrica(itemRubrica item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITM_COL_2, item.getRubrica_id());
        contentValues.put(ITM_COL_3, item.getDescripcion());
        contentValues.put(ITM_COL_4, item.getPorcentaje());
        db.update(TABLE_ITEMRUBRICA, contentValues, ITM_COL_1 + " = ?", new String[]{String.valueOf(item.getId())});
        return true;
    }

    public int DeleteitemRubrica(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMRUBRICA, ITM_COL_1 + " = ?", new String[]{id});
    }

    /*-----------------------METODOS TABLA EVALCORTE-----------------------*/

    public int AddEvaluacionCorte(EvaluacionCorte corte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVC_COL_2, corte.getNota());
        long result = db.insert(TABLE_EVALCORTE, null, contentValues);
        if (result == -1) {
            return -1;
        }
        else {
            String selectQuery = "SELECT * FROM " + TABLE_EVALCORTE;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null){
                c.moveToLast();
                return c.getInt(c.getColumnIndex(EVC_COL_1));
            }
        }
        return -1;
    }

    public EvaluacionCorte getEvaluacionCorte(long mat_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EVALCORTE +
                " WHERE " + EVC_COL_1 + " = " + mat_id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        EvaluacionCorte corte = new EvaluacionCorte();
        corte.setId(c.getInt(c.getColumnIndex(EVC_COL_1)));
        corte.setNota(c.getDouble(c.getColumnIndex(EVC_COL_2)));

        return corte;
    }

    public ArrayList<EvaluacionCorte> getAllEvaluacionCortes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EvaluacionCorte> cortes = new ArrayList<EvaluacionCorte>();

        String selectQuery = "SELECT * FROM " + TABLE_EVALCORTE;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                EvaluacionCorte corte = new EvaluacionCorte();
                corte.setId(c.getInt(c.getColumnIndex(EVC_COL_1)));
                corte.setNota(c.getDouble(c.getColumnIndex(EVC_COL_2)));

                // adding to todo list
                cortes.add(corte);
            } while (c.moveToNext());
        }
        return cortes;
    }

    public boolean UpdateEvaluacionCorte(EvaluacionCorte corte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVC_COL_2, corte.getNota());
        db.update(TABLE_EVALCORTE, contentValues, EVC_COL_1 + " = ?", new String[]{String.valueOf(corte.getId())});
        return true;
    }

    public int DeleteEvaluacionCorte(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EVALCORTE, EVC_COL_1 + " = ?", new String[]{id});
    }

    /*----------------------METODOS TABLA EVALMATERIA------------------------*/


    public boolean AddEvaluacionMateria(EvaluacionMateria evlMat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVM_COL_2, evlMat.getPrimerCorte_id());
        contentValues.put(EVM_COL_3, evlMat.getSegundoCorte_id());
        contentValues.put(EVM_COL_4, evlMat.getTercerCorte_id());
        contentValues.put(EVM_COL_5, evlMat.getNotaFinal());
        contentValues.put(EVM_COL_6, evlMat.getMateria_id());
        long result = db.insert(TABLE_EVALMATERIA, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public EvaluacionMateria getEvaluacionMateria(long eval_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EVALMATERIA +
                " WHERE " + EVM_COL_1 + " = " + eval_id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        EvaluacionMateria evlMat = new EvaluacionMateria();
        evlMat.setId(c.getInt(c.getColumnIndex(EVM_COL_1)));
        evlMat.setPrimerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_2)));
        evlMat.setSegundoCorte_id(c.getInt(c.getColumnIndex(EVM_COL_3)));
        evlMat.setTercerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_4)));
        evlMat.setNotaFinal(c.getDouble(c.getColumnIndex(EVM_COL_5)));
        evlMat.setMateria_id(c.getInt(c.getColumnIndex(EVM_COL_6)));

        return evlMat;
    }

    public EvaluacionMateria getEvaluacionMateriaById(int mat_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVALMATERIA +
                " WHERE " + EVM_COL_6 + " = " + mat_id;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToLast()) {

                EvaluacionMateria evlMat = new EvaluacionMateria();
                evlMat.setId(c.getInt(c.getColumnIndex(EVM_COL_1)));
                evlMat.setPrimerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_2)));
                evlMat.setSegundoCorte_id(c.getInt(c.getColumnIndex(EVM_COL_3)));
                evlMat.setTercerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_4)));
                evlMat.setNotaFinal(c.getDouble(c.getColumnIndex(EVM_COL_5)));
                evlMat.setMateria_id(c.getInt(c.getColumnIndex(EVM_COL_6)));
                // adding to todo list

            return evlMat;
        }
       return null;
    }
    public ArrayList<EvaluacionMateria> getAllEvaluaciónMaterias() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EvaluacionMateria> evlMats = new ArrayList<EvaluacionMateria>();

        String selectQuery = "SELECT * FROM " + TABLE_EVALMATERIA;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                EvaluacionMateria evlMat = new EvaluacionMateria();
                evlMat.setId(c.getInt(c.getColumnIndex(EVM_COL_1)));
                evlMat.setPrimerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_2)));
                evlMat.setSegundoCorte_id(c.getInt(c.getColumnIndex(EVM_COL_3)));
                evlMat.setTercerCorte_id(c.getInt(c.getColumnIndex(EVM_COL_4)));
                evlMat.setNotaFinal(c.getDouble(c.getColumnIndex(EVM_COL_5)));
                evlMat.setMateria_id(c.getInt(c.getColumnIndex(EVM_COL_6)));
                // adding to todo list
                evlMats.add(evlMat);
            } while (c.moveToNext());
        }
        return evlMats;
    }

    public boolean UpdateEvaluacionMateria(EvaluacionMateria evlMat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVM_COL_2, evlMat.getPrimerCorte_id());
        contentValues.put(EVM_COL_3, evlMat.getSegundoCorte_id());
        contentValues.put(EVM_COL_4, evlMat.getTercerCorte_id());
        contentValues.put(EVM_COL_5, evlMat.getNotaFinal());
        contentValues.put(EVM_COL_6, evlMat.getMateria_id());
        db.update(TABLE_EVALMATERIA, contentValues, EVM_COL_1 + " = ?", new String[]{String.valueOf(evlMat.getId())});
        return true;
    }

    public int DeleteEvaluacionMateria(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EVALMATERIA, EVM_COL_1 + " = ?", new String[]{id});
    }

}