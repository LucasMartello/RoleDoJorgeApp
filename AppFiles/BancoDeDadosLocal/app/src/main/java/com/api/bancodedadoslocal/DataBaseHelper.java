package com.api.bancodedadoslocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String LISTAS_TABLE = "LISTAS_TABLE";
    public static final String COLUMN_LIS_CDILISTA = "LIS_CDILISTA";
    public static final String COLUMN_LIS_DSSLISTA = "LIS_DSSLISTA";
    public static final String COLUMN_LIS_DTSDATA = "LIS_DTSDATA";

    public static final String PRODUTOS_TABLE = "PRODUTOS_TABLE";
    public static final String COLUMN_PRD_CDIPRODUTO = "PRD_CDIPRODUTO";
    public static final String COLUMN_PRD_DSSPRODUTO = "PRD_DSSPRODUTO";
    public static final String COLUMN_PRD_VLRVALOR   = "PRD_VLRVALOR";

    public static final String ITEMLISTA_TABLE = "ITEMLISTA_TABLE";
    public static final String COLUMN_ITL_CDILISTA = "ITL_CDILISTA";
    public static final String COLUMN_ITL_CDIPRODUTO = "ITL_CDIPRODUTO";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "lista.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableListas = "CREATE TABLE " + LISTAS_TABLE + " (" + COLUMN_LIS_CDILISTA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                         + COLUMN_LIS_DSSLISTA + " String, "
                                                                         + COLUMN_LIS_DTSDATA  + " String )";

        String createTableProduto = "CREATE TABLE " + PRODUTOS_TABLE + " (" + COLUMN_PRD_CDIPRODUTO + " INTEGER, "
                                                                           + COLUMN_PRD_DSSPRODUTO + " String, "
                                                                           + COLUMN_PRD_VLRVALOR   + " Real )";

        String createTableItemLista = "CREATE TABLE " + ITEMLISTA_TABLE + " (" + COLUMN_ITL_CDILISTA + " INTEGER, "
                                                                              + COLUMN_ITL_CDIPRODUTO + " INTEGER )";

        db.execSQL(createTableListas);
        db.execSQL(createTableProduto);
        db.execSQL(createTableItemLista);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addProduto(Produto produto){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PRD_CDIPRODUTO,produto.getId());
        cv.put(COLUMN_PRD_DSSPRODUTO,produto.getNome());
        cv.put(COLUMN_PRD_VLRVALOR,produto.getValor());

        long insert = db.insertOrThrow(PRODUTOS_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean addOne(Lista lista){

        int idLista = getNextIdListas();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LIS_CDILISTA,idLista);
        cv.put(COLUMN_LIS_DSSLISTA,lista.getNomeLista());
        cv.put(COLUMN_LIS_DTSDATA,lista.getDataLista());
        long insert = db.insert(LISTAS_TABLE, null, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(COLUMN_ITL_CDILISTA,Integer.toString(idLista));
        db.update(ITEMLISTA_TABLE,cv2,COLUMN_ITL_CDILISTA + " = -1 ",null );

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<Produto> getAllProduto(int lista){

        List<Produto> returnList = new ArrayList<>();

        String queryString = "Select * from " + PRODUTOS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do{
                int idProduto = cursor.getInt(index(COLUMN_PRD_CDIPRODUTO,cursor));
                String nomeProduto = cursor.getString(index(COLUMN_PRD_DSSPRODUTO,cursor));
                double valorProduto = cursor.getDouble(index(COLUMN_PRD_VLRVALOR,cursor));
                boolean selected = findRelation(lista,idProduto);

                Produto newProduto = new Produto(idProduto,nomeProduto,valorProduto,selected);
                returnList.add(newProduto);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }


    public List<Lista> getAllListas(){

        List<Lista> returnList = new ArrayList<>();
        String queryString = "Select * from " + LISTAS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do{
                int idLista = cursor.getInt(index(COLUMN_LIS_CDILISTA,cursor));
                String nomeLista = cursor.getString(index(COLUMN_LIS_DSSLISTA,cursor));
                String dataLista = cursor.getString(index(COLUMN_LIS_DTSDATA,cursor));

                Lista newlista = new Lista(idLista,nomeLista,dataLista);
                returnList.add(newlista);

            }while (cursor.moveToNext());

        }else{

        }
        cursor.close();
        db.close();

        return returnList;
    }

    private int index(String column, Cursor cursor){

        int indexNumber;
        indexNumber = cursor.getColumnIndex(column);
        if (indexNumber == -1){
            throw new RuntimeException();
        }else {
            return indexNumber;
        }
    }

    public int getNextIdListas(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX("+COLUMN_LIS_CDILISTA+")+1 FROM LISTAS_TABLE",null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }else{
            cursor.close();
            db.close();
            return 1;
        }

    }

    public int getCountProdutos(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + PRODUTOS_TABLE,null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }else{
            cursor.close();
            db.close();
            return 0;
        }
    }

    public void clearDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +LISTAS_TABLE);
        db.execSQL("DELETE FROM " +PRODUTOS_TABLE);
        db.execSQL("DELETE FROM " +ITEMLISTA_TABLE);
        db.close();
    }

    protected Lista getListaDB(int id){

        String queryString = "Select * from " + LISTAS_TABLE + " where " + COLUMN_LIS_CDILISTA + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()) {

            return new Lista(cursor.getInt(index(COLUMN_LIS_CDILISTA,cursor)),
                             cursor.getString(index(COLUMN_LIS_DSSLISTA,cursor)),
                             cursor.getString(index(COLUMN_LIS_DTSDATA,cursor)));

        }
        return null;

    }

    protected void updateListaTable(int ID, String Nome, String Data){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LIS_DSSLISTA,Nome);
        cv.put(COLUMN_LIS_DTSDATA,Data);
        db.update(LISTAS_TABLE,cv,COLUMN_LIS_CDILISTA + " = ?", new String[]{Integer.toString(ID)});

    }

    public void deletarLista(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEMLISTA_TABLE, COLUMN_ITL_CDILISTA  + " = ?", new String[]{Integer.toString(id)});
        db.delete(LISTAS_TABLE,COLUMN_LIS_CDILISTA + " = ?", new String[]{Integer.toString(id)});
    }

    public boolean findRelation(int lista,int produto){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMLISTA_TABLE + " WHERE " + COLUMN_ITL_CDILISTA + " = " + lista
                                                                                    + " AND " + COLUMN_ITL_CDIPRODUTO + " = " + produto,null);


        if (cursor.getCount() == 0) {
            return false;
        }else{
            return true;
        }
    }

    public void addRelation(int lista,int produto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITL_CDILISTA,lista);
        cv.put(COLUMN_ITL_CDIPRODUTO,produto);
        db.insertOrThrow(ITEMLISTA_TABLE, null, cv);
    }

    public void deleteRelation(int lista,int produto){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEMLISTA_TABLE, COLUMN_ITL_CDILISTA + " = ? and " + COLUMN_ITL_CDIPRODUTO + " = ? ", new String[]{Integer.toString(lista),Integer.toString(produto)});
    }

}
