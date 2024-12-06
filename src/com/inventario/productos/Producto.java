/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.productos;

import java.io.Serializable;

/**
 *
 * @author joaxx
 * 
 * Clase abstracta que representa un producto genérico en el inventario.
 */
public abstract class Producto implements Serializable{
    private static final long serialVersionUID = 1L;

    // Contador global para generar IDs únicos
    private static int contadorGlobal = 0;

    // Atributos privados
    private int codigo;
    private String nombre;
    private double precio;
    private int stock;
    private String tipo;

    /**
     * Constructor sin parámetros.
     */
    public Producto() {
        this.codigo = ++contadorGlobal;
        this.nombre = "";
        this.precio = 0.0;
        this.stock = 0;
    }

    /**
     * Constructor con nombre y precio.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(String nombre, double precio) {
        this();
        setNombre(nombre);
        setPrecio(precio);
    }

    /**
     * Constructor con todos los parámetros excepto código.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param stock  Cantidad disponible en inventario.
     */
    public Producto(String nombre, double precio, int stock) {
        this(nombre, precio);
        setStock(stock);
    }

    /**
     * Obtiene el código único del producto.
     *
     * @return El código del producto.
     */
    public int getCodigo() {
        return codigo; 
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nombre del producto.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El precio del producto.
     * @throws IllegalArgumentException Si el precio es negativo.
     */
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    /**
     * Obtiene el stock disponible del producto.
     *
     * @return El stock disponible.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del producto.
     *
     * @param stock El stock disponible.
     * @throws IllegalArgumentException Si el stock es negativo.
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    /**
     * Obtiene el tipo del producto.
     *
     * @return El tipo del producto.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del producto.
     *
     * @param tipo El tipo del producto.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método abstracto para mostrar los detalles del producto.
     */
    public abstract void mostrarDetalles();

    /**
     * Método abstracto para verificar la disponibilidad del producto.
     *
     * @param cantidad La cantidad requerida.
     * @return true si el producto está disponible en la cantidad requerida, false en caso contrario.
     */
    public abstract boolean verificarDisponibilidad(int cantidad);

    /**
     * Retorna una representación en cadena del producto.
     *
     * @return Una cadena que representa al producto.
     */
    
    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
