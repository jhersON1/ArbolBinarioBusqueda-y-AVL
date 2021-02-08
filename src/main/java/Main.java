
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author neON
 */
public class Main {
     public static void main(String[] args) {

           ArbolBinarioBusqueda<Integer,String> arbolPrueba = new ArbolBinarioBusqueda<>();
           ArbolBinarioBusqueda<Integer,String> arbolAVL = new AVL<>();
          
           arbolPrueba.insertar(50,"azul");
           arbolPrueba.insertar(78,"naranja");
           arbolPrueba.insertar(74,"zapato");
           arbolPrueba.insertar(30,"jeans");
           arbolPrueba.insertar(40,"amarillo");
           arbolPrueba.insertar(20,"negro");
           arbolPrueba.insertar(25,"cafe");
           arbolPrueba.insertar(24,"camisa");
           arbolPrueba.insertar(23,"mesa");
           arbolPrueba.insertar(28,"TV");
           arbolPrueba.insertar(73,"TV2");
           
           arbolAVL.insertar(50,"azul");
           arbolAVL.insertar(78,"naranja");
           arbolAVL.insertar(74,"zapato");
           arbolAVL.insertar(30,"jeans");
           arbolAVL.insertar(40,"amarillo");
           arbolAVL.insertar(20,"negro");
           arbolAVL.insertar(25,"cafe");
           arbolAVL.insertar(24,"camisa");
           arbolAVL.insertar(23,"mesa");
           arbolAVL.insertar(28,"TV");
           arbolAVL.insertar(73,"TV2");
           
           List listClaveInOrden = new ArrayList();
           List listValorInOrden = new ArrayList();
           List listClavePreOrden = new ArrayList();
           List listValorPreOrden = new ArrayList();
           
           listClaveInOrden.add(20);
           listClaveInOrden.add(23);
           listClaveInOrden.add(24);
           listClaveInOrden.add(25);
           listClaveInOrden.add(28);
           listClaveInOrden.add(30);
           listClaveInOrden.add(40);
           listClaveInOrden.add(50);
           listClaveInOrden.add(74);
           listClaveInOrden.add(78);
           
           listValorInOrden.add("negro");
           listValorInOrden.add("mesa");
           listValorInOrden.add("camisa");
           listValorInOrden.add("cafe");
           listValorInOrden.add("TV");
           listValorInOrden.add("jeans");
           listValorInOrden.add("amarillo");
           listValorInOrden.add("azul");
           listValorInOrden.add("zapato");
           listValorInOrden.add("naranja");
           
           listClavePreOrden.add(23);
           listClavePreOrden.add(24);
           listClavePreOrden.add(28);
           listClavePreOrden.add(25);
           listClavePreOrden.add(20);
           listClavePreOrden.add(40);
           listClavePreOrden.add(30);
           listClavePreOrden.add(74);
           listClavePreOrden.add(78);
           listClavePreOrden.add(50);
         
           listValorPreOrden.add("mesa");
           listValorPreOrden.add("camisa");
           listValorPreOrden.add("TV");
           listValorPreOrden.add("cafe");
           listValorPreOrden.add("negro");
           listValorPreOrden.add("amarillo");
           listValorPreOrden.add("jeans");
           listValorPreOrden.add("zapato");
           listValorPreOrden.add("naranja");
           listValorPreOrden.add("azul");
         
        ArbolBinarioBusqueda<Integer,String> arbolPrueba3 = new ArbolBinarioBusqueda<>();
        
        arbolPrueba3.insertar(50, "AZUL");
        arbolPrueba3.insertar(78, "NARANJA");
        arbolPrueba3.insertar(74, "ZAPATO");
        arbolPrueba3.insertar(30, "JEANS");
        arbolPrueba3.insertar(44, "AMARILLO");
        arbolPrueba3.insertar(20, "NEGRO");
        arbolPrueba3.insertar(25, "CAFE");
        arbolPrueba3.insertar(24, "CAMISA");
        arbolPrueba3.insertar(23, "MESA");
        arbolPrueba3.insertar(24, "TV");
        ArbolBinarioBusqueda<Integer,String> arbolPrueba4 = new ArbolBinarioBusqueda<>(listClaveInOrden,listValorInOrden,listClavePreOrden,listValorPreOrden,false);


//---------- PRACTICO #1 ----------------------------------------------------------
        // ejercicio1
        // todos los metodos implementados
        //reconstruccion
        //    System.out.println(arbolPrueba4);
        //maximo
        //    System.out.println(arbolPrueba.maximo());
        //ejercicio2
        //System.out.println(arbolPrueba.cantidadDeNodosQuetTieneSoloHijoIzquierdo());
        //ejercicio3
        // System.out.println(arbolPrueba.cantidadDeNodosQueSoloTienenHijoIzquierdoNoVacio());
        //ejercicio4
        //System.out.println(arbolPrueba.cantidadDeHijosIzquierdoDebajoDelNivel(3));
        //ejercicio5
        //System.out.println(arbolPrueba.cantidadDeHijosDerechosDebajoDelNivelIterativo(1));
        //ejercicio6
        //System.out.println(arbolPrueba.cantidadDeHijosIzquierdoAntesDelNivelIterativo(5));
        //ejercicio7
        // System.out.println(arbolPrueba.esSimilar(arbolPrueba3));
        //ejercicio8 (esta implementado en la clase AVL)
         //   System.out.println(arbolAVL.eliminar(78));
        //ejercicio9
        //System.out.println(arbolPrueba.cantidadDeHijosCompletoDebajoDelNivel(1));
        //ejercicio 10
        //arbolPrueba.invertir();
        //System.out.println(arbolPrueba);
        //ejercicio11
        //System.out.println(arbolPrueba.ArbolCompleto());
        
    }
}
