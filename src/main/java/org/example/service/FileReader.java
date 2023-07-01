package org.example.service;

import com.poiji.bind.Poiji;
import org.example.model.Objeto;

import java.io.File;
import java.util.List;

import static org.example.util.Values.DIR;


public class FileReader {

    public static List<Objeto> getObjetos(){
        File file = new File(DIR);
        List<Objeto> objetos = Poiji.fromExcel(file, Objeto.class);

        return objetos;
    }

}
