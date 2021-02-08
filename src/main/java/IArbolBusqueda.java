
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author neON
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda <K extends Comparable<K>,V>{
    
    void vaciar(); 
    boolean esArbolVacio();
    int size();
    int altura();
    int nivel();
    K minimo();
    K maximo();
    void insertar(K clave,V valor);
    V eliminar(K clave);
    boolean contiene(K clave);
    V buscar(K clave);
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnPostOrden();
    List<K> recorridoPorNiveles();
    


}
