/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.productos;

/**
 *
 * @author joaxx
 *
 * Clase que representa un electrodoméstico en el inventario.
 */
public class Electrodomestico extends Producto {
    private static final long serialVersionUID = 1L;

    // Atributos específicos de los electrodomésticos
    private int garantiaMeses;
    private CategoriaEnergetica categoriaEnergetica;

    /**
     * Constructor sin parámetros.
     */
    public Electrodomestico() {
        super();
        setTipo("Electrodomestico");
        this.garantiaMeses = 0;
        this.categoriaEnergetica = CategoriaEnergetica.G;
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param nombre              Nombre del electrodoméstico.
     * @param precio              Precio del electrodoméstico.
     * @param stock               Cantidad disponible en inventario.
     * @param garantiaMeses       Duración de la garantía en meses.
     * @param categoriaEnergetica Categoría energética del electrodoméstico.
     */
    public Electrodomestico(String nombre, double precio, int stock,
                            int garantiaMeses, CategoriaEnergetica categoriaEnergetica) {
        super(nombre, precio, stock);
        setTipo("Electrodomestico");
        setGarantiaMeses(garantiaMeses);
        setCategoriaEnergetica(categoriaEnergetica);
    }

    /**
     * Muestra los detalles del electrodoméstico.
     */
    @Override
    public void mostrarDetalles() {
        System.out.println("=== Electrodoméstico ===");
        System.out.println(this.toString());
    }

    /**
     * Verifica si el electrodoméstico está disponible en la cantidad requerida.
     *
     * @param cantidad La cantidad requerida.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    @Override
    public boolean verificarDisponibilidad(int cantidad) {
        return getStock() >= cantidad;
    }

    /**
     * Obtiene la garantía en meses del electrodoméstico.
     *
     * @return La garantía en meses.
     */
    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    /**
     * Establece la garantía en meses del electrodoméstico.
     *
     * @param garantiaMeses La garantía en meses.
     * @throws IllegalArgumentException Si la garantía es negativa.
     */
    public void setGarantiaMeses(int garantiaMeses) {
        if (garantiaMeses < 0) {
            throw new IllegalArgumentException("La garantía no puede ser negativa.");
        }
        this.garantiaMeses = garantiaMeses;
    }

    /**
     * Obtiene la categoría energética del electrodoméstico.
     *
     * @return La categoría energética.
     */
    public CategoriaEnergetica getCategoriaEnergetica() {
        return categoriaEnergetica;
    }

    /**
     * Establece la categoría energética del electrodoméstico.
     *
     * @param categoriaEnergetica La categoría energética.
     * @throws IllegalArgumentException Si la categoría energética es nula.
     */
    public void setCategoriaEnergetica(CategoriaEnergetica categoriaEnergetica) {
        if (categoriaEnergetica == null) {
            throw new IllegalArgumentException("La categoría energética no puede ser nula.");
        }
        this.categoriaEnergetica = categoriaEnergetica;
    }

    /**
     * Retorna una representación en cadena del electrodoméstico.
     *
     * @return Una cadena que representa al electrodoméstico.
     */
    @Override
    public String toString() {
        return super.toString() +
                ", garantiaMeses=" + garantiaMeses +
                ", categoriaEnergetica=" + categoriaEnergetica +
                '}';
    }
}
