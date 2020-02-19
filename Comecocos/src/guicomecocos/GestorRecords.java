package guicomecocos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar los records
 * 
 */
public final class GestorRecords implements Serializable {
    private int max_record = 10; // Maximo de records por mapa
    private transient ComecocosFrame frame;

    // Se ordenan: 0 - Mayor 9 - Menor
    private ArrayList<ArrayList<Record>> records;

    public GestorRecords(ComecocosFrame cf) {
        frame = cf;
        records = new ArrayList<ArrayList<Record>>();
        // Cargamos los records por defecto
        this.generar();
    }

    public void generar() {
        records = new ArrayList<ArrayList<Record>>();
        for (int i = 0; i < frame.cgui.mapas.size(); i++) {
            ArrayList<Record> records_mapa = new ArrayList<Record>();
            for (int j = 0; j < max_record; j++) {
                Record r = new Record();
                r.nivel = j;
                r.nombre = "Anonimo";
                r.puntos = j * 10;
                records_mapa.add(0, r);
            }
            records.add(records_mapa);
        }
    }

    public ArrayList<Record> getRecord(int mapa) {
        return this.records.get(mapa);
    }

    // Indica si es record y en que posicion estaría. -1 en caso de que no haya record
    public int esRecord(int mapa, int puntos) {
        ArrayList<Record> records_mapa = this.records.get(mapa);
        for (int i = records_mapa.size() - 1; i >= 0; i--) {
            Record r = records_mapa.get(i);
            if (puntos > r.puntos) {
                return i;
            }
        }
        return -1;
    }

    // Añade un record
    public void anadirRecord(int mapa, Record r) {
        ArrayList<Record> records_mapa = this.records.get(mapa);
        boolean fin = false;
        for (int i = 0; !fin && i < records_mapa.size(); i++) {
            Record rec = records_mapa.get(i);
            if (r.puntos > rec.puntos) {
                records_mapa.add(i, r);
                fin = true;
            }
        }
        // Borramos el ultimo record
        records_mapa.remove(this.max_record);
    }
}
