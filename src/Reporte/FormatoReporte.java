package Reporte;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FormatoReporte implements JRDataSource {

    private Object[][] data;
    private int index = -1;

    public FormatoReporte(Object[][] data) {
        this.data = data;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < data.length;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        int columnIndex = getColumnIndex(jrField.getName());
        return data[index][columnIndex];
    }

    private int getColumnIndex(String columnName) {
        // Buscar el índice correspondiente al nombre de la columna en el arreglo data
        for (int i = 0; i < data[0].length; i++) {
            if (columnName.equals(getNombreColumna(i))) {
                return i;
            }
        }
        return -1; // Si no se encuentra la columna, retorna -1 o lanza una excepción, según lo que prefieras
    }

    private String getNombreColumna(int columnIndex) {
        // Aquí puedes definir los nombres de las columnas que se corresponden con el arreglo data
        // Puedes crear un arreglo de nombres de columna o utilizar una lista, base de datos, etc.
        String[] nombresColumnas = {"id_reservacion", "id_casa", "IDCliente", "fecha_inicio", "fecha_fin"};
        return nombresColumnas[columnIndex];
    }

}
