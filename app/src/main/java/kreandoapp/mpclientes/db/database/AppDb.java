package kreandoapp.mpclientes.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kreandoapp.mpclientes.Constantes.Cons;
import kreandoapp.mpclientes.db.dao.OrdenesDao;
import kreandoapp.mpclientes.db.entity.Ordenes;

@Database(entities = {Ordenes.class},version = 1)
public abstract class AppDb extends RoomDatabase {
    private static AppDb INSTANCE;

    public  abstract OrdenesDao ordenesDao();

    public static AppDb getAppDb (Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDb.class, Cons.NAME_DATABASE)
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static  void destroyInstance(){
        INSTANCE = null;
    }

}
