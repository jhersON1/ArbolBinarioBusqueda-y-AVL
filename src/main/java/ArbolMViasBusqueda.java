
import excepciones.ExcepcionOrdenInvalido;
import java.time.chrono.ThaiBuddhistEra;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import jdk.jfr.consumer.RecordedFrame;

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
public class ArbolMViasBusqueda<K extends Comparable<K>,V> 
    implements IArbolBusqueda<K, V> {
    
    protected NodoMVias<K,V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;
    
    public ArbolMViasBusqueda(){
        this.orden = 3;
    }
    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido{
        if(orden < 3){
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();   
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {
        
        if (this.esArbolVacio()){
            return 0;
        }
        int cantidad = 0;
        // offer inserta datos a la cola 
        // poll saca datos de la cola
        Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            NodoMVias<K,V> nodoActual = colaDeNodos.poll();
            cantidad++;
            for (int i=0; i < nodoActual.cantidadDeClavesNoVacias();i++){
               
                if (!nodoActual.esHijoVacio(i)){
                colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            
            
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }
        return cantidad;
    }

    @Override
    public int altura() {
         return altura(this.raiz);
    }
    protected int altura(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < orden; i++){
            int alturaDeHijo = altura(nodoActual.getHijo(i));
            if(alturaDeHijo>alturaMayor){
                alturaMayor = alturaDeHijo;
            }    
        }

        return alturaMayor+1; 
    }

    
    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K minimo() {
         if (esArbolVacio()){
            return null;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        NodoMVias<K,V> nodoAnterior = (NodoMVias<K,V>) NodoMVias.nodoVacio();
        while (!NodoMVias.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(0);
            
        }
        return nodoAnterior.getClave(0);
    }

    @Override
    public K maximo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (this.esArbolVacio()){
            this.raiz = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
            return;
        }
        
        NodoMVias<K,V> nodoActual = this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            int posicionClaveExistente = this.existeClaveEnNodo(nodoActual, claveAInsertar);
            if(posicionClaveExistente != POSICION_INVALIDA){
                nodoActual.setValor(posicionClaveExistente, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            }
            if (nodoActual.esHoja()){
                if(nodoActual.estanClaveLlenas()){
                    int posicionPorDondeBajar = this.porDondeBajar(nodoActual,claveAInsertar);
                    NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                }else {
                    this.insertarDatoOrdenadoEnNodo(nodoActual,claveAInsertar,valorAInsertar);
                }
                nodoActual = NodoMVias.nodoVacio();
            }else {
                int posicionPorDondeBajar = this.porDondeBajar(nodoActual,claveAInsertar);
                if (nodoActual.esHijoVacio(posicionPorDondeBajar)){
                    NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    nodoActual = NodoMVias.nodoVacio();
                }else{
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar); 
                }
            }
        }
    }   
    protected int existeClaveEnNodo(NodoMVias<K,V> nodoActual, K claveABuscar){
        for (int i=0; i<nodoActual.cantidadDeClavesNoVacias();i++){
            K claveActual = nodoActual.getClave(i);
            if (claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        
        return POSICION_INVALIDA;
    }
    private void insertarDatoOrdenadoEnNodo(NodoMVias<K,V> nodoAuxiliar,K claveAInsertar, V valorAInsertar ) {
        int i = orden - 1;
        while(i>0){
            if (!nodoAuxiliar.esClaveVacia(i-1)) {
                if (claveAInsertar.compareTo(nodoAuxiliar.getClave(i-1))>0) {
                    nodoAuxiliar.setClave(i, claveAInsertar);
                    return;
                }else{
                    nodoAuxiliar.setClave(i, nodoAuxiliar.getClave(i-1));
                }                
            }
            i--;
        }
        nodoAuxiliar.setClave(0, claveAInsertar);
    }
    private int porDondeBajar(NodoMVias<K,V> nodoAuxiliar,K claveAInsertar){
        return 0;
    }

    @Override
    public V eliminar(K clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K,V> nodoActual  = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)){
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual; i++){
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0){
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0){
                    huboCambioDeNodoActual = true;
                    nodoActual = nodoActual.getHijo(i);
                }
            }
            
            if(!huboCambioDeNodoActual){
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }
        return (V)NodoMVias.datoVacio();
    }
// RECORRIDO EN INORDEN ITERATIVO Y RECURSIVO
    @Override
    public List<K> recorridoEnInOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<K> recorridoEnInOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrdenRec(this.raiz,recorrido);
        return recorrido ;
        
    }
    private void recorridoEnInOrdenRec(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias();i++){
            recorrido.add(nodoActual.getClave(i));
            recorridoEnInOrdenRec(nodoActual.getHijo(i),recorrido);
        }
        
        recorridoEnPreOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }
    // RECORRIDO EN PRE ORDEN ITERATIVO Y RECURSIVO
    @Override
    public List<K> recorridoEnPreOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<K> recorridoEnPreOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrdenRec(this.raiz,recorrido);
        return recorrido ;
        
    }
    private void recorridoEnPreOrdenRec(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnPreOrdenRec(nodoActual.getHijo(i),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        
        recorridoEnPreOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()),recorrido);
    }

    // RECORRIDO EN POST ORDEN ITERATIVO Y RECURSIVO
    @Override
    public List<K> recorridoEnPostOrden() {
        return null;
    }
    public List<K> recorridoEnPostOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrdenRec(this.raiz,recorrido);
        return recorrido ;
        
    }
    private void recorridoEnPostOrdenRec(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        recorridoEnPostOrdenRec(nodoActual.getHijo(0),recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias();i++){
            recorridoEnPostOrdenRec(nodoActual.getHijo(i+1),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }
// RECORRIDO POR NIVELES ITERATIVO
    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        // offer inserta datos a la cola 
        // poll saca datos de la cola
        Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            NodoMVias<K,V> nodoActual = colaDeNodos.poll();
            for (int i=0; i < nodoActual.cantidadDeClavesNoVacias();i++){
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)){
                colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            
            
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }
        return recorrido;
    }
   
    
    public int cantidadDeDatosVacios(){
        return cantidadDeDatosVacios(this.raiz);
    }
    private int cantidadDeDatosVacios(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidad =0;
        for (int i=0;i< orden-1 ; i++){
            cantidad += cantidadDeDatosVacios(nodoActual.getHijo(i));
            if(nodoActual.esClaveVacia(i)){
                cantidad++;
            }
        }
        cantidad += cantidadDeDatosVacios(nodoActual.getHijo(orden-1));
        return cantidad;
    }
    
    
    public int cantidadDeHojas(){
        return cantidadDeHojas(this.raiz);
    }
    private int cantidadDeHojas(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        if (nodoActual.esHoja()){
            return 1;
        }
        int cantidad = 0;
        for (int i=0; i<orden;i++){
            cantidad+=cantidadDeHojas(nodoActual.getHijo(i));
        }
        return cantidad;
    }
    
    //---------------------------------------
     public int cantidadDeHojasDesdeNivel(int nivelBase){
        return cantidadDeHojasDesdeNivel(this.raiz,nivelBase,0);
    }
    private int cantidadDeHojasDesdeNivel(NodoMVias<K,V> nodoActual,int nivelBase,int nivelActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        if (nivelActual >= nivelBase){
            if (nodoActual.esHoja()){
                return 1;
            }  
        }else{
            if (nodoActual.esHoja()){
                return 0;
            }  
        }

        int cantidad = 0;
        for (int i=0; i<orden;i++){
            cantidad+=cantidadDeHojasDesdeNivel(nodoActual.getHijo(i),nivelBase,nivelActual+1);
        }
        return cantidad;
    }
}
