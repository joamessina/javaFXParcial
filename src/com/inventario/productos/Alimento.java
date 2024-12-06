/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.productos;

import java.time.LocalDate;

/**
 *
 * @author joaxx
 * 
 * Clase que representa un alimento en el inventario.
 */
public class Alimento extends Producto {
    private static final long serialVersionUID = 1L;

    // Atributos específicos de los alimentos
    private LocalDate fechaVencimiento;
    private boolean refrigeracionRequerida;

    /**
     * Constructor sin parámetros.
     */
    public Alimento() {
        super();
        setTipo("Alimento");
        this.fechaVencimiento = LocalDate.now();
        this.refrigeracionRequerida = false;
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param nombre                Nombre del alimento.
     * @param precio                Precio del alimento.
     * @param stock                 Cantidad disponible en inventario.
     * @param fechaVencimiento      Fecha de vencimiento del alimento.
     * @param refrigeracionRequerida Indica si el alimento requiere refrigeración.
     */
    public Alimento(String nombre, double precio, int stock,
                    LocalDate fechaVencimiento, boolean refrigeracionRequerida) {
        super(nombre, precio, stock);
        setTipo("Alimento");
        setFechaVencimiento(fechaVencimiento);
        setRefrigeracionRequerida(refrigeracionRequerida);
    }

    /**
     * Muestra los detalles del alimento.
     */
    @Override
    public void mostrarDetalles() {
        System.out.println("=== Alimento ===");
        System.out.println(this.toString());
    }

    /**
     * Verifica si el alimento está disponible en la cantidad requerida y no está vencido.
     *
     * @param cantidad La cantidad requerida.
     * @return true si hay suficiente stock y no está vencido, false en caso contrario.
     */
    @Override
    public boolean verificarDisponibilidad(int cantidad) {
        return getStock() >= cantidad && !estaVencido();
    }

    /**
     * Verifica si el alimento está vencido.
     *
     * @return true si el alimento está vencido, false en caso contrario.
     */
    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    /**
     * Obtiene la fecha de vencimiento del alimento.
     *
     * @return La fecha de vencimiento.
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Establece la fecha de vencimiento del alimento.
     *
     * @param fechaVencimiento La fecha de vencimiento.
     * @throws IllegalArgumentException Si la fecha de vencimiento es nula.
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Indica si el alimento requiere refrigeración.
     *
     * @return true si requiere refrigeración, false en caso contrario.
     */
    public boolean isRefrigeracionRequerida() {
        return refrigeracionRequerida;
    }

    /**
     * Establece si el alimento requiere refrigeración.
     *
     * @param refrigeracionRequerida true si requiere refrigeración, false en caso contrario.
     */
    public void setRefrigeracionRequerida(boolean refrigeracionRequerida) {
        this.refrigeracionRequerida = refrigeracionRequerida;
    }

    /**
     * Retorna una representación en cadena del alimento.
     *
     * @return Una cadena que representa al alimento.
     */
    @Override
    public String toString() {
        return super.toString() +
                ", fechaVencimiento=" + fechaVencimiento +
                ", refrigeracionRequerida=" + refrigeracionRequerida +
                '}';
    }
}
