import com.rapido.persistence.dao.DBManager;
import com.rapido.persistence.dao.SQLScripts;
import com.rapido.persistence.model.*;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBManager manager = new DBManager();
        List<Ciclo> test4 = manager.consultCicloTable(SQLScripts.getTable("Ciclo"));
        for(Ciclo ciclo : test4){
            System.out.println(ciclo.toString());
        }
        List<Materia> test1 = manager.consultMateriaTable(SQLScripts.getTable("Materia"));
        for(Materia materia : test1){
            System.out.println(materia.toString());
        }
        List<Profesor> test = manager.consultProfesorTable(SQLScripts.getTable("Profesores"));
        for(Profesor profesor : test){
            System.out.println(profesor.toString());
        }
        List<Seccion> test2 = manager.consultSeccionTable(SQLScripts.getTable("Seccion"));
        for(Seccion seccion : test2){
            System.out.println(seccion.toString());
        }
        List<Horario> test3 = manager.consultHorarioTable(SQLScripts.getTable("Horario"));
        for(Horario horario : test3){
            System.out.println(horario.toString());
        }
    }
}
