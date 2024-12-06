/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.utils;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author joaxx
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Serializa un objeto LocalDate en formato JSON.
     * 
     * @param out el escritor JSON donde se escribirá el valor.
     * @param value el objeto LocalDate a serializar.
     * @throws IOException si ocurre un error al escribir el valor.
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(FORMATTER));
        }
    }

    /**
     * Deserializa un objeto LocalDate desde un JSON.
     * 
     * @param in el lector JSON desde el cual se leerá el valor.
     * @return un objeto LocalDate deserializado.
     * @throws IOException si ocurre un error al leer el valor.
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String date = in.nextString();
        return LocalDate.parse(date, FORMATTER);
    }
}
