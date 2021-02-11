
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
public class AVL <K extends Comparable<K>,V> extends ArbolBinarioBusqueda<K, V>{
    private  static final byte DIFERENCIA_MAXIMA = 1;
    
    // insertar
    @Override
    public void insertar(K clave, V valor) {
        if(clave == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if(valor == null) {
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
        
        super.raiz = this.insertar(this.raiz, clave, valor);
        
        
    }
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual, K claveInsertar, V valorInsertar) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveInsertar, valorInsertar);
            return nuevoNodo;
        }
        
        K claveActual = nodoActual.getClave();
        if(claveInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> nuevoHijoDerecho = insertar(nodoActual.getHijoDerecho(), claveInsertar, valorInsertar);
            nodoActual.setHijoDerecho(nuevoHijoDerecho);
            return this.balancear(nodoActual);
            //return nodoActual;
        }
        if(claveInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> nuevoHijoizquierdo = insertar(nodoActual.getHijoIzquierdo(), claveInsertar, valorInsertar);
            nodoActual.setHijoIzquierdo(nuevoHijoizquierdo);
            return this.balancear(nodoActual);
            //return nodoActual;
        }
        
        //Si llego acac quiere decir que en el nodo actual esta la clave a insertar
        
        nodoActual.setValor(valorInsertar);
        return nodoActual;
    }
    
    private NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual) {
        int alturaRamaIzq = altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDer = altura(nodoActual.getHijoDerecho());
        int diferencia = alturaRamaIzq - alturaRamaDer;
        if(diferencia > DIFERENCIA_MAXIMA) {
            //SI HAY QUE BALANCEAR
            NodoBinario<K,V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzq = altura(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDer = altura(hijoIzquierdo.getHijoDerecho());
            if(alturaRamaDer > alturaRamaIzq) {
                return rotacionDobleADerecha(nodoActual);
            } else {
                return rotacionSimpleADerecha(nodoActual);
            }            
        } else if(diferencia < -DIFERENCIA_MAXIMA) {
            NodoBinario<K,V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzq = altura(hijoDerecho.getHijoIzquierdo());
            alturaRamaDer = altura(hijoDerecho.getHijoDerecho());
            if(alturaRamaDer > alturaRamaIzq) {
                return rotacionSimpleAIzquierda(nodoActual);
            } else {
                return rotacionDobleAIzquierda(nodoActual);
            }
            
        }
        return nodoActual;
    }
    
   private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual) {
       NodoBinario<K,V> nodoQueRotaIzquierda = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoQueRotaIzquierda);
        return this.rotacionSimpleADerecha(nodoActual);
    }
    
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoQueRotaDerecha = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoQueRotaDerecha);
        return this.rotacionSimpleAIzquierda(nodoActual);
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
    
 
        @Override
    public String toString() {
        return this.recorridoPorNiveles().toString();
    }
}
