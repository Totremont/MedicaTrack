package com.example.medicatrack.repo.persist.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

/*  Después de leer 500 páginas y vender mi alma en la Deepweb, llegué a la conclusión de que ésta es la mejor forma (fácil y rápida) de representar tiempo y fecha entre Java y SQL.

    Sobre Timestamp y ZonedDateTime:
    Timestamp representa el total de segundos transcurridos desde 01/01/1970 en UTC sin uso horario (sin desviación + - horas). Instant representa el mismo concepto.
    Sin embargo, cada pais o zona puede tener un uso horario particular que afecta la fecha y hora que este total representa.
    Por ejemplo, el valor 1675178100 equivale al 31/01/23 10:15 AM para America/Nueva York (Offset de -05 UTC), pero para Argentina/Buenos Aires es 12:15:00 PM (-03 UTC)

    --Se puede consultar en: https://www.epochconverter.com/timezones--

    ZonedDateTime es equivalente a LocalDateTime (tiene todas sus lindas funciones), pero con un uso horario que se puede definir, lo cual nos permite saber qué hora representa para Argentina un Instant sin ambiguedad.
    Entonces, como SQL no entiende ZonedDateTime ni Instant, hacemos las siguientes conversiones sencillas:

    Si deseo convertir de ZonedDateTime a columna SQL: Timestamp.fromInstant(ZonedDateTime.toInstant);
    Si deseo convertir de Timestamp a mi querida ZonedDateTime: ZonedDateTime.ofInstant(timestamp.toInstant , zoneId); zoneId = America/Argentina/Buenos_Aires

    Y listo.

    Por último, ¿qué diferencia hay entre zone y offset? En principio es casi lo mismo, pero en realidad la desviación la representa la zona y no al revés.
    Esto es porque a lo largo del año, y entre estaciones, el offset de una zona puede cambiar (de -04 a -05 por ejemplo). Por eso el método pide zone y no offset.

 */

@Entity
public class RegistroEntity
{
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name="medicamento_id") private int medicaId;
    private java.sql.Timestamp fecha;
    private String estado;

    public void setId(int id){this.id = id;}

    public int getId() {
        return id;
    }

    public int getMedicaId() {
        return medicaId;
    }

    public void setMedicaId(int medicaId) {
        this.medicaId = medicaId;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
