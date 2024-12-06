/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.interfaces;
import com.inventario.productos.Producto;

/**
 *
 * @author joaxx
 * Interfaz funcional para filtrar productos según un criterio específico.
 *
 * @param <T> Tipo de producto que extiende de Producto.
 */
@FunctionalInterface
public interface FiltroProducto<T extends Producto> {
    boolean filtrar(Producto producto);
}