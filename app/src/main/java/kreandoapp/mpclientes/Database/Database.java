package kreandoapp.mpclientes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import kreandoapp.mpclientes.clientes.ProcesoVenta.Carrito;
import kreandoapp.mpclientes.pojo.Order;


public class Database extends SQLiteAssetHelper {

    Carrito cart;

    public Database(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version, Carrito cart) {
        super(context, name, storageDirectory, factory, version);
        this.cart = cart;
    }


    private static final String DB_NAME = "matiasdb.db";
    private static final int DB_VER=1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    public List<Order>getCarts(){
       SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect ={"ID","ProductName","ProductId","Quantity","Price","Discount","Image"};

        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new Order(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("Image"))
                        ));
            }while (c.moveToNext());
        }
        return result;
    }

    public  void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (ProductId,ProductName,Quantity,Price,Discount,Image) VALUES ('%s','%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getImage()

        );
        db.execSQL(query);
    }
    public  void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public int getCountCart() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return  count;
    }


    public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ID = %d",order.getQuantity(),order.getID());
        db.execSQL(query);

    }
    public  void borrarxidCart(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE ID = '%s'",id);
        db.execSQL(query);
    }
    public  void borrarxidCartStringid(String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE ProductId = '%s'",id);
        db.execSQL(query);
    }
    public int versionDB(){
        SQLiteDatabase db = getReadableDatabase();

        int dbv = db.getVersion();
        return dbv;
    }
    public void Count(){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT FROM OrderDetail");

        db.execSQL(query);
    }
}
