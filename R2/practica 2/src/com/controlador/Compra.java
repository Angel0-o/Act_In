package com.controlador;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Compra extends Thread{
    protected Socket socketCliente;
    protected BufferedReader entrada;
    protected ObjectOutputStream salidaObjeto;
    protected int idCompra; //1 o 0 
  

    public Compra(Socket cliente) {
       this.socketCliente = cliente;
    }
}
