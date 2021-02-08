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
public class AVL <K extends Comparable<K>,V> extends ArbolBinarioBusqueda<K, V>{
    private  static final byte DIFERENCIA_MAXIMA = 1;
    
    // insertar
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {

        if (claveAInsertar == null ){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAInsertar == null){
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
        
        
        super.raiz = this.insertar(this.raiz,claveAInsertar, valorAInsertar);
        
    }
    
    //balancear
    
    private NodoBinario<K,V> insertar (NodoBinario<K,V> nodoActual,K claveAInsertar, V valorAInsertar){
        if (NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar,valorAInsertar);
            return nuevoNodo;
        }
        K claveActual  = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual)>0){
            NodoBinario<K,V> supuestoNuevoHijoDerecho = insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return this.balancear(nodoActual);
        }
        
        if (claveAInsertar.compareTo(claveActual)<0){
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return this.balancear(nodoActual);
        }
        
        // si llego hasta aqui quiere decir que el nodo actual esta en la clave a insertar 
        
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    private NodoBinario<K,V> balancear (NodoBinario<K,V> nodoActual){
        int alturaRamaIzq = altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDer = altura(nodoActual.getHijoDerecho());
        int diferencia = alturaRamaIzq-alturaRamaDer;
        
        if ( diferencia > DIFERENCIA_MAXIMA){
            NodoBinario<K,V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzq = altura(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDer = altura(hijoIzquierdo.getHijoDerecho());
            if (alturaRamaDer>alturaRamaIzq){
                return this.rotacionDobleADerecha(nodoActual);
            }else{
                return this.rotacionSimpleADerecha(nodoActual);
            }
        }else if (diferencia < DIFERENCIA_MAXIMA){

            NodoBinario<K,V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzq = altura(hijoDerecho.getHijoIzquierdo());
            alturaRamaDer = altura(hijoDerecho.getHijoDerecho());
            if (alturaRamaIzq>alturaRamaDer){
                return rotacionDobleAIzquierda(nodoActual);
            }else{
                return rotacionSimpleAIzquierda(nodoActual);
            }
        }
        return nodoActual;
    }
    
    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
        nodoActual.setHijoIzquierdo(rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo()));
        return this.rotacionSimpleADerecha(nodoActual);
    }

    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual){
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoIzquierdo(nodoQueRota);
               
        return nodoQueRota;
    }

    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual){
        nodoActual.setHijoDerecho(rotacionSimpleADerecha(nodoActual.getHijoDerecho()));
        return rotacionSimpleAIzquierda(nodoActual);
    }
 
//-----------------------------------------------------------------------------------------------------------------
//                                          EJERCICIOS DEL PRACTICO
//----------------------------------------------------------------------------------------------------------------- 
// 8. Implemente el método eliminar de un árbol AVL   
    public V eliminarAVL(K claveAEliminar) {
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
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> posibleNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleNuevoHijoIzquierdo);
            return balancear(nodoActual);
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
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //caso 2.1 solo tiene hijo derecho
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return balancear(nodoActual.getHijoDerecho());
        }
        //caso 3
        NodoBinario<K, V> nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> posibleNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), nodoReemplazo.getClave());
        nodoActual.setHijoDerecho(posibleNuevoHijoDerecho);
        nodoActual.setClave(nodoReemplazo.getClave());
        nodoActual.setValor(nodoReemplazo.getValor());
        return balancear(nodoActual);
    }

    @Override
    protected NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    
    }
    
   
}
