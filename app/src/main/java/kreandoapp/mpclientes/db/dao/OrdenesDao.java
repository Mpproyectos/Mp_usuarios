package kreandoapp.mpclientes.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kreandoapp.mpclientes.db.entity.Ordenes;

@Dao
public interface OrdenesDao {
    @Insert
    void insertarOrdenes (Ordenes ordenes);

    @Query("SELECT * FROM ordenes")
    List<Ordenes> dametodaslasOrdenes();

    @Query("SELECT * FROM ordenes WHERE nombre LIKE :nombre")
    Ordenes dametodoOrdenesxNombre (String nombre);

    @Query("SELECT * FROM ordenes WHERE id LIKE :id")
    Ordenes dametodoOrdenxIdnumero (int id);


    @Query("SELECT COUNT(*)FROM ordenes")
    int dameTodoCount ();

    @Query("DELETE FROM ordenes")
    void borrarTodasLasOrdenes();

    @Query("DELETE FROM ordenes WHERE id = :id")
    void borrarxidInt(int id);

    @Query("DELETE FROM ordenes WHERE idproducto = :idstring")
    void borrarxidString(String idstring);

    @Delete
    void borrarxid (Ordenes ordenes);

    @Update
    void EditarTodoxid (Ordenes ordenes);



}
