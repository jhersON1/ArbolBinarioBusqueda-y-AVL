
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
public class ArbolBinarioBusqueda<K extends Comparable<K>,V> 
    implements IArbolBusqueda<K, V>{
    
    protected NodoBinario<K,V> raiz;
    
    
    // RECONSTRUIR ARBOL BINARIO DE BUSQUEDA 
    public ArbolBinarioBusqueda() {

    }

    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
                                List<K> clavesNoInOrden, List<V> valoresNoInOrden,
                                boolean usandoPreOrden){
        if(clavesInOrden.isEmpty()||
           clavesNoInOrden.isEmpty()||
           valoresInOrden.isEmpty()||
           valoresNoInOrden.isEmpty()){
            throw new IllegalArgumentException("Los parametros no pueden ser vacios");
        }
        if(clavesInOrden.size()!=clavesNoInOrden.size() ||
           clavesInOrden.size()!=valoresInOrden.size()  ||
           clavesInOrden.size()!=valoresNoInOrden.size()){
            throw new IllegalArgumentException("Los parametros no pueden listar con diferente tamaño");
        }
        if(usandoPreOrden){
           this.raiz= reconstruirConPreOrden(clavesInOrden, valoresInOrden, clavesNoInOrden,valoresNoInOrden);
        } else{
           this.raiz= reconstruirConPostOrden(clavesInOrden, valoresInOrden, clavesNoInOrden,valoresNoInOrden);
        }
      }
    
    private NodoBinario<K,V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
                                       List<K> clavesEnPreOrden, List<V> valoresEnPreOrden){
        if (clavesInOrden.isEmpty()){
            //return NodoBinario.nodoVacio();
            return null;
        }
        int posicionDeClavePadreEnPreOrden = 0;
        K clavePadre = clavesEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        V valorPadre = valoresEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre, clavesInOrden);
        
        // para armar la rama izquierda
        List<K> clavesInOrdenPorIzquierda =  clavesInOrden.subList(0,posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0,posicionDeClavePadreEnInOrden);
        List<K> clavesPreOrdenPorIzquierda= clavesEnPreOrden.subList(1, posicionDeClavePadreEnInOrden+1);
        List<V> valoresPreOrdenPorIzquierda = valoresEnPreOrden.subList(1, posicionDeClavePadreEnInOrden+1);
        
        NodoBinario<K,V> hijoIzquierdo = reconstruirConPreOrden(clavesInOrdenPorIzquierda, valoresInOrdenPorIzquierda, 
                                                                clavesPreOrdenPorIzquierda, valoresPreOrdenPorIzquierda);
        // para armar la rama derecha
        List<K> clavesInOrdenPorDerecha =  clavesInOrden.subList(posicionDeClavePadreEnInOrden+1,clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden+1,valoresInOrden.size());
        List<K> clavesPreOrdenPorDerecha= clavesEnPreOrden.subList( posicionDeClavePadreEnInOrden+1,clavesEnPreOrden.size());
        List<V> valoresPreOrdenPorDerecha = valoresEnPreOrden.subList( posicionDeClavePadreEnInOrden+1,valoresEnPreOrden.size());
        
        NodoBinario<K,V> hijoDerecho = reconstruirConPreOrden(clavesInOrdenPorDerecha, valoresInOrdenPorDerecha, 
                                                                clavesPreOrdenPorDerecha, valoresPreOrdenPorDerecha);
        // armando el nodoActual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        
        return nodoActual;
        
    }
    
    private NodoBinario<K,V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
                                       List<K> clavesPostOrden, List<V> valoresPostOrden){
        if (clavesInOrden.isEmpty()){
            //return NodoBinario.nodoVacio();
            return null;
        }
        int posicionDeClavePadreEnPostOrden = clavesPostOrden.size()-1;
        K clavePadre = clavesPostOrden.get(posicionDeClavePadreEnPostOrden);
        V valorPadre = valoresPostOrden.get(posicionDeClavePadreEnPostOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre, clavesInOrden);
        
        // para armar la rama izquierda
        List<K> clavesInOrdenPorIzquierda =  clavesInOrden.subList(0,posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0,posicionDeClavePadreEnInOrden);
        List<K> clavesPreOrdenPorIzquierda= clavesPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresPreOrdenPorIzquierda = valoresPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        
        NodoBinario<K,V> hijoIzquierdo = reconstruirConPostOrden(clavesInOrdenPorIzquierda, valoresInOrdenPorIzquierda, 
                                                                clavesPreOrdenPorIzquierda, valoresPreOrdenPorIzquierda);
        // para armar la rama derecha
        List<K> clavesInOrdenPorDerecha =  clavesInOrden.subList(posicionDeClavePadreEnInOrden,posicionDeClavePadreEnPostOrden);
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden,posicionDeClavePadreEnPostOrden);
        List<K> clavesPreOrdenPorDerecha= clavesPostOrden.subList( posicionDeClavePadreEnInOrden+1,clavesPostOrden.size());
        List<V> valoresPreOrdenPorDerecha = valoresPostOrden.subList( posicionDeClavePadreEnInOrden+1,valoresPostOrden.size());
        
        NodoBinario<K,V> hijoDerecho = reconstruirConPostOrden(clavesInOrdenPorDerecha, valoresInOrdenPorDerecha, 
                                                                clavesPreOrdenPorDerecha, valoresPreOrdenPorDerecha);
        // armando el nodoActual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        
        return nodoActual;
        
    }
    
    private int posicionDeClave(K claveABuscar,List<K> listaDeClaves){
        for(int i=0;i<listaDeClaves.size();i++){
            K claveActual = listaDeClaves.get(i);
            if (claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
        return -1;
    }
    
// VACIAR ARBOL
    @Override
    public void vaciar() {
        this.raiz = (NodoBinario<K,V>)NodoBinario.nodoVacio();
    }

// ¿ES ARBOL VACIO?
    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }
// TAMAÑO DE LA LISTA
    @Override
    public int size() {       
        if (this.esArbolVacio()){
            return 0;
        }
        int cantidadDeNodos = 0; 
        Stack<NodoBinario<K,V>> PilaDeNodos = new Stack<>();
        PilaDeNodos.push(this.raiz);
        
        while (!PilaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = PilaDeNodos.pop();
            cantidadDeNodos++;
            if (!nodoActual.esVacioHijoDerecho()){
                PilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()){
                PilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
            
           
        }
        return cantidadDeNodos;
    }
// cantidad de hijos derechos
    public int cantidadHijosDerechos() {
        return cantidadHijosDerechos(this.raiz);
    }

    private int cantidadHijosDerechos(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int hdPorRamaIzquierda = cantidadHijosDerechos(nodoActual.getHijoIzquierdo());
        int hdPorRamaDerecha = cantidadHijosDerechos(nodoActual.getHijoDerecho());
        if (nodoActual.esVacioHijoDerecho()) {
            return hdPorRamaIzquierda + hdPorRamaDerecha + 1;
        }
        return hdPorRamaIzquierda + hdPorRamaDerecha;
    }
    
// ALTURA de forma iterativa 
    public int alturaIt(){
       
        if (this.esArbolVacio()){
            return 0;
        }
        int alturaDelArbol=0;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            int cantidadNodosEnCola = colaDeNodos.size();
            int i =0;
            while(i<cantidadNodosEnCola){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            
                if (!nodoActual.esVacioHijoIzquierdo()){
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }            
                if (!nodoActual.esVacioHijoDerecho()){
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }
    
    

 // ALTURA de forma recursiva   
    @Override
    public int altura() {
        return altura(this.raiz);
    }
    public int altura(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        if(alturaPorIzquierda>alturaPorDerecha){
            return alturaPorIzquierda+1;
        }
        return alturaPorDerecha+1; 
    }

 // NIVEL DE UN ARBOL
    @Override
    public int nivel() {
        return altura()-1;
    }
    
// MINIMO Y MAXIMO DE UN ARBOL
    @Override
    public K minimo() {
        if (esArbolVacio()){
            return null;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
            
        }
        return nodoAnterior.getClave();
    }
    @Override
    public K maximo() {
                if (esArbolVacio()){
            return null;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoDerecho();
            
        }
        return nodoAnterior.getClave();
    }
    
    
 /* vamos a implementar un metodo que retorne si un arbol binario solo tiene nodos
    completos, es decir nodos que tengan sus dos hijos diferentes de vacio en el 
    nivel n 
     */
    public boolean tieneNodosCompletosEnNivel(int nivelObjetivo) {
        return tieneNodosCompletosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private boolean tieneNodosCompletosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;
        }
        if (nivelActual == nivelObjetivo) {
            return !nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho();
        }
        boolean completoPorIzq = this.tieneNodosCompletosEnNivel(nodoActual.getHijoIzquierdo(),
                nivelObjetivo, nivelActual + 1);
        boolean completoPorDer = this.tieneNodosCompletosEnNivel(nodoActual.getHijoDerecho(),
                nivelObjetivo, nivelActual + 1);
        return completoPorIzq && completoPorDer;

    }   
// INSERTAR ITERATIVO
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        // 1. La clave no debe de ser nulo
        // 2. El valor no debe de ser nulo
        // 3. Si el arbol es vacio el nodo a insertar sera la raiz
        // 4. si el arbol no es vacio se preguntara si clave es mayor o menor y depende al resultado 
        //    el "nodoActual" cambiara, esto se repite hasta que el nodo sea nulo
        // 5. Una vez encontrado en que lugar se va a insertar el DATO, hay que enlazarlo con el nodo padre.
        if (claveAInsertar == null ){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAInsertar == null){
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
        
        
        if (this.esArbolVacio()){
            this.raiz = new NodoBinario<>(claveAInsertar,valorAInsertar);
            return;
        }
        
        NodoBinario<K,V> nodoActual= this.raiz;
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>)NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual)<0){
                nodoActual = nodoActual.getHijoIzquierdo();
            }
            else if (claveAInsertar.compareTo(claveActual)>0){
                nodoActual = nodoActual.getHijoDerecho();
            }else{
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        
        NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar,valorAInsertar);    
        K claveDelPadre = nodoAnterior.getClave(); 
        
        if (claveAInsertar.compareTo(claveDelPadre)<0){
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        }else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
        
    }
    
// INSERTAR RECURSIVO
    public void insertarR(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<K, V>(claveAInsertar, valorAInsertar);
            return;
        }
        NodoBinario<K, V> nodoActual = this.raiz;

        insertarR(nodoActual, claveAInsertar, valorAInsertar);
    }
    private void insertarR(NodoBinario<K, V> nuevoNodoRaiz, K claveAInsertar, V valorAInsertar) {
        K claveActual = nuevoNodoRaiz.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0) {
            if (nuevoNodoRaiz.esVacioHijoIzquierdo()) {
                NodoBinario<K, V> nuevoHijoHijoIzquierdo = new NodoBinario<>(claveAInsertar, valorAInsertar);
                nuevoNodoRaiz.setHijoIzquierdo(nuevoHijoHijoIzquierdo);
                return;
            } else {
                nuevoNodoRaiz = nuevoNodoRaiz.getHijoIzquierdo();
            }
        } else if (claveAInsertar.compareTo(claveActual) > 0) {
            if (nuevoNodoRaiz.esVacioHijoDerecho()) {
                NodoBinario<K, V> nuevoHijoHijoDerecho = new NodoBinario<>(claveAInsertar, valorAInsertar);
                nuevoNodoRaiz.setHijoDerecho(nuevoHijoHijoDerecho);
                return;
            } else {
                nuevoNodoRaiz = nuevoNodoRaiz.getHijoDerecho();
            }
        } else {
            nuevoNodoRaiz.setValor(valorAInsertar);
            return;
        }
        insertarR(nuevoNodoRaiz, claveAInsertar, valorAInsertar);
    }
    
// ELIMINAR 
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            throw new IllegalArgumentException("La clave no existe en el arbol");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> posibleNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(posibleNuevoHijoDerecho);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> posibleNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHijoIzquierdo);
            return nodoActual;
        }
        //si llego a este punto quiere decir que ya encontre el nodo donde
        //esta la clave a eliminar
        //caso 1: es hoja
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //caso 2:
        //caso 2.1 solo tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso 2.1 solo tiene hijo derecho
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoDerecho();
        }
        //caso 3
        NodoBinario<K, V> nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> posibleNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), nodoReemplazo.getClave());
        nodoActual.setHijoDerecho(posibleNuevoHijoDerecho);
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    
    }
    
// ¿CONTIENE CLAVE?
    @Override
    public boolean contiene (K clave) {
         return this.buscar(clave) != null;
    }
    
// BUSCAR ITERATIVO
    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar==null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (this.esArbolVacio()){
            return null;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)){
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual)==0){
                return nodoActual.getValor();
            }else if (claveABuscar.compareTo(claveActual)<0){
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{
                nodoActual=nodoActual.getHijoDerecho();
            }
        }
        // si llego a este punto quiere decir que no se encuentra la claveABuscar
        // en el arbol.
        return  null;
    }
    
// RECORRIDO EN INORDEN RECURSIVO
    @Override
    public List<K> recorridoEnInOrden() {
         List<K> recorrido = new ArrayList<>(); //To change body of generated methods, choose Tools | Templates.
        //para una implementacion recursica, se necesita
        //un metodo amigo que haga el grueso del trabajo
        if (!this.esArbolVacio()) {
            recorridoEnInOrden(this.raiz, recorrido);
        }
        return recorrido;
    }

    private void recorridoEnInOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para un caso base 
        if (NodoBinario.esNodoVacio(nodoActual)) {//como decir n = 0;
            return;
        }

        if (!nodoActual.esVacioHijoIzquierdo()) {
            recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        }
        recorrido.add(nodoActual.getClave());
        if (!nodoActual.esVacioHijoDerecho()) {
            recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
        }
    }
    
//RECORRIDO EN PREORDEN ITERATIVO
    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()){
            return recorrido;
        }
        // offer inserta datos a la cola 
        // poll saca datos de la cola
        Stack<NodoBinario<K,V>> PilaDeNodos = new Stack<>();
        PilaDeNodos.push(this.raiz);
        
        while (!PilaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = PilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()){
                PilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()){
                PilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
            
           
        }
        return recorrido;
    }

   
    
//RECORRIDO EN POSTORDEN ITERATIVO
     @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        meterPilaParaPostOrden(nodoActual, pilaDeNodos);

        //empezamos a iterar sobre la pila
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    //volver a hacer el mismo bucle incial
                    meterPilaParaPostOrden(nodoDelTope.getHijoDerecho(), pilaDeNodos);
                }
            }
        }
        return recorrido;
    }

    private void meterPilaParaPostOrden(NodoBinario<K, V> nodoActual, Stack<NodoBinario<K, V>> pilaDeNodos) {
        //el proceso inicial antes de iterar en la pila
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }
    
// RECORRIDO EN POSTORDEN RECURSIVO 
    public List<K> recorridoEnPostOrdenRec(){
        List<K> recorrido = new ArrayList<K>();
        
        recorridoEnPostOrdenRec(this.raiz,recorrido);
        return recorrido;
    }
    public void recorridoEnPostOrdenRec(NodoBinario<K,V> nodoActual, List<K> recorrido){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return;
        }
        recorridoEnPostOrdenRec(nodoActual.getHijoIzquierdo(),recorrido);
        recorridoEnPostOrdenRec(nodoActual.getHijoDerecho(),recorrido);
        recorrido.add(nodoActual.getClave());
        
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
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()){
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            
            if (!nodoActual.esVacioHijoDerecho()){
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public String toString() {
        return this.recorridoPorNiveles().toString();
    }
//---------------------------------------------------------------------------------------------------------------------------
//                              EJERCICIOS DEL PRACTICO
//---------------------------------------------------------------------------------------------------------------------------    
//2.- Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario    
    
    public int cantidadDeNodosQuetTieneSoloHijoIzquierdo() {
         
        return cantidadDeNodosQuetTieneSoloHijoIzquierdo( this.raiz);
    }

    private int cantidadDeNodosQuetTieneSoloHijoIzquierdo(
            NodoBinario<K,V> nodoActual) {
            int i=0;
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            int cant = 0;
            if ((nivelDelNodoEnArbol(nodoActual) >= i) && 
                (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo()))&&(NodoBinario.esNodoVacio(nodoActual.getHijoDerecho()))) {
                    cant = 1;
            }
            cant = cant + cantidadDeNodosQuetTieneSoloHijoIzquierdo( nodoActual.getHijoDerecho());
            cant = cant + cantidadDeNodosQuetTieneSoloHijoIzquierdo( nodoActual.getHijoIzquierdo());
            return cant;
        }
        return 0;
    }
    private int nivelDelNodoEnArbol(NodoBinario<K,V> nodoABuscar) {
        return nivelDelNodoEnArbol(nodoABuscar, this.raiz);
    }
    private int nivelDelNodoEnArbol(NodoBinario<K,V> nodoABuscar, NodoBinario<K,V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            if (nodoActual.getClave().compareTo(nodoABuscar.getClave()) > 0) {
                return nivelDelNodoEnArbol(nodoABuscar, nodoActual.getHijoIzquierdo()) + 1;
            }
            if (nodoActual.getClave().compareTo(nodoABuscar.getClave()) < 0) {
                return nivelDelNodoEnArbol(nodoABuscar, nodoActual.getHijoDerecho()) + 1;
            }
        }
        return 0;
    }
  //3.-Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario
    
    public int cantidadDeNodosQueSoloTienenHijoIzquierdoNoVacio() {
        int cantidadDeNodos = 0;
        int nivelActual = -1;
        LinkedList<NodoBinario<K, V>> colaDeNivel = new LinkedList<>();
        LinkedList<NodoBinario<K, V>> colaDeHijos = new LinkedList<>();
        colaDeHijos.add(raiz);
        NodoBinario<K, V> nodoActual = raiz;
        do {
            while (!colaDeNivel.isEmpty()) {
                nodoActual = colaDeNivel.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeHijos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeHijos.offer(nodoActual.getHijoDerecho());
                }
            }
            //cambio de nivel
            nivelActual++;
            while (!colaDeHijos.isEmpty()) {
                NodoBinario<K, V> nodoEnElNivel = colaDeHijos.poll();
                colaDeNivel.offer(nodoEnElNivel);
                if (!nodoEnElNivel.esVacioHijoIzquierdo() && nodoEnElNivel.esVacioHijoDerecho()) {
                    cantidadDeNodos++;
                }
            }
        } while (!colaDeNivel.isEmpty());
        return cantidadDeNodos;
    }
    
  // 4.- Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío
  //     en un árbol binario, pero solo en el nivel N
    
    
    public int cantidadDeHijosIzquierdoDebajoDelNivel(int i) {
        return cantidadDeHijosDerechosDebajoDelNivel(i, this.raiz);
    }

    private int cantidadDeHijosDerechosDebajoDelNivel(int i,
            NodoBinario<K,V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            int cant = 0;
            if ((nivelDelNodoEnArbol(nodoActual) >= i) && (nivelDelNodoEnArbol(nodoActual) <= i) && 
                (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo()))&& (NodoBinario.esNodoVacio(nodoActual.getHijoDerecho()))) {
                    cant = 1;
            }
                
                cant = cant + cantidadDeHijosDerechosDebajoDelNivel(i, nodoActual.getHijoDerecho());
                cant = cant + cantidadDeHijosDerechosDebajoDelNivel(i, nodoActual.getHijoIzquierdo());
                return cant;
            
            
            
        }
        return 0;
    }

  // 5.- Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío 
    //   en un árbol binario, pero solo después del nivel N  
    
   public int cantidadDeHijosIzquierdoDebajoDelNivelIterativo(int nivel) {
        if (esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K,V>> cola = new LinkedList<>();
        int cant = 0;
        NodoBinario<K,V> nodoActual = this.raiz;
        cola.offer(nodoActual);
        while(!cola.isEmpty()) {
            nodoActual = cola.poll();
            if (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
                if (nivelDelNodoEnArbol(nodoActual) >= nivel) {
                    cant++;
                }
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if (NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
                cola.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }
    
    
   //6.- Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo 
   //    izquierdo no vacío en un árbol binario, pero solo antes del nivel N
    public int cantidadDeHijosIzquierdoAntesDelNivelIterativo(int nivel) {
        if (esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K,V>> cola = new LinkedList<>();
        int cant = 0;
        NodoBinario<K,V> nodoActual = this.raiz;
        cola.offer(nodoActual);
        while(!cola.isEmpty()) {
            nodoActual = cola.poll();
            if (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())&&NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
                if (nivelDelNodoEnArbol(nodoActual) < nivel) {
                    cant++;
                }
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if (!NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
                cola.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }
    
    //7.- Implemente un método recursivo que reciba como parámetro otro árbol binario de búsqueda que retorne verdadero, si el 
    //    árbol binario es similar al árbol binario recibido como parámetro, falso en caso contrario.
    
    
     public boolean esSimilar(ArbolBinarioBusqueda<K,V> arbolAComparar) {
        return esSimilar(arbolAComparar.raiz, this.raiz);
    }

    private boolean esSimilar(NodoBinario<K,V> raizA, NodoBinario<K,V> raizB) {
        if (NodoBinario.esNodoVacio(raizA) && NodoBinario.esNodoVacio(raizB)) {
            return true;
        }
        if (NodoBinario.esNodoVacio(raizA) && !NodoBinario.esNodoVacio(raizB)
                || !NodoBinario.esNodoVacio(raizA) && NodoBinario.esNodoVacio(raizB)) {
            return false;
        }
        return esSimilar(raizA.getHijoIzquierdo(), raizB.getHijoIzquierdo())
                && esSimilar(raizA.getHijoDerecho(), raizB.getHijoDerecho());
    }
    public int cantHijosEnNivelN(int n) {
        int nivelActual = 0;
        int cantHijos = 0;
        LinkedList<NodoBinario<K,V>> colaDeNivel = new LinkedList<>();
        colaDeNivel.add(raiz);
        NodoBinario<K,V> nodoActual = raiz;
        if (n == 0) {
            if (nodoActual.esHoja()) {
                return 1;
            }
        }
        do {
            LinkedList<NodoBinario<K,V>> colaDeHijos = new LinkedList<>();
            
            while (!colaDeNivel.isEmpty()) {
                
                nodoActual = colaDeNivel.poll(); 
                
                //COLA_DE_NIVEL = [ ]
                
                //COLA_DE_HIJOS = [ G, H, F, ]
                
                // NODO_ACTUAL = F
                
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeHijos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeHijos.offer(nodoActual.getHijoDerecho());
                }
            }
            
         
            //cambio de nivel
            nivelActual++;
            if (nivelActual == n) {
                while (!colaDeHijos.isEmpty()) {
                    NodoBinario<K,V> nodoEnElNivel = colaDeHijos.poll();
                    if (nodoEnElNivel.esHoja()) {
                        cantHijos++;
                    }
                }
                return cantHijos;
            }
               
            
            while (!colaDeHijos.isEmpty()) {
                
                //COLA_DE_NIVEL = [ D, E, F]
                
                //COLA_DE_HIJOS = [  ]
                
                colaDeNivel.offer(colaDeHijos.poll());
            }
        } while (!colaDeNivel.isEmpty());
        return cantHijos;
    }
    
    //9. Para un árbol binario implemente un método que retorne la cantidad de nodos que tienen ambos hijos desde el nivel N.
    
    public int cantidadDeHijosCompletoDebajoDelNivel(int i) {
        return cantidadDeHijosCompletoDebajoDelNivel(i, this.raiz);
    }

    private int cantidadDeHijosCompletoDebajoDelNivel(int i,
            NodoBinario<K,V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            int cant = 0;
            if ((nivelDelNodoEnArbol(nodoActual) >= i) && 
                (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) && (!NodoBinario.esNodoVacio(nodoActual.getHijoDerecho()))) {
                    cant = 1;
            }
                
                cant = cant + cantidadDeHijosCompletoDebajoDelNivel(i, nodoActual.getHijoDerecho());
                cant = cant + cantidadDeHijosCompletoDebajoDelNivel(i, nodoActual.getHijoIzquierdo());
                return cant;            
        }
        return 0;
    }
    
    //10. Implementar un método que retorne un nuevo árbol binario de búsqueda invertido.
    
     public void invertir(){
        invertirArbol(raiz);
    }
    private void invertirArbol(NodoBinario nodoActual){
        if(!NodoBinario.esNodoVacio(nodoActual)){
            invertirArbol(nodoActual.getHijoIzquierdo());
            invertirArbol(nodoActual.getHijoDerecho());
            NodoBinario<K,V> nodoAuxiliarIzquierdo=nodoActual.getHijoIzquierdo();
            NodoBinario<K,V> nodoAuxiliarDerecho=nodoActual.getHijoDerecho();
            nodoActual.setHijoDerecho(nodoAuxiliarIzquierdo);
            nodoActual.setHijoIzquierdo(nodoAuxiliarDerecho);
        }
    }
    
    //11. Implementar un método que retorne verdadero si un árbol binario esta lleno.
    public boolean ArbolCompleto(){
        int a=0;
        boolean b = false;
        for (int i=0;i<=nivel();i++){
            a=a+2^i;
        }
        if (size()==a){
            b = true;
        }
    return b;
}
}    

   
