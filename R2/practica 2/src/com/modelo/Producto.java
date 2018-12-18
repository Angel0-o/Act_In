/*

* Arellano Cristopher 
* Hernandez Marco
*
*/

package com.modelo;

import java.io.Serializable;


public class Producto implements Serializable{
    String nombre;
    String dir;
    double precio;
    int existencia;
    int id;
    
    public Producto(String nombre,String dir,double precio,int existencia,int id){
        this.nombre=nombre;
        this.dir=dir;
        this.precio=precio;
        this.existencia=existencia;
        this.id=id; //  COSTRUCTOR de la clase producto
    }
    //geters y seters de la clase producto para la escritura y lectura de los datos
    public String getDir(){
        return this.dir;
    }
    public String getNombre(){
        return this.nombre;
    }
    public int getId(){
        return this.id;
    }
    public String getIdString(){
        return this.id+"";
    }
    public double getPrecio(){
        return this.precio;
    }
    public String getPrecioString(){
        return this.precio+"";
    }  
    
    public int getExistencia(){
        return this.existencia;
    }
    public void setExistencia(int e){
        this.existencia=e;
    }
    
 
    @Override
    public String toString(){
        return "Nombre: "+this.nombre+" ID: "+this.id+" Precio: "+this.precio;
    }
}


