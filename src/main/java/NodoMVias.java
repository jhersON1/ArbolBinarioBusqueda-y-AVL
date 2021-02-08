
import java.util.LinkedList;
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
public class NodoMVias<K,V> {
    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K,V>> listaDeHijos;
    
    public NodoMVias(int orden){
        listaDeHijos = new LinkedList<> ();
        listaDeClaves = new LinkedList<> ();
        listaDeValores = new LinkedList<> ();
        for (int i = 0; i < orden - 1; i++){
            listaDeHijos.add(NodoMVias.nodoVacio());
            listaDeClaves.add((K)NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
        }
        listaDeHijos.add(NodoMVias.nodoVacio());
    }
    
    //--------------------------
    
    public NodoMVias(int orden, K primerClave, V primerValor){
        this(orden);
        this.listaDeClaves.set(0, primerClave);
        this.listaDeValores.set(0, primerValor);
    }
    
    public static NodoMVias nodoVacio(){
        return null;
    }
    public static Object datoVacio(){
        return null;
    }
    /**
     * Retorna la clave de la poscicion indicada por el parametro poscion.Pre-Condicion: El parametro posicion indica una posicion valida en el 
 arreglo de la lista de claves
     * @param posicion
     * @return 
     */
    public K getClave(int posicion){
        return this.listaDeClaves.get(posicion);
    }
    public void setClave(int posicion, K clave){
        this.listaDeClaves.set(posicion, clave);
    }
    public V getValor(int posicion){
        return this.listaDeValores.get(posicion);
    }
    public void setValor(int posicion, V valor){
        this.listaDeValores.set(posicion, valor);
    }
    public NodoMVias<K,V> getHijo(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    public void setHijo(int posicion,NodoMVias<K,V> nodo){
        this.listaDeHijos.set(posicion, nodo);
    }
    public static boolean esNodoVacio(NodoMVias nodo){
        return nodo==NodoMVias.nodoVacio();
    }
    public boolean esClaveVacia(int posicion){
        return this.listaDeClaves.get(posicion)==NodoMVias.datoVacio();
    }
    public boolean esHijoVacio(int posicion){
        return this.listaDeHijos.get(posicion)==NodoMVias.nodoVacio();
    }
    public boolean esHoja(){
        for (int i = 0;i < this.listaDeHijos.size(); i++){
            if (!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    public boolean estanClaveLlenas(){
        for (int i = 0;i < this.listaDeClaves.size(); i++){
            if (!this.esClaveVacia(i)){
                return false;
            }
        }
        return true;
    }
    public int cantidadDeClavesNoVacias(){
        int cantidad = 0;
        for (int i = 0;i < this.listaDeClaves.size(); i++){
            if (!this.esClaveVacia(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    public int cantidadDeHijosVacios(){
        int cantidad = 0;
        for (int i = 0;i < this.listaDeHijos.size(); i++){
            if (this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    public int cantidadDeHijosNoVacios(){
        int cantidad = 0;
        for (int i = 0;i < this.listaDeHijos.size(); i++){
            if (!this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
}

